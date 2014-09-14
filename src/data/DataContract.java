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
    
    
    public static class ListaClaseCursoEntry implements BaseColumns{

        
        /*SELECT  C.id,
         C.dia,
         C.id_Horarios,
         Ma.nombre as ma_nombre,
         M.nombre as maestro,
         CONCAT(G.semestre,G.grupo) as grupo,
         S.nombre as salon*/
        public static final String TABLE_NAME = "listaclasescurso";
        public static final String COLUMN_MATERIA = "ma_nombre";
        public static final String COLUMN_MAESTRO = "maestro";
        public static final String COLUMN_ID_MAESTRO = "id_maestro";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_SALON = "salon";
        public static final String COLUMN_ID_HORARIO = "id_Horarios";
        public static final String COLUMN_GRUPO = "grupo";
        public static final String COLUMN_ASISTENCIA = "asistio";
        
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
    
    public static class AsistenciaDataViewEntry implements BaseColumns{

        
        /*SELECT 
	C.id,
	C.dia,
	U.id as id_maestro,
	U.nombre as nombre_maestro,
	U.passhash,
	U.permission,
	U.picture,
	U.email,
	C.id_Horarios,
	H.inicio,
	M.nombre as materia_nombre,
	S.nombre as salon
        FROM clases C
        INNER JOIN usuarios U on C.id_maestro = U.id
        INNER JOIN  horarios H on C.id_Horarios = H.id
        INNER JOIN materias M on C.id_Materias = M.id
        INNER JOIN salones S on C.id_Salones = S.id*/
        public static final String TABLE_NAME = "asistenciadataview";
        public static final String COLUMN_MATERIA = "materia_nombre";
        public static final String COLUMN_MAESTRO = "nombre_maestro";
        public static final String COLUMN_ID_MAESTRO = "id_maestro";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_SALON = "salon";
        public static final String COLUMN_ID_HORARIO = "id_Horarios";
        public static final String COLUMN_MAESTRO_PASSWORD = "passhash";
        public static final String COLUMN_MAESTRO_PERMISSIONS = "permission";
        public static final String COLUMN_MAESTRO_PICTURES = "picture";
        public static final String COLUMN_MAESTRO_EMAIL = "email";
        public static final String COLUMN_HORARIO_INICIO = "inicio";
        
        
        
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
    
    public static class ListaClaseEntry implements BaseColumns{

        public static final String TABLE_NAME = "listaclases";
        public static final String COLUMN_MATERIA = "ma_nombre";
        public static final String COLUMN_MAESTRO = "nombre";
        public static final String COLUMN_DIA = "dia";
        public static final String COLUMN_DIA_STRING = "dia_str";
        public static final String COLUMN_SALON = "salon";
        public static final String COLUMN_HORARIO = "inicio";
        public static final String COLUMN_GRUPO = "grupo";
        
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
    
    public static class UsuarioEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "usuarios";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_PASSWORD_HASH = "passhash";
        public static final String COLUMN_PERMISSIONS = "permission";
        public static final String COLUMN_TIMESTAMP_CREATED = "created";
        public static final String COLUMN_TIMESTAMP_MODIFIED = "modified";
        public static final String COLUMN_ACTIVO = "activo";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PICTURE_URI = "picture";

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
        public static final String COLUMN_ID_USUARIO = "id_Maestros";
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





    
    

}
