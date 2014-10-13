/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.ClasesWeb;
import model.HorarioMaestro;
import model.database.DataContract;

/**
 *
 * @author Yknx
 */
public class ClasesWebController {

    public static ClasesWeb getFromResultSet(ResultSet data) throws SQLException {
        int id;
        String hora;
        String grupo;
        String materia;
        String salon;
        boolean asistio;
        boolean justifico;
        String fechaAsistio;
        String maestro;
        String fechaJustifico;
        int dia;
        int horario;
        int usuario;
        int min;
        dia = data.getInt(DataContract.ClasesWebEntry.COLUMN_DIA);
        horario = data.getInt(DataContract.ClasesWebEntry.COLUMN_HORARIO);
        usuario = data.getInt(DataContract.ClasesWebEntry.COLUMN_ID_USUARIO);
        id = data.getInt(DataContract.ClasesWebEntry._ID);
        min = data.getInt(DataContract.ClasesWebEntry.COLUMN_MINUTES);
        materia = data.getString(DataContract.ClasesWebEntry.COLUMN_MATERIA);
        salon = data.getString(DataContract.ClasesWebEntry.COLUMN_SALON);
        hora = data.getString(DataContract.ClasesWebEntry.COLUMN_HORA);
        maestro = data.getString(DataContract.ClasesWebEntry.COLUMN_MAESTRO);
        grupo = data.getString(DataContract.ClasesWebEntry.COLUMN_GRUPO);
        fechaAsistio = data.getString(DataContract.ClasesWebEntry.COLUMN_FECHA_ASISTIO);
        fechaJustifico = data.getString(DataContract.ClasesWebEntry.COLUMN_FECHA_JUSTIFICO);
        asistio = data.getInt(DataContract.ClasesWebEntry.COLUMN_ASISTIO) == 1;
        justifico = data.getInt(DataContract.ClasesWebEntry.COLUMN_JUSTIFICO) == 1;
        ClasesWeb mClaseWeb = new ClasesWeb(id, hora, grupo, materia, salon, asistio, justifico, fechaAsistio, fechaJustifico, dia, horario, usuario, min,maestro);
        return mClaseWeb;
    }
}
