package capo.ostkit.sdk.service

import android.content.Context

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.util.HashMap
import capo.ostkit.sdk.common.Constants
import capo.ostkit.sdk.utils.Logger
import capo.ostkit.sdk.utils.Utilities
import com.android.volley.toolbox.Volley

/**
* Created by TinhVC on 5/16/18.
*/

class VolleyRequestQueue constructor(_context: Context, _method: Int, _url: String, _param: Map<String, String>, _callback: VolleyRequestCallback) {
    private var time: Long = 0
    private var currentRequest: StringRequest? = null
    private var context: Context = _context
    private var method: Int = _method
    private var url: String = _url
    private var param: Map<String, String> = _param
    private var callback: VolleyRequestCallback = _callback

    init {
        executeRequest()
    }

    /*EXECUTE REQUEST*/
    private fun executeRequest() {
        Logger.log(Constants.TAG, "Url: " + url + " ->> " + param.toString())
        val t0 = System.currentTimeMillis()
        currentRequest = object : StringRequest(method, url,
                Response.Listener { response ->
                    val t1 = System.currentTimeMillis()
                    time = t1 - t0
                    if (Utilities.isDebug) {
                        Logger.log(Constants.TAG, url + " -> " + response)
                    }
                    callback.callback(context, true, response)
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()

                    var responseError = ""

                    try {
                        responseError = String(error.networkResponse.data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (error.networkResponse == null) {
                        callback.callback(context, false, Constants.ERROR_MESS_SERVER_TIMEOUT)
                    } else {

                        val finalResponseError = responseError
                        callback.callback(context, false, if (finalResponseError.equals("", ignoreCase = true)) Constants.ERROR_MESS_REQUEST else finalResponseError)
                    }
                }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/x-www-form-urlencoded")
                headers.put("Accept", "application/json")
                if (Utilities.isDebug) {
                    Logger.log(Constants.TAG, "Header:  " + headers.toString())
                }
                return headers

            }

            override fun getParams(): Map<String, String> {
                if (Utilities.isDebug) {
                    Logger.log(Constants.TAG, "Param: " + param.toString())
                }
                return param
            }
        }
        Volley.newRequestQueue(context).add(currentRequest)
    }
}

