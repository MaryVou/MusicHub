/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basics;

import java.util.ArrayList;
import java.util.Date;

public class Person extends Artist {
    private boolean isMale;

    public Person(boolean isMale, String Name, String Country, String Cities, ArrayList<String> Aliases, ArrayList<String> Tags, String Type, Date BeginDate, Date EndDate, String IDA) {
        super(Name, Country, Cities, Aliases, Tags, "Person", BeginDate, EndDate, IDA);
        this.isMale = isMale;
    }
    
    public Person(boolean isMale){
        super("Person");
        this.isMale = isMale;
    }
    
    public Person(){
        super("Person");
    }
    
    public String getGender() {
        return isMale ? "Male" : "Female";
    }

    public void isMale(boolean isMale) {
        this.isMale = isMale;
    }
    
}
