package myapps.studentgrades;

import java.util.ArrayList;
import java.util.Arrays;

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
        this.listaCorsi         = new ArrayList<Corso>();
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
        return arrotondaMedia(mediaAttuale, 2);
    }

    public void setMediaAttuale(double mediaAttuale) {
        this.mediaAttuale = mediaAttuale;
    }

    public double getMediaPrecedente() {
        return arrotondaMedia(mediaPrecedente, 2);
    }

    public void setMediaPrecedente(double mediaPrecedente) {
        this.mediaPrecedente = mediaPrecedente;
    }

    public ArrayList<Corso> getListaCorsi() {
        return listaCorsi;
    }

    public String[] CreaArrayNomiCorsi() {
        String[] listaNomiCorsi = new String[listaCorsi.size()];
        for (int i = 0; i<listaCorsi.size(); i++)
            listaNomiCorsi[i] = listaCorsi.get(i).getNomeCorso();
        Arrays.sort(listaNomiCorsi);
        return listaNomiCorsi;
    }

    public Corso getCorso(String nomeCorso) {
        for (Corso corsoCreato : listaCorsi)
            if ( corsoCreato.getNomeCorso().equals(nomeCorso) )
                return corsoCreato;
        return null;
    }

    public void rimuoviCorso(String nomeCorso) {
        for (int i = 0; i<listaCorsi.size(); i++)
            if ( listaCorsi.get(i).getNomeCorso().equals(nomeCorso) )
                listaCorsi.remove(i);
    }

    public boolean corsoGiaEsistente(String corsoSelezionato) {
        for (Corso corsoCreato : listaCorsi) {
            if ( corsoCreato.getNomeCorso().equalsIgnoreCase(corsoSelezionato) )
                return true;
        }
        return false;
    }

    public int numeroVotiAnnuali() {
        int numVoti = 0;
        for (Corso corso : listaCorsi)
            numVoti += corso.getListaVoti().size();
        return numVoti;
    }

    public double getDifferenzaMediaAttualePrecedente() {
        return arrotondaMedia(mediaAttuale - mediaPrecedente, 2);
    }

    private double arrotondaMedia(double valore, int precisione) {
        long factor = (long) Math.pow(10, precisione);
        valore = valore * factor;
        long tmp = Math.round(valore);
        return (double) tmp / factor;
    }

    public void aggiornaMedia() {
        double sommaMedia = 0;
        double sommaCorsi = listaCorsi.size();

        if (sommaCorsi == 0) {
            mediaPrecedente = mediaAttuale;
            mediaAttuale    = 0;
            return;
        }

        for (Corso corso : listaCorsi) {
            if(corso.getMedia() == 0) {
                sommaCorsi--;
                continue;
            }
            else
                sommaMedia += corso.getMedia();
        }

        mediaPrecedente = mediaAttuale;
        mediaAttuale    = sommaMedia/sommaCorsi;
    }
}
