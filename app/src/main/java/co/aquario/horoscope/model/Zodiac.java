package co.aquario.horoscope.model;

/**
 * Created by root1 on 8/26/15.
 */
public class Zodiac {
    public int id; // 1
    public String codeName; // leo
    public String nameTh; // ราศีสิงห์
    public String nameEn; // Leo

    public Zodiac(int id, String codeName, String nameTh, String nameEn) {
        this.id = id;
        this.codeName = codeName;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
    }
}
