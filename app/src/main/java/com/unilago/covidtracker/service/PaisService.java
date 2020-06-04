package com.unilago.covidtracker.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.unilago.covidtracker.model.PaisModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PaisService extends Http {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void getPais(String pais, final HttpCallback<PaisModel> callback) {
        get(pais, new HttpCallback<JSONObject>() {
            @Override
            public void onComplete(JSONObject res) {
                try {
                    JSONObject obj = res.getJSONObject("data");
                    PaisModel pais = new PaisModel();
                    pais.setNome(obj.getString("country"));
                    pais.setCasos(obj.getInt("cases"));
                    pais.setConfirmados(obj.getInt("confirmed"));
                    pais.setMortes(obj.getInt("deaths"));
                    pais.setRecuperados(obj.getInt("recovered"));
                    callback.onComplete(pais);
                } catch (JSONException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
