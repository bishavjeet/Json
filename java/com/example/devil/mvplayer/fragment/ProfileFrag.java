package com.example.devil.mvplayer.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devil.mvplayer.Model.CategoryGetter;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.Model.SubCategoryGetter;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.adapter.CustomCategoryAdapter;
import com.example.devil.mvplayer.adapter.OnRecyclerViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class ProfileFrag extends Fragment {
    ProgressDialog progressDialog;
    TextView name,mobile,emailid;
    ImageView imageView;

    Activity activity;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_profile_frag, container, false);
        progressDialog = new ProgressDialog(getActivity());
        activity=getActivity();
        LdPreferences.putString(activity,"fragment","3");

        init(layout);
        return (layout);
    }
    public void init(View layout)

    {
        imageView=(ImageView)layout.findViewById(R.id.imgProfile);
        name=(TextView)layout.findViewById(R.id.tvName);
        emailid=(TextView)layout.findViewById(R.id.tvEmail);
        mobile=(TextView)layout.findViewById(R.id.tvMobile);
        String url = Config.Profile + "&UserId=" + LdPreferences.readString(activity, "userId");
        Log.e("Profile url ", url);
        new  HitProfile().execute(url);


    }

    public class HitProfile extends AsyncTask<String ,String, String>
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

            HttpHandler httpHandler = new HttpHandler();
            String response = httpHandler.makeServiceCall(strings[0]);

            Log.e("response ", "Profile response--" + response);

            return response;

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
                        JSONArray jsonArray= jsonObject.getJSONArray("data");
                        if(jsonArray.length()>=0) {


                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                            String name = jsonObject1.getString("name");
                            String email = jsonObject1.getString("email");
                            String mobile = jsonObject1.getString("mobile");
                        }


                        name.setText(LdPreferences.readString(activity,"name"));
                        emailid.setText(LdPreferences.readString(activity,"emailId"));
                        mobile.setText(LdPreferences.readString(activity,"mobile"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }}}}
