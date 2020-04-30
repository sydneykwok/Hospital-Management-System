package hospitalmanagement;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Pane that shows the patient's basic information.
*
*/
public class DoctorPatientsBasicInfoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Creates the pane that shows the patient's basic information
	 * @param email The email of the doctor
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @param patient JsonObject representation of the patient in the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsBasicInfoView(String email, String patientIndex, JsonObject patient) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 435);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Gets the basic information of the patient needed
		ArrayList<Object> informations = getMedicalRecordBasicInfo(patientIndex);
		String fullName = (String) patient.get("first_name") + " / " + patient.get("last_name");
		String gender = (String) patient.get("gender");
		String age = (String) patient.get("age");
		String maritalstatus = (String) informations.get(0);
		String race = (String) informations.get(1);
		String problems = (String) informations.get(2);
		String location = (String) informations.get(3);
		@SuppressWarnings("unchecked")
		ArrayList<String> allergies = (ArrayList<String>) informations.get(4);
		contentPane.setLayout(null);
		
		// Creates and shows the labels for the patient's basic information
		JLabel lblName = new JLabel("Full Name (First / Last):");
		lblName.setBounds(12, 19, 137, 16);
		contentPane.add(lblName);
		
		JLabel lblFullname = new JLabel(fullName);
		lblFullname.setHorizontalAlignment(SwingConstants.CENTER);
		lblFullname.setBounds(216, 19, 168, 16);
		contentPane.add(lblFullname);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(67, 53, 27, 16);
		lblAge.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAge);
		
		JLabel lblShownAge = new JLabel(age);
		lblShownAge.setHorizontalAlignment(SwingConstants.CENTER);
		lblShownAge.setBounds(226, 56, 158, 13);
		contentPane.add(lblShownAge);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(58, 87, 46, 16);
		contentPane.add(lblGender);
		
		JLabel lblShowngender = new JLabel(gender);
		lblShowngender.setHorizontalAlignment(SwingConstants.CENTER);
		lblShowngender.setBounds(250, 88, 100, 15);
		contentPane.add(lblShowngender);
		
		JLabel lblMaritalStatus = new JLabel("Marital Status:");
		lblMaritalStatus.setBounds(39, 121, 84, 16);
		contentPane.add(lblMaritalStatus);
		
		JLabel lblShownmarital = new JLabel(maritalstatus);
		lblShownmarital.setHorizontalAlignment(SwingConstants.CENTER);
		lblShownmarital.setBounds(250, 120, 100, 15);
		contentPane.add(lblShownmarital);
		
		JLabel lblRace = new JLabel("Race:");
		lblRace.setBounds(64, 155, 33, 16);
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblRace);
		
		JLabel lblShownrace = new JLabel(race);
		lblShownrace.setHorizontalAlignment(SwingConstants.CENTER);
		lblShownrace.setBounds(240, 155, 120, 15);
		contentPane.add(lblShownrace);
		
		JLabel lblProblems = new JLabel("Problems:");
		lblProblems.setBounds(52, 204, 58, 16);
		contentPane.add(lblProblems);
		
		JTextPane txtpnShownproblems = new JTextPane();
		txtpnShownproblems.setBounds(153, 182, 317, 60);
		txtpnShownproblems.setEditable(false);
		txtpnShownproblems.setText(problems);
		contentPane.add(txtpnShownproblems);
		
		JLabel lblAllergies = new JLabel("Allergies:");
		lblAllergies.setBounds(54, 268, 54, 16);
		contentPane.add(lblAllergies);
		
		JList<Object> shownAllergies = new JList<>(allergies.toArray(new String [allergies.size()]));
		shownAllergies.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JScrollPane allergyScroller = new JScrollPane();
		allergyScroller.setBounds(153, 246, 317, 60);
		allergyScroller.setViewportView(shownAllergies);
		contentPane.add(allergyScroller);
		
		JLabel lblClinicLocation = new JLabel("Clinic Location:");
		lblClinicLocation.setBounds(38, 317, 86, 16);
		contentPane.add(lblClinicLocation);
		
		JLabel lblShownlocation = new JLabel(location);
		lblShownlocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblShownlocation.setBounds(210, 320, 200, 15);
		contentPane.add(lblShownlocation);
		
		// Button to close the window and return to the previous one
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(377, 344, 93, 25);
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		contentPane.add(btnReturn);
		
	}
	
	/**
	 * This method gets the patient's basic information in the medical_records.json
	 * with the help of their index
	 * @param patientIndex The index of the patient in the accounts2.json
	 * @return The JsonObject containing their basic information
	 * @author ggdizon
	 */
	private ArrayList<Object> getMedicalRecordBasicInfo(String patientIndex) {
		try {
			// create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/medical_records.json")));
			
			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			
			// read medicalrecords from json
			JsonArray medicalrecords = (JsonArray) parser.get("medicalrecords");
			
			// Extract the JsonObject representation of the patient's medical records
			JsonObject record = (JsonObject) medicalrecords.get(Integer.parseInt(patientIndex));
			
			// get the recorded index of the patient
			String recordedIndex = (String) record.get("patientIndex");
			
			// if recorded index does not match the supposed index, this is a bug
			if (!recordedIndex.equals(patientIndex)) {
				throw new Exception("BUG: Patient's index does not match the one in the records.");
			}
			
			// get the basic information of the patient
			String maritalstatus = (String) record.get("maritalstatus");
			String race = (String) record.get("race");
			String problems = (String) record.get("problems");
			String location = (String) record.get("location");
			ArrayList<String> allergies = new ArrayList<>();
			
			// get the JsonArray representation of the list of allergies
			// using an interator
			JsonArray allergyArr = (JsonArray) record.get("allergies");
			Iterator i = allergyArr.iterator();
			int index = 0;
			
			while (i.hasNext()) {
				i.next();
				allergies.add(allergyArr.getString(index));
				index++;
			}
			
			// adds the information as strings into a String ArrayList
			ArrayList<Object> infos = new ArrayList<>();
			infos.add(maritalstatus);
			infos.add(race);
			infos.add(problems);
			infos.add(location);
			infos.add(allergies);
			return infos;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}


}
