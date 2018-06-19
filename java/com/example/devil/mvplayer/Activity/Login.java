package com.example.devil.mvplayer.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText Emailid, Password;
    ProgressDialog progressDialog;
    Button Login,Register;
    String val="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=this;
        Init();

    }
    public void Init() {

        Emailid = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        Register=(Button) findViewById(R.id.btnreg);
        Login = (Button) findViewById(R.id.btnLogin);
        progressDialog=new ProgressDialog(activity);

       Register.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v)

            {
                InitView();

            }
        });

       Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)

            {
                validateValues();
            }
        });
    }


    private void validateValues() {
        if (Emailid.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Please enter valid email", Toast.LENGTH_LONG).show();
        }
        else if (Password.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Please enter the password", Toast.LENGTH_LONG).show();

        } else if (Password.getText().toString().length() < 6) {
            Toast.makeText(activity, "Password too short", Toast.LENGTH_SHORT).show();

        }

        else if(!Emailid.getText().toString().matches(val))
        {
            Toast.makeText(activity, "invalid format", Toast.LENGTH_SHORT).show();;
        }
        else
        {

            String url = Config.loginUrl + "&email=" + Emailid.getText().toString() + "&password=" + Password.getText().toString();
            Log.e("loginUrl ",url);
            new HitLoginService().execute(url);

        }
    }
    private void InitView()
    {
        Intent in=new Intent(activity,Register.class);

        startActivity(in);

        finish();
    }

    public class HitLoginService extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Loading.....");
            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(true);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {


            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(strings[0]);


            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s!=null){

                try {
                    JSONObject jsonObject = new JSONObject(s);

                    String code = jsonObject.getString("code");

                    String message = jsonObject.getString("status");

                    if (code.equalsIgnoreCase("1")){

                        LdPreferences.putString(activity,"login_status","true");

                        startHomeActivity();
                    }
                    else {

                        Toast.makeText(activity, ""+message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else {
                Toast.makeText(activity, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void startHomeActivity() {
        Intent intent = new Intent(activity,Home.class);
        startActivity(intent);
        finish();
    }
}




