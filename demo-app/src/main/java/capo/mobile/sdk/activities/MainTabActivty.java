package capo.mobile.sdk.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import capo.mobile.sdk.R;
import capo.mobile.sdk.common.Constants;
import capo.mobile.sdk.fragments.TransactionsFragment;
import capo.mobile.sdk.fragments.UsersFragment;
import capo.mobile.sdk.models.TransactionTypeModel;
import capo.mobile.sdk.models.UserModel;
import capo.ostkit.sdk.service.VolleyRequestCallback;
import capo.ostkit.sdk.wrapper.OstWrapperSdk;

/**
 * Created by TinhVC on 5/14/18.
 */

public class MainTabActivty extends AppCompatActivity {

    private String TAG = "caposdk";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public OstWrapperSdk ostWrapperSdk;
    public ArrayList<UserModel> listUser = new ArrayList<>();
    public TransactionTypeModel transactionTypeReward = new TransactionTypeModel();

    public void setTransactionTypeReward(TransactionTypeModel transactionTypeReward) {
        this.transactionTypeReward = transactionTypeReward;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_tabs);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initOstSdk();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
    }

    /**
     * Adding custom view to tab
     */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
        tabOne.setText("Transactions");
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
        tabTwo.setText("Users");
        tabLayout.getTabAt(1).setCustomView(tabTwo);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionsFragment(), "Transactions");
        adapter.addFragment(new UsersFragment(), "Users");
        viewPager.setAdapter(adapter);
    }

    private void initOstSdk() {
        ostWrapperSdk = new OstWrapperSdk(MainTabActivty.this, Constants.API_KEY, Constants.SECRET);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void executeTransactionType(String fromUserId, String toUserId, final String nameKind) {
        ostWrapperSdk.getTransactionTypeWrapper().executeTransactionType(fromUserId, toUserId, nameKind, new VolleyRequestCallback() {
            @Override
            public void callback(Context context, Boolean isSuccess, String result) {
                Log.d(TAG, result);
                if (isSuccess) {
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        JSONObject jsonData = jsonResult.getJSONObject("data");
                        Boolean isSuccessApi = jsonResult.getBoolean("success");
                        if (isSuccessApi) {
                            Toast.makeText(MainTabActivty.this, "Transaction " + nameKind + " execute successfully!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}
