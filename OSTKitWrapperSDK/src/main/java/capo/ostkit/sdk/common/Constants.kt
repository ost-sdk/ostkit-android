package capo.ostkit.sdk.common

/**
* Created by Darshan on 5/26/2015.
*/
object Constants {

    /*Define url request*/
    const val BASE_URL = "https://playgroundapi.ost.com"
    const val USER_CREATE = "/users/create"
    const val USER_EDIT = "/users/edit"
    const val USER_LIST = "/users/list"
    const val AIRDROP_EXECUTE = "/airdrops"
    const val AIRDROP_RETRIEVE = "/airdrops/"
    const val AIRDROP_LIST = "/airdrops"
    const val TRANSACTION_TYPE_CREATE = "/transaction-types/create"
    const val TRANSACTION_TYPE_EDIT = "/transaction-types/edit"
    const val TRANSACTION_TYPE_LIST = "/transaction-types/list"
    const val TRANSACTION_TYPE_EXECUTE = "/transaction-types/execute"
    const val TRANSACTION_TYPE_STATUS = "/transaction-types/status"

    /*Define response error*/
    const val ERROR_MESS_FAILED_CONNECT_SERVER = "Cannot connect to server!"
    const val ERROR_MESS_EXCEPTION = "An error occurred. Please try again!"
    const val ERROR_MESS_INTERNET = "Connection error. Please try again!"
    const val ERROR_MESS_REQUEST = "Bad request. Please try again!"
    const val ERROR_MESS_RESPONSE_FORMAT = "Error format response. Please try again!"
    const val ERROR_MESS_SERVER_TIMEOUT = "Server timeout. Please try again!"
    const val ERROR_MESS_NO_RESPONSE_DATA = "No any response. Please try again!"

    /*Define tag*/
    const val TAG = "CAPO_SDK"
}
