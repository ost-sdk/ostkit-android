package capo.mobile.sdk.models;

import org.json.JSONObject;

import java.io.Serializable;

import capo.mobile.sdk.common.Utilities;

/**
 * Created by TinhVC on 5/17/18.
 */

public class UserModel implements Serializable {
    private String id = "";
    private String name = "";
    private String uuid = "";
    private String totalAirdroppedTokens = "";
    private String tokenBalance = "";


    public void setData(JSONObject jsonObject) {
        this.id = Utilities.getDataString(jsonObject, "id");
        this.name = Utilities.getDataString(jsonObject, "name");
        this.totalAirdroppedTokens = Utilities.getDataString(jsonObject, "total_airdropped_tokens");
        this.tokenBalance = Utilities.getDataString(jsonObject, "token_balance");
        this.uuid = Utilities.getDataString(jsonObject, "uuid");
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTotalAirdroppedTokens() {
        return totalAirdroppedTokens;
    }

    public void setTotalAirdroppedTokens(String totalAirdroppedTokens) {
        this.totalAirdroppedTokens = totalAirdroppedTokens;
    }

    public String getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(String tokenBalance) {
        this.tokenBalance = tokenBalance;
    }
}

