package capo.ostkit.sdk.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by TinhVC on 5/16/18.
 */

public class Utilities {
    public static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static String hmacDigest(String msg, String keyString, String algo) {
        String digest = null;
        try {
            SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);
            byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));
            StringBuffer hash = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            digest = hash.toString();
        } catch (UnsupportedEncodingException e) {

        } catch (InvalidKeyException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return digest;
    }

    public static String generateQueryString(String _endPoint, Map<String, String> mParams) {
        String mStringToSign = "";
        // TreeMap to store values of HashMap
        TreeMap<String, String> sorted = new TreeMap<>(mParams);

        for (Map.Entry<String, String> entry : sorted.entrySet()) {
            if (mStringToSign.equalsIgnoreCase("")) {
                mStringToSign = _endPoint + "?" + entry.getKey().toLowerCase() + "=" + (entry.getValue().toString().replace(" ", "+"));
            } else {
                mStringToSign += "&" + entry.getKey().toLowerCase() + "=" + (entry.getValue().toString().replace(" ", "+"));
            }
        }
        Logger.log("QueryString: " + mStringToSign);
        return mStringToSign;
    }

    public static String generateApiSignature(String mStringToSign, String mSecret) {
        String mApiSignature = Utilities.hmacDigest(mStringToSign, mSecret, "HmacSHA256");
        Logger.log("ApiSignature: " + mApiSignature);
        return mApiSignature;
    }
}
