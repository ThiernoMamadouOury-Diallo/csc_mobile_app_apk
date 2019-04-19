package fr.tas.esipe.tasclientmobile.service;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(result, User.class);
        if(user == null){
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
                connection.connect();

                String result;
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String content = "", line;
                while ((line = rd.readLine()) != null) {
                    content += line + "\n";
                }
                result = content;
                return result;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
