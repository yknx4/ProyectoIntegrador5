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
        String usuario = "alc.ina@telematica.mx";
        String password = "109514";
        System.out.println("Pass: "+password);
        System.out.println("ID de "+usuario+" :"+m.getUser(usuario, password));
        System.out.println("-> "+usuario+" :"+m.setAsistencia(usuario, password,1,13));
        
        password = "invalid";
        System.out.println("Pass: "+password);
        System.out.println("ID de "+usuario+" :"+m.getUser(usuario, password));
       
        // TODO code application logic here
    }
    
}
