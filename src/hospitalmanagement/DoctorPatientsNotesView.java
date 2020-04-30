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
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Pane that allows the doctor to view notes about a patient.
*
*/
public class DoctorPatientsNotesView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Creates the pane for viewing doctor notes about the patient
	 * @param email The email of the doctor
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsNotesView(String email, String patientIndex) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(135, 206, 235));
		setContentPane(contentPane);
		
		// Get list of notes for the patient in the medical records json
		List<String> notes = getPatientsNotes(patientIndex);
		// Number the list of notes
		for (int i = 0; i < notes.size(); i++) {
			String numberedList = (i+1) + ". " + notes.get(i);
			notes.set(i, numberedList);
		}
		contentPane.setLayout(null);
		
		// Title Label
		JLabel lblNewLabel = new JLabel("Patient Notes and Developments");
		lblNewLabel.setBounds(124, 19, 184, 16);
		contentPane.add(lblNewLabel);
		
		// JList of the notes about the patients
		JList<Object> notesList = new JList<>(notes.toArray(new String[notes.size()]));
		notesList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//TODO: possibly check for details if it's an appt note?
			}
		});
		notesList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.GRAY));
		JScrollPane notesListScroller = new JScrollPane();
		notesListScroller.setBounds(12, 60, 408, 152);
		notesListScroller.setViewportView(notesList);
		contentPane.add(notesListScroller);
		
		// Button to add another note for the patient
		JButton btnNewButton = new JButton("Add Notes");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				DoctorPatientsNotesAdd addNotesPane = new DoctorPatientsNotesAdd(email, patientIndex);
				addNotesPane.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(127, 216, 100, 25);
		contentPane.add(btnNewButton);
		
		// Button to leave the window and return to the previous one
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(228, 216, 93, 25);
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		contentPane.add(btnReturn);
		
	}
	
	/**
	 * This method gets the paitent's notes data as a String ArrayList
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @return ArrayList<String> of patient's notes
	 * @author ggdizon
	 */
	private ArrayList<String> getPatientsNotes(String patientIndex) {
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
			
			// get JsonArray representation of the "notes" section of the records
			JsonArray notesArr = (JsonArray) record.get("notes");
			
			// Make an String ArrayList containing the notes
			ArrayList<String> notes = new ArrayList<>();
			
			// use iterator to iterate through the notes in the json
			// and then add it to the ArrayList
			Iterator i = notesArr.iterator();
			int index = 0;
			
			while (i.hasNext()) {
				i.next();
				notes.add((String) notesArr.getString(index));
				index++;
			}
			
			return notes;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

}
