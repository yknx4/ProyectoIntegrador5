
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package data.SQLData;

//~--- non-JDK imports --------------------------------------------------------

import app.Utility;

import app.Utility.SQLHelper;

import data.BaseColumns;
import data.DataContract;

import data.DataContract.HorarioEntry;
import data.DataContract.ListaClaseCursoEntry;
import data.DataContract.ListaClaseEntry;
import data.SQLData.Getters.ListaClasesCursoDataWorker;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Yknx
 */
public class ClasesTableModel extends AbstractTableModel {
    private final int         dia;
    private ArrayList<Object> cache;    // will hold String[] objects . . .
    private ListaClasesCursoDataWorker        getDataWorker;
    private final int         horario;
    private int               colCount;
    private String[]          headers;
    private final String      db;
    private String            currentURL;

    /**
     * Constructs a ClasesTableModel
     *
     *
     * @param dbs
     * A  jdbc valid url for the database
     * @param horarioc
     * A valid value for Horario (1-index)
     * @param diac
     * A valid value for dia (1-index)
     */
    private ClasesTableModel(String dbs, int horarioc, int diac) {
        db      = dbs;
        horario = horarioc;
        dia     = diac;
        cache   = new ArrayList();
        updateData();

        // new gsl.sql.driv.Driver();
    }

    /**
     * This methods update the data in the table.
     *
     */
    public final void updateData() {
        System.out.println(db);
        cache = new ArrayList();

        try {
            getDataWorker = new ListaClasesCursoDataWorker(horario, dia, db) {
                @Override
                public void done() {
                    try {

                        // Execute the query and store the result set and its metadata
                        ResultSet rs = get();

                        System.out.println("Data Worker DONE!");

                        ResultSetMetaData meta = rs.getMetaData();

                        colCount = meta.getColumnCount();

                        // Now we must rebuild the headers array with the new column names
                        headers = new String[colCount];

                        for (int h = 1; h <= colCount; h++) {
                            headers[h - 1] = meta.getColumnName(h);
                        }

                        // and file the cache with the records from our query. This would
                        // not be
                        // practical if we were expecting a few million records in response
                        // to our
                        // query, but we aren't, so we can do this.
                        while (rs.next()) {
                            String[] record = new String[colCount];

                            for (int i = 0; i < colCount; i++) {
                                record[i] = rs.getString(i + 1);
                            }

                            cache.add(record);
                        }

                        fireTableChanged(null);    // notify everyone that we have a new table.
                    } catch (SQLException | InterruptedException | ExecutionException ex) {
                        Logger.getLogger(ClasesTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            getDataWorker.execute();
        } catch (SQLException e) {
            cache = new ArrayList();    // blank it out and keep going.
            Logger.getLogger(ClasesTableModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method description
     *
     *
     * @param conn
     * @param horarioc
     * @param diac
     *
     * @return
     */
    public static ClasesTableModel with(String conn, int horarioc, int diac) {
        ClasesTableModel res = new ClasesTableModel(conn, horarioc, diac);

        return res;
    }

    /**
     * Method description
     *
     *
     * @param i
     *
     * @return
     */
    @Override
    public String getColumnName(int i) {
        switch (headers[i]) {
        case ListaClaseCursoEntry.COLUMN_GRUPO :
            return "#";

        case ListaClaseCursoEntry.COLUMN_MAESTRO :
            return "Profesor";

        case ListaClaseCursoEntry.COLUMN_MATERIA :
            return "Materia";

        case ListaClaseCursoEntry.COLUMN_SALON :
            return "Salón";

        default :
            return headers[i];
        }
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getColumnCount() {
        return colCount;
    }

    /**
     * Method description
     *
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return cache.size();
    }

    /**
     * Method description
     *
     *
     * @param row
     * @param col
     *
     * @return
     */
    @Override
    public Object getValueAt(int row, int col) {
        return ((String[]) cache.get(row))[col];
    }

    
}


//~ Formatted by Jindent --- http://www.jindent.com
