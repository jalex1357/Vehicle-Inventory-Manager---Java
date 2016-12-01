package inventorymanager;

import java.sql.*;
import java.util.ArrayList;

public class vehicles{
    private Connection myConn;
    private Statement myStmt;
    private ResultSet myRs;
    public vehicles(){
        myConn = null;
        myStmt = null;
        myRs = null;
    }
    
    public ArrayList<String> getMakes() throws SQLException{
        String make;
        ArrayList<String> makesList = new ArrayList<>();
        
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/possibleVehicles", "student", "student");
        myStmt = myConn.createStatement();
        myRs = myStmt.executeQuery("select * from makes");
        
        while(myRs.next()){
            make = myRs.getString("make");
            makesList.add(make);
        }
        
        return makesList;
    }
    public ArrayList<String> getModels(String make) throws SQLException{
        ArrayList<String> modelsList = new ArrayList<>();
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/possibleVehicles", "student", "student");
        myStmt = myConn.createStatement();
        myRs = myStmt.executeQuery("select * from models where Model like '"+make+":%'");
        
        while(myRs.next()){
            String item = myRs.getString("Model");
            String newItem = item.replace(make+":", "");
            modelsList.add(newItem);
            
        }
        
        
        
        
        return modelsList;
    }
    
}