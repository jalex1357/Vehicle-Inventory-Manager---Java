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
    /*public String[] getEmployees(String dept){
        
    }*/
    public void fireEmployee(String dept, String empName) throws SQLException{
        //Require username and password for termination verification
        String username = JOptionPane.showInputDialog("Enter Username");
        String password = JOptionPane.showInputDialog("Enter Password");
        
        loginVerify authenticate = new loginVerify();
        
        Boolean isAuthenticated = authenticate.check(username, password);
        
        String statusLevel = authenticate.getStatusLevel(username, password);
        
        myRs = myStmt.executeQuery("select employeeId from users where firstName + ' ' + lastName = '"+empName+"' and dept = '"+dept+"'");
        int empId = 0;
        while(myRs.next()){
            empId = myRs.getInt("employeeId");
        }
        

        
        if(isAuthenticated && (statusLevel.equals("Admin") || statusLevel.equals("Manager (Level 2)") || statusLevel.equals("Manager (Level 3)"))){
            int update = myStmt.executeUpdate("update users set accountStatus = 'Terminated' where employeeId = "+empId);
        }
        
        JOptionPane.showMessageDialog(null, empName + " has been terminated.");
        
        
        
        
    }
    public ArrayList<String> getEmps(String dept)throws SQLException{
        ArrayList<String> employees = new ArrayList<>();
        myRs = myStmt.executeQuery("select firstName, lastName from users where dept = '"+dept+"' and employeeStatus = 'Normal' order by firstName");
        while(myRs.next()){
            if(!myRs.getString("firstName").equals("Test"))
                employees.add(myRs.getString("firstName") + " "+ myRs.getString("lastName"));
        }
        return employees;
    }
    
    public void promoteEmployee(){
        
    }
    public Boolean newDepartment(String[] depts, String newDepartment)throws SQLException{
        String dept = "";
        
        String formatNewDepartment = "";
        
        //This next for loop used to capitalize first letter of user input
        char[] newDeptSplit = newDepartment.toCharArray();
        for(int count = 0; count<newDeptSplit.length; count++){
            if(count == 0){
                formatNewDepartment += Character.toString(newDeptSplit[0]).toUpperCase();
            }
            else{
                formatNewDepartment += newDeptSplit[count];
            }
        }
        
        
        for(int count = 0; count<depts.length; count++){
            dept = depts[count];
            if(dept.equals(formatNewDepartment)){
                return false;
            }
        }
        int isAdded = myStmt.executeUpdate("insert into dept(Department) values('"+formatNewDepartment+"')");
        return true;
    }
}