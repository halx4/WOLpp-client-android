package android.foivos.door;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    //String ip = "http://192.168.2.177/";
    TextView text;
    Button openButton;
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text=(TextView) findViewById(R.id.textView);
        text.setText("push to open");

        openButton=(Button) findViewById(R.id.button);



    }

    public void openPushed(View view){

        text.setText("Sending request ...");
        openButton.setEnabled(false);
        try {
            new HTTPRequest(this).execute(new URL(getString(R.string.url_for_open_command)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }


    private void executeReq(URL url) throws IOException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(3000);
        con.setConnectTimeout(3500);
        con.setRequestMethod("GET");
        con.setDoInput(true);

        // Connect
        con.connect();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
