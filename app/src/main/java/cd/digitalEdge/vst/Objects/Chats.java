package cd.digitalEdge.vst.Objects;

import com.google.gson.annotations.SerializedName;

public class Chats {
    public String complaint_id = "complaint_id";
    @SerializedName("created_at")
    public String created_at = "created_at";
    public String from_avatar = "from_avatar";
    public String from_firstname = "from_firstname";
    public String from_id = "from_id";
    public String from_middlename = "from_middlename";
    public String from_name = "from_name";
    public String from_role_id = "from_role_id";
    public String id = "id";
    public String message = "message";
    public String notified = "notified";
    public String read_at = "read_at";
    public String read_status = "read_status";
    public String status = "status";
    public String to_id = "to_id";

    public String getFrom_role_id() {
        return this.from_role_id;
    }

    public void setFrom_role_id(String from_role_id2) {
        this.from_role_id = from_role_id2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getRead_at() {
        return this.read_at;
    }

    public void setRead_at(String read_at2) {
        this.read_at = read_at2;
    }

    public String getFrom_name() {
        return this.from_name;
    }

    public void setFrom_name(String from_name2) {
        this.from_name = from_name2;
    }

    public String getFrom_middlename() {
        return this.from_middlename;
    }

    public void setFrom_middlename(String from_middlename2) {
        this.from_middlename = from_middlename2;
    }

    public String getFrom_firstname() {
        return this.from_firstname;
    }

    public void setFrom_firstname(String from_firstname2) {
        this.from_firstname = from_firstname2;
    }

    public String getFrom_avatar() {
        return this.from_avatar;
    }

    public void setFrom_avatar(String from_avatar2) {
        this.from_avatar = from_avatar2;
    }

    public String getRead_status() {
        return this.read_status;
    }

    public void setRead_status(String read_status2) {
        this.read_status = read_status2;
    }

    public String getFrom_id() {
        return this.from_id;
    }

    public void setFrom_id(String from_id2) {
        this.from_id = from_id2;
    }

    public String getTo_id() {
        return this.to_id;
    }

    public void setTo_id(String to_id2) {
        this.to_id = to_id2;
    }

    public String getComplaint_id() {
        return this.complaint_id;
    }

    public void setComplaint_id(String complaint_id2) {
        this.complaint_id = complaint_id2;
    }

    public String getNotified() {
        return this.notified;
    }

    public void setNotified(String notified2) {
        this.notified = notified2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(String created_at2) {
        this.created_at = created_at2;
    }


}
