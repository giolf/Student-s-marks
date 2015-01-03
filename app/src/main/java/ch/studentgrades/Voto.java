package ch.studentgrades;

import android.database.Cursor;

import static ch.studentgrades.DBAdapter.getF_VOTO_D;
import static ch.studentgrades.DBAdapter.getF_VOTO_GD;
import static ch.studentgrades.DBAdapter.getF_VOTO_ID;
import static ch.studentgrades.DBAdapter.getF_VOTO_ID_C;
import static ch.studentgrades.DBAdapter.getF_VOTO_MD;
import static ch.studentgrades.DBAdapter.getF_VOTO_N;

/**
 * Created by Gio on 01.12.2014.
 */
public class Voto {
    private long id;
    private long idCorso;
    private String nomeCorso;
    private String data;
    // Utili per ordinare i voti per data
    private int meseData;
    private int giornoData;
    private double nota;

    public Voto(long idCorso, String nomeCorso, String data, int giornoData, int meseData, double nota) {
        this.idCorso    = idCorso;
        this.nomeCorso  = nomeCorso;
        this.data       = data;
        this.giornoData = giornoData;
        this.meseData   = meseData;
        this.nota       = arrotondaMedia(nota, 2);
    }

    // Costruttore utilizzato se i dati vengono recuperati dal DB
    public Voto (Cursor cursor, String nomeCorso) {
        this.id                 = cursor.getLong( cursor.getColumnIndex( getF_VOTO_ID() ) );
        this.idCorso            = cursor.getLong( cursor.getColumnIndex( getF_VOTO_ID_C() ) );
        this.nomeCorso          = nomeCorso;
        this.data               = cursor.getString( cursor.getColumnIndex( getF_VOTO_D() ) );
        this.giornoData         = cursor.getInt( cursor.getColumnIndex( getF_VOTO_GD() ) );
        this.meseData           = cursor.getInt( cursor.getColumnIndex( getF_VOTO_MD() ) );
        this.nota               = cursor.getDouble( cursor.getColumnIndex( getF_VOTO_N() ) );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCorso() {
        return nomeCorso;
    }

    public void setNomeCorso(String nomeCorso) {
        this.nomeCorso = nomeCorso;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getMeseData() {
        return meseData;
    }

    public void setMeseData(int meseData) {
        this.meseData =meseData;
    }

    public int getGiornoData() {
        return giornoData;
    }

    public void setGiornoData(int giornoData) {
        this.giornoData = giornoData;
    }

    public double getNota() {
        return nota;
    }

    private double arrotondaMedia(double valore, int precisione) {
        long factor = (long) Math.pow(10, precisione);
        valore = valore * factor;
        long tmp = Math.round(valore);
        return (double) tmp / factor;
    }

    public void setNota(double nota) {
        this.nota = arrotondaMedia(nota, 2);
    }
}
