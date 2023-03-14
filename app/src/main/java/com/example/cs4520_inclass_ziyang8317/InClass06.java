/*
 * CS5520 In class assignment 06
 * Name: Ziyang Wang
 * Date: 2023-03-13
 * */


package com.example.cs4520_inclass_ziyang8317;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InClass06 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner country_spinner;
    private Spinner category_spinner;
    private Button search_button;
    private final String TAG = "final";
    private final String apiKey = "e4615ecddd214111abbacfef7d7d5db1";
    private OkHttpClient client;
    private Map<String, String> countryDict;
    private Map<String, String> categoryDict;

    private Articles article_list = new Articles();
    private RecyclerView recyclerView_articles;

    private ArticleAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class06);

        //set the title for this screen
        setTitle("News Articles");
        client = new OkHttpClient();
        adapter = new ArticleAdapter(this,article_list.getArticles());

        //create the hashmaps to store values of countries and categories
        countryDict = new HashMap<String,String>();
        countryDict.put("China","cn");
        countryDict.put("Japan","jp");
        countryDict.put("United States","us");
        countryDict.put("France","fr");
        countryDict.put("Russia","ru");
        countryDict.put("India","in");




        categoryDict = new HashMap<String,String>();
        categoryDict.put("Business","business");
        categoryDict.put("Entertainment","entertainment");
        categoryDict.put("General","general");
        categoryDict.put("Health","health");
        categoryDict.put("Science","science");
        categoryDict.put("Sports","sports");
        categoryDict.put("Technology","technology");








        //match variables with the elements created for UI
        recyclerView_articles = findViewById(R.id.recyclerView_articles);
        country_spinner = findViewById(R.id.spinner_country);
        ArrayAdapter<CharSequence> country_adapter = ArrayAdapter.createFromResource(this,R.array.country_spinner_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        country_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        country_spinner.setAdapter(country_adapter);
        country_spinner.setOnItemSelectedListener(this);


        category_spinner = findViewById(R.id.spinner_category);
        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(this,R.array.category_spinner_items, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category_spinner.setAdapter(category_adapter);
        category_spinner.setOnItemSelectedListener(this);


        search_button = findViewById(R.id.button_search_news);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country_selected = countryDict.get(country_spinner.getSelectedItem().toString());
                String category_selected = categoryDict.get(category_spinner.getSelectedItem().toString());
                getNews(country_selected,category_selected);

                recyclerView_articles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //recyclerView_articles.setAdapter(new ArticleAdapter(getApplicationContext(),article_list.getArticles()));
                recyclerView_articles.setAdapter(adapter);


            }
        });



    }

    private void getNews(String country,String category){
        HttpUrl url = HttpUrl.parse("https://newsapi.org/v2/top-headlines").newBuilder().addQueryParameter("country",country)
                .addQueryParameter("category",category)
                .addQueryParameter("apiKey",apiKey)
                .build();
        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gsonData = new Gson();
                    String stringResponse = response.body().string();
                    JSONObject jj = null;
                    try {
                        jj = new JSONObject(stringResponse);
                        String articles = String.valueOf(jj.getJSONArray("articles"));
                        JSONObject newsArray = new JSONObject();
                        newsArray.put("articles",jj.getJSONArray("articles"));
                        Articles newsList = gsonData.fromJson(newsArray.toString(), Articles.class);
                        article_list.setArticles(newsList.getArticles());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ArticleAdapter(getApplicationContext(),article_list.getArticles());
                                recyclerView_articles.setAdapter(adapter);
                            }
                        });

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Failed to get responses.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}