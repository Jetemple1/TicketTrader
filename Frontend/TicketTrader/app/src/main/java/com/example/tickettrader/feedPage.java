package com.example.tickettrader;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tickettrader.Adapters.feedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class feedPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView mFeed;
    private feedAdapter mAdapter;
    private ImageButton bRefresh;
    RequestQueue requestQueue;
    String url;

    List<feed> feedData =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);
        mFeed = findViewById(R.id.ticketList);
        bRefresh = findViewById(R.id.refresh);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feed");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        requestQueue = Volley.newRequestQueue(this);
//        url = "https://api.androidhive.info/json/contacts.json";
//        url = "https://api.myjson.com/bins/77i0u";
        url = "https://api.myjson.com/bins/dndee"; //With opponent and logo
        refresh(url);

        bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh("https://api.myjson.com/bins/i8mh2" );//Refresh data test
            }
        });



    }


    private void refresh(String url) {

        feedData.clear();

//    url = "https://api.myjson.com/bins/77i0u";
        {
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("ticket");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json_data = jsonArray.getJSONObject(i);

                            feed Feed = new feed();
                            Feed.setSport(json_data.getString("sport"));
//                    Feed.setOpponent(json_data.getString("opponent"));
                            Feed.setLogo(json_data.getString("logo"));
                            Feed.setSport(json_data.getString("sport"));

                            //User.setGameLocation(json_data.getString("gameLocation"));
                            //User.setTicketiD(json_data.getString("ticketID"));
//                        User.setLogo(json_data.getString("image"));
                            Feed.setGame_Date(json_data.getString("game_date"));
                            Feed.setPrice(json_data.getInt("price"));

                            feedData.add(Feed);

                        }

                        mAdapter = new feedAdapter(feedPage.this, feedData);
                        mFeed.setAdapter(mAdapter);
                        mFeed.setLayoutManager(new LinearLayoutManager(feedPage.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(feedPage.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.feed) {
            // Handle the camera action
        } else if (id == R.id.sell) {
            Intent sell = new Intent(com.example.tickettrader.feedPage.this, com.example.tickettrader.sellPage.class);
            startActivity(sell);
        } else if (id == R.id.userAccount) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}