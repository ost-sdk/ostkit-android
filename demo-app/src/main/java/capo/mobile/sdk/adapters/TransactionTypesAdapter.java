package capo.mobile.sdk.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import capo.mobile.sdk.Interface.MultibleCallback;
import capo.mobile.sdk.R;
import capo.mobile.sdk.activities.MainTabActivty;
import capo.mobile.sdk.models.TransactionTypeModel;


/**
 * Created by TinhVC on 11/13/17.
 */

public class TransactionTypesAdapter extends ArrayAdapter<TransactionTypeModel> {

    private Activity activity;
    private MultibleCallback callback;

    class ViewHolder {
        private Button btnItem;
    }

    public TransactionTypesAdapter(Activity _activity, MultibleCallback _callback) {
        super(_activity, R.layout.item_transaction_type_layout);
        this.activity = _activity;
        this.callback = _callback;
    }

    public void addListItems(List<TransactionTypeModel> popups) {
        for (TransactionTypeModel obj : popups) {
            add(obj);
            if (obj.getName().equalsIgnoreCase("Reward")) {
                ((MainTabActivty) activity).setTransactionTypeReward(obj);
            }
        }
        notifyDataSetChanged();
    }

    public ArrayList<TransactionTypeModel> getListItems() {
        ArrayList<TransactionTypeModel> listItems = new ArrayList<>();
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
            convertView = inflater.inflate(R.layout.item_transaction_type_layout, null);
            holder.btnItem = (Button) convertView.findViewById(R.id.btnItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TransactionTypeModel item = getItem(position);

        holder.btnItem.setText(item.getName());

        holder.btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(position, item);
            }
        });

        return convertView;
    }
}



