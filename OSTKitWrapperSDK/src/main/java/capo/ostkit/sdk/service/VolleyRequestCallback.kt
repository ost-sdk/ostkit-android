package capo.ostkit.sdk.service

import android.content.Context


/**
* Created by TinhVC on 2/16/17.
*/

interface VolleyRequestCallback {
    fun callback(context: Context, isSuccess: Boolean, result: String)
}
