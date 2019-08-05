/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.util.ArrayList;
import java.util.Date;

public abstract class Release {
    private String Title;
    private boolean isOfficial;
    private String Language;
    private Date ReleaseDate;
    private String Format;
    private int TrackCount;
    private String IDR;
    private Artist Artist;
    private ArrayList<Artist> Artists;
    private String Type;
    private String Country;

    public Release(){}
    
    public Release(String Type){this.Type=Type;}
    
    public Release(String Title, boolean isOfficial, String Language, Date ReleaseDate, String Format, int TrackCount, String IDR) {
        this.Title = Title;
        this.isOfficial = isOfficial;
        this.Language = Language;
        this.ReleaseDate = ReleaseDate;
        this.Format = Format;
        this.TrackCount = TrackCount;
        this.IDR = IDR;
    }
    
    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getStatus() {
        return isOfficial ? "Official" : "Unofficial";
    }

    public void isOfficial(boolean isOfficial) {
        this.isOfficial = isOfficial;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public Date getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(Date ReleaseDate) {
        this.ReleaseDate = ReleaseDate;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String Format) {
        this.Format = Format;
    }

    public int getTrackCount() {
        return TrackCount;
    }

    public void setTrackCount(int TrackCount) {
        this.TrackCount = TrackCount;
    }

    public String getIDR() {
        return IDR;
    }

    public void setIDR(String IDR) {
        this.IDR = IDR;
    }

    public Artist getArtist() {
        return Artist;
    }

    public void setArtist(Artist Artist) {
        this.Artist = Artist;
    }
    
    public void setArtistName(String name){
       Artist tmp = new Person();
       tmp.setName(name);
       this.Artist = tmp;
    }
    public ArrayList<Artist> getArtists() {
        return Artists;
    }

    public void setArtists(ArrayList<Artist> Artists) {
        this.Artists = Artists;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }
    
    public String getArtistName(){
        return null;
    }
    
}
