/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.util.ArrayList;
import java.util.Date;

public class Compilation extends Release{
    private ArrayList<Artist> Artists;

    public Compilation(){
        super("Compilation");
        this.Artists = new ArrayList<Artist>();
    }

    public Compilation(ArrayList<basics.Artist> Artists, String Title, boolean isOfficial, String Language, Date ReleaseDate, String Format, int TrackCount, String IDR) {
        super(Title, isOfficial, Language, ReleaseDate, Format, TrackCount, IDR);
        this.Artists = Artists;
    }
    
    @Override
    public ArrayList<Artist> getArtists() {
        return Artists;
    }
    
    public void setArtists(Artist Artist) {
        Artists.add(Artist);
    }
}