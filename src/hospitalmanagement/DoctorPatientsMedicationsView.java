package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
* Pane for viewing patient's medication in the medical record
*
*/
public class DoctorPatientsMedicationsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * This creates the pane for viewing patient's medication in the medical record
	 * @param email The email of the doctor viewing the patient
	 * @param patientIndex The index of the patient according to their position in the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsMedicationsView(String email, String patientIndex) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(135, 206, 235));
		setContentPane(contentPane);
		
		// get the list of patient's medications
		List<String> medications = getPatientsMedications(patientIndex);
		// add numbering in front
		for (int i = 1; i < medications.size(); i++) {
			String numbered = (i) + ". " + medications.get(i);
			medications.set(i, numbered);
		}
		contentPane.setLayout(null);
		
		// Title label
		JLabel lblCurrentMedications = new JLabel("Current Medications");
		lblCurrentMedications.setBounds(159, 19, 114, 16);
		contentPane.add(lblCurrentMedications);
		
		// JList showing the patient's current medications
		JList<Object> medicationList = new JList<>(medications.toArray(new String[medications.size()]));
		medicationList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JScrollPane medicationListScroller = new JScrollPane(medicationList);
		medicationListScroller.setBounds(12, 60, 408, 152);
		contentPane.add(medicationListScroller);
		
		// Button that will put the user to the pane where they can add current medication to the patient
		JButton btnEdit = new JButton("Add");
		btnEdit.setBounds(94, 216, 71, 25);
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsMedicationsAdd addMedsPane = new DoctorPatientsMedicationsAdd(email, patientIndex);
				addMedsPane.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnEdit);
		
		// Button allowing the user to close this pane and return to the previous one
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(169, 216, 79, 25);
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		contentPane.add(btnReturn);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(252, 216, 90, 25);
		contentPane.add(btnRemove);

	}
	
	/**
	 * This method gets the list of patient's medications as an ArrayList
	 * @param patientIndex The patients index according to their location in the accounts2.json
	 * @return ArrayList<String> containig the medications name
	 * @author ggdizon
	 */
	private ArrayList<String> getPatientsMedications(String patientIndex) {
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
			
			// get the JsonArray representation of the medications in the json
			// and iterate through it using an iterator and store the data
			// into an ArrayList of Strings
			JsonArray medicationsArr = (JsonArray) record.get("medications");
			Iterator i = medicationsArr.iterator();
			ArrayList<String> medications = new ArrayList<>();
			int index = 0;
			
			while (i.hasNext()) {
				i.next();
				medications.add(medicationsArr.getString(index));
				index++;
			}
			
			// return the ArrayList containing the medications
			return medications;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
