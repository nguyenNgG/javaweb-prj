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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author bchao
 */
public class DBHelpers implements Serializable {

    //DAO dung phuong thuc nay
    public static Connection makeConnection() throws /*ClassNotFoundException*/ SQLException, NamingException {
        
        Context currentContext = new InitialContext();
        Context tomcatContext = (Context)currentContext.lookup("java:comp/env");
        DataSource ds = (DataSource)tomcatContext.lookup("DSLOC");
        Connection con = ds.getConnection();
        
        return con;
//        //1. Load driver -> Add driver into Project
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2. Tạo chuoi~ ket noi Database (connection string) to determine Container address
//        String url = "jdbc:sqlserver://localhost:1433;databaseName=COMPANY";
//        //3. Open connection
//        Connection con = DriverManager.getConnection(url, "sa", "123");
//        javax.sql.DataSource
//
//        return con;
    }

}
