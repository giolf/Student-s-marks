package myapps.studentsmarks;

import java.util.ArrayList;

/**
 * Created by Gio on 06.12.2014.
 */
public class Anno {

    private int id;
    private String nome;
    private double mediaAttuale;
    private double mediaPrecedente;
    private ArrayList<Corso> listaCorsi;

    public Anno (String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getMediaAttuale() {
        return mediaAttuale;
    }

    public void setMediaAttuale(double mediaAttuale) {
        this.mediaAttuale = mediaAttuale;
    }

    public double getMediaPrecedente() {
        return mediaPrecedente;
    }

    public void setMediaPrecedente(double mediaPrecedente) {
        this.mediaPrecedente = mediaPrecedente;
    }

    public ArrayList<Corso> getListaCorsi() {
        return listaCorsi;
    }

    public void setListaCorsi(ArrayList<Corso> listaCorsi) {
        this.listaCorsi = listaCorsi;
    }
}
