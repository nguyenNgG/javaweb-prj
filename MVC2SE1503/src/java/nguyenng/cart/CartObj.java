package nguyenng.cart;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bchao
 */
public class CartObj implements Serializable {

    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addItemToCart(String title) {
        //1. Check if cart existed
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        //2. Check if item already exist
        int quantity = 1;
        if (this.items.containsKey(title)) {
            quantity = this.items.get(title) + 1;
        }
        //3. Update cart
        this.items.put(title, quantity);

    }

    public void removeItemFromCart(String title) {
        //1. Check if cart existed
        if (this.items == null) {
            return;
        }
        //2. Check if item exist in cart
        if (this.items.containsKey(title)) {
            this.items.remove(title);
            //3. Check if there are any item left in cart
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
        
        
    }
}
