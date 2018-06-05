package project.com.cebs.pingfoodsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jatin on 28-07-2017.
 */

public class ListViewOrderAdapter extends BaseAdapter
{
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewOrderAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View row=convertView;
        // Declare Variables
        TextView txt_list_order_id,txt_list_branch_id,txt_list_user_id,txt_list_shipping_address,txt_list_amount,txt_list_action;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_orders, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        txt_list_order_id = (TextView) itemView.findViewById(R.id.txt_list_order_id);
        txt_list_branch_id = (TextView) itemView.findViewById(R.id.txt_list_branch_id);
        txt_list_user_id = (TextView) itemView.findViewById(R.id.txt_list_user_id);
        txt_list_amount=(TextView)itemView.findViewById(R.id.txt_list_amount) ;
        txt_list_action=(TextView)itemView.findViewById(R.id.txt_list_action);
        txt_list_shipping_address=(TextView)itemView.findViewById(R.id.txt_list_shipping_address);

        // Locate the ImageView in listview_item.xml

        // Capture position and set results to the TextViews
        txt_list_order_id.setText(resultp.get(OrdersFragment.ORDER_ID));
        txt_list_branch_id.setText(resultp.get(OrdersFragment.BRANCH_ID));
        txt_list_user_id.setText(resultp.get(OrdersFragment.USER_ID));
        txt_list_shipping_address.setText(resultp.get(OrdersFragment.SHIPPING_ADDRESS));
        txt_list_amount.setText(resultp.get(OrdersFragment.TOTAL));
        txt_list_action.setText(resultp.get("status"));





        return itemView;
    }
}
