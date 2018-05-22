package capo.ostkit.sdk.wrapper

import android.content.Context
import capo.ostkit.sdk.common.Constants
import capo.ostkit.sdk.utils.Utilities

/**
 * Created by TinhVC on 5/16/18.
 */

class OstWrapperSdk @JvmOverloads constructor(_context: Context, _apiKey: String, _secret: String, _isDebug: Boolean = false) {
    private var baseUrl = Constants.BASE_URL
    private var context: Context = _context
    private var apiKey: String = _apiKey
    private var secret: String = _secret
    private var isDebug: Boolean = _isDebug

    fun newUserWrapper(): UserWrapper {
        return UserWrapper(context, apiKey, secret, baseUrl)
    }

    fun newTransactionTypeWrapper(): TransactionTypeWrapper {
        return TransactionTypeWrapper(context, apiKey, secret, baseUrl)
    }

    init {
        Utilities.isDebug = isDebug
    }
}
