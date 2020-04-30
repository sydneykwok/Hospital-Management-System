package hospitalmanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Iterator;
import javax.swing.JOptionPane;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/**
 * This class is used in many instances when writing to the JSON files.
 * 
 * @author erinpaslawski, sydneykwok
 *
 */
public class WriteToJSON {

	/**
	 * This method adds the new account to our database.
	 * @param newAccount: the new account to be added
	 * @throws Exception if email given is already in system, throw an exception
	 */
	public static void addNewAccount(Account newAccount) throws Exception {
		
		// grab all the info from the new account
		String userType = newAccount.getAccountType();
		String firstName = newAccount.getFirstName();
		String lastName = newAccount.getLastName();
		String email = newAccount.getEmail();
		String gender = newAccount.getGender();
		String password = newAccount.getPassword();
		
		if (invalidRegistrationCredentials(email, userType)) {
			Register rframe = new Register();
			JOptionPane.showMessageDialog(rframe, "The email you have given is already in the system.");
			throw new Exception("Email already exists.");
		} else if (userType.equals("Administrator") || userType.equals("Assistant") || userType.equals("Doctor") || userType.equals("Nurse")) {
			// if the user is a staff member, add them to the json as such
			writeStaffAccountToJSON(userType, firstName, lastName, email, gender, password);
	    } else {
	    	// otherwise the user is a patient and we will add them as a patient
	    	writePatientAccountToJSON(firstName, lastName, newAccount.getAge(), email, gender, password);
	    }
	}

	/**
	 * This method writes the new account (of a user who is a patient) to the JSON.
	 * @param first the user's first name
	 * @param last the user's last name
	 * @param age the user's age
	 * @param email the user's email
	 * @param gender the user's gender
	 * @param user the user's username
	 * @param pass the user's password
	 */
	public static void writePatientAccountToJSON(String first, String last, int age, String email, String gender, String pass) {
		try {
		    // create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

		    // create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the patients section of the accounts array
		    // then get the array representation of that object
		    JsonObject patients = (JsonObject) accounts.get(0);
	    	JsonArray patientArr = (JsonArray) patients.get("patient");
	    	
	    	// Get most recent patient id
	    	JsonObject mostRecentPatient = (JsonObject) patientArr.get(patientArr.size()-1);	// get the most recently added patient

	    	// id is incremented from most recent patient's id
	    	String id = Integer.toString(Integer.parseInt((String)mostRecentPatient.get("id")) +1);
	    	
	    	//close reader
		    reader.close();
	    	
	    	// Create a new patient with the given new account parameters
			JsonObject newPatient = new JsonObject();

			newPatient.put("id", id);	
			newPatient.put("first_name", first);
			newPatient.put("last_name", last);
			newPatient.put("age", Integer.toString(age));
      
			newPatient.put("email", email);
			newPatient.put("gender", gender);
			newPatient.put("password", pass);
			JsonArray appointments = new JsonArray();
			newPatient.put("appointments", appointments);
			JsonArray prescriptions = new JsonArray();
			newPatient.put("prescriptions", prescriptions);
			
			// append new patient to patient list
			patientArr.add(newPatient);
			// put this updated patient array as the patient object
			patients.put("patient", patientArr);
			// put this updated patient object at index 0 of the accounts array
			accounts.set(0, patients);
			// put the updated accounts array as the account entry in the JSON
			parser.put("accounts", accounts);
		    
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();
			
			// create reader
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/medical_records.json")));
			
			// create parser
			parser = (JsonObject) Jsoner.deserialize(reader);
			
			// read medicalrecords array from json
			JsonArray medicalRecords = (JsonArray) parser.get("medicalrecords");
			
			reader.close();
			
			writeNewPatientMedicalRecordList(id, medicalRecords);
			
			writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/medical_records.json"));
			
			Jsoner.serialize(parser, writer);
			
			writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}

	/**
	 * This method writes the new account (of a user who is a staff member) to the JSON.
	 * @param userType: the user's account type (doctor, nurse, etc.)
	 * @param first the user's first name
	 * @param last the user's last name
	 * @param email the user's email
	 * @param gender the user's gender
	 * @param user the user's username
	 * @param pass the user's password
	 */
	public static void writeStaffAccountToJSON(String userType, String first, String last, String email, String gender, String pass) {
		try {
		    // create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

		    // create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the specified account type section of the accounts array
		    // then get the array representation of that object
		    JsonObject accountTypeObj;
		    JsonArray accountTypeArr;
		    String lowerCaseType;	// get the user type in lower case so it matches the JSON key names
		    int arrayIndex;
		    if (userType.equals("Administrator")) {
		    	accountTypeObj = (JsonObject) accounts.get(4);
		    	accountTypeArr = (JsonArray) accountTypeObj.get("administrator");
		    	lowerCaseType = "administrator";
		    	arrayIndex = 4;
		    } else if (userType.equals("Assistant")) {
		    	accountTypeObj = (JsonObject) accounts.get(3);
		    	accountTypeArr = (JsonArray) accountTypeObj.get("assistant");
		    	lowerCaseType = "assistant";
		    	arrayIndex = 3;
		    } else if (userType.equals("Doctor")) {
		    	accountTypeObj = (JsonObject) accounts.get(1);
		    	accountTypeArr = (JsonArray) accountTypeObj.get("doctor");
		    	lowerCaseType = "doctor";
		    	arrayIndex = 1;
		    } else {
		    	accountTypeObj = (JsonObject) accounts.get(2);
		    	accountTypeArr = (JsonArray) accountTypeObj.get("nurse");
		    	lowerCaseType = "nurse";
		    	arrayIndex = 2;
		    }
	    	
		    // get the most recently added person of that account type
	    	JsonObject mostRecentAccount = (JsonObject) accountTypeArr.get(accountTypeArr.size()-1);
	    	// get id from most recent person and increment to get new account's id
	    	String id = Integer.toString(Integer.parseInt((String) mostRecentAccount.get("id")) + 1);
	    	
	    	//close reader
		    reader.close();
	    	
	    	// Create a new Json Object with the given new account parameters
			JsonObject newAccount = new JsonObject();

			newAccount.put("id", id);	// id is incremented from most recent account's id

			newAccount.put("first_name", first);
			newAccount.put("last_name", last);
			newAccount.put("email", email);
			newAccount.put("gender", gender);
			newAccount.put("password", pass);
			newAccount.put("schedule","");
			
			if(userType.equals("Doctor")) {
				JsonArray appointments = new JsonArray();
				newAccount.put("appointments", appointments);
				
				JsonArray patients = new JsonArray();
				newAccount.put("patients", patients);
			}
			
			// get JsonObject of last user's schedule if doc or nurse
	    	if(userType.equals("Doctor") || userType.equals("Nurse")) {
	    		JsonObject schedule = new JsonObject();
				newAccount.put("schedule", schedule);
			}
			
			// append new patient to patient list
			accountTypeArr.add(newAccount);
			// put this updated patient array as the patient object
			accountTypeObj.put(lowerCaseType, accountTypeArr);
			// put this updated patient object at index 0 of the accounts array
			accounts.set(arrayIndex, accountTypeObj);
			// put the updated accounts array as the account entry in the JSON
			parser.put("accounts", accounts);
		    
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}

	private static boolean invalidRegistrationCredentials(String user, String userType) {
		boolean isInvalid = false;
		try {
		    // create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

		    // create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the account type from the accounts array
		    // then get the array representation of that account type
		    // then create an iterator to iterate through that array
		    Iterator i;
		    
		    if (userType.equals("Administrator")) {
		    	JsonObject administrators = (JsonObject) accounts.get(4);
		    	JsonArray adminArr = (JsonArray) administrators.get("administrator");
		    	i = adminArr.iterator();
		    } else if (userType.equals("Assistant")) {
		    	JsonObject assistants = (JsonObject) accounts.get(3);
		    	JsonArray assistantArr = (JsonArray) assistants.get("assistant");
		    	i = assistantArr.iterator();
		    } else if (userType.equals("Doctor")) {
		    	JsonObject doctors = (JsonObject) accounts.get(1);
		    	JsonArray doctorArr = (JsonArray) doctors.get("doctor");
		    	i = doctorArr.iterator();
		    } else if (userType.equals("Nurse")) {
		    	JsonObject nurses = (JsonObject) accounts.get(2);
		    	JsonArray nurseArr = (JsonArray) nurses.get("nurse");
		    	i = nurseArr.iterator();
		    } else {
		    	JsonObject patients = (JsonObject) accounts.get(0);
		    	JsonArray patientArr = (JsonArray) patients.get("patient");
		    	i = patientArr.iterator();
		    }
		    
		    // iterate through all pairs of usernames and passwords in the user type array
		    while (i.hasNext()) {
		    	
		        JsonObject account = (JsonObject) i.next();
		        String username = (String) account.get("email");
		        
		        // if the login credentials match a pair in the database, the input is valid!
		        if(user.equals(username)) {
		        	isInvalid = true;
		        }
		    }
		    
		    //close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		
		return isInvalid;
	}
	
	
	/**
	 * This method writes the doctor to the assigned department in the JSON.
	 * 
	 * @param username   the doctor's username
	 * @param department the department that is to be written to
	 * @author erinpaslawski
	 * @return false if failed, true otherwise
	 */
	public static int addDocToDepartment(String username, String department) {
		try {
			// create reader
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/departments.json")));

			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

			// read departments array from json
			JsonArray departments = (JsonArray) parser.get("departments");

			// close reader
			reader.close();

			// extract the object representation of the specified department type section of
			// the departments array
			// then get the array representation of that object
			JsonObject departmentTypeObj;
			JsonArray docArray;
			int arrayIndex;

			if (department.equals("Neurology")) {
				departmentTypeObj = (JsonObject) departments.get(0);
				arrayIndex = 0;
			} else if (department.equals("Cardiology")) {
				departmentTypeObj = (JsonObject) departments.get(1);
				arrayIndex = 1;
			} else {
				departmentTypeObj = (JsonObject) departments.get(2);
				arrayIndex = 2;
			}

			docArray = (JsonArray) departmentTypeObj.get("doctors");
			if (!docArray.contains(username)) {
				docArray.add(username);

				// put this updated doctor array as the doctors object
				departmentTypeObj.put("doctors", docArray);
				// put this updated doctors object at index of the accounts array
				departments.set(arrayIndex, departmentTypeObj);
				// put the updated accounts array as the account entry in the JSON
				parser.put("departments", departments);

				// Create a writer
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/departments.json"));

				// Write updates to JSON file
				Jsoner.serialize(parser, writer);

				// Close the writer
				writer.close();
				return 0;
			}
			else {
				return 1;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 2;
		}
	}
	
	public static void updatePatientInfo(String firstName,	String lastName, String age,
			String email, String gender, String password) {
		try {
			// create reader
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

			// create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the patients section of the accounts array
		    // then get the array representation of that object
		    JsonObject patients = (JsonObject) accounts.get(0);
	    	JsonArray patientArr = (JsonArray) patients.get("patient");
	    	
			// close reader
			reader.close();
			
			JsonObject user = Account.getAccountJSONObj("Patient", email);
			int arrayIndex = patientArr.indexOf(user);
			
			user.put("first_name", firstName);
			user.put("last_name", lastName);
			user.put("age", age);
			user.put("email", email);
			user.put("gender", gender);
			user.put("password", password);
			
			// update user in the patients array
			patientArr.set(arrayIndex, user);
			// put this updated patient array as the patient object
			patients.put("patient", patientArr);
			// put this updated patient object at index 0 of the accounts array
			accounts.set(0, patients);
			// put the updated accounts array as the account entry in the JSON
			parser.put("accounts", accounts);
		    
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	/**
	 * This method adds patient's ID to doctor's list of patients in the JSON when doctor
	 * wants to assign themselves as a patient's physician
	 * 
	 * @param patientID the ID of the patient to be added to the doctor's patient list
	 * @param doctorEmail the email of the doctor who wants to add a patient
	 * @author ggdizon
	 */
	public static void addPatientToDoc(String patientID, String doctorEmail) {
		try {
			// create reader
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

			// create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the doctors section of the accounts array
		    // then get the array representation of that object
		    JsonObject doctors = (JsonObject) accounts.get(1);
	    	JsonArray doctorArr = (JsonArray) doctors.get("doctor");
	    	
			// close reader
			reader.close();
			
			JsonObject user = Account.getAccountJSONObj("Doctor", doctorEmail);
			int arrayIndex = doctorArr.indexOf(user);
			JsonArray patientList = (JsonArray) user.get("patients");
			
			patientList.add(patientID);
			
			// update list of patients in the user
			user.put("patients", patientList);
			// update user in the doctors array
			doctorArr.set(arrayIndex, user);
			// put this updated doctor array as the doctor object
			doctors.put("patient", doctorArr);
			// put this updated doctor object at index 0 of the accounts array
			accounts.set(1, doctors);
			// put the updated accounts array as the account entry in the JSON
			parser.put("accounts", accounts);
		    
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	
	/**
	 * This method writes the test info to the assigned patient in the JSON.
	 * 
	 * @param staffType either doctor or nurse in a String
	 * @param staffEmail the email of the staff member submitting the info
	 * @param selectedPatientEmail the email of the patient
	 * @param typeOfTest the type of test (MRI, X-Ray, etc.)
	 * @param textToSubmit the actual String of text to write
	 * @author erinpaslawski
	 * @return false if failed, true otherwise
	 */
	public static boolean writeTestInfo(String staffType, String staffEmail, String selectedPatientEmail , String typeOfTest, String textToSubmit) {
		try {
			// create reader
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(new FileInputStream("src/hospitalmanagement/tests.json")));

			// create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read tests array from json
		    JsonArray testArray = (JsonArray) parser.get("tests");
	    	
		    // Get most recent patient test
	    	JsonObject mostRecentTest = (JsonObject) testArray.get(testArray.size()-1);	// get the most recently added test

	    	// id is incremented from most recent test's id
	    	String id = Integer.toString(Integer.parseInt((String)mostRecentTest.get("id")) +1);
		    
			// close reader
			reader.close();
			
			JsonObject newTest = new JsonObject(); 
			//add items to new JsonObject
			newTest.put("id", id);
			newTest.put("type", typeOfTest);
			newTest.put("staffType", staffType);
			newTest.put("staffName", staffEmail);
			newTest.put("patient", selectedPatientEmail);
			newTest.put("notes", textToSubmit);
			
			// update tests array
			testArray.add(newTest);

			// put the updated test array as the tests entry in the JSON
			parser.put("tests", testArray);
		    
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/tests.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();
			//return true if it works
			return true;

		} catch (Exception ex) {
			//return false if there are any exceptions thrown
		    return false;
		}
	}
	
	/**
	 * This method removes the approved appointment from the req_appointments.json and writes it to the
	 * appointments.json file. It also adds that appointment's ID number to the appointment arrays of the
	 * doctor and patient (in accounts2.json), as well as the appointment array of the department (in
	 * departments.json).
	 * 
	 * @param ID The ID number of the appointment in the req_appointment.json file.
	 * @param patientEmail The email of the patient being booked in the appointment.
	 * @param docEmail The email of the doctor being booked in the appointment.
	 * @param department The department (in title-case) for the appointment to be booked in.
	 * @param time The time of the appointment (start/end format).
	 * @param year The year of the appointment.
	 * @param date The date of the appointment (MM/DD).
	 * 
	 * @author shavonnetran, sydneykwok
	 */
	public static void writeApprovedAppointment(String ID, String patientEmail, String docEmail, String department, String time, String year, String date) {
		
		// put the appointment in the appointment json, and add the appointment
		// id to the appointment arrays of the doctor, patient, and department
		bookAppointment("Nurse", patientEmail, docEmail, department, time, year, date);
		
		// then remove the appointment from req appointment
		try {
			// Create reader
			BufferedReader reader = new BufferedReader(
			new InputStreamReader(new FileInputStream("src/hospitalmanagement/req_appointments.json")));

			// Create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
	
			// Read requested appointments array from JSON
		    JsonArray reqAppoints = (JsonArray) parser.get("requested_appointments");
		    
		    // find the specific appointment
		    JsonObject appointment = null;
		    for(int i = 0; i < reqAppoints.size(); i++) {
		    	appointment = (JsonObject) reqAppoints.get(i);
		    	if(appointment.get("number").equals(ID)) {
		    		break;
		    	}
		    }
	    		
			// Remove the appointment from request_appointments.json
			reqAppoints.remove(appointment);
			
			// put the updated appointment array back into the parser
			parser.put("requested_appointments", reqAppoints);
		    
			// Close reader
			reader.close();
			
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/req_appointments.json"));

			// Write the updates to the req_appointments.json file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	/**
	 * This method edits the JsonArray of the medicalrecords in the medical_records.json
	 * file. It adds the items needed to the medical records of the new patient registering.
	 * The items were examples taken from the source:
	 * {@link https://www.ahrq.gov/ncepcr/tools/pf-handbook/mod8-app-b-monica-latte.html} 
	 * 
	 * @param patientID the new patient's ID
	 * @param records the JsonArray of medical records that contains all the records of all patients
	 * @author ggdizon
	 */
	public static void writeNewPatientMedicalRecordList(String patientID, JsonArray records) {
		// create new JSON object in which the records will be put in
		JsonObject newRecord = new JsonObject();
		
		// add items to JsonObject
		newRecord.put("patientIndex", patientID);
		
		JsonArray notesArr = new JsonArray();
		newRecord.put("notes", notesArr);
		
		newRecord.put("maritalstatus", "NO VALUE");
		newRecord.put("race", "NO VALUE");
		newRecord.put("problems", "NO VALUE");
		
		JsonArray medsArr = new JsonArray();
		newRecord.put("medications", medsArr);
		
		JsonArray allergiesArr = new JsonArray();
		newRecord.put("allergies", allergiesArr);
		
		// array for things involving a physical of a patient
		JsonArray physicalArr = new JsonArray();
		// add items into JsonObject
		JsonObject physicalItems = new JsonObject();
		physicalItems.put("temp", "NO VALUE");
		physicalItems.put("pulse", "NO VALUE");
		physicalItems.put("rhythm", "NO VALUE");
		physicalItems.put("bp", "NO VALUE");
		physicalItems.put("height", "NO VALUE");
		physicalItems.put("weight", "NO VALUE");
		physicalItems.put("appearance", "NO VALUE");
		physicalItems.put("eyes", "NO VALUE");
		physicalItems.put("ENMT", "NO VALUE");
		physicalItems.put("respiratory", "NO VALUE");
		physicalItems.put("cardiovascular", "NO VALUE");
		physicalItems.put("skin", "NO VALUE");
		physicalItems.put("problems", "NO VALUE");
		physicalItems.put("impression", "NO VALUE");
		physicalItems.put("lastupdated", "NO VALUE");
		physicalItems.put("updatedby", "NO VALUE");
		physicalArr.add(physicalItems);	

		newRecord.put("physical", physicalArr);
		
		
		newRecord.put("plan", "NO VALUE");

		newRecord.put("testorders", "NO VALUE");
		
		newRecord.put("location", "Totally-Legit-And-Not-Imaginary Hospital");
		
		records.add(newRecord);
	}
	
	/**
	 * This method adds a new medication to a patient's medical records
	 * @param patientIndex The index of patient according to their position on the accounts2.json
	 * @param medicationName Name of the medication
	 * @param docName Name of the doctor prescribing the medication
	 * @param amount Amount of medication prescribed
	 * @param date Date of refill
	 * @author ggdizon
	 */
	public static void addNewMedication(String patientIndex, String medicationName, String docName, String amount, String date) {
		try {	
			// create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/medical_records.json")));
			
			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			
			// read medicalrecords array from json
			JsonArray medicalRecords = (JsonArray) parser.get("medicalrecords");
			
			reader.close();
			
			// JsonObject representation of the specified patient's medical records
			JsonObject record = (JsonObject) medicalRecords.get(Integer.parseInt(patientIndex));
			
			// get the JsonArray representation of the patient's medications
			JsonArray medications = (JsonArray) record.get("medications");
			
			// String of the medication info in the proper format
			String medicationInfo = medicationName + " / " + docName + " / " + amount + " / " + date;
			
			// Add the medication information into the JsonArray of the medications
			medications.add(medicationInfo);
			
			// set the new medications JsonArray as the new one in the JsonObject
			// of the patient's medical record
			record.put("medications", medications);
			
			// set the JsonObject of the patient's medical record as the new one
			// in the array of all JsonObjects of all patients' medical records
			medicalRecords.set(Integer.parseInt(patientIndex), record);
			
			// update the entire json of medical records to the new one by putting
			// the updated JsonArray
			parser.put("medicalrecords", medicalRecords);
			
			// create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/medical_records.json"));
			
			// updates the json file
			Jsoner.serialize(parser, writer);
			
			// close the writer
			writer.close();
			
			// create reader
			reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

			// create parser
		    parser = (JsonObject) Jsoner.deserialize(reader);

		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    
		    // extract the object representation of the patients section of the accounts array
		    // then get the array representation of that object
		    JsonObject patients = (JsonObject) accounts.get(0);
	    	JsonArray patientsArr = (JsonArray) patients.get("patient");
	    	
			// close reader
			reader.close();
			
			// get the JsonObject representation of the patient's account in the json
			JsonObject patient = (JsonObject) patientsArr.get(Integer.parseInt(patientIndex));
			
			// get the JsonArray representation of the patient's prescriptions
			JsonArray prescriptions = (JsonArray) patient.get("prescriptions");
			
			// add the newly prescribed medication to the array of prescriptions of the patient
			prescriptions.add(medicationName);
			
			// update the patient's account
			patient.put("prescriptions", prescriptions);
			
			// update the patients list
			patientsArr.set(Integer.parseInt(patientIndex), patient);
			
			// update the Object representation of the patients list
			patients.put("patient", patientsArr);
			
			// update the JsonArray of accounts
			accounts.set(0, patients);
			
			// update the parser
			parser.put("accounts", accounts);
			
			// open writer
			writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));
			
			// update the json file
			Jsoner.serialize(parser, writer);
			
			// close the writer
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * This method updated a patient's physical information in their medical records json
	 * @param patientIndex Index of the patient according to their position in the accounts2.json
	 * @param newPhysicalInfo JsonObject containing the new physical informations
	 */
	public static void setNewPhysicalInfo(String patientIndex, JsonObject newPhysicalInfo) {
		try {
			// create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/medical_records.json")));
			
			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			
			// read medicalrecords array from json
			JsonArray medicalRecords = (JsonArray) parser.get("medicalrecords");
			
			reader.close();
			
			// JsonObject representation of the specified patient's medical records
			JsonObject record = (JsonObject) medicalRecords.get(Integer.parseInt(patientIndex));
			
			// get the JsonArray representation of the patient's physical information
			JsonArray physical = (JsonArray) record.get("physical");
			
			// update the physical information JsonArray's with the newPhysicalInfo JsonObject
			physical.set(0, newPhysicalInfo);
			
			// update the JsonObject of the patient's physical medical record
			record.put("physical", physical);
			
			// update the JsonArray of all patient's medical record
			medicalRecords.set(Integer.parseInt(patientIndex), record);
			
			// update the parser
			parser.put("medicalrecords", medicalRecords);
			
			// open writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/medical_records.json"));
			
			// update the json file
			Jsoner.serialize(parser, writer);
			
			// close the writer
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
   /**
	 * Writes the appointment to the appropriate json files.
	 * 
	 * @param accountType The account type of the user confirming the appointment.
	 * @param patientEmail The email of the patient being booked in the appointment.
	 * @param docEmail The email of the doctor being booked in the appointment.
	 * @param department The department (in title-case) for the appointment to be booked in.
	 * @param time The time of the appointment (start/end format).
	 * @param year The year of the appointment.
	 * @param date The date of the appointment (MM/DD).
	 */
	public static void bookAppointment(String accountType, String patientEmail, String docEmail, String department,
			String time, String year, String date) {
		
		if(accountType.equals("Doctor") || accountType.equals("Nurse")) {
			/************
			 * when the doctor books the appointment we must
			 * 		(1) add this appointment to the APPOINTMENTS JSON file
			 * 		(2) add the appointment ID to the patient's appointment array in ACCOUNT json
			 * 		(3) add the appointment ID to the doctor's appointment array in ACCOUNT json
			 * 		(4) add the appointment ID to the department's appointment array in DEPARTMENT json
			 ****************/
			try {
				/******* (1) APPOINTMENT ADDED TO APPOINTMENTS JSON *********/
				// create reader for APPOINTMENT json
				BufferedReader appointmentReader = new BufferedReader(
						new InputStreamReader(new FileInputStream("src/hospitalmanagement/appointments.json")));

				// create parser for APPOINTMENT json
			    JsonObject appointmentParser = (JsonObject) Jsoner.deserialize(appointmentReader);

			    // read appointments array from json
			    JsonArray appointmentsArr = (JsonArray) appointmentParser.get("appointments");
		    	
			    // get the most recently added appointment
		    	JsonObject mostRecentAppointment = (JsonObject) appointmentsArr.get(appointmentsArr.size()-1);
		    	// get number from most recent and increment to get new appointment's number
		    	String IDNumber = Integer.toString(Integer.parseInt((String) mostRecentAppointment.get("number")) + 1);
			    
				// close reader
				appointmentReader.close();
				
				// create the new appointment obj
				JsonObject appointment = new JsonObject();

				appointment.put("number", IDNumber);
				appointment.put("patient_email", patientEmail);
				appointment.put("doctor_email", docEmail);
				appointment.put("department", department);
				appointment.put("time", time);
				appointment.put("year", year);
				appointment.put("date", date);
				
				// append new appointment to appointments array
				appointmentsArr.add(appointment);
				appointmentParser.put("appointments", appointmentsArr);
			    
				// Create a writer for APPOINTMENT json
				BufferedWriter appointmentWriter = new BufferedWriter(new FileWriter("src/hospitalmanagement/appointments.json"));

				// Write updates to APPOINTMENT json
				Jsoner.serialize(appointmentParser, appointmentWriter);

				// Close the writer
				appointmentWriter.close();
				
				/******* (2)/(3) APPOINTMENT ID ADDED TO PATIENT & DOCTOR APPOINTMENT ARRAYS IN ACCOUNT JSON *********/
				// create reader for ACCOUNT json
				BufferedReader accountReader = new BufferedReader(
						new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));

				// create parser for ACCOUNT json
			    JsonObject accountParser = (JsonObject) Jsoner.deserialize(accountReader);

			    // read accounts array from json
			    JsonArray accountsArr = (JsonArray) accountParser.get("accounts");
		    	
			    // get the patients object
			    JsonObject patientObj = (JsonObject) accountsArr.get(0);
			    // get the patient array
			    JsonArray patientArr = (JsonArray) patientObj.get("patient");
			    // get the object of this specific patient
			    JsonObject patient = Account.getAccountJSONObj("Patient", patientEmail);
			    // save index of patient in patientArr
			    int arrIndex = patientArr.indexOf(patient);
			    // get the appointments array of the patient
			    JsonArray patientAppts = (JsonArray) patient.get("appointments");
			    // add the IDNumber to the array
			    patientAppts.add(IDNumber);
			    // put the appointments array back into the patient object
			    patient.put("appointments", patientAppts);
				// put patient object back into patientArr
				patientArr.set(arrIndex, patient);
				// put patient array back in patients object
				patientObj.put("patient", patientArr);
			    
			    // get the doctors object
			    JsonObject doctorObj = (JsonObject) accountsArr.get(1);
			    // get the doctor array
			    JsonArray doctorArr = (JsonArray) doctorObj.get("doctor");
			    // get the object of this specific doctor
			    JsonObject doctor = Account.getAccountJSONObj("Doctor", docEmail);
			    // save index of doctor in doctorArr
			    arrIndex = doctorArr.indexOf(doctor);
			    // get the appointments array of the doctor
			    JsonArray doctorAppts = (JsonArray) doctor.get("appointments");
			    // create new object with id
			    JsonObject obj = new JsonObject();
			    obj.put("ID", IDNumber);
				// append new obj to appointments array
			    doctorAppts.add(obj);
			    // put doctor appointment array back into doctor object
				doctor.put("appointments", doctorAppts);
				// put doctor object back into doctorArr
				doctorArr.set(arrIndex, doctor);
				// put doctor array back in doctors object
				doctorObj.put("doctor", doctorArr);
				
				// put the patient and doctor objects back into the accounts array
				accountsArr.set(0, patientObj);
				accountsArr.set(1, doctorObj);
				
				// put the accountsArr back into the accountParser
				accountParser.put("accounts", accountsArr);
			    
				// Create a writer for ACCOUNT json
				BufferedWriter accountWriter = new BufferedWriter(new FileWriter("src/hospitalmanagement/accounts2.json"));

				// Write updates to ACCOUNT json
				Jsoner.serialize(accountParser, accountWriter);

				// Close the writer
				accountWriter.close();
				
				/******* (4) APPOINTMENT ID ADDED TO APPOINTMENT ARRAY IN DEPARTMENT JSON *********/
				
				// create reader for DEPARTMENT json
				BufferedReader departmentReader = new BufferedReader(
						new InputStreamReader(new FileInputStream("src/hospitalmanagement/departments.json")));

				// create parser for DEPARTMENT json
			    JsonObject departmentParser = (JsonObject) Jsoner.deserialize(departmentReader);

			    // read departments array from json
			    JsonArray departmentsArr = (JsonArray) departmentParser.get("departments");
			    
		    	// get the specific department object
			    JsonObject dept = null;
			    for (int i = 0; i < departmentsArr.size(); i++) {

					// get the i-th department object
					dept = (JsonObject) departmentsArr.get(i);
					// if the department type matches the given department String, we have the correct obj
					if(dept.get("type").equals(department)) {
						break;
					}
				}
			    // save index of dept in departmentsArr
			    arrIndex = departmentsArr.indexOf(dept);
			    
			    // get the department's appointment array
			    JsonArray deptAppts = (JsonArray) dept.get("appointments");
			    // close reader
				appointmentReader.close();
			    // add the IDNumber to the array
			    deptAppts.add(IDNumber);
			    // put the department's appointment array back into the dept object
			    dept.put("appointments", deptAppts);
				// put dept object back into departmentsArr
				departmentsArr.set(arrIndex, dept);
				// put departments array back in departments parser
				departmentParser.put("departments", departmentsArr);
			    
				// Create a writer for DEPARTMENT json
				BufferedWriter departmentWriter = new BufferedWriter(new FileWriter("src/hospitalmanagement/departments.json"));

				// Write updates to DEPARTMENT json
				Jsoner.serialize(departmentParser, departmentWriter);

				// Close the writer
				departmentWriter.close();

			} catch (Exception ex) {
			    ex.printStackTrace();
			}
			
		} else if(accountType.equals("Patient")) {
			// write appointment to requested appointments json
			writeRequestedAppointment(patientEmail, docEmail, department, time, year, date);
			
		} else {
			System.out.println("Invalid account type. Appointment could not be booked.");
		}
		
	}
	
	/**
	 * Write the appointment to the requested appointment json where it will have to be
	 * approved by an assistant before being officially booked.
	 * 
	 * @param patientEmail The email of the patient being booked in the appointment.
	 * @param docEmail The email of the doctor being booked in the appointment.
	 * @param department The department (in title-case) for the appointment to be booked in.
	 * @param time The time of the appointment (start/end format).
	 * @param year The year of the appointment.
	 * @param date The date of the appointment (MM/DD).
	 */
	private static void writeRequestedAppointment(String patientEmail, String docEmail, String department,
			String time, String year, String date) {
		// add this appointment to the requested appointments JSON file.
		try {
			// create reader
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/req_appointments.json")));

			// create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);

		    // read requested appointments array from json
		    JsonArray reqApptsArr = (JsonArray) parser.get("requested_appointments");
	    	
		    // get the most recently added requested appointment
	    	JsonObject mostRecent = (JsonObject) reqApptsArr.get(reqApptsArr.size()-1);
	    	// get number from most recent and increment to get new appointment's number
	    	String number = Integer.toString(Integer.parseInt((String) mostRecent.get("number")) + 1);
		    
			// close reader
			reader.close();
			
			// create the new appointment obj
			JsonObject appointment = new JsonObject();

			appointment.put("number", number);
			appointment.put("patient_email", patientEmail);
			appointment.put("doctor_email", docEmail);
			appointment.put("department", department);
			appointment.put("time", time);
			appointment.put("year", year);
			appointment.put("date", date);
			
			// append new appointment to requested appointments array
			reqApptsArr.add(appointment);
			parser.put("requested_appointments", reqApptsArr);
		    
			// Create a writer
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/hospitalmanagement/req_appointments.json"));

			// Write updates to JSON file
			Jsoner.serialize(parser, writer);

			// Close the writer
			writer.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
}
