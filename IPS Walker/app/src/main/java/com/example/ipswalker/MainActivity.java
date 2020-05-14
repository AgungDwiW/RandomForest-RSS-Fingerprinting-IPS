package com.example.ipswalker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {
    //console
    public TextView console;
    public int n;

      /*
        SSID	            BSSID       	    A	        N
        Ephemeral blessing	ce:73:14:c4:7a:28	-63.555	    2.18345215481207
        Elfais	            18:0f:76:91:f2:72	-47.43	    3.40041922816446
        TP-LINK_E630	    c0:25:e9:7a:e6:30	-54.685	    2.87070468917083
         */

    private Button send;
    private TextView text, subtitle;
    private WifiManager wifiManager;
    private String uri = "http://192.168.100.77:5000/api";
//
//    private ArrayAdapter adapter;
//    private ArrayList<String> arrayList = new ArrayList<>();
//    public ListView groupList;
    private List<Info> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Adapter mAdapter;

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        console = findViewById(R.id.console);
//        console.setMovementMethod(new ScrollingMovementMethod());
        n=1;

        send = findViewById(R.id.button);
        text = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        subtitle.setMovementMethod(new ScrollingMovementMethod());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new scanWifi().execute();
            }
        });
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        recyclerView = (RecyclerView) findViewById(R.id.list);

        mAdapter = new Adapter(arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


//        groupList = findViewById(R.id.list);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
//        groupList.setAdapter(adapter);
        new scanWifi().execute();
    }

    private void renderView(String result){
        JSONObject reader = null;
        JSONObject infos = null;
        try {
            reader = new JSONObject(result);

            String name = reader.getString("booth_name");
            String subtitle_s = reader.getString("boot_subtitle");
            infos = reader.getJSONObject("booth_info");

//
//            printConsole("========================");
//            printConsole("Reply");
//            printConsole("========================");
//            printConsole("Boooth name :" + name);
//            for (int x = 1; x<=infos.length(); x++){
//                JSONObject item = infos.getJSONObject(x+"");
//                printConsole("------------"+x+"------------");
//                printConsole("title: " + item.getString("title"));
//                printConsole("subtitle: ");
//                printConsole( item.getString("subtitle"));
//            }
//            printConsole("========================");


            text.setText(name);
            subtitle.setText(subtitle_s);
            console = subtitle;
            final Layout layout = console.getLayout();
            if(layout != null){
                int scrollDelta = layout.getLineBottom(console.getLineCount() - 1)
                        - console.getScrollY() - console.getHeight();
                if(scrollDelta > 0)
                    console.scrollBy(0, scrollDelta);
            }
            arrayList.clear();

            for (int x = 1; x<=infos.length(); x++) {
                JSONObject item = infos.getJSONObject(x + "");
                Info info = new Info (item.getString("title"),item.getString("subtitle") );
                arrayList.add(info);
            }

            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            text.setText("error");
//            printConsole(result);
            e.printStackTrace();
            subtitle.setText("error in position aquisition");
            arrayList.clear();
            mAdapter.notifyDataSetChanged();
        }
        send.setEnabled(true);
    }

    private class SendDeviceDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            renderView(result);

        }
    }

    public void printConsole(String s){
        // Function to print to console
        arrayList.clear();
        mAdapter.notifyDataSetChanged();
        console = subtitle;
        console.setText("");
        console.append(s +"\n");

        final Layout layout = console.getLayout();
        if(layout != null){
            int scrollDelta = layout.getLineBottom(console.getLineCount() - 1)
                    - console.getScrollY() - console.getHeight();
            if(scrollDelta > 0)
                console.scrollBy(0, scrollDelta);
        }
    }


    private class scanWifi extends AsyncTask<String, Void, String> {
        // daemon class to get rssi and freq of certain wifi
        String BSSID1 = "ce:73:14:c4:7a:28", BSSID2 = "18:0f:76:91:f2:72", BSSID3 = "c0:25:e9:7a:e6:2f";
        double level1 =0, level2 = 0, level3=0;
        int count1 =0, count2 = 0, count3=0;

        JSONObject json = new JSONObject();

         /*
        SSID	            BSSID       	    A	        N
        Ephemeral blessing	ce:73:14:c4:7a:28	-63.555	    2.18345215481207
        Elfais	            18:0f:76:91:f2:72	-47.43	    3.40041922816446
        TP-LINK_E630	    c0:25:e9:7a:e6:30	-54.685	    2.87070468917083
         */

        @Override
        protected String doInBackground(String... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    send.setEnabled(false);
//                    printConsole("scanning data;");
//                    printConsole("--------------------- ");
                    text.setText("scanning data");
                }
            });
            List<ScanResult> results;


            for (int x = 0; x<20;x++){
                results = wifiManager.getScanResults();
                for (ScanResult scanResult : results) {
                    //final String sca = scanResult.BSSID;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            printConsole(sca+"");
//                        }
//                    });
                    if(scanResult.BSSID.equals(BSSID1) ){
                        level1 += scanResult.level; count1+=1;}
                    else if (scanResult.BSSID.equals(BSSID2)){
                        level2 += scanResult.level; count2+=1;}
                    else if (scanResult.BSSID.equals(BSSID3)){
                        level3 += scanResult.level; count3+=1;}
                }
                sleep(500);
                if(x%2 == 0) {
                    final int y = x;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            printConsole("scanning data - " + y + "/20");
                        }
                    });
                }

            }

            return "aaaa";
        }

        @Override
        protected void onPostExecute(String result) {
            level1 /=count1;
            level2 /=count2;
            level3 /= count3;


//            printConsole("============");
//            printConsole(BSSID1 + " : " + level1);
//            printConsole(BSSID2 + " : " + level2);
//            printConsole(BSSID3 + " : " + level3);
//            printConsole("============");



            try {
                json.put(BSSID1 , level1);
                json.put(BSSID2 , level2);
                json.put(BSSID3 , level3);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            new SendDeviceDetails().execute(uri, json.toString());
        }
    }
}
