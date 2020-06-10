package cd.digitalEdge.vst.Controllers.Offline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBase extends SQLiteOpenHelper {
    public static final String db_name = "clinnet.db";
    public static final int db_version = 2;

    public DataBase(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL(Tables_queries_methods.create_table_Illness_question());
        db.execSQL(Tables_queries_methods.create_table_symptoms());
        db.execSQL(Tables_queries_methods.create_table_pharmacies());
        db.execSQL(Tables_queries_methods.create_table_chat_discussion());
        db.execSQL(Tables_queries_methods.create_table_chats());*/
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str = " ;";
        String str2 = "DROP TABLE ";
        try {
            /*StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(new Symptoms().table_name);
            sb.append(str);
            db.execSQL(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(new Pharma().table_name);
            sb2.append(str);
            db.execSQL(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str2);
            sb3.append(new Chat_discussions().table_name);
            sb3.append(str);
            db.execSQL(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str2);
            sb4.append(Config_preferences.table_name);
            sb4.append(str);
            db.execSQL(sb4.toString());*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(db);
    }
}
