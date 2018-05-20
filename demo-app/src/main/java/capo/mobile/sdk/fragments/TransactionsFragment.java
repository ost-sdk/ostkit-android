package capo.mobile.sdk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import capo.mobile.sdk.Interface.MultibleCallback;
import capo.mobile.sdk.R;
import capo.mobile.sdk.activities.MainTabActivty;
import capo.mobile.sdk.adapters.TransactionTypesAdapter;
import capo.mobile.sdk.common.Constants;
import capo.mobile.sdk.common.Utilities;
import capo.mobile.sdk.libs.ProgressWheel;
import capo.mobile.sdk.models.TransactionTypeModel;
import capo.ostkit.sdk.service.VolleyRequestCallback;
import capo.ostkit.sdk.utils.Logger;

/**
 * Created by TinhVC on 5/14/18.
 */

public class TransactionsFragment extends Fragment {

    public TransactionsFragment() {
        // Required empty public constructor
    }

    private String TAG = "caposdk";
    private View footerView;
    private ViewGroup viewGroup;
    private View view;
    private TextView tvTitle;
    private SwipeRefreshLayout swipeLayout;
    private ListView lvItem;

    private TransactionTypesAdapter transactionTypesAdapter;
    private int nextPage = 1;
    private Boolean isLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.transactions_type_listing, container, false);
        this.findViews();
        return view;
    }

    private void findViews() {
        tvTitle = view.findViewById(R.id.tvTitle);
        lvItem = view.findViewById(R.id.lvItem);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        setData();

    }

    private void setData() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoading = false;
                nextPage = 1;
                setTransactionTypesAdapter();
                getTransactionTypes();
            }
        });
        setListLoadMore();
        setTransactionTypesAdapter();
        swipeLayout.setRefreshing(true);
        getTransactionTypes();
    }

    private void setTransactionTypesAdapter() {
        transactionTypesAdapter = new TransactionTypesAdapter(getActivity(), new MultibleCallback() {
            @Override
            public void callback(Object... objects) {
                TransactionTypeModel transactionTypeModel = (TransactionTypeModel) objects[1];
                String typeKind = transactionTypeModel.getKind();
                String nameKind = transactionTypeModel.getName();
                Log.d(TAG, typeKind);

                String fromUserId = "";
                String toUserId = "";
                if (typeKind.equalsIgnoreCase("company_to_user")) {
                    fromUserId = Constants.COMPANY_UUID;
                    toUserId = ((MainTabActivty) getActivity()).listUser.get(Utilities.getRandom(((MainTabActivty) getActivity()).listUser.size())).getId();
                } else if (typeKind.equalsIgnoreCase("user_to_company")) {
                    fromUserId = ((MainTabActivty) getActivity()).listUser.get(Utilities.getRandom(((MainTabActivty) getActivity()).listUser.size())).getId();
                    toUserId = Constants.COMPANY_UUID;
                } else if (typeKind.equalsIgnoreCase("user_to_user")) {
                    fromUserId = ((MainTabActivty) getActivity()).listUser.get(Utilities.getRandom(((MainTabActivty) getActivity()).listUser.size())).getId();
                    toUserId = ((MainTabActivty) getActivity()).listUser.get(Utilities.getRandom(((MainTabActivty) getActivity()).listUser.size())).getId();
                }
                Log.d(TAG, fromUserId);
                Log.d(TAG, toUserId);

                ((MainTabActivty) getActivity()).executeTransactionType(fromUserId, toUserId, nameKind);

            }
        });
        lvItem.setAdapter(transactionTypesAdapter);
    }

    private void addListItem(ArrayList<TransactionTypeModel> listItemAdd) {
        try {
            if (lvItem.getFooterViewsCount() == 0) {
                addFooterLoadmore();
            }
        } catch (Exception e) {

        }
        transactionTypesAdapter.addListItems(listItemAdd);
    }


    private ProgressWheel pbLoading;

    private void addFooterLoadmore() {
        this.footerView = View.inflate(getActivity(), R.layout.footer_loadmore_layout, viewGroup);
        this.pbLoading = footerView.findViewById(R.id.pbLoading);
        this.lvItem.addFooterView(footerView);
    }

    private void removeFooter() {
        try {
            if (lvItem.getFooterViewsCount() > 0) {
                footerView.setAlpha(0);
                pbLoading.getLayoutParams().height = 0;
                pbLoading.requestLayout();
            }
            lvItem.removeFooterView(footerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListLoadMore() {
        lvItem.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && totalItemCount > 0) {
                    if (firstVisibleItem >= totalItemCount - visibleItemCount) {
                        if (footerView != null) {
                            footerView.setAlpha(1);
                            pbLoading.getLayoutParams().height = getActivity().getResources().getDimensionPixelSize(R.dimen.height_progress_loading_more);
                            pbLoading.requestLayout();
                        }
                        getTransactionTypes();
                    }
                }
            }
        });
    }

    private void getTransactionTypes() {
        ((MainTabActivty) getActivity()).ostWrapperSdk.getTransactionTypeWrapper().getListTransactionType(new VolleyRequestCallback() {
            @Override
            public void callback(Context context, Boolean isSuccess, String result) {
                Log.d("caposdk", result);
                if (isSuccess) {
                    try {
                        JSONObject jsonResult = new JSONObject(result);

                        JSONObject jsonData = jsonResult.getJSONObject("data");
                        JSONObject jsonMetaData = jsonData.getJSONObject("meta");
                        JSONObject jsonNextPagePayload = jsonMetaData.getJSONObject("next_page_payload");

                        Boolean isSuccessApi = jsonResult.getBoolean("success");
                        if (isSuccessApi) {
                            ArrayList<TransactionTypeModel> listTransType = new ArrayList<>();
                            JSONArray arrayData = jsonData.getJSONArray("transaction_types");
                            for (int i = 0; i < arrayData.length(); i++) {
                                TransactionTypeModel itemModel = new TransactionTypeModel();
                                itemModel.setData(arrayData.getJSONObject(i));
                                listTransType.add(itemModel);
                            }
                            Log.d("caposdk: ", "Size: " + listTransType.size());
                            addListItem(listTransType);
                            if (Utilities.checkKeyValid(jsonNextPagePayload, "page_no")) {
                                nextPage = jsonNextPagePayload.getInt("page_no");
                                isLoading = false;
                            } else {
                                isLoading = true;
                                removeFooter();
                            }
                        } else {
                            isLoading = true;
                            removeFooter();
                        }
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                        isLoading = true;
                        removeFooter();
                    }
                } else {
                    isLoading = true;
                    removeFooter();
                }
                swipeLayout.setRefreshing(false);
            }
        });
    }

}
