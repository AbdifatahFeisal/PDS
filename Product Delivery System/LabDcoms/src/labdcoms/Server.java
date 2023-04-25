/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labdcoms;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tahmi
 */
public class Server extends UnicastRemoteObject implements LoginInterface, SignUpInterface, ICInterface, StoreDataInterface, ViewProductsInterface{
    public Server() throws RemoteException {
    }
    
    public String[] authenticate(String loginUsername, String loginPassword) throws RemoteException, SQLException{
        boolean authenticated = false;
        String[] userData = new String[4];
        System.out.println("works here");
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        System.out.println("Authenticating...");
        Statement stmt = con.createStatement();
        String sqlCommand = "select * from userinfo";
        ResultSet loginData = stmt.executeQuery(sqlCommand);
        
        while(loginData.next()){
            String username = loginData.getString("username");
            String password = loginData.getString("password");
            if(loginUsername.equals(username) && loginPassword.equals(password)) {
                userData[0] = username;
                userData[1] = loginData.getString("firstname");
                userData[2] = loginData.getString("lastname");
                userData[3] = loginData.getString("ic");
                authenticated = true;
                break;
            }
        }
        con.close();
        if(authenticated == true) {
           return userData;
        }
        else{
           return null;
        }
    }
    public boolean checkUsername(String signUpUsername) throws RemoteException, SQLException{
        boolean usernameExists = false;
        
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        System.out.println("Authenticating...");
        Statement stmt = con.createStatement();
        String sqlCommand = "select * from userinfo";
        ResultSet loginData = stmt.executeQuery(sqlCommand);
        
        while(loginData.next()){
            String username = loginData.getString("username");
            if(signUpUsername.equals(username)){
                usernameExists = true;
                break;
            }
        }
        con.close();
        return usernameExists;
    }
    public boolean checkIC(String signUpIC) throws RemoteException, SQLException{
        boolean icExists = false;
        
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        System.out.println("Authenticating...");
        Statement stmt = con.createStatement();
        String sqlCommand = "select * from userinfo";
        ResultSet loginData = stmt.executeQuery(sqlCommand);
        
        while(loginData.next()){
            String ic = loginData.getString("ic");
            if(signUpIC.equals(ic)){
                icExists = true;
                break;
            }
        }
        con.close();
        return icExists;
    }
    public boolean storeData(String username, String password, String firstName, String lastName, String ic) throws RemoteException, SQLException{
        boolean dataStored = false;
        
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        //con.setAutoCommit(false);
        System.out.println("Connection Created");
        PreparedStatement ps = conn.prepareStatement("insert into userinfo (username, password, firstname, lastname, ic) values(?,?,?,?,?)");
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, firstName);
        ps.setString(4, lastName);
        ps.setString(5, ic);
        //con.commit();
        ps.executeUpdate();
        conn.close();
        dataStored = true;
        System.out.println("Successfully inserted into database");
        return dataStored;
    }
    
    public String[][] getProducts() throws RemoteException, SQLException {
        int i = 0, size = 0;
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        System.out.println("Authenticating...");
        Statement stmtL = con.createStatement();
        Statement stmt = con.createStatement();
        String sqlCommand = "select * from products";
        String getLength = "select count(*) from products";
        ResultSet productsLength = stmtL.executeQuery(getLength);
        ResultSet productsData = stmt.executeQuery(sqlCommand);
        
        // Get data length
        productsLength.next();
        size = productsLength.getInt(1);
        String[][] products = new String[size][5];
        while(productsData.next()){
            products[i][0] = productsData.getString("id");
            products[i][1] = productsData.getString("name");
            products[i][2] = productsData.getString("price");
            products[i][3] = productsData.getString("review");
            products[i][4] = productsData.getString("quantity");
            i++;
        }
        con.close();
        return products;
    }
}
