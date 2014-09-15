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
import java.util.Calendar;
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
    private int dia;
    private int horario;
    
    private static String userPassQuery(){
        String query ="";
        final String[] selectColumns = {UsuarioEntry._ID,UsuarioEntry.COLUMN_PERMISSIONS};
        //final String[] selectColumns = {UsuarioEntry._ID};
        query += SQLHelper.generateSelect(DataContract.UsuarioEntry.TABLE_NAME, selectColumns);
        final String[] whereColumns = {UsuarioEntry.COLUMN_EMAIL,UsuarioEntry.COLUMN_PASSWORD_HASH};
        query+=SQLHelper.generateWhere(whereColumns, SQLHelper.WHERE_TYPE_EQUAL);
        return query;
    }
    private static String asistenciaQuery(){
        String query ="";
        query += SQLHelper.generateSelect(DataContract.AsistenciaDataViewEntry.TABLE_NAME, null);
        final String[] whereColumns = {DataContract.AsistenciaDataViewEntry.COLUMN_ID_MAESTRO,DataContract.AsistenciaDataViewEntry.COLUMN_DIA, DataContract.AsistenciaDataViewEntry.COLUMN_ID_HORARIO};
        final int[] types ={ SQLHelper.WHERE_TYPE_EQUAL,SQLHelper.WHERE_TYPE_EQUAL,SQLHelper.WHERE_TYPE_GREATER_OR_EQUAL};
        query+=SQLHelper.generateWhere(whereColumns, types);
        
        //query = query.replaceFirst("\\?", " ("+userPassQuery()+") ");
        
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
    public static AsistenciaController getInstance(int dia, int horario) throws SQLException{
        AsistenciaController instance = getInstance();
        instance.setDia(dia);
        instance.setHorario(horario);
        return instance;
        
    }
    
    public long getData(){
            return 0L;
    }

    /**
     * @return the dia
     */
    public int getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     * @return the horario
     */
    public int getHorario() {
        return horario;
    }

    /**
     * @param horario the horario to set
     */
    public void setHorario(int horario) {
        this.horario = horario;
    }

    public ResultSet getRawData() {
        return rawData;
    }

    
    
    private class UserMeta{
        public long id;
        public int perm;
        
        @Override
        public String toString(){
            return ""+id+" "+perm;
        }
        
    }
    
    private UserMeta getUser(String email, String password){
        UserMeta res = new UserMeta();
        res.id = Usuario.INVALID;
        res.perm = Usuario.INVALID_PERMISSION;
        try {
            PreparedStatement query = db.prepareStatement(userPassQuery());
            query.setString(1, email);
            String hash = (new HexBinaryAdapter()).marshal(getHash(salt+password));
            query.setString(2, hash.toLowerCase());
            System.out.println(query);
            ResultSet res2;
            res2 = query.executeQuery();
            if(res2.first()){
                res.id = res2.getLong(UsuarioEntry._ID);
                res.perm = res2.getInt(UsuarioEntry.COLUMN_PERMISSIONS);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    private String _error;
    private boolean setAsistencia(int permissionID,long usuario) throws SQLException{
        switch(permissionID){
            case Usuario.ADMINISTRATIVE_PERSONNEL:
                return setAsistenciaAdministrative(usuario);
            case Usuario.PROFESSOR:
                return setAsistenciaMaestro(usuario);
            default:
                return false;
        }
    }
    private boolean setAsistenciaAdministrative(long usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ResultSet rawData;
    private boolean setAsistenciaMaestro(long usuario) throws SQLException {
        ResultSet Data = getAsistenciaDataProfessor(usuario);
        String query;
        rawData = Data;
        String date;
        int chorario = horario;
        int grupo=0;
        while(rawData.next()){
            if(rawData.isFirst()){
                grupo = rawData.getInt(DataContract.AsistenciaDataViewEntry.COLUMN_ID_GRUPO);
            }else{
                if(grupo != rawData.getInt(DataContract.AsistenciaDataViewEntry.COLUMN_ID_GRUPO))
                    break;
            }
            int tHorario = rawData.getInt(DataContract.AsistenciaDataViewEntry.COLUMN_ID_HORARIO);
            if(tHorario == chorario){
                query="";
                date = Utility.getFormalDate(Utility.globalCalendar.get(Calendar.YEAR), Utility.globalCalendar.get(Calendar.MONTH), dia);
                final String[] columns = {DataContract.AsistenciaEntry.COLUMN_ID_CLASE,DataContract.AsistenciaEntry.COLUMN_ID_USUARIO,DataContract.AsistenciaEntry.COLUMN_ASISTIO, DataContract.AsistenciaEntry.COLUMN_FECHA};
                query+= SQLHelper.generateInsert(DataContract.AsistenciaEntry.TABLE_NAME, columns);
                PreparedStatement as = db.prepareStatement(query);
                as.setLong(1, rawData.getLong(DataContract.AsistenciaDataViewEntry._ID));
                as.setLong(2, rawData.getLong(DataContract.AsistenciaDataViewEntry.COLUMN_ID_MAESTRO));
                as.setInt(3, 1);
                as.setString(4, date);
                System.out.println(as);
                if(!as.execute()){
                    System.out.println("Asistencia añadida en "+date+" para el usuario: "+rawData.getString(DataContract.AsistenciaDataViewEntry.COLUMN_MAESTRO));
                }
                chorario++;
            }
        }
        
        return Data==null;
    }
    public boolean setAsistencia(String email, String password){
        UserMeta metaData = getUser(email,password);
        Integer[] permission = Usuario.getPermissions(metaData.perm);
        System.out.println("Usuario: "+metaData.toString());
        for(Integer x:permission){
            System.out.println(Usuario.permissionName(x));
            try {
                return setAsistencia(x,metaData.id);
            } 
            catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
                System.out.println("No puedes tomar asistencia dos veces para la misma clase.");
            }
            catch (SQLException ex) {
                Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return false;
    }
    
    private ResultSet getAsistenciaDataProfessor(long id){
        ResultSet res =null;
        try{
            
            PreparedStatement query = db.prepareStatement(asistenciaQuery());
            query.setLong(1, id);
            query.setInt(2, dia);
            query.setLong(3, horario);
            System.out.println(query);
            res = query.executeQuery();
            /*while(res.next()){
                System.out.println("Row: "+res.getRow());
            }*/
            
        } catch (SQLException ex) {
            Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    
    
    
    
}
