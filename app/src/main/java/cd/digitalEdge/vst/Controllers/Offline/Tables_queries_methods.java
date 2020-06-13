package cd.digitalEdge.vst.Controllers.Offline;


import cd.digitalEdge.vst.Objects.Products;
import cd.digitalEdge.vst.Objects.Projects;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.Tools.Constants;

public class Tables_queries_methods {
    public static String create_table_User() {
        Users c = new Users();
        return "create table "+ Constants.USERS +" (" +
                c.id+ " varchar(25) primary key, " +
                c.role_id+ " varchar(500),"+
                c.name+ " varchar(500),"+
                c.username+ " varchar(500),"+
                c.email+ " varchar(500),"+
                c.phone+ " varchar(500),"+
                c.password+ " varchar(500),"+
                c.avatar+ " varchar(500),"+
                c.address+ " varchar(500),"+
                c.gender+ " varchar(500),"+
                c.office_id+ " varchar(500),"+
                c.project_ids+ " varchar(500),"+
                c.created_at+ " varchar(500),"+
                c.created_by+ " varchar(500),"+
                c.updating+ " varchar(500),"+
                c.status+ " varchar(500) )";
    }
    public static String create_table_Products() {
        Products c = new Products();
        return "create table "+ Constants.PRODUCT +" (" +
                c.id+ " varchar(25) primary key, " +
                c.title+ " varchar(500),"+
                c.mesure+ " varchar(500),"+
                c.price+ " varchar(500),"+
                c.price_editable+ " varchar(500),"+
                c.picture+ " varchar(500),"+
                c.project_id+ " varchar(500),"+
                c.created_at+ " varchar(500),"+
                c.created_by+ " varchar(500),"+
                c.updating+ " varchar(500),"+
                c.status+ " varchar(500) )";
    }

    public static String create_table_Projects() {
        Projects c = new Projects();
        return "create table "+ Constants.PRODUCT +" (" +
                c.id+ " varchar(25) primary key, " +
                c.title+ " varchar(500),"+
                c.description+ " varchar(500),"+
                c.picture+ " varchar(500),"+
                c.office_id+ " varchar(500),"+
                c.created_at+ " varchar(500),"+
                c.created_by+ " varchar(500),"+
                c.updating+ " varchar(500),"+
                c.status+ " varchar(500) )";
    }

}
