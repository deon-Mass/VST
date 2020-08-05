package cd.digitalEdge.vst.Controllers.Offline.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Offline.ExecuteUpdate;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.Tools.Constants;

public class Sqlite_updates_methods {
    // TODO : SQLITE INSERT Product
    public static long insert_PRODUCT(Context context, Articles p){
        ArrayList<Articles> PANIER = new ArrayList<>();
        boolean response = false;
        PANIER = Sqlite_selects_methods.getall_Articles(context);
        if ( null == PANIER || PANIER.isEmpty() ){
        }else{
            for (Articles a: PANIER) {
                Log.e("CARD_EXIST",a.getId()+"  --  "+p.getId());
                if (a.getId().equals(p.getId())) {
                    response = true;
                    break;
                }
            }
        }
        if (response == true) {
            //ExecuteUpdate.delete(context, Constants.ARTICLE, new Articles().id, p.getId());
            return 2;
        }else{
            ContentValues values = new ContentValues();
            values.put(new Articles().id, p.getId() );
            values.put(new Articles().name, p.getName() );
            values.put(new Articles().slug, p.getSlug() );
            values.put(new Articles().qnt, p.getQnt() );
            values.put(new Articles().price, p.getPrice() );
            values.put(new Articles().stock, p.getStock() );
            values.put(new Articles().phone, p.getPhone() );
            values.put(new Articles().availability, p.getAvailability() );
            values.put(new Articles().created_at, p.getCreated_at() );
            values.put(new Articles().status, p.getStatus() );
            return ExecuteUpdate.insert(context, values, Constants.ARTICLE);
        }
    }

    // TODO : SQLITE INSERT Categories
    public static long insert_CATEGORIE(Context context, Categories p){
        ArrayList<Categories> PANIER = new ArrayList<>();
        boolean response = false;
        PANIER = Sqlite_selects_methods.getall_Categorie(context);
        if ( null == PANIER || PANIER.isEmpty() ){
            Log.e("CATE_EXIST","EMPTY DATA");
        }else{
            for (Categories a: PANIER) {
                if (a.getId().equals(p.getId())) {
                    //Log.e("CARD_EXIST",a.getId()+"  --  "+p.getId());
                    response = true;
                    break;
                }
            }
        }
        if (response == true) {
            //ExecuteUpdate.delete(context, Constants.ARTICLE, new Articles().id, p.getId());
            return 2;
        }else{
            ContentValues values = new ContentValues();
            values.put(new Categories().id, p.getId() );
            values.put(new Categories().name, p.getName() );
            values.put(new Categories().parent_id, p.getParent_id() );
            values.put(new Categories().slug, p.getSlug() );
            values.put(new Categories().created_at, p.getCreated_at() );
            return ExecuteUpdate.insert(context, values, Constants.CATEGORIES);
        }
    }
    // TODO : SQLITE INSERT User
    public static long insert_USER(Context context, Users p){
        if(ExecuteUpdate.delete(context, Constants.USERS, new Users().id, p.getId()) == 0){
            return 0;
        }
        ContentValues values = new ContentValues();
        values.put(new Users().id, p.getId() );
        values.put(new Users().name, p.getName() );
        return ExecuteUpdate.insert(context, values, Constants.USERS);
    }


}
