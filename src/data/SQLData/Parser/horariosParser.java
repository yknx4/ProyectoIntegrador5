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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 5j
 */
public class horariosParser {
    private horariosParser(){}
    ArrayList<Horario> horarios = new ArrayList<>();
    ResultSet rawData;
    
    public static horariosParser with(Connection con) throws SQLException{
        horariosParser n = new horariosParser();
        n.mConnection = con;
        n.init();
        return n;
    }
    private void init() throws SQLException{
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
    
    private void processData() throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Horario nuevo;
        while(rawData.next()){
            nuevo = new Horario();
            nuevo.id = rawData.getLong(DataContract.HorarioEntry._ID);
            
            horarios.add(nuevo);
        }
    }
    
    
    
}
