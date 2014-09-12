/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import controller.AsistenciaController;
import java.sql.SQLException;

/**
 *
 * @author Yknx
 */
public class TestGetUser {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        AsistenciaController m = AsistenciaController.getInstance();
        String usuario = "cab.nia@telematica.mx";
        String password = "1091750";
        System.out.println("Pass: "+password);
        System.out.println("ID de "+usuario+" :"+m.getUser(usuario, password));
        password = "invalid";
        System.out.println("Pass: "+password);
        System.out.println("ID de "+usuario+" :"+m.getUser(usuario, password));
       
        // TODO code application logic here
    }
    
}
