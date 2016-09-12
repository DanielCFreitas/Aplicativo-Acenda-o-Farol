package com.example.aluno.acenda_o_farol2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe que representa a conexão com o JSON
 */

public class Connection{

    private final String USER_AGENT = "Mozilla/5.0";


    /**
     * Método que faz a Requisicao para Internet
     * @return retorna lista de Enderecos encontrados no JSON
     * @throws Exception
     */
    public HashMap<String, ArrayList<String>> sendGet() throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //https://api.myjson.com/bins/3kpyw
        //http://api.flickr.com/services/feeds/photos_public.gne?tags=beatles&format=json&jsoncallback=?
        String url = "https://api.myjson.com/bins/43pdi";

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

        HashMap<String, ArrayList<String>> found = findAllItems(new JSONArray(response.toString()));

        return found;
    }

    /**
     * Monta lista de Enderecos
     * @param response
     * @return
     */
    public HashMap<String, ArrayList<String>> findAllItems(JSONArray response) {
        HashMap<String, ArrayList<String>> found = new HashMap<String, ArrayList<String>>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                Iterator<String> chave = obj.keys();
                String cidadeAtual = chave.next();

                ArrayList<String> listaRodovias = new ArrayList<String>();
                JSONArray jArray = obj.getJSONArray(cidadeAtual);

                if (jArray != null) {
                    for (int indice=0;indice<jArray.length();indice++){
                        listaRodovias.add(jArray.get(indice).toString());
                    }
                }

                found.put(cidadeAtual,listaRodovias);
            }
        } catch (JSONException e) {
            // handle exception
        }

        return found;
    }

}
