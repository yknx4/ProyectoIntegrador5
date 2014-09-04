/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.SQLData;

import app.Utility;

/**
 *
 * @author 5j
 */
public class Join{
            public static Join generateJoin(int type, String tableName, String[] tables, String[] from, String[] to){
                Join mJoin = new Join();
                mJoin._type = type;
                mJoin._from = from;
                mJoin._to = to;
                mJoin._tables = tables;
                mJoin._tableName = tableName;
                
                return mJoin;
            }
            public String getString(){
                return Utility.SQLHelper.generateJoin(_type, _tableName, _tables, _from, _to);
            }
            private int _type;
            private String _tableName;
            private String[] _tables;
            private String[] _from;
            private String[] _to;
            private Join(){}
            
            
        }