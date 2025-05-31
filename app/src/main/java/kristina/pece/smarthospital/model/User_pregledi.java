package kristina.pece.smarthospital.model;

import android.widget.TextView;

public class User_pregledi {

    private String datum;
    private String nazivPregleda;
    private String id;

    public User_pregledi(String datum, String nazivPregleda, String id) {
        this.datum = datum;
        this.nazivPregleda = nazivPregleda;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public String getNazivPregleda() {
        return nazivPregleda;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setNazivPregleda(String nazivPregleda) {
        this.nazivPregleda = nazivPregleda;
    }
}
