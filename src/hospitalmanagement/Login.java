package hospitalmanagement;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/*
* Class that displays the home page for users to log into their accounts or register for a new account
* Currently is the main class used to launch the program
*/
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JTextField password;

	/*
	* Launch the application.
	*/
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();	// create the login frame
					frame.setVisible(true);		// make the frame visible
				} catch (Exception e) {
					e.printStackTrace();		// if there's an issue, print the error
				}
			}
		});
	}

	/*
	* Create the frame.
	*/
	public Login() {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1000, 600, 1000, 600);
		setLocationRelativeTo(null);
		
		// create the panel for the frame
		contentPane = new JPanel();
		// set properties of the pane
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add title to the pane
		JLabel lblNewLabel_2 = new JLabel("Hospital Management System");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblNewLabel_2.setBounds(120, 28, 834, 66);
		contentPane.add(lblNewLabel_2);
		
		// add username label to the pane
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(177, 219, 211, 36);
		contentPane.add(lblNewLabel);

		// add password label to the pane
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel_1.setBounds(147, 310, 211, 36);
		contentPane.add(lblNewLabel_1);
		
		// add textfield for the user to enter their username
		username = new JTextField();
		username.setBounds(363, 219, 236, 39);
		contentPane.add(username);
		username.setColumns(10);

		// add textfield for the user to enter their password
		password = new JTextField();
		password.setBounds(363, 310, 236, 39);
		contentPane.add(password);
		password.setColumns(10);

		// Create a dropdown for users to select their account type
		JComboBox<String> comboBox = new JComboBox<String>();

		// add items (account types) to the combo box
		comboBox.addItem("Administrator");
		comboBox.addItem("Assistant");
		comboBox.addItem("Doctor");
		comboBox.addItem("Nurse");
		comboBox.addItem("Patient");

		// set bounds of the combobox and add it to the pane
		comboBox.setBounds(400, 150, 155, 39);
		contentPane.add(comboBox);

		// create the login button for the pane
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {	// add an action listener to the button
			/**
			 * if the user clicks the login button, error check their inputs
			 */
			public void actionPerformed(ActionEvent arg0) {
				
				String email = username.getText();							// grab their username input from the username textfield
				String pass = password.getText();							// grab their password input from the password textfield
				String userType = comboBox.getSelectedItem().toString();	// grab their account type from the user type combo box
				
				if (email.isEmpty() || pass.isEmpty() || !email.contains("@")) {	// check if email and password fields are empty, or proper email wasn't given
					
					Login lframe = new Login();
					JOptionPane.showMessageDialog(lframe, "Invalid email or password inputs.");	// if empty or improper email, display error message
					
				} else if (!validLoginCredentials(email, pass, userType)) {	// check JSON to verify user's login credentials
					
					Login lframe1 = new Login();		// if the credentials are not in the database, show an error message
					JOptionPane.showMessageDialog(lframe1, "The login credentials you provided are not in our system.");
					
				} else {	// if the credentials are valid, log the user into their account type view
					
				    if (userType.equals("Administrator")) {
				    	AdminPerspective adminPane = new AdminPerspective(email);
				    	adminPane.setVisible(true);
				    } else if (userType.equals("Assistant")) {
				    	AssistantPerspective assistantPane = new AssistantPerspective(email);
				    	assistantPane.setVisible(true);
				    } else if (userType.equals("Doctor")) {
				    	DoctorPerspective docPane = new DoctorPerspective(email);
				    	docPane.setVisible(true);
				    } else if (userType.equals("Nurse")) {
				    	NursePerspective nursePane = new NursePerspective(email);
				    	nursePane.setVisible(true);
				    } else if (userType.equals("Patient")) {
				    	PatientPerspective patientPane = new PatientPerspective(email);
				    	patientPane.setVisible(true);
				    }
					dispose();	// dispose of the log in pane
				}
			}
		});
		btnNewButton.setBounds(307, 388, 171, 41);	// set bounds of the login button
		contentPane.add(btnNewButton);		// add the login button to the pane

		// add the register button to the pane
		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			/**
			 * if the user presses the register button,
			 * show the register pane.
			 */
			public void mouseReleased(MouseEvent e) {
				Register registerPage = new Register();
				registerPage.setVisible(true);
				contentPane.revalidate();
			}
		});
		btnNewButton_1.setBounds(516, 388, 171, 41);
		contentPane.add(btnNewButton_1);
	}
	
	/**
	 * Method checks that the entered login credentials are a valid (username, password) pair in our database.
	 * @param user: the username input
	 * @param pass: the password input
	 * @param userType: the account type input
	 * @return boolean: true if the login credentials are valid, otherwise false.
	 */
	private boolean validLoginCredentials(String user, String pass, String userType) {
		boolean isValid = false;
		try {
		    // create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

		    // create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the account type from the accounts array
		    // then get the array representation of that account type
		    // then create an iterator to iterate through that array
		    Iterator i;
		    
		    if (userType.equals("Administrator")) {
		    	JsonObject administrators = (JsonObject) accounts.get(4);
		    	JsonArray adminArr = (JsonArray) administrators.get("administrator");
		    	i = adminArr.iterator();
		    } else if (userType.equals("Assistant")) {
		    	JsonObject assistants = (JsonObject) accounts.get(3);
		    	JsonArray assistantArr = (JsonArray) assistants.get("assistant");
		    	i = assistantArr.iterator();
		    } else if (userType.equals("Doctor")) {
		    	JsonObject doctors = (JsonObject) accounts.get(1);
		    	JsonArray doctorArr = (JsonArray) doctors.get("doctor");
		    	i = doctorArr.iterator();
		    } else if (userType.equals("Nurse")) {
		    	JsonObject nurses = (JsonObject) accounts.get(2);
		    	JsonArray nurseArr = (JsonArray) nurses.get("nurse");
		    	i = nurseArr.iterator();
		    } else {
		    	JsonObject patients = (JsonObject) accounts.get(0);
		    	JsonArray patientArr = (JsonArray) patients.get("patient");
		    	i = patientArr.iterator();
		    }
		    
		    // iterate through all pairs of usernames and passwords in the user type array
		    while (i.hasNext()) {
		    	
		        JsonObject account = (JsonObject) i.next();
		        String username = (String) account.get("email");
		        String password = (String) account.get("password");
		        
		        // if the login credentials match a pair in the database, the input is valid!
		        if(user.equals(username) && pass.equals(password)) {
		        	isValid = true;
		        }
		    }
		    
		    //close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		
		return isValid;
	}
}
