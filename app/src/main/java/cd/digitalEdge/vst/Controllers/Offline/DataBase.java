package cd.digitalEdge.vst.Controllers.Offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import cd.digitalEdge.vst.Tools.Constants;


public class DataBase extends SQLiteOpenHelper {
    public static final String db_name = "VST.db";
    public static final int db_version = 2;

    public DataBase(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tables_queries_methods.create_table_Products());
        db.execSQL(Tables_queries_methods.create_table_User());
        db.execSQL(Tables_queries_methods.create_table_Projects());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str2 = "DROP TABLE ";
        try {
            db.execSQL(str2 + Constants.PRODUCT +" ;");
            db.execSQL(str2 + Constants.USERS +" ;");
            db.execSQL(str2 + Constants.PROJECT +" ;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(db);
    }
}
