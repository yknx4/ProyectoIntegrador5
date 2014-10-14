/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.SQLData.Parser;

import helper.Utility;
import controller.SQLData.SQLHelper;
import model.database.BaseColumns;
import model.Clase;
import model.database.DataContract;
import model.database.DataContract.HorarioEntry;
import model.Horario;
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
import model.database.DatabaseInstance;

/**
 *
 * @author 5j
 */
public class HorariosParse {
private final static Logger LOGGER = Logger.getLogger(HorariosParse.class.getName());
    
    static HorariosParse instance;
    private HorariosParse() throws SQLException, ParseException{
        mConnection = DatabaseInstance.getInstance();
        init();
        
    }
    ArrayList<Horario> horarios = new ArrayList<>();
    ResultSet rawData;
    
    public String getHora(int t){
        if(t==0){
            return "00:00:00";
        }
        return formatter.format(get(t).inicio);
    }
     public String getHora(int t, boolean printFriendly){
        if(t==0){
            return "00:00";
        }
        return sformatter.format(get(t).inicio);
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
        if(n<0) n+=horarios.size();
        Horario r =horarios.get(n);
        System.out.println("ID: "+t+"->"+n+" Info:"+r);
        return r;
    }
    
    public static HorariosParse with() throws SQLException, ParseException{
        return getInstance();
    }
     public static HorariosParse getInstance() throws SQLException, ParseException{
        if(instance==null){
            instance = new HorariosParse();
        }
        return instance;
    }
     public int count(){
         return horarios.size();
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
    SimpleDateFormat sformatter= new SimpleDateFormat("HH:mm");
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
        int t=1;
        
        long tmp=0;
       try{
            today = today();
       }
       catch(ParseException ex){
           today = new Date();
       }
            
            //today = new Date();
        
        long v=Math.abs(today.getTime() - horarios.get(0).inicio.getTime());
        
        
        for(Horario h:horarios){
            tmp = Math.abs(today.getTime() - h.inicio.getTime());
            if(tmp<v){
                v=tmp;
                t=(int)h.id;
            }
        }
        res[0] = t;
        res[1] = (t+1)%(horarios.size()+1);
        System.out.println("Yolo "+t);
        System.out.println("Yolo3 "+get(t));
        System.out.println("Yolo2 "+tmp);
        return res;
    }
    
    private Date today() throws ParseException{
        return formatter.parse(formatter.format(new Date()));
    }

    public String getFullDateString(int aInt) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(getFullDate(aInt));
    }
    
    
    
}
