/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.SQLData.Parser;

import app.Utility;
import app.Utility.SQLHelper;
import data.BaseColumns;
import data.Clase;
import data.DataContract;
import data.DataContract.HorarioEntry;
import data.Horario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * @author 5j
 */
public class horariosParser {

    public static horariosParser with(String con) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("com.mysql.jdbc.Driver");
                // setup the connection with the DB.
                return with(DriverManager
                        .getConnection(con)); //To change body of generated methods, choose Tools | Templates.
    }
    private horariosParser(){}
    ArrayList<Horario> horarios = new ArrayList<>();
    ResultSet rawData;
    
    public String getHora(int t){
        
        return formatter.format(get(t).inicio);
    }
    
    public Date getFullDate(int t){
        return getFullDate(t,new Date());
    }
    public Date getFullDate(int t, Date dia){
        Date horario = get(t).inicio;
        Calendar cHorario = Calendar.getInstance();
        cHorario.setTime(horario);
        Calendar c = Calendar.getInstance();
        c.setTime(dia);
        c.set(Calendar.HOUR_OF_DAY, cHorario.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, cHorario.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND,0);
        
        
        return c.getTime();
    }
    
    public Horario get(int t){
        int n = t;
        n--;
        if (n<1){
            n += (horarios.size()-1);
        }
        return horarios.get(n);
    }
    
    public static horariosParser with(Connection con) throws SQLException, ParseException{
        horariosParser n = new horariosParser();
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
        query+= SQLHelper.generateSelect(HorarioEntry.TABLE_NAME, null);
        return query;
    }
    
    
    private void loadData() throws SQLException{
        rawData = mConnection.createStatement().executeQuery(getQuery());
        
    }
    SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss");
    private void processData() throws SQLException, ParseException {
        
        Horario nuevo;
        while(rawData.next()){
            nuevo = new Horario();
            nuevo.id = rawData.getLong(DataContract.HorarioEntry._ID);
            nuevo.inicio = formatter.parse(rawData.getString(HorarioEntry.COLUMN_INICIO));
            //System.out.println(nuevo);
            horarios.add(nuevo);
        }
        
    }
    
    public int[] getClosest(){
        Date today;
        int[] res = new int[2];
        int t=0;
        
        long tmp=0;
        try {
            today = today();
        } catch (ParseException ex) {
            Logger.getLogger(horariosParser.class.getName()).log(Level.SEVERE, null, ex);
            today = new Date();
        }
        long v=Math.abs(today.getTime() - horarios.get(0).inicio.getTime());
        
        
        for(Horario h:horarios){
            tmp = Math.abs(today.getTime() - h.inicio.getTime());
            if(tmp<v){
                v=tmp;
                t=(int)h.id;
            }
        }
        res[0] = t;
        res[1] = (t+1)%horarios.size();
        System.out.println("Yolo "+t);
        System.out.println("Yolo3 "+get(t));
        System.out.println("Yolo2 "+tmp);
        return res;
    }
    
    private Date today() throws ParseException{
        return formatter.parse(formatter.format(new Date()));
    }
    
    
    
}
