package myapps.studentgrades;

import java.util.ArrayList;

/**
 * Created by Gio on 06.12.2014.
 */
public class Corso {
    private int id;
    private String nomeCorso;
    private double mediaAttuale;
    private double mediaPrecedente;
    private ArrayList<Voto> listaVoti;

    public Corso (String nomeCorso) {
        this.nomeCorso          = nomeCorso;
        this.mediaAttuale       = 0;
        this.mediaPrecedente    = 0;
        this.listaVoti          = new ArrayList<Voto>();
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

    public double getMediaAttuale() {
        return mediaAttuale;
    }

    public void setMediaAttuale(double media) {
        this.mediaAttuale = media;
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

    private double arrotondaMedia(double valore, int precisione) {
        long factor = (long) Math.pow(10, precisione);
        valore = valore * factor;
        long tmp = Math.round(valore);
        return (double) tmp / factor;
    }

    public void aggiornaMedia() {
        double sommaNote = 0;
        double sommaVoti = listaVoti.size();
        if (sommaVoti == 0) {
            mediaPrecedente = arrotondaMedia(mediaAttuale, 2);
            mediaAttuale    = 0;
            return;
        }

        for (Voto voto : listaVoti)
            sommaNote += voto.getNota();

        mediaPrecedente = mediaAttuale;
        mediaAttuale    = arrotondaMedia(sommaNote/sommaVoti, 2);
    }

    public void aggiungiVotoOrdinatoPerData(Voto voto) {
        if (listaVoti.size() == 0) {
            listaVoti.add(voto);
            return;
        }
        int meseData          = voto.getMeseData();
        int giornoData        = voto.getGiornoData();
        int start             = 0;
        int end               = 0;
        boolean startIsSetted = false;

        for (int i = 0; i<listaVoti.size(); i++) {
            Voto votoCorrente = listaVoti.get(i);
            if (votoCorrente.getMeseData() == meseData) {
                if (start == 0 && !startIsSetted) {
                    start         = i;
                    end           = i;
                    startIsSetted = true;
                }
                else
                    end++;
            }
        }
        //non ci sono voti creati con lo stesso mese del voto da inserire
        if (start == 0 && end == 0 && !startIsSetted) {
            if (meseData >= 9 && meseData <= 12) {
                for (int i = 0; i<listaVoti.size(); i++) {
                    Voto votoCorrente = listaVoti.get(i);
                    if (votoCorrente.getMeseData() >= 1 && votoCorrente.getMeseData() <= 8) {
                        listaVoti.add(i, voto);
                        return;
                    }
                    else {
                        if (votoCorrente.getMeseData() > meseData) {
                            listaVoti.add(i, voto);
                            return;
                        }
                    }
                }
            }
            else {
                for (int i = 0; i<listaVoti.size(); i++) {
                    Voto votoCorrente = listaVoti.get(i);
                    if (votoCorrente.getMeseData() >= 9 && votoCorrente.getMeseData() <= 12) {
                        continue;
                    }
                    else {
                        if (votoCorrente.getMeseData() > meseData) {
                            listaVoti.add(i, voto);
                            return;
                        }
                    }
                }
            }
            listaVoti.add(voto);
            return;
        }
        //ce solo un voto creato che ha lo stesso mese del voto da inserire
        if (start == end) {
            int giornoDataVotoCreato = listaVoti.get(start).getGiornoData();
            if (giornoDataVotoCreato > giornoData)
                listaVoti.add(start, voto);
            else
                listaVoti.add(start+1, voto);
        }
        //ci sono piu voti creati con lo stesso mese del voto da inserire
        if (start != end) {
            for (; start <= end; start++) {
                if (giornoData <= listaVoti.get(start).getGiornoData()) {
                    listaVoti.add(start, voto);
                    return;
                }
            }
            listaVoti.add(end, voto);
        }
    }
}
