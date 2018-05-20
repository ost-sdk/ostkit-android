package capo.ostkit.sdk.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import capo.ostkit.sdk.common.Constants;
import capo.ostkit.sdk.utils.Logger;
import capo.ostkit.sdk.volley.VolleySingleton;

public class VolleyRequestQueue {


    private Context mContext;
    private VolleyRequestCallback mCallback;
    private long mTime = 0;
    private String mUrl;
    private int method;
    private Map<String, String> param;
    public StringRequest currentRequest;

    public VolleyRequestQueue(Context _context, int _method, String _url, Map<String, String> _param, VolleyRequestCallback _callback) {
        this.mCallback = _callback;
        this.mContext = _context;
        this.mUrl = _url;
        this.method = _method;
        this.param = _param;

        executeRequest();
    }

    /*EXCUTE REQUEST*/
    private void executeRequest() {
        Logger.log("Url: " + mUrl + " ->> " + param.toString());
        final long t0 = System.currentTimeMillis();
        currentRequest = new StringRequest(method, mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Logger.log("Respone: " + response);
                        final long t1 = System.currentTimeMillis();
                        mTime = t1 - t0;
                        Logger.log(mUrl + " -> " + response);
                        mCallback.callback(mContext, true, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {

                        error.printStackTrace();

                        String responseError = "";

                        try {
                            responseError = new String(error.networkResponse.data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (error.networkResponse == null) {
                            mCallback.callback(mContext, false, Constants.ERROR_MESS_SERVER_TIMEOUT);
                        } else {

                            final String finalResponseError = responseError;
                            mCallback.callback(mContext, false, (finalResponseError.equalsIgnoreCase("")) ? Constants.ERROR_MESS_REQUEST : finalResponseError);
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                Logger.log("Header:  " + headers.toString());
                return headers;

            }

            protected Map<String, String> getParams() {
                Logger.log("Param: "+param.toString());
                return param;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(currentRequest);
    }
}

