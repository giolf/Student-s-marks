package myapps.studentsmarks;

import java.util.ArrayList;

/**
 * Created by Gio on 06.12.2014.
 */
public class Corso {
    private int id;
    private String nomeCorso;
    private double media;
    private ArrayList<Voto> listaVoti;

    public Corso (String nomeCorso) {
        this.setNomeCorso(nomeCorso);
    }


    public int getId() {
        return id;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public ArrayList<Voto> getListaVoti() {
        return listaVoti;
    }

    public void setListaVoti(ArrayList<Voto> listaVoti) {
        this.listaVoti = listaVoti;
    }
}
