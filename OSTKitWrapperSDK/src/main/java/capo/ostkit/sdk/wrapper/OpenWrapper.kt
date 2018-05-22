package capo.ostkit.sdk.wrapper

import android.content.Context
import capo.ostkit.sdk.common.Constants
import capo.ostkit.sdk.service.VolleyRequestCallback
import capo.ostkit.sdk.service.VolleyRequestQueue
import com.android.volley.Request

/**
* Created by TinhVC on 5/17/18.
*/
open class OpenWrapper {

    protected lateinit var context: Context
    protected var apiKey: String = ""
    protected var secret: String = ""
    protected var endPoint: String = ""
    protected var params = HashMap<String, String>()
    protected var baseUrl: String = ""

    private fun getMethod(endPoint: String): Int {

        if (endPoint.equals(Constants.USER_CREATE, false)) return Request.Method.POST

        if (endPoint.equals(Constants.USER_EDIT, false)) return Request.Method.POST

        if (endPoint.equals(Constants.USER_LIST, false)) return Request.Method.GET

        if (endPoint.equals(Constants.AIRDROP_EXECUTE, false)) return Request.Method.POST

        if (endPoint.equals(Constants.AIRDROP_RETRIEVE, false)) return Request.Method.GET

        if (endPoint.equals(Constants.AIRDROP_LIST, false)) return Request.Method.GET

        if (endPoint.equals(Constants.TRANSACTION_TYPE_CREATE, false)) return Request.Method.POST

        if (endPoint.equals(Constants.TRANSACTION_TYPE_EDIT, false)) return Request.Method.POST

        if (endPoint.equals(Constants.TRANSACTION_TYPE_LIST, false)) return Request.Method.GET

        if (endPoint.equals(Constants.TRANSACTION_TYPE_EXECUTE, false)) return Request.Method.POST

        if (endPoint.equals(Constants.TRANSACTION_TYPE_STATUS, false)) return Request.Method.POST

        return -1
    }

    fun execute(callback: VolleyRequestCallback) {
        val paramSorted = params.toSortedMap()
        val method = getMethod(endPoint)
        var url = ""

        if (method == Request.Method.GET) {
            for (entry in paramSorted.entries) {
                if (url.equals("", ignoreCase = true)) {
                    url = Constants.BASE_URL + endPoint + "?" + entry.key + "=" + entry.value
                } else {
                    url += "&" + entry.key + "=" + entry.value
                }
            }
        } else {
            url = Constants.BASE_URL + endPoint
        }
        VolleyRequestQueue(context, getMethod(endPoint), url, paramSorted, callback)
    }
}