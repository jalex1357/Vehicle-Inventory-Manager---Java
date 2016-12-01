package inventorymanager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class Employee{
    Connection myConn = null;
    Statement myStmt = null;
    ResultSet myRs = null;
    int num = 0;
    
    public Employee(){
         try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "student", "student");
            myStmt = myConn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(loginVerify.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createEmployee(String username, String fName, String lName, String empRank)throws SQLException{
        int count = 0;
        myRs = myStmt.executeQuery("select * from users");
        while(myRs.next()){
            count++;
        }
        count+=1;
        int newEmployeeId = count;
        String passcode = fName.toLowerCase() + lName.toLowerCase();
        num = myStmt.executeUpdate("INSERT INTO users VALUES ("+newEmployeeId+", '"+username+"', '"+passcode+"', '"+fName+"', '"+lName+"', '"+empRank+"', 'Normal')");
        JOptionPane.showMessageDialog(null, "New Employee Submission Has Been Issued.");
    }
    
    public void fireEmployee(){
        
    }
    public void suspendEmployee(){
        
    }
    public void promoteEmployee(){
        
    }
    
}