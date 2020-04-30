package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.awt.Font;

/**
* Pane for viewing the patient's physical info.
*
*/
public class DoctorPatientsPhysicalInfoView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Creates the pane for viewing the patient's physical information
	 * @param email The email of the doctor
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsPhysicalInfoView(String email, String patientIndex) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 645, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(135, 206, 235));
		setContentPane(contentPane);
		
		// Get patient's physical information
		JsonObject physicalInfo = getPatientsPhysicalInfo(patientIndex);
		String temp = (String) physicalInfo.get("temp");
		String pulse = (String) physicalInfo.get("pulse");
		String rhythm = (String) physicalInfo.get("rhythm");
		String bp = (String) physicalInfo.get("bp");
		String height = (String) physicalInfo.get("height");
		String weight = (String) physicalInfo.get("weight");
		String appear = (String) physicalInfo.get("appearance");
		String eyes = (String) physicalInfo.get("eyes");
		String enmt = (String) physicalInfo.get("ENMT");
		String resp = (String) physicalInfo.get("respiratory");
		String cardio = (String) physicalInfo.get("cardiovascular");
		String skin = (String) physicalInfo.get("skin");
		String problems = (String) physicalInfo.get("problems");
		String impression = (String) physicalInfo.get("impression");
		String lastupdated = (String) physicalInfo.get("lastupdated");
		String updatedby = (String) physicalInfo.get("updatedby");
		contentPane.setLayout(null);
		
		// Add the labels for the physical information (and scroll bar if needed)
		JLabel lblPhysical = new JLabel("Physical Information");
		lblPhysical.setBounds(54, 15, 142, 17);
		lblPhysical.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblPhysical);
		
		JLabel lblTemp = new JLabel("Temperature:");
		lblTemp.setBounds(85, 41, 80, 16);
		lblTemp.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTemp);
		
		JTextArea txtTemp = new JTextArea(temp);
		txtTemp.setBounds(241, 38, 374, 22);
		txtTemp.setEditable(false);
		contentPane.add(txtTemp);
		
		JLabel lblPulse = new JLabel("Pulse:");
		lblPulse.setBounds(107, 67, 35, 16);
		contentPane.add(lblPulse);
		
		JTextArea txtPulse = new JTextArea(pulse);
		txtPulse.setBounds(241, 64, 374, 22);
		txtPulse.setEditable(false);
		contentPane.add(txtPulse);
		
		JLabel lblRhythm = new JLabel("Rhythm:");
		lblRhythm.setBounds(101, 93, 48, 16);
		contentPane.add(lblRhythm);
		
		JTextArea txtRhythm = new JTextArea(rhythm);
		txtRhythm.setBounds(241, 90, 374, 22);
		txtRhythm.setEditable(false);
		contentPane.add(txtRhythm);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure:");
		lblBloodPressure.setBounds(80, 120, 90, 16);
		contentPane.add(lblBloodPressure);
		
		JTextArea txtBP = new JTextArea(bp);
		txtBP.setBounds(241, 116, 374, 23);
		txtBP.setEditable(false);
		contentPane.add(txtBP);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(104, 146, 41, 16);
		contentPane.add(lblHeight);
		
		JTextArea txtHeight = new JTextArea(height);
		txtHeight.setBounds(241, 143, 374, 22);
		txtHeight.setEditable(false);
		contentPane.add(txtHeight);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(102, 172, 45, 16);
		contentPane.add(lblWeight);
		
		JTextArea txtWeight = new JTextArea(weight);
		txtWeight.setBounds(241, 169, 374, 22);
		txtWeight.setEditable(false);
		contentPane.add(txtWeight);
		
		JLabel lblAppearance = new JLabel("Appearance:");
		lblAppearance.setBounds(88, 208, 73, 16);
		contentPane.add(lblAppearance);
		
		JTextArea txtAppear = new JTextArea(appear);
		txtAppear.setEditable(false);
		JScrollPane appearanceScroller = new JScrollPane(txtAppear);
		appearanceScroller.setBounds(241, 195, 374, 42);
		contentPane.add(appearanceScroller);
		
		JLabel lblEyes = new JLabel("Eyes:");
		lblEyes.setBounds(109, 254, 31, 16);
		contentPane.add(lblEyes);
		
		JTextArea txtEyes = new JTextArea(eyes);
		txtEyes.setEditable(false);
		JScrollPane eyesScroller = new JScrollPane(txtEyes);
		eyesScroller.setBounds(241, 241, 374, 42);
		contentPane.add(eyesScroller);
		
		JLabel lblEarnosemouththroat = new JLabel("Ear/Nose/Mouth/Throat:");
		lblEarnosemouththroat.setBounds(55, 300, 140, 16);
		contentPane.add(lblEarnosemouththroat);
		
		JTextArea txtENMT = new JTextArea(enmt);
		txtENMT.setEditable(false);
		JScrollPane enmtScroller = new JScrollPane(txtENMT);
		enmtScroller.setBounds(241, 287, 374, 42);
		contentPane.add(enmtScroller);
		
		JLabel lblRespiratory = new JLabel("Respiratory:");
		lblRespiratory.setBounds(90, 346, 70, 16);
		contentPane.add(lblRespiratory);
		
		JTextArea txtResp = new JTextArea(resp);
		txtResp.setEditable(false);
		JScrollPane respScroller = new JScrollPane(txtResp);
		respScroller.setBounds(241, 333, 374, 42);
		contentPane.add(respScroller);
		
		JLabel lblCardiovascular = new JLabel("Cardiovascular:");
		lblCardiovascular.setBounds(80, 392, 89, 16);
		contentPane.add(lblCardiovascular);
		
		JTextArea txtCardio = new JTextArea(cardio);
		txtCardio.setEditable(false);
		JScrollPane cardioScroller = new JScrollPane(txtCardio);
		cardioScroller.setBounds(241, 379, 374, 42);
		contentPane.add(cardioScroller);
		
		JLabel lblSkin = new JLabel("Skin:");
		lblSkin.setBounds(110, 439, 29, 16);
		contentPane.add(lblSkin);
		
		JTextArea txtSkin = new JTextArea(skin);
		txtSkin.setEditable(false);
		JScrollPane skinScroller = new JScrollPane(txtSkin);
		skinScroller.setBounds(241, 425, 374, 43);
		contentPane.add(skinScroller);
		
		JLabel lblProblems = new JLabel("Problems:");
		lblProblems.setBounds(96, 485, 58, 16);
		contentPane.add(lblProblems);
		
		JTextArea txtProblems = new JTextArea(problems);
		txtProblems.setEditable(false);
		JScrollPane problemsScroller = new JScrollPane(txtProblems);
		problemsScroller.setBounds(241, 472, 374, 42);
		contentPane.add(problemsScroller);
		
		JLabel lblImpression = new JLabel("Impression:");
		lblImpression.setBounds(91, 531, 68, 16);
		contentPane.add(lblImpression);
		
		JTextArea txtImpression = new JTextArea(impression);
		txtImpression.setEditable(false);
		JScrollPane impressionScroller = new JScrollPane(txtImpression);
		impressionScroller.setBounds(241, 518, 374, 42);
		contentPane.add(impressionScroller);
		
		JLabel lblLastUpdated = new JLabel("Last Updated:");
		lblLastUpdated.setBounds(85, 567, 79, 16);
		contentPane.add(lblLastUpdated);
		
		JTextArea txtLastUpdated = new JTextArea(lastupdated);
		txtLastUpdated.setBounds(241, 564, 374, 22);
		txtLastUpdated.setEditable(false);
		contentPane.add(txtLastUpdated);
		
		JLabel lblUpdatedLastBy = new JLabel("Updated Last By:");
		lblUpdatedLastBy.setBounds(77, 593, 96, 16);
		contentPane.add(lblUpdatedLastBy);
		
		JTextArea txtUpdatedBy = new JTextArea(updatedby);
		txtUpdatedBy.setBounds(241, 590, 374, 22);
		txtUpdatedBy.setEditable(false);
		contentPane.add(txtUpdatedBy);
		
		// Button that closes the pane and returns to the previous one
		JButton btnReturn = new JButton("Return");
		btnReturn.setBounds(85, 616, 80, 25);
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		contentPane.add(btnReturn);
		
		// Button that brings user to a pane that allows them to update patient's physical information
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(375, 616, 103, 25);
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsPhysicalInfoEdit physInfoEdit = new DoctorPatientsPhysicalInfoEdit(email, patientIndex);
				physInfoEdit.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnUpdate);
		
	}
	
	/**
	 * This method gets the patient's physical information in the medical records
	 * @param patientIndex The patient's index according to their location in the accounts2.json
	 * @return JsonObject representation of their physical information in the medical_records.json
	 * @author ggdizon
	 */
	private JsonObject getPatientsPhysicalInfo(String patientIndex) {
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
			
			// get the JsonArray representation of the physical information data
			// and then get the object representation of the array
			JsonArray physicalInfoArr = (JsonArray) record.get("physical");
			JsonObject physicalInfoObj = (JsonObject) physicalInfoArr.get(0);
			
			return physicalInfoObj;			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

}
