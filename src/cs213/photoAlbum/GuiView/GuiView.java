package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JButton;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Backend;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.Color;

public class GuiView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026624120347734379L;
	private JPanel contentPane;
	private JTextField textField;
	public static Backend backend = new Backend();
	public static Control control = new Controller();
	public static GuiView frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new GuiView();
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
	public GuiView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		try {
			GuiView.backend.setUserList(GuiView.backend.load());
			//System.out.println("LOADED: " + CmdView.backend.getUserList().get("dennisr").getFullName().toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(10, 50));
		panel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel titleLabel = new JLabel("Team 06 Photo Album");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		northPanel.add(titleLabel, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);
		
		JLabel lblUserId = new JLabel("User ID:");
		lblUserId.setBounds(63, 78, 61, 16);
		centerPanel.add(lblUserId);

		final JLabel errorHandlerLabel = new JLabel("");
		errorHandlerLabel.setForeground(Color.RED);
		errorHandlerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorHandlerLabel.setBounds(6, 32, 428, 16);
		centerPanel.add(errorHandlerLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_ENTER) {
	            	if (textField.getText().length() == 0)
	            		errorHandlerLabel.setText("Error: Please input a User ID");
	            	else if (textField.getText().toLowerCase().equals("admin")) {
						dispose();
						AdminLogin al = new AdminLogin();
						al.setVisible(true);
						al.setEnabled(true);
					}
					else {
						if (GuiView.control.login(textField.getText()) == true) {
							dispose();
							UserLogin ul = new UserLogin();
							ul.setVisible(true);
							ul.setEnabled(true);
						}
						else {
							errorHandlerLabel.setText("Error: User '" + textField.getText() + "' does not exist in the User Database");
						}
					}
	            }
	          }
		});
		textField.setBounds(149, 72, 207, 28);
		centerPanel.add(textField);
		textField.setColumns(10);
		
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(10, 50));
		panel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(null);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().length() == 0)
            		errorHandlerLabel.setText("Error: Please input a User ID");
				else if (textField.getText().toLowerCase().equals("admin")) {
					dispose();
					AdminLogin al = new AdminLogin();
					al.setVisible(true);
					al.setEnabled(true);
				}
				else {
					if (GuiView.backend.getUserList().containsKey(textField.getText())) {
						dispose();
						UserLogin ul = new UserLogin();
						ul.setVisible(true);
						ul.setEnabled(true);
						control.login(textField.getText());
					}
					else {
						errorHandlerLabel.setText("Error: User '" + textField.getText() + "' does not exist in the User Database");
					}
				}
			}
		});
		btnConfirm.setBounds(84, 6, 117, 29);
		southPanel.add(btnConfirm);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(213, 6, 117, 29);
		southPanel.add(btnExit);
	}
}
