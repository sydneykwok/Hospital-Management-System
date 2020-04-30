package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonObject;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.awt.Font;

/**
* Pane for editing the patient's physical info.
*
*/
public class DoctorPatientsPhysicalInfoEdit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Creates the pane for editing the patient's physical information
	 * @param email The email of the doctor
	 * @param patientIndex The index of the patient according to the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsPhysicalInfoEdit(String email, String patientIndex) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 645, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(135, 206, 235));
		setContentPane(contentPane);
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
		
		JTextArea txtTemp = new JTextArea("");
		txtTemp.setBounds(241, 38, 374, 22);
		contentPane.add(txtTemp);
		
		JLabel lblPulse = new JLabel("Pulse:");
		lblPulse.setBounds(107, 67, 35, 16);
		contentPane.add(lblPulse);
		
		JTextArea txtPulse = new JTextArea("");
		txtPulse.setBounds(241, 64, 374, 22);
		contentPane.add(txtPulse);
		
		JLabel lblRhythm = new JLabel("Rhythm:");
		lblRhythm.setBounds(101, 93, 48, 16);
		contentPane.add(lblRhythm);
		
		JTextArea txtRhythm = new JTextArea("");
		txtRhythm.setBounds(241, 90, 374, 22);
		contentPane.add(txtRhythm);
		
		JLabel lblBloodPressure = new JLabel("Blood Pressure:");
		lblBloodPressure.setBounds(80, 120, 90, 16);
		contentPane.add(lblBloodPressure);
		
		JTextArea txtBP = new JTextArea("");
		txtBP.setBounds(241, 116, 374, 23);
		contentPane.add(txtBP);
		
		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setBounds(104, 146, 41, 16);
		contentPane.add(lblHeight);
		
		JTextArea txtHeight = new JTextArea("");
		txtHeight.setBounds(241, 143, 374, 22);
		contentPane.add(txtHeight);
		
		JLabel lblWeight = new JLabel("Weight:");
		lblWeight.setBounds(102, 172, 45, 16);
		contentPane.add(lblWeight);
		
		JTextArea txtWeight = new JTextArea("");
		txtWeight.setBounds(241, 169, 374, 22);
		contentPane.add(txtWeight);
		
		JLabel lblAppearance = new JLabel("Appearance:");
		lblAppearance.setBounds(88, 208, 73, 16);
		contentPane.add(lblAppearance);
		
		JTextArea txtAppear = new JTextArea("");
		JScrollPane appearanceScroller = new JScrollPane(txtAppear);
		appearanceScroller.setBounds(241, 195, 374, 42);
		contentPane.add(appearanceScroller);
		
		JLabel lblEyes = new JLabel("Eyes:");
		lblEyes.setBounds(109, 254, 31, 16);
		contentPane.add(lblEyes);
		
		JTextArea txtEyes = new JTextArea("");
		JScrollPane eyesScroller = new JScrollPane(txtEyes);
		eyesScroller.setBounds(241, 241, 374, 42);
		contentPane.add(eyesScroller);
		
		JLabel lblEarnosemouththroat = new JLabel("Ear/Nose/Mouth/Throat:");
		lblEarnosemouththroat.setBounds(55, 300, 140, 16);
		contentPane.add(lblEarnosemouththroat);
		
		JTextArea txtENMT = new JTextArea("");
		JScrollPane enmtScroller = new JScrollPane(txtENMT);
		enmtScroller.setBounds(241, 287, 374, 42);
		contentPane.add(enmtScroller);
		
		JLabel lblRespiratory = new JLabel("Respiratory:");
		lblRespiratory.setBounds(90, 346, 70, 16);
		contentPane.add(lblRespiratory);
		
		JTextArea txtResp = new JTextArea("");
		JScrollPane respScroller = new JScrollPane(txtResp);
		respScroller.setBounds(241, 333, 374, 42);
		contentPane.add(respScroller);
		
		JLabel lblCardiovascular = new JLabel("Cardiovascular:");
		lblCardiovascular.setBounds(80, 392, 89, 16);
		contentPane.add(lblCardiovascular);
		
		JTextArea txtCardio = new JTextArea("");
		JScrollPane cardioScroller = new JScrollPane(txtCardio);
		cardioScroller.setBounds(241, 379, 374, 42);
		contentPane.add(cardioScroller);
		
		JLabel lblSkin = new JLabel("Skin:");
		lblSkin.setBounds(110, 439, 29, 16);
		contentPane.add(lblSkin);
		
		JTextArea txtSkin = new JTextArea("");
		JScrollPane skinScroller = new JScrollPane(txtSkin);
		skinScroller.setBounds(241, 425, 374, 43);
		contentPane.add(skinScroller);
		
		JLabel lblProblems = new JLabel("Problems:");
		lblProblems.setBounds(96, 485, 58, 16);
		contentPane.add(lblProblems);
		
		JTextArea txtProblems = new JTextArea("");
		JScrollPane problemsScroller = new JScrollPane(txtProblems);
		problemsScroller.setBounds(241, 472, 374, 42);
		contentPane.add(problemsScroller);
		
		JLabel lblImpression = new JLabel("Impression:");
		lblImpression.setBounds(91, 531, 68, 16);
		contentPane.add(lblImpression);
		
		JTextArea txtImpression = new JTextArea("");
		JScrollPane impressionScroller = new JScrollPane(txtImpression);
		impressionScroller.setBounds(241, 518, 374, 42);
		contentPane.add(impressionScroller);
		
		JLabel lblLastUpdated = new JLabel("Last Updated:");
		lblLastUpdated.setBounds(85, 567, 79, 16);
		contentPane.add(lblLastUpdated);
		
		JTextArea txtLastUpdated = new JTextArea("");
		txtLastUpdated.setBounds(241, 564, 374, 22);
		txtLastUpdated.setEditable(false);
		contentPane.add(txtLastUpdated);
		
		JLabel lblUpdatedLastBy = new JLabel("Updated Last By:");
		lblUpdatedLastBy.setBounds(77, 593, 96, 16);
		contentPane.add(lblUpdatedLastBy);
		
		JTextArea txtUpdatedBy = new JTextArea("");
		txtUpdatedBy.setBounds(241, 590, 374, 22);
		txtUpdatedBy.setEditable(false);
		contentPane.add(txtUpdatedBy);
		
		// Button that closes the pane and returns to the previous one
		JButton btnReturn = new JButton("Cancel");
		btnReturn.setBounds(80, 616, 90, 25);
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsPhysicalInfoView physInfoPane = new DoctorPatientsPhysicalInfoView(email, patientIndex);
				physInfoPane.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnReturn);
		
		// Button that brings user to a pane that allows them to update patient's physical information
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(375, 616, 102, 25);
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// JsonObject containing the patient's new information
				JsonObject physicalInfo = new JsonObject();
				// Variables for the patient's new physical information
				String temp = txtTemp.getText();
				String pulse = txtPulse.getText();
				String rhythm = txtRhythm.getText();
				String bp = txtBP.getText();
				String height = txtHeight.getText();
				String weight = txtWeight.getText();
				String appear = txtAppear.getText();
				String eyes = txtEyes.getText();
				String enmt = txtENMT.getText();
				String resp = txtResp.getText();
				String cardio = txtCardio.getText();
				String skin = txtSkin.getText();
				String problems = txtProblems.getText();
				String impression = txtImpression.getText();
				setPhysicalInfoJSONObj(physicalInfo, temp, pulse, rhythm, bp, height, weight, appear, eyes, enmt, resp, cardio, skin, problems, impression, email);
				WriteToJSON.setNewPhysicalInfo(patientIndex, physicalInfo);
				DoctorPatientsPhysicalInfoView physInfoPane = new DoctorPatientsPhysicalInfoView(email, patientIndex);
				physInfoPane.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnUpdate);
		
	}
	
	/**
	 * This method takes in the new physical information and stores it in a JsonObject
	 * @param physicalInfo JsonObject in which the data will be stored
	 * @param temp Temperature data
	 * @param pulse Pulse data
	 * @param rhythm Rhythm data
	 * @param bp Blood Pressure data
	 * @param height Height data
	 * @param weight Weight data
	 * @param appearance Appearance data
	 * @param eyes Eyes data
	 * @param enmt Ear, Nose, Mouth, Throat data
	 * @param respiratory Respiratory data
	 * @param cardiovascular Cardiovascular data
	 * @param skin Skin data
	 * @param problems Problems data
	 * @param impression Impressions data
	 * @param docEmail The doctor's email
	 * @author ggdizon
	 */
	private void setPhysicalInfoJSONObj(JsonObject physicalInfo, String temp, String pulse, String rhythm, String bp, String height,
										String weight, String appearance, String eyes, String enmt, String respiratory,
										String cardiovascular, String skin, String problems, String impression, String docEmail) {
		physicalInfo.put("temp", temp);
		physicalInfo.put("pulse", pulse);
		physicalInfo.put("rhythm", rhythm);
		physicalInfo.put("bp", bp);
		physicalInfo.put("height", height);
		physicalInfo.put("weight", weight);
		physicalInfo.put("appearance", appearance);
		physicalInfo.put("eyes", eyes);
		physicalInfo.put("ENMT", enmt);
		physicalInfo.put("respiratory", respiratory);
		physicalInfo.put("cardiovascular", cardiovascular);
		physicalInfo.put("skin", skin);
		physicalInfo.put("problems", problems);
		physicalInfo.put("impression", impression);
		physicalInfo.put("lastupdated", getDate());
		physicalInfo.put("updatedby", getDoctorName(docEmail));
	}
	
	/**
	 * Method for getting the current date
	 * @return String of date in the format DD/MM/YYYY
	 * @author ggdizon
	 */
	private String getDate() {
		// get the local date right now
		LocalDate date = LocalDate.now();
		
		int day = date.getDayOfMonth();		// day expressed as integer
		int month = date.getMonthValue();	// months expressed as integer
		int year = date.getYear();			// year expressed as integer
		
		String stringDay;					// day expressed as String
		if (day < 10) {
			stringDay = "0" + Integer.toString(day);	// prepend 0 to day if single digit
		} else {
			stringDay = Integer.toString(day);
		}
		
		String stringMonth;					// months expressed as String
		if (month < 10) {
			stringMonth = "0" + Integer.toString(month);	// prepend 0 to month if single digit
		} else {
			stringMonth = Integer.toString(month);
		}
		
		String stringYear = Integer.toString(year);	//year expressed as String
		
		String formattedDate = stringDay + "/" + stringMonth + "/" + stringYear;
		return formattedDate;
	}
	
	/**
	 * Method for getting doctor's name
	 * @param email The email of the doctor
	 * @return String of doctor's last name
	 * @author ggdizon
	 */
	private String getDoctorName(String email) {
		JsonObject doctor = Account.getAccountJSONObj("Doctor", email);
		String last_name = "Dr." + (String) doctor.get("last_name");
		return last_name;
	}
	

}
