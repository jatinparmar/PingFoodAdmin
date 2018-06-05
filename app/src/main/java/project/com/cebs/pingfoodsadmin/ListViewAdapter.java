package project.com.cebs.pingfoodsadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jatin on 26-07-2017.
 */

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
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
        TextView restaurant_name,restaurant_cc1,restaurant_description,restaurant_id;

        ImageView logo=null;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        // Get the position
        resultp = data.get(position);

        // Locate the TextViews in listview_item.xml
        restaurant_name = (TextView) itemView.findViewById(R.id.res_name);
        restaurant_cc1 = (TextView) itemView.findViewById(R.id.res_cc1);
        restaurant_description = (TextView) itemView.findViewById(R.id.res_desc);
        restaurant_id=(TextView)itemView.findViewById(R.id.res_id) ;
        // Locate the ImageView in listview_item.xml
        logo = (ImageView) itemView.findViewById(R.id.logo);

        // Capture position and set results to the TextViews
        restaurant_name.setText(resultp.get(RestaurantFragment.BRANCH_NAME));
        restaurant_cc1.setText(resultp.get(RestaurantFragment.BRANCH_ADDRESS1));
        restaurant_description.setText(resultp.get(RestaurantFragment.BRANCH_ADDRESS2));
        restaurant_id.setText(resultp.get(RestaurantFragment.ID));

        imageLoader.DisplayImage(resultp.get(RestaurantFragment.LOGO), logo);

       /* itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                resultp = data.get(position);

                Intent intent = new Intent(context,MenuActivity.class);
                intent.putExtra("id", resultp.get(RestaurantFragment.ID));
                context.startActivity(intent);

            }
        });*/
        return itemView;
    }
}
