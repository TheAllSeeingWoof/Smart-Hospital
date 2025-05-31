package kristina.pece.smarthospital.model;

public class Korisnik {

    private String ime;
    private String prezime;
    private String username;
    private String pol;
    private String datum;
    private String password;
    private String pregledi;
    private String id;

    public Korisnik(String ime, String prezime, String username, String pol, String datum, String password, String pregledi, String id) {
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.pol = pol;
        this.datum = datum;
        this.password = password;
        this.pregledi = pregledi;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPregledi() {
        return pregledi;
    }

    public void setPregledi(String pregledi) {
        this.pregledi = pregledi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
