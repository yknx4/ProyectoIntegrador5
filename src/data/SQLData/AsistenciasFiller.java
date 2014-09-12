/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.SQLData;

import app.Utility;
import app.Utility.SQLHelper;
import data.BaseColumns;
import data.DataContract;
import data.DataContract.AsistenciaEntry;
import data.DataContract.ClaseEntry;
import data.SQLData.Getters.ListaClasesCursoDataWorker;
import data.SQLData.Parser.HorariosParse;
import database.DatabaseInstance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yknx
 */
public class AsistenciasFiller {
    private final int dia;
    private final Connection con;
    private static final String[] columnsAssist = {AsistenciaEntry.COLUMN_ID_CLASE,AsistenciaEntry.COLUMN_ID_USUARIO,AsistenciaEntry.COLUMN_FECHA};
    private static final String[] columnsClases = {DataContract.ListaClaseCursoEntry._ID,DataContract.ListaClaseCursoEntry.COLUMN_ID_MAESTRO, DataContract.ListaClaseCursoEntry.COLUMN_ID_HORARIO};
    
    
    private final ListaClasesCursoDataWorker getDataWorker;
    private final HorariosParse horas;
    
    
    public AsistenciasFiller(String db,int dia) throws SQLException, ClassNotFoundException, ParseException{
        this.dia = dia;
        con = DatabaseInstance.getInstance();
        getDataWorker = new ListaClasesCursoDataWorker(dia, columnsClases);
        horas = HorariosParse.with(db);
        getDataWorker.execute();
    }
    
    public void fill(){
        try {
            //System.out.println("INICIA LLENADO");
            final String finsert = Utility.SQLHelper.generateInsert(DataContract.AsistenciaEntry.TABLE_NAME, columnsAssist,Utility.SQLHelper.INSERT_MODE_IGNORE);
            PreparedStatement st ;
            ResultSet res = getDataWorker.get();
            while(res.next()){
                //CLASE,MAESTRO,FECHA
                st = con.prepareStatement(finsert);
                st.setLong(1, res.getLong(columnsClases[0]));
                st.setLong(2, res.getLong(columnsClases[1]));
                st.setString(3, horas.getFullDateString(res.getInt(columnsClases[2])));
                //System.out.println(st);
            }
         
        } catch (SQLException | InterruptedException | ExecutionException ex) {
            Logger.getLogger(AsistenciasFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
