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

    public String[] creaArrayNomiCorsi() {
        String[] listaNomiCorsi = new String[listaCorsi.size()];
        for (int i = 0; i<listaCorsi.size(); i++)
            listaNomiCorsi[i] = listaCorsi.get(i).getNomeCorso();
        Arrays.sort(listaNomiCorsi);
        return listaNomiCorsi;
    }

    public ArrayList<Corso> creaListaCorsiConVoti() {
        ArrayList<Corso> listaCorsi = new ArrayList<Corso>();
        for (Corso corso : this.listaCorsi)
            if (corso.getListaVoti().size() > 0)
                listaCorsi.add(corso);
        return listaCorsi;
    }

    private void ordinaCorsiPerMedia() {
        for (int i = 0; i < listaCorsi.size(); i++) {
            for (int k = i+1; k < listaCorsi.size(); k++) {
                if (listaCorsi.get(i).getMedia() < listaCorsi.get(k).getMedia()) {
                    Corso corsoMediaMaggiore = listaCorsi.get(k);
                    listaCorsi.remove(k);
                    listaCorsi.add(i, corsoMediaMaggiore);
                }
            }
        }
    }

    public Corso getCorso(String nomeCorso) {
        for (Corso corsoCreato : listaCorsi)
            if ( corsoCreato.getNomeCorso().equals(nomeCorso) )
                return corsoCreato;
        return null;
    }

    public void aggiungiCorso(Corso corso) {
        listaCorsi.add(corso);
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
            }
            else
                sommaMedia += corso.getMedia();
        }

        mediaPrecedente = mediaAttuale;
        mediaAttuale    = sommaMedia/sommaCorsi;


        /*
        * aggiornare la media di un anno implica che prima sia stata aggiornata
        * la media di un corso di tale anno. Quindi se la media di un corso è stata aggiornata,
        * tale corso dev'essere riordinato per media, tra gli altri corsi del medesimo anno
        */
        //se nell'anno ce piu di un corso ri-ordino i corsi per media
        if ( listaCorsi.size() > 1)
            ordinaCorsiPerMedia();
    }
}
