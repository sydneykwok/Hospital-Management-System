package hospitalmanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;

/**
 * Displays the patient's prescriptions.
 * 
 * @author sydneykwok
 *
 */
public class PatientPrescriptionView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame. Here, the patient will be able to see a list of their
	 * prescribed drugs.
	 * 
	 * @param email The email of the patient. Used to uniquely identify the user so
	 *              we can easily access their info.
	 */
	public PatientPrescriptionView(String email) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add a label to the panel to let the user know what the panel is for
		JLabel lblmyPrescriptions = new JLabel("My Prescriptions");
		lblmyPrescriptions.setBounds(174, 13, 97, 16);
		contentPane.add(lblmyPrescriptions);

		// get a list of this user's prescriptions
		ArrayList<String> drugs = new ArrayList<>();
		//get the JsonObject of this patient
		JsonObject patient = Account.getAccountJSONObj("Patient", email);
		// get this patient's array of prescriptions
		JsonArray prescriptions = (JsonArray) patient.get("prescriptions");

		// iterate through the array of prescriptions and add them to the arraylist
		for (int i = 0; i < prescriptions.size(); i++) {
		    String p = prescriptions.getString(i);
		    drugs.add(p);
		}

		// create a list from the arraylist
		List<String> values = new ArrayList<>(drugs.size());
		for (int j = 0; drugs.size() > j; j++) {
			values.add(drugs.get(j));
		}

		// create a scrollable list of prescriptions
		JList<Object> pList = new JList<Object>(values.toArray(new String[values.size()]));
		pList.setValueIsAdjusting(true);
		pList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pList.setBounds(64, 34, 315, 161);
		JScrollPane patientListScroller = new JScrollPane();
		patientListScroller.setLocation(50, 36);
		patientListScroller.setSize(330, 169);
		patientListScroller.setViewportView(pList);
		pList.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(patientListScroller);

		// add a button to the panel to allow the user to return to the previous view
		JButton btnCancel = new JButton("Return");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(174, 215, 97, 25);
		contentPane.add(btnCancel);
	}
}
