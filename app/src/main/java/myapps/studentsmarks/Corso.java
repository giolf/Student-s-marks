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
        this.nomeCorso = nomeCorso;
        this.listaVoti = new ArrayList<Voto>();
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

    public Voto getVoto(String DataVotoSelezionato) {
        for (Voto votoCreato : listaVoti)
            if ( votoCreato.getData().equals(DataVotoSelezionato) )
                return votoCreato;
        return null;
    }

    public boolean votoGiaEsistente(String dataVotoSelezionato) {
        for (Voto votoCreato : listaVoti) {
            if ( votoCreato.getData().equalsIgnoreCase(dataVotoSelezionato) )
                return true;
        }
        return false;
    }

    public void rimuoviVoto(String dataVotoSelezionato) {
        for (int i = 0; i<listaVoti.size(); i++)
            if ( listaVoti.get(i).getData().equals(dataVotoSelezionato) )
                listaVoti.remove(i);
    }

    public void aggiornaMedia() {
        double sommaNote = 0;
        double sommaVoti = listaVoti.size();
        if (sommaVoti == 0) {
            media = 0;
            return;
        }

        for (Voto voto : listaVoti)
            sommaNote += voto.getNota();
        media = sommaNote/sommaVoti;
    }
}
