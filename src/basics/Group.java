/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.util.Date;
import java.util.ArrayList;

public class Group extends Artist {
    private ArrayList<Person> Members; //there is no jsonobject to get the members from!!!
    
    public Group(){
        super("Group");
        this.Members = new ArrayList<Person>();
    }

    public Group(ArrayList<Person> Members, String Name, String Country, String Cities, ArrayList<String> Aliases, ArrayList<String> Tags, String Type, Date BeginDate, Date EndDate, String IDA) {
        super(Name, Country, Cities, Aliases, Tags, Type, BeginDate, EndDate, IDA);
        this.Members = Members;
    }

    public ArrayList<Person> getMembers() {
        return Members;
    }

    public void setMembers(ArrayList<Person> Members) {
        this.Members = Members;
    }

}
