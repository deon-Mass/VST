package cd.digitalEdge.vst.Objects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;

import java.io.Serializable;

import cd.digitalEdge.vst.Controllers.Offline.DataBase;
import cd.digitalEdge.vst.Tools.Constants;

public class Articles implements Serializable {

    public String id                    = "id";
    public String name                  = "name";
    public String slug                  = "slug";
    public String qnt                   = "qnt";
    public String images                ="images";
    public String description           = "description";
    public String price                 = "total_cost";
    public String price2                 = "price";
    public String phone                 = "phone";
    public String availability          = "availability";
    public String status                = "status";
    public String created_at            = "created_at";
    public String stock                 = "stock";
    public String keywords              = "keywords";

    public String user_id               = "user.id";
    public String user_name             = "user.name";
    public String email                 = "user.email";

    public String cat_id                = "category.id";
    public String cat_name              = "category.name";




    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getQnt() {
        return qnt;
    }

    public void setQnt(String qnt) {
        this.qnt = qnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    //TODO : METHODS
    public static boolean if_exist_id(Context context, String id2) {
        SQLiteDatabase base;
        Exception e;
        DataBase mabase = null;
        try {
            mabase = new DataBase(context, mabase.db_name, null, 2);
            base = mabase.getReadableDatabase();

            Articles column = new Articles();
            String[] strArr = {column.id};
            if (base.query(
                    Constants.ARTICLE,
                    strArr,
                    column.id +" = '"+ id2 +"'" , null, null, null, null).getCount() < 1) {
                base.close();
                mabase.close();
                return false;
            }else{
                base.close();
                mabase.close();
                return true;
            }

        } catch (Exception e3) {
            Exception exc = e3;
            e = exc;
            e.printStackTrace();
            mabase.close();
            return false;
        }
    }

}
