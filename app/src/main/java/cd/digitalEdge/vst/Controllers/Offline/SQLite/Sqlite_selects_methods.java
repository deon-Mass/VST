package cd.digitalEdge.vst.Controllers.Offline.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Offline.DataBase;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Colors;
import cd.digitalEdge.vst.Objects.Etats;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.Tools.Constants;

public class Sqlite_selects_methods {
    
    // TODO : GETTING CURRENT USER
    public static ArrayList<Users> get_currentUser(Context context){
        ArrayList<Users> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Users column = new Users();
            Cursor c = base.query(Constants.USERS, new String[]{
                    column.id,
                    column.role_id,
                    column.name,
            },null, null, null, null, null);
            //Log.i("USER_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Users C = new Users();
                    C.setId(c.getString(0));
                    C.setRole_id(c.getString(1));
                    ret.add(C);
                }while (c.moveToNext());
                //Log.i("USER_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                //Log.i("USER_GET2".toUpperCase(), " After2" + c.getCount());
                //Log.i("USER_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            //Log.i("USER_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // TODO : GETTING ALL PRODUCT
    public static ArrayList<Articles> getall_Articles(Context context){
        ArrayList<Articles> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Articles column = new Articles();
            Cursor c = base.query(Constants.ARTICLE, new String[]{
                    column.id,
                    column.name,
                    column.slug,
                    column.price,
                    column.stock,
                    column.phone,
                    column.availability,
                    column.created_at,
                    column.status,
                    column.qnt
            },null, null, null, null, null);
            //Log.i("product_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Articles C = new Articles();
                    C.setId(c.getString(0));
                    C.setName(c.getString(1));
                    C.setSlug(c.getString(2));
                    C.setPrice(c.getString(3));
                    C.setStock(c.getString(4));
                    C.setPhone(c.getString(5));
                    C.setAvailability(c.getString(6));
                    C.setCreated_at(c.getString(7));
                    C.setStatus(c.getString(8));
                    C.setQnt(c.getString(9));
                    ret.add(C);
                }while (c.moveToNext());
                //Log.i("product_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                //Log.i("product_GET2".toUpperCase(), " After2" + c.getCount());
                //Log.i("product_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            //Log.i("product_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // TODO : GETTING ALL CATEGORIES
    public static ArrayList<Categories> getall_Categorie(Context context){
        ArrayList<Categories> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Categories column = new Categories();
            Cursor c = base.query(Constants.CATEGORIES, new String[]{
                    column.id,
                    column.name,
                    column.parent_id,
                    column.slug,
                    column.created_at
            },null, null, null, null, null);
            //Log.i("product_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Categories C = new Categories();
                    C.setId(c.getString(0));
                    C.setName(c.getString(1));
                    C.setParent_id(c.getString(2));
                    C.setSlug(c.getString(3));
                    C.setCreated_at(c.getString(4));

                    ret.add(C);
                }while (c.moveToNext());
                //Log.i("product_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                //Log.i("Categories_GET2".toUpperCase(), " After2" + c.getCount());
                //Log.i("Categories_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            //Log.i("Categories_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // TODO : GETTING ALL COLORS
    public static ArrayList<Colors> getall_Colors(Context context){
        ArrayList<Colors> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Colors column = new Colors();
            Cursor c = base.query(Constants.COLORS, new String[]{
                    column.id,
                    column.name,
                    column.codage_rvb
            },null, null, null, null, null);
            //Log.i("product_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Colors C = new Colors();
                    C.setId(c.getString(0));
                    C.setName(c.getString(1));
                    C.setCodage_rvb(c.getString(2));
                    ret.add(C);
                }while (c.moveToNext());
                //Log.i("product_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                //Log.i("Categories_GET2".toUpperCase(), " After2" + c.getCount());
                //Log.i("Categories_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            //Log.i("Categories_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // TODO : GETTING ALL ETAT
    public static ArrayList<Etats> getall_Etat(Context context){
        ArrayList<Etats> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Etats column = new Etats();
            Cursor c = base.query(Constants.ETATS, new String[]{
                    column.id,
                    column.name,
                    column.description
            },null, null, null, null, null);
            //Log.i("product_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Etats C = new Etats();
                    C.setId(c.getString(0));
                    C.setName(c.getString(1));
                    C.setDescription(c.getString(2));
                    ret.add(C);
                    //Log.e("TAG_ETAT".toUpperCase(), "COUNT" + c.getCount());
                }while (c.moveToNext());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                //Log.i("Categories_GET2".toUpperCase(), " After2" + c.getCount());
                //Log.i("Categories_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            //Log.i("Categories_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}