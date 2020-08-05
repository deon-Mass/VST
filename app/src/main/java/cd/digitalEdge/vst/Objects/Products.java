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




}
