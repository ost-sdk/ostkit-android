package capo.ostkit.sdk.wrapper;

import android.content.Context;

/**
 * Created by TinhVC on 5/16/18.
 */

public class OstWrapperSdk {
    private Context mContext;
    private String mApiKey;
    private String mSecret;
    private String mBaseUrl = OstWrapperSdk.BASE_URL;

    /*Define url request*/
    public static final String BASE_URL = "https://playgroundapi.ost.com";
    public static final String USER_CREATE = "/users/create";
    public static final String USER_EDIT = "/users/edit";
    public static final String USER_LIST = "/users/list";
    public static final String AIRDROP_EXECUTE = "/airdrops";
    public static final String AIRDROP_RETRIEVE = "/airdrops/";
    public static final String AIRDROP_LIST = "/airdrops";
    public static final String TRANSACTION_TYPE_CREATE = "/transaction-types/create";
    public static final String TRANSACTION_TYPE_EDIT = "/transaction-types/edit";
    public static final String TRANSACTION_TYPE_LIST = "/transaction-types/list";
    public static final String TRANSACTION_TYPE_EXECUTE = "/transaction-types/execute";
    public static final String TRANSACTION_TYPE_STATUS = "/transaction-types/status";

    public OstWrapperSdk(Context context, String apiKey, String secret) {
        this.mContext = context;
        this.mApiKey = apiKey;
        this.mSecret = secret;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getSecret() {
        return mSecret;
    }

    public UserWrapper getUserWrapper() {
        UserWrapper userWrapper = new UserWrapper(mContext, mApiKey, mSecret, mBaseUrl);
        return userWrapper;
    }

    public TransactionTypeWrapper getTransactionTypeWrapper() {
        TransactionTypeWrapper transactionTypeWrapper = new TransactionTypeWrapper(mContext, mApiKey, mSecret, mBaseUrl);
        return transactionTypeWrapper;
    }
}
