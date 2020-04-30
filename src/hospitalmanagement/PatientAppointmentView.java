package hospitalmanagement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/*
* Class that displays the patient's appointments so that they
* can select one to view more about it.
*/
public class PatientAppointmentView extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Create the frame. This constructor will display ALL the patient's (past,
	 * present, and future appointments). 
	 * 
	 * @param email The email of the patient. Used to uniquely identify the user so
	 *              we can easily access their info.
	 */
	public PatientAppointmentView(String email) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add a label to the panel to prompt the user to select an appt
		JLabel lblselectAppt = new JLabel("Please Select An Appointment");
		lblselectAppt.setBounds(132, 13, 182, 16);
		contentPane.add(lblselectAppt);
		
		// get the arraylist of appointment id's for this user's appointments
		ArrayList<String> myApptIDs = getApptIDsFromAccount(email); //new ArrayList<String>();
		// get the arraylist of info strings for this user's appointments
		ArrayList<String> myApptInfo = getApptInfoStrings(myApptIDs); //new ArrayList<String>();

		// create a list of these appts
		List<String> values = new ArrayList<>(myApptInfo.size());
		for (int j = 0; myApptInfo.size() > j; j++) {
			values.add(myApptInfo.get(j));
		}

		// add the list of appts to the panel
		JList<Object> apptList = new JList<Object>(values.toArray(new String[values.size()]));
		apptList.setValueIsAdjusting(true);
		apptList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		apptList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		apptList.setBounds(64, 34, 315, 161);
		JScrollPane patientListScroller = new JScrollPane();
		patientListScroller.setLocation(50, 38);
		patientListScroller.setSize(330, 169);
		patientListScroller.setViewportView(apptList);
		apptList.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(patientListScroller);

		// add a button to the panel to allow the user to confirm their selection
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int selectedIndex = apptList.getSelectedIndex();
				// if the user has not made a selection, give them a pop-up
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(contentPane, "Please select an appointment.");
				} else {	// else, show the appt info on this new pane
					PatientAppointmentInfoView p = new PatientAppointmentInfoView(email, myApptIDs.get(selectedIndex));
					p.setVisible(true);
					dispose();
				}
			}
		});
		btnConfirm.setBounds(104, 215, 97, 25);
		contentPane.add(btnConfirm);

		// add a button to the panel to allow the user to return to the previous view
		JButton btnCancel = new JButton("Return");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(236, 215, 97, 25);
		contentPane.add(btnCancel);
	}
	
	/**
	 * Read from the account json and get the appointment IDs of all the appointments
	 * of the patient with the given email.
	 * @param email The email of this patient.
	 * @return An ArrayList of Strings of the ID numbers of the appointments.
	 */
	private ArrayList<String> getApptIDsFromAccount(String email) {
		// create the arraylist
		ArrayList<String> myApptIDs = new ArrayList<String>();
		// get the jsonobj of this patient
		JsonObject patient = Account.getAccountJSONObj("Patient", email);
		// get this patient's array of appointments
		JsonArray appts = (JsonArray) patient.get("appointments");
		
		// add the ID of each of these appointments to the arraylist
		for(int i = 0; i < appts.size(); i++) {
			myApptIDs.add(appts.getString(i));
		}
		return myApptIDs;
	}
	
	/**
	 * Create an array of Strings with information about each of the appointments
	 * specified in the apptIDs arraylist.
	 * 
	 * @param apptIDs The ArrayList of apptIDs of appointments to get the info for.
	 * @return An ArrayList of info strings to display info about the appointments
	 * 			to the patient.
	 */
	private ArrayList<String> getApptInfoStrings(ArrayList<String> apptIDs) {
		// create the arraylist
		ArrayList<String> apptInfo = new ArrayList<String>();
		
		// read the appointments from the appointment json
		try {
			// reader to read appointments json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/appointments.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the json array of appointments
			JsonArray myAppts = (JsonArray) parser.get("appointments");

			// for each apptID, get the info string of that appt
			for(int j = 0; j < apptIDs.size(); j++) {
				// for each appointment
				for(int k = 0; k < myAppts.size(); k++) {
					// get the json object of the appt
					JsonObject appt = (JsonObject) myAppts.get(k);
					// get the id # of this appt
					String id = (String) appt.get("number");
					// compare that id# to the j-th appt id
					if (id.equals(apptIDs.get(j))) {
						// create a string of some of the appt info and add it to the arraylist
						apptInfo.add("Date: " + appt.get("date") + "/" + appt.get("year") + ", Time: " + appt.get("time")
						+ ", Doctor: " + appt.get("doctor_email") + ", Department: " + appt.get("department"));
					}
				}
			}
			//close the reader
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return the arraylist of strings
		return apptInfo;
	}
	
	/**
	 * Create the frame. This constructor will display only the patient's appointments
	 * that are set on or after the given date.
	 * @param email The email of the patient. Used to uniquely identify the user so
	 *              we can easily access their info.
	 * @param date The current date. Only appointments on or after this date will be
	 * 				displayed to the user.
	 */
	public PatientAppointmentView(String email, String date) {
		// TODO: implement this!!
		// we will need to get the current date and time and only display 
		// appointments after this time
	}

}
