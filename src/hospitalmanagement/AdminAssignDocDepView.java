package hospitalmanagement;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.awt.Color;

/**
* Class that displays the panel for the adminstrator to assign a department to a doctor
* @author sydneykwok, epaslawski 
*/
public class AdminAssignDocDepView extends JFrame {

	private JPanel contentPane;

	/**
	* Create the frame for the panel to assign departments to doctors
	* @param email The email of the admin. Used to uniquely identify the user so we can easily access their info.
	*/
	public AdminAssignDocDepView(String email) {
		
		// set properties of the frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 590, 444);
		
		// create a new panel
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// add the main title label to the panel
		JLabel MainLabel = new JLabel("Assign a Doctor to a Department");
		MainLabel.setBounds(189, 73, 203, 14);
		contentPane.add(MainLabel);
		
		// add the department label to the panel
		JLabel DepartmentLabel = new JLabel("Department:");
		DepartmentLabel.setBounds(173, 203, 75, 14);
		contentPane.add(DepartmentLabel);
		
		// create a drop down for admin to select a department
		JComboBox<String> departmentDropdown = new JComboBox<String>();
		departmentDropdown.addItem("Cardiology");
		departmentDropdown.addItem("Nephrology");
		departmentDropdown.addItem("Neurology");
		departmentDropdown.setBounds(292, 199, 141, 22);
		contentPane.add(departmentDropdown);
		
		// add the doctor label to the panel
		JLabel DoctorNameLabel = new JLabel("Doctor:");
		DoctorNameLabel.setBounds(173, 138, 75, 14);
		contentPane.add(DoctorNameLabel);
		
		// create a drop down for admin to select a doctor
		JComboBox<String> docDropdown = new JComboBox<String>();
		// go into the account JSON and display all doctors as options here
		ArrayList<String> docUsername = new ArrayList<String>();
		try {
			
		    // create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
		    // create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    // extract the object representation of doctor from the accounts array
		    JsonObject doctors = (JsonObject) accounts.get(1);
		    // then get the array representation of doctor 
	    	JsonArray doctorArr = (JsonArray) doctors.get("doctor");
	    	
	    	// then create an iterator to iterate through that array
	    	Iterator i = doctorArr.iterator();
		    // iterate through all doctors in the doctor array
		    while (i.hasNext()) {
		        JsonObject doctor = (JsonObject) i.next();
		        docDropdown.addItem("Dr. " + (String) doctor.get("last_name"));
		        docUsername.add((String) doctor.get("username"));
		    }
		    
		    //close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		docDropdown.setBounds(292, 134, 141, 22);
		contentPane.add(docDropdown);
		
		// add the "assign" button for the admin to confirm their assignment
		JButton assignButton = new JButton("Assign");
		assignButton.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String selectedDoc = docUsername.get(docDropdown.getSelectedIndex());
				String selectedDep = departmentDropdown.getSelectedItem().toString();
				int pass = WriteToJSON.addDocToDepartment(selectedDoc, selectedDep);
				if (pass == 0) {
				Login lframe = new Login();
				JOptionPane.showMessageDialog(lframe, "Your assignment has been successfully processed!");
				}
				else if (pass == 1) {
					Login lframe = new Login();
					JOptionPane.showMessageDialog(lframe, "This doctor is already assigned!");
				}
				else if (pass == 2) {
					Login lframe = new Login();
					JOptionPane.showMessageDialog(lframe, "The assignment was not successful.");
				}
				}
		});
		assignButton.setBounds(230, 273, 89, 23);
		contentPane.add(assignButton);
	}
}
