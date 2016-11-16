package com.devazt.networking;

import android.os.StrictMode;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Neksze on 30/09/2015.
 */
public class HttpClient {
    private ArrayList<OnHttpRequestComplete> httpRequestComplete;

    public HttpClient(OnHttpRequestComplete o) {
        httpRequestComplete = new ArrayList<>();
        httpRequestComplete.add(o);
    }

    /**
     * Este método hace una solicitud web a la url enviada como parametro, y al terminar, se ejecuta el
     * listener OnHttpRequestComplete [seteado al instanciar clase]
     * @param urlHttp url para request
     */
    public void excecute(String urlHttp){
        Response r = getResponse(urlHttp);
        for (OnHttpRequestComplete http : httpRequestComplete) {
            http.onComplete(r);
        }
    }

    /**
     * Este método hace una solicitud web a la url enviada como parametro, y al terminar, se ejecuta el
     * listener OnHttpRequestComplete [seteado al instanciar clase]
     * @param urlHttp url para request
     * @param idResponse id para la respuesta
     */
    public void excecute(String urlHttp, int idResponse){
        Response r = getResponse(urlHttp);
        Response response = new Response(r.getResult(),r.isSuccess(),idResponse);
        for (OnHttpRequestComplete http : httpRequestComplete) {
            http.onComplete(response);
        }
    }

    private Response getResponse(String urlHttp){
        AndroidHelper.AddStrictMode(); // Para versiones superiores a SDK 9
        String text = "";
        boolean success = false;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urlHttp);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            InputStreamReader isw = new InputStreamReader(in);
            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                text += String.valueOf(current);
            }
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                success = false;
            }
        }

        return new Response(text,success,0);
    }

}
