
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package test;

//~--- non-JDK imports --------------------------------------------------------

import controller.AsistenciaController;
import java.sql.SQLException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 *
 * @author Yknx
 */
public class TestGetUser {

    /**
     * @param args the command line arguments
     *
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        Handler systemOutHandler = new StreamHandler(System.out, new SimpleFormatter()); 
 systemOutHandler.setLevel(Level.FINEST); 
 Logger rootLogger = Logger.getLogger(""); 
 rootLogger.addHandler(systemOutHandler); 
 rootLogger.setLevel(Level.FINEST);

        AsistenciaController m       = AsistenciaController.getInstance(4, 14);
        String               usuario = "alc.ina@telematica.mx";

        // String usuario = "moc.ose@telematica.mx";
        // String usuario = "bri.ian@telematica.mx";
        // String usuario = "vaz.ron@telematica.mx";
        String password = "password";

        System.out.println("Pass: " + password);

        // System.out.println("ID de "+usuario+" :"+m.getUser(usuario, password));
        System.out.println("-> " + usuario + " :" + m.setAsistencia(usuario, password));

        /*
         *   System.out.println("Message: "+m.getMessage());
         *
         * password = "invalid";
         * System.out.println("Pass: "+password);
         */

        // System.out.println("ID de "+usuario+" :"+m.getUser(usuario, password));

        // TODO code application logic here
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
