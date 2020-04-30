package hospitalmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * Class that displays the panel for patients to book an appointment.
 * 
 * @author sydneykwok
 */
public class PatientBookAppointment extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JComboBox<String> docDropdown;
	JComboBox<String> monthDropdown;
	JComboBox<String> dayComboBox;
	JComboBox<String> timeComboBox;
	// initializing some values
	String year = "2020";
	String month = "April";
	String day = "1";
	String time = "12";
	String docEmail = "bajwa@ucalgary.ca";
	String docLastName = "Sohaib";

	/**
	 * Create the frame for when the patient clicks on the "book appointment" button
	 * Shows drop down menus for patients to book an appointment by selecting the
	 * department, doctor, and timeslot for their appointment.
	 * 
	 * @param email The email of the patient. Used to uniquely identify the user so
	 *              we can easily access their info.
	 */
	public PatientBookAppointment(String email) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 510);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add title label for the panel
		JLabel MainLabel = new JLabel("Book Appointment");
		MainLabel.setBounds(173, 39, 113, 14);
		contentPane.add(MainLabel);

		// radio button for cardiology
		JRadioButton rdCardiology = new JRadioButton("Cardiology");
		rdCardiology.setActionCommand("Cardiology");
		buttonGroup.add(rdCardiology);
		rdCardiology.setBounds(40, 84, 113, 25);
		contentPane.add(rdCardiology);

		// radio button for nephrology
		JRadioButton rdNephrology = new JRadioButton("Nephrology");
		rdNephrology.setActionCommand("Nephrology");
		buttonGroup.add(rdNephrology);
		rdNephrology.setBounds(173, 84, 113, 25);
		contentPane.add(rdNephrology);

		// radio button for neurology
		JRadioButton rdNeurology = new JRadioButton("Neurology");
		rdNeurology.setActionCommand("Neurology");
		buttonGroup.add(rdNeurology);
		rdNeurology.setBounds(304, 84, 113, 25);
		contentPane.add(rdNeurology);
		
		// create an action listener
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if a radio button was selected...
				if (e.getSource() instanceof JRadioButton) {
					JRadioButton radioButton = (JRadioButton) e.getSource();
					if (radioButton.isSelected()) {
						// ...update the doctor combobox values to display
						// the chosen department's doctors
						setDocDropDownVals(email, radioButton.getText());
					}
				}
			}
		};

		// add the action listener to all the radiobuttons
		rdCardiology.addActionListener(actionListener);
		rdNephrology.addActionListener(actionListener);
		rdNeurology.addActionListener(actionListener);

		// add label for the doctors
		JLabel DoctorNameLabel = new JLabel("Doctor:");
		DoctorNameLabel.setBounds(87, 145, 75, 14);
		contentPane.add(DoctorNameLabel);

		// create drop menu for patients to select a doctor when booking an appointment
		docDropdown = new JComboBox<String>();
		docDropdown.setBounds(203, 141, 154, 22);
		contentPane.add(docDropdown);
		docDropdown.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// update the docEmail variable to match the currently selected doc
					docEmail = getSelectedDoctorEmail((String)event.getItem());
					// update other values dynamically
					updateDocLastName(docEmail);
					setTimeDropdownVals(year, month, day);
				}
			}
			
		});

		// add label for year
		JLabel yearLabel = new JLabel("Year:");
		yearLabel.setBounds(87, 190, 75, 14);
		contentPane.add(yearLabel);

		// TODO: decide how many years in advance we will allow bookings.
		// for now i have made the executive decision to allow bookings 5 years in advance.
		JComboBox<String> yearDropdown = new JComboBox<String>();
		yearDropdown.setBounds(203, 186, 154, 22);
		yearDropdown.addItem("2020");
		yearDropdown.addItem("2021");
		yearDropdown.addItem("2022");
		yearDropdown.addItem("2023");
		yearDropdown.addItem("2024");
		yearDropdown.addItem("2025");
		contentPane.add(yearDropdown);
		yearDropdown.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent event) {
		       if (event.getStateChange() == ItemEvent.SELECTED) {
		    	   year = (String)event.getItem();
		    	   // set month options depending on the selected year
		          setMonthDropdownVals(year);
		       }
		    }     
		});

		// label for month
		JLabel MonthLabel = new JLabel("Month:");
		MonthLabel.setBounds(87, 237, 75, 14);
		contentPane.add(MonthLabel);

		// dropdown menu of months for patient to book in
		monthDropdown = new JComboBox<String>();
		monthDropdown.setBounds(203, 233, 154, 22);
		setMonthDropdownVals(year);
		contentPane.add(monthDropdown);
		monthDropdown.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent event) {
		       if (event.getStateChange() == ItemEvent.SELECTED) {
		    	   month = (String)event.getItem();
		    	   // set date options depending on the selected month
		          setDayDropdownVals(year, month.toLowerCase());
		       }
		    }     
		});
		
		// label for day
		JLabel dayLabel = new JLabel("Day:");
		dayLabel.setBounds(87, 284, 56, 16);
		contentPane.add(dayLabel);
		
		// dropdown menu of days for the patient to book in
		dayComboBox = new JComboBox<String>();
		dayComboBox.setBounds(203, 281, 154, 22);
		setDayDropdownVals(year, month);
		contentPane.add(dayComboBox);
		dayComboBox.addItemListener(new ItemListener() {
			@Override
		    public void itemStateChanged(ItemEvent event) {
		       if (event.getStateChange() == ItemEvent.SELECTED) {
		    	   day = (String)event.getItem();
		    	   // set time options depending on the selected day
		    	   setTimeDropdownVals(year, month, day);
		       }
		    }     
		});
		
		// label for time
		JLabel lblTime = new JLabel("Start Time (1 hr):");
		lblTime.setBounds(87, 333, 104, 16);
		contentPane.add(lblTime);
		
		// dropdown menu of hours the patient can book in
		timeComboBox = new JComboBox<String>();
		timeComboBox.setBounds(203, 330, 154, 22);
		setTimeDropdownVals(year, month, day);
		contentPane.add(timeComboBox);

		// Adding button for booking an appointment
		JButton BookButton = new JButton("Book");
		BookButton.setBounds(162, 386, 89, 23);
		// Add event handler for book button
		BookButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String selectedDep = getSelectedDeptText(buttonGroup);
				String selectedDocEmail = getSelectedDoctorEmail(docDropdown.getSelectedItem().toString());
				String selectedYear = yearDropdown.getSelectedItem().toString();
				String selectedMonth = monthDropdown.getSelectedItem().toString();
				String selectedDay = dayComboBox.getSelectedItem().toString();
				String selectedMMDD = getMMDDString(selectedMonth, selectedDay);
				String selectedTime = getStartEndTime(timeComboBox.getSelectedItem().toString());
				
				// book the appointment with the given info
				WriteToJSON.bookAppointment("Patient", email, selectedDocEmail, selectedDep, selectedTime, selectedYear, selectedMMDD);
				
				Login lframe = new Login();
				// Displays a message to confirm that the patient has successfully booked their appointment
				JOptionPane.showMessageDialog(lframe, "Your appointment request has been made!");
			}
		});
		contentPane.add(BookButton);
	}

	/**
	 * Set the values of the docDropdown JComboBox depending on the user selected
	 * department.
	 * 
	 * @param email The patient's email.
	 * @param department The String value of the selected department.
	 */
	private void setDocDropDownVals(String email, String department) {
		// remove all current combo box values
		docDropdown.removeAllItems();
		// get a list of last names of all the doctors in the department
		ArrayList<String> docList = getDepDocs(email, department);
		// add each doctor in the list to the combobox
		for (String lastName: docList) {
			docDropdown.addItem(lastName);
		}
	}

	/**
	 * Returns an arraylist of the last names of all doctors in the given department.
	 * @param email The email of the patient.
	 * @param department The chosen department.
	 * @return an arraylist of the last names of all doctors in the given department.
	 */
	private ArrayList<String> getDepDocs(String email, String department) {
		ArrayList<String> docList = new ArrayList<String>();
		// get the index of the department in the departments json
		int depIndex = getDeptIndex(department);
		// go into the department JSON and add all doctors of the given department
		try {

			// create reader
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/departments.json")));
			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// read departments array from json
			JsonArray departments = (JsonArray) parser.get("departments");
			// extract the object representation of the department from the departments array
			JsonObject dept = (JsonObject) departments.get(depIndex);
			// then get the array representation of the doctors in that department
			JsonArray doctorArr = (JsonArray) dept.get("doctors");
			// close reader
			reader.close();
			
			// iterate through the array of doctors and add their last name to the ArrayList
			for (int i = 0; i < doctorArr.size(); i++) {
				
				String docUsername = doctorArr.getString(i);
				// get the JsonObject of the doctor with that username
				JsonObject doc = Account.getAccountJSONObjUsername(1, docUsername);
				// get the last name of that doctor
				String docLastName = (String) doc.get("last_name");
				// add that last name to the arraylist
			    docList.add(docLastName);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// return the arraylist
		return docList;
	}
	
	/**
	 * Helper method for the getDepDocs method. Purely for improving readability.
	 * Returns the String value of the given department.
	 * 
	 * @param department The chosen department.
	 * @return the String value of the department.
	 */
	private int getDeptIndex(String department) {
		if(department.equals("Neurology")) {
			return 0;
		} else if(department.equals("Cardiology")) {
			return 1;
		} else { // if(department.equals("Nephrology")) {
			return 2;
		}
	}
	
	/**
	 * Updates the values of the month JComboBox.
	 * If the year is 2020, only show from April onwards.
	 * Otherwise, show all the months.
	 * 
	 * @param year The currently selected year
	 */
	private void setMonthDropdownVals(String year) {
		// remove all current combo box values
		monthDropdown.removeAllItems();
		// now add new values depending on the year
		if(!(year.equals("2020"))) {
			monthDropdown.addItem("January");
			monthDropdown.addItem("February");
			monthDropdown.addItem("March");
		}
		monthDropdown.addItem("April");
		monthDropdown.addItem("May");
		monthDropdown.addItem("June");
		monthDropdown.addItem("July");
		monthDropdown.addItem("August");
		monthDropdown.addItem("September");
		monthDropdown.addItem("October");
		monthDropdown.addItem("November");
		monthDropdown.addItem("December");
	}
	
	/** TODO: Get current date and only show today and days after today.
	 * Updates the values of the day JComboBox.
	 * It refers to the dates.json to see the number of days the month has
	 * and adds the days accordingly.
	 * Note: for simplicity I am choosing not to take into account leap years.
	 * 
	 * @param year The currently selected month.
	 * @param month The currently selected month.
	 */
	private void setDayDropdownVals(String year, String month) {
		// remove all current combo box values
		dayComboBox.removeAllItems();
		// go into the dates json to add the days in the given month
		try {
			// reader to read dates.json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/dates.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the object with latest day of each month for the current year
			JsonObject chosenYear = (JsonObject) parser.get(year);
			// get the String representation of the number of days in that month
			String monthSizeString = (String) chosenYear.get(month.toLowerCase());
			//close the reader
			reader.close();
			
			// get the integer value of the number of days in the month
			int daysInMonth = Integer.parseInt(monthSizeString);
			// add the days in the month from 1 to daysInMonth
			for(int i = 1; i <= daysInMonth; i++) {
				dayComboBox.addItem(""+i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** TODO: get current time and only show future hours.
	 * Goes into the doctor's schedule and displays all available booking times for
	 * that doctor on that day.
	 * @param year The currently selected year. (MAY NOT NEED since scheduling doesn't currently take into account year...)
	 * @param month The currently selected month.
	 * @param day The currently selected day.
	 */
	private void setTimeDropdownVals(String year, String month, String day) {
		// remove all past vals
		timeComboBox.removeAllItems();
		
		// get the MM/DD representation of the currently chosen date
		String date = getMMDDString(month, day);
		
		// get the doctor object from the accounts json
		JsonObject doctor = (JsonObject) Account.getAccountJSONObj("Doctor", docEmail);
		// get the doctor's schedule object
		JsonObject schedule = (JsonObject) doctor.get("schedule");
		// get the doctor's schedule for the chosen day
		String availability = (String) schedule.get(date);
		if(availability == null) {
			timeComboBox.addItem("Doctor unavailable today.");
			timeComboBox.addItem("Please try another day.");
		} else {
			// availability = StartTime/EndTime
			// split the date so that halves[0] = StartTime and halves[1] = EndTime
			String[] halves = availability.split("/");
			int startTime = Integer.parseInt(halves[0]);
			int endTime = Integer.parseInt(halves[1]);
			// add all hours from ST to ET-1
			// (ET-1 because if shift ends at 9 and appointments are 1hr, appointment start times go up to 8)
			for(int i = startTime; i < endTime; i++) {
				timeComboBox.addItem(""+i);
			}
		}
	}
	
	/**
	 * Returns the MM/DD format of the currently selected date.
	 * @param month The selected month.
	 * @param day The selected day.
	 * @return the MM/DD format of the currently selected date.
	 */
	private String getMMDDString(String month, String day) {
		String MM = "";
		String DD = "";
		
		// set MM
		if(month == "January") {
			MM = "01";
		} else if(month == "February") {
			MM = "02";
		} else if(month == "March") {
			MM = "03";
		} else if(month == "April") {
			MM = "04";
		} else if(month == "May") {
			MM = "05";
		} else if(month == "June") {
			MM = "06";
		} else if(month == "July") {
			MM = "07";
		} else if(month == "August") {
			MM = "08";
		} else if(month == "September") {
			MM = "09";
		} else if(month == "October") {
			MM = "10";
		} else if(month == "November") {
			MM = "11";
		} else { //if(month == "december") {
			MM = "12";
		}
		
		// set DD
		DD = day;
		if(DD.length() == 1) {
			DD = "0" + DD;
		}	
		
		// return MM/DD
		return MM + "/" + DD;
	}
	
	/**
	 * Returns the email of the currently selected doctor.
	 * @param docLastName The currently selected doctor's last name.
	 * @return The email of the currently selected doctor.
	 */
	private String getSelectedDoctorEmail(String docLastName) {
		// get the array of doctors from the account json
		JsonArray doctors = (JsonArray) Account.getAccountJSONObj("Doctor");
		// iterate through the array to get the JsonObject of the chosen doctor
		JsonObject doc = null;
		for(int i = 0; i <= doctors.size(); i++) {
			doc = (JsonObject) doctors.get(i);
			if((doc.get("last_name")).equals(docLastName)) {
				break;
			}
		}
		return (String) doc.get("email");
	}
	
	/**
	 * Updates the docLastName variable whenever the chosen doctor changes.
	 * @param docEmail The email of the currently selected doctor.
	 */
	private void updateDocLastName(String docEmail) {
		// get JsonObject of the doctor
		JsonObject doc = (JsonObject) Account.getAccountJSONObj("Doctor", docEmail);
		// get the doctor's last name
		docLastName = (String) doc.get("last_name");
	}
	
	/**
	 * Returns the text of the selected department.
	 * 
	 * @param b The button group of department buttons.
	 * @return The String value of the selected department.
	 */
	private String getSelectedDeptText(ButtonGroup b) {
		for(Enumeration<AbstractButton> buttons = b.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if(button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}
	
	/**
	 * Returns the selected time of the appointment in startTime/endTime format.
	 * (Assumes all appointment bookings are to be 1 hour)
	 * @param startTime The selected appointment start time.
	 * @return The String representation of the appointment time in startTime/endTime format.
	 */
	private String getStartEndTime(String startTime) {
		int start = Integer.parseInt(startTime);
		int end = start+1;
		return startTime + "/" + end;
	}
}
