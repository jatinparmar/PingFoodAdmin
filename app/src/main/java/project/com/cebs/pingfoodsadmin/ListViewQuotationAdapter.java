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

public class ListViewQuotationAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewQuotationAdapter(Context context,
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
        // Declare Variables
        TextView txt_serial_number,txt_restaurant_name,txt_contact_person,txt_website,txt_contact_number;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_quotations, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        txt_serial_number = (TextView) itemView.findViewById(R.id.txt_serial_number);
        txt_restaurant_name = (TextView) itemView.findViewById(R.id.txt_restaurant_name);
        txt_contact_person = (TextView) itemView.findViewById(R.id.txt_contact_person);
        txt_website=(TextView)itemView.findViewById(R.id.txt_website) ;
        txt_contact_number=(TextView)itemView.findViewById(R.id.txt_contact_number);

        // Locate the ImageView in listview_item.xml

        // Capture position and set results to the TextViews
        txt_serial_number.setText(resultp.get(QuotationsFragment.SERIAL_NUMBER));
        txt_restaurant_name.setText(resultp.get(QuotationsFragment.RESTAURANT_NAME));
        txt_contact_person.setText(resultp.get(QuotationsFragment.CONTACT_NAME));
        txt_website.setText(resultp.get(QuotationsFragment.WEBSITE));
        txt_contact_number.setText(resultp.get(QuotationsFragment.CONTACT_NUMBER));
        // txt_list_action.setText(resultp.get(OrdersFragment.TOTAL));



        return itemView;
    }
}
