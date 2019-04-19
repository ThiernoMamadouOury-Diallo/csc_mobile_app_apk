package fr.tas.esipe.tasclientmobile.endpoint;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ConnectToRestApi extends AsyncTask<String, Integer, String> {

    /**
     *
     * @param urls
     * @return
     */
    protected String doInBackground(String... urls) {
        String result = "";
        try{

            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String content = "", line;
            while ((line = rd.readLine()) != null) {
                content += line + "\n";
            }
            result = content;
            return result;

        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param progress
     */
    protected void onProgressUpdate(Integer... progress) {
    }

    /**
     *
     * @param result
     */
    protected void onPostExecute(String result) {
        // this is executed on the main thread after the process is over


    }
}
