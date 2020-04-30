package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
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
import javax.swing.JTextArea;

/*
* Class that allows the doctor to add notes about a patient.
*/
public class DoctorPatientsNotesAdd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Creates the pane for viewing doctor notes about the patient
	 * @param email The email of the doctor
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsNotesAdd(String email, String patientIndex) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(135, 206, 235));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Title Label
		JLabel lblNewLabel = new JLabel("Patient Notes and Developments");
		lblNewLabel.setBounds(124, 19, 184, 16);
		contentPane.add(lblNewLabel);
		
		// Text area to put notes into
		JTextArea newNotes = new JTextArea();
		newNotes.setLineWrap(true);
		newNotes.setBounds(12, 45, 408, 158);
		contentPane.add(newNotes);
		
		// Button to add another note for the patient
		JButton btnNewButton = new JButton("Add Notes");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String note = newNotes.getText();
				setPatientsNotes(patientIndex, note);
				DoctorPatientsNotesView notesViewPane = new DoctorPatientsNotesView(email, patientIndex);
				notesViewPane.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(127, 216, 100, 25);
		contentPane.add(btnNewButton);
		
		// Button to leave the window and return to the previous one
		JButton btnReturn = new JButton("Cancel");
		btnReturn.setBounds(228, 216, 93, 25);
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsNotesView notesViewPane = new DoctorPatientsNotesView(email, patientIndex);
				notesViewPane.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnReturn);

		
	}
	
	/**
	 * This method adds notes to patient's list of notes in the json
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @param notes The notes that needs to be added
	 * @author ggdizon
	 */
	private void setPatientsNotes(String patientIndex, String notes) {
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
			
			// update the notes array by adding the new note
			notesArr.add(notes);
			
			// update the medical records of the patient
			record.put("notes", notesArr);
			
			// update the array of medical records
			medicalrecords.set(Integer.parseInt(patientIndex), record);
			
			// update the entire json
			parser.put("medicalrecords", medicalrecords);
			
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/medical_records.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
