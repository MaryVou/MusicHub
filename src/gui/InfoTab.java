/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import db.Database;
import static gui.GUI_USER.st;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author manoslysi
 */
public class InfoTab extends javax.swing.JFrame {
    private boolean isRelease;
    private String ID;
    static Connection conn= Database.getConn();
    static Statement st;
    static ResultSet rs;
    /**
     * Creates new form InfoTab
     */
    public InfoTab() {
        initComponents();
    }
    
    public void setIsRelease(boolean tmp){
        isRelease = tmp;
    }
    
    public void setID(String ID){
        this.ID = ID;
    }
            
    public boolean connect(){
        String query;
        String favoriteExists="0";
        try{
            st=conn.createStatement();
            st.executeQuery("commit");
        }catch(Exception E){
            System.out.println("Couldn't Commit11");
        }
        
        
        try{
           String username = FirstPages.getUsername();
           st = conn.createStatement();
           if(isRelease){
                query = "select count(*) from likedreleases where userid=\'" + FirstPages.getUsername() + "\' AND releaseid='" + ID +"\'";
                
           }else{
                query = "select count(*) from likedartists where userid=\'" + FirstPages.getUsername() + "\' AND artistid='" + ID +"\'";
           }
           //System.out.println(query);
           rs = st.executeQuery(query);
           while(rs.next()){
              favoriteExists =rs.getString("COUNT(*)");
           }
           rs.close();
          // System.out.println("Favorite: "+favoriteExists);
           if(favoriteExists.equals("1")){
               favoriteBtn.setText("Remove from favorites");
           }else{
               favoriteBtn.setText("Add to favorites");
           }
           
           
        }catch(Exception E){
        
        }   
        
        
        
        
        if(isRelease){
            
                label1.setText("Release");
                label2.setText("Artist");
                label3.setText("Type");
                label4.setText("Status");
                label5.setText("Format");
                label6.setText("Language");
                label7.setText("Release Date");
                label8.setText("TrackCount");
                label9.setText("ID");
                
                try{
                    st = conn.createStatement();
                    String commit = "commmit";
                    st.executeQuery(commit);
                }catch(Exception E){
                    System.out.print(" ");
                    
                }
                
                try{
                                    st = conn.createStatement();
                                    StringBuilder select =  new StringBuilder("select * from releases where id=\'");
                                    select.append(ID);
                                    select.append("\'");
                                    String select1 = select.toString();
                                    //System.out.println(select1);
                                    rs = st.executeQuery(select1);
                                    while(rs.next()){
                                     
                                     infoLabel1.setText(rs.getString("TITLE"));
                                     infoLabel2.setText(rs.getString("ARTIST"));
                                     infoLabel3.setText(rs.getString("TYPE"));
                                     infoLabel4.setText(rs.getString("STATUS"));
                                     infoLabel5.setText(rs.getString("FORMAT"));
                                     infoLabel6.setText(rs.getString("LANGUAGE"));
                                     infoLabel7.setText(rs.getString("RELEASE_DATE"));
                                     infoLabel8.setText(rs.getString("TRACKCOUNT"));
                                     infoLabel9.setText(rs.getString("ID"));
                                    }
                                    rs.close();
                    return true;
                }catch(Exception e){
                    System.out.println("Couldn't open MusicHub Info tab");
                    return false;
                }
        }else{
                label1.setText("Artist");
                label2.setText("Type");
                label3.setText("Country");
                label4.setText("Cities");
                label5.setText("Begin Date");
                label6.setText("End Date");
                label7.setText("Aliases");
                label8.setText("Tags");
                label9.setText("ID");
                try{
                    st = conn.createStatement();
                    String commit = "commmit";
                    st.executeQuery(commit);
                }catch(Exception E){
                    System.out.print(" ");
                    
                }
                
                try{
                                    st = conn.createStatement();
                                    StringBuilder select =  new StringBuilder("select * from artists1 where id=\'");
                                    select.append(ID);
                                    select.append("\'");
                                    String select1 = select.toString();
                                    //System.out.println(select1);
                                    rs = st.executeQuery(select1);
                                    while(rs.next()){
                                     
                                     infoLabel1.setText(rs.getString("NAME"));
                                     infoLabel2.setText(rs.getString("TYPE"));
                                     infoLabel3.setText(rs.getString("COUNTRY"));
                                     infoLabel4.setText(rs.getString("CITIES"));
                                     infoLabel5.setText(rs.getString("BEGIN_DATE"));
                                     infoLabel6.setText(rs.getString("END_DATE"));
                                     infoLabel7.setText(rs.getString("ALIASES"));
                                     infoLabel8.setText(rs.getString("TAGS"));
                                     infoLabel9.setText(rs.getString("ID"));
                                    }
                                    rs.close();
                    return true;
                }catch(Exception e){
                    System.out.println("Couldn't open MusicHub Info tab");
                    return false;
                }
        
        
        
        
        }
        
        
        
        
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        label4 = new javax.swing.JLabel();
        label5 = new javax.swing.JLabel();
        label6 = new javax.swing.JLabel();
        label7 = new javax.swing.JLabel();
        label8 = new javax.swing.JLabel();
        label9 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        favoriteBtn = new javax.swing.JButton();
        infoLabel1 = new javax.swing.JLabel();
        infoLabel2 = new javax.swing.JLabel();
        infoLabel3 = new javax.swing.JLabel();
        infoLabel4 = new javax.swing.JLabel();
        infoLabel5 = new javax.swing.JLabel();
        infoLabel6 = new javax.swing.JLabel();
        infoLabel7 = new javax.swing.JLabel();
        infoLabel8 = new javax.swing.JLabel();
        infoLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MusicHub Info");
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("FreeSans", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MusicHub Info");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 12, 430, 58);

        label1.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label1.setForeground(new java.awt.Color(153, 153, 255));
        label1.setText("Title:");
        getContentPane().add(label1);
        label1.setBounds(12, 91, 109, 28);

        label3.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label3.setForeground(new java.awt.Color(153, 153, 255));
        label3.setText("Type:");
        getContentPane().add(label3);
        label3.setBounds(12, 181, 109, 29);

        label4.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label4.setForeground(new java.awt.Color(153, 153, 255));
        label4.setText("Status:");
        getContentPane().add(label4);
        label4.setBounds(12, 228, 109, 25);

        label5.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label5.setForeground(new java.awt.Color(153, 153, 255));
        label5.setText("Format");
        getContentPane().add(label5);
        label5.setBounds(12, 271, 109, 24);

        label6.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label6.setForeground(new java.awt.Color(153, 153, 255));
        label6.setText("Language");
        getContentPane().add(label6);
        label6.setBounds(12, 313, 109, 26);

        label7.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label7.setForeground(new java.awt.Color(153, 153, 255));
        label7.setText("Release Date:");
        getContentPane().add(label7);
        label7.setBounds(12, 357, 109, 25);

        label8.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label8.setForeground(new java.awt.Color(153, 153, 255));
        label8.setText("TrackCount:");
        getContentPane().add(label8);
        label8.setBounds(12, 400, 109, 26);

        label9.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label9.setForeground(new java.awt.Color(153, 153, 255));
        label9.setText("ID");
        getContentPane().add(label9);
        label9.setBounds(12, 444, 109, 25);

        label2.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        label2.setForeground(new java.awt.Color(153, 153, 255));
        label2.setText("Artist");
        getContentPane().add(label2);
        label2.setBounds(12, 137, 109, 26);

        jButton1.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(12, 515, 90, 30);

        favoriteBtn.setFont(new java.awt.Font("FreeSans", 1, 14)); // NOI18N
        favoriteBtn.setText("Add to Favorites");
        favoriteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoriteBtnActionPerformed(evt);
            }
        });
        getContentPane().add(favoriteBtn);
        favoriteBtn.setBounds(218, 515, 200, 30);

        infoLabel1.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel1.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel1);
        infoLabel1.setBounds(127, 91, 291, 28);

        infoLabel2.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel2.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel2);
        infoLabel2.setBounds(127, 137, 291, 26);

        infoLabel3.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel3.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel3);
        infoLabel3.setBounds(127, 181, 291, 30);

        infoLabel4.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel4.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel4);
        infoLabel4.setBounds(127, 228, 291, 25);

        infoLabel5.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel5.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel5);
        infoLabel5.setBounds(127, 271, 291, 24);

        infoLabel6.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel6.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel6);
        infoLabel6.setBounds(127, 313, 291, 26);

        infoLabel7.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel7.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel7);
        infoLabel7.setBounds(127, 357, 291, 25);

        infoLabel8.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel8.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel8);
        infoLabel8.setBounds(127, 400, 291, 26);

        infoLabel9.setFont(new java.awt.Font("FreeSans", 1, 12)); // NOI18N
        infoLabel9.setForeground(new java.awt.Color(153, 153, 255));
        infoLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(infoLabel9);
        infoLabel9.setBounds(127, 444, 291, 25);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/texture-990104_1920.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 430, 560);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       GUI_USER.hideInfo(this);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void favoriteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoriteBtnActionPerformed
       if(favoriteBtn.getText().equals("Add to favorites")){
           if(label1.getText().equals("Release")){
               try{
                    st = conn.createStatement();
                    st.executeQuery("INSERT INTO LIKEDRELEASES VALUES(\'"+FirstPages.getUsername()+"\',\'" + infoLabel9.getText() + "\')");
               }catch(Exception E){
                   System.out.println("Couldn't add to favorites");
               }     
           }else if(label1.getText().equals("Artist")){
               try{
                    st = conn.createStatement();
                    st.executeQuery("INSERT INTO LIKEDARTISTS VALUES(\'"+FirstPages.getUsername()+"\',\'" + infoLabel9.getText() + "\')");
               }catch(Exception E){
                   System.out.println("Couldn't add to favorites");
               }  
           }
           favoriteBtn.setText("Remove from favorites");
           
           
       }else if(favoriteBtn.getText().equals("Remove from favorites")){
           if(label1.getText().equals("Release")){
               try{
                    st = conn.createStatement();
                    st.executeQuery("DELETE FROM LIKEDRELEASES WHERE userid=\'"+FirstPages.getUsername()+"\' AND releaseid=\'" + infoLabel9.getText() + "\'");
                    //System.out.println("DELETE FROM LIKEDRELEASES WHERE userid=\'"+FirstPages.getUsername()+"\' AND releaseid=\'" + infoLabel9.getText() + "\'");
               }catch(Exception E){
                   System.out.println("Couldn't add to favorites");
               }     
           }else if(label1.getText().equals("Artist")){
               try{
                    st = conn.createStatement();
                    st.executeQuery("DELETE FROM LIKEDARTISTS WHERE userid=\'"+FirstPages.getUsername()+"\'AND artistid=\'" + infoLabel9.getText() + "\'");
                   // System.out.println("DELETE FROM LIKEDARTISTS WHERE userid=\'"+FirstPages.getUsername()+"\'AND artistid=\'" + infoLabel9.getText() + "\'");
               }catch(Exception E){
                   System.out.println("Couldn't add to favorites");
               } 
               //System.out.println("DELETED " + infoLabel9.getText());
           }
           favoriteBtn.setText("Add to favorites");
       
       
       }
       GUI_USER.hideInfo(this);
    }//GEN-LAST:event_favoriteBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InfoTab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InfoTab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InfoTab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InfoTab.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InfoTab().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton favoriteBtn;
    private javax.swing.JLabel infoLabel1;
    private javax.swing.JLabel infoLabel2;
    private javax.swing.JLabel infoLabel3;
    private javax.swing.JLabel infoLabel4;
    private javax.swing.JLabel infoLabel5;
    private javax.swing.JLabel infoLabel6;
    private javax.swing.JLabel infoLabel7;
    private javax.swing.JLabel infoLabel8;
    private javax.swing.JLabel infoLabel9;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    private javax.swing.JLabel label4;
    private javax.swing.JLabel label5;
    private javax.swing.JLabel label6;
    private javax.swing.JLabel label7;
    private javax.swing.JLabel label8;
    private javax.swing.JLabel label9;
    // End of variables declaration//GEN-END:variables
}
