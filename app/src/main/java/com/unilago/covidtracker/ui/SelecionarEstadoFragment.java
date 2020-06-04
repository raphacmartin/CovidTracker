package com.unilago.covidtracker.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.unilago.covidtracker.MainActivity;
import com.unilago.covidtracker.R;
import com.unilago.covidtracker.model.EstadoModel;
import com.unilago.covidtracker.service.EstadoService;
import com.unilago.covidtracker.service.Http;

import java.util.ArrayList;


public class SelecionarEstadoFragment extends Fragment {
    private Spinner spn_estados;
    private Button btn_confirmar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selecionar_estado, container, false);

        getElements(view);

        setListeners();

        carregarEstados();

        return view;
    }

    private void getElements(View v) {
        spn_estados = v.findViewById(R.id.spn_estados);
        btn_confirmar = v.findViewById(R.id.btn_confirmar);
    }

    private void setListeners() {
        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EstadoModel estado = (EstadoModel) spn_estados.getSelectedItem();
                Bundle args = new Bundle();
                args.putParcelable("estado", estado);
                MainActivity.setFragment(new EstadoDetalhesFragment(), args);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void carregarEstados() {
        final Handler handler = new Handler();
        final Context context = getContext();

        EstadoService.getEstados(new Http.HttpCallback<ArrayList<EstadoModel>>() {
            @Override
            public void onComplete(ArrayList<EstadoModel> res) {
                final ArrayAdapter<EstadoModel> adapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, res);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        spn_estados.setAdapter(adapter);
                    }
                });

            }

            @Override
            public void onError(Exception e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.showToast("Não foi possível buscar os estados no momento.");
                    }
                });
            }
        });
    }


}
