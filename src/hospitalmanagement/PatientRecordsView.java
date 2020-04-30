package hospitalmanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class is run after the patient selects the "view records" button from the patient
 * perspective. The patient will now be able to select whether they would like to view
 * a list of all of their prescriptions, or a list of all their past, present, and future
 * appointments.
 * 
 * @author sydneykwok
 *
 */
public class PatientRecordsView extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Create the frame. This is for when the Patient clicks on the "View records"
	 * button. This constructor will give the patient the option to view their
	 * appointments or prescriptions from the hospital records.
	 * 
	 * @param email The email of the patient. Used to uniquely identify the user so
	 *              we can easily access their info.
	 */
	public PatientRecordsView(String email) {

		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// create panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add a label to the panel to prompt the user to make a selection
		JLabel lblselectView = new JLabel("What Would You Like to View?");
		lblselectView.setBounds(125, 56, 182, 16);
		contentPane.add(lblselectView);

		// add a button to the panel to allow the user to view their appointments
		JButton btnAppts = new JButton("My Appointments");
		btnAppts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// switch to the panel that shows all of the patient's appointments
				PatientAppointmentView appts = new PatientAppointmentView(email);
				appts.setVisible(true);
				dispose();
			}
		});
		btnAppts.setBounds(143, 105, 137, 25);
		contentPane.add(btnAppts);

		// add a button to the panel to allow the user to view their prescriptions
		JButton btnPresciptions = new JButton("My Prescriptions");
		btnPresciptions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// switch to the panel that shows all of the patient's prescriptions
				PatientPrescriptionView p = new PatientPrescriptionView(email);
				p.setVisible(true);
				//dispose();
			}
		});
		btnPresciptions.setBounds(143, 162, 137, 25);
		contentPane.add(btnPresciptions);
	}
}
