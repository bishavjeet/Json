package com.example.devil.mvplayer.fragment.Setting;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.Activity.home.Home;
import com.example.devil.mvplayer.Model.Config;
import com.example.devil.mvplayer.Model.HttpHandler;
import com.example.devil.mvplayer.Model.LdPreferences;
import com.example.devil.mvplayer.R;
import com.example.devil.mvplayer.Utils.Util;
import com.example.devil.mvplayer.fragment.ProfileFrag;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by whizkraft on 8/5/18.
 */

public class SettingFragment extends Fragment {
    Activity activity;
    Dialog changepassworddialog;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.settinglayout, container, false);
        activity = getActivity();
        ButterKnife.bind(this, view1);
        LdPreferences.putString(activity, "back_status", "true");
        LdPreferences.putString(activity, "fragment", "9");
        initview(view1);
        ((Home) getActivity()).setToolBaricon();
        return view1;
    }

    private void initview(View view1) {






    }
    @OnClick({R.id.changePassword,R.id.txtUserProfile})
    public void buttonClicks(View view) {
        switch (view.getId()) {
            case R.id.changePassword:
                changepassworddialog = Util.changepassword(activity, R.layout.changepassword);
                final EditText edtOldPassword = (EditText) changepassworddialog.findViewById(R.id.editOldPassword);
                final EditText newPassword = (EditText) changepassworddialog.findViewById(R.id.editNewPassword);
                final EditText confirmPassword = (EditText) changepassworddialog.findViewById(R.id.editConfirmPassword);

                TextView textChangePassword = (TextView) changepassworddialog.findViewById(R.id.changePasswordText);


                final Dialog finalChangepassworddialog = changepassworddialog;
                textChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        passwordValidation(edtOldPassword, newPassword, confirmPassword, finalChangepassworddialog);
                    }
                });
                changepassworddialog.show();

                break;
            case R.id.txtUserProfile:
                FragmentManager fm = getFragmentManager();

// Transaction start
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentFrame, new ProfileFrag());

                ft.commit();
                break;

        }
    }

    private void passwordValidation(EditText edtOldPassword, EditText newPassword, EditText confirmPassword, Dialog changepassworddialog) {

        if (edtOldPassword.getText().toString().isEmpty() || newPassword.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Please fill all the details", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
            Toast.makeText(activity, "Password didn't match.", Toast.LENGTH_SHORT).show();
        } else if (edtOldPassword.getText().toString().trim().equals(newPassword.getText().toString().trim())) {
            Toast.makeText(activity, "Please choose different password", Toast.LENGTH_SHORT).show();
        }  else {
            String ChangePassword= Config.ChangePassword+"&UserId="+LdPreferences.readString(activity,"userId")+"&oldpassword="+edtOldPassword.getText().toString()+"&newpassword="+newPassword.getText().toString();
            Log.e("url",ChangePassword);
            new HitChangePasswordService().execute(ChangePassword);

            changepassworddialog.dismiss();
        }
    }
    class HitChangePasswordService extends AsyncTask<String, String,String>{
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog.setTitle("Loading....");

            progressDialog.setMessage("Please Wait....");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpHandler sh= new HttpHandler();
            String jsonStr =sh.makeServiceCall(strings[0]);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null){
                try{
                    JSONObject jsonObject=new JSONObject(s);
                    String code=jsonObject.getString("code");
                    String status =jsonObject.getString("status");
                    String message= jsonObject.getString("message");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(activity,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }
    }




}
