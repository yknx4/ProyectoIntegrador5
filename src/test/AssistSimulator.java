/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.mysql.jdbc.Util;
import helper.Utility;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import model.database.DatabaseInstance;

/**
 *
 * @author Yknx
 */
public class AssistSimulator {

    /**
     * @param args the command line arguments
     */
    static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    static Connection db;

    public static void main(String[] args) throws SQLException, ParseException {
        db = DatabaseInstance.getInstance();
        String clasesQuery = "SELECT C.id, id_maestro,H.inicio as hora FROM jfperez.clases C INNER JOIN horarios H on H.id = C.id_Horarios where C.dia = ";
        ResultSet clases;
        GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
        cal.set(2014, 8, 1, 1, 1, 1);
        while (cal.getTime().before(GregorianCalendar.getInstance().getTime())) {
            Calendar pcal = Calendar.getInstance();
            Date now = cal.getTime();
            System.out.println(sdf.format(now));
            int usuario;
            int asistio, justifico, clase;
            int dia = cal.get(GregorianCalendar.DAY_OF_WEEK);
            String fecha, createds, modified;
            dia -= 2;
            if (dia < 0) {
                dia += 7;
            }
            System.out.println("Dia: " + dia);

            clases = db.createStatement().executeQuery(clasesQuery + dia);
            //System.out.println("Query: " + clasesQuery + dia);
            while (clases.next()) {
                asistio = 1;
                justifico = 0;
                Date time = /*now.getTime() +*/ formatter.parse(clases.getString(3));
                pcal.set(2014, now.getMonth(), now.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
                usuario = clases.getInt(2);
                clase = clases.getInt(1);
                Date created = pcal.getTime();
                fecha = sdf.format(now);
                
                modified = sdf.format(created);
                if(Math.random()<.05){
                    asistio = 0;
                    if(Math.random()<.33){
                        justifico = 1;
                    }
                }else{
                    if(Math.random()<.05){
                        created.setMinutes(created.getMinutes()+21);
                    }
                }
                createds = sdf.format(created);
                String sql = "replace into asistencias(id_Clases, id_usuarios, fecha, created, modified, asistio, justifico) values("+clase+","+usuario+",'"+fecha+"','"+createds+"','"+modified+"',"+asistio+","+justifico+")";
                
                
                System.out.println("Clase: " + clase);
                //System.out.println("Dia: " + dia);
                System.out.println("Usuario: " + usuario);
                System.out.println("Fecha: " + fecha);
                System.out.println("Created: " + createds);
                System.out.println("Modified: " + modified);
                System.out.println("Asistio: " + asistio);
                System.out.println("Justifico: " + justifico);
                System.out.println("Insert: " + sql);
                System.out.println("");
                db.createStatement().execute(sql);
            }
            cal.roll(GregorianCalendar.DAY_OF_YEAR, true);
        }
        // TODO code application logic here
    }

}
