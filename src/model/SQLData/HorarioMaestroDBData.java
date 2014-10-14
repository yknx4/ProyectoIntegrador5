/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.SQLData;

import java.sql.PreparedStatement;
import com.mysql.jdbc.Util;
import controller.HorarioMaestroController;
import controller.SQLData.Parser.HorariosParse;
import controller.SQLData.SQLHelper;
import helper.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.HorarioMaestro;
import model.database.DataContract;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class HorarioMaestroDBData implements DBData<List<HorarioMaestro>> {

    public HorarioMaestroDBData(int maestro) throws SQLException {
        this.db = DatabaseInstance.getInstance();
        this.maestro = maestro;
        RawData = init();
    }
    final int maestro;
    final Connection db;
    final ResultSet RawData;
    String sqlQuery;

    @Override
    public List<List<HorarioMaestro>> getData() {
        List<List<HorarioMaestro>> result = new ArrayList<>();
        List<HorarioMaestro> current;
        HorarioMaestro m,tmp;

        try {
            for (int i = 0; i < HorariosParse.getInstance().count(); i++) {
                current = new ArrayList<>();
                
                for (int j = 0; j < 5; j++) {
                    m=null;
                    RawData.beforeFirst();
                    while (RawData.next()) {
                        tmp = HorarioMaestroController.getFromResultSet(RawData);
                        if(tmp.getHorario()==i && tmp.getDia()==j){
                            m=tmp;
                            RawData.afterLast();
                        }
                    }
                    if(m==null){
                        m = new HorarioMaestro(j, i, "", "", true,"","");
                    }
                    current.add(m);
                }
                result.add(current);
            }

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(HorarioMaestroDBData.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private ResultSet init() throws SQLException {
        sqlQuery = controller.SQLData.SQLHelper.generateSelect(DataContract.HorarioMaestroEntry.TABLE_NAME, null);
        sqlQuery += SQLHelper.generateWhere(DataContract.HorarioMaestroEntry.COLUMN_ID_MAESTRO, SQLHelper.WHERE_TYPE_EQUAL);
        PreparedStatement query = db.prepareStatement(sqlQuery);
        query.setInt(1, maestro);
        return query.executeQuery();
    }
}
