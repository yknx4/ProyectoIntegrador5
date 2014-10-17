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

    
    public Clase(){}
    public Clase(long maestro, short dia, long salon, long horario, long grupo, long materia) {
        this.maestro = maestro;
        this.dia = dia;
        this.salon = salon;
        this.horario = horario;
        this.grupo = grupo;
        this.materia = materia;
    }
    public Clase(String maestro, String dia, String salon, String horario, String grupo, String materia) {
        this.maestro = Long.parseLong(maestro);
        this.dia = Short.parseShort(dia);
        this.salon = Long.parseLong(salon);
        this.horario = Long.parseLong(horario);
        this.grupo = Long.parseLong(grupo);
        this.materia = Long.parseLong(materia);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMaestro() {
        return maestro;
    }

    public void setMaestro(long maestro) {
        this.maestro = maestro;
    }

    public short getDia() {
        return dia;
    }

    public void setDia(short dia) {
        this.dia = dia;
    }

    public long getSalon() {
        return salon;
    }

    public void setSalon(long salon) {
        this.salon = salon;
    }

    public long getHorario() {
        return horario;
    }

    public void setHorario(long horario) {
        this.horario = horario;
    }

    public long getGrupo() {
        return grupo;
    }

    public void setGrupo(long grupo) {
        this.grupo = grupo;
    }

    public long getMateria() {
        return materia;
    }

    public void setMateria(long materia) {
        this.materia = materia;
    }
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
    public String selectValues(){
        String base = "WHERE id_maestro = "+maestro+" AND"+
                "dia = "+dia+" AND"+
                "id_Salones = "+salon+" AND"+
                "id_Horarios = "+horario+" AND"+
                "id_Grupos = "+grupo+" AND"+
                "id_Materias = "+materia;  
        System.out.println(base);
        return base;
    }
    public static Clase getClase(ResultSet rawData) throws SQLException, ParseException{
        Clase res = new Clase();
        
        
        
        return res;
    }
}
