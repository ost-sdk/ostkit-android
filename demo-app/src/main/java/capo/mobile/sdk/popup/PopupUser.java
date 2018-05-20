package capo.mobile.sdk.popup;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnCancelListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.ViewHolder;

import capo.mobile.sdk.Interface.MultibleCallback;
import capo.mobile.sdk.R;
import capo.mobile.sdk.adapters.PopupNoneAdapter;
import capo.mobile.sdk.models.UserModel;

/**
 * Created by TinhVC on 4/11/17.
 */

public class PopupUser implements View.OnClickListener {

    public static final int ADD_NEW_USER = 1;
    public static final int EDIT_USER = 3;

    private Activity activity;
    private MultibleCallback callback;
    private int type;
    private UserModel userModel;

    private LinearLayout lnPopup;
    private TextView tvName;
    private TextView tvTitle;
    private EditText edNameUser;
    private LinearLayout lnButton;
    private Button btnCancel;
    private Button btnOk;


    public PopupUser(Activity _activity, int type, UserModel userModel, MultibleCallback _callback) {
        this.activity = _activity;
        this.callback = _callback;
        this.type = type;
        this.userModel = userModel;

        this.handleShowPopup();
    }


    private void handleShowPopup() {
        Holder holder;
        holder = new ViewHolder(R.layout.popup_add_user_layout);
        showOnlyContentDialog(holder, Gravity.CENTER, dismissListener, cancelListener);
        handleContentPopup(holder);
    }

    private void handleContentPopup(Holder holder) {
        lnPopup = holder.getInflatedView().findViewById(R.id.lnPopup);
        tvName = holder.getInflatedView().findViewById(R.id.tvName);
        tvTitle = holder.getInflatedView().findViewById(R.id.tvTitle);
        edNameUser = holder.getInflatedView().findViewById(R.id.edNameUser);
        lnButton = holder.getInflatedView().findViewById(R.id.lnButton);
        btnCancel = holder.getInflatedView().findViewById(R.id.btnCancel);
        btnOk = holder.getInflatedView().findViewById(R.id.btnOk);

        initData();
    }

    private void initData() {
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        if (userModel != null) {
            edNameUser.setText(userModel.getName());
            btnOk.setText("Update");
        }
    }

    private DialogPlus dialog;

    private void showOnlyContentDialog(Holder holder, int gravity, OnDismissListener dismissListener,
                                       OnCancelListener cancelListener) {
        dialog = DialogPlus.newDialog(activity)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setAdapter(new PopupNoneAdapter(activity))
                .setOnDismissListener(dismissListener)
                .setOnCancelListener(cancelListener)
                .setExpanded(false)
                .setCancelable(false)
                .setContentBackgroundResource(R.color.transparent)
                .create();
    }

    public void show() {
        dialog.show();
    }

    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {

        }
    };

    OnCancelListener cancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogPlus dialog) {

        }
    };


    @Override
    public void onClick(View v) {
        dialog.dismiss();
        if (v == btnCancel) {

        } else if (v == btnOk) {
            callback.callback(type, edNameUser.getText().toString());
        }
    }
}

