/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.order_details;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import nguyenng.utils.DBHelpers;

/**
 *
 * @author bchao
 */
public class Order_DetailsDAO implements Serializable {

    public boolean addOrderDetails(String orderID, String productID, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "INSERT INTO Order_Details"
                        + "(ID, Quantity, PID) "
                        + "VALUES(?, ?, ?)";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setInt(2, quantity);
                stm.setString(3, productID);
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
}
