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
public class Grupo {

    public int getId() {
        return id;
    }

    public int getSemestre() {
        return semestre;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getFull() {
        return full;
    }

    public boolean isHelper() {
        return helper;
    }

    public Grupo(int id, int semestre, String grupo) {
        this.id = id;
        this.semestre = semestre;
        this.grupo = grupo;
        this.full = semestre+grupo;
        helper = false;
    }
    public Grupo(int id,String helper) {
        this.id = id;
        this.full = helper;
        this.helper = true;
        semestre = -1;
        grupo = null;
    }
    final int id;
    final int semestre;
    final String grupo;
    final String full;
    final boolean helper;
}
