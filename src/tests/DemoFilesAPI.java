/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import basics.*;
import files.*;
import db.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONException;

public class DemoFilesAPI {

    public static void main(String[] args) throws IOException, JSONException, ParseException, ClassNotFoundException {
        
        /*-----------TESTING THE SECOND STAGE-------------------*/
        ArrayList<Artist> allArtists;
        allArtists = APIWrapper.getArtist("Linkin Park", "US" ,"10");

        ArrayList<Release> allReleases;
        allReleases = APIWrapper.getRelease("love", "US","10");

        FileWrapper.writeArtistsToFile(".\\myartists.txt", allArtists);
        System.out.println(FileWrapper.readArtistsFromFile(".\\myartists.txt"));

        FileWrapper.writeReleasesToFile(".\\myreleases.txt", allReleases); 
        //System.out.println(FileWrapper.readReleasesFromFile(".\\myreleases.txt"));
        
        allArtists = FileWrapper.readArtistsFromFile(".\\myartists.txt");
        //allReleases = FileWrapper.readReleasesFromFile(".\\myreleases.txt");
        
        for (int counter = 0; counter < allArtists.size(); counter++) {
            System.out.println("=============================================");
            System.out.println("Name: " + allArtists.get(counter).getName());
            System.out.println("Type: " + allArtists.get(counter).getType());
            System.out.println("Country: " + allArtists.get(counter).getCountry());
            System.out.println("Aliases: " + allArtists.get(counter).getAliases());
            System.out.println("Tags: " + allArtists.get(counter).getTags());
            System.out.println("Cities: " + allArtists.get(counter).getCities());
            System.out.println("Begin Date: " + allArtists.get(counter).getBeginDate());
            System.out.println("End Date: " + allArtists.get(counter).getEndDate());
            System.out.println("ID: " + allArtists.get(counter).getIDA());
            System.out.println("---------------------------------------------");
        }

        for (int counter = 0; counter < allReleases.size(); counter++) {
            System.out.println("=============================================");
            System.out.println("Title: " + allReleases.get(counter).getTitle());
            System.out.println("Type: " + allReleases.get(counter).getType());
            if(allReleases.get(counter).getType().equals("Album")&&allReleases.get(counter).getArtist()!=null){
                System.out.println("Artist: "+allReleases.get(counter).getArtist().getName());
            }
            else if(allReleases.get(counter).getType().equals("Compilation")){
                System.out.println("Artists: "+allReleases.get(counter).getArtists());
            }
            System.out.println("Status: " + allReleases.get(counter).getStatus());
            System.out.println("Format: " + allReleases.get(counter).getFormat());
            System.out.println("Language: " + allReleases.get(counter).getLanguage());
            System.out.println("Release Date: " + allReleases.get(counter).getReleaseDate());
            System.out.println("Track count: " + allReleases.get(counter).getTrackCount());
            System.out.println("ID: " + allReleases.get(counter).getIDR());
            System.out.println("----------------------------------------------------");

        }
        /*----------------------------------------------------------------*/
        
        /*-----------TESTING THE THIRD STAGE-------------------*/
        
        //In case of problems use the following:
        /*
                    Database.Connect();
                    Database.dropArtistsTable();
                    //Database.dropReleasesTable();
                    Database.Disconnect();
         */
        
        
        try {
            if (Database.Connect() == true) {

                //checking if releases table exists
                if (Database.tableExist("RELEASES") == false) {
                    Database.createReleaseTable();
                }

                //checking if artists table exists 
                if (Database.tableExist("ARTISTS") == false) {
                    Database.createArtistTable();
                }
                
                //inserting the lists in each table
                Database.insertReleaseList(allReleases);
                Database.insertArtistList(allArtists);
                
                //getting results using keywords
                ArrayList<Release> results = Database.selectFromReleases("love");
                for (int i = 0; i < results.size(); i++) {
                    System.out.println(results.get(i));
                }
                ArrayList<Artist> results2 = Database.selectFromArtists("name");
                for (int i = 0; i < results2.size(); i++) {
                    System.out.println(results2.get(i));
                }
                
               Database.Disconnect();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    
    /*-------------------------------------------------------------*/
    }
}
