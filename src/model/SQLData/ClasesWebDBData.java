/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.SQLData;

import controller.ClasesWebController;
import controller.SQLData.SQLHelper;
import helper.Utility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ClasesWeb;
import model.database.DataContract;
import model.database.DataContract.ClasesWebEntry;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class ClasesWebDBData implements DBData<ClasesWeb>{
    
    public ClasesWebDBData(int maestro) throws SQLException {
        this.db = DatabaseInstance.getInstance();
        this.maestro = maestro;
        dia = -1;
        horario = -1;
        RawData = null;
    }
    public ClasesWebDBData() throws SQLException {
        this.db = DatabaseInstance.getInstance();
        this.maestro = -1;
        dia = -1;
        horario = -1;
        RawData = null;
    }
    final int maestro;
    final Connection db;
    ResultSet RawData;
    String sqlQuery;
    private int dia;
    private int horario;
    boolean allHorarios=false;
    boolean allDias= false;
    private boolean limit = false;
    boolean thisWeek;
    boolean dateSpecific;
    private Date dateQuery = null;

    public boolean isDateSpecific() {
        if(dateQuery == null) return false;
        return dateSpecific;
    }

    public void setDateSpecific(boolean dateSpecific) {
        this.dateSpecific = dateSpecific;
    }

    public boolean isThisWeek() {
        return thisWeek;
    }

    public void setThisWeek(boolean thisWeek) {
        this.thisWeek = thisWeek;
    }

    public void setAllHorarios(boolean allHorarios) {
        horario = Integer.MAX_VALUE;
        this.allHorarios = allHorarios;
    }

    public void setAllDias(boolean allDias) {
        if(allDias)dia = Integer.MAX_VALUE;
        else{
            dia = Utility.getDate();
        }
        this.allDias = allDias;
    }

    @Override
    public List<ClasesWeb> getData() {
        if(dia==-1 || horario ==-1) return null;
        init();
        List<ClasesWeb> result = new ArrayList<>();
        try {
            RawData.beforeFirst();
            while(RawData.next()){
                result.add(ClasesWebController.getFromResultSet(RawData));
            }   
        } catch (SQLException ex) {
            Logger.getLogger(ClasesWebDBData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }

    private String getTableName(){
        if(isThisWeek()){
            return DataContract.ClasesWebEntry.TABLE_NAME+2;
        }
        else return DataContract.ClasesWebEntry.TABLE_NAME;
    }
    private void init() {
        try {
            sqlQuery = controller.SQLData.SQLHelper.generateSelect(getTableName(), null);
            ArrayList<String> columns = new ArrayList<>();
            if(!allHorarios) columns.add(ClasesWebEntry.COLUMN_HORARIO);
            if(!allDias)  columns.add(ClasesWebEntry.COLUMN_DIA);
            if(maestro!=-1)  columns.add(ClasesWebEntry.COLUMN_ID_USUARIO);
            String dateParam = null;
            if(isDateSpecific()){
                dateParam = "DATE("+ClasesWebEntry.COLUMN_FECHA_ASISTIO+")";
                columns.add(dateParam);
            }
            System.out.println("Horarios: "+allHorarios);
            System.out.println("Dias: "+allDias);
            System.out.println("Maestro: "+maestro);
            
            String[] use = columns.toArray(new String[columns.size()]);
            if(columns.size()>0)sqlQuery += SQLHelper.generateWhere(use, SQLHelper.WHERE_TYPE_EQUAL);
            if(limit)sqlQuery+=" LIMIT 300";
            PreparedStatement query = db.prepareStatement(sqlQuery);
            System.out.println("ClasesDBbalba "+query.toString());
            
            if(!allHorarios)query.setInt(columns.indexOf(ClasesWebEntry.COLUMN_HORARIO)+1, horario);
            if(!allDias)query.setInt(columns.indexOf(ClasesWebEntry.COLUMN_DIA)+1, dia);
            if(maestro!=-1)query.setInt(columns.indexOf(ClasesWebEntry.COLUMN_ID_USUARIO)+1, maestro);
            if(isDateSpecific()) query.setString(columns.indexOf(dateParam)+1, Utility.MySQLDateFormatter.format(dateQuery));
            RawData = query.executeQuery();
            System.out.println(query.toString());
        } catch (SQLException ex) {
            Logger.getLogger(ClasesWebDBData.class.getName()).log(Level.SEVERE, null, ex);
            RawData = null;
        }
        
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public void setLimit(boolean limit) {
        this.limit = limit;
    }

    public Date getDateQuery() {
        return dateQuery;
    }

    public void setDateQuery(Date dateQuery) {
        this.dateQuery = dateQuery;
    }
    
}
