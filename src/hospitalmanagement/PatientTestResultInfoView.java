package hospitalmanagement;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Class that displays the panel for the patients to view their test info and results.
 * @author sydneykwok
 *
 */
public class PatientTestResultInfoView extends JFrame {

	private JPanel contentPane;
	private JLabel lbltestIDNum_1;
	private JLabel lblTestTypeVal_1;
	private JLabel txtTestResults_1;

	/**
	 * Create the frame. This frame is the frame for when the Patient selects the
	 * test in the View Test pane. The Patient will be shown information about the
	 * specific test that was chosen in the previous pane.
	 * 
	 * @param email     The email of the patient (used as an identifier for
	 *                  reading/writing to JSON).
	 * @param id		The id number of the test.
	 */
	public PatientTestResultInfoView(String email, String id) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 328);

		// create the panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		// label for test results
		JLabel lblNewLabel = new JLabel("My Test Results");
		lblNewLabel.setBounds(159, 51, 90, 16);

		// label for test id number
		JLabel lblTestId = new JLabel("Test ID #:");
		lblTestId.setBounds(92, 100, 59, 16);

		// label for test type
		JLabel lblTestType = new JLabel("Test Type:");
		lblTestType.setBounds(89, 129, 62, 16);

		// label for test results
		JLabel lblTestResults = new JLabel("Test Results:");
		lblTestResults.setBounds(79, 159, 75, 16);
		
		// Get the test from the tests.json as a JsonObject
		try {
			// reader to read tests json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/tests.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the json array of tests
			JsonArray myTests = (JsonArray) parser.get("tests");
			
			// get the specified test
			Iterator i = myTests.iterator();
			JsonObject test = null;
			int arrIndex = 0;

			// iterate through the json array of tests
			while (i.hasNext()) {
				// get the json object of the test
				test = (JsonObject) i.next();
				// get the email of the patient of this test
				String idNum = (String) test.get("id");
				// compare that email to this user's email
				if (idNum.equals(id)) {
					// break out of the loop
					break;
				}
				arrIndex++;
			}

			// add the test info to the pane by reading from the json
			lbltestIDNum_1 = new JLabel((String) test.get("id"));
			lbltestIDNum_1.setBounds(180, 94, 189, 26);
			lblTestTypeVal_1 = new JLabel((String) test.get("type"));
			lblTestTypeVal_1.setBounds(180, 126, 159, 26);
			txtTestResults_1 = new JLabel("<html>" + (String) test.get("notes") + "</html>");	// used html to get line wrap
			txtTestResults_1.setBounds(180, 159, 189, 81);
			txtTestResults_1.setVerticalAlignment(SwingConstants.TOP);
			
			// close the reader
			reader.close();
		} catch (Exception e) {
			// if we get any exceptions, print them
			e.printStackTrace();
		}
		
		// add the components to the content pane
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(lblTestId);
		contentPane.add(lbltestIDNum_1);
		contentPane.add(lblTestResults);
		contentPane.add(lblTestType);
		contentPane.add(lblTestTypeVal_1);
		contentPane.add(txtTestResults_1);
	}
}
