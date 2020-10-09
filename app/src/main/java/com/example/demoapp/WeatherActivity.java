package com.example.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    EditText adress;
    TextView resultTextView;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                String message = "";
                JSONObject jsonObject = new JSONObject(result);
                String weatherInfo = jsonObject.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject jsonPart = arr.getJSONObject(i);
                    String main = "";
                    String description = "";
                    main = jsonPart.getString("main");
                    description = jsonPart.getString("description");
                    if (main != "" && description != "") {
                        message += main + ": " + description + "\r\n";
                    }
                }
                if (message != "") {
                    resultTextView.setText(message);//text view here
                } else {
                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
                }
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
            }
        }
    }

    public double[] getLocationFromAddress(Context context, String strAddress) {

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
//        LatLng p1 = null;
        try {
            addresses = geocoder.getFromLocationName(strAddress, 5);
            if (addresses == null) {
                return null;
            }
            if(addresses.size() > 0) {
                double latitude= addresses.get(0).getLatitude();
                double longitude= addresses.get(0).getLongitude();
                return new double[]{latitude, longitude};
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void findWeather(View view) {

        Log.i("adress", adress.getText().toString());

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(adress.getWindowToken(), 0);

        try {
            String encodedCityName = URLEncoder.encode(adress.getText().toString(), "UTF-8");
            double coord[] = getLocationFromAddress(this, adress.getText().toString());
            DownloadTask task = new DownloadTask();
            task.execute("https://api.openweathermap.org/data/2.5/onecall?lat=" + coord[0] + "&lon=" + coord[1] + "&appid=ce5f605af52583ce5c00a6613ec0e05f");//api key

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        adress =  findViewById(R.id.address);
        resultTextView = findViewById(R.id.resultTextView);
    }

}