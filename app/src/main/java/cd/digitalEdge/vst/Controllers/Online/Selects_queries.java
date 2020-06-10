package cd.digitalEdge.vst.Controllers.Online;

public class Selects_queries {
    public static String auth(String phone, String passe) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `users` WHERE `phone` LIKE '%");
        sb.append(phone);
        sb.append("%' AND `password`='");
        sb.append(passe);
        sb.append("' AND status = 1 ");
        return sb.toString();
    }

    public static String get_discussion(String myId, String To_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `chats` WHERE  `from_id`= '");
        sb.append(myId);
        String str = "' AND `to_id`='";
        sb.append(str);
        sb.append(To_id);
        sb.append("' OR        `from_id`= '");
        sb.append(To_id);
        sb.append(str);
        sb.append(myId);
        sb.append("'  AND status = 1  ORDER BY `chats`.`id` ASC");
        return sb.toString();
    }

    public static String get_My_discussions(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `chats` WHERE  `from_id`= '");
        sb.append(myId);
        sb.append("' OR `to_id`='");
        sb.append(myId);
        sb.append("'  AND status = 1  ORDER BY `chats`.`id` ASC");
        return sb.toString();
    }

    public static String get_discussion_list(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT `chats`.`id` as idd ,`from_id` as f_id, `firstname`, `name`, `middlename`, `avatar` as avatar,  (SELECT `message` FROM `chats` WHERE `to_id` = f_id AND `from_id`= '");
        sb.append(myId);
        String str = "' OR `to_id` = '";
        sb.append(str);
        sb.append(myId);
        sb.append("' AND `from_id`= f_id ORDER BY `created_at` DESC LIMIT 1) as msg,  (SELECT `created_at` FROM `chats` WHERE `to_id` = f_id AND `from_id`= '");
        sb.append(myId);
        sb.append(str);
        sb.append(myId);
        sb.append("' AND `from_id`= f_id ORDER BY `created_at` DESC LIMIT 1) as cr  FROM `chats`, `users`  WHERE `chats`.from_id = `users`.`id` AND `to_id` = '");
        sb.append(myId);
        sb.append("' AND `chats`.status = 1 GROUP BY `from_id` ORDER BY cr DESC");
        return sb.toString();
    }

    public static String get_discussion_listv(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT `from_id`, `firstname`, `name`, `middlename`, `avatar` as avatar,`message`, `chats`.`created_at` as cr FROM `chats`, `users` WHERE `chats`.from_id = `users`.`id` AND `to_id` = '");
        sb.append(myId);
        sb.append("' AND `chats`.status = 1 GROUP BY `from_id` ORDER BY `chats`.created_at DESC  ");
        return sb.toString();
    }

    public static String get_pays() {
        return "SELECT `designation` FROM `pays_atteint`";
    }

    public static String get_pharmacie() {
        return "SELECT * FROM `pharmacies` WHERE status = 1";
    }

    public static String get_unnotified_msg(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Count(chats.id) as countM, `chats`.`id` as chat_id, `to_id`, `from_id`, `message`, `notified`, `firstname`, `middlename`, `name`  FROM `chats`,`users`  WHERE `chats`.`from_id` = `users`.`id` AND `to_id` = '");
        sb.append(myId);
        sb.append("' AND `chats`.status = 1 AND `notified`= 0 ");
        return sb.toString();
    }

    public static String get_unred_msg(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Count(chats.id) as countM, `chats`.`id` as chat_id, `to_id`, `from_id`, `message`, `notified`, `firstname`, `middlename`, `name`  FROM `chats`,`users`  WHERE `chats`.`from_id` = `users`.`id` AND `to_id` = '");
        sb.append(myId);
        sb.append("' AND `read_status` = '0'  AND `chats`.status = 1 ");
        return sb.toString();
    }

    public static String get_covid_QSTs() {
        return "SELECT * FROM `illness_questions` WHERE `illness_ids` = '1'  AND status = 1 ";
    }

    public static String get_urgence_QSTs() {
        return "SELECT * FROM `illness_questions` WHERE `category_age` = 'URG'  AND status = 1 ";
    }

    public static String get_Symptomes() {
        return "SELECT * FROM `symptoms`  WHERE status = 1";
    }

    public static String get_my_childs(String myid) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `users` WHERE `parent_id` = '");
        sb.append(myid);
        sb.append("'  AND status = 1 ");
        return sb.toString();
    }

    public static String add_Lastcomplaints_query(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `complaints` WHERE `created_by` = '");
        sb.append(id);
        sb.append("'  AND status = 1 ORDER BY `created_at` DESC LIMIT 1 ");
        return sb.toString();
    }

    public static String get_Illness_sqtions() {
        return "SELECT * FROM `illness_questions` WHERE `status` = 1";
    }

    public static String get_Antecedent_sqtions(int Age, String sexe) {
        if (Age < 18) {
            return "SELECT * FROM `illness_questions` WHERE category_age = 'MIN'  OR category_age = 'ALL'  AND status = 1 ";
        }
        if (sexe.equals("M")) {
            return "SELECT * FROM `illness_questions` WHERE category_age = 'MAJ' AND gender = 'ALL'  AND status = 1 ";
        }
        return "SELECT * FROM `illness_questions` WHERE category_age = 'MAJ' AND gender = 'ALL' OR gender = 'F'  AND status = 1 ";
    }

    public static String get_Antecedent_sqtions2() {
        return "SELECT * FROM `illness_questions` WHERE gender = 'F'  OR gender = 'ALL'  AND status = 1 ";
    }

    public static String get_mes_Antecedent_sqtions(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `backgrounds` WHERE `user_id` ='");
        sb.append(myId);
        sb.append("' AND status = 1 ");
        return sb.toString();
    }

    public static String get_my_complaints(String myid) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `complaints` WHERE NOT `type_id` = '1' AND `created_by`= '");
        sb.append(myid);
        sb.append("'  AND status = 1  ORDER BY `created_at` DESC");
        return sb.toString();
    }

    public static String get_my_urgences(String myid) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `complaints` WHERE`type_id` = '1' AND `created_by`= '");
        sb.append(myid);
        sb.append("'  AND status = 1 ORDER BY `created_at` DESC");
        return sb.toString();
    }

    public static String get_my_exams(String ID_COMPLAINT, String myid) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT *, exam_passed.id as idd, `exam_id`, exams.title as exam__title,`result_by`,`result_at`,`comment`,`result`,`exam_passed`.`created_at` as exam_created_at, exam_categories.id as exam_categorie_id ,exam_categories.title as exam_categorie_title FROM `exam_passed` ,`exams`, `exam_categories`,`users` WHERE `exam_passed`.`exam_id`= `exams`.id AND `exams`.category_id = `exam_categories`.`id` AND `exam_passed`.`created_by`= `users`.id AND        `patient_id` = '");
        sb.append(myid);
        sb.append("' AND complaint_id = '");
        sb.append(ID_COMPLAINT);
        sb.append("'  AND `exam_passed`.status = 1 ");
        return sb.toString();
    }

    public static String get_my_ordonnance(String ID_COMPLAINT, String myid) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT *, ordonnances.created_at as Ocreated_at FROM `ordonnance_details`, `ordonnances`, `users`,`pharmacies` WHERE `ordonnance_details`.`ordonnance_id` = `ordonnances`.`id` AND `ordonnances`.`patient_id` = `users`.`id`        AND `ordonnances`.`pharmacy_id` = `pharmacies`.`id`       AND `patient_id` = '");
        sb.append(myid);
        sb.append("'  AND complaint_id = '");
        sb.append(ID_COMPLAINT);
        sb.append("'  AND `ordonnance_details`.status = 1 ");
        return sb.toString();
    }

    public static String get_unclosed_query(String myid) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `complaints` WHERE `created_by` = '");
        sb.append(myid);
        sb.append("' AND `closed_by` = '0' AND NOT `type_id`= '1'   AND status = 1");
        return sb.toString();
    }

    public static String get_covid_qst_selected_for_update_query(String id_complaint) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `complaint_inqueries` WHERE `complaint_id` = '");
        sb.append(id_complaint);
        sb.append("'  AND status = 1  ");
        return sb.toString();
    }

    public String get_consulting_price() {
        return "SELECT * FROM `params` ";
    }

    public static String get_my_rdv_query(String myId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `meetings`  WHERE created_by = '");
        sb.append(myId);
        sb.append("'  AND status = 1 ORDER BY created_at DESC ");
        return sb.toString();
    }

    public static String check_if_exist(String number) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT Count(id) as count FROM `users` WHERE `phone` LIKE '%");
        sb.append(number);
        sb.append("%' AND status = 1 ");
        return sb.toString();
    }
}
