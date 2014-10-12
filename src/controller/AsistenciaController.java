/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import helper.Utility;
import controller.SQLData.SQLHelper;
import controller.SQLData.UserController;
import model.database.DataContract;
import model.database.DataContract.UsuarioEntry;
import model.Usuario;
import model.database.DatabaseInstance;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Yknx
 */
public class AsistenciaController {
    private final static Logger LOGGER = Logger.getLogger(AsistenciaController.class.getName());
    private static AsistenciaController _instance;
    private static MessageDigest hasher ;
    private static final String salt = "ho";
    private final Connection db;
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
    private static String setAsistenciaAdministrativaQuery(){
        final String[] columns = {DataContract.CheckInEntry.COLUMN_ID_USUARIO,DataContract.CheckInEntry.COLUMN_FECHA};
        String query = SQLHelper.generateInsert(DataContract.CheckInEntry.TABLE_NAME, columns);
                
        return query;
    }
    private byte[] getHash(String input){
        byte[] finalhash = {};
        hasher.reset();
        try {
            hasher.update(input.getBytes("UTF-8"));
            finalhash = hasher.digest();
            LOGGER.log(Level.FINEST, "Hashed {0} into {1}", new Object[]{input, finalhash});
        } catch (UnsupportedEncodingException ex) {
            hasher.update(input.getBytes());
            LOGGER.log(Level.SEVERE,"Incorrect encoding, trying with default. Full error"+ex.toString(),ex);
        }
        return finalhash;
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
        LOGGER.config("db instance getted.");
        LOGGER.config("hasher instance getted with SHA-512 algorithm.");
    }
            
    public static AsistenciaController getInstance() throws SQLException{
        if(_instance == null) try {
            LOGGER.config("Instance not found, initializing.");
            _instance = new AsistenciaController();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, "Failed to initialize becase of {0}", ex.toString());
        }
        LOGGER.finer("Instance returned.");
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
        LOGGER.log(Level.CONFIG, "dia set to: {0}", dia);
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
        LOGGER.log(Level.CONFIG, "horario set to: {0}", horario);
        this.horario = horario;
    }

    public ResultSet getRawData() {
        return rawData;
    }

    private String getFormalDate() {
        return Utility.getFormalDate(Utility.globalCalendar.get(Calendar.YEAR), Utility.globalCalendar.get(Calendar.MONTH), dia);
    }

    public String getMessage() {
        if(_message == null || _message.isEmpty())
            return _errorMessage;
        return _message;
    }

    
    
    private class UserMeta{
        public long id;
        public int perm;
        
        @Override
        public String toString(){
            return "Id: "+id+" Permission:"+perm;
        }
        
    }
    
    private UserMeta getUser(String email, String password) throws Exception{
        LOGGER.log(Level.INFO, "Getting user data for {0} with password {1}", new Object[]{email, password.substring(password.length()/2)});
        UserMeta res = new UserMeta();
        res.id = Usuario.INVALID;
        res.perm = Usuario.INVALID_PERMISSION;
        try {
           /* PreparedStatement query = db.prepareStatement(userPassQuery());
            query.setString(1, email);
            String hash = (new HexBinaryAdapter()).marshal(getHash(salt+password));
            query.setString(2, hash.toLowerCase());
            LOGGER.log(Level.FINE, "Query to execute: {0}", query.toString());
            //System.out.println(query);*/
            ResultSet res2;
            //res2 = query.executeQuery();
            res2 = UserController.getUser(email, password);
            /*if(res2.first()){
                res.id = res2.getLong(UsuarioEntry._ID);
                res.perm = res2.getInt(UsuarioEntry.COLUMN_PERMISSIONS);
                LOGGER.log(Level.INFO, "User data is: {0}", res.toString());
            }else{
                LOGGER.warning("Couldn't get data for this user.");
                _message = "No se pudo añadir asistencia al usuario "+email;
                _errorMessage = "Usuario o contraseña incorrectos.";
                _message+=" : "+_errorMessage;
                throw new Exception(_errorMessage);
            }*/
            if(res2!=null){
                res.id = res2.getLong(UsuarioEntry._ID);
                res.perm = res2.getInt(UsuarioEntry.COLUMN_PERMISSIONS);
            }else{
                _message = "No se pudo añadir asistencia al usuario "+email;
                _errorMessage = "Usuario o contraseña incorrectos.";
                _message+=" : "+_errorMessage;
                throw new Exception(_errorMessage);
            }
            return res;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE,"Cannot get user data. Full error: "+ex.toString(),ex);
        }
        return null;
    }
    private String _error;
    private void setAsistencia(int permissionID,long usuario) throws SQLException, Exception{
        LOGGER.log(Level.INFO, "Adding assistance to user {0} and permission {1}", new Object[]{usuario, Usuario.permissionName(permissionID)});
        switch(permissionID){
            case Usuario.ADMINISTRATIVE_PERSONNEL:
                setAsistenciaAdministrative(usuario);
            case Usuario.PROFESSOR:
                setAsistenciaMaestro(usuario);
            default:
                //Exception e = new Exception(permissionID+" assist not implemented.");
                //throw e;
        }
    }
    private void setAsistenciaAdministrative(long usuario) {
        try {
            String fecha = getFormalDate();
            PreparedStatement as = db.prepareStatement(setAsistenciaAdministrativaQuery());
            as.setLong(1, usuario);
            as.setString(2, fecha);
            System.out.println(as);
           
            if(!as.execute() && as.getUpdateCount()>0){
                LOGGER.log(Level.INFO, "Asistencia a\u00f1adida en {0} para el usuario: {1}", new Object[]{fecha, usuario});
            }
            else{
                LOGGER.log(Level.WARNING, "No se pudo a\u00f1adir la asistencia para el usuario {0} en {1}", new Object[]{usuario, fecha});
                SQLException e = new SQLException("No se pudo a\u00f1adir la asistencia para el usuario "+usuario);
                throw e;
            }
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE,ex.toString(),ex);
        }
        
    }
    private String _message;
    private String _errorMessage="";
    ResultSet rawData;
    private void setAsistenciaMaestro(long usuario) throws SQLException {
        boolean resultado = false;
        LOGGER.log(Level.INFO, "Adding asistencia to teacher with id: {0}", usuario);
        ResultSet Data = getAsistenciaDataProfessor(usuario);
        rawData = Data;
        if(rawData == null || !rawData.first()) {
            LOGGER.log(Level.SEVERE, "Couldn''t get data for professor with id: {0}", usuario);
            NullPointerException e;
            _errorMessage = "No hay clases disponibles para el usuario.";
            e = new NullPointerException(_errorMessage);
            //NullPointerException e = new Exception("No se pudo obtener la información del profesor con id: "+usuario);
            throw e;
        }
        String query;
        
        String date;
        
        int chorario = horario;
        int grupo=0;
        rawData.beforeFirst();
        while(rawData.next()){
            
            if(rawData.isFirst()){
                grupo = rawData.getInt(DataContract.AsistenciaDataViewEntry.COLUMN_ID_GRUPO);
                
            }else{
                if(grupo != rawData.getInt(DataContract.AsistenciaDataViewEntry.COLUMN_ID_GRUPO)){
                    LOGGER.log(Level.FINER, "Grupo distinto.");
                    break;
                }
                    
            }
            int tHorario = rawData.getInt(DataContract.AsistenciaDataViewEntry.COLUMN_ID_HORARIO);
            if(tHorario == chorario){
                
                query="";
                date = getFormalDate();
                final String[] columns = {DataContract.AsistenciaEntry.COLUMN_ID_CLASE,DataContract.AsistenciaEntry.COLUMN_ID_USUARIO,DataContract.AsistenciaEntry.COLUMN_ASISTIO, DataContract.AsistenciaEntry.COLUMN_FECHA};
                query+= SQLHelper.generateInsert(DataContract.AsistenciaEntry.TABLE_NAME, columns);
                LOGGER.log(Level.INFO, "A\u00f1adiendo asistencia para profesor {0} en horario {1} en {2}", new Object[]{usuario, horario, date});
                PreparedStatement as = db.prepareStatement(query);
                as.setLong(1, rawData.getLong(DataContract.AsistenciaDataViewEntry._ID));
                as.setLong(2, rawData.getLong(DataContract.AsistenciaDataViewEntry.COLUMN_ID_MAESTRO));
                as.setInt(3, 1);
                as.setString(4, date);
                System.out.println(as);
                if(!as.execute() && as.getUpdateCount()>0){
                    LOGGER.log(Level.INFO, "Asistencia a\u00f1adida para el maestro: {0}", rawData.getString(DataContract.AsistenciaDataViewEntry.COLUMN_MAESTRO));
                }
                else{
                    LOGGER.log(Level.SEVERE, "No se pudo a\u00f1adir asistencia al maestro {0}", rawData.getString(DataContract.AsistenciaDataViewEntry.COLUMN_MAESTRO));
                }
                chorario++;
            }
        }
        
        
    }
    public void setAsistencia(String email, String password) throws Exception{
        _message ="";
        _error = "";
        _errorMessage ="";
        UserMeta metaData = getUser(email,password);
        Integer[] permission = Usuario.getPermissions(metaData.perm);
        
        LOGGER.log(Level.INFO, "A\u00f1adiendo asistencia al usuario {0} ({1}).", new Object[]{email, metaData.id});
        for(Integer x:permission){
            try {
                 setAsistencia(x,metaData.id);
                 _message = "Asistencia añadida a "+email;
            } 
            catch(com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e){
                String error = "No puedes tomar asistencia dos veces para la misma clase.";
                LOGGER.warning(error);
                LOGGER.log(Level.SEVERE,e.toString(),e);
                _errorMessage = error;
                throw new Exception(error);
            }
            catch (SQLException ex) {
                LOGGER.log(Level.SEVERE,ex.toString(),ex);
            } catch (Exception ex) {
                    _message = "No se pudo anadir asistencia a "+email+": "+_errorMessage;
                Logger.getLogger(AsistenciaController.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(_message);
                        
            }
            
        }
        
    }
    
    private ResultSet getAsistenciaDataProfessor(long id){
        LOGGER.log(Level.INFO, "Getting info for usuario with id: {0}", id);
        ResultSet res =null;
        try{
            PreparedStatement query = db.prepareStatement(asistenciaQuery());
            query.setLong(1, id);
            query.setInt(2, dia);
            query.setLong(3, horario);
            LOGGER.log(Level.FINE, "Query to execute: {0}", query.toString());
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
