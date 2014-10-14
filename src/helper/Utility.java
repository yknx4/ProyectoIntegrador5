package helper;

import controller.SQLData.SQLHelper;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import model.SQLData.Join;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.database.DatabaseInstance;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yknx
 */
public class Utility {
    public static boolean isDebug = false;
    public static String SERVER_PATH="http://localhost/";
    public static String IMG_PATH="img/";
    public static String PASSWORD_SALT = "ho";
    private static MessageDigest hasher ;
     public static int fixedDay = 4;
    public static int [] fixedHorarios = {14,15};
    private final static Logger LOGGER = Logger.getLogger(Utility.class.getName());
    public static SimpleDateFormat SQLDateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static String getFormalDate(int year, int month, int day){
        
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.set(year, month, day, 0, 0, 0);
        String result = sdf.format(c.getTime());
        LOGGER.log(Level.FINER, "Requested formal date is: {0}", result);
        return result;
    }
    public static byte[] getHash(String input){
        if(hasher == null){
            try {
                hasher = MessageDigest.getInstance("SHA-512");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
                return new byte[0];
            }
        }
        
        
        
        byte[] finalhash = {};
        hasher.reset();
        try {
            hasher.update(input.getBytes("UTF-8"));
            finalhash = hasher.digest();
            LOGGER.log(Level.FINEST, "Hashed {0} into {1}", new Object[]{input, finalhash});
        } catch (UnsupportedEncodingException ex) {
            hasher.update(input.getBytes());
            LOGGER.log(Level.SEVERE,"Incorrect encoding, trying with default. Full error"+ex.toString(),ex);
        }
        return finalhash;
    }
    public static final String DB_STRING ="jdbc:mysql://yknx4.b0ne.com:3306/jfperez?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&"
                            + "user=root&password=konami1994";
//    public static final String DB_STRING ="jdbc:mysql://localhost:3306/jfperez?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&"
//                            + "user=root&password=konami1994";
    public static String removeLast(final String input){
        return input.substring(0,input.length()-1);
    }
    public static Calendar globalCalendar = Calendar.getInstance();
    public static void resetCalendar(){
        LOGGER.log(Level.CONFIG,"Global calendar resetted.");
        globalCalendar = Calendar.getInstance();
    }
    public static String arrayToCSV(final String[] values){
         String csv="";
            for(String val:values) {
                csv+=val+",";
            }
            csv= removeLast(csv);
            return csv;
    }
    public static String arrayToANDSeparator(final String[] values){
        final int ssize = values.length;
            
            String query ="";
            for(int i =0;i<ssize;i++){
                query += values;
                if(i!=ssize-1)query+=" AND ";
            }
            return query;
    }
    

    //TODO: MOVE TO UTILITY
    public static String basicQuery(String TableName, String[] projection, String[] selection, int[] selectionTypes, Join... joins){
        //get preparedStatement
        String query = "";
        query += SQLHelper.generateSelect(TableName, projection);
        query+=" ";
        query += SQLHelper.generateWhere(selection, selectionTypes);
        if(joins!=null){
        for(Join j : joins){
            query+= j.getString()+" ";
        }
        }
        //  String fjoin = Utility.SQLHelper.generateJoin(type, "t1", tables, from, to);


        


        return query;
    }

    public static int getDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int rec = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("Dia segun java: " + rec);
        rec -= 2;
        if (rec < 0) {
            rec = 7 + rec;
        }
        System.out.println("Dia segun sho: " + rec);
        return rec;
    }

    public static void forceAusent() {
        String query = "call FORCE_AUSENT();";
        try {
            DatabaseInstance.getInstance().createStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    }


