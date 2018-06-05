package project.com.cebs.pingfoodsadmin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddRestaurantsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddRestaurantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRestaurantsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btn_add_restaurant_details,btn_add_restaurant_branches;
    JSONObject jsonObject;
    JSONArray jsonArray;
    View v;
    ListView listview;
    EditText txt_insert_restaurant_name,txt_insert_restaurant_details,txt_insert_restaurant_tagline1,txt_insert_restaurant_tagline2,txt_insert_restaurant_website;
    ArrayList<ModelAddRestaurant> restaurant_details;
    static ArrayList<HashMap<String,String>> arraylist;
    static String RESTAURANT_NAME,RESTAURANT_DESCRIPTION,TAGLINE1,TAGLINE2,RESTAURANT_WEBSITE;
    ProgressDialog mProgressDialog;
    TextView txtTotal;

    private OnFragmentInteractionListener mListener;

    public AddRestaurantsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRestaurantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRestaurantsFragment newInstance(String param1, String param2) {
        AddRestaurantsFragment fragment = new AddRestaurantsFragment();
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
        // Inflate the layout for this fragment
        getActivity().setTitle("Add Restaurants");
         v=inflater.inflate(R.layout.fragment_add_restaurants, container, false);
        txt_insert_restaurant_name=(EditText)v.findViewById(R.id.txt_insert_restaurant_name);
        txt_insert_restaurant_details=(EditText)v.findViewById(R.id.txt_insert_restaurant_description);
        txt_insert_restaurant_tagline1=(EditText)v.findViewById(R.id.txt_insert_restaurant_tagline1);
        txt_insert_restaurant_tagline2=(EditText)v.findViewById(R.id.txt_insert_restaurant_tagline2);
        txt_insert_restaurant_website=(EditText)v.findViewById(R.id.txt_insert_restaurant_website);
        btn_add_restaurant_details=(Button)v.findViewById(R.id.btn_add_restaurant_details);
        btn_add_restaurant_branches=(Button)v.findViewById(R.id.btn_add_branches) ;
       btn_add_restaurant_details.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                       RESTAURANT_NAME=txt_insert_restaurant_name.getText().toString().trim();
                        RESTAURANT_DESCRIPTION=txt_insert_restaurant_details.getText().toString().trim();
                        TAGLINE1=txt_insert_restaurant_tagline1.getText().toString().trim();
                        TAGLINE2=txt_insert_restaurant_tagline2.getText().toString().trim();
                       RESTAURANT_WEBSITE=txt_insert_restaurant_website.getText().toString().trim();

                        if(RESTAURANT_NAME.length()==0 && RESTAURANT_WEBSITE.length()==0 && RESTAURANT_DESCRIPTION.length()==0)
                        {
                            txt_insert_restaurant_name.setError("Mandatory Field");


                        }
                        else if(RESTAURANT_WEBSITE.length()==0)
                        {
                            txt_insert_restaurant_website.setError("Mandatory Field");
                        }

                        else if(RESTAURANT_DESCRIPTION.length()==0)
                        {
                            txt_insert_restaurant_details.setError("Mandatory Field");

                        }

                        else {
                            new DownloadJSON().execute();
                            clearFields();
                            btn_add_restaurant_branches.setVisibility(View.VISIBLE);
                        }




                    }
                });
        btn_add_restaurant_branches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDisplay(new AddBranchFragment());
            }
        });
        return v;
    }
    public void clearFields(){
        txt_insert_restaurant_name.setText("");
        txt_insert_restaurant_website.setText("");
        txt_insert_restaurant_details.setText("");
        txt_insert_restaurant_tagline1.setText("");
        txt_insert_restaurant_tagline2.setText("");
        txt_insert_restaurant_name.requestFocus();
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

    private class DownloadJSON extends AsyncTask<Void, Void, Boolean> {

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
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mProgressDialog.dismiss();
            if(aBoolean)
            {
               Toast.makeText(getActivity(),"Details added successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                txt_insert_restaurant_name.setText("");
                txt_insert_restaurant_name.requestFocus();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                jsonObject = JSONfunctions.getJSONfromURL((getResources().getString(R.string.local_ip)+"/addrestaurant.jsp?res_name="+RESTAURANT_NAME+"&res_website="+RESTAURANT_WEBSITE+"&res_desc="+RESTAURANT_DESCRIPTION+"&res_cc1="+TAGLINE1+"&res_cc2="+TAGLINE2).replace(" ","%20"));
                jsonArray=jsonObject.getJSONArray("data");
                return !jsonArray.getJSONObject(0).getString("id").equals("0");
            }
            catch (Exception ex)
            {
                Log.e("Exception",ex.toString());
            }

            return false;
        }

        }

    private void updateDisplay(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    }


