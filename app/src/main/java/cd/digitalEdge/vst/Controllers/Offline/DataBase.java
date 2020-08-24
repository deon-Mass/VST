package cd.digitalEdge.vst.Controllers.Offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import cd.digitalEdge.vst.Tools.Constants;


public class DataBase extends SQLiteOpenHelper {
    public static final String db_name = "Shupreme.db";
    public static final int db_version = 3;

    public DataBase(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Tables_queries_methods.create_table_Article());
        db.execSQL(Tables_queries_methods.create_table_Categorie());
        db.execSQL(Tables_queries_methods.create_table_Colors());
        db.execSQL(Tables_queries_methods.create_table_Etat());
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str2 = "DROP TABLE ";
        try {
            db.execSQL(str2 + Constants.ARTICLE +" ;");
            db.execSQL(str2 + Constants.CATEGORIES +" ;");
            db.execSQL(str2 + Constants.COLORS +" ;");
            db.execSQL(str2 + Constants.ETATS +" ;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(db);
    }
}
