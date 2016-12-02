//create constructor with initial values
package inventorymanager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class loginVerify{
    private String username;
    private String password;
    private int employeeId = 0;
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
        this.username = username;
        this.password = password;
        Boolean doesNotContainSQL = validate(this.username);
        Boolean isVerified;
        
        if(doesNotContainSQL){
            isVerified = checkAgainstDB(this.username, this.password);
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
    public String isPasswordDefault(String username, String password) throws SQLException{
        
        
        myRs = myStmt.executeQuery("select employeeId, isPasswordDefault from users where username = '"+username+"' and passcode = '"+password+"'");
        while(myRs.next()){
            //this is verifying that the users password is not the same as the default password
            //It will force them to change their password to a unique password
            this.employeeId = myRs.getInt("employeeId");
            String defaultStatus = myRs.getString("isPasswordDefault");
            if(defaultStatus.equals("Default")){
                return "isDefault";
            }
            
        }
        return "isUnique";
    }
    public void setNewPassword(String username, String oldPassword, String newPassword) throws SQLException{
        //Set new password
        /*int empId = 0;
        myRs = myStmt.executeQuery("select employeeId from users where username = '"+username+"' and passcode = '"+oldPassword+"'");*/
       /* while(myRs.next()){
            empId = myRs.getInt("employeeId");
        }*/
        int isSet = myStmt.executeUpdate("Update users set passcode = '"+ newPassword+"' where employeeId = "+this.employeeId);
        isSet = myStmt.executeUpdate("Update users set isPasswordDefault = 'Unique' where employeeId = "+this.employeeId);
        JOptionPane.showMessageDialog(null, "Your password has been updated.");
    }
}