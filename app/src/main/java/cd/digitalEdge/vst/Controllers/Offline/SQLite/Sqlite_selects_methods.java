package cd.digitalEdge.vst.Controllers.Offline.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Offline.DataBase;

public class Sqlite_selects_methods {
    
    // TODO : GETTING ALL PHARMACIE
    /*public static ArrayList<Pharmacies> getall_pharmacies(Context context){
        ArrayList<Pharmacies> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Pharmacies column = new Pharmacies();
            Cursor c = base.query(Constants.PHARMACIES, new String[]{
                    column.id,
                    column.title,
                    column.street,
                    column.commune,
                    column.city,
                    column.phone,
                    column.email,
                    column.responsable,
                    column.latlng,
                    column.picture,
                    column.slug,
                    column.created_at,
                    column.created_by,
                    column.updating,
                    column.status
            },null, null, null, null, null);
            Log.i("Client0".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Pharmacies C = new Pharmacies();
                    C.setId(c.getString(0));
                    C.setTitle(c.getString(1));
                    C.setStreet(c.getString(2));
                    C.setCommune(c.getString(3));
                    C.setCity(c.getString(4));
                    C.setPhone(c.getString(5));
                    C.setEmail(c.getString(6));
                    C.setResponsable(c.getString(7));
                    C.setLatlng(c.getString(8));
                    C.setPicture(c.getString(9));
                    C.setSlug(c.getString(10));
                    C.setCreated_at(c.getString(11));
                    C.setCreated_by(c.getString(12));
                    C.setUpdating(c.getString(13));
                    C.setStatus(c.getString(14));
                    ret.add(C);
                }while (c.moveToNext());
                Log.i("PHARMACIE_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                Log.i("PHARMACIE_GET2".toUpperCase(), " After2" + c.getCount());
                Log.i("PHARMACIE_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            Log.i("PHARMACIE_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }*/


}