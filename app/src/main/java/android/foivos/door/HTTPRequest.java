package android.foivos.door;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by foivos on 08-Dec-16.
 */

public class HTTPRequest extends AsyncTask<URL, Void, Boolean> {

    private MainActivity mActivity;

    public HTTPRequest(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(URL... urls) {

        Boolean success=true;

        {

            URL url=urls[0];
            HttpURLConnection urlConnection = null;
            try {
                //url = new URL("http://192.168.2.180/?submitButton=UNLOCK");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                /*
                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    System.out.print(current);
                }

                */
            }
            catch (Exception e) {
                //e.printStackTrace();
                success=false;
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }


        }

        return success;
    }



    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        mActivity.openButton.setEnabled(true);
        mActivity.text.setText( (success)?
                mActivity.getString(R.string.message_done) :
                mActivity.getString(R.string.error) );

    }
}
