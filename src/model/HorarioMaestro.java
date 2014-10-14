/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;


/**
 *
 * @author Yknx
 */
public class HorarioMaestro {

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "HorarioMaestro{" + "dia=" + dia + ", horario=" + horario + ", materia=" + materia + ", salon=" + salon + ", empty=" + empty + '}';
    }
     public HorarioMaestro(int dia, int horario, String materia, String salon, boolean empty, String hora,String grupo) {
        this.dia = dia;
        this.horario = horario;
        this.materia = materia;
        this.salon = salon;
        this.empty = empty;
        this.hora = hora;
        this.grupo = grupo;
        
    }

    private int dia;
    private int horario;
    private String materia;
    private String salon;
    private boolean empty;
    private String hora;
    private String grupo;

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    

    

    
}
