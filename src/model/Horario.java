/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 *
 * @author 5j
 */
public class Horario {
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
