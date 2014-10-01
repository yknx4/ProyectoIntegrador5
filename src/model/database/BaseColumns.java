/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model.database;


/**
 *
 * @author 5j
 */
public interface BaseColumns {
    public static final String _ID = "id";
    public abstract String getQuery();
    public abstract String getInsert();
    public abstract String getDelete();
    public abstract String getUpdate();
}
    
