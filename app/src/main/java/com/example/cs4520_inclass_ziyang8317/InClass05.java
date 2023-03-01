/*
* CS5520 In class assignment 05
* Name: Ziyang Wang
* Date: 2023-02-27
* */

package com.example.cs4520_inclass_ziyang8317;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InClass05 extends AppCompatActivity {
    private final String TAG = "final";

    private ArrayList<String> validKeywords;
    private ArrayList<String> image_resources;

    private int curr_index;

    // elements of the user interface.
    private OkHttpClient client;

    private EditText keyword_enter;
    private ProgressBar progress_loading_image;
    private TextView loading_indicator;

    private ImageView imageLoad;

    private Button buttonGO;

    private ImageView button_prev;
    private ImageView button_next;


    private boolean isInternetAvailable() {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(1000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
        }
        return inetAddress != null && !inetAddress.equals("");
    }

    private void reset_blank_image(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageLoad.setImageResource(R.drawable.solid_white);
            }
        });

    }

    public void disable_buttons(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button_prev.setOnClickListener(null);
                button_next.setOnClickListener(null);
            }
        });
    }

    public void test_connection(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!isInternetAvailable()){
                    Toast.makeText(getApplicationContext(),"No Internet Connection!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return;
    }

    public void load_image(int i,String dir){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress_loading_image.setVisibility(View.VISIBLE);
                loading_indicator.setText("Loading "+dir+" ...");
                Glide.with(InClass05.this).load(image_resources.get(i)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progress_loading_image.setVisibility(View.GONE);
                        loading_indicator.setText("");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress_loading_image.setVisibility(View.GONE);
                        loading_indicator.setText("");
                        return false;
                    }
                }).into(imageLoad);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);

        setTitle("Image Search");

        // match the elements with the variables.
        client = new OkHttpClient();
        imageLoad = findViewById(R.id.image_load);
        keyword_enter = findViewById(R.id.keyword_enter);
        buttonGO = findViewById(R.id.buttonGO);
        progress_loading_image = findViewById(R.id.progress_loading_image);
        progress_loading_image.setVisibility(View.GONE);

        loading_indicator = findViewById(R.id.loading_indicator);
        loading_indicator.setTextColor(Color.BLACK);
        loading_indicator.setTypeface(null, Typeface.BOLD);
        loading_indicator.setTextSize(20);

        button_prev = findViewById(R.id.button_prev);
        button_next = findViewById(R.id.button_next);


        // check if the input is valid and pop up the toast
        buttonGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check internet access.
                if(!isInternetAvailable()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"No Internet Connection!",Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }


                String user_input = keyword_enter.getText().toString();
                if(validKeywords.contains(user_input)){
                    //build the url according to input keyword
                    HttpUrl url = HttpUrl.parse("http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/images/retrieve").newBuilder().addQueryParameter("keyword",user_input).build();
                    Request req = new Request.Builder().url(url).build();
                    //get image urls using the built url
                    client.newCall(req).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),"No Images Found",Toast.LENGTH_SHORT).show();
                    }
                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            ResponseBody body = response.body();
                            String bodyString = body.string();
                            image_resources = new ArrayList<>(Arrays.asList(bodyString.split("\n")));
                            if (image_resources.size() > 1){
                                //start loading the image.
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // set the current location of the image shown.
                                        curr_index = 0;
                                        // show the progress bar spinning and the loading message
                                        progress_loading_image.setVisibility(View.VISIBLE);
                                        // set onclick listeners for two buttons.
                                        button_prev.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                test_connection();
                                                if(curr_index == 0){
                                                    int last = image_resources.size() - 1;
                                                    load_image(last,"previous");
                                                    curr_index = last;
                                                } else{
                                                    load_image(curr_index - 1,"previous");
                                                    curr_index = curr_index - 1;

                                                }
                                            }
                                        });
                                        button_next.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                test_connection();
                                                if(curr_index == image_resources.size() - 1){
                                                    load_image(0,"next");
                                                    curr_index = 0;
                                                } else{
                                                    load_image(curr_index + 1,"next");
                                                    curr_index = curr_index + 1;
                                                }
                                            }
                                        });


                                        // loading the first image of the giving keyword.
                                        loading_indicator.setText("Loading...");
                                        Glide.with(view).load(image_resources.get(0)).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                progress_loading_image.setVisibility(View.GONE);
                                                loading_indicator.setText("");
                                                Toast.makeText(getApplicationContext(),"No Images Found",Toast.LENGTH_SHORT).show();
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                progress_loading_image.setVisibility(View.GONE);
                                                loading_indicator.setText("");
                                                return false;
                                            }
                                        }).into(imageLoad);
                                    }
                                });

                            } else if(image_resources.size() == 1 &&image_resources.get(0) == ""){
                                reset_blank_image();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(),"No Images Found",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                disable_buttons();
                            } else if(image_resources.get(0) != "" && image_resources.size() == 1){
                                load_image(0,"");
                                disable_buttons();
                            }
                        }
                    });

                }else{
                    Toast.makeText(getApplicationContext(),"No Images Found",Toast.LENGTH_SHORT).show();                  }
            }
            });


        //use OKhttp to get the keywords.
        Request request = new Request.Builder()
                .url("http://ec2-54-164-201-39.compute-1.amazonaws.com/apis/images/keywords")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String[] strSplit = responseBody.string().split(",");
                    validKeywords = new ArrayList<String>(Arrays.asList(strSplit));
                }
                }

            });





        }

    }