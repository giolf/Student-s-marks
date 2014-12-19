package myapps.studentgrades;

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

    public static ArrayList<Anno> getListaAnni() {
        return listaAnni;
    }

    public static String getNomeAnnoSelezionato() {
        return NomeAnnoSelezionato;
    }

    public static void setNomeAnnoSelezionatoo(String anno) {
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
    }

    public static void rimuoviAnno(String nomeAnnoScolastico) {
        for (int i = 0; i<listaAnni.size(); i++)
            if ( listaAnni.get(i).getNomeAnnoScolastico().equals(nomeAnnoScolastico) )
                listaAnni.remove(i);
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

}
