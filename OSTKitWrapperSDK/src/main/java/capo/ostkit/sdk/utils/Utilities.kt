package capo.ostkit.sdk.utils

import capo.ostkit.sdk.common.Constants
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
* Created by TinhVC on 5/16/18.
*/

class Utilities {
    companion object Utilities {

        var isDebug:Boolean = false

        private val HEX_CHARS = "0123456789ABCDEF".toCharArray()

        fun getTimestamp(): String {
            return (System.currentTimeMillis() / 1000).toString()
        }

        private fun String.sha256(keyString: String, algorithm: String): String {
            val data: ByteArray = this.toByteArray()
            val key: ByteArray = keyString.toByteArray()
            val keySpec = SecretKeySpec(key, algorithm)
            val mac = Mac.getInstance(algorithm)
            mac.init(keySpec)

            val hMac = mac.doFinal(data).toHex()
            return hMac.toLowerCase()
        }

        private fun ByteArray.toHex(): String {
            val result = StringBuffer()

            forEach {
                val octet = it.toInt()
                val firstIndex = (octet and 0xF0).ushr(4)
                val secondIndex = octet and 0x0F
                result.append(HEX_CHARS[firstIndex])
                result.append(HEX_CHARS[secondIndex])
            }

            return result.toString()
        }


        fun generateQueryString(_endPoint: String, mParams: Map<String, String>): String {
            var mStringToSign = ""
            // TreeMap to store values of HashMap
            val sorted = TreeMap(mParams)

            for ((key, value) in sorted) {
                if (mStringToSign.equals("", ignoreCase = true)) {
                    mStringToSign = _endPoint + "?" + key.toLowerCase() + "=" + value.toString().replace(" ", "+")
                } else {
                    mStringToSign += "&" + key.toLowerCase() + "=" + value.toString().replace(" ", "+")
                }
            }
            Logger.log(Constants.TAG, mStringToSign)
            return mStringToSign
        }

        fun generateApiSignature(mStringToSign: String, mSecret: String): String {
            return mStringToSign.sha256(mSecret, "HmacSHA256")
        }
    }
}