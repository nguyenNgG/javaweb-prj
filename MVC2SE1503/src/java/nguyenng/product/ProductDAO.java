/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.product;

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
public class ProductDAO implements Serializable {

    List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return this.productList;
    }

    public void viewBookstore() throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. Connect DB using method built
            con = DBHelpers.makeConnection();
            if (con != null) {
                //2. Create SQL String using DML
                String sql = "SELECT PID, NAME "
                        + "FROM Product ";
                //3. Create Statement
                stm = con.prepareStatement(sql);
                //4. Execute Query and get ResultSet
                rs = stm.executeQuery();
                //5. Process ResultSet
                while (rs.next()) {
                    String pid = rs.getString("PID");
                    String name = rs.getString("NAME");
                    ProductDTO dto = new ProductDTO(pid, name);
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    } // end if productlist not existed
                    this.productList.add(dto);
                }
            } //end if con existed
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

    public String getProductIDFromName(String productName) throws SQLException, NamingException {
        String productID = "";
        if (this.productList == null) {
            viewBookstore();
        }
        for (ProductDTO dto : this.productList) {
            if (dto.getProductName().equals(productName)) {
                productID = dto.getProductID();
            }
        }
        return productID;
    }
}
