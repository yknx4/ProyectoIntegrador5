/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.SQLData.Getters;

import app.Utility;
import app.Utility.SQLHelper;
import data.DataContract;
import data.DataContract.ListaClaseCursoEntry;
import data.SQLData.ClasesTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Yknx
 */
public class ListaClasesCursoDataWorker extends SwingWorker<ResultSet, Void> {

        /** Field description */
        PreparedStatement  statement;
        private Connection connect;
        private int        dia;
        private boolean allHorarios = false;

        /** Field description */
        int horario;

        /** Field description */
        Connection con;

        /**
         * Constructs ...
         *
         *
         * @param horario
         * @param dia
         * @param con
         *
         * @throws SQLException
         */
        public ListaClasesCursoDataWorker(int horario, int dia, String con) throws SQLException {
            try {
                Class.forName("com.mysql.jdbc.Driver");

                // setup the connection with the DB.
                connect      = DriverManager.getConnection(con);
                this.horario = horario;
                this.dia     = dia;
                statement    = connect.prepareStatement(getQuery());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClasesTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public ListaClasesCursoDataWorker(int horario, int dia, Connection con) throws SQLException {
                // setup the connection with the DB.
                connect      = con;
                this.horario = horario;
                this.dia     = dia;
                statement    = connect.prepareStatement(getQuery());
        }
        public ListaClasesCursoDataWorker(int horario, int dia, Connection con, String[] columns) throws SQLException {
                // setup the connection with the DB.
                connect      = con;
                this.horario = horario;
                this.dia     = dia;
                statement    = getStatement(columns);
        }
        public ListaClasesCursoDataWorker(int dia, Connection con, String[] columns) throws SQLException {
                connect      = con;
                this.dia     = dia;
                statement    = getStatement(columns);
                setAllHorarios(true);
        }
        
        
        private PreparedStatement getStatement(String[] columns) throws SQLException{
            if (columns==null || columns.length==0) return connect.prepareStatement(getQuery());
            else return connect.prepareStatement(getQuery(columns));
        }

        /**
         * Method description
         *
         *
         * @return
         */
        private String getQuery() {
            final String[] columns = { DataContract.ListaClaseCursoEntry.COLUMN_GRUPO, DataContract.ListaClaseCursoEntry.COLUMN_MAESTRO,
                                       DataContract.ListaClaseCursoEntry.COLUMN_MATERIA, DataContract.ListaClaseCursoEntry.COLUMN_SALON };
            return getQuery(columns);
        }
        private String getQuery(String[] columns) {
        String         query   = "";
            
            final String[] where = { DataContract.ListaClaseCursoEntry.COLUMN_ID_HORARIO, DataContract.ListaClaseCursoEntry.COLUMN_DIA };
            
            final int[]    type  = { Utility.SQLHelper.WHERE_TYPE_EQUAL, Utility.SQLHelper.WHERE_TYPE_EQUAL };

            query += Utility.SQLHelper.generateSelect(DataContract.ListaClaseCursoEntry.TABLE_NAME, columns);
            query += " ";
            if(!allHorarios)query += Utility.SQLHelper.generateWhere(where, type);
            else query+= SQLHelper.generateWhere(ListaClaseCursoEntry.COLUMN_DIA, SQLHelper.WHERE_TYPE_EQUAL);
            System.out.println(query);

            return query;
    }

        /**
         * Method description
         *
         *
         * @return
         *
         * @throws Exception
         */
        @Override
        protected ResultSet doInBackground() throws Exception {
            if(allHorarios){
             statement.setInt(1, dia);
            }
            else{
            statement.setInt(1, horario);
            statement.setInt(2, dia);
            }
            // statement.setAsciiStream(horario, null);
            System.out.println("INICIADO " + statement.toString());

            ResultSet rs = statement.executeQuery();

            Logger.getLogger(ClasesTableModel.class.getName()).log(Level.INFO, null, statement.toString());
            System.out.println(statement.toString());

            return rs;
        }

    /**
     * @return the allHorarios
     */
    public boolean isAllHorarios() {
        return allHorarios;
    }

    /**
     * @param allHorarios the allHorarios to set
     */
    public final void setAllHorarios(boolean allHorarios) {
        this.allHorarios = allHorarios;
    }

    
    }
