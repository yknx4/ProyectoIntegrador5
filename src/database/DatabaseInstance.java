/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import app.Utility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Yknx
 */
public class DatabaseInstance {
    private final Connection instance;
    private static DatabaseInstance holder;

    public DatabaseInstance() throws SQLException, ClassNotFoundException {
         Class.forName("com.mysql.jdbc.Driver");

                // setup the connection with the DB.
                instance      = DriverManager.getConnection(Utility.DB_STRING);
    }
    
    public static Connection getInstance() throws SQLException{
        try {
            if(holder == null) holder = new DatabaseInstance();
            return holder.instance;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
      throw new SQLException("SQL Connection cannot be initiated.");
    }
    
}