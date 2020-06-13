package cd.digitalEdge.vst.Controllers.Offline.SQLite;

import android.content.ContentValues;
import android.content.Context;

import cd.digitalEdge.vst.Controllers.Offline.ExecuteUpdate;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Objects.Projects;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.Tools.Constants;

public class Sqlite_updates_methods {
    // TODO : SQLITE INSERT Product
    public static long insert_PRODUCT(Context context, Products p){
        if (Products.if_exist_id(context, p.getId())) {
            ExecuteUpdate.delete(context, Constants.PRODUCT, new Products().id, p.getId());
        }
        ContentValues values = new ContentValues();
        values.put(new Products().id, p.getId() );
        values.put(new Products().title, p.getTitle() );
        values.put(new Products().mesure, p.getMesure() );
        values.put(new Products().price, p.getPrice() );
        values.put(new Products().price_editable, p.getPrice_editable() );
        values.put(new Products().picture, p.getPicture() );
        values.put(new Products().project_id, p.getProject_id() );
        values.put(new Products().created_by, p.getCreated_by() );
        values.put(new Products().created_at, p.getCreated_at() );
        values.put(new Products().updating, p.getUpdating() );
        values.put(new Products().status, p.getStatus() );
        return ExecuteUpdate.insert(context, values, Constants.PRODUCT);
    }
    // TODO : SQLITE INSERT User
    public static long insert_USER(Context context, Users p){
        if(ExecuteUpdate.delete(context, Constants.USERS, new Users().id, p.getId()) == 0){
            return 0;
        }
        ContentValues values = new ContentValues();
        values.put(new Users().id, p.getId() );
        values.put(new Users().name, p.getName() );
        values.put(new Users().username, p.getUsername() );
        values.put(new Users().email, p.getEmail() );
        values.put(new Users().phone, p.getPhone() );
        values.put(new Users().password, p.getPassword() );
        values.put(new Users().avatar, p.getAvatar() );
        values.put(new Users().address, p.getAddress() );
        values.put(new Users().gender, p.getGender() );
        values.put(new Users().office_id, p.getOffice_id() );
        values.put(new Users().project_ids, p.getProject_ids());
        values.put(new Users().created_by, p.getCreated_by() );
        values.put(new Users().created_at, p.getCreated_at() );
        values.put(new Users().updating, p.getUpdating() );
        values.put(new Users().status, p.getStatus() );
        return ExecuteUpdate.insert(context, values, Constants.USERS);
    }
    // TODO : SQLITE INSERT Project
    public static long insert_PROJECT(Context context, Projects p){
        if (Projects.if_exist_id(context, p.getId())) {
            ExecuteUpdate.delete(context, Constants.PROJECT, new Products().id, p.getId());
        }
        ContentValues values = new ContentValues();
        values.put(new Projects().id, p.getId() );
        values.put(new Projects().title, p.getTitle() );
        values.put(new Projects().description, p.getDescription() );
        values.put(new Projects().picture, p.getPicture() );
        values.put(new Projects().office_id, p.getOffice_id() );
        values.put(new Projects().created_by, p.getCreated_by() );
        values.put(new Projects().created_at, p.getCreated_at() );
        values.put(new Projects().updating, p.getUpdating() );
        values.put(new Projects().status, p.getStatus() );
        return ExecuteUpdate.insert(context, values, Constants.PROJECT);
    }

}
