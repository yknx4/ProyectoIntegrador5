/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.HorarioMaestro;
import model.database.DataContract;

/**
 *
 * @author Yknx
 */
public class HorarioMaestroController {

    public static HorarioMaestro getFromResultSet(ResultSet data) throws SQLException {
        int dia;
        int horario;
        String materia;
        String salon;
        String hora;
        String grupo;
        dia = data.getInt(DataContract.HorarioMaestroEntry.COLUMN_DIA);
        horario = data.getInt(DataContract.HorarioMaestroEntry.COLUMN_HORARIO);
        materia = data.getString(DataContract.HorarioMaestroEntry.COLUMN_MATERIA);
        grupo = data.getString(DataContract.HorarioMaestroEntry.COLUMN_GRUPO);
        salon = data.getString(DataContract.HorarioMaestroEntry.COLUMN_SALON);
        hora = data.getString(DataContract.HorarioMaestroEntry.COLUMN_HORA);
        HorarioMaestro mMaestro = new HorarioMaestro(dia, horario, materia, salon, false,hora,grupo);
        return mMaestro;
    }
    
}
