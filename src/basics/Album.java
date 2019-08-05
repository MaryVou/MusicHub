/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.util.Date;

public class Album extends Release{
    private Artist artist;

    public Album(){
        super("Album"); 
    }
    
    public Album(basics.Artist Artist, String Title, boolean isOfficial, String Language, Date ReleaseDate, String Format, int TrackCount, String IDR) {
        super(Title, isOfficial, Language, ReleaseDate, Format, TrackCount, IDR);
        this.artist = Artist;
    }

    @Override
    public Artist getArtist() {
        return artist;
    }
    
    @Override
    public String getArtistName(){
        return artist.getName();
    }

    @Override
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    @Override
    public void setArtistName(String name){
       Artist tmp = new Person();
       tmp.setName(name);
       this.artist = tmp;
    }
    
}
