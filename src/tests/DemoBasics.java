/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import basics.*;

import db.Database;
import static files.APIWrapper.readJsonFromUrl;
import gui.AlertNotification;
import gui.FirstPages;
import gui.GUI;
import gui.GUI_USER;
import java.awt.Dimension;
import java.awt.Toolkit;

//import static basics.JsonReader.readJsonFromUrl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import org.json.JSONException;
import org.json.JSONObject;

public class DemoBasics {
    public static void main(String[] args) throws IOException, JSONException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        

    
    
    if(!Database.Connect()){                                //Load alert window
       AlertNotification newAlert = new AlertNotification();
       newAlert.setSize(600,400);
       newAlert.setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int)screenSize.getWidth()/2;
                int y = (int)screenSize.getHeight()/ 2;  
                x -= 300;
                y -= 200;
       newAlert.setLocation(x, y);
       newAlert.setVisible(true);
       
       
    }else{     
        
                            //Load login page
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't load Nimbus");
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    FirstPages newGUI = new FirstPages();
    newGUI.runFirstPages();
    
    

  }
} 
    
    public static void closeFirstPages(FirstPages tmp){
        tmp.setVisible(false);
    }
}
