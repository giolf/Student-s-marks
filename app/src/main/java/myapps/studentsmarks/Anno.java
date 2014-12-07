package myapps.studentsmarks;

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

}
