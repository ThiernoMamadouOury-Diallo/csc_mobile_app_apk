package fr.tas.esipe.tasclientmobile.service;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import fr.tas.esipe.tasclientmobile.R;
import fr.tas.esipe.tasclientmobile.exception.LoginException;
import fr.tas.esipe.tasclientmobile.model.User;

public class LoginService {

    private final Context context;

    public LoginService(Context context){
        this.context = context;
    }

    public User getLoggedUser(String login, String password) throws LoginException, IOException, ExecutionException, InterruptedException {
        String result = new GetRequestUrl().execute(new String[]{login, password}).get();

        User user = null;
        if(result != null && result != "") {
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.readValue(result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1).replace("\\", ""), User.class);
        }

        if (user == null) {
            throw new LoginException("Bad authentication");
        }

        return user;
    }

    private class GetRequestUrl extends AsyncTask<String[], Void, String>{

        @Override
        protected String doInBackground(String[]... strings) {
            try {
                URL url = new URL(context.getResources().getString(R.string.dev_api) + "restapiclient/login");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");

                JSONObject cred = new JSONObject();
                cred.put("login", strings[0][0]);
                cred.put("password", strings[0][1]);

                OutputStream os = connection.getOutputStream();
                os.write(cred.toString().getBytes("UTF-8"));
                os.close();

                StringBuilder sb = new StringBuilder();
                int HttpResult = connection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = rd.readLine()) != null) {
                        sb.append(line + "\n");
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
