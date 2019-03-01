package com.blondi.movie;

import android.app.VoiceInteractor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListenr{

private ImageView ivBackground;
private EditText etSearch;
private ImageButton btnSearch;
private String URL_JSON;
public String Title;
public Toolbar toolbar;
public String API_KEY="11a19ffa";
public String BASE_URL="http://www.omdbapi.com/";
private RequestQueue mQueue;
private List<Movie> movieList= new ArrayList<>();
private RecyclerView recycler;
 public int Pages;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menue,menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivBackground=findViewById(R.id.ivBackground);
        mQueue = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.rvRecyclerView);
        toolbar = findViewById(R.id.Toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);




            }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.Search:
                ivBackground.animate().alpha(0f);
                openDialog();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {

Dialog dialog = new Dialog();
dialog.show(getSupportFragmentManager(),"dialog");
    }


    @Override
    public void applyData(String Title, String Type) {
        movieList.clear();
        Log.e("From Dialog",Title+" "+Type);
        jsonRequest(Title, Type);    // Type (movie,game,series)
        toolbar.setTitle("Results for:  "+Title);

    }

    private void jsonRequest(String Title, String Type) {

        URL_JSON=urlBuilder(Title, Type);


        JsonObjectRequest request0 = new JsonObjectRequest(Request.Method.GET, URL_JSON, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            int Results = Integer.parseInt(response.getString("totalResults"));
                            getPages(Results);

                            Toast.makeText(MainActivity.this,"Total Results: "+ String.valueOf(Results) , Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                               Toast.makeText(MainActivity.this, "Request not found :/", Toast.LENGTH_SHORT).show();

                        }
                    }

                    public void getPages(int results) {
                     int temp = results;
                     results/=10;
                     if(temp%10 != 0)
                         results++;
                        Pages=results;



                        //


                        for(int a=0; a<Pages; a++){

                            String DinamicURL = URL_JSON + "&page="+String.valueOf(a+1);
                            Log.e("dinamic",DinamicURL);
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, DinamicURL, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            //

                                            try {

                                                JSONArray jsonArray = response.getJSONArray("Search");


                                                for (int i = 0; i<jsonArray.length(); i++){
                                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                    Movie movie = new Movie();
                                                    movie.setTitle(jsonObject.getString("Title"));
                                                    movie.setYear(jsonObject.getString("Year"));
                                                    movie.setType(jsonObject.getString("Type"));
                                                    movie.setPoster(jsonObject.getString("Poster"));
                                                    movie.setImdbID(jsonObject.getString("imdbID"));



                                                    movieList.add(movie);
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            //

                                            setupRecyclerview(movieList);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }


                            );

                            mQueue.add(request);





                        }


                        //



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }
        );

        mQueue.add(request0);

//

    }

    private String urlBuilder(String title, String Type) {
        String FinalURL;
        title.replace(" ","%20");
        FinalURL=BASE_URL+"?s="+title+"&type="+Type+"&apikey="+API_KEY;
        return  FinalURL;
    }

    private void setupRecyclerview(List<Movie> movieList) {

      RecyclerAdapter adapter = new RecyclerAdapter(this,movieList);
      recycler.setLayoutManager(new LinearLayoutManager(this));
      recycler.setAdapter(adapter);
    }
}
