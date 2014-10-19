/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.SQLData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;
import model.Clase;
import model.database.DataContract;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class ClasesController {

    public ClasesController() throws SQLException {
        
    }
    static Connection db;
    
    public static boolean isValid(Clase input){
       
            if(getClase(input)==null){
                return tryInsert(input);
            }
        return false;
    }
    
    public static boolean insert(Clase input) throws SQLException{
        boolean result;
        
            db = DatabaseInstance.getInstance(); 
            Statement query = db.createStatement();
            String queryString = input.print();
            result= query.executeUpdate(queryString) != 0;
        
        
        return result;
    }
    
    private static boolean tryInsert(Clase input){
        boolean result = false;
        Connection tdb = null; 
        try {
            tdb = DatabaseInstance.getTransactedInstance();
            Statement query = tdb.createStatement();
            String queryString = input.print();
            result= query.executeUpdate(queryString) != 0;
        } catch (SQLException ex) {
            Logger.getLogger(ClasesController.class.getName()).log(Level.SEVERE, null, ex);
            
        }finally{
            try {
                if(tdb!=null)tdb.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ClasesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    private static Clase getClase(Clase input){
        try {
            db = DatabaseInstance.getInstance();
            Statement query = db.createStatement();
            String queryString = SQLHelper.generateSelect(DataContract.ClaseEntry.TABLE_NAME, null);
            queryString += input.selectValues();
            ResultSet res = query.executeQuery(queryString);
            if(res.first()){
                input.setId(res.getInt(DataContract.ClaseEntry._ID));
                return input;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClasesController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
        
        
        
    }
}
