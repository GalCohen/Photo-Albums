package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class is responsible display and functionality for the Admin Login window. The Admin Login window allows a special user, 
 * "admin", to create additional users.
 * 
 * @author Gal 
 */
public class AdminLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5798218630123158670L;
	private JPanel contentPane;
	private JTextField txtEnterUseridHere;
	private JTextField txtEnterNameHere;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogin frame = new AdminLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlUsersList = new JPanel();
		pnlUsersList.setPreferredSize(new Dimension(10, 50));
		panel.add(pnlUsersList, BorderLayout.NORTH);
		
		JList userList = new JList();
		pnlUsersList.add(userList);
		
		JPanel pnlCreateUser = new JPanel();
		panel.add(pnlCreateUser, BorderLayout.CENTER);
		pnlCreateUser.setLayout(null);
		
		JLabel lblCreateANew = new JLabel("Create a New User:");
		lblCreateANew.setBounds(91, 17, 118, 16);
		pnlCreateUser.add(lblCreateANew);
		
		JLabel lblUserID = new JLabel("UserID:");
		lblUserID.setBounds(132, 45, 46, 16);
		pnlCreateUser.add(lblUserID);
		
		txtEnterUseridHere = new JTextField();
		txtEnterUseridHere.setBounds(182, 39, 134, 28);
		pnlCreateUser.add(txtEnterUseridHere);
		txtEnterUseridHere.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(138, 75, 40, 16);
		pnlCreateUser.add(lblName);
		
		txtEnterNameHere = new JTextField();
		txtEnterNameHere.setBounds(182, 69, 134, 28);
		pnlCreateUser.add(txtEnterNameHere);
		txtEnterNameHere.setColumns(10);
		
		final JLabel lblMessages = new JLabel("");
		lblMessages.setBounds(43, 124, 360, 16);
		pnlCreateUser.add(lblMessages);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setPreferredSize(new Dimension(10, 50));
		panel.add(pnlButtons, BorderLayout.SOUTH);
		
		JButton btnCreateUser = new JButton("Create User");
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("create user");
				if (txtEnterUseridHere.getText().length() == 0){
					lblMessages.setForeground(Color.RED);
					lblMessages.setText("Error: Cannot create a user with an empty userID");
				}else{
					if (GuiView.control.addUser(txtEnterUseridHere.getText(), txtEnterNameHere.getText())){
						lblMessages.setForeground(Color.BLACK);
						lblMessages.setText("User " + txtEnterUseridHere.getText() + " created successfully.");
					}else{
						lblMessages.setForeground(Color.RED);
						lblMessages.setText("Error: User " + txtEnterUseridHere.getText() + " already exists.");
					}
				}
			}
		});
		pnlButtons.add(btnCreateUser);
		
		JButton btnDeleteUser = new JButton("Delete User");
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("delete user");
				if (GuiView.control.deleteUser(txtEnterUseridHere.getText())){
					lblMessages.setText("User " + txtEnterUseridHere.getText() + " deleted successfully.");
				}else{
					lblMessages.setForeground(Color.RED);
					lblMessages.setText("Error: User " + txtEnterUseridHere.getText() + " does not exist.");
				}
				
			}
		});
		pnlButtons.add(btnDeleteUser);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiView.control.logout();
				//setVisible(false);
				//setEnabled(false);
				dispose();
				// return to GuiView
				GuiView.frame.setVisible(true);
				GuiView.frame.setEnabled(true);
				
			}
		});
		pnlButtons.add(btnLogout);
	}
}
