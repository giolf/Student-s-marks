package myapps.studentsmarks;

import java.util.ArrayList;

/**
 * Created by Gio on 06.12.2014.
 */
public class Anno {

    private int id;
    private String nomeAnnoScolastico;
    private double mediaAttuale;
    private double mediaPrecedente;
    private ArrayList<Corso> listaCorsi;

    public Anno (String nome) {
        this.nomeAnnoScolastico = nome;
        this.mediaAttuale       = 0;
        this.mediaPrecedente    = 0;
    }

    public int getId() {
        return id;
    }

    public String getNomeAnnoScolastico() {
        return nomeAnnoScolastico;
    }

    public void setNomeAnnoScolastico(String nome) {
        this.nomeAnnoScolastico = nome;
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
