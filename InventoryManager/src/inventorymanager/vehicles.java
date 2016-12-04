package inventorymanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class vehicles{
    private Connection myConn;
    private Statement myStmt;
    private ResultSet myRs;
    public vehicles() throws SQLException{
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/possibleVehicles", "student", "student");
        myStmt = myConn.createStatement();
        myRs = null;
    }
    
    public ArrayList<String> getMakes() throws SQLException{
        String make;
        ArrayList<String> makesList = new ArrayList<>();
        
        myRs = myStmt.executeQuery("select * from makes order by Make");
        
        while(myRs.next()){
            make = myRs.getString("make");
            makesList.add(make);
        }
        
        return makesList;
    }
    public ArrayList<String> getModels(String make) throws SQLException{
        ArrayList<String> modelsList = new ArrayList<>();
        
        myRs = myStmt.executeQuery("select * from models where Model like '"+make+":%'");
        
        while(myRs.next()){
            String item = myRs.getString("Model");
            if(item.equals(""))
                break;
            String newItem = item.replace(make+":", "");
            modelsList.add(newItem);
            
        }
        
        
        Collections.sort(modelsList);
        
        return modelsList;
    }
    public Boolean checkMake(String getMake)throws SQLException{
        String preformat = getMake.toLowerCase();
        String formatMake = preformat.substring(0, 1).toUpperCase() + preformat.substring(1);
        
        myRs = myStmt.executeQuery("select * from makes");
        while(myRs.next()){
            if(myRs.getString("Make").equals(formatMake)){
                return true;
            }
        }
        return false;
    }
    public void createNewMake(String newMake)throws SQLException{
        
        
        int update = myStmt.executeUpdate("insert into makes values('"+newMake+"')");
        JOptionPane.showMessageDialog(null, "New Make Has Been Added");
    }
    public void createNewModel(String newModel){
        
    }
    
}