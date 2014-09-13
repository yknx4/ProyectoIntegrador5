/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import app.Utility;
import app.Utility.SQLHelper;
import data.DataContract;
import data.DataContract.UsuarioEntry;
import data.Usuario;
import database.DatabaseInstance;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 *
 * @author Yknx
 */
public class AsistenciaController {
    private static AsistenciaController _instance;
    private static MessageDigest hasher ;
    private static final String salt = "ho";
    private Connection db;
    
    private static String userPassQuery(){
        String query ="";
        final String[] selectColumns = {UsuarioEntry._ID,UsuarioEntry.COLUMN_PERMISSIONS};
        query += SQLHelper.generateSelect(DataContract.UsuarioEntry.TABLE_NAME, selectColumns);
        final String[] whereColumns = {UsuarioEntry.COLUMN_EMAIL,UsuarioEntry.COLUMN_PASSWORD_HASH};
        query+=SQLHelper.generateWhere(whereColumns, SQLHelper.WHERE_TYPE_EQUAL);
        return query;
    }
    private static String asistenciaQuery(){
        String query ="";
        query += SQLHelper.generateSelect(DataContract.ListaClaseCursoEntry.TABLE_NAME, null);
        final String[] whereColumns = {DataContract.ListaClaseCursoEntry.COLUMN_ID_MAESTRO, DataContract.ListaClaseCursoEntry.COLUMN_DIA, DataContract.ListaClaseCursoEntry.COLUMN_ID_HORARIO};
        query+=SQLHelper.generateWhere(whereColumns, SQLHelper.WHERE_TYPE_EQUAL);
        query = query.replaceFirst("\\?", userPassQuery());
        return query;
    }
    private byte[] getHash(String input){
        hasher.reset();
        try {
            hasher.update(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            hasher.update(input.getBytes());
            Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hasher.digest();
    }
    /*
    b0 : Administrative Personnel
    b1 : Professor
    b2 : System Administrator
    b3 : EXTRA_FLAG
    b4 => b7 : UNUSED
    
    */
    
    private AsistenciaController() throws SQLException, NoSuchAlgorithmException{
        db = DatabaseInstance.getInstance();
        hasher = MessageDigest.getInstance("SHA-512");
    }
            
    public static AsistenciaController getInstance() throws SQLException{
        if(_instance == null) try {
            _instance = new AsistenciaController();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return _instance;
    }
    
    public long getData(){
            return 0L;
    }
    
    public long getUser(String email, String password){
        long id = Usuario.INVALID;
        try {
            PreparedStatement query = db.prepareStatement(userPassQuery());
            query.setString(1, email);
            String hash = (new HexBinaryAdapter()).marshal(getHash(salt+password));
            query.setString(2, hash.toLowerCase());
            System.out.println(query);
            ResultSet res = query.executeQuery();
            if(res.first()){
                id = res.getLong(UsuarioEntry._ID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    public ResultSet setAsistencia(String email, String password, int dia, int horario){
        ResultSet res =null;
        try{
            PreparedStatement query = db.prepareStatement(asistenciaQuery());
            query.setString(1, email);
            String hash = (new HexBinaryAdapter()).marshal(getHash(salt+password));
            query.setString(2, hash.toLowerCase());
            query.setInt(3, dia);
            query.setLong(4, horario);
            System.out.println(query);
           // res = query.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    
    
    
    
}
