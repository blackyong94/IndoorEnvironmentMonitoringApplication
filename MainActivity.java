package com.example.indoorenvironmentmonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.xml.sax.Parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button button1;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        안드로이드에서는 HttpURL Library 사용시 쓰레드를 사용해야함
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = null;
                    // Open the connection
                    URL url = new URL("http://www.atlasencontrol.com:5001/api/ba265ab638b63dd0e36bb4e1824e62df1d2a4323c04fc4a8806672350537bc1f/realtime/");
//                    누적 데이터 호출 URL 끝단에 date
//                    URL url = new URL("http://www.atlasencontrol.com:5001/api/ba265ab638b63dd0e36bb4e1824e62df1d2a4323c04fc4a8806672350537bc1f/date/");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream is = conn.getInputStream();

                    // Get the stream
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }

                    // Set the result
                    result = builder.toString();

//          REST API 호출하여 불러온 데이터(result)
                    Log.d("print",result);
                    Log.d("print",result.getClass().toString());

                    JsonParser jsonparser = new JsonParser();
                    JsonObject jsonObj = (JsonObject) jsonparser.parse(result);
                    JsonArray jsonArray = (JsonArray) jsonObj.get("sensor_list");

                    for(int i = 0; i<jsonArray.size();i++){
                        Log.d("print",jsonArray.get(i).toString());
                    }

                } catch (Exception e) {
                    Log.e("print", "GET method failed: " + e.getMessage());
//            e.printStackTrace();
                }
            }
        });

        thread.start();
    }

}



