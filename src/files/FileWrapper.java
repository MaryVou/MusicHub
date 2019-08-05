/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import basics.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.JSONException;

public class FileWrapper {

    public static void writeArtistsToFile(String filename, ArrayList<Artist> artists) throws FileNotFoundException, IOException {
        Gson gsonBuilder = new GsonBuilder().create();
        
        //discriminate the 2 genres to avoid serialization and deserialization issues
        ArrayList<Person> soloArtists = new ArrayList<Person>();
        ArrayList<Group> groups = new ArrayList<Group>();
        for (int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getType().equals("Person")) {
                soloArtists.add((Person) artists.get(i));
            } else {
                groups.add((Group) artists.get(i));
            }
        }
        String JsonFromJava1 = gsonBuilder.toJson(soloArtists);
        String JsonFromJava2 = gsonBuilder.toJson(groups);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(JsonFromJava1);
        writer.newLine();
        writer.write(JsonFromJava2);
        writer.close();
    }

    public static ArrayList<Artist> readArtistsFromFile(String filename) throws FileNotFoundException, IOException, JSONException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String JsonArrayString1 = reader.readLine();
        String JsonArrayString2 = reader.readLine();
        Gson gsonBuilder = new GsonBuilder().create();
        ArrayList<Artist> artists = new ArrayList<Artist>();
        ArrayList<Person> soloArtists = new ArrayList<Person>();
        ArrayList<Group> groups = new ArrayList<Group>();
        soloArtists = gsonBuilder.fromJson(JsonArrayString1, new TypeToken<ArrayList<Person>>() {
        }.getType());
        groups = gsonBuilder.fromJson(JsonArrayString2, new TypeToken<ArrayList<Group>>() {
        }.getType());
        //combine the 2 arrays in one:
        artists.addAll(soloArtists);
        artists.addAll(groups);
        return artists;
    }

    public static void writeReleasesToFile(String filename, ArrayList<Release> releases) throws FileNotFoundException, IOException {
        Gson gsonBuilder = new GsonBuilder().create();
        
        //discriminate the 2 genres to avoid serialization and deserialization issues
        ArrayList<Album> albums = new ArrayList<Album>();
        ArrayList<Compilation> compilations = new ArrayList<Compilation>();
        for (int i = 0; i < releases.size(); i++) {
            if (releases.get(i).getType().equals("Album")) {
                albums.add((Album) releases.get(i));
            } else {
                compilations.add((Compilation) releases.get(i));
            }
        }
        String JsonFromJava1 = gsonBuilder.toJson(albums);
        String JsonFromJava2 = gsonBuilder.toJson(compilations);
        System.out.println("write albums to file: "+JsonFromJava1);
        System.out.println("write compilations to file: "+JsonFromJava2);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(JsonFromJava1);
        writer.newLine();
        writer.write(JsonFromJava2);
        writer.close();
    }

    public static ArrayList<Release> readReleasesFromFile(String filename) throws FileNotFoundException, IOException, JSONException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String JsonArrayString1 = reader.readLine();
        String JsonArrayString2 = reader.readLine();
        Gson gsonBuilder = new GsonBuilder().create();
        ArrayList<Release> releases = new ArrayList<Release>();
        ArrayList<Album> albums = new ArrayList<Album>();
        ArrayList<Compilation> compilations = new ArrayList<Compilation>();
        albums = gsonBuilder.fromJson(JsonArrayString1, new TypeToken<ArrayList<Album>>() {
        }.getType());
        compilations = gsonBuilder.fromJson(JsonArrayString2, new TypeToken<ArrayList<Compilation>>() {
        }.getType());
        //combine the 2 arrays in one:
        releases.addAll(albums);
        releases.addAll(compilations);
        return releases;
    }

}
