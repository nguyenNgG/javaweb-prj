/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nguyenng.order;

import java.io.Serializable;

/**
 *
 * @author bchao
 */
public class OrderDTO implements Serializable{
    private String orderID;
    private String custName;
    private String custAddress;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, String custName, String custAddress) {
        this.orderID = orderID;
        this.custName = custName;
        this.custAddress = custAddress;
    }

    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the custAddress
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     * @param custAddress the custAddress to set
     */
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
    
    
}
