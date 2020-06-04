package com.unilago.covidtracker.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;

@SuppressLint("ParcelCreator")
public class EstadoModel implements Parcelable {
    private String uf;
    private String nome;
    private int casos;
    private int mortes;
    private int suspeitos;
    private Date ultima_atualizacao;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uf);
        dest.writeString(nome);
        dest.writeInt(casos);
        dest.writeInt(mortes);
        dest.writeInt(suspeitos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCasos() {
        return casos;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }

    public int getMortes() {
        return mortes;
    }

    public void setMortes(int mortes) {
        this.mortes = mortes;
    }

    public int getSuspeitos() {
        return suspeitos;
    }

    public void setSuspeitos(int suspeitos) {
        this.suspeitos = suspeitos;
    }

    public Date getUltima_atualizacao() {
        return ultima_atualizacao;
    }

    public void setUltima_atualizacao(Date ultima_atualizacao) {
        this.ultima_atualizacao = ultima_atualizacao;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
