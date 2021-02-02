/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nguyenng.utils.DBHelpers;

/**
 *
 * @author bchao
 */
public class RegistrationDAO implements Serializable {

    public boolean checkLogin(String username, String password) throws 
            SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Select Username "
                        + "From tblEmployees "
                        + "Where Username = ? And Password = ?";
                //3. create statement and assign Parameter if any
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process ResultSet
                if (rs.next()) {
                    return true; //check only, no data returned
                }

            } //end if con is connected

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

    private List<RegistrationDTO> accountList;

    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }

    public void searchLastName(String searchValue) throws SQLException, 
            ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        //1. Connect DB
        try {
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String
                String sql = "SELECT Username, Password, LastName, isAdmin "
                        + "From tblEmployee "
                        + "Where lastname LIKE ?";
                //3. Create Statement & Assign value to parameter
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result
                while (rs.next()) {
                    String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    String fullName = rs.getString("LastName");
                    boolean role = rs.getBoolean("isAdmin");
                    RegistrationDTO dto = new RegistrationDTO(
                            username, password, fullName, role);
                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    } //end if list account is not existed
                    this.accountList.add(dto);
                } //end while traversing result
            } //end if con is opened
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
