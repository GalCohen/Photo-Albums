package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ListSelectionModel;

public class UserLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8651212493414583667L;
	private JPanel contentPane;
	private JTextField textField;
	private int selectedIndex = -1;
	public static String currentAlbum = "";
	public static Control control = new Controller();
	public static UserLogin frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new UserLogin();
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
	public UserLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 769, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setPreferredSize(new Dimension(10, 150));
		panel.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BorderLayout(0, 0));

		JPanel centerPanel = new JPanel();
		centerPanel.setMaximumSize(new Dimension(32767, 200));
		panel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		textField = new JTextField();
		textField.setBounds(158, 28, 576, 28);
		centerPanel.add(textField);
		textField.setColumns(10);
		
		final DefaultListModel model = new DefaultListModel();
		final JList list = new JList(model);
		
		ListSelectionListener lsl = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedIndex = list.getSelectedIndex();
				String albumName = "";
				if (selectedIndex != -1) {
					albumName = list.getModel().getElementAt(selectedIndex).toString();
					albumName = albumName.substring(albumName.indexOf("'") + 1, albumName.lastIndexOf("'"));
					currentAlbum = albumName;
				}
				textField.setText(albumName);
			}
		};
		
		list.addListSelectionListener(lsl);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		generateAlbumList(model, list);
		northPanel.add(list, BorderLayout.CENTER);
		
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(33, 34, 61, 16);
		centerPanel.add(nameLabel);
		
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(10, 150));
		panel.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel row1 = new JPanel();
		row1.setPreferredSize(new Dimension(10, 70));
		southPanel.add(row1, BorderLayout.NORTH);
		row1.setLayout(new GridLayout(1, 0, 0, 0));
		
		Component strut1 = Box.createHorizontalStrut(20);
		strut1.setPreferredSize(new Dimension(5, 0));
		row1.add(strut1);
		
		JButton createAlbumBtn = new JButton("Create Album");
		createAlbumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String albumName = textField.getText();
				if (albumName.length() == 0) {
					textField.setForeground(Color.RED);
					textField.setText("Please input an album name");
				}
				else if (GuiView.control.createAlbum(albumName) == true) {
					textField.setForeground(Color.BLACK);
					textField.setText(albumName + " has been created for user " + GuiView.control.getCurrentUser().getID().toString());
					generateAlbumList(model, list);
				}
				else {
					textField.setForeground(Color.RED);
					textField.setText(albumName + " already exists for user " + GuiView.control.getCurrentUser().getID().toString());
				}
			}
		});
		row1.add(createAlbumBtn);
		
		Component strut2 = Box.createHorizontalStrut(20);
		strut2.setPreferredSize(new Dimension(5, 0));
		row1.add(strut2);
		
		JButton renameAlbumBtn = new JButton("Rename Album");
		renameAlbumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().length() == 0) {
					textField.setForeground(Color.RED);
					textField.setText("Please select the album from the list above that is to be renamed");
				}
				else if (GuiView.control.getCurrentUser().getAlbumList().containsKey(textField.getText())) {
					textField.setForeground(Color.RED);
					textField.setText(textField.getText() + " already exists for user " + GuiView.control.getCurrentUser().getID().toString());
				}
				else if (selectedIndex != -1 && !currentAlbum.equals(textField.getText())) {
					textField.setForeground(Color.BLACK);
					String newName = textField.getText();
					
					String user = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getUserName();
					String userID = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getUserId();
					HashMap<String, Photo> photos = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList();
					Album a = new Album(newName, user, userID);
					a.setPhotoList(photos);
					
					GuiView.control.getCurrentUser().getAlbumList().remove(currentAlbum);
					GuiView.control.getCurrentUser().addAlbum(a);
					
					list.clearSelection();
					generateAlbumList(model, list);
					textField.setText(currentAlbum + " album name has been changed to " + newName);
				}
				/*
				Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
					System.out.println(pairs.getKey());
				}
				*/
			}
		});
		row1.add(renameAlbumBtn);
		
		Component strut3 = Box.createHorizontalStrut(20);
		strut3.setPreferredSize(new Dimension(5, 0));
		row1.add(strut3);
		
		JButton deleteAlbumBtn = new JButton("Delete Album");
		deleteAlbumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String albumName = textField.getText();
				if (GuiView.control.deleteAlbum(albumName) == true) {
					textField.setText(albumName + " has been deleted for user " + GuiView.control.getCurrentUser().getID().toString());
					list.clearSelection();
					generateAlbumList(model, list);
				}
				else if (selectedIndex == -1) {
					textField.setText("Please select an album from the list above to delete");
				}
			}
		});
		row1.add(deleteAlbumBtn);
		
		Component strut4 = Box.createHorizontalStrut(20);
		strut4.setPreferredSize(new Dimension(5, 0));
		row1.add(strut4);
		
		JPanel divider = new JPanel();
		southPanel.add(divider, BorderLayout.CENTER);
		
		JPanel row2 = new JPanel();
		row2.setPreferredSize(new Dimension(10, 70));
		southPanel.add(row2, BorderLayout.SOUTH);
		row2.setLayout(new GridLayout(1, 0, 0, 0));
		
		Component strut5 = Box.createHorizontalStrut(20);
		strut5.setPreferredSize(new Dimension(5, 0));
		row2.add(strut5);
		
		JButton openAlbumBtn = new JButton("Open Album");
		openAlbumBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.isSelectionEmpty() == false) {
					dispose();
					OpenAlbum oa = new OpenAlbum(currentAlbum);
					oa.setVisible(true);
					oa.setEnabled(true);
				}
				else {
					textField.setForeground(Color.RED);
					textField.setText("Please select an album to open");
				}
			}
		});
		row2.add(openAlbumBtn);
		
		Component strut6 = Box.createHorizontalStrut(20);
		strut6.setPreferredSize(new Dimension(5, 0));
		row2.add(strut6);
		
		JButton searchPhotosBtn = new JButton("Search Photos");
		searchPhotosBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				SearchPhotos sp = new SearchPhotos(UserLogin.currentAlbum);
				sp.setVisible(true);
				sp.setEnabled(true);
			}
		});
		row2.add(searchPhotosBtn);
		
		Component strut7 = Box.createHorizontalStrut(20);
		strut7.setPreferredSize(new Dimension(5, 0));
		row2.add(strut7);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GuiView.control.logout();
				GuiView.frame.setVisible(true);
				GuiView.frame.setEnabled(true);
			}
		});
		row2.add(logoutBtn);
		
		Component strut8 = Box.createHorizontalStrut(20);
		strut8.setPreferredSize(new Dimension(5, 0));
		row2.add(strut8);
	}
	
	private void generateAlbumList(DefaultListModel model, JList list) {
		model.removeAllElements();
		ArrayList<Album> albumslist = GuiView.control.listAlbums();
		if (albumslist != null){
			int count = 1;
			for (int y = 0; y < albumslist.size(); y++) {
				if (albumslist.get(y).getPhotoList().size() > 0) {
					model.add(list.getModel().getSize(), " " + count + ". Album Name: '" + albumslist.get(y).getName() + "' - number of photos: " + albumslist.get(y).getPhotoList().size()+", "+ control.getStartDate(albumslist.get(y)) +" - "+ control.getEndDate(albumslist.get(y)));
					count++;
					//System.out.println(albumslist.get(y).getName() + " number of photos: " + albumslist.get(y).getPhotoList().size()+", "+ control.getStartDate(albumslist.get(y)) +" - "+ control.getEndDate(albumslist.get(y)));
				}
				else {
					model.add(list.getModel().getSize(), " " + count + ". Album Name: '" + albumslist.get(y).getName() + "' - number of photos:0"+", "+ "MM/DD/YYYY-HH:MM:SS" + " - " +"MM/DD/YYYY-HH:MM:SS");
					count++;
					//System.out.println(albumslist.get(y).getName() + " number of photos:0"+", "+ "MM/DD/YYYY-HH:MM:SS" + " - " +"MM/DD/YYYY-HH:MM:SS");
				}
			}
		}
	}
}
