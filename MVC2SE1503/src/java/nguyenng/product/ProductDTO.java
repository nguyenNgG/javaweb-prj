/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nguyenng.product;

import java.io.Serializable;

/**
 *
 * @author bchao
 */
public class ProductDTO implements Serializable{
    private String productID;
    private String productName;

    public ProductDTO() {
    }

    public ProductDTO(String productID, String productName) {
        this.productID = productID;
        this.productName = productName;
    }

    /**
     * @return the productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID the productID to set
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

}
