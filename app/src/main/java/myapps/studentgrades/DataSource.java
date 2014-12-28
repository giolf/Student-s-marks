package myapps.studentgrades;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gio on 16.12.2014.
 */
public abstract class DataSource {

    /*Contiene la lista degli anni creati*/
    private static ArrayList<Anno> listaAnni = new ArrayList<Anno>();

    /*Contiene la scelta dell'anno da parte dell'utente per il quale visualizzarne le statistiche*/
    private static String NomeAnnoSelezionato = null;

    /*Contiente l'adapter del DB*/
    private static DBAdapter DBAdapter = null;

    public static void inizializzazioneStrutDati(Context context) {
        DBAdapter = new DBAdapter(context);
    }

    public static ArrayList<Anno> getListaAnni() {
        return listaAnni;
    }

    public static String getNomeAnnoSelezionato() {
        return NomeAnnoSelezionato;
    }

    public static void setNomeAnnoSelezionato(String anno) {
        NomeAnnoSelezionato = anno;
    }

    public static Anno getAnno(String nomeAnnoScolastico) {
        for (Anno annoCreato : listaAnni)
            if ( annoCreato.getNomeAnnoScolastico().equals(nomeAnnoScolastico) )
                return annoCreato;
        return null;
    }

    public static void aggiungiAnno(Anno anno) {
        listaAnni.add(anno);

        // Aggiungo l'anno nel db
        DBAdapter.open();
        DBAdapter.aggiungiAnno(anno);
        DBAdapter.close();
    }

    public static void modificaAnno(String oldNomeAnnoScolastico, String newNomeAnnoScolastico) {
        for (Anno annoCreato : listaAnni) {
            if ( annoCreato.getNomeAnnoScolastico().equals(oldNomeAnnoScolastico) ) {
                annoCreato.setNomeAnnoScolastico(newNomeAnnoScolastico);

                // Apporto la modifica anche sul db
                DBAdapter.open();
                DBAdapter.modificaAnno(annoCreato);
                DBAdapter.close();
                return;
            }
        }
    }

    public static void rimuoviAnno(String nomeAnnoScolastico) {
        for (int i = 0; i<listaAnni.size(); i++) {
            if ( listaAnni.get(i).getNomeAnnoScolastico().equals(nomeAnnoScolastico) ) {
                Anno annoDaRimuovere = listaAnni.get(i);
                listaAnni.remove(i);

                // Apporto la modifica anche sul db
                DBAdapter.open();
                DBAdapter.rimuoviAnno(annoDaRimuovere);
                DBAdapter.close();
                return;
            }
        }
    }

    public static boolean annoGiaEsistente(String annoSelezionato) {
        for (Anno annoCreato : listaAnni) {
            if ( annoCreato.getNomeAnnoScolastico().equals(annoSelezionato) )
                return true;
        }
        return false;
    }

    public static String[] CreaArrayNomiAnni() {
        String[] listaNomiAnni = new String[listaAnni.size()];
        for (int i = 0; i<listaAnni.size(); i++)
            listaNomiAnni[i] = listaAnni.get(i).getNomeAnnoScolastico();
        Arrays.sort(listaNomiAnni);
        return listaNomiAnni;
    }

    public static String getNomeUltimoAnnoCreato() {
        return listaAnni.get(listaAnni.size()-1).getNomeAnnoScolastico();
    }

    public static int getPosizioneAnno(String[] listaNomiAnni, String nomeAnno) {
        int posizioneAnno = 0;
        for (int i = 0; i<listaNomiAnni.length; i++) {
            if (listaNomiAnni[i].equals(nomeAnno)) {
                posizioneAnno = i;
                break;
            }
        }
        return posizioneAnno;
    }
}
