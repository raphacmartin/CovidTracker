package com.unilago.covidtracker.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.unilago.covidtracker.R;
import com.unilago.covidtracker.model.EstadoModel;
import com.unilago.covidtracker.model.PaisModel;
import com.unilago.covidtracker.service.Http;
import com.unilago.covidtracker.service.PaisService;

public class EstadoDetalhesFragment extends Fragment {
    private TextView txv_titulo;
    private TextView txv_casos;
    private TextView txv_mortes;
    private TextView txv_suspeitos;

    private TextView txv_comparativo_casos;
    private TextView txv_comparativo_mortes;

    private ProgressBar pgb_casos;
    private ProgressBar pgb_mortes;

    private EstadoModel estado;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estado_detalhes, container, false);

        Bundle args = getArguments();
        estado = args.getParcelable("estado");

        getElements(view);
        carregarBrasil();
        popularDadosEstado();

        return view;
    }

    private void getElements(View v) {
        txv_titulo = v.findViewById(R.id.txv_titulo_detalhes);
        txv_casos = v.findViewById(R.id.txv_numero_casos);
        txv_mortes = v.findViewById(R.id.txv_numero_mortes);
        txv_suspeitos = v.findViewById(R.id.txv_numero_suspeitos);

        txv_comparativo_casos = v.findViewById(R.id.txv_comparativo_casos);
        txv_comparativo_mortes = v.findViewById(R.id.txv_comparativo_mortes);

        pgb_casos = v.findViewById(R.id.pgb_casos);
        pgb_mortes = v.findViewById(R.id.pgb_mortes);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void carregarBrasil() {
        final Handler handler = new Handler();
        PaisService.getPais("brazil", new Http.HttpCallback<PaisModel>() {
            @Override
            public void onComplete(PaisModel res) {

                final PaisModel pais = res;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        popularDadosComparativos(pais);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void popularDadosEstado() {
        txv_titulo.setText(String.format(getString(R.string.titulo_detalhes), estado.getNome()));
        txv_casos.setText(String.valueOf(estado.getCasos()));
        txv_mortes.setText(String.valueOf(estado.getMortes()));
        txv_suspeitos.setText(String.valueOf(estado.getSuspeitos()));
    }

    private void popularDadosComparativos(PaisModel brasil) {
        float casos_estado = estado.getCasos();
        float mortes_estado = estado.getMortes();
        float casos_brasil = brasil.getCasos();
        float mortes_brasil = brasil.getMortes();
        int percentual_casos = Math.round((casos_estado / casos_brasil) * 100);
        int percentual_mortes = Math.round((mortes_estado / mortes_brasil) * 100);

        String texto_nao_formatado_casos = getString(R.string.comparativo_casos);
        String texto_casos = String.format(texto_nao_formatado_casos, (percentual_casos + "%"));
        txv_comparativo_casos.setText(texto_casos);

        String texto_nao_formatado_mortes = getString(R.string.comparativo_mortes);
        String texto_mortes = String.format(texto_nao_formatado_mortes, (percentual_mortes + "%"));
        txv_comparativo_mortes.setText(texto_mortes);

        pgb_casos.setProgress(percentual_casos);
        pgb_mortes.setProgress(percentual_mortes);
    }
}
