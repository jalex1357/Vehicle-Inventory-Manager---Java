package inventorymanager;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class Employee{
    /*private*/Connection myConn = null;
    /*private */Statement myStmt = null;
    /*private*/ ResultSet myRs = null;
    /*private*/int num = 0;
    
    public Employee(){
         try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "student", "student");
            myStmt = myConn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(loginVerify.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createEmployee(String username, String fName, String lName, String empRank, String dept)throws SQLException{
        int count = 0;
        myRs = myStmt.executeQuery("select * from users");
        while(myRs.next()){
            count++;
        }
        count+=1;
        int newEmployeeId = count;
        String passcode = fName.toLowerCase() + lName.toLowerCase();
        num = myStmt.executeUpdate("INSERT INTO users VALUES ("+newEmployeeId+", '"+username+"', '"+passcode+"', '"+fName+"', '"+lName+"', '"+empRank+"', 'Normal', 'Normal', '"+dept+"', 'Default')");
        JOptionPane.showMessageDialog(null, "New Employee Submission Has Been Issued.");
    }
    public String[] getDepts() throws SQLException{
        
        myRs = myStmt.executeQuery("select Department from dept order by Department");
        ArrayList<String> d = new ArrayList<>();
        
        while(myRs.next()){
            d.add(myRs.getString("Department"));
        }
        String[] dept = new String[d.size()];
        
        for(int count = 0; count < d.size(); count++){
            dept[count] = d.get(count);
        }
        
        return dept;
    }
    public String[] getEmployees(String dept){
        
    }
    public void fireEmployee(){
        
    }
    
    public void promoteEmployee(){
        
    }
    public void newDepartment(String newDepartment){
        
    }
    
}