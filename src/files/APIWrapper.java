/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import basics.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class APIWrapper {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /*It takes as a parameter a String Url and returns a Json object*/
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        //System.out.println(url);
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
             
        }
    }

    /*-------------------------------------------------------------------------------------------*/
    //general method for both albums and compilations
    private static Release ReleaseGeneral(Release currRelease, JSONObject currJson) throws JSONException, ParseException {

        /* Match Title of JSONObject to Release object */
        if (!currJson.isNull("title")) {
            currRelease.setTitle(currJson.getString("title"));
        }

        /* Match isOfficial of JSONObject to Release object */
        if (!currJson.isNull("status")) {
            currRelease.isOfficial(true);
        } else {
            currRelease.isOfficial(false);
        }

        /* Match Language of JSONObject to Release object */
        if (!currJson.isNull("text-representation")) {
            if (!currJson.getJSONObject("text-representation").isNull("language")) {
                currRelease.setLanguage(currJson.getJSONObject("text-representation").getString("language"));
            }
        }

        /* Match Release date of JSONObject to Release object*/
        if (!currJson.isNull("date")) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                currRelease.setReleaseDate(format2.parse(currJson.getString("date")));
            } catch (Exception e) {
                currRelease.setReleaseDate(format1.parse(currJson.getString("date")));
            }
        }

        /* Match format of JSONObject to Release object*/
        if (currJson.isNull("release-group") == false) {
            if (currJson.isNull("media") == false) {
                if (currJson.getJSONArray("media").getJSONObject(0).isNull("format") == false) {
                    currRelease.setFormat(currJson.getJSONArray("media").getJSONObject(0).getString("format"));
                }
            }
        }

        /* Match Track Count of JSONObject to Release object*/
        if (currJson.isNull("track-count") == false) {
            currRelease.setTrackCount(currJson.getInt("track-count"));
        }

        /* Match Country of JSONObject to Release object*/
        if (!currJson.isNull("country")) {
            currRelease.setCountry(currJson.getString("country"));
        }

        /* Match IDR of JSONObject to Release object */
        if (!currJson.isNull("id")) {
            currRelease.setIDR(currJson.getString("id"));
        }

        return currRelease;

    }

    private static Artist getArtistForRelease(Release currRelease, JSONObject currJson) throws JSONException, IOException, ParseException {
        /*Problem: Album and compilation contains the info: artist. However there is no JSONObject in this search
                        to discriminate whether the artist is a person or a group, so that we can create an object.
                        To solve this we will run a second search with the name of the releases's artist and use their 
                        id to match the result. Then we'll get the type and finally we will know what object to create*/
        //creating temporary variables
        String name = new String();
        String id = new String();
        JSONObject res = new JSONObject();
        Artist currArtist = null;
        JSONObject currJson2 = new JSONObject();

        //getting the name and the id of the artist
        if (!currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").isNull("name")) {
            name = currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name");
        }
        if (!currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").isNull("id")) {
            id = currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("id");
        }
        //checking if we got both name and id
        
        if (!(name.equals("null")) && !(id.equals("null"))) {

            //searching the artist by name
            name = name.replace(" ", "+");
            res = readJsonFromUrl("https://musicbrainz.org/ws/2/artist?query=" + name + "&fmt=json");
            
            
            //matching the id
            for (int i = 0; i < res.getJSONArray("artists").length(); i++) {
                currJson2 = res.getJSONArray("artists").getJSONObject(i);

                if (!currJson2.isNull("id")) {
                    if (currJson2.getString("id").equals(id)) {

                        //getting the artist's information
                        if (!currJson2.isNull("type")) {
                            if (currJson2.getString("type").equals("Person")) {
                                if (!currJson2.isNull("gender")) {
                                    if (currJson2.getString("gender").equals("male")) {
                                        currArtist = new Person(true);
                                    } else {
                                        currArtist = new Person(false);
                                    }
                                } else {
                                    currArtist = new Person();
                                }

                                currArtist = ArtistGeneral(currArtist, currJson2);

                            } else if (currJson2.getString("type").equals("Group")) {
                                currArtist = new Group();

                                currArtist = ArtistGeneral(currArtist, currJson2);
                            }
                            //we now have the artist information
                        }
                    }
                }
            }
        }
        return currArtist;
    }

    private static ArrayList<Release> ReleaseObjectify(JSONObject output) throws JSONException, ParseException, IOException {
        ArrayList<Release> result = new ArrayList<Release>();
        JSONObject currJson = new JSONObject();
        Release currRelease = new Album();
        //System.out.println(output);
        for (int i = 0; i < output.getJSONArray("releases").length(); i++) {

            currJson = output.getJSONArray("releases").getJSONObject(i);
            
            if(!currJson.isNull("release-group")){
                if(!currJson.getJSONObject("release-group").isNull("primary-type")){
                    if(currJson.getJSONObject("release-group").getString("primary-type").equals("Album") || currJson.getJSONObject("release-group").getString("primary-type").equals("Single")){
                        currRelease = new Album();
                        
                    }else if(currJson.getJSONObject("release-group").getString("primary-type").equals("Compilation")){
                        currRelease = new Compilation();
                    }
                }
            }
             currRelease = ReleaseGeneral(currRelease, currJson);
                  
                        currRelease.setArtistName(currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name"));
                       
                        result.add(currRelease);
            
            /*
            //checking if the release object is of type "album"
            if (!currJson.isNull("release-group")) {
                if (!currJson.getJSONObject("release-group").isNull("primary-type")) {
                    if (currJson.getJSONObject("release-group").getString("primary-type").equals("Album") == true) {
                        currRelease = new Album();

                        currRelease = ReleaseGeneral(currRelease, currJson);
                        System.out.println(currRelease.getTitle());
                        if(!currJson.isNull("artist-credit")){
                            currRelease.setArtist(currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name"));
                        }
                        
                        //if (getArtistForRelease(currRelease, currJson) != null) {
                          //  currRelease.setArtist(getArtistForRelease(currRelease, currJson));
                        //}

                        result.add(currRelease);
                    }
                }

                //checking if the release is of type "compilation"
                if (!currJson.getJSONObject("release-group").isNull("secondary-types")) {
                    for (i = 0; i < currJson.getJSONObject("release-group").getJSONArray("secondary-types").length(); i++) {
                        if (currJson.getJSONObject("release-group").getJSONArray("secondary-types").getString(i).equals("Compilation")) {
                            currRelease = new Compilation();
                            //System.out.println("Compilation");
                            currRelease = ReleaseGeneral(currRelease, currJson);
                            
                               System.out.println("Compilation");
                            // Match Artists of JSONObject to Release object 
                            if (!currJson.isNull("artist-credit")) {
                                //ArrayList<Artist> temp = new ArrayList<Artist>();
                                
                                //for (int cnt = 0; cnt < currJson.getJSONArray("artist-credit").length(); cnt++) {
                                  //  temp.add(getArtistForRelease(currRelease, currJson));
                                    
                                //}
                                //currRelease.setArtists(temp);
                                
                            
                                    
                            currRelease.setArtist(currJson.getJSONArray("artist-credit").getJSONObject(0).getJSONObject("artist").getString("name"));
                        }
                            
                            result.add(currRelease);
                        }
                    }
                }
            }*/
        }
        return result;
    }

    public static ArrayList<Release> getRelease(String release, String country ,String numberOfArtists) throws IOException, JSONException, ParseException {
        release = release.replace(" ", "+");
        //System.out.println("https://musicbrainz.org/ws/2/release?query=" + release + "&fmt=json&limit=" + numberOfArtists);
        JSONObject output = null;
        if (country.equals("(none)")) {
            output = readJsonFromUrl("https://musicbrainz.org/ws/2/release?query=" + release + "&fmt=json&limit=" + numberOfArtists);
        } else {
            output = readJsonFromUrl("http://musicbrainz.org/ws/2/release/?query=" + release + "%20AND%20country:" + country + "&fmt=json&limit=" + numberOfArtists);
        }
        ArrayList<Release> releaseList = ReleaseObjectify(output);
        return releaseList;
    }

    private static Artist ArtistGeneral(Artist currArtist, JSONObject currJson) throws JSONException, ParseException {

        ArrayList<String> temp = new ArrayList<String>();

        /*Match Name of JSONObject to Artist object*/
        if (!currJson.isNull("name")) {
            currArtist.setName(currJson.getString("name"));
        }

        /* Match Country of JSONObject to Artist object */
        if (!currJson.isNull("country")) {
            currArtist.setCountry(currJson.getString("country"));
        }

        /* Match Begin-Area of JSONObject to Artist object */
        if (!currJson.isNull("begin-area")) {
            if (!currJson.isNull("name")) {
                currArtist.setCities(currJson.getJSONObject("begin-area").getString("name"));
            }
        }
        /* Match Aliases of JSONObject to Artist object */
        if (!currJson.isNull("aliases")) {
            for (int j = 0; j < currJson.getJSONArray("aliases").length(); j++) {
                temp.add(currJson.getJSONArray("aliases").getJSONObject(j).getString("name"));

            }
            currArtist.setAliases(temp);
            temp = new ArrayList<String>();
        }

        /* Match Tags of JSONObject to Artist object */
        if (!currJson.isNull("tags")) {
            for (int j = 0; j < currJson.getJSONArray("tags").length(); j++) {
                temp.add(currJson.getJSONArray("tags").getJSONObject(j).getString("name"));
            }
            currArtist.setTags(temp);
            temp = new ArrayList<String>();
        }

        /* Match BeginDate of JSONObject to Artist object */
        if (!currJson.isNull("life-span")) {
            if (!currJson.getJSONObject("life-span").isNull("begin")) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    currArtist.setBeginDate(format2.parse(currJson.getJSONObject("life-span").getString("begin")));
                } catch (Exception e) {
                    currArtist.setBeginDate(format1.parse(currJson.getJSONObject("life-span").getString("begin")));
                }
            }
        }

        /* Match EndDate of JSONObject to Artist object */
        if (!currJson.isNull("life-span")) {
            if (!currJson.getJSONObject("life-span").isNull("end")) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    currArtist.setEndDate(format2.parse(currJson.getJSONObject("life-span").getString("end")));
                } catch (Exception e) {
                    currArtist.setEndDate(format1.parse(currJson.getJSONObject("life-span").getString("end")));
                }
            }
        }

        /* Match ID of JSONObject to Artist object */
        if (!currJson.isNull("id")) {
            currArtist.setIDA(currJson.getString("id"));
        }

        return currArtist;
    }

    private static ArrayList<Artist> ArtistsObjectify(JSONObject output) throws JSONException, ParseException {
        ArrayList<Artist> result = new ArrayList<Artist>();
        JSONObject currJson = new JSONObject();
        Artist currArtist;
        for (int i = 0; i < output.getJSONArray("artists").length(); i++) {
            currJson = output.getJSONArray("artists").getJSONObject(i);
            if (!currJson.isNull("type")) {
                if (currJson.getString("type").equals("Person")) {
                    if (!currJson.isNull("gender")) {
                        /* Match gender of JSONObject to Artist object */
                        boolean isMale;
                        if (currJson.getString("gender").equals("male")) {
                            isMale = true;
                        } else {
                            isMale = false;
                        }

                        currArtist = new Person(isMale);
                    } else {
                        currArtist = new Person();
                    }

                    currArtist = ArtistGeneral(currArtist, currJson);

                    result.add(currArtist);
                } else if (currJson.getString("type").equals("Group")) {

                    currArtist = new Group();

                    currArtist = ArtistGeneral(currArtist, currJson);

                    result.add(currArtist);
                }
            }
        }
        return result;
    }

    public static ArrayList<Artist> getArtist(String artist, String country, String numberOfArtists) throws IOException, JSONException, ParseException {
        artist = artist.replace(" ", "+");
        JSONObject output = null;

        if (country.equals("(none)")) {
            output = readJsonFromUrl("https://musicbrainz.org/ws/2/artist?query=" + artist + "&fmt=json&limit=" + numberOfArtists);
        } else {
            output = readJsonFromUrl("http://musicbrainz.org/ws/2/artist/?query=" + artist + "%20AND%20country:" + country + "&fmt=json&limit=" + numberOfArtists);
        }

        ArrayList<Artist> artistList = ArtistsObjectify(output);
        return artistList;
    }
}
