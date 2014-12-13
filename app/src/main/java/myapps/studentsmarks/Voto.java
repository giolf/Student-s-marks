package myapps.studentsmarks;

/**
 * Created by Gio on 01.12.2014.
 */
public class Voto {
    private int id;
    private String nomeCorso;
    /*viene solo salvato il campo data nel db*/
    private String data;
    /* meseData e giornoData sono campi di supporto
     * che non vengono salvati nel db
     * sono utili per ordinare i voti per data
    */
    private int meseData;
    private int giornoData;
    private double nota;

    public Voto(String nomeCorso, String data, int giornoData, int meseData, double nota) {
        this.nomeCorso  = nomeCorso;
        this.data       = data;
        this.giornoData = giornoData;
        this.meseData   = meseData;
        this.nota       = nota;
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

    public void setNota(double nota) {
        this.nota = nota;
    }
}
