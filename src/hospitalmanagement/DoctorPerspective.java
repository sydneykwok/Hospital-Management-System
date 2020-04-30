package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
* Class that displays the options the doctor can choose from upon logging in to their account;
* Includes options like: viewing appointments, patients, booking appointments, etc.
* @author arthurbfranca, ggdizon, sydneykwok, erinpaslawski
*/
public class DoctorPerspective extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	* Create the frame.
	* This is for when a Doctor logs into the system. They will be greeted with this Frame
	* Doctor can do various tasks by pressing the appropriate buttons.
	* @param email The email of the doctor. Used to uniquely identify the user so we can easily access their info.
	*/
	public DoctorPerspective(String email) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 430);
		setLocationRelativeTo(null);
		
		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setLayout(null);
		
		// add title label of the panel
		JLabel menuLabel = new JLabel("Alberta Health");
		menuLabel.setBounds(239, 11, 119, 14);
		contentPane.add(menuLabel);
		
		//Adding button for viewing appointments
		JButton ViewAppointmentsButton = new JButton("View Appointments");
		//Add event handler for view appointment button
		ViewAppointmentsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Switch to panel that displays the appointments to view
				DoctorAppointmentView appointmentPane = new DoctorAppointmentView(email);
				appointmentPane.setVisible(true);
			}
		});
		ViewAppointmentsButton.setBounds(115, 73, 148, 53);
		contentPane.add(ViewAppointmentsButton);
		
		//Adding button for viewing patients
		JButton ViewPatientsButton = new JButton("View Patients");
		//Add event handler for view patient button
		ViewPatientsButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//Switch to panel that displays the patients that the doctor can view 
				DoctorPatientsView viewPatientsPane = new DoctorPatientsView(email);
				viewPatientsPane.setVisible(true);
			}
		});
		ViewPatientsButton.setBounds(115, 204, 148, 53);
		contentPane.add(ViewPatientsButton);
		
		//Adding button for viewing availability
		JButton ViewAvailabilityButton = new JButton("View Availability");
		ViewAvailabilityButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ViewSchedulePerspective seeSched = new ViewSchedulePerspective(email, 1, getToday());
				seeSched.setVisible(true);
			}
		});
		ViewAvailabilityButton.setBounds(115, 137, 148, 53);
		contentPane.add(ViewAvailabilityButton);
		
		//Adding button for booking appointments
		JButton BookAppointmentButton = new JButton("Book Appointment");
		//Add event handler for book appointment button
		BookAppointmentButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Switch to panel where the doctor can book an appointment
				// ORIGINAL !!!!!!!!!!!1
				//DoctorBookPatient patientsPane = new DoctorBookPatient(email);
				// Sydney experiment panel
				DoctorBookAppointment patientsPane = new DoctorBookAppointment(email);
				patientsPane.setVisible(true);
			}
		});
		BookAppointmentButton.setBounds(289, 73, 148, 53);
		contentPane.add(BookAppointmentButton);
		
		//Adding button for setting up the doctor's availability
		JButton SetAvailabilityButton = new JButton("Set Availability");
		SetAvailabilityButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				SetAvailability availabilityPane = new SetAvailability(email, 1);
				availabilityPane.setVisible(true);
			}
		});
		SetAvailabilityButton.setBounds(289, 137, 148, 53);
		contentPane.add(SetAvailabilityButton);
		
		//Adding button to return back to the home page
		JButton btnReturn = new JButton("Log out");
		//Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Return back to the login home page after clicking return button
				Login loginPane = new Login();
				loginPane.setVisible(true);
				dispose();
			}
		});
		btnReturn.setBounds(220, 345, 119, 25);
		contentPane.add(btnReturn);
		
		JButton btnUpdatePatientTest = new JButton("Update Test Results");
		btnUpdatePatientTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnUpdatePatientTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				UploadTestsView uploadTests = new UploadTestsView(email, "doctor");
				uploadTests.setVisible(true);
			}
		});
		btnUpdatePatientTest.setBounds(289, 270, 148, 53);
		contentPane.add(btnUpdatePatientTest);
		
		JButton btnAddPatients = new JButton("Add Patients");
		btnAddPatients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				DoctorPatientsAdd addPatientsPane = new DoctorPatientsAdd(email);
				addPatientsPane.setVisible(true);
			}
		});
		btnAddPatients.setBounds(289, 204, 148, 53);
		contentPane.add(btnAddPatients);
	}
	
	/**
	 * This method gets the current date using the System clock, and returns it
	 * as a string in the same format used in the JSONs
	 * 
	 * @return the current date in the same format used in the JSON
	 * @author erinpaslawski
	 *
	 */
	private String getToday() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String today;
		return(today = dtf.format(now)); 
	}
}