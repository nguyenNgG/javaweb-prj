/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nguyenng.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bchao
 */
public class DBHelpers implements Serializable {

    //DAO dung phuong thuc nay
    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        //1. Load driver -> Add driver into Project
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //2. Tạo chuoi~ ket noi Database (connection string) to determine Container address
        String url = "jdbc:sqlserver://localhost:1433;databaseName=COMPANY";
        //3. Open connection
        Connection con = DriverManager.getConnection(url, "sa", "123");

        return con;
    }

}
