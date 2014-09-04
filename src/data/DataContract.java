/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

/**
 *
 * @author 5j
 */
public class DataContract {
    public static class MaestroEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "Maestros";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_PASSWORD_HASH = "passhash";
        public static final String COLUMN_PERMISSIONS = "permission";
        public static final String COLUMN_TIMESTAMP_CREATED = "created";
        public static final String COLUMN_TIMESTAMP_MODIFIED = "modified";
        public static final String COLUMN_ACTIVO = "activo";

        @Override
        public String getQuery() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getInsert() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDelete() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getUpdate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    public static class ClaseEntry implements BaseColumns{
        public static final String TABLE_NAME = "Clases";
        public static final String COLUMN_ID_MATERIA = "id_Materias";
        public static final String COLUMN_ID_MAESTRO = "id_maestro";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_ID_SALONES = "id_Salones";
        public static final String COLUMN_ID_HORARIOS = "id_Horarios";
        public static final String COLUMN_ID_GRUPOS = "id_Grupos";
        public static final String COLUMN_MODIFIED = "modified";

        @Override
        public String getQuery() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getInsert() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDelete() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getUpdate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    public static class HorarioEntry implements BaseColumns {
        public static final String TABLE_NAME = "Horarios";
        public static final String COLUMN_INICIO  = "inicio";
        public static final String COLUMN_DURACION  = "duracion";

        @Override
        public String getQuery() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getInsert() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDelete() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getUpdate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    public static class SalonEntry implements BaseColumns {
        public static final String TABLE_NAME = "Salones";
        public static final String COLUMN_NOMBRE = "nombre";

        @Override
        public String getQuery() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getInsert() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDelete() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getUpdate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    public static class GrupoEntry implements BaseColumns {
        public static final String TABLE_NAME = "Grupos";
        public static final String COLUMN_SEMESTRE  = "semestre";
        public static final String COLUMN_GRUPO  = "grupo";

        @Override
        public String getQuery() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getInsert() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDelete() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getUpdate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    public static class AsistenciaEntry implements BaseColumns {
        public static final String TABLE_NAME = "Asistencias";
        public static final String COLUMN_ID_CLASE = "id_Clases";
        public static final String COLUMN_ID_MAESTRO = "id_Maestros";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_TIMESTAMP_CREATED = "created";
        public static final String COLUMN_TIMESTAMP_MODIFIED = "modified";
        public static final String COLUMN_ASISTIO = "asistio";
        public static final String COLUMN_JUSTIFICO = "justificio";

        @Override
        public String getQuery() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getInsert() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getDelete() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getUpdate() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }


/*
inner join. For example: 
SELECT * FROM t1 LEFT JOIN (t2, t3, t4)
                 ON (t2.a=t1.a AND t3.b=t1.b AND t4.c=t1.c)


is equivalent to: 
SELECT * FROM t1 LEFT JOIN (t2 CROSS JOIN t3 CROSS JOIN t4)
                 ON (t2.a=t1.a AND t3.b=t1.b AND t4.c=t1.c)


*/


    
    

}
