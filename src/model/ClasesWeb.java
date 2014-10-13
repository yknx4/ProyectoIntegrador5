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
public class ClasesWeb {

    public ClasesWeb(int id, String hora, String grupo, String materia, String salon, boolean asistio, boolean justifico, String fechaAsistio, String fechaJustifico, int dia, int horario, int usuario, int min,String maestro) {
        this.id = id;
        this.hora = hora;
        this.grupo = grupo;
        this.materia = materia;
        this.salon = salon;
        this.maestro = maestro;
        this.asistio = asistio;
        this.justifico = justifico;
        this.fechaAsistio = fechaAsistio;
        this.fechaJustifico = fechaJustifico;
        this.dia = dia;
        this.horario = horario;
        this.usuario = usuario;
        this.min = min;
    }
    public ClasesWeb(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isJustifico() {
        return justifico;
    }

    public void setJustifico(boolean justifico) {
        this.justifico = justifico;
    }

    public String getFechaAsistio() {
        return fechaAsistio;
    }

    public void setFechaAsistio(String fechaAsistio) {
        this.fechaAsistio = fechaAsistio;
    }

    public String getFechaJustifico() {
        return fechaJustifico;
    }

    public void setFechaJustifico(String fechaJustifico) {
        this.fechaJustifico = fechaJustifico;
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

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public boolean isLate() {
        return min>(20*60);
    }

    
    int id;
    String hora;
    String grupo;
    String materia;
    String salon;
    boolean asistio;
    boolean justifico;
    String fechaAsistio;
    String fechaJustifico;
    int dia;
    int horario;
    int usuario;
    int min;
    String maestro;

    private String format(String input){
        String d = input.toLowerCase();
        String output = d.substring(0, 1).toUpperCase() + d.substring(1);
        return output;
    }
    
    public String getMaestro() {
        
        return maestro;
    }

    public void setMaestro(String maestro) {
        this.maestro = maestro;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
    
    public String getJson(){
        String[] parameters = {hora,grupo,materia,salon,dia+"",horario+"",usuario+"",String.valueOf(asistio),String.valueOf(justifico),String.valueOf(isLate()),fechaAsistio,fechaJustifico};
        String res="{\n \"hora\": \"?\",\n \"grupo\": \"?\",\n \"materia\": \"?\",\n \"salon\": \"?\",\n \"dia\": \"?\",\n \"horario\": \"?\",\n \"usuario\": \"?\",\n \"flags\": {\n \"asistio\": ?,\n \"justifico\": ?,\n \"late\": ?\n },\n \"horas\": {\n \"asistio\": \"?\",\n \"justifico\": \"?\"\n }\n}";
        for(String p : parameters){
            res = res.replaceFirst("\\?", p);
        }
        System.out.println(res);
        return res;
    }
}
