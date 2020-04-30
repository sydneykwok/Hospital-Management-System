package hospitalmanagement;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/**
 * This class displays the stored information of the specified appointment.
 * @author sydneykwok, arthurfranca
 *
 */
public class PatientAppointmentInfoView extends JFrame {

	private JPanel contentPane;
	
	



	/**
	 * Create the frame. The patient will be shown information about
	 * the specific appointment that was chosen in the previous pane.
	 * 
	 * @param email        The email of the patient (used as an identifier for
	 *                     reading/writing to JSON).
	 * @param apptID 	   The id number of the appointment.
	 */
	public PatientAppointmentInfoView(String email, String apptID) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// main label for panel
		JLabel lblApptInformation = new JLabel("Appointment Information");

		// create panel for patient info
		JPanel ApptInfo = new JPanel();
		ApptInfo.setBackground(new Color(135, 206, 235));
		GridBagLayout gbl_ApptInfo = new GridBagLayout();
		gbl_ApptInfo.columnWidths = new int[] { 50, 50 };
		gbl_ApptInfo.rowHeights = new int[] { 30, 30, 30, 30, 30 };
		gbl_ApptInfo.columnWeights = new double[] { 0.0, 1.0 };
		gbl_ApptInfo.rowWeights = new double[] { 0.2, 0.2, 0.2, 0.2, 0.2 };
		ApptInfo.setLayout(gbl_ApptInfo);

		// Label for department
		JLabel lblDepartment = new JLabel("Department:");
		GridBagConstraints gbc_lblDepartment = new GridBagConstraints();
		gbc_lblDepartment.anchor = GridBagConstraints.EAST;
		gbc_lblDepartment.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartment.gridx = 0;
		gbc_lblDepartment.gridy = 3;
		ApptInfo.add(lblDepartment, gbc_lblDepartment);

		// Label for doctor
		JLabel lblDoctor = new JLabel("Doctor:");
		GridBagConstraints gbc_lblDoctor = new GridBagConstraints();
		gbc_lblDoctor.anchor = GridBagConstraints.EAST;
		gbc_lblDoctor.insets = new Insets(0, 0, 5, 5);
		gbc_lblDoctor.gridx = 0;
		gbc_lblDoctor.gridy = 4;
		ApptInfo.add(lblDoctor, gbc_lblDoctor);

		// Label for appt id number
		JLabel ApptIDNumLabel = new JLabel("Appt ID#:");
		GridBagConstraints gbc_ApptIDNumLabel = new GridBagConstraints();
		gbc_ApptIDNumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ApptIDNumLabel.gridx = 0;
		gbc_ApptIDNumLabel.gridy = 0;
		ApptInfo.add(ApptIDNumLabel, gbc_ApptIDNumLabel);

		// Label for date of appointment
		JLabel lblDate = new JLabel("Date:");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 1;
		ApptInfo.add(lblDate, gbc_lblDate);

		// Label for time of appointment
		JLabel lblTime = new JLabel("Time:");
		GridBagConstraints gbc_lblTime = new GridBagConstraints();
		gbc_lblTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblTime.gridx = 0;
		gbc_lblTime.gridy = 2;
		ApptInfo.add(lblTime, gbc_lblTime);

		// department value from json
		JLabel lblDeptVal = new JLabel("");
		// doctor email from json
		JLabel lblDoctorEmail = new JLabel("");
		// date of appointment from json
		JLabel ApptDate = new JLabel("");
		// time of appointment from json
		JLabel lblApptTime = new JLabel("");

		// Get the appointment from the appointments.json as a JsonObject
		try {
			// reader to read tests json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/appointments.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the json array of appointments
			JsonArray appointments = (JsonArray) parser.get("appointments");

			// get the specified appointment
			Iterator i = appointments.iterator();
			JsonObject appt = null;
			int arrIndex = 0;

			// iterate through the json array of appointments
			while (i.hasNext()) {
				// get the json object of the appt
				appt = (JsonObject) i.next();
				// get the id of the appt
				String idNum = (String) appt.get("number");
				// compare that number to the passed apptID
				if (idNum.equals(apptID)) {
					// break out of the loop
					break;
				}
				arrIndex++;
			}

			// add the appt info to the pane by reading from the json
			lblDeptVal.setText((String) appt.get("department"));
			lblDoctorEmail.setText((String) appt.get("doctor_email"));
			ApptDate.setText(appt.get("date") + "/" + appt.get("year"));
			lblApptTime.setText((String) appt.get("time"));

			// close the reader
			reader.close();
		} catch (Exception e) {
			// if we get any exceptions, print them
			e.printStackTrace();
		}

		// add the department value label
		GridBagConstraints gbc_lblDeptVal = new GridBagConstraints();
		gbc_lblDeptVal.insets = new Insets(0, 0, 5, 0);
		gbc_lblDeptVal.gridx = 1;
		gbc_lblDeptVal.gridy = 3;
		ApptInfo.add(lblDeptVal, gbc_lblDeptVal);

		// add the doctor email label
		GridBagConstraints gbc_lblDoctorEmail = new GridBagConstraints();
		gbc_lblDoctorEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblDoctorEmail.gridx = 1;
		gbc_lblDoctorEmail.gridy = 4;
		ApptInfo.add(lblDoctorEmail, gbc_lblDoctorEmail);

		// appointment id number
		JLabel ApptNum = new JLabel(apptID);
		GridBagConstraints gbc_ApptNum = new GridBagConstraints();
		gbc_ApptNum.insets = new Insets(0, 0, 5, 0);
		gbc_ApptNum.gridx = 1;
		gbc_ApptNum.gridy = 0;
		ApptInfo.add(ApptNum, gbc_ApptNum);

		// add the appointment date label
		GridBagConstraints gbc_ApptDate = new GridBagConstraints();
		gbc_ApptDate.insets = new Insets(0, 0, 5, 0);
		gbc_ApptDate.gridx = 1;
		gbc_ApptDate.gridy = 1;
		ApptInfo.add(ApptDate, gbc_ApptDate);

		// add the appointment time label
		GridBagConstraints gbc_lblApptTime = new GridBagConstraints();
		gbc_lblApptTime.insets = new Insets(0, 0, 5, 0);
		gbc_lblApptTime.gridx = 1;
		gbc_lblApptTime.gridy = 2;
		ApptInfo.add(lblApptTime, gbc_lblApptTime);

		// button to return to previous view
		JButton btnReturn = new JButton("Return");
		// Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// Return back to the appointments page after clicking return button
				PatientAppointmentView p = new PatientAppointmentView(email);
				p.setVisible(true);
				dispose();
			}
		});
		
		//Event Handler that signals the patient wishes to cancel the appointment in display
		//this is done by finding the patient's JsonObject, getting its JsonArray of appointments, and removing the appointment with the ID in display
		JButton CancelButton = new JButton("Cancel");
		CancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String aptID = ApptNum.getText();
				try {
					//reader to read accounts2.json
					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
					//parser to parse the reader
					JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
					//we look for the JsonArray within accounts2.json, for that is where the JsonObject of our doctor is
					JsonArray accounts = (JsonArray) parser.get("accounts");
					JsonObject patientsObj = (JsonObject) accounts.get(0);	//this is the object that has the list of all patients
					JsonArray patientArr = (JsonArray) patientsObj.get("patient");
					
					Iterator i;
					//Now we find the JsonObject for the doctor in particular we are dealing with, whom is identified by the passed email
					i = patientArr.iterator(); 
					int flag = 0; //flag used to stop the iteration when we've found the correct object
					JsonObject patient = null;
					//go through all doctors to find the one we're dealing with. A constant time search could be implemented, but that would conflict with the 
					//json format we are going with, which simplifies the syntax. This is a tradeoff in terms of writing code more easily, but we have lesser efficiency
					//had we more time to troubleshoot, we'd opt for the optimal setup.
					//keeps track of the patient's account in the array of patients
					int k = 0;
					while(i.hasNext() && flag == 0) {
						patient = (JsonObject) i.next();
						String currentEmail = (String) patient.get("email");
						if(currentEmail.equals(email)) {
							flag = 1;
						}else {
							k += 1;
						}
					}
					reader.close();
					
					//get the patients appointments
					JsonArray aptArray = (JsonArray) patient.get("appointments");
					// iterate through the array of appointments until we find the one in display
					//keeps track of the index
					int j = 0;
					//tracks whether we've found the appointment index
					int success = 0; //determines whether we've
					while (j < aptArray.size()) {
					    if(aptID.equals(aptArray.getString(j))) {
					    	success += 1;
					    	break;
					    }
					    j += 1;
					}
					if(success != 0) {
						//remove the appointment from the patient's array of appointments
						aptArray.remove(j);
						//update the doctor's appointments
						patient.put("appointments",aptArray);
						//update the array of doctors
						patientArr.set(k,patient);
						//put this updated array of doctors as the "doctor" object
						patientsObj.put("patient", patientArr);
						//update the object with all accounts to have this updated doctor object
						accounts.set(0, patientsObj);
						//put the updated accounts array as the account entry in the JSON
						parser.put("accounts", accounts);
						
						// Create a writer
						BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));
						// Write updates to JSON file
						Jsoner.serialize(parser, writer);
						// Close the writer
						writer.close();
						dispose();
					}
				} catch(Exception exception) {
					System.out.println("Something went with the cancellation");
				}
			}
			
		});

		// implement the group layout
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(72, Short.MAX_VALUE)
					.addComponent(ApptInfo, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addGap(68))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(137)
					.addComponent(lblApptInformation)
					.addContainerGap(163, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(96, Short.MAX_VALUE)
					.addComponent(CancelButton, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(71)
					.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addGap(109))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblApptInformation)
					.addGap(18)
					.addComponent(ApptInfo, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(CancelButton)
						.addComponent(btnReturn))
					.addGap(15))
		);

		// add the grouplayout content pane to the main content pane
		contentPane.setLayout(gl_contentPane);
	}
}

