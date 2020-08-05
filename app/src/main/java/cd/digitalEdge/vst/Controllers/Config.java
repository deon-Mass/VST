package cd.digitalEdge.vst.Controllers;

public class Config {
    /**
     * Application Authentication
     */
    public static final String auth = "VTS@DeonMass.digitaledge";

    /**
     * ONLINE : API LINKS
     */
    public static String ROOT = "https://lesupreme.shop/api/";
    public static String ROOT_img = "https://lesupreme.shop/storage/";
    public static String SERVER_PATH = "https://lesupreme.shop/api/";

    // TODO : PRODUITs
    //récupère tous les produist link: https://lesupreme.shop/api/products
    public static String GET_PRODUCTS = ROOT+"products";
    // recupère un prdoduit  https://domaine.com/api/products/show/{id}
    public static String GET_PRODUCT = GET_PRODUCTS.concat("/show/");
    // recupère la liste des catégories des produits https://lesupreme.shop/api/products/categories
    public static String GET_PRODUCT_CAT = GET_PRODUCTS.concat("/categories");

    // recherche d'un produit https://lesupreme.shop/api/products/search avec un paramètre en GET ayant le nom de query
    public static String GET_PRODUCT_SEARCH = GET_PRODUCTS.concat("/search");
    // Ajoute un avis à un produits https://domaine.com/api/products/reviews/store
    // avec 5 paramètres POST
    // 1. body (avis) en texte,
    // 2. user_id (id de l'utisateur qui commente en entier)
    // 3. property_id (id de l'article dont on commente en entier)
    // 4. recommend => [ Yes , No]
    // 5. La note, en entier valeur comprise entre 1 et 5
    // si tout se passe bien, ça retour success en json
    // S'il y a une erreur, un json contenant les messages d'erreurs
    public static String GET_PRODUCT_NOTE = GET_PRODUCTS.concat("/reviews/store");

    // TODO : USER
    // https://lesupreme.shop/api/user
    public static String GET_USER = ROOT+"user";
    // TODO : BLOG
    // récupère tous les articles du blog link: https://domaine.com/api/posts =>{ posts: [{id: 1}, {id: 2}]}
    public static String GET_POST = ROOT.concat("posts");
    // recupère un article et ses commentaires https://domaine.com/api/posts/show/{id} =>{ post: [{id: 1, comments: [ {id: 2},{id: 1} ]}, {id: 2}]}
    public static String GET_POST_COMMENTS = GET_POST.concat("/show/");
    // recupère la liste des catégories du blog https://domaine.com/api/posts/categories
    public static String GET_CAT = GET_POST.concat("/categories");





    /**

     /**
     * OFFLINE
     */
    /**
     * BASE DE DONNEES LOCAL CONFIGURATION
     */
    public static final String db_name= "zuajob2.db";


    /**
     * DATA LIMIT FOR QUERIES
     */
    public static int DATA_LIMIT = 20;

}
