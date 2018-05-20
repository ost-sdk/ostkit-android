package capo.mobile.sdk.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import capo.mobile.sdk.R;
import capo.mobile.sdk.common.Constants;
import capo.ostkit.sdk.wrapper.OstWrapperSdk;
import capo.ostkit.sdk.service.VolleyRequestCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private Button btnCreateUser;
    private Button btnListUser;
    private Button btnTransDetail;
    private Button btnMakeTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initView();
        this.initData();
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnCreateUser = (Button) findViewById(R.id.btnCreateUser);
        btnListUser = (Button) findViewById(R.id.btnListUser);
        btnTransDetail = (Button) findViewById(R.id.btnTransDetail);
        btnMakeTrans = (Button) findViewById(R.id.btnMakeTrans);
    }

    private void initData() {
        btnCreateUser.setOnClickListener(this);
        btnListUser.setOnClickListener(this);
        btnTransDetail.setOnClickListener(this);
        btnMakeTrans.setOnClickListener(this);
    }

    private void changeActivitys(Class activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreateUser) {
//            changeActivitys(MainTabActivty.class);
//            Map<String, String> params = new HashMap<>();
//            params.put("filter", "all");
//            params.put("order", "desc");
//            params.put("order_by", "creation_time");
//            params.put("page_no", "1");
//            OstWrapperSdk ostWrapperSdk = new OstWrapperSdk(MainActivity.this, Constants.API_KEY, Constants.SECRET);
//            ostWrapperSdk.createRequest(OstWrapperSdk.USER_LIST, params).execute(new VolleyRequestCallback() {
//                @Override
//                public void callback(Context context, Boolean isSuccess, String result) {
//                    Log.d("caposdk", result);
//                }
//            });


        } else if (v == btnListUser) {
            changeActivitys(MainTabActivty.class);
        } else if (v == btnTransDetail) {
            changeActivitys(MainTabActivty.class);
        } else if (v == btnMakeTrans) {
            changeActivitys(MainTabActivty.class);
        }
    }
}
