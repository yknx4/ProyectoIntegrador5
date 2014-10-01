/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.SQLData;

import helper.Utility;

/**
 *
 * @author Yknx
 */
public class SQLHelper {
    public static final int TYPE_INNER_JOIN = 0;
    public static final int TYPE_LEFT_JOIN = 1;
    public static final int TYPE_OUTER_JOIN = 2;
    public static final int TYPE_RIGHT_JOIN = 3;
    public static final int WHERE_TYPE_EQUAL = 0;
    public static final int WHERE_TYPE_GREATER = 1;
    public static final int WHERE_TYPE_LESSER = 2;
    public static final int WHERE_TYPE_GREATER_OR_EQUAL = 3;
    public static final int WHERE_TYPE_LESSER_OR_EQUAL = 4;

    private static String getWhereType(int type) {
        String query;
        switch (type) {
            case WHERE_TYPE_GREATER:
                query = ">";
                break;
            case WHERE_TYPE_LESSER:
                query = "<";
                break;
            case WHERE_TYPE_GREATER_OR_EQUAL:
                query = ">=";
                break;
            case WHERE_TYPE_LESSER_OR_EQUAL:
                query = "<=";
                break;
            default:
                query = "=";
        }
        return query;
    }

    private static String getJoinType(final int type) {
        String sType;
        switch (type) {
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
                sType = "INNER";
        }
        return sType;
    }

    public static String generateSelect(String tableName, String[] columns) {
        String query = "SELECT ";
        if (columns == null || columns.length == 0) {
            query += "*";
        } else {
            //query+="("+arrayToCSV(columns)+")";
            query += "" + Utility.arrayToCSV(columns) + "";
        }
        query += " FROM " + tableName + " ";
        return query;
    }
    //INSERT INTO person (first_name, last_name) VALUES ('John', 'Doe');
    public static final int INSERT_MODE_DEFAULT = 0;
    public static final int INSERT_MODE_IGNORE = 1;
    public static final int INSERT_MODE_REPLACE = 2;

    public static String generateInsert(String tableName, String[] columns) {
        return generateInsert(tableName, columns, INSERT_MODE_DEFAULT);
    }

    public static String generateInsert(String tableName, String[] columns, int duplicateMode) {
        String query;
        switch (duplicateMode) {
            case INSERT_MODE_IGNORE:
                query = "INSERT IGNORE INTO " + tableName + " ";
                break;
            case INSERT_MODE_REPLACE:
                query = "REPLACE INTO " + tableName + " ";
                break;
            default:
                query = "INSERT INTO " + tableName + " ";
                break;
        }
        if (columns == null || columns.length == 0) {
            return "ERROR";
        } else {
            query += "(" + Utility.arrayToCSV(columns) + ")";
        }
        query += " VALUES (" + Utility.arrayToCSV(filler(columns.length)) + ")";
        return query;
    }

    public static String generateWhere(String column, int type) {
        return generateWhere(new String[]{column}, new int[]{type});
    }

    public static String generateWhere(String[] columns, int type) {
        int[] types = new int[columns.length];
        for (int i = 0; i < types.length; i++) {
            types[i] = type;
        }
        return generateWhere(columns, types);
    }

    public static String generateWhere(String[] columns, int[] type) {
        final int ssize = columns.length;
        if (type.length != ssize) {
            return "";
        }
        String query = "WHERE ";
        for (int i = 0; i < ssize; i++) {
            query += columns[i] + " " + getWhereType(type[i]) + " ? ";
            if (i != ssize - 1) {
                query += " AND ";
            }
        }
        return query;
    }

    public static String generateJoin(int type, String tableName, String[] tables, String[] from, String[] to) {
        final int nfinal = from.length;
        String query = "";
        /*SELECT * FROM t1 LEFT JOIN (t2, t3, t4)
        ON (t2.a=t1.a AND t3.b=t1.b AND t4.c=t1.c)*/
        query += getJoinType(type) + " JOIN (";
        for (String table : tables) {
            if (table.equals(tableName)) {
                continue;
            }
            query += table + ",";
        }
        query = Utility.removeLast(query);
        query += ") ON (";
        for (int i = 0; i < nfinal; i++) {
            query += from[i] + "=" + to[i];
            if (i != nfinal - 1) {
                query += " AND ";
            }
        }
        query += ")";
        return query;
    }

    private static String[] filler(int length) {
        String[] res = new String[length];
        for (int i = 0; i < res.length; i++) {
            res[i] = "?";
        }
        return res;
    }
    
}
