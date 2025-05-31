package kristina.pece.smarthospital.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kristina.pece.smarthospital.model.Korisnik;
import kristina.pece.smarthospital.model.User_pregledi;
import kristina.pece.smarthospital.model.adminUredjaj;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "smartHospitalele.db";
    private static final int DATABASE_VERSION = 1;

    /*****KORISNICI*****/

    private static final String TABLE_NAME = "KORISNICI";
    private static final String COLUMN_NAME = "ime";
    private static final String COLUMN_PNAME = "prezime";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_POL = "pol";
    private static final String COLUMN_DATUM = "datum";
    private static final String COLUMN_PASSWORD = "lozinka"; //probs not how it is supposed to be like
    private static final String COLUMN_PREGLEDI = "pregledi";
    private static final String COLUMN_ID = "id";

    /****UREDJAJI****/

    private static final String TABLE_NAME2 = "UREDJAJI";
    private static final String COLUMN_UREDJAJ_IME = "ime";
    private static final String COLUMN_IMAGE = "img";
    private static final String COLUMN_SWITCH = "switch";
    private static final String COLUMN_UREDJAJ_ID = "id";

    /*****PREGLEDI*****/

    private static final String TABLE_NAME3 = "PREGLEDI";
    private static final String COLUMN_PREGLED_NAZIV = "ime";
    private static final String COLUMN_PREGLED_DATUM = "datum";
    private static final String COLUMN_PREGLED_ID = "id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE korisnici (NAME TEXT, PNAME TEXT, Pol TEXT, Datum TEXT, Pregledi TEXT, ID TEXT);
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PNAME + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_POL + " TEXT, " +
                COLUMN_DATUM + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_PREGLEDI + " TEXT, " +
                COLUMN_ID + " TEXT)"
        );

        //CREATE TABLE uredjaji (UREDJAJ_IME TEXT, IMAGE TEXT, SWITCH INTEGER);
        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (" +
                COLUMN_UREDJAJ_IME + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_SWITCH + " INTEGER, " +
                COLUMN_UREDJAJ_ID + " TEXT)"
        );

        //CREATE TABLE pregledi (PREGLED_NAZIV TEXT, DATUM TEXT, ID TEXT);
        db.execSQL("CREATE TABLE " + TABLE_NAME3 + " (" +
                COLUMN_PREGLED_NAZIV + " TEXT, " +
                COLUMN_PREGLED_DATUM + " TEXT, " +
                COLUMN_PREGLED_ID + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*****KORISNIK****/

    public boolean korisnikExists(Korisnik korisnik){

        String selection = COLUMN_USERNAME + " IN (?) and " + COLUMN_POL + " IN (?) and " + COLUMN_DATUM + " IN (?)";
        String[] selectionArgs = {korisnik.getUsername() , korisnik.getPol(), korisnik.getDatum()};

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return false;
        }

        close();
        return true;
    }

    public boolean usernameExists(String username){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return false;
        }

        close();
        return true;
    }

    public String getId(Korisnik korisnik){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERNAME + "=?", new String[]{korisnik.getUsername()}, null, null, null);

        if (cursor.getCount() <= 0) {

            close();
            return null;
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_ID));
    }

    public int generateId(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        int cnt = cursor.getCount();

        close();
        return cnt + 1;
    }

    public String getPassword(String username){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERNAME + "=?", new String[]{username},null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return null;
        }
        cursor.moveToFirst();
        String temp = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        close();
        return temp;
    }

    public Korisnik[] readAllKorisnici() {
        //SELECT * FROM korisnici

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return null;
        }

        Korisnik[] korisnik = new Korisnik[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            korisnik[i++] = createKorisnik(cursor);
        }

        close();
        return korisnik;
    }

    public Korisnik readKorisnik(String username) {
        //SELECT * FROM korisnik WHERE USERNAME = username

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USERNAME + "=?", new String[]{username}, null, null, null);

        if (cursor.getCount() <= 0) {

            close();
            return null;
        }
        cursor.moveToFirst();
        Korisnik korisnik = createKorisnik(cursor);
        close();
        return korisnik;
    }

    public Korisnik createKorisnik(Cursor cursor){
        String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String pname = cursor.getString(cursor.getColumnIndex(COLUMN_PNAME));
        String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
        String pol = cursor.getString(cursor.getColumnIndex(COLUMN_POL));
        String datum = cursor.getString(cursor.getColumnIndex(COLUMN_DATUM));
        String pass = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        String pregledi = cursor.getString(cursor.getColumnIndex(COLUMN_PREGLEDI));
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));

        return new Korisnik(name, pname, username, pol, datum, pass, pregledi, id);
    }

    public void insertKorisiik(Korisnik korisnik){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, korisnik.getIme());
        values.put(COLUMN_PNAME, korisnik.getPrezime());
        values.put(COLUMN_USERNAME, korisnik.getUsername());
        values.put(COLUMN_POL, korisnik.getPol());
        values.put(COLUMN_DATUM, korisnik.getDatum());
        values.put(COLUMN_PASSWORD, korisnik.getPassword());
        values.put(COLUMN_PREGLEDI, korisnik.getPregledi());
        values.put(COLUMN_ID, korisnik.getId());

        db.insert(TABLE_NAME, null, values);
        close();

    }

    public void deleteKorisnik(String id) {
        //DELETE FROM korisnici WHERE ID = id
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        close();
    }

    /****UREDJAJI****/

    public adminUredjaj[] readAllUredjaji() {
        //SELECT * FROM uredjaj

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return null;
        }

        adminUredjaj[] uredjaj = new adminUredjaj[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            uredjaj[i++] = createUredjaj(cursor);
        }

        close();
        return uredjaj;
    }

    public void updateDB(adminUredjaj u, boolean sw) {
        SQLiteDatabase db = getWritableDatabase();

        int newValue = 0;

        if(sw)
            newValue = 1;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SWITCH, newValue);
        db.update(TABLE_NAME2, cv, COLUMN_UREDJAJ_IME + "= ?", new String[] {u.getNazivUredjaj()});

        close();
    }

    public boolean uredjajExists(String id){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, null, COLUMN_UREDJAJ_ID + "=?", new String[]{id}, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return false;
        }

        close();
        return true;
    }


    public adminUredjaj createUredjaj(Cursor cursor){
        String ime = cursor.getString(cursor.getColumnIndex(COLUMN_UREDJAJ_IME));
        byte[] img = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
        Integer sw = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_SWITCH))); //sw = 1 true/ sw = 0 false
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_UREDJAJ_ID));

        return new adminUredjaj(ime, img, (sw.equals(1)), id);
    }

    public void insertUredjaj(adminUredjaj uredjaj){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_UREDJAJ_IME, uredjaj.getNazivUredjaj());
        values.put(COLUMN_IMAGE, uredjaj.getImg());
        values.put(COLUMN_SWITCH, uredjaj.getSw());
        values.put(COLUMN_UREDJAJ_ID, uredjaj.getId());

        db.insert(TABLE_NAME2, null, values);
        close();

    }

    public int generateUredjajId(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME2, null, null, null, null, null, null);

        int cnt = cursor.getCount();

        close();
        return cnt + 1;
    }

    public void deleteUredjaj(String ime) {
        //DELETE FROM uredjaji WHERE COLUMN_UREDJAJ_IME = ime
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME2, COLUMN_UREDJAJ_IME + "=?", new String[]{ime});
        close();
    }


    /*****PREGLEDI*****/

    public User_pregledi[] readAllPregledi() {
        //SELECT * FROM pregledi

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME3, null, null, null, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return null;
        }

        User_pregledi[] pregled = new User_pregledi[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            pregled[i++] = createPregled(cursor);
        }

        close();
        return pregled;
    }

    public User_pregledi[] readIDPregledi( String id) {
        //SELECT * FROM pregledi WHERE COLUMN_PREGLED_ID = id

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME3, null, COLUMN_PREGLED_ID + "=?", new String[]{id}, null, null, null);

        if (cursor.getCount() <= 0) {
            close();
            return null;
        }

        User_pregledi[] pregled = new User_pregledi[cursor.getCount()];
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            pregled[i++] = createPregled(cursor);
        }

        close();
        return pregled;
    }

    public User_pregledi createPregled(Cursor cursor){
        String naziv = cursor.getString(cursor.getColumnIndex(COLUMN_PREGLED_NAZIV));
        String datum = cursor.getString(cursor.getColumnIndex(COLUMN_PREGLED_DATUM));
        String id = cursor.getString(cursor.getColumnIndex(COLUMN_PREGLED_ID));

        return new User_pregledi(naziv, datum, id);
    }

    public void insertPregled(User_pregledi pregled){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PREGLED_NAZIV, pregled.getNazivPregleda());
        values.put(COLUMN_PREGLED_DATUM, pregled.getDatum());
        values.put(COLUMN_PREGLED_ID, pregled.getId());

        db.insert(TABLE_NAME3, null, values);
        close();

    }

    public void deletePregled(String naziv) {
        //DELETE FROM pregledi WHERE COLUMN_PREGLED_NAZIV = naziv
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME3, COLUMN_PREGLED_NAZIV + "=?", new String[]{naziv});
        close();
    }
}
