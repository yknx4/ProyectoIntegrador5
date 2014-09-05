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
import data.DataContract.HorarioEntry;
import data.DataContract.ListaClaseCursoEntry;
import data.DataContract.ListaClaseEntry;
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
    private final int dia;

    private class DataWorker extends SwingWorker<ResultSet, Void> {

        PreparedStatement statement;
        private Connection connect;
        private int dia;

        private String getQuery() {
            String query = "";
            final String[] columns = {ListaClaseCursoEntry.COLUMN_GRUPO, ListaClaseCursoEntry.COLUMN_MAESTRO, ListaClaseCursoEntry.COLUMN_MATERIA, ListaClaseCursoEntry.COLUMN_SALON};
            final String[] where = {ListaClaseCursoEntry.COLUMN_ID_HORARIO,ListaClaseCursoEntry.COLUMN_DIA};
            final int[] type = {SQLHelper.WHERE_TYPE_EQUAL,SQLHelper.WHERE_TYPE_EQUAL};
            query += Utility.SQLHelper.generateSelect(ListaClaseCursoEntry.TABLE_NAME, columns);
            query += " ";
            query += SQLHelper.generateWhere(where, type);
            System.out.println(query);
            
            
            return query;

        }

        int horario;
        Connection con;

        public DataWorker(int horario,int dia, String con) throws SQLException {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                // setup the connection with the DB.
                connect = DriverManager
                        .getConnection(con);
                this.horario = horario;
                this.dia = dia;

                statement = connect.prepareStatement(getQuery());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClasesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        protected ResultSet doInBackground() throws Exception {

            statement.setInt(1, horario);
            statement.setInt(2, dia);
            //statement.setAsciiStream(horario, null);
            System.out.println("INICIADO " + statement.toString());
            ResultSet rs = statement.executeQuery();
            Logger.getLogger(ClasesTableModel.class.getName()).log(Level.INFO, null, statement.toString());
            System.out.println(statement.toString());
            return rs;
        }

    }

    ArrayList<Object> cache; // will hold String[] objects . . .

    DataWorker getDataWorker;

    int horario;

    int colCount;

    String[] headers;

    String db;

    String currentURL;

    public final void updateData() {
        System.out.println(db);
        cache = new ArrayList();
        try {
            getDataWorker = new DataWorker(horario,dia, db) {
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
                        fireTableChanged(null); // notify everyone that we have a new table.
                    } catch (SQLException | InterruptedException | ExecutionException ex) {
                        Logger.getLogger(ClasesTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };

            getDataWorker.execute();
        } catch (SQLException e) {
            cache = new ArrayList(); // blank it out and keep going.
            e.printStackTrace();
        }
    }

    public static ClasesTableModel with(String conn, int horarioc, int diac) {

        ClasesTableModel res = new ClasesTableModel(conn, horarioc, diac);

        return res;
    }

    private ClasesTableModel(String dbs, int horarioc, int diac) {
        db = dbs;
        horario = horarioc;
        dia = diac;
        cache = new ArrayList();
        updateData();
        //new gsl.sql.driv.Driver();
    }

    @Override
    public String getColumnName(int i) {
        switch (headers[i]) {

            case ListaClaseCursoEntry.COLUMN_GRUPO:
                return "#";
            case ListaClaseCursoEntry.COLUMN_MAESTRO:
                return "Profesor";
            case ListaClaseCursoEntry.COLUMN_MATERIA:
                return "Materia";
            case ListaClaseCursoEntry.COLUMN_SALON:
                return "Salón";

            default:
                return headers[i];
        }

    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public int getRowCount() {
        return cache.size();
    }

    @Override
    public Object getValueAt(int row, int col) {

        return ((String[]) cache.get(row))[col];
    }

}
