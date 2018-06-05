package project.com.cebs.pingfoodsadmin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements AddBranchFragment.OnFragmentInteractionListener,AccountFragment.OnFragmentInteractionListener, AddRestaurantsFragment.OnFragmentInteractionListener,QuotationsFragment.OnFragmentInteractionListener,OrdersFragment.OnFragmentInteractionListener,TrackingFragment.OnFragmentInteractionListener,NavigationView.OnNavigationItemSelectedListener,DashboardFragment.OnFragmentInteractionListener/*,AboutFragment.OnFragmentInteractionListener,RequestQuotation.OnFragmentInteractionListener,OrderTrackingFragment.OnFragmentInteractionListener,MyProfileFragment.OnFragmentInteractionListener*/

        {

    TextView txtUserName, txtUserEmail;
            MenuItem itemMenu;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        try {
            drawer.setDrawerListener(toggle);
        } catch (NullPointerException ex) {
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        txtUserEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.useremail);
        txtUserName.setText(LoginActivity.USERNAME);
        txtUserEmail.setText(LoginActivity.USEREMAIL);
        navigationView.setNavigationItemSelectedListener(this);
        updateDisplay(new DashboardFragment());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        //for hiding the item from optionmenu in fragment


       //syntax here  /* itemMenu=menu.findItem(R.id.action_logout); */

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            return true;
        }
        if(id==R.id.action_logout)
        {
            new Logout().execute();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        switch (id) {

            case R.id.nav_dashboard:

                updateDisplay(new DashboardFragment());

                //calling the setVisible method to invisible the item from options menu

                //syntax here   /*item.setVisible(false);*/

                break;

            case R.id.nav_restaurants:
                updateDisplay(new RestaurantFragment());


                break;

            /*
            case R.id.nav_offers:
                updateDisplay(new OrderTrackingFragment());
                break;
            */


            case R.id.nav_orders:
                updateDisplay(new OrdersFragment());
                break;

            case R.id.nav_tracking:
                updateDisplay(new TrackingFragment());
                break;

            case R.id.nav_request:
                updateDisplay(new QuotationsFragment());
                break;

            case R.id.nav_account:
                updateDisplay(new AccountFragment());
                break;
            /*
            case R.id.nav_about:
                updateDisplay(new AboutFragment());
                break;
            case R.id.nav_request:
                updateDisplay(new RequestQuotation());
                break;
                */

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateDisplay(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

            private class Logout extends AsyncTask<Void,Void,Boolean>
            {
                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    if(aBoolean)
                    {
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Boolean doInBackground(Void... voids)
                {

                    JSONObject jsonObject=JSONfunctions.getJSONfromURL(getResources().getString(R.string.local_ip)+"logout.jsp?uid="+LoginActivity.USERID);
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
            }


}