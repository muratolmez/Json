package com.example.murat.json;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends Activity {


    TextView httpStuff;
    TextView httpStuff1;
    TextView httpStuff2;
    HttpClient client;
    String girilen;
    EditText editText;
    String langa;
    String lang;
    String langa1;
    String lang1;
    String langa2;
    String lang2;
    String langa3;
    String lang3;
    String lang4;
    String langa4;
    TextView httpStuff3;
    TextView httpStuff4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);



        setContentView(R.layout.httpex);
        httpStuff = (TextView) findViewById(R.id.tvHttp);
        httpStuff1 = (TextView) findViewById(R.id.tvHttp1);
        httpStuff2 = (TextView) findViewById(R.id.tvHttp2);
        httpStuff3=(TextView)findViewById(R.id.tvHttp3);
        httpStuff4=(TextView)findViewById(R.id.tvHttp4);

        Button button = (Button) findViewById(R.id.button);

        editText = (EditText) findViewById(R.id.editText);

        client = new DefaultHttpClient();


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                girilen = editText.getText().toString();
                new Read().execute();


            }
        });


    }

    public void lastTweet(String sulo) throws IOException, JSONException {

        String suleyman = sulo;
        String osman = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + suleyman + "%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback";

        StringBuilder url = new StringBuilder(osman);

        HttpGet get = new HttpGet(url.toString());
        HttpResponse response = client.execute(get);
        int status = response.getStatusLine().getStatusCode();

        if (status == 200) {
            HttpEntity entity = response.getEntity();
            String data = EntityUtils.toString(entity);
            JSONObject JSON = new JSONObject(data);
            JSONObject last2 = JSON.getJSONObject("query");
            JSONObject last3 = last2.getJSONObject("results");
            JSONObject last4 = last3.getJSONObject("quote");
            lang = last4.getString("PercentChangeFromYearLow");
            lang1 = last4.getString("PercentChangeFromYearHigh");
            lang2 = last4.getString("Bid");
            lang3=last4.getString("PriceSales");
            lang4=last4.getString("AverageDailyVolume");

        }


    }

    public class Read extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                lastTweet(girilen);
                langa = lang;
                langa1 = lang1;
                langa2 = lang2;
                langa3=lang3;
                langa4=lang4;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {


            httpStuff.setText(langa);
            httpStuff1.setText(langa1);
            httpStuff2.setText(langa2);
            httpStuff3.setText(langa3);
            httpStuff4.setText(langa4);
        }
    }


}
