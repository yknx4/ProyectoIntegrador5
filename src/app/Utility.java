package app;

import data.SQLData.Join;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;



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
    //public static final String DB_STRING ="jdbc:mysql://yknx4.b0ne.com:3306/jfperez?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&"
//                            + "user=root&password=konami1994";
    public static final String DB_STRING ="jdbc:mysql://localhost:3306/jfperez?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8&"
                            + "user=root&password=konami1994";
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
    
    public static class SQLHelper{
        public static final int TYPE_INNER_JOIN = 0;
        public static final int TYPE_LEFT_JOIN = 1;
        public static final int TYPE_OUTER_JOIN = 2;
        public static final int TYPE_RIGHT_JOIN = 3;
        
        public static final int WHERE_TYPE_EQUAL = 0;
        public static final int WHERE_TYPE_GREATER = 1;
        public static final int WHERE_TYPE_LESSER = 2;
        public static final int WHERE_TYPE_GREATER_OR_EQUAL = 3;
        public static final int WHERE_TYPE_LESSER_OR_EQUAL = 4;
        
        
        
        
        private static String getWhereType(int type){
            String query;
            switch(type){
                case WHERE_TYPE_GREATER:
                    query=">";
                    break;
                 case WHERE_TYPE_LESSER:
                    query="<";
                    break;
                     case WHERE_TYPE_GREATER_OR_EQUAL:
                    query=">=";
                    break;
                         case WHERE_TYPE_LESSER_OR_EQUAL:
                    query="<=";
                    break;
                             
                
                default:
                    query = "=";
            }
            
            
            return query;
        }
        private static String getJoinType(final int type){
            String sType;
           
            switch(type){
                case TYPE_INNER_JOIN:
                    sType = "INNER";
                    break;
                case TYPE_LEFT_JOIN:
                    sType = "LEFT";
                    break;
                case TYPE_OUTER_JOIN:
                    sType = "OUTER";
                    break;
                case TYPE_RIGHT_JOIN:
                    sType = "RIGHT";
                    break;
                default:
                    sType="INNER";
            }
            return sType;
        }
        
        public static String generateSelect(String tableName, String[] columns){
            String query = "SELECT "; 
            if(columns==null || columns.length==0) query +="*";
            else{
                //query+="("+arrayToCSV(columns)+")";
                query+=""+arrayToCSV(columns)+"";
            }
            query+=" FROM "+tableName+" ";
            return query;
        }
        //INSERT INTO person (first_name, last_name) VALUES ('John', 'Doe');
        public static final int INSERT_MODE_DEFAULT = 0;
        public static final int INSERT_MODE_IGNORE = 1;
        public static final int INSERT_MODE_REPLACE = 2;
        public static String generateInsert (String tableName, String[] columns){
            return generateInsert (tableName, columns, INSERT_MODE_DEFAULT);
        }
        public static String generateInsert (String tableName, String[] columns, int duplicateMode){
            String query;
            switch (duplicateMode){
                case INSERT_MODE_IGNORE:
                    query = "INSERT IGNORE INTO "+tableName+" "; 
                   break;
                case INSERT_MODE_REPLACE:
                    query = "REPLACE INTO "+tableName+" ";
                    break;
                default:
                    query = "INSERT INTO "+tableName+" "; 
                    break;
            }
            
            if(columns==null || columns.length==0) return "ERROR";
            else{               
                query+="("+arrayToCSV(columns)+")";
            }
            query+=" VALUES ("+arrayToCSV(filler(columns.length))+")";
  
            return query;
        }
        public static String generateWhere(String column, int type) {
            return generateWhere(new String[]{column},new int[]{type});
        }
        public static String generateWhere(String[] columns, int type) {
            int[] types = new int[columns.length];
            for(int i=0;i<types.length;i++)
                types[i] = type;
            
            return generateWhere(columns,types);
        }
        public static String generateWhere(String[] columns, int[] type) {
            final int ssize = columns.length;
            if(type.length!=ssize){
                return "";
            }
            String query ="WHERE ";
            for(int i =0;i<ssize;i++){
                query += columns[i] +" "+ getWhereType(type[i])+" ? ";
                if(i!=ssize-1)query+=" AND ";
            }
            return query;
        }
        
        public static String generateJoin(int type, String tableName, String[] tables, String[] from, String[] to){
             final int nfinal = from.length;
            String query="";
            
            /*SELECT * FROM t1 LEFT JOIN (t2, t3, t4)
                 ON (t2.a=t1.a AND t3.b=t1.b AND t4.c=t1.c)*/
            query+=getJoinType(type)+" JOIN (";
            for(String table:tables) {
                if(table.equals(tableName)) continue;
                query+=table+",";
            }
            query = removeLast(query);
            query+=") ON (";
            for(int i =0;i<nfinal;i++){
                query+=from[i]+"="+to[i];
                if(i!=nfinal-1)query+=" AND ";
            }
            query+=")";
            
            return query;   
        }

        private static String[] filler(int length) {
            String[] res = new String[length];
            for(int i = 0;i<res.length;i++){
                res[i] = "?";
            }
            return res;
        }

        
    }

    //TODO: MOVE TO UTILITY
    public static String basicQuery(String TableName, String[] projection, String[] selection, int[] selectionTypes, Join... joins){
        //get preparedStatement
        String query = "";
        query += Utility.SQLHelper.generateSelect(TableName, projection);
        query+=" ";
        query += Utility.SQLHelper.generateWhere(selection, selectionTypes);
        if(joins!=null){
        for(Join j : joins){
            query+= j.getString()+" ";
        }
        }
        //  String fjoin = Utility.SQLHelper.generateJoin(type, "t1", tables, from, to);


        


        return query;
    }
    
    
    
    }


