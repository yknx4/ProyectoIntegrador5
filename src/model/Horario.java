/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import helper.Utility;
import java.util.Date;

/**
 *
 * @author 5j
 */
public class Horario {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getInicio() {
        return inicio;
    }
    
     public String getInicio(boolean string) {
        return Utility.MySQLTimeFormatter.format(inicio);
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }
    public long id;
    public Date inicio;
    @Override
    public String toString(){
       String res = "";
       res+= "Id: "+id;
       res+= " Hora: "+inicio;
       return res;
    }
}
