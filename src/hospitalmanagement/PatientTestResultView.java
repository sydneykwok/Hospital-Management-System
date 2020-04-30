package hospitalmanagement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.Color;
import java.awt.Font;

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
import com.github.cliftonlabs.json_simple.Jsoner;

/**
 * Class that displays the patient's tests so that
 * they can select one and view those test results.
 * 
 * @author sydneykwok
 */
public class PatientTestResultView extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame. This is for when the Patient clicks on the "View test
	 * results" button. Here, the Patient can select a test to see more info about.
	 * 
	 * @param email The email of the patient. Used to uniquely identify the user so
	 *              we can easily access their info.
	 */
	public PatientTestResultView(String email) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add a label to the panel to prompt the user to select a test
		JLabel lblselectTest = new JLabel("Please Select A Test");
		lblselectTest.setBounds(146, 13, 125, 16);
		contentPane.add(lblselectTest);

		// get the arraylist of this user's test info
		ArrayList<String> myTestInfo = getMyTests(email);

		// create a list of these tests
		List<String> values = new ArrayList<>(myTestInfo.size());
		for (int j = 0; myTestInfo.size() > j; j++) {
			values.add(myTestInfo.get(j));
		}

		// add the list of tests to the panel
		JList<Object> testList = new JList<Object>(values.toArray(new String[values.size()]));
		testList.setValueIsAdjusting(true);
		testList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		testList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		testList.setBounds(64, 34, 315, 161);
		JScrollPane patientListScroller = new JScrollPane();
		patientListScroller.setLocation(50, 33);
		patientListScroller.setSize(330, 169);
		patientListScroller.setViewportView(testList);
		testList.setLayoutOrientation(JList.VERTICAL);
		contentPane.add(patientListScroller);

		// add a button to the panel to allow the user to confirm their actions
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int selectedIndex = testList.getSelectedIndex();
				// if the user has not made a selection, give them a pop-up
				if (selectedIndex < 0) {
					JOptionPane.showMessageDialog(contentPane, "Please select a test.");
				} else {	// else, show the test results on this new pane
					String temp = (myTestInfo.get(selectedIndex)).substring(4);
					String id = temp.substring(0, 2);
					PatientTestResultInfoView info = new PatientTestResultInfoView(email, id);
					info.setVisible(true);
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

	/**
	 * This method returns an ArrayList of info for all of this user's tests.
	 * 
	 * @param email: the patient's email
	 * @return an ArrayList of info strings for all of this user's tests
	 */
	private ArrayList<String> getMyTests(String email) {
		// declare the arraylist
		ArrayList<String> tests = new ArrayList<String>();
		try {
			// reader to read tests json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/tests.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the json array of tests
			JsonArray myTests = (JsonArray) parser.get("tests");

			Iterator i = myTests.iterator();
			JsonObject test;
			int arrIndex = 0;

			// iterate through the json array of tests
			while (i.hasNext()) {
				// get the json object of the test
				test = (JsonObject) i.next();
				// get the email of the patient of this test
				String patient = (String) test.get("patient");
				// compare that email to this user's email
				if (patient.equals(email)) {
					// get the json object of the test
					test = (JsonObject) myTests.get(arrIndex);
					// create a string of some of the test info and add it to the arraylist
					tests.add("id: " + test.get("id") + ", type: " + test.get("type"));
				}
				// increment the array index counter
				arrIndex++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return the arraylist
		return tests;
	}
}
