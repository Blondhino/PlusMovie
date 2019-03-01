package com.blondi.movie;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


/**
 * Created by Enio on 1/29/2019.
 */

public class DetailActivity extends AppCompatActivity{


    private RequestQueue mQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getIncomingIntent();


    }


    private void getIncomingIntent(){



        String imageUrl=getIntent().getStringExtra("image_URL");
        String title = getIntent().getStringExtra("title");
        String IMDBkey = getIntent().getStringExtra("key");
        String URL_JSON=urlBuilder(IMDBkey);

        Log.e("URL: ",URL_JSON);


JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, URL_JSON, null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String rating = response.getString("imdbRating");
                    String duration = response.getString("Runtime");
                    String year = response.getString("Year");
                    String description = response.getString("Plot");

                    TextView tvPlot=findViewById(R.id.tvDetailPlot);
                    TextView tvRating = findViewById(R.id.tvDetailIMDBRating);
                    TextView tvYear= findViewById(R.id.tvDetailYear);
                    TextView tvDuration = findViewById(R.id.tvDetailDuration);

                    tvPlot.setText(description);
                    tvDuration.setText(duration);
                    tvYear.setText(year);
                    tvRating.setText(rating);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },

        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

   RequestQueue mQueue = Volley.newRequestQueue(this);
   mQueue.add(request1);

    TextView Title= findViewById(R.id.tvDetailTitle);
    Title.setText(title);
    ImageView image=findViewById(R.id.ivDetailPoster);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);


    }

    private String urlBuilder(String imdBkey) {

        return "http://www.omdbapi.com/?i="+imdBkey+"&plot=full&apikey=11a19ffa";
    }


}
