package hospitalmanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

/**
 * This class is used by both doctors and nurses.
 * With this class, the user will be able to view their schedule.
 * 
 * @author erinpaslawski
 *
 */
public class ViewSchedulePerspective extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame. 
	 */
	public ViewSchedulePerspective(String email, int accountType, String today) {
		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 626, 436);
		setLocationRelativeTo(null);
		
		// create pane for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// add the main title label to the panel
		JLabel MainLabel = new JLabel("Schedule:");
		MainLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		MainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MainLabel.setBounds(212, 28, 203, 22);
		contentPane.add(MainLabel);
		
		//Adding button to return to account perspective home page
		JButton btnReturn = new JButton("Return");
		//Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Return back to the main page after clicking return button
				if(accountType == 1) {
					DoctorPerspective previousPane = new DoctorPerspective(email);
					previousPane.setVisible(true);
				} else {
					NursePerspective previousPane = new NursePerspective(email);
					previousPane.setVisible(true);
				}
				dispose();
			}
		});
		btnReturn.setBounds(253, 354, 136, 22);
		contentPane.add(btnReturn);
		
		// add 2 panels for the appointments to sit
		JPanel firstDay = new JPanel();
		firstDay.setBounds(82, 62, 467, 73);
		contentPane.add(firstDay);
		firstDay.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Date:");
		lblNewLabel.setBounds(6, 6, 209, 16);
		firstDay.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Time: ");
		lblNewLabel_1.setBounds(227, 6, 61, 16);
		firstDay.add(lblNewLabel_1);
		
		String dayOne = getSched(email, accountType, today);
		String dateOne = today;
		String timeOne;
		if (dayOne == null) {
			timeOne = "No Scheduled Time";
		}
		else {
			timeOne = convertTime(dayOne); 
		}
		
		String nextDay = getNextDate(dateOne);
		
		JLabel day_1 = new JLabel(dateOne);
		day_1.setBounds(6, 34, 61, 16);
		firstDay.add(day_1);
		
		JLabel label_3 = new JLabel(timeOne);
		label_3.setBounds(227, 34, 209, 16);
		firstDay.add(label_3);

		JPanel secondDay = new JPanel();
		secondDay.setLayout(null);
		secondDay.setBounds(82, 148, 467, 73);
		contentPane.add(secondDay);
		
		JLabel date_1 = new JLabel("Date:");
		date_1.setBounds(6, 6, 209, 16);
		secondDay.add(date_1);
		
		JLabel time_1 = new JLabel("Time: ");
		time_1.setBounds(227, 6, 61, 16);
		secondDay.add(time_1);
		
		String dayTwo = getSched(email, accountType, nextDay);
		String dateTwo = nextDay;
		String timeTwo;
		if (dayTwo == null) {
			timeTwo = "No Scheduled Time";
		}
		else {
			timeTwo = convertTime(dayTwo); 
		}
		
		String nextDay2 = getNextDate(dateTwo);
		
		JLabel lblNewLabel_2 = new JLabel(dateTwo);
		lblNewLabel_2.setBounds(6, 34, 61, 16);
		secondDay.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(timeTwo);
		lblNewLabel_3.setBounds(227, 34, 209, 16);
		secondDay.add(lblNewLabel_3);

		
		JPanel thirdDay = new JPanel();
		thirdDay.setLayout(null);
		thirdDay.setBounds(82, 233, 467, 73);
		contentPane.add(thirdDay);
		
		JLabel label_4 = new JLabel("Date:");
		label_4.setBounds(6, 6, 209, 16);
		thirdDay.add(label_4);
		
		JLabel label_5 = new JLabel("Time: ");
		label_5.setBounds(227, 6, 61, 16);
		thirdDay.add(label_5);
		
		String dayThree = getSched(email, accountType, nextDay2);
		String dateThree = nextDay2;
		String timeThree;
		if (dayThree == null) {
			timeThree = "No Scheduled Time";
		}
		else {
			timeThree = convertTime(dayThree); 
		}
		String nextScreenDay = getNextDate(dateThree);
		
		JLabel label_6 = new JLabel(nextDay2);
		label_6.setBounds(6, 34, 61, 16);
		thirdDay.add(label_6);
		
		JLabel label_7 = new JLabel(timeThree);
		label_7.setBounds(227, 34, 209, 16);
		thirdDay.add(label_7);
	
		// Add next button 
		JButton btnNext = new JButton("Next"); 
		//Add event handler for next button 
		btnNext.addMouseListener(new MouseAdapter() {
			@Override public void mouseReleased(MouseEvent e) {
				ViewSchedulePerspective nextPane = new ViewSchedulePerspective(email, accountType, nextScreenDay);
				nextPane.setVisible(true);
				dispose();
			} 
		});
		btnNext.setBounds(413, 354, 136, 22); contentPane.add(btnNext); 
	}
	
	
	/**
	 * Read from the account json to get the schedule of this user on the given date.
	 * 
	 * @param email account email
	 * @param accountType account type (2 = nurse)
	 * @param date current date to query for
	 * @return a string containing the time scheduled, or null if not scheduled
	 * @author erinpaslawski
	 */
	private String getSched(String email, int accountType, String date) {
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
			//close the reader
			reader.close();
			// get the JsonObject of this particular account
			JsonObject account = new JsonObject();
			if(accountType == 1) {
				account = Account.getAccountJSONObj("Doctor", email);
			} else {
				account = Account.getAccountJSONObj("Nurse", email);
			}
			// get the schedule object of this user
			JsonObject schedule = (JsonObject) account.get("schedule");
			// find if there is a schedule for todays date
			if (schedule.containsKey(date)) {
				String s = (String) schedule.get(date);
				return s;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method takes in the current date and returns the next date.
	 * 
	 * @param date current date
	 * @return date next date
	 * @author erinpaslawski
	 */
	private String getNextDate(String date) {
		String nextDate = "";
		try {
			// reader to read dates.json
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(new FileInputStream("src/hospitalmanagement/dates.json")));
			// parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			// get the current year
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
			LocalDateTime now = LocalDateTime.now();
			String yearToGet = dtf.format(now);
			
			//get JsonObject of current year
			JsonObject year = (JsonObject) parser.get(yearToGet);
			// current month from the inputed date
			int month =  Integer.parseInt(date.substring(0, 2));
			// String representation of month
			String monthString = SetAvailability.getMonthString(month);
			// int day (from input date)
			int day = Integer.parseInt(date.substring(3));
			// get the number of days in that month
			String monthSizeString = (String) year.get(monthString);
			// get the integer value of the number of days in the month
			int max = Integer.parseInt(monthSizeString);
			// if the passed day is greater than the number of days in that month
			if (day >= max) { // end of month
				if (!monthString.equals("december")) {
				    int nextMonth = month += 1;
					String s = "";
					if (nextMonth < 10) {
						// if month has a single digit, add a 0 prefix
						s += "0";
					}
					s += nextMonth + "/";
				    s  += "01";
				    return s;
				}
				else {
		            throw new Exception("End of Year");
				}
			}
			else {
				String s = "";
				if (month < 10) {
					// if month has a single digit, add a 0 prefix
					s += "0";
				}
				s += month + "/";
				day += 1;
				if (day < 10) {
					// if day has a single digit, add a 0 prefix
					s += "0";
				}
				s += day;
				return s;
			}
		} catch (Exception e) {
			System.out.println("Something went wrong, presumably dates.json couldn't be opened");
			return null;
		}
	}
	
	/**
	 * This method converts the "condensed" form we use to store times in the json
	 * into the form that we would like the time to be displayed in the GUI.
	 * 
	 * @param condensedTime the time from the json
	 * @return a string with the time to be displayed
	 * @author erinpaslawski
	 */
	public String convertTime(String condensedTime) {
		String[] times = condensedTime.split("/");
		String startTime = times[0];
		String endTime = times[1];
		String s = startTime + ":00 - " + endTime + ":00";
		return s;
	}
}