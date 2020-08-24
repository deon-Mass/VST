package cd.digitalEdge.vst.Controllers.Offline;


import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Colors;
import cd.digitalEdge.vst.Objects.Etats;
import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.Tools.Constants;

public class Tables_queries_methods {
    public static String create_table_User() {
        Users c = new Users();
        return "create table "+ Constants.USERS +" (" +
                c.id+ " varchar(25) primary key, " +
                c.role_id+ " varchar(500),"+
                c.name+ " varchar(500),"+
                c.created_at+ " varchar(500) )";
    }
    public static String create_table_Article() {
        Articles c = new Articles();
        return "create table "+ Constants.ARTICLE +" (" +
                c.id+ " varchar(25) primary key, " +
                c.name+ " varchar(500),"+
                c.slug+ " varchar(500),"+
                c.price+ " varchar(500),"+
                c.qnt+ " varchar(500),"+
                c.stock+ " varchar(500),"+
                c.phone+ " varchar(500),"+
                c.availability+ " varchar(500),"+
                c.created_at+ " varchar(500),"+
                c.status+ " varchar(500) )";
    }

    public static String create_table_Categorie() {
        Categories c = new Categories();
        return "create table "+ Constants.CATEGORIES +" (" +
                c.id+ " varchar(25) primary key, " +
                c.name+ " varchar(500),"+
                c.slug+ " varchar(500),"+
                c.parent_id+ " varchar(500),"+
                c.created_at+ " varchar(500) )";
    }

    public static String create_table_Colors() {
        Colors c = new Colors();
        return "create table "+ Constants.COLORS +" (" +
                c.id+ " varchar(25) primary key, " +
                c.name+ " varchar(500),"+
                c.codage_rvb+ " varchar(500) )";
    }
    public static String create_table_Etat() {
        Etats c = new Etats();
        return "create table "+ Constants.ETATS +" (" +
                c.id+ " varchar(25) primary key, " +
                c.name+ " varchar(700),"+
                c.description+ " varchar(700) )";
    }

}
