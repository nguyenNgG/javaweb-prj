/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.order_details;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import nguyenng.utils.DBHelpers;

/**
 *
 * @author bchao
 */
public class Order_DetailsDAO implements Serializable {

    List<Order_DetailsDTO> order_Details_List;

    public List<Order_DetailsDTO> getOrder_Details_List() {
        return this.order_Details_List;
    }

    public boolean addOrderDetails(String orderID, Map<String, Integer> productList) throws SQLException, NamingException {
        /*
            --
            Order_Details Table
            ID = orderID
            Quantity = quantity
            PID = productID
        
         */
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1.1 Create connection using method built
            con = DBHelpers.makeConnection();
            //1.2 Configure so that statement is 
            // committed on completion, not on execution 
            con.setAutoCommit(false);

            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO "
                        + "Order_Details(ID, Quantity, PID) "
                        + "VALUES(?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                for (String productID : productList.keySet()) {
                    stm.setString(1, orderID);
                    stm.setInt(2, productList.get(productID));
                    stm.setString(3, productID);
                    // add statement to batch
                    stm.addBatch();
                    // flush parameter (safety measure)
                    stm.clearParameters();
                }
                //4. Execute Update and get int array 
                // (each int element correspond to a statement in the batch
                // arranged in the order added)
                int[] result = stm.executeBatch();
                con.commit();
                //5. Process result
                boolean foundFail = false;
                // traverse result to see if any statement failed (0 row affected)
                for (int i : result) {
                    if (i == 0) {
                        foundFail = true;
                    }
                } // end traverse result
                if (!foundFail) {
                    return true;
                } // end if all statement executed successfully (no failed)
            }//end if con existed
        } catch (SQLException ex) {
            System.out.println("Order_DetailsDAO _ SQLException: "
                    + ex.getMessage());
            // An error occurred, attempt to rollback
            try {
                con.rollback();
            } catch (SQLException ex_rollback) {
                System.out.println("Order_DetailsDAO _ SQLException: "
                        + ex_rollback.getMessage());
            } catch (NullPointerException ex_null) {
                System.out.println("Order_DetailsDAO _ NullPointerException: "
                        + ex_null.getMessage());
            }
        } finally {
            con.setAutoCommit(true);
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean deleteOrderDetails(String orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT ID "
                        + "FROM Order_Details "
                        + "WHERE ID LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                //4. Execute Update and get int
                int result = stm.executeUpdate();
                //5. Process result
                if (result > 0) {
                    return true;
                }//end if result existed
            }//end if con existed
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public void viewOrder_Details_List() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT ID, Quantity, PID "
                        + "FROM Order_Details";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("ID");
                    int quantity = rs.getInt("Quantity");
                    String productID = rs.getString("PID");
                    Order_DetailsDTO dto = new Order_DetailsDTO(id, productID, quantity);
                    if (this.order_Details_List == null) {
                        this.order_Details_List = new ArrayList<>();
                    } //end if order_details list not existed
                    this.order_Details_List.add(dto);
                }//end if while traversing result
            }//end if con existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public void searchOrder_Details(String orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT ID, Quantity, PID "
                        + "FROM Order_Details "
                        + "WHERE ID LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("ID");
                    int quantity = rs.getInt("Quantity");
                    String productID = rs.getString("PID");
                    Order_DetailsDTO dto = new Order_DetailsDTO(id, productID, quantity);
                    if (this.order_Details_List == null) {
                        this.order_Details_List = new ArrayList<>();
                    } //end if order_details list not existed
                    this.order_Details_List.add(dto);
                }//end if while traversing result
            }//end if con existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
