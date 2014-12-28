package myapps.studentgrades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gio on 27.12.2014.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "studentgrades.db";
    private static final int DB_VERSION = 1;

    // Lo statement SQL di creazione delle tabelle del db
    private static final String DB_CREATE_TAB_ANNO = "CREATE TABLE anno ("                            +
                                                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"      +
                                                        "nome_anno TEXT NOT NULL,"                    +
                                                        "media_p REAL NOT NULL,"                      +
                                                        "media_a REAL NOT NULL"                       +
                                                     ");";

    private static final String DB_CREATE_TAB_CORSO = "CREATE TABLE corso ("                                           +
                                                         "_id INTEGER PRIMARY KEY AUTOINCREMENT,"                      +
                                                         "id_anno INTEGER NOT NULL,"                                   +
                                                         "nome_corso TEXT NOT NULL,"                                   +
                                                         "media_p REAL NOT NULL,"                                      +
                                                         "media_a REAL NOT NULL,"                                      +
                                                         "FOREIGN KEY(id_anno) REFERENCES anno(_id) ON DELETE CASCADE" +
                                                      ");";

    private static final String DB_CREATE_TAB_VOTO = "CREATE TABLE voto ("                                              +
                                                        "_id INTEGER PRIMARY KEY AUTOINCREMENT,"                        +
                                                        "id_corso INTEGER NOT NULL,"                                    +
                                                        "data TEXT NOT NULL,"                                           +
                                                        "mese_data INTEGER NOT NULL,"                                   +
                                                        "giorno_data INTEGER NOT NULL,"                                 +
                                                        "nota REAL NOT NULL,"                                           +
                                                        "FOREIGN KEY(id_corso) REFERENCES corso(_id) ON DELETE CASCADE" +
                                                     ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DB_CREATE_TAB_ANNO);
        database.execSQL(DB_CREATE_TAB_CORSO);
        database.execSQL(DB_CREATE_TAB_VOTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS anno");
        database.execSQL("DROP TABLE IF EXISTS corso");
        database.execSQL("DROP TABLE IF EXISTS voto");
        onCreate(database);
    }
}
