/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.checkout;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import javax.naming.NamingException;
import nguyenng.utils.DBHelpers;

/**
 *
 * @author bchao
 */
public class CheckoutDAO implements Serializable {

    public boolean checkout(String orderID,
            String custName, String custAddress, int quantity, String productID)
            throws SQLException, NamingException {
        /*
            Order Table
            ID = orderID
            NAME = custName
            ADDRESS = custAddress
        
            --
            Order_Details Table
            ID = orderID
            Quantity = quantity
            PID = productID
        
            --
            Product Table
            PID = productID
        
         */

        Connection con = null;
        PreparedStatement stmOrder = null;
        PreparedStatement stmOrder_Details = null;

        try {
            //1.1 Create connection using method built
            con = DBHelpers.makeConnection();
            //1.2 Configure so that statement is 
            // committed on completion, not on execution 
            con.setAutoCommit(false);

            if (con != null) {
                //2. Create SQL String using DML
                String sqlOrder = "INSERT INTO "
                        + "[Order](ID, NAME, ADDRESS) "
                        + "VALUES(?, ?, ?)";
                String sqlOrder_Details = "INSERT INTO "
                        + "Order_Details(ID, Quantity, PID) "
                        + "VALUES(?, ?, ?)";
                //3. Create statement
                stmOrder = con.prepareStatement(sqlOrder);
                stmOrder.setString(1, orderID);
                stmOrder.setString(2, custName);
                stmOrder.setString(3, custAddress);

                stmOrder_Details = con.prepareStatement(sqlOrder_Details);
                stmOrder_Details.setString(1, orderID);
                stmOrder_Details.setInt(2, quantity);
                stmOrder_Details.setString(3, productID);

                //4. Execute Update
                int rs_order = stmOrder.executeUpdate();
                int rs_order_details = stmOrder_Details.executeUpdate();
                con.commit();

                //5. Get row affected
                if (rs_order > 0 && rs_order_details > 0) {
                    return true;
                }
            } // end if connection existed
        } catch (SQLException ex) {
            System.out.println("CheckoutDAO _ SQLException: " + ex.getMessage());
            if (con!=null) {
                try {
                    System.out.println("Checkout transaction will be rolled back.");
                    con.rollback();
                } catch (SQLException ex_rollback) {
                    System.out.println("CheckDAO _ SQLException: " + ex.getMessage());
                }
            }
        } finally {
            if (stmOrder_Details != null) {
                stmOrder_Details.close();
            }
            if (stmOrder != null) {
                stmOrder.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
}
