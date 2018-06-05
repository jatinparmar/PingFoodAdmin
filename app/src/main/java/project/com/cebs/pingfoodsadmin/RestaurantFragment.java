package project.com.cebs.pingfoodsadmin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */

public class RestaurantFragment extends Fragment {

    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    int city_id=0;
    FloatingActionButton btn_add_restaurant;
    static String ID="id";
    static String BRANCH_NAME = "res_name";
    static String BRANCH_ADDRESS1 = "res_cc1";
    static String BRANCH_ADDRESS2 = "res_desc";
    static String LOGO = "logo";
    static int BRANCHID;
    View view;
    public RestaurantFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("All Restaurants");
        view=inflater.inflate(R.layout.fragment_restaurant, container, false);
        listview=(ListView) view.findViewById(R.id.restaurants_list);
        btn_add_restaurant=(FloatingActionButton)view.findViewById(R.id.btn_add_restaurant);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                /*
                Map<String,String> m=(Map<String,String>)arraylist.get(position);
                int branchid= Integer.parseInt( m.get("id"));
                BRANCHID=branchid;
                Bundle b=new Bundle();
                b.putInt("id",branchid);
                DashboardFragment fragment=new DashboardFragment();
                fragment.setArguments(b);
                Intent i=new Intent(getActivity(),MenuActivity.class);
                i.putExtra("id",branchid);
                startActivity(i);
                */
                updateDisplay(new AddBranchFragment());
            }
        });

        btn_add_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDisplay(new AddRestaurantsFragment());

            }
        });
        new DownloadJSON().execute();
        return view;
    }


    private void updateDisplay(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog title
            mProgressDialog.setTitle("Ping Food");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
           jsonobject = JSONfunctions.getJSONfromURL(getResources().getString(R.string.local_ip)+"/restaurants.jsp");//?id="+AddressFragment.CITYID);

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("Restaurants");
                Log.d("JSON",jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);

                    // Retrieve JSON Objects
                    map.put("id", jsonobject.getString("id"));
                    map.put("res_name", jsonobject.getString("res_name"));
                    map.put("res_cc1", jsonobject.getString("res_cc1"));
                    map.put("res_desc", jsonobject.getString("res_desc"));
                    map.put("logo", jsonobject.getString("logo"));
                    // Set the JSON Objects into the array
                    arraylist.add(map);
                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Locate the listview in listview_main.xml
            mProgressDialog.dismiss();
            listview = (ListView) view.findViewById(R.id.restaurants_list);
            // Pass the results into ListViewAdapter.java

            adapter = new ListViewAdapter(getActivity(), arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog

        }
    }

}