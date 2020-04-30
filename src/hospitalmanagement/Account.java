package hospitalmanagement;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/*
* Class that represents a user's acccount.
* It stores the user's information such as username and password.
*/
public class Account {

	private String accountType;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String gender;
	private String password;
	
	/**
	 * Constructor for accounts that accept an age (Patients).
	 * @param first: the user's first name
	 * @param last: the user's last name
	 * @param age: the user's age
	 * @param email: the user's email
	 * @param gender: the user's gender
	 * @param password: the user's password
	 */
	public Account(String accountType, String first, String last, int age, String email, String gender, String password) {
		this(accountType, first, last, email, gender, password);
		this.age = age;
	}
	
	/**
	 * Constructor for accounts that do not accept an age (Staff).
	 * @param first: the user's first name
	 * @param last: the user's last name
	 * @param email: the user's email
	 * @param gender: the user's gender
	 * @param password: the user's password
	 */
	public Account(String accountType, String first, String last, String email, String gender, String password) {
		this.accountType = accountType;
		this.firstName = first;
		this.lastName = last;
		this.email = email;
		this.gender = gender;
		this.password = password;
	}

	/*
	* This method is used to check the length of the password of an account
	* @param pass: the password for the account
	* @return boolean: returns true/false on whether the password length is greater than or equal to 16 characters
	*/
	public static boolean checkPassword(String pass) {
		if(pass.length() >= 16) {
			return true;
		}
		return false; 
	}

	/*
	* This is a getter method for the password
	* @return String: the password
	*/
	public String getPassword() {
		return password;
	}

	/*
	* This is a setter method for the password
	* @param password: the password for the account
	* @return boolean: returns true/false on whether the password length has been checked
	*/
	protected boolean setPassword(String password) {
		if(Account.checkPassword(password)) {
			this.password = password;
			return true;
		}
		return false;
	}

	/**
	 * This is a getter method for the first name.
	 * @return String: the user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This is a setter method for the user's first name.
	 * @param firstName: the name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This is a getter method for age.
	 * @return int: the user's age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * This is a setter method for the user's age.
	 * @param age: the age to be set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * This is a getter method for the last name.
	 * @return String: the user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * This is a setter method for the user's last name.
	 * @param lastName: the last name to be set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * This is a getter method for the user's gender.
	 * @return String: the user's gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * This is a setter method for the user's gender.
	 * @param gender: the gender to be set.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * This is a getter method for the user's email.
	 * @return String: the user's email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This is a setter method for the user's email.
	 * @param email: the email to be set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This is a getter method for the user's account type.
	 * @return accountType: the user's account type.
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * This is a setter method for the user's account type.
	 * @param accountType: the account type to be set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * This method takes an account type and email. It returns the JsonObject of the user
	 * with the passed account type and email.
	 * @param accountType: the type of account the user has
	 * @param email: the user's email
	 * @return account: the JsonObject of the account with the specified account type and email
	 */
	public static JsonObject getAccountJSONObj(String accountType, String email) {
		try {
			//reader to read accounts2.json
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			//parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			//we look for the JsonArray within accounts2.json, for that is where the JsonObject of our user is
			JsonArray accounts = (JsonArray) parser.get("accounts");
			
			Iterator i;
			if (accountType.equals("Administrator") || accountType.equals("administrator")) {
		    	JsonObject administrators = (JsonObject) accounts.get(4);
		    	JsonArray adminArr = (JsonArray) administrators.get("administrator");
		    	i = adminArr.iterator();
		    } else if (accountType.equals("Assistant") || accountType.equals("assistant")) {
		    	JsonObject assistants = (JsonObject) accounts.get(3);
		    	JsonArray assistantArr = (JsonArray) assistants.get("assistant");
		    	i = assistantArr.iterator();
		    } else if (accountType.equals("Doctor") || accountType.equals("doctor")) {
		    	JsonObject doctors = (JsonObject) accounts.get(1);
		    	JsonArray doctorArr = (JsonArray) doctors.get("doctor");
		    	i = doctorArr.iterator();
		    } else if (accountType.equals("Nurse") || accountType.equals("nurse")) {
		    	JsonObject nurses = (JsonObject) accounts.get(2);
		    	JsonArray nurseArr = (JsonArray) nurses.get("nurse");
		    	i = nurseArr.iterator();
		    } else {
		    	JsonObject patients = (JsonObject) accounts.get(0);
		    	JsonArray patientArr = (JsonArray) patients.get("patient");
		    	i = patientArr.iterator();
		    }
			
			JsonObject account = new JsonObject();
			int flag = 0;
			while(i.hasNext() && flag == 0) {
				account = (JsonObject) i.next();
				String currentEmail = (String) account.get("email");
				if(currentEmail.equals(email)) {
					flag = 1;
				}
			}
			reader.close();
			return account;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Helper method for patient book appointment.
	 * This method takes a username and the index of an account type in the accounts json.
	 * It returns the JsonObject of the user with the passed account type and username.
	 * @param accountIndex: the index of an account type in the accounts json
	 * @param username: the user's username
	 * @return account: the JsonObject of the account with the specified account type and username
	 */
	public static JsonObject getAccountJSONObjUsername(int accountIndex, String username) {
		try {
			//reader to read accounts2.json
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			//parser to parse the reader
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			//we look for the JsonArray within accounts2.json, for that is where the JsonObject of our user is
			JsonArray accounts = (JsonArray) parser.get("accounts");
			// get the object of the account type using the passed index
			JsonObject accountType = (JsonObject) accounts.get(accountIndex);
			
			// get the user type name from the passed index
			String userType;
			if (accountIndex == 0) {
		    	userType = "patient";
		    } else if (accountIndex == 1) {
		    	userType = "doctor";
		    } else if (accountIndex == 2) {
		    	userType = "nurse";
		    } else if (accountIndex == 3) {
		    	userType = "assistant";
		    } else {
		    	userType = "administrator";
		    }
			
			// get the array of the account type using the user type name
	    	JsonArray accountTypeArr = (JsonArray) accountType.get(userType);
	    	
	    	// create an iterator for the array
			Iterator i = accountTypeArr.iterator();
			
			JsonObject account = null;
			int flag = 0;
			
			// iterate through the array to find the account with the given username
			while(i.hasNext() && flag == 0) {
				account = (JsonObject) i.next();
				String currentUsername = (String) account.get("username");
				if(currentUsername.equals(username)) {
					flag = 1;
				}
			}
			// close the reader
			reader.close();
			// return the account object
			return account;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method takes an account type. It returns a JsonArray of the all accounts
	 * within the passed account type.
	 * @param accountType: the type of account the users has
	 * @return the JsonArray of the accounts with the specified account type
	 */
	public static JsonArray getAccountJSONObj(String accountType) {
		ArrayList<String> names = new ArrayList<String>();
		try {
			// create reader
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

			// read accounts array from json
			JsonArray accounts = (JsonArray) parser.get("accounts");

			// extract the object representation of the account type from the accounts array
			// then get the array representation of that account type
			// then create an iterator to iterate through that array
			
			JsonArray accountArrayReturn;
			if (accountType.equals("Administrator") || accountType.equals("administrator")) {
		    	JsonObject administrators = (JsonObject) accounts.get(4);
		    	accountArrayReturn = (JsonArray) administrators.get("administrator");
		    } else if (accountType.equals("Assistant") || accountType.equals("assistant")) {
		    	JsonObject assistants = (JsonObject) accounts.get(3);
		    	accountArrayReturn = (JsonArray) assistants.get("assistant");
		    } else if (accountType.equals("Doctor") || accountType.equals("doctor")) {
		    	JsonObject doctors = (JsonObject) accounts.get(1);
		    	accountArrayReturn = (JsonArray) doctors.get("doctor");
		    } else if (accountType.equals("Nurse") || accountType.equals("nurse")) {
		    	JsonObject nurses = (JsonObject) accounts.get(2);
		    	accountArrayReturn = (JsonArray) nurses.get("nurse");
		    } else {
		    	JsonObject patients = (JsonObject) accounts.get(0);
		    	accountArrayReturn = (JsonArray) patients.get("patient");
		    }

			// close reader
			reader.close();
			return accountArrayReturn;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
