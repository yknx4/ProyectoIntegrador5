/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.SQLData;

import helper.Utility;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import model.User;
import model.database.DataContract;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class UserController {
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private static Connection db;
    
    private static final String salt = Utility.PASSWORD_SALT;
    
    private static String userPassQuery(){
        final String[] selectColumns = {DataContract.UsuarioEntry._ID,DataContract.UsuarioEntry.COLUMN_PERMISSIONS};
        return userPassQuery(selectColumns);
    }
    private static String userPassQuery(String[] selectColumns){
        String query ="";
        query += SQLHelper.generateSelect(DataContract.UsuarioEntry.TABLE_NAME, selectColumns);
        final String[] whereColumns = {DataContract.UsuarioEntry.COLUMN_EMAIL,DataContract.UsuarioEntry.COLUMN_PASSWORD_HASH};
        query+=SQLHelper.generateWhere(whereColumns, SQLHelper.WHERE_TYPE_EQUAL);
        return query;
    }
    
    public static User getUser(ResultSet raw) throws Exception{
        Exception invalidError = new java.lang.IllegalArgumentException("Input ResultSet is malformed.");
        if(!raw.first()){
            invalidError.initCause(new Exception("Empty Dataset"));
            throw invalidError;
        }
        try {
            User n = new User();
            
            n.setId(raw.getInt(DataContract.UsuarioEntry._ID));
            n.setPermission(raw.getInt(DataContract.UsuarioEntry.COLUMN_PERMISSIONS));
            n.setNombre(raw.getString(DataContract.UsuarioEntry.COLUMN_NOMBRE));
            n.setPasshash(raw.getString(DataContract.UsuarioEntry.COLUMN_PASSWORD_HASH));
            n.setCreated(raw.getString(DataContract.UsuarioEntry.COLUMN_TIMESTAMP_CREATED));
            n.setModified(raw.getString(DataContract.UsuarioEntry.COLUMN_TIMESTAMP_MODIFIED));
            n.setEmail(raw.getString(DataContract.UsuarioEntry.COLUMN_EMAIL));
            n.setPicture(raw.getString(DataContract.UsuarioEntry.COLUMN_PICTURE_URI));
            
            int act = raw.getInt(DataContract.UsuarioEntry.COLUMN_ACTIVO);
            if(act==1) n.setActivo(true);
            else n.setActivo(false);
            
            
            return n;
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            invalidError.initCause(ex);
            throw invalidError;
        }
    }
    
    static public ResultSet getUser(String username, String password) throws Exception{
        ResultSet result=null;
        try {
            db = DatabaseInstance.getInstance();
            
            PreparedStatement query = db.prepareStatement(userPassQuery(null));
            query.setString(1, username);
            String hash = (new HexBinaryAdapter()).marshal(Utility.getHash(salt+password));
            query.setString(2, hash.toLowerCase());
            LOGGER.log(Level.FINE, "Query to execute: {0}", query.toString());
            System.out.println(query);
            
            result = query.executeQuery();
            if(!result.first()){
                result = null;
                LOGGER.log(Level.WARNING, "Couldn''t get data for user. {0}", username);
            }else{
                LOGGER.log(Level.INFO, "User data is: {0}", result.toString());
            }
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE,"Cannot get user data. Full error: "+ex.toString(),ex);
        }
            return result;
            
         
        
    }
}