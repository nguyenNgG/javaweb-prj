/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import nguyenng.utils.DBHelpers;

/**
 *
 * @author bchao
 */
public class OrderDAO implements Serializable {

    List<OrderDTO> orderList;

    public List<OrderDTO> getOrderList() {
        return this.orderList;
    }

    public boolean addOrder(String orderID, String custName, String custAddress) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO "
                        + "[Order](ID, NAME, ADDRESS) "
                        + "VALUES(?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, custName);
                stm.setString(3, custAddress);
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

    public boolean isOrderIDTaken(String orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT ID "
                        + "FROM [Order] "
                        + "WHERE ID LIKE ?";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    return true;
                }//end if result existed
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
        return false;
    }

    public boolean deleteOrder(String orderID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "DELETE FROM [Order] "
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

    public void viewOrderList() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT ID, NAME, ADDRESS "
                        + "FROM [Order]";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String id = rs.getString("ID");
                    String custName = rs.getString("NAME");
                    String custAddress = rs.getString("ADDRESS");
                    OrderDTO dto = new OrderDTO(id, custName, custAddress);
                    if (this.orderList == null) {
                        this.orderList = new ArrayList<>();
                    } // end if order list not existed
                    this.orderList.add(dto);
                }//end while traversing result
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
