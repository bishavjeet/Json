       package com.example.admin.tablayout;
       import android.app.Activity;
       import android.content.Intent;
       import android.support.annotation.NonNull;
       import android.support.design.widget.AppBarLayout;
       import android.support.design.widget.NavigationView;
       import android.support.design.widget.TabLayout;
       import android.support.v4.app.Fragment;
       import android.support.v4.app.FragmentManager;
       import android.support.v4.app.FragmentTransaction;
       import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
       import android.view.Menu;
       import android.view.MenuInflater;
       import android.view.MenuItem;
import android.view.View;
       import android.widget.AdapterView;
       import android.widget.ArrayAdapter;
       import android.widget.FrameLayout;
       import android.widget.ImageView;
       import android.widget.ListView;
       import android.widget.TableLayout;
       import android.widget.Toast;
       import android.widget.Toolbar;

       import java.util.List;

       public class MainActivity extends AppCompatActivity {
           TabLayout tabLayout;
           ViewPager viewPager;
           String[] array;
           Activity activity;
           FrameLayout frameLayout;
           android.support.v7.widget.Toolbar toolbar;
           private DrawerLayout drawerLayout;
           NavigationView navigationView;
           private ActionBarDrawerToggle actionBarDrawerToggle;
           @Override
           protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);

               setContentView(R.layout.activity_main);
               this.activity=activity;
               tabLayout = (TabLayout) findViewById(R.id.tabLayout);
              toolbar=findViewById(R.id.activity_my_toolbar);
              navigationView=(NavigationView)findViewById(R.id.design_navigation_view);
              frameLayout=(FrameLayout)findViewById(R.id.contentFrame);

               viewPager = (ViewPager) findViewById(R.id.viewPager);
               drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
               actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
               drawerLayout.addDrawerListener(actionBarDrawerToggle);
               actionBarDrawerToggle.syncState();
               //setSupportActionBar(toolbar);
              getSupportActionBar().setDisplayHomeAsUpEnabled(true);

               Adapter adapter = new Adapter(getSupportFragmentManager());
               adapter.AddFragment(new Frag1(), "Home");
               adapter.AddFragment(new Frag3(), "Profile");
               adapter.AddFragment(new Frag2(), "Video");
               adapter.AddFragment(new Frag4(), "Media");
               adapter.AddFragment(new Frag5(),"ECom");
               viewPager.setAdapter(adapter);
               tabLayout.setupWithViewPager(viewPager);
       }
           @Override
           public boolean onCreateOptionsMenu(Menu menu) {
               MenuInflater inflater = getMenuInflater();
               inflater.inflate(R.menu.option, menu);
               return true;
           }
           @Override
           public boolean onOptionsItemSelected(MenuItem item) {
               if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
                   return true;
               }
               return super.onOptionsItemSelected(item);
           }

       }








