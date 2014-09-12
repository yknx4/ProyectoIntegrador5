/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.SQLData.Parser;

import app.Utility;
import data.BaseColumns;
import data.Clase;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yknx
 */
public class ClasesParser {
    private ClasesParser(){}
    ArrayList<Clase> clases = new ArrayList<>();
    
    public static ClasesParser with(Connection con){
        ClasesParser n = new ClasesParser();
        n.mConnection = con;
        return n;
    }
    //private static String[] Materias = {"ADMINISTRACIÓN DE PROYECTOS DE SOFTWARE","ANÁLISIS DE SEÑALES","ANÁLISIS Y MODELADO DE SOFTWARE","ARQUITECTURA DE COMPUTADORAS","BASES DE DATOS","COMUNICACIONES DIGITALES","DERECHO INFORMÁTICO","DIRECCIÓN DE PROYECTOS","ELECTRICIDAD Y MAGNETISMO","ENRUTAMIENTO WAN Y CONMUTACIÓN","ÉTICA Y COMPORT.  EN  LAS  ORGANIZAC.","EVALUACIÓN DE PROYECTOS","FÍSICA","FORMULACIÓN DE PROYECTOS","HERRAMIENTAS DE PROGRAMACIÓN","INGENIERÍA DE SOFTWARE","INGLÉS I","INGLÉS III","INGLÉS V","INGLÉS VII","INTERACCIÓN HUMANO-COMPUTADORA","LENGUAJES ALGORÍTMICOS","MANTENIMIENTO DE SOFTWARE","MATEMÁTICAS BÁSICAS","MATEMÁTICAS DISCRETAS","MATEMÁTICAS PARA INGENIERÍA","MÉTODOS NUMÉRICOS","OFIMÁTICA","PROBABILIDAD","PROBABILIDAD Y MÉTODOS ESTADÍSTICOS","PROGRAMACIÓN","PROGRAMACIÓN DE DISPOSITIVOS MÓVILES","PROGRAMACIÓN DISTRIBUIDA","PROGRAMACIÓN PARA WEB","PROGRAMACIÓN VISUAL","REDES DE DATOS","REDES INALÁMBRICAS DE SENSORES","ROBÓTICA","SEGURIDAD DE DATOS","SEMINARIO DE INVESTIGACIÓN II","SIMULACIÓN Y CALIDAD DE SERVICIO","SISTEMAS DE HIPERMEDIA","SISTEMAS DIGITALES"};
    Connection mConnection;
    //1A ÉTICA Y COMPORT.  EN  LAS  ORGANIZAC.	3	ALCARAZ AMADOR MARTHA CRISTINA		13				Auditorio 1
    private String getBaseQuery(String column, String tableName, int mtype){
        final String[] Columns = {column};
        final int[] type = {mtype};
        final String[] toGet = {BaseColumns._ID};
        String query="";
        query+= Utility.SQLHelper.generateSelect(tableName, toGet);
        query += " ";
        query+= Utility.SQLHelper.generateWhere(Columns, type);
        return query;
    }
    private PreparedStatement getBasicStringStatement(String value, String query){
        PreparedStatement res=null;
        if(mConnection != null){
        try {
            res = mConnection.prepareStatement(query);
            res.setString(1, value);
        } catch (SQLException ex) {
            //Logger.getLogger(ClasesParser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error = "+ex.getMessage());
        }
        }
        //System.out.println("Query = "+query);
        return res==null?null:res;
    }
    public PreparedStatement getGrupoStatement(String group){
        String query = getBaseQuery("grupo","grupoHelpView",Utility.SQLHelper.WHERE_TYPE_EQUAL);
        return getBasicStringStatement(group,query);
    }
    public long getGrupoID(String grupo){
        return getID(getGrupoStatement(grupo));
    }
    public PreparedStatement getMateriaStatement(String materia){
        String query = getBaseQuery("nombre","Materias",Utility.SQLHelper.WHERE_TYPE_EQUAL);
        return getBasicStringStatement(materia,query);
    }
    public long getMateriaID(String materia){
        return getID(getMateriaStatement(materia));
    }
    public PreparedStatement getMaestroStatement(String maestro){
        String query = getBaseQuery("nombre","maestros",Utility.SQLHelper.WHERE_TYPE_EQUAL);
        return getBasicStringStatement(maestro,query);
    }
     public long getMaestroID(String maestro){
        return getID(getMaestroStatement(maestro));
    }

    public PreparedStatement getSalonStatement(String salon) {
        String query = getBaseQuery("nombre","salones",Utility.SQLHelper.WHERE_TYPE_EQUAL);
        return getBasicStringStatement(salon,query);
    }
    public long getSalonID(String salon){
        return getID(getSalonStatement(salon));
    }
    
    public static long getID(PreparedStatement state){
        long res = -1;
        try {
            ResultSet resset = state.executeQuery();
            if(resset.first())
            res = resset.getLong(1);
        } catch (SQLException ex) {
            Logger.getLogger(ClasesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(res==-1){
         System.out.println(   state.toString());
         System.out.println("ID = "+res);
        }
        return res;
    }
    
    public void parseString(String[] toParse){
        long grupo = getGrupoID(toParse[0].trim());
        long materia = getMateriaID(toParse[1].trim());
        long maestro = getMaestroID(toParse[3].trim());
        long[][] horas = new long[5][5];
        int cnt=0;
        for(int i = 4; i<=8;i++){
            String t = toParse[i];
            if(t.contains("!")){
                String[] tHoras = t.split("!");
                for(String x:tHoras){
                    horas[i-4][cnt] = Long.parseLong(x);
                    cnt++;
                }
                
            }
            else horas[i-4][0] = Long.parseLong(t);
        }
        long salon = getSalonID(toParse[9]);
        boolean justOne = true;
        for(short dia = 0;dia<5;dia++)
            for(int hora=0;hora<5;hora++){
                if(horas[dia][hora] >0){
                    Clase curr = new Clase();
                    curr.dia = dia;
                    curr.grupo = grupo;
                    curr.horario = horas[dia][hora];
                    curr.maestro = maestro;
                    curr.materia = materia;
                    curr.salon = salon;
                    clases.add(curr);
                    /////DEBUG
                    /*if(justOne){
                    System.out.println(toParse[3]);
                    justOne = false;
                    }
                    System.out.print("Dia :");
                    System.out.println(dia+1);
                    System.out.print("Hora :");
                    System.out.println(horas[dia][hora]);*/
                }
            }
        
        
        
        
    }
    public void readData(String file) throws IOException{
        BufferedReader dataBR = new BufferedReader(
		   new InputStreamReader(
                      new FileInputStream(file), "UTF-8"));
        String line = "";

        ArrayList<String[]> dataArr = new ArrayList<>(); //An ArrayList is used because I don't know how many records are in the file.

        while ((line = dataBR.readLine()) != null) { // Read a single line from the file until there are no more lines to read

            

            String[] value = line.split(",");
            

            dataArr.add(value); // Add the "club" info to the list of clubs.
        }

       /* for (int i = 0; i < dataArr.size(); i++) {
            for (int x = 0; x < dataArr.get(i).length; x++) {
                //System.out.printf("dataArr[%d][%d]: ", i, x);
               // System.out.println(dataArr.get(i)[x]);
            }
        }*/
        for(String[] x:dataArr) parseString(x);
        for(Clase x:clases) x.print();
        WriteData();

       
}
    
    public void WriteData(){
        try {
		File fileDir = new File("D:\\Users\\Yknx\\Dropbox\\Programación distribuida\\db\\gen_clases.sql");
 
            try (Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"))) {
                for(Clase x:clases) out.append(x.print()).append("\r\n");
                
                
                
                out.flush();
            }
 
	    } 
	   catch (UnsupportedEncodingException e) 
	   {
		System.out.println(e.getMessage());
	   } 
	   catch (IOException e) 
	   {
		System.out.println(e.getMessage());
	    }
	   catch (Exception e)
	   {
		System.out.println(e.getMessage());
	   } 

    }
}




/*dataArr[428][0]: 7G
dataArr[428][1]: REDES INAL�MBRICAS DE SENSORES
dataArr[428][2]: 4
dataArr[428][3]: AMEZCUA VALDOVINOS ISMAEL
dataArr[428][4]: -1
dataArr[428][5]: 2
dataArr[428][6]: -1
dataArr[428][7]: 2
dataArr[428][8]: -1
dataArr[428][9]: L Redes*/