package cd.digitalEdge.vst.Objects;

public class Payments {

    public String id            = "id";
    public String usd_amount    = "usd_amount";
    public String cdf_amount    = "cdf_amount";
    public String rate          = "rate";
    public String selling_id    = "selling_id";
    public String created_by    = "created_by";
    public String created_at    = "created_at";
    public String updating      = "updating";
    public String status        = "status";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsd_amount() {
        return usd_amount;
    }

    public void setUsd_amount(String usd_amount) {
        this.usd_amount = usd_amount;
    }

    public String getCdf_amount() {
        return cdf_amount;
    }

    public void setCdf_amount(String cdf_amount) {
        this.cdf_amount = cdf_amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSelling_id() {
        return selling_id;
    }

    public void setSelling_id(String selling_id) {
        this.selling_id = selling_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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
