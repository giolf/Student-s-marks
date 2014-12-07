package myapps.studentsmarks;

/**
 * Created by Gio on 01.12.2014.
 */
public class Voto {
    private int id;
    private String nomeCorso;
    private String data;
    private double nota;

    public Voto(String nomeCorso, String data, double nota) {
        this.setNomeCorso(nomeCorso);
        this.setData(data);
        this.setNota(nota);
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

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
