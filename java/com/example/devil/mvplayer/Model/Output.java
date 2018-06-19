package com.example.devil.mvplayer.Model;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devil.mvplayer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devil on 3/22/2018.
 */

public class Output extends Activity {
    String Email;
    String Password;
    String Confirm;
    TextView txt;
    ProgressDialog progressDialog;
    Activity activity;
    DataBase dataBase;
    List<Getter>list;
    Dialog dialog;
    ArrayList<SubCategoryGetter>sublist;
    ArrayList<CategoryGetter>categorylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output);
        activity = this;
       /* Email=getIntent().getExtras().getString("EMAIL");
        Password=getIntent().getExtras().getString("PASSWORD");
        Confirm=getIntent().getExtras().getString("CONFIRM");*/
        progressDialog = new ProgressDialog(activity);
        dataBase = new DataBase(activity);
        categorylist = new ArrayList<>();
        // dialog();
      /*  dialog = Utility.createBidForm(activity);
        dialog.setCancelable(true);
        dialog.show();*/
        intview();


      /*  txt=(TextView) findViewById(R.id.view);
        txt.setText("Email: "+Email+"\n"+"Password: "+Password+"\n"+"Confirm Password: "+Confirm);*/

    }


    public void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
    }

    private void intview() {

        new Test().execute(Config.getCategory);

    }

    public class Test extends AsyncTask<String ,String,String>{

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

            Log.e("reposne ", "login response--"+response);

            return response;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                String code = jsonObject.getString("code");
                if (code.equalsIgnoreCase("1")){

                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("bollywood");
                    String name = jsonObject2.getString("name");
                    String id = jsonObject2.getString("id");
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("data");
                    sublist = new ArrayList<>();
                    JSONArray jsonArray = jsonObject3.getJSONArray("bollywood");
                    if (jsonArray.length()>0){

                        for (int i =0;i<jsonArray.length();i++){


                            JSONObject jsonObject4 = jsonArray.getJSONObject(i);

                            String subid = jsonObject4.getString("id");
                            String subname = jsonObject4.getString("name");
                            String songurl = jsonObject4.getString("location");
                            String  releasedate = jsonObject4.getString("releaseDate");
                            String status = jsonObject4.getString("status");
                            String artist = jsonObject4.getString("artist");
                            String image = jsonObject4.getString("Image");
                            String songid = jsonObject4.getString("song_id");
                            SubCategoryGetter subCategorygetter = new SubCategoryGetter(subid,subname,songurl,releasedate,"vijay",status,image,songid);
                            sublist.add(subCategorygetter);

                        }

                        CategoryGetter categoryGetter = new CategoryGetter(id,name,sublist);
                        categorylist.add(categoryGetter);

                        Log.e("list"," "+categorylist.get(0).getCatName()+"\n"+categorylist.get(0).getList().get(0).getImageurl());


                    }
                    else {

                    }



                }
                else {

                    Toast.makeText(activity, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (s!=null){


            }
            else {

                Toast.makeText(activity, "Plesse check your internet connection", Toast.LENGTH_SHORT).show();
            }


        }
    }
}

