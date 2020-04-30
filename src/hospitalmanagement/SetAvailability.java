package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//libraries necessary to work with our JSON:
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class is used by doctors and nurses to set their own availability.
 * Availabilities are set by date, where the user has the ability to set it in
 * chunks. That is, he might say my availability is 10 am to 2 pm on 04/01, as well as
 * the upcoming 5 days. When the operation is carried out, the days 04/01 to
 * 04/06 will have their availability set as 10-14. This availability is stored
 * in accounts2.json as the user's "schedule" object.
 * 
 * @author Arthur
 *
 */
public class SetAvailability extends JFrame {

	private JPanel contentPane;
	private JTextField dateField;
	private JTextField startField;
	private JTextField finishField;
	private JCheckBox consecutiveCheckbox;
	private JTextField dayCount;

	/**
	 * This constructor creates the panel.
	 * 
	 * @param email       the login that identifies the user
	 * @param accountType identifies which kind of account has accessed this panel.
	 *                    the integer is the index of the account type object in the
	 *                    "accounts" array of accounts2.json.
	 */
	public SetAvailability(String email, int accountType) {

		// set the frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 816, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add the panel's main label
		JLabel mainLabel = new JLabel("Set Availability");
		mainLabel.setBounds(329, 86, 114, 22);
		mainLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(mainLabel);

		// add label to help user enter hours in correct form
		JLabel lblPleaseEnterStarting = new JLabel(
				"Please enter starting and finishing hour as hours from 0 to 24 (e.g. start: 9, finish: 15)");
		lblPleaseEnterStarting.setBounds(164, 135, 495, 16);
		contentPane.add(lblPleaseEnterStarting);

		// label for date
		JLabel dateLabel = new JLabel("Date (MM/DD):");
		dateLabel.setBounds(90, 194, 86, 16);
		contentPane.add(dateLabel);

		// textfield for date entry
		dateField = new JTextField();
		dateField.setBounds(280, 191, 397, 22);
		contentPane.add(dateField);
		dateField.setColumns(10);

		// label for starting hours
		JLabel startLabel = new JLabel("Starting Hour:");
		startLabel.setBounds(90, 229, 81, 16);
		contentPane.add(startLabel);

		// textfield for starting hour entry
		startField = new JTextField();
		startField.setBounds(280, 226, 397, 22);
		startField.setColumns(10);
		contentPane.add(startField);

		// label for finishing hours
		JLabel finishLabel = new JLabel("Finishing Hour:");
		finishLabel.setBounds(90, 264, 86, 16);
		contentPane.add(finishLabel);

		// textfield for finishing hour entry
		finishField = new JTextField();
		finishField.setBounds(280, 261, 397, 22);
		finishField.setColumns(10);
		contentPane.add(finishField);

		// label for asking user how many consecutive days they would like to set
		// the given availability for
		JLabel moreDaysLabel = new JLabel("How many more days:");
		moreDaysLabel.setBounds(90, 329, 129, 16);
		contentPane.add(moreDaysLabel);
		moreDaysLabel.setVisible(false);

		// give the user the option to set this availability for consecutive days
		consecutiveCheckbox = new JCheckBox("Set schedule for following days");
		consecutiveCheckbox.setBounds(360, 292, 207, 25);
		consecutiveCheckbox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (consecutiveCheckbox.isSelected()) {
					moreDaysLabel.setVisible(true);
					dayCount.setVisible(true);
				} else {
					moreDaysLabel.setVisible(false);
					dayCount.setVisible(false);
				}
			}
		});
		contentPane.add(consecutiveCheckbox);

		// text field for user to enter how many days
		dayCount = new JTextField();
		dayCount.setBounds(280, 326, 397, 22);
		dayCount.setColumns(10);
		contentPane.add(dayCount);
		dayCount.setVisible(false);

		// button for user to confirm their actions
		JButton confirmButton = new JButton("Confirm");
		confirmButton.setBounds(280, 387, 86, 25);
		confirmButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				String date = dateField.getText(); // the date the user wants to set their availability for
				String start = startField.getText(); // the start hour of their availability
				String finish = finishField.getText(); // the end hour of their availabilty

				// check that all fields were filled in (although not necessarily correctly/no error checking yet)
				if (date.length() > 0 && start.length() > 0 && finish.length() > 0) {

					// if consecutive days checkbox was not checked (only setting availability for one day)
					if (!consecutiveCheckbox.isSelected()) {

						// create a single element string array containing the given date
						String[] day = new String[] { date };
						// set the availability for this date
						set(email, accountType, day, start + "/" + finish);
						// dispose();

					} else { // else if more than one day is being set

						// split the date so that halves[0] = MM and halves[1] = DD
						String[] halves = date.split("/");
						int month = Integer.parseInt(halves[0]); 		// get int of the month of the given date
						int day = Integer.parseInt(halves[1]); 			// get int of the day of the given date
						int q = Integer.parseInt(dayCount.getText()); 	// get int of how many more days to set availability
						// get an array of all the dates that need to have their availability set
						String[] days = getDays(month, day, q);
						// set the availability for these days
						set(email, accountType, days, start + "/" + finish);
						// dispose();
					}
				} else { // if not all fields were filled in, send the user a message
					Login lframe = new Login();
					JOptionPane.showMessageDialog(lframe, "Please fill in all fields.");
					// return;
				}
			}
		});
		contentPane.add(confirmButton);

		// if user presses cancel button, close the set availability window
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(417, 387, 86, 25);
		contentPane.add(cancelButton);
	}

	/**
	 * Helper method. It returns the String form of the account
	 * type given as an integer that corresponds to the index of the accountType in
	 * the array within accounts2.json. This method was written for the sake of
	 * readability.
	 * 
	 * @param i the integer index of the account type in the accounts array of the
	 *          accounts2 json
	 * @return the String of the account type
	 */
	public static String findType(int i) {
		if (i == 0) {
			return "patient";
		} else if (i == 1) {
			return "doctor";
		} else if (i == 2) {
			return "nurse";
		} else if (i == 3) {
			return "assistant";
		} else if (i == 4) {
			return "administrator";
		} else {
			// notify the tester a bad request was made
			System.out.println("Invalid index. There's no such index in accounts2.json. Index was: " + i);
			return null;
		}
	}

	/**
	 * Helper method for getDays() and dayExists().
	 * Takes in a number, and returns the month that it is equivalent to it.
	 * @param i The integer representation of the month
	 * @return the String representation of the month
	 */
	public static String getMonthString(int i) {
		if (i == 1) {
			return "january";
		} else if (i == 2) {
			return "february";
		} else if (i == 3) {
			return "march";
		} else if (i == 4) {
			return "april";
		} else if (i == 5) {
			return "may";
		} else if (i == 6) {
			return "june";
		} else if (i == 7) {
			return "july";
		} else if (i == 8) {
			return "august";
		} else if (i == 9) {
			return "september";
		} else if (i == 10) {
			return "october";
		} else if (i == 11) {
			return "november";
		} else if (i == 12) {
			return "december";
		} else {
			System.out.println(i + " is not a month");
			return null;
		}
	}

	/** 
	 * This method is used by getDays().
	 * Checks whether a month has passed in the range from starting to starting + q.
	 * It refers to the dates.json to see the number of days the month has.
	 * @param day The integer representation of the day that may roll over to the next month.
	 * @param month The integer representation of the ("current") month
	 * @return
	 */
	private Boolean dayExists(int day, int month) {
		try {
			// reader to read dates.json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/dates.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			
			// get the object with latest day of each month for the current year
			JsonObject year = (JsonObject) parser.get("2020");
			// get the String representation of the given month
			String ms = getMonthString(month);
			// get the number of days in that month
			String monthSizeString = (String) year.get(ms);
			// get the integer value of the number of days in the month
			int max = Integer.parseInt(monthSizeString);
			// if the passed day is greater than the number of days in that month
			if (day > max) {
				return false;
			}
			// else if the day falls within the month
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong, presumably dates.json couldn't be opened");
			return null;
		}
	}

	/**
	 * This method takes in the information regarding a specific day of the year,
	 * and returns a string array containing the date of this day, as well as the
	 * next q days after that.
	 * @param month The month of the starting day, in numbers.
	 * @param day The day of the starting day, in numbers.
	 * @param q The number of additional days.
	 * @return a String array of all the days to be added in mm/dd form
	 */
	private String[] getDays(int month, int day, int q) {
		// create the array for the set of days (1 + q more days)
		String[] arr = new String[q + 1];
		
		// i should be 0 if no extra days are to be set, keep this in mind when looking at range.
		// for the entire range of days, not including the first, which has already been
		// added to the array, add their numerical date to the string array
		// to be returned in the form mm/dd
		for (int i = 0; i <= q; i++) {
			
			if (!dayExists(day, month)) {
				// we have crossed the latest day of the given month
				// move to the first day of the next month
				month += 1;
				day = 1;
			}
			String s = "";
			if (month < 10) {
				// if month has a single digit, add a 0 prefix
				// FIXME
				s += "0";
			}
			s += month + "/";
			if (day < 10) {
				// if day has a single digit, add a 0 prefix
				s += "0";
			}
			s += day;
			//System.out.println("finally s is: " + s);
			arr[i] = s;
			day += 1;
		}
		return arr;
	}

	/**
	 * This method was written for the sake of readability. It is called in the event handler
	 * of confirmButton, and it performs the functionality of this class, that is, set an availability.
	 * @param email The email of this user.
	 * @param accountType The account type of this user (in array integer index form)
	 * @param days The string array of days (in mm/dd format) whose schedule will be set
	 * @param time The String of the availability being set.
	 * 				model: 10:20/14:30 or 10/12 (either is valid, only use colon if necessary)
	 */
	private void set(String email, int accountType, String[] days, String time) {
		try {
			// reader to read accounts2.json
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the JsonArray of accounts within accounts2.json, for that is where the JsonObject of our account is
			JsonArray accounts = (JsonArray) parser.get("accounts");
			// get the JsonObject of this user's accountType
			JsonObject userType = (JsonObject) accounts.get(accountType);
			// get the JsonArray of this account type
			JsonArray accountTypeArr = Account.getAccountJSONObj(findType(accountType));
			//close the reader
			reader.close();
			
			// get the JsonObject of this particular account
			JsonObject account = Account.getAccountJSONObj(findType(accountType), email);
			// get the schedule object of this user
			JsonObject schedule = (JsonObject) account.get("schedule");
			// get the array index of the account in it's accountTypeArr
			int arrIndex = accountTypeArr.indexOf(account);
			
			// add all of the day/time pairs to the schedule object
			for (int i = 0; i < days.length; i++) {
				schedule.put(days[i], time);
			}

			// put the updated schedule back into the account
			account.put("schedule", schedule);
			// put the account back into it's usertype array
			accountTypeArr.set(arrIndex, account);
			// put this updated account type array as the account type obj
			userType.put(findType(accountType), accountTypeArr);
			// put this updated account type obj at the correct index of the accounts array
			accounts.set(accountType, userType);
			// put the updated accounts array as the account entry in the JSON
			parser.put("accounts", accounts);
			
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));
			// Write updates to JSON file
			Jsoner.serialize(parser, writer);
			// Close the writer
			writer.close();
			System.out.println("Account's schedule is now: " + schedule);
			Login lframe = new Login();
			JOptionPane.showMessageDialog(lframe, "Successful.");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong in set()");
			Login lframe = new Login();
			JOptionPane.showMessageDialog(lframe, "Not successful.");
		}
	}

} // end of class
