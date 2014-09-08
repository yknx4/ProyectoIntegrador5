/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data;

import java.util.Date;

/**
 *
 * @author Yknx
 */
public class Maestro {
    public long id;
    public String nombre;
    public String passhash;
    public int permission;
    public Date created;
    public Date modified;
    public int activo;
    public String email;
    public String picture_uri;
    @Override
    public String toString(){
        return "Id: "+id+
              " Nombre: "+nombre+
              " Picture: "+picture_uri;
    }
}
