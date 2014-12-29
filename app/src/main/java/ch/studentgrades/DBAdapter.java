package ch.studentgrades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String F_ANNO_ID = "_id";
    public static final String F_ANNO_NA = "nome_anno";
    public static final String F_ANNO_MP = "media_p";
    public static final String F_ANNO_MA = "media_a";

    // Database campi tabella corso
    public static final String F_CORSO_ID = "_id";
    public static final String F_CORSO_ID_A = "id_anno";
    public static final String F_CORSO_NC = "nome_corso";
    public static final String F_CORSO_MP = "media_p";
    public static final String F_CORSO_MA = "media_a";

    // Database campi tabella voto
    public static final String F_VOTO_ID = "_id";
    public static final String F_VOTO_ID_C = "id_corso";
    public static final String F_VOTO_D = "data";
    public static final String F_VOTO_MD = "mese_data";
    public static final String F_VOTO_GD = "giorno_data";
    public static final String F_VOTO_N = "nota";

    public DBAdapter(Context context) {
        this.context = context;
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
