package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This frame displays the list of patients the doctor has an appointment with.
 */
public class DoctorPatientsView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame. This is for when the Doctor clicks on the "View Patients"
	 * button. Here, the Doctor will be able to see a list of patients they have an
	 * appointment with.
	 * @param email The email of the doctor. Used to uniquely identify the user so we can easily access their info.
	 * @author ggdizon
	 */
	public DoctorPatientsView(String email) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ArrayList<String> names = new ArrayList<>();
		JsonArray patientAccounts = Account.getAccountJSONObj("Patient");
		Iterator i = patientAccounts.iterator();
		
		while(i.hasNext()) {
			JsonObject account = (JsonObject) i.next();
			String last_name = (String) account.get("last_name");
			String first_name = (String) account.get("first_name");
			names.add(last_name + ", " + first_name);
		}
		
		List<String> values = new ArrayList<>(names.size());
		for (int j = 0; names.size() > j; j++) {
			values.add(names.get(j));
		}
    
		// create a list of patients
		JList<Object> patientList = new JList<Object>(values.toArray(new String[values.size()]));
		patientList.setValueIsAdjusting(true);
		patientList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		patientList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		patientList.setBounds(64, 34, 315, 161);
		JScrollPane patientListScroller = new JScrollPane();
		patientListScroller.setLocation(50, 33);
		patientListScroller.setSize(330, 169);
		patientListScroller.setViewportView(patientList);
		patientList.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(patientListScroller);

		// add a label to the panel to prompt the user to select a patient
		JLabel lblPleaseSelectPatient = new JLabel("Please Select Patient");
		lblPleaseSelectPatient.setBounds(146, 13, 125, 16);
		contentPane.add(lblPleaseSelectPatient);

		// add a button to the panel to allow the user to confirm their actions
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int selectedIndex = patientList.getSelectedIndex();
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(contentPane, "Please select a patient.");
				} else {
				DoctorPatientsViewInformation patientInfoPane = new DoctorPatientsViewInformation(email, selectedIndex);
				patientInfoPane.setVisible(true);
				dispose();
				}
			}
		});
		btnConfirm.setBounds(104, 215, 97, 25);
		contentPane.add(btnConfirm);

		// add a button to the panel to allow the user to return to the previous view
		JButton btnCancel = new JButton("Return");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(236, 215, 97, 25);
		contentPane.add(btnCancel);
	}
}
