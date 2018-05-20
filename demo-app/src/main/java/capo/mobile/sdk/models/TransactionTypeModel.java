package capo.mobile.sdk.models;

import org.json.JSONObject;

import java.io.Serializable;

import capo.mobile.sdk.common.Utilities;

/**
 * Created by TinhVC on 5/16/18.
 */

public class TransactionTypeModel implements Serializable {
    private String id;
    private String client_transaction_id;
    private String name;
    private String kind;
    private String currency_type;
    private String currency_value;
    private String commission_percent;
    private String status;

    public void setData(JSONObject jsonObject) {
        this.id = Utilities.getDataString(jsonObject, "id");
        this.client_transaction_id = Utilities.getDataString(jsonObject, "client_transaction_id");
        this.name = Utilities.getDataString(jsonObject, "name");
        this.kind = Utilities.getDataString(jsonObject, "kind");
        this.currency_type = Utilities.getDataString(jsonObject, "currency_type");
        this.currency_value = Utilities.getDataString(jsonObject, "currency_value");
        this.commission_percent = Utilities.getDataString(jsonObject, "commission_percent");
        this.status = Utilities.getDataString(jsonObject, "status");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientTransactionId() {
        return client_transaction_id;
    }

    public void setClientTransactionId(String client_transaction_id) {
        this.client_transaction_id = client_transaction_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCurrencyType() {
        return currency_type;
    }

    public void setCurrencyType(String currency_type) {
        this.currency_type = currency_type;
    }

    public String getCurrencyValue() {
        return currency_value;
    }

    public void setCurrencyValue(String currency_value) {
        this.currency_value = currency_value;
    }

    public String getCommissionPercent() {
        return commission_percent;
    }

    public void setCommissionPercent(String commission_percent) {
        this.commission_percent = commission_percent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
