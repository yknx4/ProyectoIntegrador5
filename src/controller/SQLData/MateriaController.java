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
import model.Materia;
import model.database.DataContract;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class MateriaController {
    public MateriaController(String extra) throws SQLException {
        this.db = DatabaseInstance.getInstance();
      
        this.extra = extra;
      
    }
    public MateriaController() throws SQLException {
        this.db = DatabaseInstance.getInstance();
        
        this.extra = null;
        
    }
    
    
    private boolean hasExtra(){
        return extra != null && !extra.isEmpty();
    }
    
    String extra;
    String tableName = DataContract.MateriaEntry.TABLE_NAME;
    final Connection db;
    
    public List<Materia> getMaterias(){
        List<Materia> resultado = new ArrayList<>();
        try {
            ResultSet result;
            
            String query=SQLHelper.generateSelect(tableName, null);
            if(hasExtra())query +=" "+extra;
            result = db.createStatement().executeQuery(query);
            
            
            while(result.next()){
            
                Materia mMateria = getMateria(result);
                if(mMateria!=null)resultado.add(mMateria);
                //System.out.println("Grupo: "+mMateria.toString());
            }
            
        } catch (SQLException ex) {
             Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return resultado;
        
        
        
    }
    public static Materia getMateria(ResultSet raw){
       Materia n = null;
         try {
             
            int id;
            String nombre;
            
            id = raw.getInt(DataContract.GrupoEntry._ID);
            
                nombre = raw.getString(DataContract.MateriaEntry.COLUMN_NOMBRE);
            
            
                n = new Materia(id, nombre);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
         return n;
    }
}
