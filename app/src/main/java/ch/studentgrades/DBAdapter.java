package ch.studentgrades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gio on 27.12.2014.
 */
public class DBAdapter {

    private Context context;
    private SQLiteDatabase database;
    private DBHelper DBHelper;

    // Database tabelle
    private static final String TBL_ANNO   = "anno";
    private static final String TBL_CORSO  = "corso";
    private static final String TBL_VOTO   = "voto";

    // Database campi tabella anno
    private static final String F_ANNO_ID = "_id";
    private static final String F_ANNO_NA = "nome_anno";
    private static final String F_ANNO_MP = "media_p";
    private static final String F_ANNO_MA = "media_a";

    // Database campi tabella corso
    private static final String F_CORSO_ID = "_id";
    private static final String F_CORSO_ID_A = "id_anno";
    private static final String F_CORSO_NC = "nome_corso";
    private static final String F_CORSO_MP = "media_p";
    private static final String F_CORSO_MA = "media_a";

    // Database campi tabella voto
    private static final String F_VOTO_ID = "_id";
    private static final String F_VOTO_ID_C = "id_corso";
    private static final String F_VOTO_D = "data";
    private static final String F_VOTO_MD = "mese_data";
    private static final String F_VOTO_GD = "giorno_data";
    private static final String F_VOTO_N = "nota";

    public DBAdapter(Context context) {
        this.context = context;
    }

    public static String getF_ANNO_ID() {
        return F_ANNO_ID;
    }

    public static String getF_ANNO_NA() {
        return F_ANNO_NA;
    }

    public static String getF_ANNO_MP() {
        return F_ANNO_MP;
    }

    public static String getF_ANNO_MA() {
        return F_ANNO_MA;
    }

    public static String getF_CORSO_ID() {
        return F_CORSO_ID;
    }

    public static String getF_CORSO_ID_A() {
        return F_CORSO_ID_A;
    }

    public static String getF_CORSO_NC() {
        return F_CORSO_NC;
    }

    public static String getF_CORSO_MP() {
        return F_CORSO_MP;
    }

    public static String getF_CORSO_MA() {
        return F_CORSO_MA;
    }

    public static String getF_VOTO_ID() {
        return F_VOTO_ID;
    }

    public static String getF_VOTO_ID_C() {
        return F_VOTO_ID_C;
    }

    public static String getF_VOTO_D() {
        return F_VOTO_D;
    }

    public static String getF_VOTO_MD() {
        return F_VOTO_MD;
    }

    public static String getF_VOTO_GD() {
        return F_VOTO_GD;
    }

    public static String getF_VOTO_N() {
        return F_VOTO_N;
    }

    public DBAdapter open() throws SQLException {
        DBHelper = new DBHelper(context);
        database = DBHelper.getWritableDatabase();
        database.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public ArrayList<Anno> recuperoStrutturaCompleta() {
        ArrayList<Anno> listaAnni = new ArrayList<Anno>();
        Cursor cursorAnni = database.rawQuery("SELECT * FROM " +TBL_ANNO, null);

        while ( cursorAnni.moveToNext() ) {
            Anno anno   = new Anno(cursorAnni);
            long idAnno = anno.getId();
            listaAnni.add(anno);

            Cursor cursorCorsi = database.rawQuery("SELECT * FROM "+TBL_CORSO+" WHERE "+F_CORSO_ID_A+" = "+idAnno, null);
            while ( cursorCorsi.moveToNext() ) {
                Corso corso  = new Corso(cursorCorsi);
                long idCorso = corso.getId();
                anno.aggiungiCorso(corso, false);

                Cursor cursorVoti = database.rawQuery("SELECT * FROM "+TBL_VOTO+" WHERE "+F_VOTO_ID_C+" = "+idCorso, null);
                while ( cursorVoti.moveToNext() ) {
                    Voto voto = new Voto( cursorVoti, corso.getNomeCorso() );
                    corso.aggiungiVotoOrdinatoPerData(voto, false);

                    if ( cursorCorsi.isLast() && cursorVoti.isLast() )
                        anno.ordinaCorsiPerMedia();
                }
            }
        }

        return listaAnni;
    }

    public void aggiungiAnno(Anno anno) {
        String nomeAnno  = anno.getNomeAnnoScolastico();
        double mediaP    = anno.getMediaPrecedente();
        double mediaA    = anno.getMediaAttuale();
        ContentValues cv = new ContentValues();
        cv.put(F_ANNO_NA, nomeAnno);
        cv.put(F_ANNO_MP, mediaP);
        cv.put(F_ANNO_MA, mediaA);

        // Inserisco l'anno nel DB
        database.insert(TBL_ANNO, null, cv);

        // Recupero l'anno appena inserito dal DB
        Cursor cursor = database.rawQuery("SELECT "+F_ANNO_ID+" FROM "+TBL_ANNO+" ORDER BY "+F_ANNO_ID+" DESC LIMIT 1", null);
        cursor.moveToFirst();

        // Recupero l'id dell'anno appena inserito (è l'id che gli ha assegnato il DB)
        long id = cursor.getLong( cursor.getColumnIndex(F_ANNO_ID) );
        anno.setId(id);
    }

    public void modificaAnno(Anno anno) {
        long idAnno      = anno.getId();
        String nuovoNome = anno.getNomeAnnoScolastico();
        ContentValues cv = new ContentValues();
        cv.put(F_ANNO_NA, nuovoNome);
        database.update(TBL_ANNO, cv, F_ANNO_ID+"="+idAnno, null);
    }

    public void rimuoviAnno(Anno anno) {
        long idAnno = anno.getId();
        database.delete(TBL_ANNO, F_ANNO_ID + "=" + idAnno, null);
    }

    public void aggiungiCorso(Corso corso) {
        long idAnno       = corso.getIdAnno();
        String nomeCorso  = corso.getNomeCorso();
        double mediaP     = corso.getMediaPrecedente();
        double mediaA     = corso.getMediaAttuale();
        ContentValues cv  = new ContentValues();
        cv.put(F_CORSO_ID_A, idAnno);
        cv.put(F_CORSO_NC, nomeCorso);
        cv.put(F_CORSO_MP, mediaP);
        cv.put(F_CORSO_MA, mediaA);

        // Inserisco il corso nel DB
        database.insert(TBL_CORSO, null, cv);

        // Recupero il corso appena inserito dal DB
        Cursor cursor = database.rawQuery("SELECT "+F_CORSO_ID+" FROM "+TBL_CORSO+" ORDER BY "+F_CORSO_ID+" DESC LIMIT 1", null);
        cursor.moveToFirst();

        // Recupero l'id del corso appena inserito (è l'id che gli ha assegnato il DB)
        long id = cursor.getLong( cursor.getColumnIndex(F_CORSO_ID) );
        corso.setId(id);
    }

    public void modificaCorso(Corso corso) {
        long idCorso      = corso.getId();
        String nuovoNome = corso.getNomeCorso();
        ContentValues cv = new ContentValues();
        cv.put(F_CORSO_NC, nuovoNome);
        database.update(TBL_CORSO, cv, F_CORSO_ID+"="+idCorso, null);
    }

    public void rimuoviCorso(Corso corso) {
        long idCorso = corso.getId();
        database.delete(TBL_CORSO, F_CORSO_ID + "=" + idCorso, null);
    }

    public void aggiornaMediaAnno(Anno anno) {
        long idAnno   = anno.getId();
        double mediaP = anno.getMediaPrecedente();
        double mediaA = anno.getMediaAttuale();

        ContentValues cv = new ContentValues();
        cv.put(F_ANNO_MP, mediaP);
        cv.put(F_ANNO_MA, mediaA);
        database.update(TBL_ANNO, cv, F_ANNO_ID+"="+idAnno, null);
    }

    public void aggiungiVoto(long idCorso, Voto voto) {
        String data      = voto.getData();
        int meseD        = voto.getMeseData();
        int giornoD      = voto.getGiornoData();
        double nota      = voto.getNota();
        ContentValues cv = new ContentValues();
        cv.put(F_VOTO_ID_C, idCorso);
        cv.put(F_VOTO_D, data);
        cv.put(F_VOTO_MD, meseD);
        cv.put(F_VOTO_GD, giornoD);
        cv.put(F_VOTO_N, nota);

        // Inserisco il voto nel DB
        database.insert(TBL_VOTO, null, cv);

        // Recupero il voto appena inserito dal DB
        Cursor cursor = database.rawQuery("SELECT "+F_VOTO_ID+" FROM "+TBL_VOTO+" ORDER BY "+F_VOTO_ID+" DESC LIMIT 1", null);
        cursor.moveToFirst();

        // Recupero l'id del voto inserito (è l'id che gli ha assegnato il DB)
        long id = cursor.getLong( cursor.getColumnIndex(F_VOTO_ID) );
        voto.setId(id);
    }

    public void aggiornaMediaCorso(Corso corso) {
        long idCorso   = corso.getId();
        double mediaP  = corso.getMediaPrecedente();
        double mediaA  = corso.getMediaAttuale();

        ContentValues cv = new ContentValues();
        cv.put(F_CORSO_MP, mediaP);
        cv.put(F_CORSO_MA, mediaA);
        database.update(TBL_CORSO, cv, F_CORSO_ID+"="+idCorso, null);
    }

    public void rimuoviVoto(Voto voto) {
        long idVoto = voto.getId();
        database.delete(TBL_VOTO, F_VOTO_ID + "=" + idVoto, null);
    }
}
