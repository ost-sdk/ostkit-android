package capo.ostkit.sdk.wrapper

import android.content.Context
import capo.ostkit.sdk.service.VolleyRequestCallback
import capo.ostkit.sdk.utils.Utilities

/**
 * Created by TinhVC on 5/18/18.
 */

class AirDropWrapper(_context: Context, _apiKey: String, _secret: String, _baseUrl: String) : OpenWrapper() {

    init {
        this.context = _context
        this.apiKey = _apiKey
        this.secret = _secret
        this.baseUrl = _baseUrl
    }

    /*
    * Send a Post request to /airdrops to airdrop certain amount of branded tokens to a set of users.
    * You can use this API to send or reward your end-users a selected amount of branded tokens.
    * This enables them to participate in your branded token economy.
    * Param:
    * - amount: (mandatory) The amount of BT that needs to be air-dropped to the selected end-users. Example:10
    * - airdropped: true/false. Indicates whether to airdrop tokens to end-users who have been airdropped some tokens at least once or to end-users who have never been airdropped tokens.
    * - user_ids: a comma-separated list of user_ids specifies selected users in the token economy to be air-dropped tokens to.
    * */
    fun executeAirdrop(amount: String, airDropped: Boolean, userIds: String, callBack: VolleyRequestCallback) {
        this.endPoint = OstWrapperSdk.AIRDROP_EXECUTE
        val mRequestTimestamp = Utilities.getTimestamp()
        params.put("amount", amount)
        params.put("airdropped", airDropped.toString())
        params.put("user_ids", userIds)
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }

    /*
    * Send a GET request to /airdrops/{id} to receive the airdrop status.
    * {id} in this API endpoint is the unique identifier of the airdrop that is returned on executing an airdrop.
    * Get the status of the airdrop of branded tokens.
    * This API can be used to understand which stage the processing of airdropping the tokens are going through.
    * Param:
    * - airDropId: {id} in this API endpoint is the unique identifier of the airdrop that is returned on executing an airdrop.
    * */
    fun retrieveAirdrop(airDropId: String, airDropped: Boolean, userIds: String, callBack: VolleyRequestCallback) {
        this.endPoint = OstWrapperSdk.AIRDROP_RETRIEVE + airDropId
        val mRequestTimestamp = Utilities.getTimestamp()
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }

    /*
    * Send a GET request to /airdrops to receive a list airdrops.
    * Gets a paginated list of airdrops executed.
    * This API can also be used to understand the status of multiple airdrops
    * in a single request incases when you execute multiple airdrops simultaneouly.
    * Param:
    * - pageNumber: page number (starts from 1)
    * - order_by: order the list by when the airdrop was executed (default). Can only order by execution date.
    * - order: orders the list in 'desc' (default). Accepts value 'asc' to order in ascending order.
    * - limit: limits the number of airdrop objects to be sent in one request. Possible Values Min 1, Max 100, Default 10.
    * - optional__filters: filters can be used to refine your list. The Parameters on which filters are supported are detailed in the table below.
    * */
    @JvmOverloads
    fun getListAirDrop(pageNumber: Int, callBack: VolleyRequestCallback, orderBy: String = "creation_time", order: String = "asc", limit: Int = 10, optionalFilters: String = "") {
        this.endPoint = OstWrapperSdk.AIRDROP_LIST
        val mRequestTimestamp = Utilities.getTimestamp()
        params.put("page_no", pageNumber.toString())
        params.put("order_by", orderBy)
        params.put("order", order)
        params.put("limit", limit.toString())
        params.put("optional__filters", optionalFilters)
        params.put("request_timestamp", mRequestTimestamp)
        params.put("api_key", apiKey)

        var stringToSign = Utilities.generateQueryString(endPoint, params)
        var apiSignature = Utilities.generateApiSignature(stringToSign, secret)
        params.put("signature", apiSignature)
        this.execute(callBack)
    }
}