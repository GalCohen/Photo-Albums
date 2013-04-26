package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

public class OpenAlbum extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2614109574863611888L;
	private JPanel contentPane;
	private JTable table;
	private JTextField captionTextField;
	private JTextField dateTimeTextField;
	private ArrayList<Image> images;
	private static OpenAlbum frame;
	private JTextField tagTextField;
	private String selectedPicture;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new OpenAlbum(UserLogin.currentAlbum);
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
	public OpenAlbum(final String currentAlbum) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(10, 175));
		panel.add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		panel.add(topPanel, BorderLayout.CENTER);
		topPanel.setLayout(new BorderLayout(0, 0));

		JPanel topCenterPanel = new JPanel();
		topCenterPanel.setPreferredSize(new Dimension(270, 10));
		topPanel.add(topCenterPanel, BorderLayout.EAST);
		topCenterPanel.setLayout(new BorderLayout(0, 0));

		JPanel dynamicPanel = new JPanel();
		dynamicPanel.setPreferredSize(new Dimension(10, 130));
		topCenterPanel.add(dynamicPanel, BorderLayout.NORTH);
		dynamicPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel namePanel = new JPanel();
		namePanel.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
		namePanel.setPreferredSize(new Dimension(10, 45));
		dynamicPanel.add(namePanel, BorderLayout.NORTH);
		namePanel.setLayout(new BorderLayout(0, 0));
		
		final JLabel nameLabel = new JLabel("");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		namePanel.add(nameLabel, BorderLayout.CENTER);
		
		JPanel topRightPanel = new JPanel();
		topPanel.add(topRightPanel, BorderLayout.CENTER);
		topRightPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel dateTimePanel = new JPanel();
		dateTimePanel.setPreferredSize(new Dimension(10, 70));
		topRightPanel.add(dateTimePanel, BorderLayout.CENTER);
		dateTimePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel dateTimeLabelPanel = new JPanel();
		dateTimeLabelPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		dateTimeLabelPanel.setPreferredSize(new Dimension(10, 31));
		dateTimePanel.add(dateTimeLabelPanel, BorderLayout.NORTH);
		dateTimeLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblDateTime = new JLabel("Date & Time:");
		lblDateTime.setHorizontalAlignment(SwingConstants.CENTER);
		dateTimeLabelPanel.add(lblDateTime, BorderLayout.CENTER);
		
		JPanel dateTimeTextFieldPanel = new JPanel();
		dateTimeTextFieldPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		dateTimeTextFieldPanel.setPreferredSize(new Dimension(10, 36));
		dateTimePanel.add(dateTimeTextFieldPanel, BorderLayout.SOUTH);
		dateTimeTextFieldPanel.setLayout(new BorderLayout(0, 0));
		
		dateTimeTextField = new JTextField();
		dateTimeTextFieldPanel.add(dateTimeTextField, BorderLayout.CENTER);
		dateTimeTextField.setColumns(10);

		JPanel tagPanel = new JPanel();
		tagPanel.setPreferredSize(new Dimension(10, 140));
		topRightPanel.add(tagPanel, BorderLayout.SOUTH);
		tagPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel tagLabelPanel = new JPanel();
		tagLabelPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tagLabelPanel.setPreferredSize(new Dimension(10, 31));
		tagPanel.add(tagLabelPanel, BorderLayout.NORTH);
		tagLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTags = new JLabel("Tags:");
		lblTags.setHorizontalAlignment(SwingConstants.CENTER);
		tagLabelPanel.add(lblTags, BorderLayout.CENTER);
		
		JPanel tagButtonPanel = new JPanel();
		tagButtonPanel.setPreferredSize(new Dimension(10, 36));
		tagPanel.add(tagButtonPanel, BorderLayout.SOUTH);
		tagButtonPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_5.setPreferredSize(new Dimension(100, 10));
		tagButtonPanel.add(panel_5, BorderLayout.WEST);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel tagTextFieldPanel = new JPanel();
		tagTextFieldPanel.setBorder(null);
		tagPanel.add(tagTextFieldPanel, BorderLayout.CENTER);
		tagTextFieldPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_12.setPreferredSize(new Dimension(10, 36));
		tagTextFieldPanel.add(panel_12, BorderLayout.SOUTH);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		tagTextField = new JTextField();
		tagTextField.setText("tag type:\"tag value\"");
		panel_12.add(tagTextField, BorderLayout.CENTER);
		tagTextField.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(10, 5));
		panel_5.add(panel_7, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_6.setPreferredSize(new Dimension(100, 10));
		tagButtonPanel.add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(10, 5));
		panel_6.add(panel_8, BorderLayout.NORTH);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_11.setPreferredSize(new Dimension(10, 36));
		tagTextFieldPanel.add(panel_11, BorderLayout.NORTH);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		final JComboBox tagComboBox = new JComboBox();
		panel_11.add(tagComboBox, BorderLayout.CENTER);
		/*
		ArrayList<Tag> tags = new ArrayList<Tag>();
		ArrayList<Photo> photos = new ArrayList<Photo>();
		Iterator photoIter = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().entrySet().iterator();
		while (photoIter.hasNext()) {
			Map.Entry<String, Photo> pairs = (Map.Entry<String, Photo>) photoIter.next();
			photos.add(pairs.getValue());
		}
		if (photos.size() != 0) {
			Iterator tagIter = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(photos.get(0).getName()).getTagList().entrySet().iterator();
			while (tagIter.hasNext()) {
				Map.Entry<String, Tag> pairs = (Map.Entry<String, Tag>) tagIter.next();
				tags.add(pairs.getValue());
			}
			if (tags.size() != 0) {
				for (int t = 0; t < tags.size(); t++) {
					tagComboBox.addItem("<" + tags.get(0).getType() + ":" + tags.get(0).getValue() + ">");
				}
			}
		}
		*/
		
		// ---------------------- table starts
		
		JPanel tablePanel = new JPanel();
		
		images = new ArrayList<Image>();
		
		final DefaultTableModel model = new DefaultTableModel();
		model.addColumn(new String[]{""});
		model.addColumn(new String[]{""});
		model.addColumn(new String[]{""});
		model.addColumn(new String[]{""});
		model.addColumn(new String[]{""});
		
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setIntercellSpacing(new Dimension(20, 20));
		table.setGridColor(Color.LIGHT_GRAY);
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(true);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
		    protected void setValue(Object value) {
		        if( value instanceof ImageIcon ) {
		            setIcon((ImageIcon)value);
		            setText("");
		        } else {
		            setIcon(null);
		            super.setValue(value);
		        }
		    }
		});
		table.setRowHeight(150);
		table.setAutoscrolls(true);
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		
		JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(tablePanel);
        
		JPanel topLeftPanel = new JPanel();
		topLeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		topLeftPanel.setPreferredSize(new Dimension(350, 10));
		topPanel.add(topLeftPanel, BorderLayout.WEST);
		topLeftPanel.setLayout(new BorderLayout(0, 0));
		
		final JLabel imageLabel;
		if (images.size() > 0) {
			
			double w = images.get(0).getWidth(null);
			double h = images.get(0).getHeight(null);
			double h2 = 270 * (h / w);
			ImageIcon i = new ImageIcon(images.get(0).getScaledInstance(270, (int) h2, 0));
			//Image scaled = images.get(0).getScaledInstance(300, 300, 0);
			//ImageIcon i = new ImageIcon(scaled);
			imageLabel = new JLabel(i);
			imageLabel.setBorder(new LineBorder(Color.BLACK, 2));
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			topLeftPanel.add(imageLabel, BorderLayout.CENTER);
		}
		else {
			imageLabel = new JLabel("");
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			topLeftPanel.add(imageLabel, BorderLayout.CENTER);
		}
		
        refreshTable(model, imageLabel, currentAlbum, tagComboBox);
		
		JButton btnAddTag = new JButton("Add Tag");
		btnAddTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tagType = "", tagValue = "";
				if (tagTextField.getText().contains(":")) {
					tagType = tagTextField.getText().substring(0, tagTextField.getText().indexOf(":"));
					tagValue = tagTextField.getText().substring(tagTextField.getText().indexOf(":") + 1, tagTextField.getText().length());
				}
				
				if (tagTextField.getText().length() == 0) {
					tagTextField.setForeground(Color.RED);
					tagTextField.setText("Please input type:value");
				}
				else if (!tagTextField.getText().matches(".+:\".+\"")) {
					tagTextField.setForeground(Color.RED);
					tagTextField.setText("Invalid Format");
				}
				else if (selectedPicture == null) {
					tagTextField.setForeground(Color.RED);
					tagTextField.setText("Select a Picture");
				}
				else if (selectedPicture != null && GuiView.control.addTag(selectedPicture, tagType, tagValue) == 1) {
					tagTextField.setForeground(Color.BLACK);
					tagTextField.setText("Tag Created");
					refreshTable(model, imageLabel, currentAlbum, tagComboBox);
					selectedPicture = null;
				}
				else {
					tagTextField.setForeground(Color.RED);
					tagTextField.setText("Tag already exists");
				}
			}
		});
		panel_5.add(btnAddTag, BorderLayout.CENTER);
		
		JButton btnDeleteTag = new JButton("Delete Tag");
		btnDeleteTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedPicture == null) {
					tagTextField.setForeground(Color.RED);
					tagTextField.setText("Select a Picture");
				}
				else if (selectedPicture != null && tagComboBox.getItemCount() == 0) {
					tagTextField.setForeground(Color.RED);
					tagTextField.setText("No tags to delete");
				}
				else {
					tagTextField.setForeground(Color.BLACK);
					String tag = tagComboBox.getSelectedItem().toString();
					String tagType = tag.substring(1, tag.indexOf(":"));
					String tagValue = tag.substring(tag.indexOf(":") + 1, tag.indexOf(">"));
					GuiView.control.deleteTag(selectedPicture, tagType, tagValue);
					refreshTable(model, imageLabel, currentAlbum, tagComboBox);
				}
				selectedPicture = null;
			}
		});
		panel_6.add(btnDeleteTag, BorderLayout.CENTER);
		
        table.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e) {
        		int nRow = table.rowAtPoint(e.getPoint());
        		int nCol = table.columnAtPoint(e.getPoint());
        		
        		if (table.getValueAt(nRow, nCol) != "") {
        			double w = images.get(0).getWidth(null);
        			double h = images.get(0).getHeight(null);
        			double h2 = 270 * (h / w);
        			Image i = ((ImageIcon) table.getValueAt(nRow, nCol)).getImage().getScaledInstance(270, (int) h2, 0);
	        		//Image i = ((ImageIcon) table.getValueAt(nRow, nCol)).getImage().getScaledInstance(300, 300, 0);
	        		//imageLabel.setIcon(new ImageIcon(i));
        			imageLabel.setIcon(new ImageIcon(i));
	        		
	        		ArrayList<Photo> photos = new ArrayList<Photo>();
	        		Iterator it = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().entrySet().iterator();
	        		while (it.hasNext()) {
	        			Map.Entry<String, Photo> pairs = (Map.Entry<String, Photo>) it.next();
	        			photos.add(pairs.getValue());
	        		}
	        		
	        		int row = 0, col = 0;
	        		for (int x = 0; x < photos.size(); x++) {
	        			if (col == 4) {
	        				row++;
	        				col = 0;
	        			}
	        			else if (row == nRow && col == nCol) {
	        				nameLabel.setText(photos.get(x).getName());
	        				captionTextField.setText(photos.get(x).getCaption());
	        				dateTimeTextField.setText(photos.get(x).getDateString());
	        				selectedPicture = photos.get(x).getName();
	        				break;
	        			}
	        			else
	        				col++;
	        		}
	        		
	        		tagComboBox.removeAllItems();
	        		if (GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(selectedPicture).getTagList().size() != 0) {
	        			Iterator tagIter = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(selectedPicture).getTagList().entrySet().iterator();
	        			while (tagIter.hasNext()) {
	        				Map.Entry<String, Tag> pairs2 = (Map.Entry<String, Tag>) tagIter.next();
	        				tagComboBox.addItem("<" + pairs2.getValue().getType() + ":" + pairs2.getValue().getValue() + ">");
	        			}
	        		}
        		}
    		}
		});
        
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		tablePanel.add(table, BorderLayout.CENTER);
		
		// ------------------ table end
		
		JPanel backPanel = new JPanel();
		backPanel.setPreferredSize(new Dimension(10, 20));
		bottomPanel.add(backPanel, BorderLayout.SOUTH);
		backPanel.setLayout(new BorderLayout(0, 0));
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					GuiView.backend.save(GuiView.backend.getUserList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dispose();
				UserLogin ul = new UserLogin();
				ul.setVisible(true);
				ul.setEnabled(true);
			}
		});
		backPanel.add(btnBack, BorderLayout.EAST);
		
		JPanel buttonPanel1 = new JPanel();
		buttonPanel1.setPreferredSize(new Dimension(10, 70));
		topCenterPanel.add(buttonPanel1, BorderLayout.CENTER);
		buttonPanel1.setLayout(new BorderLayout(0, 0));
		
		JPanel movePhotoPanel = new JPanel();
		movePhotoPanel.setPreferredSize(new Dimension(10, 36));
		buttonPanel1.add(movePhotoPanel, BorderLayout.NORTH);
		movePhotoPanel.setLayout(new BorderLayout(0, 0));
		
		class TypeOfFile extends FileFilter {

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpeg") || f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png")
						|| f.getName().toLowerCase().endsWith(".gif") || f.getName().toLowerCase().endsWith(".bmp");
			}

			@Override
			public String getDescription() {
				return "Please select a JPEG, PNG, GIF, or BMP Image File";
			}
			
		}
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(10, 5));
		movePhotoPanel.add(panel_2, BorderLayout.NORTH);
		
		JPanel addPhotoPanel = new JPanel();
		addPhotoPanel.setPreferredSize(new Dimension(10, 36));
		buttonPanel1.add(addPhotoPanel, BorderLayout.SOUTH);
		addPhotoPanel.setLayout(new BorderLayout(0, 0));
		
		JButton btnAddPhoto = new JButton("Add Photo");
		btnAddPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (captionTextField.getText().length() == 0) {
					captionTextField.setForeground(Color.RED);
					captionTextField.setText("Insert caption to Add");
				}
				else if (captionTextField.getText().length() >= 1) {
					captionTextField.setForeground(Color.BLACK);
					JFileChooser fc = new JFileChooser(".");
					fc.setFileFilter(new TypeOfFile());
				    if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
				    	File pic = fc.getSelectedFile();
				    	GuiView.control.addPhoto(fc.getSelectedFile().getName(), captionTextField.getText(), currentAlbum, pic);
				    }
				    refreshTable(model, imageLabel, currentAlbum, tagComboBox);
				}
			}
		});
		addPhotoPanel.add(btnAddPhoto, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(10, 5));
		addPhotoPanel.add(panel_4, BorderLayout.SOUTH);
		
		JPanel movePhotoToPanel = new JPanel();
		movePhotoToPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		dynamicPanel.add(movePhotoToPanel, BorderLayout.CENTER);
		movePhotoToPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel movePhotoLabelPanel = new JPanel();
		movePhotoLabelPanel.setPreferredSize(new Dimension(10, 42));
		movePhotoToPanel.add(movePhotoLabelPanel, BorderLayout.NORTH);
		movePhotoLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMovePhotoTo = new JLabel("Move Photo To:");
		lblMovePhotoTo.setHorizontalAlignment(SwingConstants.CENTER);
		movePhotoLabelPanel.add(lblMovePhotoTo, BorderLayout.CENTER);
		
		JPanel movePhotoComboBoxPanel = new JPanel();
		movePhotoToPanel.add(movePhotoComboBoxPanel, BorderLayout.CENTER);
		movePhotoComboBoxPanel.setLayout(new BorderLayout(0, 0));
		
		final JComboBox movePhotoComboBox = new JComboBox();
		movePhotoComboBoxPanel.add(movePhotoComboBox, BorderLayout.CENTER);
		
		JButton btnMovePhoto = new JButton("Move Photo");
		btnMovePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the name of the album we want to move the photo to
				String movedToAlbum = movePhotoComboBox.getSelectedItem().toString();
				if (!movedToAlbum.equals(currentAlbum)) {
					// add the photo to the new album
					GuiView.control.addPhoto(selectedPicture, GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(selectedPicture).getCaption(), 
							movedToAlbum, GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(selectedPicture).getPic());
					// set new tag list
					GuiView.control.getCurrentUser().getAlbumList().get(movedToAlbum).getPhotoList().get(selectedPicture).setTagList(
							GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(selectedPicture).getTagList());
					// set time
					GuiView.control.getCurrentUser().getAlbumList().get(movedToAlbum).getPhotoList().get(selectedPicture).setStringDate(
							GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(selectedPicture).getDateString());
					// remove photo from old album
					GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().remove(selectedPicture);
					// refresh table
					refreshTable(model, imageLabel, currentAlbum, tagComboBox);
				}
			}
		});
		movePhotoPanel.add(btnMovePhoto, BorderLayout.CENTER);
		
		Iterator it = GuiView.control.getCurrentUser().getAlbumList().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
			movePhotoComboBox.addItem(pairs.getKey());
		}
		
		JPanel buttonPanel2 = new JPanel();
		buttonPanel2.setPreferredSize(new Dimension(10, 70));
		topCenterPanel.add(buttonPanel2, BorderLayout.SOUTH);
		buttonPanel2.setLayout(new BorderLayout(0, 0));
		
		JPanel removePhotoPanel = new JPanel();
		removePhotoPanel.setPreferredSize(new Dimension(10, 36));
		buttonPanel2.add(removePhotoPanel, BorderLayout.NORTH);
		removePhotoPanel.setLayout(new BorderLayout(0, 0));
		
		JButton btnRemovePhoto = new JButton("Remove Photo");
		btnRemovePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().remove(selectedPicture);
				refreshTable(model, imageLabel, currentAlbum, tagComboBox);
			}
		});
		removePhotoPanel.add(btnRemovePhoto, BorderLayout.CENTER);
		
		JPanel fillerPanel3 = new JPanel();
		fillerPanel3.setPreferredSize(new Dimension(10, 5));
		removePhotoPanel.add(fillerPanel3, BorderLayout.NORTH);
		
		JPanel panel_9 = new JPanel();
		panel_9.setPreferredSize(new Dimension(10, 5));
		panel_9.setMinimumSize(new Dimension(10, 5));
		removePhotoPanel.add(panel_9, BorderLayout.SOUTH);
		
		JPanel slideshowButtonPanel = new JPanel();
		slideshowButtonPanel.setPreferredSize(new Dimension(10, 36));
		buttonPanel2.add(slideshowButtonPanel, BorderLayout.SOUTH);
		slideshowButtonPanel.setLayout(new BorderLayout(0, 0));
		
		JButton btnSlideshow = new JButton("Slideshow");
		btnSlideshow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Slideshow s = new Slideshow(UserLogin.currentAlbum);
				s.setVisible(true);
				s.setEnabled(true);
			}
		});
		slideshowButtonPanel.add(btnSlideshow, BorderLayout.CENTER);
		
		JPanel fillerPanel4 = new JPanel();
		fillerPanel4.setPreferredSize(new Dimension(10, 5));
		fillerPanel4.setMinimumSize(new Dimension(10, 5));
		slideshowButtonPanel.add(fillerPanel4, BorderLayout.SOUTH);
		
		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(10, 5));
		slideshowButtonPanel.add(panel_10, BorderLayout.NORTH);
		
		JPanel captionPanel = new JPanel();
		captionPanel.setPreferredSize(new Dimension(10, 70));
		topRightPanel.add(captionPanel, BorderLayout.NORTH);
		captionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel captionLabelPanel = new JPanel();
		captionLabelPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		captionLabelPanel.setPreferredSize(new Dimension(10, 31));
		captionPanel.add(captionLabelPanel, BorderLayout.NORTH);
		captionLabelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCaption = new JLabel("Caption:");
		lblCaption.setPreferredSize(new Dimension(53, 12));
		lblCaption.setHorizontalAlignment(SwingConstants.CENTER);
		captionLabelPanel.add(lblCaption, BorderLayout.CENTER);
		
		JPanel captionTextFieldPanel = new JPanel();
		captionTextFieldPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		captionTextFieldPanel.setPreferredSize(new Dimension(10, 36));
		captionPanel.add(captionTextFieldPanel, BorderLayout.SOUTH);
		captionTextFieldPanel.setLayout(new BorderLayout(0, 0));
		
		captionTextField = new JTextField();
		captionTextFieldPanel.add(captionTextField, BorderLayout.CENTER);
		captionTextField.setColumns(10);
		
	}
	
	public void refreshTable(DefaultTableModel model, JLabel imageLabel, String currentAlbum, JComboBox tagComboBox) {
		for (int r = model.getRowCount()-1; r >= 0; r--) {
			model.removeRow(r);
		}
		for (int a = images.size()-1; a >= 0; a--) {
			images.remove(a);
		}
		tagComboBox.removeAllItems();
		
		Iterator it = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Photo> pairs = (Map.Entry<String, Photo>) it.next();
			try {
				Image image = ImageIO.read(pairs.getValue().getPic());
				images.add(image);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		ArrayList<Photo> photos = new ArrayList<Photo>();
		Iterator photoIter = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().entrySet().iterator();
		while (photoIter.hasNext()) {
			Map.Entry<String, Photo> pairs = (Map.Entry<String, Photo>) photoIter.next();
			photos.add(pairs.getValue());
		}
		if (photos.size() > 0) {
			if (photos.get(0).getTagList().size() != 0) {
				Iterator tagIter = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().get(photos.get(0).getName()).getTagList().entrySet().iterator();
				while (tagIter.hasNext()) {
					Map.Entry<String, Tag> pairs2 = (Map.Entry<String, Tag>) tagIter.next();
					tagComboBox.addItem("<" + pairs2.getValue().getType() + ":" + pairs2.getValue().getValue() + ">");
				}
			}
		}
		
		ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
        for (int x = 0; x < images.size(); x++) {
        	double w = images.get(x).getWidth(null);
			double h = images.get(x).getHeight(null);
			double h2 = 100 * (h / w);
			Image s = images.get(x).getScaledInstance(100, (int) h2, 0);
        	//Image s = images.get(x).getScaledInstance(130, 130, 0);
        	ImageIcon p = new ImageIcon(s);
        	icons.add(p);
        }
        int row = 0, col = 0;
		model.addRow(new Object[] {"", "", "", "", ""});
		for (int i = 0; i < icons.size(); i++) {
			model.setValueAt(icons.get(i), row, col);
			if (col == 4) {
				model.addRow(new Object[] {"", "", "", "", ""});
				row++;
				col = 0;
			}
			else
				col++;
		}


		if (images.size() != 0) {
			double w = images.get(0).getWidth(null);
			double h = images.get(0).getHeight(null);
			double h2 = 270 * (h / w);
			ImageIcon i = new ImageIcon(images.get(0).getScaledInstance(270, (int) h2, 0));

			imageLabel.setIcon(i);
			table.revalidate();
		}
	}
}
