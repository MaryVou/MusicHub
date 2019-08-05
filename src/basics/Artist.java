/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.util.ArrayList;
import java.util.Date;

public abstract class Artist {
    /*classes person and group extend artist*/
    private String Name;
    private String Country;
    private String Cities;
    private ArrayList<String> Aliases;
    private ArrayList<String> Tags;
    private String Type;
    private Date BeginDate; //for singers-->birthdate | for groups--> begin date
    private Date EndDate; //for singers--> deathdate | for groups--> end date
    private String IDA;

    public Artist(String Name, String Country, String Cities, ArrayList<String> Aliases, ArrayList<String> Tags, String Type, Date BeginDate, Date EndDate, String IDA) {
        this.Name = Name;
        this.Country = Country;
        this.Cities = Cities;
        this.Aliases = Aliases;
        this.Tags = Tags;
        this.Type = Type;
        this.BeginDate = BeginDate;
        this.EndDate = EndDate;
        this.IDA = IDA;
    }

    public Artist(String Type) {
        this.Type = Type;
    }
   
    public Artist(){
        
    }
    
    
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getCities() {
        return Cities;
    }

    public void setCities(String Cities) {
        this.Cities = Cities;
    }

    public ArrayList<String> getAliases() {
        return Aliases;
    }

    public void setAliases(ArrayList<String> Aliases) {
        this.Aliases = Aliases;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public void setTags(ArrayList<String> Tags) {
        this.Tags = Tags;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getType() {
        return this.Type;
    }   

    public String getIDA() {
        return IDA;
    }

    public void setIDA(String IDA) {
        this.IDA = IDA;
    }

    public Date getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(Date BeginDate) {
        this.BeginDate = BeginDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date EndDate) {
        this.EndDate = EndDate;
    }
    
    
    
}
