package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonObject;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Pane that gives the doctor the ability to view the medical records of the patient.
*
*/
public class DoctorPatientsMedicalRecord extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Creates the pane that gives the doctor the ability to view the medical records of the patient
	 * @param email The email of the doctor
	 * @param patient The JsonObject representation of the patient in the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsMedicalRecord(String email, JsonObject patient) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 380);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// Get patient's information
		// Get patient's name
		String last_name = (String) patient.get("last_name") + ", ";
		String first_name = (String) patient.get("first_name");
		
		// Get patient's index with their ID
		String id = (String) patient.get("id");
		int indexAsInt = Integer.parseInt(id) - 1;
		String index = Integer.toString(indexAsInt);
		contentPane.setLayout(null);
		
		// Creates and adds the labels for the different
		// kinds of medical records into the pane
		// as well as buttons to view them
		JLabel lblRecords = new JLabel("Records");
		lblRecords.setBounds(213, 12, 197, 32);
		contentPane.add(lblRecords);
		
		JLabel lblName = new JLabel(last_name + first_name);
		lblName.setBounds(87, 48, 48, 32);
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblName);
		
		JLabel lblBaasicInformation = new JLabel("Basic Information");
		lblBaasicInformation.setBounds(12, 84, 197, 32);
		lblBaasicInformation.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBaasicInformation);
		
		JButton btnViewBI = new JButton("View Basic Info");
		btnViewBI.setBounds(244, 88, 135, 25);
		btnViewBI.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				DoctorPatientsBasicInfoView basicInfoPanel = new DoctorPatientsBasicInfoView(email, index, patient);
				basicInfoPanel.setVisible(true);
			}
		});
		contentPane.add(btnViewBI);
		
		JLabel lblNotes = new JLabel("Notes");
		lblNotes.setBounds(12, 120, 197, 32);
		lblNotes.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNotes);
		
		JButton btnViewNotes = new JButton("View Notes");
		btnViewNotes.setBounds(254, 124, 113, 25);
		btnViewNotes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsNotesView notesPane = new DoctorPatientsNotesView(email, index);
				notesPane.setVisible(true);
			}
		});
		contentPane.add(btnViewNotes);
		
		JLabel lblPhysicalInformation = new JLabel("Physical Information");
		lblPhysicalInformation.setBounds(12, 156, 197, 32);
		lblPhysicalInformation.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPhysicalInformation);
		
		JButton btnViewPhysicalInfo = new JButton("View Physical Info");
		btnViewPhysicalInfo.setBounds(222, 160, 179, 25);
		btnViewPhysicalInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsPhysicalInfoView physInfoPane = new DoctorPatientsPhysicalInfoView(email, index);
				physInfoPane.setVisible(true);
			}
		});
		contentPane.add(btnViewPhysicalInfo);
		
		JLabel lblMedications = new JLabel("Medications");
		lblMedications.setBounds(12, 192, 197, 32);
		lblMedications.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMedications);
		
		JButton btnViewCurrentMedications = new JButton("View Current Medications");
		btnViewCurrentMedications.setBounds(213, 196, 197, 25);
		btnViewCurrentMedications.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsMedicationsView medicationsPane = new DoctorPatientsMedicationsView(email, index);
				medicationsPane.setVisible(true);
			}
		});
		contentPane.add(btnViewCurrentMedications);
		
		JLabel lblTestOrders = new JLabel("Test Orders");
		lblTestOrders.setBounds(12, 228, 197, 32);
		lblTestOrders.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTestOrders);
		
		
		JLabel lblMedicalRecord = new JLabel("Medical");
		lblMedicalRecord.setBounds(12, 12, 197, 32);
		lblMedicalRecord.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblMedicalRecord);
		
		JButton btnViewTestOrders = new JButton("View Test Orders");
		btnViewTestOrders.setBounds(233, 232, 158, 25);
		btnViewTestOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsTestOrdersView testOrderPane = new DoctorPatientsTestOrdersView(email, index);
				testOrderPane.setVisible(true);
			}
		});
		contentPane.add(btnViewTestOrders);
		
		// Button allowing the user to close the pane and return to the previous one
		JButton btnNewButton = new JButton("Return");
		btnNewButton.setBounds(244, 272, 135, 25);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsViewInformation docViewPatientPane = new DoctorPatientsViewInformation(email, indexAsInt);
				docViewPatientPane.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnNewButton);
	}
}
