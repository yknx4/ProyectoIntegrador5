/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.SQLData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Grupo;
import model.database.DataContract;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class GrupoController {

    public GrupoController(int type, String extra) throws SQLException {
        this.db = DatabaseInstance.getInstance();
        this.type = type;
        this.extra = extra;
        setTableName();
    }
    public GrupoController(int type) throws SQLException {
        this.db = DatabaseInstance.getInstance();
        this.type = type;
        this.extra = null;
        setTableName();
    }
    
    private void setTableName(){
        if(type == TYPE_RAW){
            tableName = DataContract.GrupoEntry.TABLE_NAME;
        }else{
            tableName = DataContract.GrupoEntry.HELPER_TABLE_NAME;
        }
    }
    private boolean hasExtra(){
        return extra != null && !extra.isEmpty();
    }
    public static int TYPE_HELPER = 1;
    public static int TYPE_RAW = 0;
    int type;
    String extra;
    String tableName;
    final Connection db;
    
    public List<Grupo> getGrupos(){
        List<Grupo> resultado = new ArrayList<>();
        try {
            ResultSet result;
            
            String query=SQLHelper.generateSelect(tableName, null);
            if(hasExtra())query +=" "+extra;
            result = db.createStatement().executeQuery(query);
            
            
            while(result.next()){
            
                Grupo mGrupo = getGrupo(result,type);
                if(mGrupo!=null)resultado.add(mGrupo);
                //System.out.println("Grupo: "+mGrupo.toString());
            }
            
        } catch (SQLException ex) {
             Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return resultado;
        
        
        
    }
    public static Grupo getGrupo(ResultSet raw,int type){
        Grupo n = null;
         try {
             
            int id;
            int semestre;
            String grupo;
            String full;
            
            id = raw.getInt(DataContract.GrupoEntry._ID);
            if(type == TYPE_HELPER){
                full = raw.getString("grupo");
                n = new Grupo(id, full);
            }
            else{
                semestre = raw.getInt(DataContract.GrupoEntry.COLUMN_SEMESTRE);
                grupo = raw.getString(DataContract.GrupoEntry.COLUMN_GRUPO);
                n = new Grupo(id, semestre, grupo);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
         return n;
    }
    
    
}
