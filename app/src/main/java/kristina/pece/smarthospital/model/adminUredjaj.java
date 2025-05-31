package kristina.pece.smarthospital.model;

public class adminUredjaj {

    private String nazivUredjaj;
    private String neaktivan;
    private byte[] img;
    private boolean mSwitch;
    private String id;

    public adminUredjaj(String nazivUredjaj, byte[] img, boolean mSwitch, String id) {
        this.nazivUredjaj = nazivUredjaj;
        this.img = img;
        this.mSwitch = mSwitch;
        this.id = id;
    }

    public String getNazivUredjaj() {
        return nazivUredjaj;
    }

    public void setNazivUredjaj(String nazivUredjaj) {
        this.nazivUredjaj = nazivUredjaj;
    }

    public String getNeaktivan() {
        return neaktivan;
    }

    public void setNeaktivan(String neaktivan) {
        this.neaktivan = neaktivan;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean getSw() {
        return mSwitch;
    }

    public void setSw(boolean sw) {
        this.mSwitch = sw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
