package unitTests;

import org.junit.Test;
import static org.junit.Assert.*;
import hospitalmanagement.*;

/**
* Test for Account class: tests for the user information required to create an account
* @author shavonnetran
*/
public class JUnit_Account {

	/**
	 * Tests the constructor with age for the Account class
	 */
	@Test
	public void test_ageConstructorAccount() {
		//Test setup, create Account object with parameters to test constructor
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 
		
		//Verification for the parameters of the constructor
		assertEquals("Expected user type of account to be Patient", "Patient", acc.getAccountType());
		assertEquals("Expected first name of account holder to be Erin", "Erin", acc.getFirstName());
		assertEquals("Expected last name of account holder to be Paslawski", "Paslawski", acc.getLastName()); 
		assertEquals("Expected age of account holder to be 20", 20, acc.getAge());  
		assertEquals("Expected email of account holder to be erinpaslawski@gmail.com", "erinpaslawski@gmail.com", acc.getEmail());
		assertEquals("Expected gender of account holder to be Female", "Female", acc.getGender());
		assertEquals("Expected password of account to be erin123", "erin123", acc.getPassword());
	}
	
	/**
	 * Test getAccountType using constructor with age  
	 * Tests if the getter returns the correct account type, in this case, Patient
	 */
	@Test
	public void test_getAccountType_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 
		
		//Verification
		assertEquals("Account type should be Patient", "Patient", acc.getAccountType());
	}
	
	/**
	 * Test getFirstName using constructor with age  
	 * Tests if the getter returns the correct first name, in this case, Erin
	 */
	@Test
	public void test_getFirstName_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 
		
		//Verification
		assertEquals("First name should be Erin", "Erin", acc.getFirstName());
	}
	
	/**
	 * Test getLastName using constructor with age  
	 * Tests if the getter returns the correct last name, in this case, Paslawski
	 */
	@Test
	public void test_getLastName_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 	
		
		//Verification
		assertEquals("Last name should be Paslawski", "Paslawski", acc.getLastName());
	}
	
	/**
	 * Test getAge using constructor with age  
	 * Tests if the getter returns the correct age, in this case, 20
	 */
	@Test
	public void test_getAge_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 	
		
		//Verification
		assertEquals("Age should be 20", 20, acc.getAge());
	}
	
	/**
	 * Test getEmail using constructor with age  
	 * Tests if the getter returns the correct email, in this case, erinpaslawski@gmail.com
	 */
	@Test
	public void test_getEmail_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 	
		
		//Verification
		assertEquals("Email should be erinpaslawski@gmail.com", "erinpaslawski@gmail.com", acc.getEmail());
	}
	
	/**
	 * Test getGender using constructor with age  
	 * Tests if the getter returns the correct gender, in this case, Female
	 */
	@Test
	public void test_getGender_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 	
		
		//Verification
		assertEquals("Gender should be Female", "Female", acc.getGender());
	}
	
	/**
	 * Test getPassword using constructor with age  
	 * Tests if the getter returns the correct password, in this case, erin123
	 */
	@Test
	public void test_getPassword_ageConstructor() {
		//Test setup
		Account acc = new Account("Patient", "Erin", "Paslawski", 20, "erinpaslawski@gmail.com", "Female", "erin123"); 	
		
		//Verification
		assertEquals("Password should be erin123", "erin123", acc.getPassword());
	}
	
	/**
	 * Tests the constructor without age for the Account class
	 */
	@Test
	public void test_noAgeConstructorAccount() {
		//Test setup, create Account object with parameters to test constructor
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 
		
		//Verification for the parameters of the constructor
		assertEquals("Expected user type of account to be Doctor", "Doctor", acc.getAccountType());
		assertEquals("Expected first name of account holder to be Sohaib", "Sohaib", acc.getFirstName());
		assertEquals("Expected last name of account holder to be Bajwa", "Bajwa", acc.getLastName());  
		assertEquals("Expected email of account holder to be sohaibbajwa@gmail.com", "sohaibbajwa@gmail.com", acc.getEmail());
		assertEquals("Expected gender of account holder to be Male", "Male", acc.getGender());
		assertEquals("Expected password of account to be sohaibb300", "sohaibb300", acc.getPassword());
	}
	
	/**
	 * Test getAccountType using constructor without age  
	 * Tests if the getter returns the correct account type, in this case, Doctor
	 */
	@Test
	public void test_getAccountType_noAgeConstructor() {
		//Test setup
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 	
		
		//Verification
		assertEquals("Account type should be Doctor", "Doctor", acc.getAccountType());
	}
	
	/**
	 *  Test getFirstName using constructor without age  
	 * Tests if the getter returns the correct first name, in this case, Sohaib
	 */
	@Test
	public void test_getFirstName_noAgeConstructor() {
		//Test setup
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 	
		
		//Verification
		assertEquals("First name should be Sohaib", "Sohaib", acc.getFirstName());
	}
	
	/**
	 * Test getLastName using constructor without age  
	 * Tests if the getter returns the correct last name, in this case, Bajwa
	 */
	@Test
	public void test_getLastName_noAgeConstructor() {
		//Test setup
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 	
		
		//Verification
		assertEquals("Last name should be Bajwa", "Bajwa", acc.getLastName());
	}
	
	/**
	 * Test getEmail using constructor without age  
	 * Tests if the getter returns the correct email, in this case, sohaibbajwa@gmail.com
	 */
	@Test
	public void test_getEmail_noAgeConstructor() {
		//Test setup
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 	
		
		//Verification
		assertEquals("Email should be sohaibbajwa@gmail.com", "sohaibbajwa@gmail.com", acc.getEmail());
	}
	
	/**
	 * Test getGender using constructor without age  
	 * Tests if the getter returns the correct gender, in this case, Male
	 */
	@Test
	public void test_getGender_noAgeConstructor() {
		//Test setup
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 	
		
		//Verification
		assertEquals("Gender should be Male", "Male", acc.getGender());
	}
	
	/**
	 * Test getPassword using constructor without age  
	 * Tests if the getter returns the correct password, in this case, sohaibb300
	 */
	@Test
	public void test_getPassword_noAgeConstructor() {
		//Test setup
		Account acc = new Account("Doctor", "Sohaib", "Bajwa", "sohaibbajwa@gmail.com", "Male", "sohaibb300"); 	
		
		//Verification
		assertEquals("Password should be sohaibb300", "sohaibb300", acc.getPassword());
	}
}
