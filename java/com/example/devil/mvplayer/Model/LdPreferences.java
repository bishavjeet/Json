package com.example.devil.mvplayer.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Devil on 3/21/2018.
 */
public class LdPreferences
{
 private static SharedPreferences getPreference(Context context) {
  return context.getSharedPreferences("LD_PREF", Context.MODE_PRIVATE);
 }

 public static String readString(Context context, String key) {
  return getPreference(context).getString(key,"");
 }

 public static void putString(Context context,String key,String value){
  getPreference(context).edit().putString(key,value).apply();
 }

 public static void getString(Context context , String key , String value){
  getPreference(context).getString(key,value);
 }
}