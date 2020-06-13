package cd.digitalEdge.vst.Controllers.Offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExecuteUpdate {
    public static long insert(Context context,ContentValues values, String table_name){
        try {
            DataBase mybase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase db = mybase.getWritableDatabase();
            long ret = db.insert(table_name,null, values);
            db.close();
            mybase.close();
            Log.i("INSERT_DONE", String.valueOf(ret));

            return 1;
        }catch (Exception e){
            e.printStackTrace();
            Log.e("INSERT_FAILED", e.toString());
            return 0;
        }

    }

    public static long update(
            Context context,
            String table_name,
            String Editcolumn,
            String value,
            String colonne_id,
            String id){
        try {
            DataBase mybase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase db = mybase.getWritableDatabase();
            //long ret = db.insertOrThrow(table_name,null, values);
            db.execSQL("UPDATE "+table_name+" SET "+Editcolumn+" = '"+value+"' WHERE "+colonne_id+" = "+id+";");
            db.close();
            mybase.close();
            Log.i("UPDATE_DONE", "PASSED");
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            Log.e("UPDATE_ERROR", e.toString());
            return 0;
        }

    }

    public static long delete(Context context,String table_name,String colonne_id, String id){
        try {
            DataBase mybase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase db = mybase.getWritableDatabase();
            //long ret = db.insertOrThrow(table_name,null, values);
            db.execSQL("DELETE FROM "+table_name+" WHERE "+colonne_id+" = "+id+";");
            db.close();
            mybase.close();
            Log.i("DELETED", "PASSED");
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            Log.e("DELETE_ERROR", e.toString());
            return 0;
        }

    }

    public static long Truncat(Context context,String table_name){
        try {
            DataBase mybase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase db = mybase.getWritableDatabase();
            //long ret = db.insertOrThrow(table_name,null, values);
            db.execSQL("DELETE FROM "+table_name+" ;");
            db.close();
            mybase.close();
            Log.i("TRUNCATED", "PASSED");
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            Log.e("TRUNCAT_ERROR", e.toString());
            return 0;
        }

    }
}
