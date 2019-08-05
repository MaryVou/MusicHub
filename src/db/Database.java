/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import basics.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database {

    static Statement st;
    static ResultSet rs;
    static Connection conn;
    
    public static Connection getConn(){
        return conn;
    }

    public static boolean Connect() throws ClassNotFoundException {
        System.out.println("Making an attempt to connect to the database");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection(""); //Enter database, username and password i.e conn = DriverManager.getConnection("jdbc:oracle:thin:@example", "root", "root")
            System.out.println("Connected to the database");
            return true;
        } catch (SQLException ex) {
            System.out.println("Unable to connect with the database");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

    }

    public static void Disconnect() {
        System.out.println("Disconnecting from the database..");
        try {
            conn.close();
            System.out.println("Disconnected!");
        } catch (Exception e) {
            System.out.println("Unable to disconnect");
        }
    }

    public static boolean tableExist(String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }

    public static boolean createReleaseTable() {
        try {
            st = conn.createStatement();
            String create = "CREATE TABLE RELEASES("
                    + "TITLE VARCHAR2(20),"
                    + "TYPE VARCHAR2(20),"
                    + "STATUS VARCHAR2(10),"
                    + "FORMAT VARCHAR2(20),"
                    + "LANGUAGE VARCHAR2(10),"
                    + "RELEASE_DATE DATE,"
                    + "TRACKCOUNT INT,"
                    + "ID VARCHAR2(50))";
            st.executeUpdate(create);
            String dateFormat = "alter session set nls_date_format = 'yyyy-mm-dd'";
            st.executeUpdate(dateFormat);
            System.out.print("Release table created!");
            return true;
        } catch (Exception e) {
            System.out.println("Unable to create table releases");
            //System.err.println(e);
            return false;
        }
    }

    public static void dropReleasesTable() {
        try {
            st = conn.createStatement();
            String drop = "drop table releases";
            st.executeUpdate(drop);
            System.out.println("Table 'releases' dropped");
        } catch (Exception e) {
            System.out.println("Unable to drop table releases");
           // System.err.println(e);
        }
    }

    public static boolean recordExists(String id, String tableName) {
        boolean rExists = false;
        try {
            st = conn.createStatement();
            String select = "select id from " + tableName + " where id ='" + id + "'";
            rs = st.executeQuery(select);
            while (rs.next()) {
                rExists = true;
                break;
            }
        } catch (Exception e) {
            System.out.println("Error while checking the existing records.");
           // System.err.println(e);
        }
        return rExists;
    }

    public static boolean insertReleaseObject(Release release) throws SQLException {
        boolean success = false;
        try {
            /*Before inserting new records, we must check if a record already exists
            using the unique id*/
            if (recordExists(release.getIDR(), "RELEASES") == false) {
                StringBuilder insert = new StringBuilder("INSERT INTO RELEASES VALUES(");
                //System.out.println(release.getArtistName());
                if (release.getTitle() != null) //Title
                {
                    insert.append("'" + release.getTitle() + "'");
                } else {
                    insert.append("null");
                }

                if (release.getType() != null) //Type
                {
                    insert.append(",'" + release.getType() + "'");
                } else {
                    insert.append(",null");
                }

                if (release.getStatus() != null) //Status
                {
                    insert.append(",'" + release.getStatus() + "'");
                } else {
                    insert.append(",null");
                }

                if (release.getFormat() != null) //Format
                {
                    insert.append(",'" + release.getFormat() + "'");
                } else {
                    insert.append(",null");
                }

                if (release.getLanguage() != null) //Language
                {
                    insert.append(",'" + release.getLanguage() + "'");
                } else {
                    insert.append(",null");
                }
                      

               
                if(release.getReleaseDate()!= null){    //ReleaseDate
                    java.util.Date date = release.getReleaseDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String format = formatter.format(date);
                    
                    insert.append(",TO_DATE('"+ format +"','YYYY-MM-DD')");
                }
                else
                    insert.append(",null");
                
                insert.append("," + release.getTrackCount());      //TrackCount
                
                if (release.getIDR() != null) //IDR
                {
                    insert.append(",'" + release.getIDR() + "'");
                } else {
                    insert.append(",null");
                }
                
                if (release.getArtistName() != null){
                    insert.append(",'" +release.getArtistName() +"'");
                            
                }else{
                    insert.append(",null");
                }

                insert.append(")");

                String query = insert.toString();
                st.executeQuery(query);

                success = true;
            }
        } catch (Exception e) {
            System.out.println("Error while adding objects into the 'releases' table.");
           // System.err.println(e);
        }
        return success;
    }

    public static void insertReleaseList(ArrayList<Release> releases) {
        try {
            int count = 0;
            for (int i = 0; i < releases.size(); i++) {
                if (insertReleaseObject(releases.get(i)) == true) {
                    count++;
                }
            }
            System.out.println("Added " + count + " records in the table 'RELEASES'.");
        } catch (Exception e) {
            System.out.println("Error while adding the release list");
           // System.err.println(e);
        }
    }

    public static ArrayList<Release> selectFromReleases(String key) {
        ArrayList<Release> resultsArray = new ArrayList<Release>();
        Release release;
        try {
            st = conn.createStatement();
            String select = "SELECT * FROM RELEASES";
            rs = st.executeQuery(select);
            while (rs.next()) {
                if(rs.getString("type").equals("Album")){
                    release = new Album();
                }else{
                    release = new Compilation();
                }
                if(rs.getString("title").contains(key)){
                    release.setTitle(rs.getString("title"));
                    release.setType(rs.getString("type"));
                    release.isOfficial(rs.getString("status").equals("Official")?true:false);
                    release.setFormat(rs.getString("format"));
                    release.setLanguage(rs.getString("language"));
                    release.setReleaseDate(rs.getDate("release_date"));
                    release.setTrackCount(rs.getInt("trackcount"));
                    
                    release.setIDR(rs.getString("id"));
                    release.setArtistName(rs.getString("ARTIST"));
                    //System.out.println(rs.getString("artist"));
                    
                    
                    resultsArray.add(release);
                }
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Couldn't display results");
           // System.err.println(e);
        }
        return resultsArray;
    }

    public static boolean createArtistTable() {
        try {
            st = conn.createStatement();
            String create = "CREATE TABLE ARTISTS("
                    + "NAME VARCHAR2(20),"
                    + "TYPE VARCHAR(20),"
                    + "COUNTRY VARCHAR2(10),"
                    + "ALIASES VARCHAR2(500),"
                    + "TAGS VARCHAR2(500),"
                    + "CITIES VARCHAR2(20),"
                    + "BEGIN_DATE DATE,"
                    + "END_DATE DATE,"
                    + "ID VARCHAR2(50))";
            st.executeQuery(create);
            String dateFormat = "alter session set nls_date_format = 'yyyy-mm-dd'";
            st.executeUpdate(dateFormat);
            System.out.print("Artists table created!");
            return true;
        } catch (Exception e) {
            System.out.println("Unable to create table Artists");
           // System.err.println(e);
            return false;
        }
    }

    public static void dropArtistsTable() {
        try {
            st = conn.createStatement();
            String drop = "drop table artists";
            st.executeUpdate(drop);
            System.out.println("Table 'artists' dropped");
        } catch (Exception e) {
            System.out.println("Unable to drop table 'artists'");
            //System.err.println(e);
        }
    }

    public static boolean insertArtistObject(Artist artist) {
        boolean success = false;
        //System.out.println("Entered Insert");
        try {
            if (recordExists(artist.getIDA(), "ARTISTS1")==false) {
                StringBuilder insert = new StringBuilder("INSERT INTO ARTISTS1 VALUES(");
                if (artist.getName() != null) //Name
                {
                    insert.append("'" + artist.getName() + "'");
                } else {
                    insert.append("null");
                }
                
                if (artist.getType() != null) //Type
                {
                    insert.append(",'" + artist.getType() + "'");
                } else {
                    insert.append(",null");
                }
                
                if (artist.getCountry() != null) //Country
                {
                    insert.append(",'" + artist.getCountry() + "'");
                } else {
                    insert.append(",null");
                }

                if (artist.getAliases() != null) //Aliases
                {
                    insert.append(",'" + artist.getAliases() + "'");
                } else {
                    insert.append(",null");
                }

                if (artist.getTags() != null) //Tags
                {
                    insert.append(",'" + artist.getTags() + "'");
                } else {
                    insert.append(",null");
                }

                if (artist.getCities() != null) //Cities
                {
                    insert.append(",'" + artist.getCities() + "'");
                } else {
                    insert.append(",null");
                }
                
                if(artist.getBeginDate()!= null){
                    java.util.Date date = artist.getBeginDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String format = formatter.format(date);
                    insert.append(",TO_DATE('"+ format +"\',\'YYYY-MM-DD\')");//BeginDate
                }                                   
                else
                    insert.append(",null");
                
                if(artist.getEndDate()!= null){                 //EndDate
                    java.util.Date date = artist.getBeginDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String format = formatter.format(date);
  
                    insert.append(",TO_DATE('"+ format +"\',\'YYYY-MM-DD\')");
                }
                else
                    insert.append(",null");
                
                if (artist.getIDA() != null) //IDA
                {
                    insert.append(",'" + artist.getIDA() + "'");
                } else {
                    insert.append(",null");
                }

                insert.append(")");
                
                String query = insert.toString();
                //System.out.println("Q:" +  query);
                st.executeQuery(query);
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Error while adding objects into the 'artists' table.");
           // System.err.println(e);
        }
        return success;
    }

    public static void insertArtistList(ArrayList<Artist> artists) throws SQLException {
        int count = 0;
        for (int i = 0; i < artists.size(); i++) {
            if(insertArtistObject(artists.get(i))==true){
                count++;
            }
        }
        System.out.println("Added " + count + " records in the 'ARTISTS' table.");
    }

    public static ArrayList<Artist> selectFromArtists(String key) {
        ArrayList<Artist> resultsArray = new ArrayList<Artist>();
        Artist artist;
        try {
            st = conn.createStatement();
            String select = "SELECT * from ARTISTS1 where name like \'%"+ key+ "%\'";
            //String select = "SELECT name,type,country,aliases,tags,cities,begin_date,end_date,id FROM ARTISTS";
            rs = st.executeQuery(select);
            while (rs.next()) {
                if(rs.getString("type").equals("Person")){
                    artist = new Person();
                }else{
                    artist = new Group();
                }
                
                if(rs.getString("name").contains(key)){
                    artist.setName(rs.getString("name"));
                    artist.setType(rs.getString("type"));
                    artist.setCountry(rs.getString("country"));
                    //artist.setAliases(rs.getString("aliases").);
                    //artist.setTags(rs.);
                    artist.setCities(rs.getString("cities"));
                    artist.setBeginDate(rs.getDate("begin_date"));
                    artist.setEndDate(rs.getDate("end_date"));
                    artist.setIDA(rs.getString("id"));
                    resultsArray.add(artist);
                }
            }
            
            rs.close();
        } catch (Exception e) {
            System.out.println("Couldn't display results");
            //System.err.println(e);
        }
        
        return resultsArray;
    }

}
