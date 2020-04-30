package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;

//libraries necessary to work with out JSON:
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
* Class that displays the panel for the doctor's appointments
* @code author arthurbfranca, ggdizon, sydneykwok
*/
public class DoctorAppointmentView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	//This variable is used to tell which appointments should be displayed when either the previous or next buttons are pressed.
	//eg:
	//the NextButton is pressed for the first time., viewAppointment(counter += 1), viewAppointment(counter += 1)
	//appointments of index 1 and 2 are now displayed
	private int counter = 1;
	//the index of the doctor's account in the "doctor" array in accounts2.json
	private int docIndex = -1;
		
	/**
	* Method returns the JsonObject of the doctor with the given email.
	* @param email The email of the doctor.
	*/
	public JsonObject getDoctor(String email) {
		try {
			//reader to read accounts2.json
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			//parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
		   //we look for the JsonArray within accounts2.json, for that is where the JsonObject of our doctor is
			JsonArray accounts = (JsonArray) parser.get("accounts");
			JsonObject doctorsObj = (JsonObject) accounts.get(1);	//this is the object that has the list of all doctors
			JsonArray doctorsArr = (JsonArray) doctorsObj.get("doctor");
			Iterator i;
			//Now we find the JsonObject for the doctor in particular we are dealing with, whom is identified by the passed email
			i = doctorsArr.iterator(); 
			int flag = 0; //flag used to stop the iteration when we've found the correct object
			JsonObject doctor = null;
			//go through all doctors to find the one we're dealing with. A constant time search could be implemented, but that would conflict with the 
			//json format we are going with, which simplifies the syntax. This is a tradeoff in terms of writing code more easily, but we have lesser efficiency
			//had we more time to troubleshoot, we'd opt for the optimal setup.
			while(i.hasNext() && flag == 0) {
				docIndex += 1;
				doctor = (JsonObject) i.next();
				String currentEmail = (String) doctor.get("email");
				if(currentEmail.equals(email)) {
					flag = 1;
				}
			}
			reader.close();
			return doctor;
		}
		catch(Exception e) {
			System.out.println("Something went wrong, presumably file couldn't be opened");
			return null;
		}
	}
	
	/**
	* Like getDoctor, this method takes in an email, and looks for the account with that email in accounts2.json of type "Patient".
	* @param email The email of the patient.
	*/
	public JsonObject getPatient(String email) {
		try {
			//reader to read accounts2.json
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			//parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
		   //we look for the JsonArray within accounts2.json, for that is where the JsonObject of our doctor is
			JsonArray accounts = (JsonArray) parser.get("accounts");
			JsonObject patientsObj = (JsonObject) accounts.get(0);	//this is the object that has the list of all doctors
			JsonArray patientArr = (JsonArray) patientsObj.get("patient");
			Iterator i;
			//Now we find the JsonObject for the doctor in particular we are dealing with, whom is identified by the passed email
			i = patientArr.iterator(); 
			int flag = 0; //flag used to stop the iteration when we've found the correct object
			JsonObject patient = null;
			//go through all doctors to find the one we're dealing with. A constant time search could be implemented, but that would conflict with the 
			//json format we are going with, which simplifies the syntax. This is a tradeoff in terms of writing code more easily, but we have lesser efficiency
			//had we more time to troubleshoot, we'd opt for the optimal setup.
			while(i.hasNext() && flag == 0) {
				patient = (JsonObject) i.next();
				String currentEmail = (String) patient.get("email");
				if(currentEmail.equals(email)) {
					flag = 1;
				}
			}
			reader.close();
			return patient;
		}
		catch(Exception e) {
			System.out.println("Something went wrong during execution of getPatient()");
			return null;
		}
	}
	
	/**
	* This method takes the doctor's JsonObject and returns the qth appointment in the doctor's list of appointments,
	* where q is an index (a valid q: 0 <=q < arraySize)
	*
	* @param doctor The JsonObject of the doctor
	* @param q The index of the appointment in the doctor's appointments list.
	*/
	public JsonObject getAppointment(JsonObject doctor, int q) {
		JsonArray appointmentIDs = (JsonArray) doctor.get("appointments");
		if(q < appointmentIDs.size()) {	//if the index is valid
			try{
				//parse the JSON file appointments.json to get the array of all appointments
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/appointments.json")));
				JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
				//the array of appointments
				JsonArray appointments = (JsonArray) parser.get("appointments");
				
				JsonObject d = (JsonObject) appointmentIDs.get(q);
				String aptID = (String) d.get("ID");
				//find the appointment information within the appointments2.json
				Iterator i = appointments.iterator();
				//flag used to signal the loop below should stop iterating
				int flag = 0;
				//the object to be returned
				JsonObject apt = null;
				//go through all appointments and check if the id matches.
				while(flag == 0 && i.hasNext()) {
					apt = (JsonObject) i.next();
					String currentID = (String) apt.get("number"); //this line wont execute TODO
					if(currentID.equals(aptID)) {
						flag = 1;
					}
				}
				if(!apt.get("number").equals(aptID)) {
					System.out.println("Error: " + aptID + " could not be found under appointments in appointments.json");
				}
				reader.close();
				return apt;
			} catch(Exception e) {
				System.out.println("Something went wrong, presumably file couldn't be opened 2");
				return null;
			}
		}else { //notify tester in the console
			System.out.println("Index out of bounds (Appointment array in doctor's at accounts2.son)");
			return null;
		}
	}	
	
	/**
	* This method takes in all the labels to be changed, looks into the desired appointment's information and updates the labels to be up to date
	*
	* @param name: label corresponding to the patient's name
	* @param date: label corresponding to date of appointment
	* @param time: label corresponding to the time of appointment
	* @param department: label corresponding to the department the appointment is under
	* @param type: label corresponding to the type of appointment (followup, etc)
	* @param doctor: a json object corresponding to the doctor we are dealing with
	* @param q: corresponds to the index in the list of a doctor's appointments that we will look at in the database
	*/
	public void viewAppointment(JLabel name, JLabel date, JLabel time, JLabel department, JLabel type, JsonObject doctor, int q) {
		JsonObject apt = getAppointment(doctor, q);
		//if a null appointment was returned by getAppointment, notify the tester in console.
		if(apt == null) {
			System.out.println("the appointment is null");
		}
		else {
			//we have found the appointment in the json
			//we now take the information from either account
			JsonObject patient = getPatient((String)apt.get("patient_email"));
			name.setText(patient.get("first_name") + " " + patient.get("last_name"));
			date.setText((String) apt.get("date"));
			time.setText((String) apt.get("time"));
			department.setText((String) apt.get("department"));
			type.setText((String)apt.get("type"));
		}
	}
	
	/**
	* This method takes in the user's list of appointments, save one entry has been removed, and writes the change to accounts2.json
	*
	* @param appointments: the JsonArray that contains the numerical ID to the user's appointments
	* @param doctor: the JsonObject corresponding to the user's account
	*/
	private void updateAppointments(JsonArray appointments, JsonObject doctor) {
		
		try {
			//reader to read accounts2.json
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			//parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
		   	//we look for the JsonArray within accounts2.json, for that is where the JsonObject of our doctor is
			JsonArray accounts = (JsonArray) parser.get("accounts");
			JsonObject doctorsObj = (JsonObject) accounts.get(1);	//this is the object that has the list of all doctors
			JsonArray doctorsArr = (JsonArray) doctorsObj.get("doctor");			
			reader.close();
			
			//update the doctor's appointments
			doctor.put("appointments",appointments);
			//update the array of doctors
			doctorsArr.set(docIndex,doctor);
			//put this updated array of doctors as the "doctor" object
			doctorsObj.put("doctor", doctorsArr);
			//update the object with all accounts to have this updated doctor object
			accounts.set(1, doctorsObj);
			//put the updated accounts array as the account entry in the JSON
			parser.put("accounts", accounts);
			
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));
			// Write updates to JSON file
			Jsoner.serialize(parser, writer);
			// Close the writer
			writer.close();
			
		}
		catch(Exception e) {
			System.out.println("Something went wrong when updating the doctors appointment's");
			System.out.println(e.getMessage());
		}
	}
   
	/**
	* Create the frame.
	* This is for when Doctor clicks "View Appointments" button.
	* It shows a list of appointments for the Doctor, including the basic appointment information
	* such as patient and date.
	* @param email The email of the doctor. Used to uniquely identify the user so we can easily access their info.
	*/
	public DoctorAppointmentView(String email){
		
		//the doctor's json object
		JsonObject doctor = getDoctor(email);
		//the array with all appointments the doctor has, this will be used in this constructor only to check for its size to see how many
		//appointments need to be set up, and whether a next button is necessary.
		JsonArray appointmentIDs = (JsonArray) doctor.get("appointments");
		//if we went thorugh the entire doctorsArr and did not find the doctor with email, notify the tester in the console that something is wrong.
		//the panel will not be initialized if this test passes.
		if(doctor != null && !doctor.get("email").equals(email)) {
			System.out.println("Error: " + email + " could not be found under doctor in account2.json");
		}
		//proceed normally otherwise.
		else {		
			//having the above information we set up the panel
			// set the frame properties
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 587, 514);
			
			// create the panel for the frame
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			// add the title label for the panel
			JLabel MainLabel = new JLabel("View Appointments");
			MainLabel.setBounds(252, 11, 154, 14);
			contentPane.add(MainLabel);
			
			// create another panel to display the first appointment
			JPanel Appointment1 = new JPanel();
			Appointment1.setBounds(123, 36, 347, 172);
			contentPane.add(Appointment1);
			GridBagLayout gbl_Appointment1 = new GridBagLayout();
			gbl_Appointment1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_Appointment1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_Appointment1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_Appointment1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			Appointment1.setLayout(gbl_Appointment1);
			
			// Label showing where patient's name is on GUI
			JLabel PatientNameLabel = new JLabel("Patient:");
			GridBagConstraints gbc_PatientNameLabel = new GridBagConstraints();
			gbc_PatientNameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_PatientNameLabel.gridx = 1;
			gbc_PatientNameLabel.gridy = 1;
			Appointment1.add(PatientNameLabel, gbc_PatientNameLabel);
			
			// Label showing patient's name
			JLabel PatientName = new JLabel("Bob, Builder");
			GridBagConstraints gbc_PatientName = new GridBagConstraints();
			gbc_PatientName.insets = new Insets(0, 0, 5, 5);
			gbc_PatientName.gridx = 3;
			gbc_PatientName.gridy = 1;
			Appointment1.add(PatientName, gbc_PatientName);
			
			// Label showing where date of appointment is on GUI
			JLabel AppointmentDateLabel = new JLabel("Date:");
			GridBagConstraints gbc_AppointmentDateLabel = new GridBagConstraints();
			gbc_AppointmentDateLabel.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentDateLabel.gridx = 1;
			gbc_AppointmentDateLabel.gridy = 2;
			Appointment1.add(AppointmentDateLabel, gbc_AppointmentDateLabel);
			
			// Label showing date of appointment
			JLabel AppointmentDate = new JLabel("03/03/03");
			GridBagConstraints gbc_AppointmentDate = new GridBagConstraints();
			gbc_AppointmentDate.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentDate.gridx = 3;
			gbc_AppointmentDate.gridy = 2;
			Appointment1.add(AppointmentDate, gbc_AppointmentDate);
			
			// Label showing where time of appointment is on GUI
			JLabel AppointmentTimeLabel = new JLabel("Time:");
			GridBagConstraints gbc_AppointmentTimeLabel = new GridBagConstraints();
			gbc_AppointmentTimeLabel.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentTimeLabel.gridx = 1;
			gbc_AppointmentTimeLabel.gridy = 3;
			Appointment1.add(AppointmentTimeLabel, gbc_AppointmentTimeLabel);
			
			// Label showing the time of appointment
			JLabel AppointmentTime = new JLabel("10:20-10:40");
			GridBagConstraints gbc_AppointmentTime = new GridBagConstraints();
			gbc_AppointmentTime.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentTime.gridx = 3;
			gbc_AppointmentTime.gridy = 3;
			Appointment1.add(AppointmentTime, gbc_AppointmentTime);
			
			// Label showing where department is on GUI
			JLabel DepartmentLabel = new JLabel("Department:");
			GridBagConstraints gbc_DepartmentLabel = new GridBagConstraints();
			gbc_DepartmentLabel.insets = new Insets(0, 0, 5, 5);
			gbc_DepartmentLabel.gridx = 1;
			gbc_DepartmentLabel.gridy = 4;
			Appointment1.add(DepartmentLabel, gbc_DepartmentLabel);
			
			// Label showing the department
			JLabel Department = new JLabel("Cardiology");
			GridBagConstraints gbc_Department = new GridBagConstraints();
			gbc_Department.insets = new Insets(0, 0, 5, 5);
			gbc_Department.gridx = 3;
			gbc_Department.gridy = 4;
			Appointment1.add(Department, gbc_Department);
			
			// Label showing where appointment type is on GUI
			JLabel AppointmentTypeLabel = new JLabel("Appointment Type:");
			GridBagConstraints gbc_AppointmentTypeLabel = new GridBagConstraints();
			gbc_AppointmentTypeLabel.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentTypeLabel.gridx = 1;
			gbc_AppointmentTypeLabel.gridy = 5;
			Appointment1.add(AppointmentTypeLabel, gbc_AppointmentTypeLabel);
			
			// Label showing type of appointment
			JLabel AppointmentType = new JLabel("Follow Up");
			GridBagConstraints gbc_AppointmentType = new GridBagConstraints();
			gbc_AppointmentType.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentType.gridx = 3;
			gbc_AppointmentType.gridy = 5;
			Appointment1.add(AppointmentType, gbc_AppointmentType);
			
			// create another panel for another appointment
			JPanel Appointment2 = new JPanel();
			Appointment2.setBounds(123, 219, 347, 172);
			contentPane.add(Appointment2);
			GridBagLayout gbl_Appointment2 = new GridBagLayout();
			gbl_Appointment2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_Appointment2.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_Appointment2.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_Appointment2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			Appointment2.setLayout(gbl_Appointment2);
			
			
			// Label showing where patient's name is on GUI
			JLabel PatientNameLabel2 = new JLabel("Patient:");
			GridBagConstraints gbc_PatientNameLabel2 = new GridBagConstraints();
			gbc_PatientNameLabel2.insets = new Insets(0, 0, 5, 5);
			gbc_PatientNameLabel2.gridx = 1;
			gbc_PatientNameLabel2.gridy = 1;
			Appointment2.add(PatientNameLabel2, gbc_PatientNameLabel2);
			
			// Label showing patient's name
			JLabel PatientName2 = new JLabel("Thomas, Train");
			GridBagConstraints gbc_PatientName2 = new GridBagConstraints();
			gbc_PatientName2.insets = new Insets(0, 0, 5, 5);
			gbc_PatientName2.gridx = 3;
			gbc_PatientName2.gridy = 1;
			Appointment2.add(PatientName2, gbc_PatientName2);
			
			// Label showing where date is on GUI
			JLabel AppointmentDateLabel2 = new JLabel("Date:");
			GridBagConstraints gbc_AppointmentDateLabel2 = new GridBagConstraints();
			gbc_AppointmentDateLabel2.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentDateLabel2.gridx = 1;
			gbc_AppointmentDateLabel2.gridy = 2;
			Appointment2.add(AppointmentDateLabel2, gbc_AppointmentDateLabel2);
			
			// Label showing date of appointment
			JLabel AppointmentDate2 = new JLabel("03/03/03");
			GridBagConstraints gbc_AppointmentDate2 = new GridBagConstraints();
			gbc_AppointmentDate2.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentDate2.gridx = 3;
			gbc_AppointmentDate2.gridy = 2;
			Appointment2.add(AppointmentDate2, gbc_AppointmentDate2);
			
			// Label showing where time is on GUI
			JLabel AppointmentTimeLabel2 = new JLabel("Time:");
			GridBagConstraints gbc_AppointmentTimeLabel2 = new GridBagConstraints();
			gbc_AppointmentTimeLabel2.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentTimeLabel2.gridx = 1;
			gbc_AppointmentTimeLabel2.gridy = 3;
			Appointment2.add(AppointmentTimeLabel2, gbc_AppointmentTimeLabel2);
			
			// Label showing the time of appointment
			JLabel AppointmentTime2 = new JLabel("10:20-10:40");
			GridBagConstraints gbc_AppointmentTime2 = new GridBagConstraints();
			gbc_AppointmentTime2.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentTime2.gridx = 3;
			gbc_AppointmentTime2.gridy = 3;
			Appointment2.add(AppointmentTime2, gbc_AppointmentTime2);
			
			// Label showing where department is on GUI
			JLabel DepartmentLabel2 = new JLabel("Department:");
			GridBagConstraints gbc_DepartmentLabel2 = new GridBagConstraints();
			gbc_DepartmentLabel2.insets = new Insets(0, 0, 5, 5);
			gbc_DepartmentLabel2.gridx = 1;
			gbc_DepartmentLabel2.gridy = 4;
			Appointment2.add(DepartmentLabel2, gbc_DepartmentLabel2);
			
			// Label showing department
			JLabel Department2 = new JLabel("Cardiology");
			GridBagConstraints gbc_Department2 = new GridBagConstraints();
			gbc_Department2.insets = new Insets(0, 0, 5, 5);
			gbc_Department2.gridx = 3;
			gbc_Department2.gridy = 4;
			Appointment2.add(Department2, gbc_Department2);
			
			// Label showing where appointment type is on GUI
			JLabel AppointmentTypeLabel2 = new JLabel("Appointment Type:");
			GridBagConstraints gbc_AppointmentTypeLabel2 = new GridBagConstraints();
			gbc_AppointmentTypeLabel2.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentTypeLabel2.gridx = 1;
			gbc_AppointmentTypeLabel2.gridy = 5;
			Appointment2.add(AppointmentTypeLabel2, gbc_AppointmentTypeLabel2);
			
			// Label showing the appointment type
			JLabel AppointmentType2 = new JLabel("Follow Up");
			GridBagConstraints gbc_AppointmentType2 = new GridBagConstraints();
			gbc_AppointmentType2.insets = new Insets(0, 0, 5, 5);
			gbc_AppointmentType2.gridx = 3;
			gbc_AppointmentType2.gridy = 5;
			Appointment2.add(AppointmentType2, gbc_AppointmentType2);
			
			// Button to cancel the appointment
			JButton CancelButton2 = new JButton("Cancel");
			CancelButton2.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(appointmentIDs.size() == 1) {
						//Next was pressed after multiple appointments were canceled up until a single one was left.
						//Appointment 2 now has non-sense information. Prevent the user from seeing it
						Appointment2.setVisible(false);
						
					}
					else if(appointmentIDs.size() == 2) {
						//there are two appointments on display, and no others
						Appointment2.setVisible(false);
						appointmentIDs.remove(1);
						counter = 0;	//may create some problems, but the actions shouldn't be possible to begin with
						updateAppointments(appointmentIDs, doctor);
					}
					else {
						//there more than three appointments
						if(appointmentIDs.size() == counter+1) {
							//this is the last appointment in the list
							Appointment2.setVisible(false);
							appointmentIDs.remove(counter);
							//counter -= 1;
						}else {
							//this is not the last appointment in the list
							viewAppointment(PatientName2, AppointmentDate2, AppointmentTime2, Department2, AppointmentType2, doctor, counter+1);
							appointmentIDs.remove(counter);
						}
						updateAppointments(appointmentIDs, doctor);
					}

					
					
				}
		});
			GridBagConstraints gbc_CancelButton2 = new GridBagConstraints();
			gbc_CancelButton2.insets = new Insets(0, 0, 0, 5);
			gbc_CancelButton2.gridx = 3;
			gbc_CancelButton2.gridy = 6;
			Appointment2.add(CancelButton2, gbc_CancelButton2);
			
			
			
			// Button for canceling the appointment
			JButton CancelButton = new JButton("Cancel");
			CancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if(appointmentIDs.size() == 1) {
							//there is only one appointment on display
							appointmentIDs.remove(0);
							Appointment1.setVisible(false);
							updateAppointments(appointmentIDs, doctor);
							
						}
						else if(appointmentIDs.size() == 2) {
							//there are two appointments on display, and no others
							viewAppointment(PatientName, AppointmentDate, AppointmentTime, Department, AppointmentType, doctor, 1);
							Appointment2.setVisible(false);
							appointmentIDs.remove(0);
							updateAppointments(appointmentIDs, doctor);
						}
						else {
							//there more than three appointments
							viewAppointment(PatientName, AppointmentDate, AppointmentTime, Department, AppointmentType, doctor, counter);
							viewAppointment(PatientName2, AppointmentDate2, AppointmentTime2, Department2, AppointmentType2, doctor, counter+1);
							appointmentIDs.remove(counter-1);
							updateAppointments(appointmentIDs, doctor);
						}

						
						
					}
			});
			GridBagConstraints gbc_CancelButton = new GridBagConstraints();
			gbc_CancelButton.insets = new Insets(0, 0, 0, 5);
			gbc_CancelButton.gridx = 3;
			gbc_CancelButton.gridy = 6;
			Appointment1.add(CancelButton, gbc_CancelButton);
			
			
			// Button that goes to previous page (if there's more than 1 page of appointments)
			JButton PreviousButton = new JButton("Previous");
			PreviousButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//Event Handler for PreviousButton
					//If PreviousButton is enabled then there is at least one prior appointment, since we're going appointment by appointment, this code won't attempt
					//to check for a negative index.
					//Currently: appointment by appointment.
					Appointment2.setVisible(true);	//this is necessary when the last page is reached
					if(counter > 1){
					//counter is the leftmost index in the doctor's list of appointments
						//PreviousButton.setVisible(false);	//assures this method will not produce an IndexOutOfBounds exception
						viewAppointment(PatientName, AppointmentDate, AppointmentTime, Department, AppointmentType, doctor, counter-2);
						viewAppointment(PatientName2, AppointmentDate2, AppointmentTime2, Department2, AppointmentType2, doctor, counter-=1);
					}
				}
			});
			PreviousButton.setBounds(10, 441, 89, 23);
			contentPane.add(PreviousButton);
			
			// Button that goes to next page (if there's more than 1 page of appointments)
			JButton NextButton = new JButton("Next");
			NextButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					//Event Handler for NextButton
					//If NextButton is enabled, then when this line is reached there are two appointments in display, and there is at least one more appointment
					//to be shown. TODO: decide whether or not we're moving forward appointment by appointment or page by page
					//Currently: appointment by appointment.
					if(counter < appointmentIDs.size() - 1){
						//there is at least one more appointment to be shown
						PreviousButton.setVisible(true);	//redundant if already enabled, I expect no harm out of that FIXME: possible bug if it toggles
						//counter is not the last index in the doctor's list of appointments, show the next one in the list
							viewAppointment(PatientName, AppointmentDate, AppointmentTime, Department, AppointmentType, doctor, counter);
							viewAppointment(PatientName2, AppointmentDate2, AppointmentTime2, Department2, AppointmentType2, doctor, counter+=1);
					}
					
				}
			});
			NextButton.setBounds(472, 441, 89, 23);
			contentPane.add(NextButton);		
			
			// Button that returns to previous window
			JButton ReturnToMainButton = new JButton("Return");
			// If button is clicked, then close this window, effectively returning to previous.
			ReturnToMainButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
				}
			});
			ReturnToMainButton.setBounds(252, 441, 89, 23);
			contentPane.add(ReturnToMainButton);
			
			//Below we initialize the appointments on display, if there are any to display.	
			if(appointmentIDs.size() == 0) {
				//no appointments have been booked, display nothing
				Appointment1.setVisible(false);
				Appointment2.setVisible(false);
				NextButton.setVisible(false);
				PreviousButton.setVisible(false);
			}
			else if(appointmentIDs.size() == 1){
				//a single appointment has been booked
				Appointment2.setVisible(false);
				NextButton.setVisible(false);
				PreviousButton.setVisible(false);
				
				viewAppointment(PatientName, AppointmentDate, AppointmentTime, Department, AppointmentType, doctor, 0);
						
			}//end of elif
			else {
				PreviousButton.setVisible(false);
				if(appointmentIDs.size() == 2) {
					NextButton.setVisible(false);
				}
				viewAppointment(PatientName, AppointmentDate, AppointmentTime, Department, AppointmentType, doctor, 0);
				viewAppointment(PatientName2, AppointmentDate2, AppointmentTime2, Department2, AppointmentType2, doctor, 1);
			}//end of else
			
		}//end bracket of the else that dictates the doctor's account has been found
	}//end of constructor
	
} //end of class
