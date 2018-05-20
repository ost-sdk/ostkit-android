package capo.ostkit.sdk.wrapper;

import android.content.Context
import capo.ostkit.sdk.service.VolleyRequestCallback
import capo.ostkit.sdk.utils.Utilities

/**
 * Created by TinhVC on 5/17/18.
 */

class TransactionTypeWrapper(_context: Context, _apiKey: String, _secret: String, _baseUrl: String) : OpenWrapper() {

    init {
        this.context = _context
        this.apiKey = _apiKey
        this.secret = _secret
        this.baseUrl = _baseUrl
    }

    /*
    * Send a POST request to /transaction-types/create to create a new transaction-type.
    * Transaction types allow users to exchange branded tokens between each
    * other for actions within the application or with the company.
    * */
    fun createTransactionType(name: String, kind: String, currencyType: String, currencyValue: Float, commissionPercent: Float, callBack: VolleyRequestCallback) {
        this.endPoint = OstWrapperSdk.TRANSACTION_TYPE_CREATE
        val mRequestTimestamp = Utilities.getTimestamp()

        params.put("name", name)
        params.put("kind", kind) // type: {user_to_user, company_to_user, user_to_company}
        params.put("currency_type", currencyType)
        params.put("currency_value", currencyValue.toString())
        params.put("commission_percent", commissionPercent.toString())
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }


    /*
    * Send a POST request to /transaction-types/edit to edit an exisiting
    * transaction-type for a given unique identifier that was returned during
    * the creation of a new transaction type.
    * This updates the specified transaction type by setting the values of the parameters passed.
    * Any parameter not provided will be left unchanged.
    * Individual keys can be unset by posting an empty value to them.
    * */
    @JvmOverloads
    fun editTransactionType(clientTransactionId: String, callBack: VolleyRequestCallback, name: String = "", kind: String = "", currencyType: String = "", currencyValue: Float = 0f, commissionPercent: Float = 0f) {
        this.endPoint = OstWrapperSdk.TRANSACTION_TYPE_EDIT
        val mRequestTimestamp = Utilities.getTimestamp()

        params.put("client_transaction_id", clientTransactionId)

        if (name.equals("", false)) {
            params.put("name", name)
        }

        if (kind.equals("", false)) {
            params.put("kind", kind)
        }
        if (currencyType.equals("", false)) {
            params.put("currency_type", currencyType)
        }

        if (currencyValue > 0) {
            params.put("currency_value", currencyValue.toString())
        }
        if (commissionPercent > 0) {
            params.put("commission_percent", commissionPercent.toString())
        }

        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }


    /*
    * Send a GET request on /transaction-types/list to receive a
    * list of all transaction types. In addition client_id,
    * price_points, and client_tokens are returned.
    * */
    fun getListTransactionType(callBack: VolleyRequestCallback) {
        this.endPoint = OstWrapperSdk.TRANSACTION_TYPE_LIST
        val mRequestTimestamp = Utilities.getTimestamp()
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }


    /*
    * Send a POST request on /transaction-types/execute to execute
    * a defined transaction type between users and/or the company.
    * */
    fun executeTransactionType(fromUuid: String, toUuid: String, transactionKind: String, callBack: VolleyRequestCallback) {
        this.endPoint = OstWrapperSdk.TRANSACTION_TYPE_EXECUTE
        val mRequestTimestamp = Utilities.getTimestamp()

        params.put("from_uuid", fromUuid)
        params.put("to_uuid", toUuid)
        params.put("transaction_kind", transactionKind)
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }


    /*
    * Send a POST request on /transaction-types/status to query the status of executed transactions.
    * Multiple uuids can be passed in a single request to receive the status of all.
    * */
    fun statusTransactionType(transactionUuids: String, callBack: VolleyRequestCallback) {
        this.endPoint = OstWrapperSdk.TRANSACTION_TYPE_STATUS
        val mRequestTimestamp = Utilities.getTimestamp()

        params.put("transaction_uuids[]", transactionUuids)
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }
}