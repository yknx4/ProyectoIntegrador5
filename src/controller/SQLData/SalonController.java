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
import model.Salon;
import model.database.DataContract;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class SalonController {
    public SalonController(String extra) throws SQLException {
        this.db = DatabaseInstance.getInstance();
      
        this.extra = extra;
      
    }
    public SalonController() throws SQLException {
        this.db = DatabaseInstance.getInstance();
        
        this.extra = null;
        
    }
    
    
    private boolean hasExtra(){
        return extra != null && !extra.isEmpty();
    }
    
    String extra;
    String tableName = DataContract.SalonEntry.TABLE_NAME;
    final Connection db;
    
    public List<Salon> getSalones(){
        List<Salon> resultado = new ArrayList<>();
        try {
            ResultSet result;
            
            String query=SQLHelper.generateSelect(tableName, null);
            if(hasExtra())query +=" "+extra;
            result = db.createStatement().executeQuery(query);
            
            
            while(result.next()){
            
                Salon mSalon = getSalon(result);
                if(mSalon!=null)resultado.add(mSalon);
                //System.out.println("Grupo: "+mSalon.toString());
            }
            
        } catch (SQLException ex) {
             Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
            return resultado;
        
        
        
    }
    public static Salon getSalon(ResultSet raw){
       Salon n = null;
         try {
             
            int id;
            String nombre;
            
            id = raw.getInt(DataContract.SalonEntry._ID);
            
                nombre = raw.getString(DataContract.SalonEntry.COLUMN_NOMBRE);
            
            
                n = new Salon(id, nombre);
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
         return n;
    }
}

