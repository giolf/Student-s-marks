package myapps.studentsmarks;

/**
 * Created by Gio on 01.12.2014.
 */
public class Voto {
    private int id;
    private int annoData;
    private int meseData;
    private int giornoData;
    private double nota;

    public Voto(int giornoData, int meseData, int annoData, double nota) {
        this.giornoData = giornoData;
        this.meseData   = meseData;
        this.annoData   = annoData;
        this.nota       = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnoData() {
        return annoData;
    }

    public void setAnnoData(int annoData) {
        this.annoData = annoData;
    }

    public int getMeseData() {
        return meseData;
    }

    public void setMeseData(int meseData) {
        this.meseData = meseData;
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
