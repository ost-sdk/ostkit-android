package capo.mobile.sdk.adapters;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import capo.mobile.sdk.Interface.MultibleCallback;
import capo.mobile.sdk.R;
import capo.mobile.sdk.models.UserModel;

/**
 * Created by TinhVC on 5/17/18.
 */

public class UsersAdapter extends ArrayAdapter<UserModel> {

    public static final String ITEM_CLICK = "USER_ITEM_CLICK";
    public static final String EXECUTE_CLICK = "EXECUTE_CLICK";
    private Activity activity;
    private MultibleCallback callback;

    class ViewHolder {

        private RelativeLayout rlItem;
        private TextView tvTotalAirdroppedTokens;
        private TextView tvUserName;
        private TextView tvBalance;
        private RelativeLayout rlExecute;
        private AppCompatImageView imgTranfer;
    }

    public UsersAdapter(Activity _activity, MultibleCallback _callback) {
        super(_activity, R.layout.item_user_layout);
        this.activity = _activity;
        this.callback = _callback;
    }

    public void addListItems(List<UserModel> popups) {
        for (UserModel obj : popups) {
            add(obj);
        }
        notifyDataSetChanged();
    }

    public ArrayList<UserModel> getListItems() {
        ArrayList<UserModel> listItems = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            listItems.add(getItem(i));
        }
        return listItems;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.item_user_layout, null);
            holder.rlItem = convertView.findViewById(R.id.rlItem);
            holder.tvTotalAirdroppedTokens = convertView.findViewById(R.id.tvTotalAirdroppedTokens);
            holder.tvUserName = convertView.findViewById(R.id.tvUserName);
            holder.tvBalance = convertView.findViewById(R.id.tvBalance);
            holder.rlExecute = convertView.findViewById(R.id.rlExecute);
            holder.imgTranfer = convertView.findViewById(R.id.imgTranfer);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final UserModel item = getItem(position);

        holder.tvTotalAirdroppedTokens.setText(item.getTotalAirdroppedTokens());
        holder.tvUserName.setText(item.getName());
        holder.tvBalance.setText(item.getTokenBalance());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(ITEM_CLICK, position, item);
            }
        });

        holder.rlExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.callback(EXECUTE_CLICK, position, item);
            }
        });

        return convertView;
    }
}




