/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;

/**
 *
 * @author Yknx
 */
public class User {

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nombre=" + name + ", activo=" + activo + ", email=" + email + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    int id;
    String name;
    String passhash;
    int permission;
    String created;
    String modified;
    boolean activo;
    String email;
    String picture;
    
    public static final int ADMINISTRATIVE_PERSONNEL = 0b00000001;
    public static final int PROFESSOR = 0b00000010;
    public static final int SYSTEM_ADMIN = 0b00000100;
    public static final int EXTRA_FLAG = 0b00001000;
    public static final int INVALID_PERMISSION = -1;
    public static final long INVALID = -1;
    
    private static int getMask(int input){
        return ~(0b11111111 ^ input);
    }
    public static boolean hasPermission(int permission, int toLook){
        return (permission & getMask(toLook)) == toLook;
    }
    public boolean hasPermission(int toLook){
        return (permission & getMask(toLook)) == toLook;
    }
    public static Integer[] getPermissions(int permission){
        ArrayList<Integer> perns = new ArrayList<>();
        if(hasPermission(permission, ADMINISTRATIVE_PERSONNEL)) perns.add(ADMINISTRATIVE_PERSONNEL);
        if(hasPermission(permission, PROFESSOR)) perns.add(PROFESSOR);
        if(hasPermission(permission, SYSTEM_ADMIN)) perns.add(SYSTEM_ADMIN);
        if(hasPermission(permission, EXTRA_FLAG)) perns.add(EXTRA_FLAG);
        
        return  (Integer[]) perns.toArray(new Integer[perns.size()]);
    }
    public static String permissionName(int Permission){
        switch(Permission){
            case ADMINISTRATIVE_PERSONNEL:
                return "Personal Administrativo";
                case PROFESSOR:
                return "Profesor";
                    case SYSTEM_ADMIN:
                return "Administrador del Sistema";
                        case EXTRA_FLAG:
                return "EXTRA FLAG";
                            default:
                return "PERMISO INVALIDO";
        }
    }
    
}
