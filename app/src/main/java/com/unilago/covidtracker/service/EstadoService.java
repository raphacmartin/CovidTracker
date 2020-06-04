package com.unilago.covidtracker.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.unilago.covidtracker.model.EstadoModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EstadoService extends Http {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void getEstados(final Http.HttpCallback<ArrayList<EstadoModel>> callback) {
        get("", new Http.HttpCallback<JSONObject>() {
            @Override
            public void onComplete(JSONObject res) {
                try {
                    JSONArray data = res.getJSONArray("data");
                    ArrayList<EstadoModel> estados = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        EstadoModel estado = new EstadoModel();
                        estado.setUf(obj.getString("uf"));
                        estado.setNome(obj.getString("state"));
                        estado.setCasos(obj.getInt("cases"));
                        estado.setMortes(obj.getInt("deaths"));
                        estado.setSuspeitos(obj.getInt("suspects"));
                        estados.add(estado);
                    }
                    callback.onComplete(estados);
                } catch (Exception e) {
                    callback.onError(e);
                }

            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }
}
