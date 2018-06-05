package project.com.cebs.pingfoodsadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity
{

    EditText txtEmail,txtPassword,txtFirstName,txtLastName,txtPhone;
    static String EMAIL,PASSWORD,FIRSTNAME,LASTNAME,PHONE,IMEI;
    Button btnSignUp;
    ProgressDialog mProgressDialog;
    JSONObject jsonObject;
    JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtEmail=(EditText)findViewById(R.id.txtemail);
        txtPassword=(EditText)findViewById(R.id.txtpassword);
        txtFirstName=(EditText)findViewById(R.id.txtfirstname);
        txtLastName=(EditText)findViewById(R.id.txtlastname);
        txtPhone=(EditText)findViewById(R.id.txtphone);
        btnSignUp=(Button)findViewById(R.id.email_sign_up_button);
        btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        EMAIL=txtEmail.getText().toString();
                        PASSWORD=txtPassword.getText().toString();
                        FIRSTNAME=txtFirstName.getText().toString();
                        LASTNAME=txtLastName.getText().toString();
                        PHONE=txtPhone.getText().toString();
                        TelephonyManager telephonyManager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                        IMEI= telephonyManager.getDeviceId();
                        Log.d("IMEI",IMEI);
                        new Register().execute();


                    }
                });

    }
    class Register extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(RegistrationActivity.this);
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
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else
            {
                txtEmail.requestFocus();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                jsonObject = JSONfunctions.getJSONfromURL(getResources().getString(R.string.local_ip)+"/register.jsp?email="+EMAIL+"&password="+PASSWORD+"&firstname="+FIRSTNAME+"&lastname="+LASTNAME+"&phone="+PHONE+"&imei="+IMEI);
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
}