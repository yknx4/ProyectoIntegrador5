/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Logger;

/**
 *
 * @author Yknx
 */
public class Clase {
    private final static Logger LOGGER = Logger.getLogger(Clase.class.getName());
    public long id;
    public long maestro;
    public short dia;
    public long salon;
    public long horario;
    public long grupo;
    public long materia;
    public String print(){
        String base = "INSERT INTO `Clases` (`id_maestro`,`dia`,`id_Salones`,`id_Horarios`,`id_Grupos`,`id_Materias`) VALUES ("+maestro+","+dia+","+salon+","+horario+","+grupo+","+materia+");";
        System.out.println(base);
        return base;
    }
    public static Clase getClase(ResultSet rawData) throws SQLException, ParseException{
        Clase res = new Clase();
        
        
        
        return res;
    }
}
