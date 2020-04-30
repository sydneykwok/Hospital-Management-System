package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Class that displays the selected patient's information.
 * @author arthurbfranca, ggdizon, sydneykwok
 */
public class DoctorPatientsAddInformation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 * This frame is the frame for when the Doctor selects the patient in the View Patient pane.
	 * The Doctor will be shown information about the specific patient that was chosen in the previous pane.
	 * @param email The email of the doctor (used as an identifier for reading/writing to JSON).
	 * @param patientIndex The index of the patient chosen by the Doctor that was passed from the previous pane.
	 */
	public DoctorPatientsAddInformation(String email, int patientIndex) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		// create the panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Get patient in accounts2.json as a JsonObject by getting the JsonArray
		// of all patients and iterating through that with an iterator and 
		// patient index. Patient index was passed from previous pane
		JsonObject patient;
		JsonArray patientAccounts = Account.getAccountJSONObj("Patient");
		Iterator i = patientAccounts.iterator();
		// iterate through patients in accounts2.json until patient reached using the index
		for (int j = 0; j < patientIndex; j++) {
			i.next();
		}
		patient = (JsonObject) i.next();
		
		// Get patient's information
		
		// Get patient's name
		String last_name = (String) patient.get("last_name");
		String first_name = (String) patient.get("first_name");
		String name = last_name + ", " + first_name;
		
		// Get patient's prescription
		ArrayList<String> prescriptions = new ArrayList<>();
		JsonArray prescriptionArray = (JsonArray) patient.get("prescriptions");
		for (Iterator j = prescriptionArray.iterator(); j.hasNext(); prescriptions.add((String) j.next()));
		
		// Get patient's gender
		String gender = (String) patient.get("gender");
		
		// Get patient's age
		String age = (String) patient.get("age");
		
		
		// Button that will close the current pane and return to the previous pane.
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				JOptionPane.showMessageDialog(contentPane, "Patient has NOT been added.");
				DoctorPatientsAdd viewPatientPane = new DoctorPatientsAdd(email);
				viewPatientPane.setVisible(true);
				dispose();
			}
		});
		
		// Button for adding patient as doctor's physician
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int addPatient = JOptionPane.showConfirmDialog(contentPane, "Add " + name + "?");
				if (addPatient == 0) {		// If YES was selected, then add patient as doctor's physician
					// TODO: doctors in account2.json have yet to have a "patients" array where the
					// selected patient can be added as doc's physician. Once this is made, add patient's
					// ID into doc's patients array
					boolean alreadyInList = checkAddPatient(patient, email);
					
					if (!alreadyInList) {
						JOptionPane.showMessageDialog(contentPane, name + " has been added.");
						dispose();
						DoctorPatientsAdd addPatientsPane = new DoctorPatientsAdd(email);
						addPatientsPane.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(contentPane, name + " is already in your list of patients.");
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, name + " has NOT been added.");
				}
			}
		});
		
		// Below will contain various labels showing the patient's information.
		JLabel lblPatientInformation = new JLabel("Patient Information");
		
		JPanel Patient = new JPanel();
		GridBagLayout gbl_Patient = new GridBagLayout();
		gbl_Patient.columnWidths = new int[]{50, 50, 0};
		gbl_Patient.rowHeights = new int[]{30, 30, 30, 0, 0};
		gbl_Patient.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_Patient.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		Patient.setLayout(gbl_Patient);
		
		JLabel PatientNameLabel = new JLabel("Patient:");
		GridBagConstraints gbc_PatientNameLabel = new GridBagConstraints();
		gbc_PatientNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_PatientNameLabel.gridx = 0;
		gbc_PatientNameLabel.gridy = 0;
		Patient.add(PatientNameLabel, gbc_PatientNameLabel);
		
		// Label for name of patient
		JLabel lblNameShown = new JLabel(name);
		GridBagConstraints gbc_lblNameShown = new GridBagConstraints();
		gbc_lblNameShown.insets = new Insets(0, 0, 5, 0);
		gbc_lblNameShown.gridx = 1;
		gbc_lblNameShown.gridy = 0;
		Patient.add(lblNameShown, gbc_lblNameShown);
		
		JLabel lblAge = new JLabel("Age:");
		GridBagConstraints gbc_lblAge = new GridBagConstraints();
		gbc_lblAge.insets = new Insets(0, 0, 5, 5);
		gbc_lblAge.gridx = 0;
		gbc_lblAge.gridy = 1;
		Patient.add(lblAge, gbc_lblAge);
		
		// Label for age of patient
		JLabel lblAgeShown = new JLabel(age);
		GridBagConstraints gbc_lblAgeShown = new GridBagConstraints();
		gbc_lblAgeShown.insets = new Insets(0, 0, 5, 0);
		gbc_lblAgeShown.gridx = 1;
		gbc_lblAgeShown.gridy = 1;
		Patient.add(lblAgeShown, gbc_lblAgeShown);
		
		JLabel lblGender = new JLabel("Gender:");
		GridBagConstraints gbc_lblGender = new GridBagConstraints();
		gbc_lblGender.insets = new Insets(0, 0, 5, 5);
		gbc_lblGender.gridx = 0;
		gbc_lblGender.gridy = 2;
		Patient.add(lblGender, gbc_lblGender);
		
		// Label for gender of patient
		JLabel lblGenderShown = new JLabel(gender);
		GridBagConstraints gbc_lblGenderShown = new GridBagConstraints();
		gbc_lblGenderShown.insets = new Insets(0, 0, 5, 0);
		gbc_lblGenderShown.gridx = 1;
		gbc_lblGenderShown.gridy = 2;
		Patient.add(lblGenderShown, gbc_lblGenderShown);

		JLabel lblPrescriptions = new JLabel("Prescriptions:");
		GridBagConstraints gbc_lblPrescriptions = new GridBagConstraints();
		gbc_lblPrescriptions.insets = new Insets(0, 0, 0, 5);
		gbc_lblPrescriptions.gridx = 0;
		gbc_lblPrescriptions.gridy = 3;
		Patient.add(lblPrescriptions, gbc_lblPrescriptions);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(158, Short.MAX_VALUE)
					.addComponent(lblPatientInformation)
					.addGap(155))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(75)
					.addComponent(Patient, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(67, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(101, Short.MAX_VALUE)
					.addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(96))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPatientInformation)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(Patient, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnAdd))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		// JList of patient's prescriptions.
		JList<Object> prescriptionList = new JList<Object>(prescriptions.toArray(new String[prescriptions.size()]));
		prescriptionList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		prescriptionList.setValueIsAdjusting(true);
		GridBagConstraints gbc_prescriptionList = new GridBagConstraints();
		gbc_prescriptionList.fill = GridBagConstraints.BOTH;
		gbc_prescriptionList.gridx = 1;
		gbc_prescriptionList.gridy = 3;
		JScrollPane prescriptionListScroller = new JScrollPane();
		prescriptionListScroller.setViewportView(prescriptionList);
		Patient.add(prescriptionListScroller, gbc_prescriptionList);
		contentPane.setLayout(gl_contentPane);
	}
	
	/**
	 * This method checks if the patient is already is the doctor's list of patients, and adds the patient's
	 * ID to the doctor's list of patients if the patient is not already in the list.
	 * @param patient JsonObject of the patient that the doctor wants to be added
	 * @param doctorEmail Email string of the doctor
	 * @return Boolean value. If the patient is already in the list, return true. If not, then return false
	 * @author ggdizon
	 */
	private boolean checkAddPatient(JsonObject patient, String doctorEmail) {
		boolean alreadyInList = false;
		
		// get patient's ID
		String patientID = (String) patient.get("id");
		
		// get JsonObject representation of the doctor's account
		JsonObject doctor = Account.getAccountJSONObj("Doctor", doctorEmail);
		
		// get the JsonArray representation of the doctor's list of patients
		// and iterate through it with an iterator, checking if the patient's
		// ID is already in the list
		JsonArray patientList = (JsonArray) doctor.get("patients");
		Iterator i = patientList.iterator();
		
		while (i.hasNext()) {
			String nextIdInList = (String) i.next();
			if (nextIdInList.equals(patientID)) {
				alreadyInList = true;
				break;
			}
		}
		
		if (!alreadyInList) {	// if the patient is NOT in the list, then add the patient's ID to the doctor's list
			WriteToJSON.addPatientToDoc(patientID, doctorEmail);
		}
		
		return alreadyInList;
		
	}
	
}
