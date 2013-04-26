package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.JComboBox;
import javax.swing.JTextPane;

import cs213.photoAlbum.model.Photo;

/**
 * The SearchPhotos class is responsible for the functionality and display of the Search Photos window. It allows a user to search
 * through his/her photos in all albums by either Tags or dates, and create a new album using the search results.
 * 
 * @author Gal 
 *
 */
public class SearchPhotos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5084110542006929943L;
	private JPanel contentPane;
	private JTextField txtAlbumName;
	private JTable picturesTable;
	private ArrayList<Photo> photos;
	private ArrayList<Image> images;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchPhotos frame = new SearchPhotos(UserLogin.currentAlbum);
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
	public SearchPhotos(final String currentAlbum) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 840, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		
		final JPanel pnlNorth = new JPanel();
		pnlNorth.setPreferredSize(new Dimension(10, 100));
		contentPane.add(pnlNorth, BorderLayout.NORTH);
		pnlNorth.setLayout(new BorderLayout(0, 0));
		
		final JPanel dynamicPanel = new JPanel();
		dynamicPanel.setPreferredSize(new Dimension(360, 10));
	//	pnlNorth.add(dynamicPanel, BorderLayout.EAST);
		dynamicPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlStartDate = new JPanel();
		dynamicPanel.add(pnlStartDate, BorderLayout.WEST);
		pnlStartDate.setLayout(new BorderLayout(0, 0));
		
		String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		
		String[] years = new String[50];
		int year = 1963;
		for (int i = 0; i < 50; i++){ 
			years[i] = Integer.toString(year);
			year++;
		}
		
		String[] days = new String[32];
		int day = 1;
		for (int i = 0; i < 32; i++){
			if (Integer.toString(day).length() ==1 ){
				days[i] = "0" + Integer.toString(day);
			}else{
				days[i] = Integer.toString(day);
			}
			day++;
		}
		
		final JLabel lblstartdate = new JLabel("Start Date:");
		pnlStartDate.add(lblstartdate, BorderLayout.NORTH);
		
		final JComboBox StartYear = new JComboBox(years);
		StartYear.setSelectedIndex(49);
		pnlStartDate.add(StartYear, BorderLayout.EAST);
		
		final JComboBox startDay = new JComboBox(days);
		pnlStartDate.add(startDay, BorderLayout.CENTER);
		
		final JComboBox startMonth = new JComboBox(months);
		pnlStartDate.add(startMonth, BorderLayout.WEST);
		
		JPanel pnlEndDate = new JPanel();
		dynamicPanel.add(pnlEndDate, BorderLayout.EAST);
		pnlEndDate.setLayout(new BorderLayout(0, 0));
		
		final JLabel lblEndDate = new JLabel("End Date:");
		pnlEndDate.add(lblEndDate, BorderLayout.NORTH);
		
		final JComboBox endMonth = new JComboBox(months);
		pnlEndDate.add(endMonth, BorderLayout.WEST);
		
		final JComboBox endDay = new JComboBox(days);
		pnlEndDate.add(endDay, BorderLayout.CENTER);
		
		final JComboBox endYear = new JComboBox(years);
		endYear.setSelectedIndex(49);
		pnlEndDate.add(endYear, BorderLayout.EAST);
		//--
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(360, 10));
		pnlNorth.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 45));
		panel_1.add(panel_5, BorderLayout.NORTH);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		lblstartdate.setVisible(false);
		StartYear.setVisible(false);
		startDay.setVisible(false);
		startMonth.setVisible(false);
		
		lblEndDate.setVisible(false);
		endMonth.setVisible(false);
		endDay.setVisible(false);
		endYear.setVisible(false);
		
		lblstartdate.setEnabled(false);
		StartYear.setEnabled(false);
		startDay.setEnabled(false);
		startMonth.setEnabled(false);
		
		lblEndDate.setEnabled(false);
		endMonth.setEnabled(false);
		endDay.setEnabled(false);
		endYear.setEnabled(false);
		
		//--
		final JButton btnSearchDate = new JButton("Search");
		dynamicPanel.add(btnSearchDate, BorderLayout.SOUTH);
		
		// -- date
		btnSearchDate.setVisible(false);
		
		btnSearchDate.setEnabled(false);
		
		//---
		
		final JPanel dynamicPanel2 = new JPanel();
	//	pnlNorth.add(dynamicPanel2, BorderLayout.CENTER);
		dynamicPanel2.setLayout(new BorderLayout(0, 0));
		
		final JButton btnSearchTag = new JButton("Search");
		dynamicPanel2.add(btnSearchTag, BorderLayout.SOUTH);
		
		final JLabel lblSearchByTag = new JLabel("Search By Tag Type and Value");
		dynamicPanel2.add(lblSearchByTag, BorderLayout.NORTH);
		
		final JTextPane txtpnTags = new JTextPane();
		txtpnTags.setText("Enter tags here in this format TagType:\"TagValue\", separated by commas");
		dynamicPanel2.add(txtpnTags, BorderLayout.CENTER);
		
		btnSearchTag.setVisible(false);
		lblSearchByTag.setVisible(false);
		txtpnTags.setVisible(false);
		
		btnSearchTag.setEnabled(false);
		lblSearchByTag.setEnabled(false);
		txtpnTags.setEnabled(false);
		
		
		JPanel pnlCenter = new JPanel();
		contentPane.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pnlCenter.add(scrollPane, BorderLayout.CENTER);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.setPreferredSize(new Dimension(10, 30));
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		pnlSouth.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(350, 10));
		pnlSouth.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblName = new JLabel("Name:  ");
		panel_2.add(lblName, BorderLayout.EAST);
		
		final JLabel lblMessages = new JLabel("");
		panel_2.add(lblMessages, BorderLayout.CENTER);
		lblMessages.setVisible(false);
		lblMessages.setEnabled(false);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				// return to OpenAlbum
				UserLogin frame = new UserLogin();
				frame.setVisible(true);
			}
		});
		panel_2.add(btnBack, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		pnlSouth.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		txtAlbumName = new JTextField();
		panel_3.add(txtAlbumName, BorderLayout.CENTER);
		txtAlbumName.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(175, 10));
		pnlSouth.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(10, 45));
		panel_1.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		
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
		
		picturesTable = new JTable(model);
		picturesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		picturesTable.setIntercellSpacing(new Dimension(20, 20));
		picturesTable.setGridColor(Color.LIGHT_GRAY);
		picturesTable.setFillsViewportHeight(true);
		picturesTable.setCellSelectionEnabled(true);
		picturesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
		picturesTable.setRowHeight(150);
		picturesTable.setAutoscrolls(true);
		
		picturesTable.getColumnModel().getColumn(0).setResizable(false);
		picturesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		picturesTable.getColumnModel().getColumn(1).setResizable(false);
		picturesTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		picturesTable.getColumnModel().getColumn(2).setResizable(false);
		picturesTable.getColumnModel().getColumn(2).setPreferredWidth(50);
		picturesTable.getColumnModel().getColumn(3).setResizable(false);
		picturesTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		picturesTable.getColumnModel().getColumn(4).setResizable(false);
		picturesTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		
		scrollPane = new JScrollPane(picturesTable);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setViewportView(tablePanel);
        
        //JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //bottomPanel.add(scrollPane, BorderLayout.CENTER);
        //scrollPane.setViewportView(tablePanel);
        
        picturesTable.setPreferredScrollableViewportSize(picturesTable.getPreferredSize());
		tablePanel.add(picturesTable, BorderLayout.CENTER);
        
	/*	JPanel topLeftPanel = new JPanel();
		topLeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		topLeftPanel.setPreferredSize(new Dimension(350, 10));
		panel_7.add(topLeftPanel, BorderLayout.WEST);
		topLeftPanel.setLayout(new BorderLayout(0, 0));
	*/	
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
		//	topLeftPanel.add(imageLabel, BorderLayout.CENTER);
		}
		else {
			imageLabel = new JLabel("");
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//	topLeftPanel.add(imageLabel, BorderLayout.CENTER);
		}
		
       
		
		
		JButton btnSearchByDate = new JButton("Search By Date");
		btnSearchByDate.addActionListener(new ActionListener() {   //------------------------ SEARCH BY DATE
			public void actionPerformed(ActionEvent e) {
				//System.out.println("searchbydate");
				btnSearchTag.setVisible(false);
				lblSearchByTag.setVisible(false);
				txtpnTags.setVisible(false);
				
				btnSearchTag.setEnabled(false);
				lblSearchByTag.setEnabled(false);
				txtpnTags.setEnabled(false);
				
				pnlNorth.remove(dynamicPanel2);
				pnlNorth.add(dynamicPanel, BorderLayout.CENTER);
				pnlNorth.repaint();
				
				btnSearchDate.setVisible(true);
				
				lblstartdate.setVisible(true);
				StartYear.setVisible(true);
				startDay.setVisible(true);
				startMonth.setVisible(true);
				
				lblEndDate.setVisible(true);
				endMonth.setVisible(true);
				endDay.setVisible(true);
				endYear.setVisible(true);
				
				btnSearchDate.setEnabled(true);
				
				lblstartdate.setEnabled(true);
				StartYear.setEnabled(true);
				startDay.setEnabled(true);
				startMonth.setEnabled(true);
				
				lblEndDate.setEnabled(true);
				endMonth.setEnabled(true);
				endDay.setEnabled(true);
				endYear.setEnabled(true);
				
				
				btnSearchDate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//System.out.println("search by date test");
						
						String startDate = null;
						String endDate = null;
						
						startDate =  (String)startMonth.getSelectedItem()+ "/" + (String)startDay.getSelectedItem() + "/" + (String)StartYear.getSelectedItem() + "-00:00:00";
						endDate =   (String)endMonth.getSelectedItem()+ "/" + (String)endDay.getSelectedItem() + "/" + (String)endYear.getSelectedItem() + "-24:00:00";
						
						//System.out.println(startDate);
						//System.out.println(endDate);
						
						try {
							 photos = GuiView.control.getPhotosByDate(startDate, endDate);
							 
							if (photos != null){
								 refreshTable(model, imageLabel, photos);
							}else{
								lblMessages.setForeground(Color.BLACK);
								lblMessages.setText("No matching photos found.");
								lblMessages.setEnabled(true);
								lblMessages.setVisible(true);
							}
							
							 
						} catch (ParseException e1) {
							lblMessages.setForeground(Color.RED);
							lblMessages.setText("Error: Could not search by date.");
							lblMessages.setEnabled(true);
							lblMessages.setVisible(true);
						}
					}
				});
				
			}
		});
		panel_5.add(btnSearchByDate);
		
		
		JButton btnSearchByTags = new JButton("Search By Tags");    //------------------------ SEARCH BY TAGS
		btnSearchByTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("searchbytags");
				
				// -- date
				btnSearchDate.setVisible(false);
				
				lblstartdate.setVisible(false);
				StartYear.setVisible(false);
				startDay.setVisible(false);
				startMonth.setVisible(false);
				
				lblEndDate.setVisible(false);
				endMonth.setVisible(false);
				endDay.setVisible(false);
				endYear.setVisible(false);
				
				btnSearchDate.setEnabled(false);
				
				lblstartdate.setEnabled(false);
				StartYear.setEnabled(false);
				startDay.setEnabled(false);
				startMonth.setEnabled(false);
				
				lblEndDate.setEnabled(false);
				endMonth.setEnabled(false);
				endDay.setEnabled(false);
				endYear.setEnabled(false);
				
				//---
				pnlNorth.remove(dynamicPanel);
				pnlNorth.add(dynamicPanel2, BorderLayout.CENTER);
				pnlNorth.repaint();
				
				btnSearchTag.setVisible(true);
				lblSearchByTag.setVisible(true);
				txtpnTags.setVisible(true);
				
				btnSearchTag.setEnabled(true);
				lblSearchByTag.setEnabled(true);
				txtpnTags.setEnabled(true);
				
				btnSearchTag.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//System.out.println("search by tag test");
						
						StringTokenizer st = new StringTokenizer(txtpnTags.getText());
						
						ArrayList<String> tagArray = new ArrayList<String>();
     					
						//String searchString = txtpnTags.getText();
     					//searchString = searchString.substring(14);
     					 
     					try {
     						while (st.hasMoreTokens()){
								tagArray.add(st.nextToken(","));
     						}
     						
     						ArrayList<String> tags = new ArrayList<String>();
							for (int x = 0; x < tagArray.size(); x++) {
								if (tagArray.get(x).contains(":")) {
									//String s = tagArray.get(x).substring(tagArray.get(x).indexOf(":") + 2, tagArray.get(x).length() - 1);
									String s = tagArray.get(x).substring(tagArray.get(x).indexOf(":") + 1, tagArray.get(x).length() );
									tags.add(s);
									//System.out.println(s);
								}
								else {
									//String s = tagArray.get(x).trim().substring(1, tagArray.get(x).trim().length() - 1);
									String s = tagArray.get(x).trim().substring(0, tagArray.get(x).trim().length() );
									tags.add(s);
									//System.out.println(s);
								}
							}				
							 
 							
 							photos = GuiView.control.getPhotosByTag(tags);
 							if (photos != null){
 								
 								refreshTable(model, imageLabel, photos);
 								
 							}else{
 								lblMessages.setForeground(Color.BLACK);
 								lblMessages.setText("No photos found with these tags.");
 								lblMessages.setEnabled(true);
 								lblMessages.setVisible(true);
 							}
							 
 						}catch (NoSuchElementException b){
 							lblMessages.setForeground(Color.RED);
							lblMessages.setText("Error: Could not search by tag.");
							lblMessages.setEnabled(true);
							lblMessages.setVisible(true);
 						} 
					}
				});
			}
		});
		panel_6.add(btnSearchByTags);

		
		
		JButton btnCreateButton = new JButton("Create Album");
		btnCreateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("create album");
				if (txtAlbumName.getText() != null){
					if (txtAlbumName.getText().length() == 0){
						lblMessages.setForeground(Color.RED);
						lblMessages.setText("Error: Please enter a valid album name.");
						lblMessages.setEnabled(true);
						lblMessages.setVisible(true);
					}else{
						if (GuiView.control.createAlbum(txtAlbumName.getText())){
							lblMessages.setForeground(Color.BLACK);
							lblMessages.setText("Album"+ txtAlbumName.getText()+" created.");
							lblMessages.setEnabled(true);
							lblMessages.setVisible(true);
							
							if (photos != null){
								for (int i = 0; i < photos.size(); i++){
									GuiView.control.addPhoto(photos.get(i).getName(), photos.get(i).getCaption(), txtAlbumName.getText() , photos.get(i).getPic());
								}
							}
							
						}else{
							lblMessages.setForeground(Color.RED);
							lblMessages.setText("Error: Album "+ txtAlbumName.getText()+" already exists.");
							lblMessages.setEnabled(true);
							lblMessages.setVisible(true);
						}
					}
				}else{
				
					lblMessages.setForeground(Color.RED);
					lblMessages.setText("Error: Please enter a valid album name.");
					lblMessages.setEnabled(true);
					lblMessages.setVisible(true);
				}
				
				
				
			}
		});
		panel_4.add(btnCreateButton, BorderLayout.CENTER);
	}

	
	public void refreshTable(DefaultTableModel model, JLabel imageLabel, ArrayList<Photo> photos) {
		for (int r = model.getRowCount()-1; r >= 0; r--) {
			model.removeRow(r);
		}
		for (int a = images.size()-1; a >= 0; a--) {
			images.remove(a);
		}
		
		//Iterator it = GuiView.control.getCurrentUser().getAlbumList().get(currentAlbum).getPhotoList().entrySet().iterator();
		Iterator it = photos.iterator();
		
		while (it.hasNext()) {
			//Map.Entry<String, Photo> pairs = (Map.Entry<String, Photo>) it.next();
			Photo It = (Photo)it.next();
			//System.out.println(It.getName());
			try {
				Image image = ImageIO.read(It.getPic());
				images.add(image);
			} catch (IOException e1) {
				e1.printStackTrace();
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
			picturesTable.revalidate();
		}
	}
}
