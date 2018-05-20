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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import capo.mobile.sdk.Interface.MultibleCallback;
import capo.mobile.sdk.R;
import capo.mobile.sdk.activities.MainTabActivty;
import capo.mobile.sdk.adapters.UsersAdapter;
import capo.mobile.sdk.common.Constants;
import capo.mobile.sdk.common.Utilities;
import capo.mobile.sdk.libs.ProgressWheel;
import capo.mobile.sdk.models.UserModel;
import capo.mobile.sdk.popup.PopupUser;
import capo.mobile.sdk.popup.PopupLoading;
import capo.ostkit.sdk.service.VolleyRequestCallback;
import capo.ostkit.sdk.utils.Logger;

/**
 * Created by TinhVC on 5/14/18.
 */

public class UsersFragment extends Fragment implements View.OnClickListener {

    public UsersFragment() {
        // UsersFragment empty public constructor
    }

    private View footerView;
    private ViewGroup viewGroup;
    private View view;
    private SwipeRefreshLayout swipeLayout;
    private ListView lvItem;
    private Button btnAddUser;
    private Button btnSendAirDrop;

    private UsersAdapter usersAdapter;
    private PopupLoading popupLoading;

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
        this.view = inflater.inflate(R.layout.user_listing_layout, container, false);
        this.viewGroup = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
        this.findViews();
        return view;
    }

    private void findViews() {
        swipeLayout = view.findViewById(R.id.swipeLayout);
        lvItem = view.findViewById(R.id.lvItem);
        btnAddUser = view.findViewById(R.id.btnAddUser);
        btnSendAirDrop = view.findViewById(R.id.btnSendAirDrop);
        setData();

    }

    private void setData() {
        popupLoading = new PopupLoading(getActivity(), false);

        btnSendAirDrop.setOnClickListener(this);
        btnAddUser.setOnClickListener(this);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainTabActivty) getActivity()).listUser = new ArrayList<>();
                isLoading = false;
                nextPage = 1;
                setUsersAdapter();
                getUsers();
            }
        });
        setListLoadMore();
        setUsersAdapter();
        swipeLayout.setRefreshing(true);
        getUsers();
    }

    private void setUsersAdapter() {
        usersAdapter = new UsersAdapter(getActivity(), new MultibleCallback() {
            @Override
            public void callback(Object... objects) {
                final int posChange = ((int) objects[1]);
                final UserModel userModel = ((UserModel) objects[2]);
                if (String.valueOf(objects[0]).equalsIgnoreCase(UsersAdapter.ITEM_CLICK)) {
                    PopupUser popupUser = new PopupUser(getActivity(), PopupUser.EDIT_USER, userModel, new MultibleCallback() {
                        @Override
                        public void callback(Object... objects) {
                            popupLoading.showPopup();
                            ((MainTabActivty) getActivity()).ostWrapperSdk.getUserWrapper().editUser(userModel.getUuid(), objects[1].toString(), new VolleyRequestCallback() {
                                @Override
                                public void callback(Context context, Boolean isSuccess, String result) {
//                                Log.d("caposdk", result);
                                    if (isSuccess) {
                                        try {
                                            JSONObject jsonResult = new JSONObject(result);
                                            Boolean isSuccessApi = jsonResult.getBoolean("success");
                                            if (isSuccessApi) {

                                                JSONArray arrayData = jsonResult.getJSONObject("data").getJSONArray("economy_users");
                                                if (arrayData.length() > 0) {
                                                    UserModel itemModel = new UserModel();
                                                    itemModel.setData(arrayData.getJSONObject(0));
                                                    usersAdapter.getItem(posChange).setName(itemModel.getName());
                                                    usersAdapter.notifyDataSetChanged();
                                                    lvItem.smoothScrollToPosition(posChange);
                                                }
                                            } else {
                                                JSONObject jsonError = jsonResult.getJSONObject("err");
                                                Toast.makeText(getActivity(), jsonError.getString("msg"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException ex) {
                                            Logger.log(ex.getLocalizedMessage());
                                            Toast.makeText(getActivity(), ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    popupLoading.hidePopup();
                                }
                            });
                        }
                    });
                    popupUser.show();
                } else if (String.valueOf(objects[0]).equalsIgnoreCase(UsersAdapter.EXECUTE_CLICK)) {

                    String fromUserId = Constants.COMPANY_UUID;
                    String toUserId = userModel.getId();
                    String nameKind = ((MainTabActivty) getActivity()).transactionTypeReward.getName();
                    ((MainTabActivty) getActivity()).executeTransactionType(fromUserId, toUserId, nameKind);
                }
            }
        });
        lvItem.setAdapter(usersAdapter);
    }

    private void addListItem(ArrayList<UserModel> listItemAdd) {
        try {
            if (lvItem.getFooterViewsCount() == 0) {
                addFooterLoadmore();
            }
        } catch (Exception e) {

        }

        ((MainTabActivty) getActivity()).listUser.addAll(listItemAdd);
        usersAdapter.addListItems(listItemAdd);
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
                        getUsers();
                    }
                }
            }
        });
    }

    private void getUsers() {
        if (isLoading) {
            return;
        }
        isLoading = true;
        ((MainTabActivty) getActivity()).ostWrapperSdk.getUserWrapper().getListUser(nextPage, new VolleyRequestCallback() {
            @Override
            public void callback(Context context, Boolean isSuccess, String result) {
//                Log.d("caposdk", result);
                if (isSuccess) {
                    try {
                        JSONObject jsonResult = new JSONObject(result);
                        Boolean isSuccessApi = jsonResult.getBoolean("success");
                        if (isSuccessApi) {

                            JSONObject jsonData = jsonResult.getJSONObject("data");
                            JSONObject jsonMetaData = jsonData.getJSONObject("meta");
                            JSONObject jsonNextPagePayload = jsonMetaData.getJSONObject("next_page_payload");


                            ArrayList<UserModel> listUser = new ArrayList<>();
                            JSONArray arrayData = jsonData.getJSONArray("economy_users");
                            for (int i = 0; i < arrayData.length(); i++) {
                                UserModel itemModel = new UserModel();
                                itemModel.setData(arrayData.getJSONObject(i));
                                listUser.add(itemModel);
                            }

                            addListItem(listUser);

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

    @Override
    public void onClick(View view) {
        if (view == btnAddUser) {

            PopupUser popupUser = new PopupUser(getActivity(), PopupUser.ADD_NEW_USER, null, new MultibleCallback() {
                @Override
                public void callback(Object... objects) {
                    popupLoading.showPopup();
                    ((MainTabActivty) getActivity()).ostWrapperSdk.getUserWrapper().createUser(objects[1].toString(), new VolleyRequestCallback() {
                        @Override
                        public void callback(Context context, Boolean isSuccess, String result) {
//                            Log.d("caposdk", result);
                            if (isSuccess) {
                                try {
                                    JSONObject jsonResult = new JSONObject(result);
                                    Boolean isSuccessApi = jsonResult.getBoolean("success");
                                    if (isSuccessApi) {
                                        JSONArray arrayData = jsonResult.getJSONObject("data").getJSONArray("economy_users");
                                        if (arrayData.length() > 0) {
                                            UserModel itemModel = new UserModel();
                                            itemModel.setData(arrayData.getJSONObject(0));
                                            usersAdapter.add(itemModel);
                                            usersAdapter.notifyDataSetChanged();
                                            lvItem.smoothScrollToPosition(usersAdapter.getCount() - 1);
                                        }
                                    } else {
                                        JSONObject jsonError = jsonResult.getJSONObject("err");
                                        Toast.makeText(getActivity(), jsonError.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException ex) {
                                    Logger.log(ex.getLocalizedMessage());
                                    Toast.makeText(getActivity(), ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                            }
                            popupLoading.hidePopup();
                        }
                    });
                }
            });
            popupUser.show();
        } else if (view == btnSendAirDrop) {

        }
    }
}
