package com.example.anil.getvaluefromnet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.text);
    }

    public void getvalue(View view) {
        new GetvaluefromInternet(this).execute();
    }

    private class GetvaluefromInternet extends AsyncTask<String,Void,String>{
        Context ct;
        ProgressDialog pd;
        public GetvaluefromInternet(MainActivity mainActivity) {
            this.ct=mainActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(ct);
            pd.setTitle("Please Wait!");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url_link="http://api.thingspeak.com/channels/313127/fields/1/last?api_key=MW5WU6XC2WXJAWHI";
            try {
                HttpClient httpClient=new DefaultHttpClient();
                HttpGet httpGet=new HttpGet(url_link);
                HttpResponse response=httpClient.execute(httpGet);
                HttpEntity httpEntity=response.getEntity();
                String value= EntityUtils.toString(httpEntity);
                return value;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            tv.setText(s);
        }
    }
}
