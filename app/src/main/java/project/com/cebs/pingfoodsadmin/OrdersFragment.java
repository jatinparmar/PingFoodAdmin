package project.com.cebs.pingfoodsadmin;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final CharSequence[] items = {"New Order", "Processed", "Delivered", " Cancelled "};
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    static String ORDER_ID="id";
    static String BRANCH_ID = "branch_id";
    AlertDialog levelDialog;
    static String USER_ID = "user_id";
    static String SHIPPING_ADDRESS = "shipping_address";
    static String TOTAL = "total";
    AlertDialog.Builder alert;

    ListViewOrderAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    android.transition.Transition fade= null;
    View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Orders");
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_orders, container, false);
        listview=(ListView) view.findViewById(R.id.orders_list);
        final TextView tvw2=(TextView)view.findViewById(R.id.dummy_heading_orders);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l)
            {

                final int orderid= Integer.parseInt(((TextView)view.findViewById(R.id.txt_list_order_id)).getText().toString());

                 alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Set The Status Of Restaurant");
                alert.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int item)
                    {
                        Toast.makeText(getActivity(),"Test Successful"+item,Toast.LENGTH_LONG).show();
                        TextView tv=(TextView)view.findViewById(R.id.txt_list_action);

                        tv.setText(items[item]);
                        new OrderStatusInsert().execute(item,orderid);
                        levelDialog.dismiss();
                    }
                });
                levelDialog = alert.create();
                levelDialog.show();

            }

        });




        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fade = getActivity().getWindow().getEnterTransition();
            fade.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    tvw2.setVisibility(View.VISIBLE);
                    fade.removeListener(this);

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }




        new OrdersFragment.DownloadJSON().execute();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            jsonobject = JSONfunctions.getJSONfromURL(getResources().getString(R.string.local_ip)+"/orders.jsp");//?id="+AddressFragment.CITYID);

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("Orders");
                Log.d("JSON",jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);

                    // Retrieve JSON Objects
                    map.put("id", jsonobject.getString("id"));
                    map.put("branch_id", jsonobject.getString("branch_id"));
                    map.put("user_id", jsonobject.getString("user_id"));
                    map.put("shipping_address", jsonobject.getString("shipping_address"));
                    map.put("total", jsonobject.getString("total"));
                    map.put("status",jsonobject.getString("status"));
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
            listview=(ListView) view.findViewById(R.id.orders_list);
            // Pass the results into ListViewOrderAdapter.java

            adapter = new ListViewOrderAdapter(getActivity(), arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog

        }
    }


    private class OrderStatusInsert extends AsyncTask<Integer,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Integer... params)
        {
            JSONObject jsonObject=JSONfunctions.getJSONfromURL(getResources().getString(R.string.local_ip)+"orderstatusadmin.jsp?order_status="+params[0]+"&orderid="+params[1]);

            try {
                JSONArray jsonArray=jsonObject.getJSONArray("data");

                if(jsonArray.length()>0)
                {
                    return jsonArray.getJSONObject(0).getBoolean("status");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean)
            {

            }
            else
            {

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

}

