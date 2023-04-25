/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labdcoms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author tahmi
 */
public class ab{
    public ab () throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/dcomstut","tahmid","tahmid");
        //con.setAutoCommit(false);
        System.out.println("Connection Created");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("insert into demo values('lannie','aloha')");
        //con.commit();
        conn.close();
        System.out.println("Successfully inserted into database");
    }
    
}
