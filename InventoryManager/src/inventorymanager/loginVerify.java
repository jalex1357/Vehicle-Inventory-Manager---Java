//create constructor with initial values
package inventorymanager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class loginVerify{
    private int loginCount = 0;
    String verifyPassword = null;
    Connection myConn = null;
    Statement myStmt = null;
    ResultSet myRs = null;
    {
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "student", "student");
            myStmt = myConn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(loginVerify.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public loginVerify(){
        myRs=null;
    }
    public loginVerify(String username)throws SQLException{
        
        
        myRs = myStmt.executeQuery("select accountStatus from users where username = '"+username+"'");
        while(myRs.next()){
            if(myRs.getString("accountStatus").equals("Locked")){
                validate("select1");
            }
        }
        loginCount++;
        if(loginCount >= 5){//If user makes a fifth attempt, lock account
            myRs = myStmt.executeQuery("update users set accountStatus = 'Locked' where username = '"+username+"'");
        }
    }
    public Boolean validate(String username){
        if(username.equals("select1")){
            JOptionPane.showMessageDialog(null, "Your Account Has Been Locked\nDue To Too Many Login Attempts");
            System.exit(0);
            return false;
        }
        else if(username.toLowerCase().contains("select")){
            return false;
        }
        return true;
    
    }
    public String getStatusLevel(String username, String password)throws SQLException{
        String status = null;
        myRs = myStmt.executeQuery("select statusLevel from users where username = '"+username+"' and passcode = '"+password+"'");
        while(myRs.next()){
            status = myRs.getString("statusLevel");
        }
        
        return status;
    }
    public Boolean check(String username, String password){
        Boolean doesNotContainSQL = validate(username);
        Boolean isVerified;
        
        if(doesNotContainSQL){
            isVerified = checkAgainstDB(username, password);
        }
        else{
            isVerified = false;
        }
        if(isVerified){
            return true;
        }
        return false;
        
    }
    private Boolean checkAgainstDB(String username, String password){
        
        
        try{
          myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "student", "student");
          myStmt = myConn.createStatement();
          
          myRs = myStmt.executeQuery("select passcode from users where username = '"+username+"'");
          while(myRs.next()){
              verifyPassword = myRs.getString("passcode");
          }
          
          
        }
        catch (Exception exc){
            System.out.println("catch");
            System.out.println(exc);
        }
        if(password.equals(verifyPassword)){
            return true;
        }
        return false;
    }
}