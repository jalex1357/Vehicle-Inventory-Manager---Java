
package inventorymanager;


import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InventoryManager extends JFrame{
    //private variables for buildManager//
    private JPanel panel;
    private JComboBox make, model, type;
    private JButton submit;
    private JLabel title, makeLabel, modelLabel, enterVin;
    private JTextField VIN;
    
    //private variables for buildLogin//
    private JLabel username, password;
    private JTextField getUsername;
    private JPasswordField getPassword;
    private JButton loginButton;
    private JPanel loginPanel;
    
    //private variables for addEmployee
    private JLabel newUsername, fname, lname, rank, dept;
    private JTextField getNewUsername, getFName, getLName;
    private JComboBox getRank, getDept;
    private JButton submitNewEmployee;
    
    
    
    public InventoryManager(String username, String password) throws FileNotFoundException, SQLException, IOException{
        
        loginVerify getUserStatusLevel = new loginVerify();
        
        String statusLevel = getUserStatusLevel.getStatusLevel(username, password);
        //once user logs in use this.
        setTitle("Inventory Manager");
        
        setResizable(false);
        
        setSize(600,400);
        
        buildManager();
        
        createMenuBar(statusLevel);
        
        add(panel);
        
        setVisible(true);
    }
    public InventoryManager(String doNothing){
        
       //this is the login screen
        setTitle("Login");
        
        setSize(300,200);
        
        setResizable(false);
        
        buildLogin();
        
        add(loginPanel);
        
        setVisible(true);
        
    }
    {
        setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public String getStatusLevel(){
        
        String status = "Employee";
        return status;
    }
    final public void createMenuBar(String statusLevel){
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        
        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setToolTipText("Exit Application");
        eMenuItem.addActionListener((ActionEvent event) ->{
            System.exit(0);
        });
        
        file.add(eMenuItem);
        menubar.add(file);
        String[] mgmt = {"Manager (Level 1)", "Manager (Level 2)", "Manager (Level 3)", "Admin"};
        JMenu create = new JMenu("Create");
        if(statusLevel.equals(mgmt[0]) || statusLevel.equals(mgmt[1])||statusLevel.equals(mgmt[2])||statusLevel.equals(mgmt[3])){
            //set the ability to add to the make/models
            
            
            JMenuItem newMake = new JMenuItem("New Make");
            newMake.setToolTipText("Create A New Vehicle Make");
            newMake.addActionListener((ActionEvent event) ->{
                String createNewMake = JOptionPane.showInputDialog("Enter New Make");
            });
            create.add(newMake);
            
            JMenuItem newModel = new JMenuItem("New Model");
            newModel.setToolTipText("Create A New Vehicle Model");
            newModel.addActionListener((ActionEvent event) -> {
                //create model
                //Create a JFrame that has a JComboBox
                //that gets all the current makes from db
                //Allow user to select one and then enabled a
                //jtextfield to allow the user to enter a new model
                //then update db
            });
            create.add(newModel);
            
            menubar.add(create);
        }
        if(statusLevel.equals("Admin")){
            //set ability for creation of departments. Admins only!!!
            JMenuItem newDepartment = new JMenuItem("New Department");
            newDepartment.setToolTipText("Create A New Department");
            newDepartment.addActionListener((ActionEvent event) ->{
                String createNewDepartment = JOptionPane.showInputDialog("Enter New Department");
            });
            create.add(newDepartment);
            menubar.add(create);
        }
        if(statusLevel.equals("Admin") || statusLevel.equals("Manager (Level 2") || statusLevel.equals("Manager (Level 3)")){
            JMenu personnel = new JMenu("Personnel");
            
            JMenuItem newEmployee = new JMenuItem("Add New Employee");
            personnel.add(newEmployee);
            menubar.add(personnel);
            newEmployee.setToolTipText("Create A New Employee");
            newEmployee.addActionListener((ActionEvent event) ->{
                //Employee createNewEmployee = new Employee();
                
                JFrame getNewEmpInfo = new JFrame();
                getNewEmpInfo.setLayout(null);
                getNewEmpInfo.setPreferredSize(new Dimension(265,220));
                getNewEmpInfo.setLocationRelativeTo(null);
                
                newUsername = new JLabel("Create Username");
                fname = new JLabel("First Name");
                lname = new JLabel("Last Name");
                
                getNewUsername = new JTextField(15);
                getFName = new JTextField(15);
                getLName = new JTextField(15);
                
                String[] ranks = {"New Hire", "Employee (Level 1)","Employee (Level 2)", "Manager (Level 1)","Manager (Level 2)", "Manager (Level 3)", "Admin"};
                getRank = new JComboBox(ranks);
                
                Employee emp = new Employee();
                String[] allDepts = null;
                try {
                    allDepts = emp.getDepts();
                } catch (SQLException ex) {
                    Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                 
                rank = new JLabel("Rank");
                
                dept = new JLabel("Department");
                getDept = new JComboBox(allDepts);
                
                
                
                submitNewEmployee = new JButton("Submit");
                submitNewEmployee.addActionListener(new submitNewEmployeeListener());
                
                newUsername.setBounds(10,1,100,20);
                getNewUsername.setBounds(115,1,135, 20);
                fname.setBounds(10,31, 100, 20);
                getFName.setBounds(115, 31, 135, 20);
                lname.setBounds(10,61, 100, 20);
                getLName.setBounds(115,61, 135, 20);
                rank.setBounds(10, 91, 100, 20);
                getRank.setBounds(115, 91, 135, 20);
                dept.setBounds(10, 121, 100, 20);
                getDept.setBounds(115, 121, 135, 20);
                submitNewEmployee.setBounds(115, 151, 75, 30);
                
                getRank.setSelectedItem("Employee (Level 2)");
                getNewEmpInfo.add(newUsername);
                getNewEmpInfo.add(getNewUsername);
                getNewEmpInfo.add(fname);
                getNewEmpInfo.add(getFName);
                getNewEmpInfo.add(lname);
                getNewEmpInfo.add(getLName);
                getNewEmpInfo.add(rank);
                getNewEmpInfo.add(getRank);
                getNewEmpInfo.add(dept);
                getNewEmpInfo.add(getDept);
                getNewEmpInfo.add(submitNewEmployee);
                
                
                
                getNewEmpInfo.pack();
                
                getNewEmpInfo.setVisible(true);

                getNewEmpInfo.setResizable(false);
                getNewEmpInfo.setTitle("New Employee");
                
                
                
                
                //Create a pop up window that gets all necessary info.
                //upon click createNewEmployee.createEmployee(); with information
            });
            
            //Next management menu command
            JMenuItem empMaintenance = new JMenuItem("Employee Maintenance");
            personnel.add(empMaintenance);
            empMaintenance.setToolTipText("Employee Maintenance");
            empMaintenance.addActionListener((ActionEvent event) ->{
                //Bring up maintenance menu with tabs
                JFrame tab = new JFrame();
                tab.setResizable(false);
                JTabbedPane maintenance = new JTabbedPane();
                
                //maintenance.setTitle("Employee Maintenance");
                getContentPane().add(maintenance);
                
                //create the add new employee tab
                JPanel addEmp = new JPanel();
                ////////////////////////////////////
                addEmp.setLayout(null);
                newUsername = new JLabel("Create Username");
                fname = new JLabel("First Name");
                lname = new JLabel("Last Name");
                
                getNewUsername = new JTextField(15);
                getFName = new JTextField(15);
                getLName = new JTextField(15);
                
                String[] ranks = {"New Hire", "Employee (Level 1)","Employee (Level 2)", "Manager (Level 1)","Manager (Level 2)", "Manager (Level 3)", "Admin"};
                getRank = new JComboBox(ranks);
                
                rank = new JLabel("Rank");
                
                submitNewEmployee = new JButton("Submit");
                submitNewEmployee.addActionListener(new submitNewEmployeeListener());
                
                newUsername.setBounds(40,30,100,20);
                getNewUsername.setBounds(145,30,135, 20);
                fname.setBounds(40,60, 100, 20);
                getFName.setBounds(145, 60, 135, 20);
                lname.setBounds(40,90, 100, 20);
                getLName.setBounds(145,90, 135, 20);
                rank.setBounds(40, 120, 100, 20);
                getRank.setBounds(145, 120, 135, 20);
                submitNewEmployee.setBounds(145, 150, 75, 30);
                
                getRank.setSelectedItem("New Hire");
                
                addEmp.add(newUsername);
                addEmp.add(getNewUsername);
                addEmp.add(fname);
                addEmp.add(getFName);
                addEmp.add(lname);
                addEmp.add(getLName);
                addEmp.add(rank);
                addEmp.add(getRank);
                addEmp.add(submitNewEmployee);
                
                //////////////////////////////////
                //create the terminate/suspend tab
                JPanel fireEmp = new JPanel();
                
                //Create the promotions tab
                JPanel promoteEmp = new JPanel();
                
                //Change usernames here
                JPanel changeAuth = new JPanel();
                
                maintenance.addTab("Add Employee", addEmp);
                maintenance.addTab("Terminate", fireEmp);
                maintenance.addTab("Promotions", promoteEmp);
                maintenance.addTab("Authorizations", changeAuth);
                
                tab.add(maintenance);
                tab.pack();
                tab.setSize(400,400);
                tab.setTitle("Employee Maintenance");
                tab.setLocationRelativeTo(null);
                tab.setVisible(true);
            });
            
        }
        setJMenuBar(menubar);
    }
    final public void buildLogin(){
        loginPanel = new JPanel();
        username = new JLabel("Username");
        password = new JLabel("Password");
        
        getUsername = new JTextField(15);
        getPassword = new JPasswordField(15);
        
        loginButton = new JButton("Login");
        loginButton.addActionListener(new loginListener());
        
        loginPanel.add(username);
        loginPanel.add(getUsername);
        
        loginPanel.add(password);
        loginPanel.add(getPassword);
        
        loginPanel.add(loginButton);
    }
    final public void buildManager() throws FileNotFoundException, SQLException, IOException{
        //make.txt
        
        
        //ArrayList<String> makesList = new ArrayList<>();
        //populate makesList with the information in the db: possibleVehicles
        //tables: makes; models;
        
        vehicles getVehicles = new vehicles();
        ArrayList<String> makesList = getVehicles.getMakes();
        //Allows the first spot in combo box to be blank to look unselected
        makesList.add(0, "");
        //String[] makes = {"honda", "Hyandai", "Nissan"};
        panel = new JPanel();
        panel.setLayout(null);
        
        Font titleFont = new Font("Arial", Font.BOLD, 28);
        
        //Set details of title
        title = new JLabel("Alexander's Vehicle Inventory");
        title.setBounds(75, 25, 400, 100);
        title.setFont(titleFont);
        panel.add(title);
        
        JLabel addSection = new JLabel("Add Vehicle");
        
        Font sectionTitleFont = new Font("Arial", Font.BOLD, 16);
        
        addSection.setBounds(115,105,100, 30);
        addSection.setFont(sectionTitleFont);
        
        panel.add(addSection);
        
        
        String[] makeList = new String[makesList.size()];
        for(int count = 0; count<makesList.size(); count++){
            makeList[count] = makesList.get(count);
        }
        make = new JComboBox(makeList);
        
        makeLabel = new JLabel("Make: ");
        makeLabel.setBounds(75, 145, 100, 30);
        panel.add(makeLabel);
        
        make.setBounds(135, 145, 130, 30);
        panel.add(make);
        
        
        modelLabel = new JLabel("Model: ");
        modelLabel.setBounds(75, 185, 100, 30);
        panel.add(modelLabel);
        
        //Create the vin text field
        enterVin = new JLabel("Enter VIN: ");
        enterVin.setBounds(75, 220, 100, 30);
        panel.add(enterVin);
        
        VIN = new JTextField(30);
        VIN.setEnabled(false);
        VIN.setEditable(false);
        VIN.setBounds(135, 220, 130, 30);
        panel.add(VIN);
        
        
        submit = new JButton("Save Item");
        submit.addActionListener(new saveItemListener());
        
        submit.setBounds(150, 255, 90, 30);
        panel.add(submit);
        
        
        make.addActionListener ((ActionEvent e) -> {
            ArrayList<String> modelsList = null;
            String makeChoice = (String)make.getSelectedItem();
            if(!makeChoice.equals("")){
                try {
                    modelsList = getVehicles.getModels(makeChoice);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                String[] modelList = new String[modelsList.size()];
            
                for(int count = 0; count<makesList.size()-1; count++){
                    modelList[count] = modelsList.get(count);
                }

                model = new JComboBox(modelList);

                model.setBounds(135, 185, 130, 30);
                panel.add(model);
                panel.revalidate();

                model.addActionListener((ActionEvent f) -> {
                    String modelChoice = (String)model.getSelectedItem();
                    if(!modelChoice.equals("")){
                        VIN.setEnabled(true);
                        VIN.setEditable(true);
                    }
                });
            }
            else{
                if(VIN.isEnabled()){
                    VIN.setEnabled(false);
                    VIN.setEditable(false);
                    panel.remove(model);
                }
            }
        });
        
        //create the right side of main screen after login
        JLabel sectionLabelSearch = new JLabel("Search VIN");
        sectionLabelSearch.setFont(sectionTitleFont);
        JLabel vinLabel = new JLabel("Enter VIN:");
        JTextField getVIN = new JTextField(15);
        JButton searchVin = new JButton("Search");
        searchVin.addActionListener(new searchVinActionListener());
        
        sectionLabelSearch.setBounds(350, 105, 100, 30);
        vinLabel.setBounds(300, 145, 100,30);
        getVIN.setBounds(370, 145, 100, 30);
        searchVin.setBounds(370,255,90,30);
        
        panel.add(sectionLabelSearch);
        panel.add(vinLabel);
        panel.add(getVIN);
        panel.add(searchVin);
        
    }
    
    public static void main(String[] args) {
        //login first
        //then based on access modifier in db
        //allow certain actions or disallow
        InventoryManager login = new InventoryManager("login");
    }
    private class searchVinActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //Search VIN for vehicle information
        }
    }
    private class submitNewEmployeeListener implements ActionListener{        
        public void actionPerformed(ActionEvent e){
           
            Employee employee = new Employee();
            
            String username = getNewUsername.getText();
            String fName = getFName.getText();
            String lName = getLName.getText();
            String empRank = getRank.getSelectedItem().toString();
            String dept = (String) getDept.getSelectedItem();
            
            
            try {
                employee.createEmployee(username, fName, lName, empRank, dept);
            } catch (SQLException ex) {
                Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            getNewUsername.setText("");
            getFName.setText("");
            getLName.setText("");
            getRank.setSelectedItem("New Hire");
        }
    }
    private class saveItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
        }
    }
    private class loginListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //Perform login stuff here
            
            loginVerify verify = null;
            try {
                verify = new loginVerify(getUsername.getText());
            } catch (SQLException ex) {
                Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //validate for attempted sql statement
            
            Boolean isVerified = verify.check(getUsername.getText(), getPassword.getText());
            
            if(isVerified == false){
                JOptionPane.showMessageDialog(null, "Invalid Username or Password.");
            }
            else{
                
                try {
                    //Forcing user to create a unique password based on whether user has a default password in db
                    String defaultStatus = verify.isPasswordDefault(getUsername.getText().toLowerCase(), getPassword.getText().toLowerCase());
                    String oldPassword = getUsername.getText()+getPassword.getText();
                    if(defaultStatus.equals("isDefault")){
                        String newPassword = JOptionPane.showInputDialog("Input a new unique password.");
                        String newPasswordRepeat = JOptionPane.showInputDialog("Re-Enter your new unique password.");
                        //FIX REQUIRED:  AT LEAST ONE LOOP IS NOT ACTIVATING: CHECK BOTH
                        while(!newPassword.equals(newPasswordRepeat)){
                            newPassword = JOptionPane.showInputDialog("New password entries did not match.\nInput a new unique password.");
                            newPasswordRepeat = JOptionPane.showInputDialog("Re-Enter your new unique password.");
                        }
                        while(newPassword.toLowerCase().equals(oldPassword.toLowerCase())){
                            newPassword = JOptionPane.showInputDialog("You must enter a NEW UNIQUE password");
                            newPasswordRepeat = JOptionPane.showInputDialog("Re-Enter your NEW UNIQUE password");
                            if(newPassword.equals(newPasswordRepeat) && !newPassword.equals(oldPassword)){
                                break;
                            }
                        }
                        verify.setNewPassword(getUsername.getText(), oldPassword, newPassword);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                setVisible(false);
                try { 
                    InventoryManager start = new InventoryManager(getUsername.getText().toLowerCase(), getPassword.getText().toLowerCase());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(InventoryManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
}
