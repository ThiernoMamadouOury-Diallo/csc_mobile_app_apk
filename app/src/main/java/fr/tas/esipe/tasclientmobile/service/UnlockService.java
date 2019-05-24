package fr.tas.esipe.tasclientmobile.service;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.exception.LoginException;
import fr.tas.esipe.tasclientmobile.model.Reservation;
import fr.tas.esipe.tasclientmobile.model.User;

public class UnlockService {

    private final Context context;

    public UnlockService(Context context){
        this.context = context;
    }

    public ArrayList<Integer> getReservation(User user) throws ExecutionException, InterruptedException {
        String result = new UnlockService.GetRequestUrl().execute(user).get();

        ArrayList<Integer> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result.replace("\"",""));
            if(jsonArray != null){
                int len = jsonArray.length();
                for(int i = 0; i < len; i++){
                    list.add(jsonArray.getJSONArray(i).getInt(0));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }

    private class GetRequestUrl extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... user) {
            try {
                URL url = new URL(context.getResources().getString(R.string.dev_api) + "unlock/getBooking");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");

                JSONObject cred = new JSONObject();
                cred.put("id_account", String.valueOf(user[0].getId()));

                OutputStream os = connection.getOutputStream();
                os.write(cred.toString().getBytes("UTF-8"));
                os.close();

                StringBuilder sb = new StringBuilder();
                int HttpResult = connection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                    rd.close();
                }
                return sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
