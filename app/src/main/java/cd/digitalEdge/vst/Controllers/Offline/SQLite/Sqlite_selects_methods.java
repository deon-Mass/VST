package cd.digitalEdge.vst.Controllers.Offline.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Offline.DataBase;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Objects.Projects;
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
                    column.username,
                    column.email,
                    column.phone,
                    column.password,
                    column.avatar,
                    column.address,
                    column.gender,
                    column.office_id,
                    column.project_ids,
                    column.created_at,
                    column.created_by,
                    column.updating,
                    column.status
            },null, null, null, null, null);
            Log.i("USER_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Users C = new Users();
                    C.setId(c.getString(0));
                    C.setRole_id(c.getString(1));
                    C.setName(c.getString(2));
                    C.setUsername(c.getString(3));
                    C.setEmail(c.getString(4));
                    C.setPhone(c.getString(5));
                    C.setPassword(c.getString(6));
                    C.setAvatar(c.getString(7));
                    C.setAddress(c.getString(8));
                    C.setGender(c.getString(9));
                    C.setOffice_id(c.getString(10));
                    C.setProject_ids(c.getString(11));
                    C.setCreated_at(c.getString(12));
                    C.setCreated_by(c.getString(13));
                    C.setUpdating(c.getString(14));
                    C.setStatus(c.getString(15));
                    ret.add(C);
                }while (c.moveToNext());
                Log.i("USER_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                Log.i("USER_GET2".toUpperCase(), " After2" + c.getCount());
                Log.i("USER_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            Log.i("USER_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // TODO : GETTING ALL PRODUCT
    public static ArrayList<Products> getall_Products(Context context){
        ArrayList<Products> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Products column = new Products();
            Cursor c = base.query(Constants.PRODUCT, new String[]{
                    column.id,
                    column.title,
                    column.mesure,
                    column.price,
                    column.price_editable,
                    column.picture,
                    column.project_id,
                    column.created_at,
                    column.created_by,
                    column.updating,
                    column.status
            },null, null, null, null, null);
            Log.i("product_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Products C = new Products();
                    C.setId(c.getString(0));
                    C.setTitle(c.getString(1));
                    C.setMesure(c.getString(2));
                    C.setPrice(c.getString(3));
                    C.setPrice_editable(c.getString(4));
                    C.setPicture(c.getString(5));
                    C.setProject_id(c.getString(6));
                    C.setCreated_at(c.getString(7));
                    C.setCreated_by(c.getString(8));
                    C.setUpdating(c.getString(9));
                    C.setStatus(c.getString(10));
                    ret.add(C);
                }while (c.moveToNext());
                Log.i("product_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                Log.i("product_GET2".toUpperCase(), " After2" + c.getCount());
                Log.i("product_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            Log.i("product_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // TODO : GETTING ALL PROJECT
    public static ArrayList<Projects> getall_Projects(Context context){
        ArrayList<Projects> ret = new ArrayList<>();
        try{
            DataBase mabase = new DataBase(context, DataBase.db_name, null, DataBase.db_version);
            SQLiteDatabase base = mabase.getReadableDatabase();
            Projects column = new Projects();
            Cursor c = base.query(Constants.PROJECT, new String[]{
                    column.id,
                    column.title,
                    column.description,
                    column.picture,
                    column.office_id,
                    column.created_at,
                    column.created_by,
                    column.updating,
                    column.status
            },null, null, null, null, null);
            Log.i("product_GET".toUpperCase(), " Before" + c.getCount());
            if(c.moveToFirst()){
                do {
                    Projects C = new Projects();
                    C.setId(c.getString(0));
                    C.setTitle(c.getString(1));
                    C.setDescription(c.getString(2));
                    C.setPicture(c.getString(3));
                    C.setOffice_id(c.getString(4));
                    C.setCreated_at(c.getString(5));
                    C.setCreated_by(c.getString(6));
                    C.setUpdating(c.getString(7));
                    C.setStatus(c.getString(8));
                    ret.add(C);
                }while (c.moveToNext());
                Log.i("product_GET".toUpperCase(), " After " + c.getCount());
                base.close();
                mabase.close();
                return ret;
            }
            else {
                Log.i("product_GET2".toUpperCase(), " After2" + c.getCount());
                Log.i("product_GET2".toUpperCase(), " no data");
                base.close();
                mabase.close();
                return null;
            }
        }catch (Exception e){
            Log.i("product_GET_ERROR".toUpperCase(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}