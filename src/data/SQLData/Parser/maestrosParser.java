/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.SQLData.Parser;

import app.Utility;
import data.BaseColumns;
import data.DataContract;
import data.DataContract.MaestroEntry;
import data.Horario;
import data.Maestro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yknx
 */
public class maestrosParser {
    public static maestrosParser with(String con) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("com.mysql.jdbc.Driver");
                // setup the connection with the DB.
                return with(DriverManager
                        .getConnection(con)); //To change body of generated methods, choose Tools | Templates.
    }
    private maestrosParser(){}
    ArrayList<Maestro> maestros = new ArrayList<>();
    ResultSet rawData;
    

    
    public Maestro get(int t){
        int n = t;
        n--;
        if (n<1){
            n += (maestros.size()-1);
        }
        return maestros.get(n);
    }
    
    public static maestrosParser with(Connection con) throws SQLException, ParseException{
        maestrosParser n = new maestrosParser();
        n.mConnection = con;
        n.init();
        return n;
    }
    private void init() throws SQLException, ParseException{
        loadData();
        processData();
    }
    //private static String[] Materias = {"ADMINISTRACIÓN DE PROYECTOS DE SOFTWARE","ANÁLISIS DE SEÑALES","ANÁLISIS Y MODELADO DE SOFTWARE","ARQUITECTURA DE COMPUTADORAS","BASES DE DATOS","COMUNICACIONES DIGITALES","DERECHO INFORMÁTICO","DIRECCIÓN DE PROYECTOS","ELECTRICIDAD Y MAGNETISMO","ENRUTAMIENTO WAN Y CONMUTACIÓN","ÉTICA Y COMPORT.  EN  LAS  ORGANIZAC.","EVALUACIÓN DE PROYECTOS","FÍSICA","FORMULACIÓN DE PROYECTOS","HERRAMIENTAS DE PROGRAMACIÓN","INGENIERÍA DE SOFTWARE","INGLÉS I","INGLÉS III","INGLÉS V","INGLÉS VII","INTERACCIÓN HUMANO-COMPUTADORA","LENGUAJES ALGORÍTMICOS","MANTENIMIENTO DE SOFTWARE","MATEMÁTICAS BÁSICAS","MATEMÁTICAS DISCRETAS","MATEMÁTICAS PARA INGENIERÍA","MÉTODOS NUMÉRICOS","OFIMÁTICA","PROBABILIDAD","PROBABILIDAD Y MÉTODOS ESTADÍSTICOS","PROGRAMACIÓN","PROGRAMACIÓN DE DISPOSITIVOS MÓVILES","PROGRAMACIÓN DISTRIBUIDA","PROGRAMACIÓN PARA WEB","PROGRAMACIÓN VISUAL","REDES DE DATOS","REDES INALÁMBRICAS DE SENSORES","ROBÓTICA","SEGURIDAD DE DATOS","SEMINARIO DE INVESTIGACIÓN II","SIMULACIÓN Y CALIDAD DE SERVICIO","SISTEMAS DE HIPERMEDIA","SISTEMAS DIGITALES"};
    Connection mConnection;
    
    private String getQuery(){
        String query="";
        query+= Utility.SQLHelper.generateSelect(DataContract.MaestroEntry.TABLE_NAME, null);
        return query;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    
    private void loadData() throws SQLException{
        rawData = mConnection.createStatement().executeQuery(getQuery());
        
    }
   
    private void processData() throws SQLException, ParseException {
        
        Maestro nuevo;
        while(rawData.next()){
            nuevo = new Maestro();
            nuevo.id = rawData.getLong(MaestroEntry._ID);
            nuevo.activo = rawData.getInt(MaestroEntry.COLUMN_ACTIVO);
            nuevo.created = sdf.parse(rawData.getString(MaestroEntry.COLUMN_TIMESTAMP_CREATED));
            nuevo.email = rawData.getString(MaestroEntry.COLUMN_EMAIL);
            nuevo.modified = sdf.parse(rawData.getString(MaestroEntry.COLUMN_TIMESTAMP_MODIFIED));
            nuevo.nombre = rawData.getString(MaestroEntry.COLUMN_NOMBRE);
            nuevo.passhash = rawData.getString(MaestroEntry.COLUMN_PASSWORD_HASH);
            nuevo.permission = rawData.getInt(MaestroEntry.COLUMN_PERMISSIONS);
            nuevo.picture_uri = rawData.getString(MaestroEntry.COLUMN_PICTURE_URI);     
            System.out.println(nuevo);
            maestros.add(nuevo);
        }
        
    }
    

}
