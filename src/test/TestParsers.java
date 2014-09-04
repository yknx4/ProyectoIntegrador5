/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import data.SQLData.Parser.clasesParser;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yknx
 */
public class TestParsers {
    private static Connection connect;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            connect = DriverManager
                    .getConnection("jdbc:mysql://189.182.229.25:3306/jfperez?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&"
                            + "user=root&password=konami1994");
            
            
            // TODO code application logic here
            //1A ÉTICA Y COMPORT.  EN  LAS  ORGANIZAC.	3	ALCARAZ AMADOR MARTHA CRISTINA		13				Auditorio 1
            String grupo = "1A";
            String maestro = "CORTÉS LUGO HUGO";
            int hora = 13;
            String salon = "Auditorio 1";
            //clasesParser.with(connect).getGrupoID(grupo);
            clasesParser.with(connect).getMaestroID(maestro);
            //clasesParser.with(connect).getSalonID(salon);
            if(args!=null && args[0] !=null)clasesParser.with(connect).readData(args[0]);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Logger.getLogger(TestParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
