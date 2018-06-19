package com.example.devil.mvplayer.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText Emailid,CnfrmPassword,Password,Name,Mobile;
    ProgressDialog progressDialog;

    Button Login,Register;
    String val="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activity=this;
        init();

    }
    public void init() {
        Name=(EditText)findViewById(R.id.editTextName);
        Emailid = (EditText) findViewById(R.id.editTextEmail);
        Password = (EditText) findViewById(R.id.editTextPassword);
        CnfrmPassword = (EditText) findViewById(R.id.editTextCnfPassword);
        Mobile=(EditText)findViewById(R.id.etMobile);
        Register = (Button) findViewById(R.id.btnReg);
        Login=(Button)findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(activity);

     Login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startLoginActvity();
         }
     });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)

            {

                validateValues();


            }
        });

    }

           private void validateValues() {
               if (Emailid.getText().toString().isEmpty()) {
                   Toast.makeText(activity, "Please enter valid email", Toast.LENGTH_LONG).show();
               } else if (Password.getText().toString().isEmpty()) {
                   Toast.makeText(activity, "Please enter the password", Toast.LENGTH_LONG).show();
               }    else if(Name.getText().toString().isEmpty()){
                   Toast.makeText(activity,"Pleas enter the user name",Toast.LENGTH_LONG).show();
               } else if (Password.getText().toString().length() < 6) {
                   Toast.makeText(activity, "Password too short", Toast.LENGTH_SHORT).show();

               } else if (!Password.getText().toString().equals(CnfrmPassword.getText().toString())) {
                   Toast.makeText(activity, "Password mistmatch", Toast.LENGTH_SHORT).show();
               } else if (!Emailid.getText().toString().matches(val)) {

                   Toast.makeText(activity, "Invalid email format", Toast.LENGTH_LONG).show();

               } else {
                   String url = Config.getRegister + "&name=" + Name.getText().toString() + "&email=" + Emailid.getText().toString() + "&password=" + Password.getText().toString() + "&mobile=" + Mobile.getText().toString();
                   Log.e("register url ", url);
                   new Test().execute(url);

               }
           }
    public class Test extends AsyncTask<String,String,String>
           {
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
                   progressDialog.dismiss();
                   super.onPostExecute(s);
                   if (s != null) {

                       try {
                           JSONObject jsonObject = new JSONObject(s);
                           String code = jsonObject.getString("code");
                           String message = jsonObject.getString("status");
                           if (code.equalsIgnoreCase("1")) {
                               JSONArray jsonArray = jsonObject.getJSONArray("data");
                               JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                               String id = jsonObject1.getString("id");
                               LdPreferences.putString(activity, "user_id", id);

                               startLoginActvity();

                           } else {

                               Toast.makeText(activity, "" + message, Toast.LENGTH_SHORT).show();
                           }


                       } catch (JSONException e) {
                           e.printStackTrace();
                       }


                   } else {
                       Toast.makeText(activity, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                   }
               }
           }

    private void startLoginActvity() {
        Intent intent = new Intent(activity, Login.class);
        startActivity(intent);
        finish();

    }
           }

