/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.database;

import helper.Utility;
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
    private final static Logger LOGGER = Logger.getLogger(DatabaseInstance.class.getName());
    private final Connection instance;
    private final Connection transactionInstance;
    private static DatabaseInstance holder;

    public DatabaseInstance() throws SQLException, ClassNotFoundException {
         Class.forName("com.mysql.jdbc.Driver");

                // setup the connection with the DB.
                instance      = DriverManager.getConnection(Utility.DB_STRING);
                transactionInstance      = DriverManager.getConnection(Utility.DB_STRING);
                transactionInstance.setAutoCommit(false);
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
    public static Connection getTransactedInstance() throws SQLException{
        try {
            if(holder == null) holder = new DatabaseInstance();
            return holder.transactionInstance;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseInstance.class.getName()).log(Level.SEVERE, null, ex);
        }
      throw new SQLException("SQL Connection cannot be initiated.");
    }
    
}
