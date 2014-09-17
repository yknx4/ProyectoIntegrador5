/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import app.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Yknx
 */
public class Usuario {
    private final static Logger LOGGER = Logger.getLogger(Usuario.class.getName());
    /*
    b0 : Administrative Personnel
    b1 : Professor
    b2 : System Administrator
    b3 : EXTRA_FLAG
    b4 => b7 : UNUSED
    
    */
    public static final int ADMINISTRATIVE_PERSONNEL = 0b00000001;
    public static final int PROFESSOR = 0b00000010;
    public static final int SYSTEM_ADMIN = 0b00000100;
    public static final int EXTRA_FLAG = 0b00001000;
    public static final int INVALID_PERMISSION = -1;
    public static final long INVALID = -1;
    
    private static int getMask(int input){
        return ~(0b11111111 ^ input);
    }
    public static boolean hasPermission(int permission, int toLook){
        return (permission & getMask(toLook)) == toLook;
    }
    public static Integer[] getPermissions(int permission){
        ArrayList<Integer> perns = new ArrayList<>();
        if(hasPermission(permission, ADMINISTRATIVE_PERSONNEL)) perns.add(ADMINISTRATIVE_PERSONNEL);
        if(hasPermission(permission, PROFESSOR)) perns.add(PROFESSOR);
        if(hasPermission(permission, SYSTEM_ADMIN)) perns.add(SYSTEM_ADMIN);
        if(hasPermission(permission, EXTRA_FLAG)) perns.add(EXTRA_FLAG);
        
        return  (Integer[]) perns.toArray(new Integer[perns.size()]);
    }
    public static String permissionName(int Permission){
        switch(Permission){
            case ADMINISTRATIVE_PERSONNEL:
                return "Personal Administrativo";
                case PROFESSOR:
                return "Profesor";
                    case SYSTEM_ADMIN:
                return "Administrador del Sistema";
                        case EXTRA_FLAG:
                return "EXTRA FLAG";
                            default:
                return "PERMISO INVALIDO";
        }
    }
    
    
    
    
    public static Usuario generateUser(ResultSet rawData) throws SQLException, ParseException{
        Usuario nuevo = new Usuario();
            nuevo.id = rawData.getLong(DataContract.UsuarioEntry._ID);
            nuevo.activo = rawData.getInt(DataContract.UsuarioEntry.COLUMN_ACTIVO);
            nuevo.created = Utility.SQLDateFormatter.parse(rawData.getString(DataContract.UsuarioEntry.COLUMN_TIMESTAMP_CREATED));
            nuevo.email = rawData.getString(DataContract.UsuarioEntry.COLUMN_EMAIL);
            nuevo.modified = Utility.SQLDateFormatter.parse(rawData.getString(DataContract.UsuarioEntry.COLUMN_TIMESTAMP_MODIFIED));
            nuevo.nombre = rawData.getString(DataContract.UsuarioEntry.COLUMN_NOMBRE);
            nuevo.passhash = rawData.getString(DataContract.UsuarioEntry.COLUMN_PASSWORD_HASH);
            nuevo.permission = rawData.getInt(DataContract.UsuarioEntry.COLUMN_PERMISSIONS);
            nuevo.picture_uri = rawData.getString(DataContract.UsuarioEntry.COLUMN_PICTURE_URI);     
            return nuevo;
    }
     
    
    public long id;
    public String nombre;
    public String passhash;
    public int permission;
    public Date created;
    public Date modified;
    public int activo;
    public String email;
    public String picture_uri;
    @Override
    public String toString(){
        return "Id: "+id+
              " Nombre: "+nombre+
              " Picture: "+picture_uri;
    }
}
