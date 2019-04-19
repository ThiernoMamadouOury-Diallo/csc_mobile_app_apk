package fr.tas.esipe.tasclientmobile.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.tas.esipe.tasclientmobile.model.BillFileBean;

public class BillsNotification {
    String uri;
    String jSon;

    private ArrayList<String> listBills = new ArrayList<>();
    ArrayList<BillFileBean> list;
    public BillsNotification(String jSon) {
        this.jSon = jSon;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getjSon() {
        return jSon;
    }

    public void setjSon(String jSon) {
        this.jSon = jSon;
    }

    public  ArrayList<BillFileBean> initList() throws JSONException {

        JSONObject obj = new JSONObject(jSon);
        JSONArray arr = obj.getJSONArray("bills");
        for (int i = 0; i < arr.length(); i++)
        {
            String uri = arr.getJSONObject(i).getString("uri");
            if(uri.equals(null) || uri.equals(""))
                listBills.add("simple_demo.pdf");
            else
                listBills.add(uri);
        }

        try{

            for(String fileName : listBills){
                //choose only the pdf files
                if(fileName.endsWith(".pdf")){
                    list.add(new BillFileBean(fileName));
                }
            }
            return list;

        }catch(Exception e){
            Log.i("show","Something went wrong. "+e.toString());
            return null;
        }
    }
}
