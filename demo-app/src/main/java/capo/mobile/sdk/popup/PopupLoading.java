package capo.mobile.sdk.popup;

/**
 * Created by TinhVC on 5/17/18.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import capo.mobile.sdk.R;
import capo.mobile.sdk.libs.ProgressWheel;

public class PopupLoading implements View.OnClickListener {

    private Context activity;
    private Boolean isCancel;
    public Dialog dialogWheel;


    public PopupLoading(Context _activity, Boolean _isCancel) {
        this.activity = _activity;
        this.isCancel = _isCancel;
        initView();
    }

    private void setupPopup() {
        dialogWheel = new Dialog(activity);
        dialogWheel.getWindow();
        dialogWheel.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWheel.setContentView(R.layout.custom_progressbar_dialog_layout);
        dialogWheel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWheel.setCanceledOnTouchOutside(false);
        dialogWheel.setCancelable(isCancel);
        ProgressWheel pbWheel = (ProgressWheel) dialogWheel.findViewById(R.id.pbLoading);
        pbWheel.setRimColor(Color.LTGRAY);
    }

    private void initView() {
        initData();
    }

    private void initData() {
        this.setupPopup();
    }


    public void showPopup() {
        this.dialogWheel.show();
    }

    public void hidePopup() {
        this.dialogWheel.dismiss();
    }

    @Override
    public void onClick(View v) {

    }
}
