/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.SQLData.Getters;

import controller.SQLData.SQLHelper;
import helper.Utility;
import controller.SQLData.SQLHelper;
import model.database.DataContract;
import model.database.DataContract.ListaClaseCursoEntry;
import model.SQLData.ClasesTableModel;
import model.database.DatabaseInstance;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author Yknx
 */
public class ListaClasesCursoDataWorker extends SwingWorker<ResultSet, Void> {
        private final static Logger LOGGER = Logger.getLogger(ListaClasesCursoDataWorker.class.getName());
        /** Field description */
        PreparedStatement  statement;
        private Connection connect;
        private int        dia;
        private boolean allHorarios = false;
        private final static int INVALID_VALUE = -1;

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
         *
         * @throws SQLException
         */
        public ListaClasesCursoDataWorker(int horario, int dia) throws SQLException {
                this();
                this.horario = horario;
                this.dia     = dia;
                statement    = connect.prepareStatement(getQuery());
                LOGGER.log(Level.CONFIG, "Horario set to: {0}.\n Dia set to: {1}", new Object[]{horario, dia});
        }
        
        public ListaClasesCursoDataWorker(int horario, int dia, String[] columns) throws SQLException {
                this(horario,dia);
                statement    = connect.prepareStatement(getQuery(columns));
        }
        public ListaClasesCursoDataWorker(int dia, String[] columns) throws SQLException {
                this(INVALID_VALUE,dia,columns);
                setAllHorarios(true);
        }
        private ListaClasesCursoDataWorker() throws SQLException{
            LOGGER.info("ListaClasesCursoDataWorker Created!");
            connect = DatabaseInstance.getInstance();
        }
        
        
        

        /**
         * Method description
         *
         *
         * @return
         */
        private String getQuery() {
            final String[] columns = { DataContract.ListaClaseCursoEntry.COLUMN_GRUPO, DataContract.ListaClaseCursoEntry.COLUMN_MAESTRO,
                                       DataContract.ListaClaseCursoEntry.COLUMN_MATERIA, DataContract.ListaClaseCursoEntry.COLUMN_SALON, 
                                        DataContract.ListaClaseCursoEntry.COLUMN_ASISTENCIA};
            return getQuery(columns);
        }
        private String getQuery(String[] columns) {
        String         query   = "";
            
            final String[] where = { DataContract.ListaClaseCursoEntry.COLUMN_ID_HORARIO, DataContract.ListaClaseCursoEntry.COLUMN_DIA };
            
            final int[]    type  = { SQLHelper.WHERE_TYPE_EQUAL, SQLHelper.WHERE_TYPE_EQUAL };

            query += SQLHelper.generateSelect(DataContract.ListaClaseCursoEntry.TABLE_NAME, columns);
            query += " ";
            if(!allHorarios)query += SQLHelper.generateWhere(where, type);
            else query+= SQLHelper.generateWhere(ListaClaseCursoEntry.COLUMN_DIA, SQLHelper.WHERE_TYPE_EQUAL);
            //System.out.println(query);

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
            //System.out.println("INICIADO " + statement.toString());
            LOGGER.log(Level.INFO, "Started with: {0}", statement.toString());

            ResultSet rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            
            LOGGER.info("Result metadata: "+rsmd.toString());
            

            //Logger.getLogger(ClasesTableModel.class.getName()).log(Level.INFO, null, statement.toString());
            //System.out.println(statement.toString());

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
