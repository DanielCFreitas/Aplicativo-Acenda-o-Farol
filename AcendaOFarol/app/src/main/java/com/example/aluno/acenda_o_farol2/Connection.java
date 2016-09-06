package com.example.aluno.acenda_o_farol2;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe que representa a conexão com o JSON
 */

public class Connection{

    private final String USER_AGENT = "Mozilla/5.0";


    // HTTP GET request
    public LinkedList<Endereco> sendGet() throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //https://api.myjson.com/bins/3kpyw
        //http://api.flickr.com/services/feeds/photos_public.gne?tags=beatles&format=json&jsoncallback=?
        String url = "https://api.myjson.com/bins/4wa2k";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //System.out.println(response.toString());

        LinkedList<Endereco> found = findAllItems(new JSONArray(response.toString()));

        return found;
    }

    public LinkedList<Endereco> findAllItems(JSONArray response) {

        LinkedList<Endereco> found = new LinkedList<Endereco>();

        try {


            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                found.add(new Endereco (obj.getString("nome_da_rua")));
            }

        } catch (JSONException e) {
            // handle exception
        }

        return found;
    }

}
