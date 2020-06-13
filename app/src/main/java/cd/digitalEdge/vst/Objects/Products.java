package cd.digitalEdge.vst.Objects;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cd.digitalEdge.vst.Controllers.Offline.DataBase;
import cd.digitalEdge.vst.Tools.Constants;

public class Products {
    public String id                = "id";
    public String title             = "title";
    public String mesure            = "mesure";
    public String price             = "price";
    public String price_editable    = "price_editable";
    public String picture           = "picture";
    public String project_id        = "project_id";
    public String project_name      = "project_name";
    public String created_at        = "created_at";
    public String created_by        = "created_by";
    public String updating          = "updating";
    public String status            = "status";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMesure() {
        return mesure;
    }

    public void setMesure(String mesure) {
        this.mesure = mesure;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_editable() {
        return price_editable;
    }

    public void setPrice_editable(String price_editable) {
        this.price_editable = price_editable;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdating() {
        return updating;
    }

    public void setUpdating(String updating) {
        this.updating = updating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




    public static boolean if_exist_id(Context context, String id2) {
        SQLiteDatabase base;
        Exception e;
        DataBase mabase = null;
        try {
            mabase = new DataBase(context, mabase.db_name, null, 2);
            base = mabase.getReadableDatabase();
            try {
                Products column = new Products();
                String str = Constants.PRODUCT;
                String[] strArr = {column.id};
                StringBuilder sb = new StringBuilder();
                sb.append(column.id);
                sb.append(" = ");
                sb.append(id2);

                if (base.query(str, strArr, sb.toString(), null, null, null, null).getCount() < 1) {
                    base.close();
                    mabase.close();
                    return false;
                }
                base.close();
                mabase.close();
                return true;
            } catch (Exception e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    base.close();
                    mabase.close();
                    return false;
                } catch (Throwable th) {
                    th = th;
                    base.close();
                    mabase.close();
                    throw th;
                }
            }
        } catch (Exception e3) {
            Exception exc = e3;
            base = null;
            e = exc;
            e.printStackTrace();
            base.close();
            mabase.close();
            return false;
        } catch (Throwable th2) {
            base = null;
            base.close();
            mabase.close();
            return false;
        }
    }

    public static boolean if_exist(Context context, Products s) {
        String str = " = '";
        DataBase mabase = null;
        SQLiteDatabase base = null;
        try {
            mabase = new DataBase(context, mabase.db_name, null, 2);
            SQLiteDatabase base2 = mabase.getReadableDatabase();
            try {
                Products column = new Products();
                String str2 = Constants.PRODUCT;
                String[] strArr = {column.id};
                StringBuilder sb = new StringBuilder();
                sb.append(column.id);
                sb.append(str);
                sb.append(s.getId());
                sb.append("' AND ");
                sb.append(column.title);
                sb.append(str);
                sb.append(s.getTitle().replaceAll("'", ""));
                sb.append("' ");
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(column.id);
                sb3.append(" ASC");
                if (base2.query(str2, strArr, sb2, null, null, null, sb3.toString()).getCount() < 1) {
                    base2.close();
                    mabase.close();
                    return false;
                }
                base2.close();
                mabase.close();
                return true;
            } catch (Exception e) {
                e = e;
                base = base2;
                try {
                    e.printStackTrace();
                    base.close();
                    mabase.close();
                    return false;
                } catch (Throwable th) {
                    th = th;
                    base2 = base;
                    base2.close();
                    mabase.close();
                    return false;
                }
            } catch (Throwable th2) {

                base2.close();
                mabase.close();
                return  false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            base.close();
            mabase.close();
            return false;
        }
    }



}
