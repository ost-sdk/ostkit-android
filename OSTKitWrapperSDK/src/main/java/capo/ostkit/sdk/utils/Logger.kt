package capo.ostkit.sdk.utils

import android.util.Log

/**
* Created by TinhVC on 5/16/18.
*/
class Logger {
    companion object Logger {
        fun log(tag: String, mess: String) {
            Log.d(tag, mess)
        }
    }
}
