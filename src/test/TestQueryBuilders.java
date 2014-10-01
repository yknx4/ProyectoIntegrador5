
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package test;

//~--- non-JDK imports --------------------------------------------------------

import controller.SQLData.SQLHelper;
import helper.Utility;

import controller.SQLData.SQLHelper;

import model.database.DataContract;
import model.database.DataContract.AsistenciaEntry;

import model.SQLData.Join;

/**
 *
 * @author Yknx
 */
public class TestQueryBuilders {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here

        /*
         * SELECT * FROM t1 LEFT JOIN (t2, t3, t4)
         *       ON (t2.a=t1.a AND t3.b=t1.b AND t4.c=t1.c)
         */
        String[] tables = { "t1", "t2", "t3", "t4" };
        String[] from   = { "t2.a", "t3.b", "t4.c" };
        String[] to     = { "t1.a", "t1.b", "t1.c" };
        int[]    types  = { 0, 1, 2 };
        int      type   = SQLHelper.TYPE_INNER_JOIN;
        String   fjoin  = SQLHelper.generateJoin(type, "t1", tables, from, to);
        Join     j1, j2;

        j1 = Join.generateJoin(type, fjoin, tables, from, to);
        j2 = Join.generateJoin(SQLHelper.TYPE_LEFT_JOIN, fjoin, tables, from, to);

        String   fselect = SQLHelper.generateSelect("t1", null);
        String   fwhere  = SQLHelper.generateWhere(to, types);
        String[] insCOl  = { AsistenciaEntry.COLUMN_ID_CLASE, AsistenciaEntry.COLUMN_ID_USUARIO,
                             AsistenciaEntry.COLUMN_FECHA };
        String finsert = SQLHelper.generateInsert(AsistenciaEntry.TABLE_NAME, insCOl,
                             SQLHelper.INSERT_MODE_IGNORE);

        System.out.println(fselect);
        System.out.println(fwhere);
        System.out.println(fjoin);
        System.out.println(fjoin.equals("INNER JOIN (t2,t3,t4) ON (t2.a=t1.a AND t3.b=t1.b AND t4.c=t1.c)"));
        System.out.println(Utility.basicQuery(tables[0], null, to, types, j1, j2));
        System.out.println(finsert);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
