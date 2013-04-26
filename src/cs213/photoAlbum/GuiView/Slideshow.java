package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import javax.swing.JButton;

import cs213.photoAlbum.model.Photo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * The slideshow class is responsible for the functionality and display of the slideshow window. The slideshow window allows a user
 * to view his or her photos in a given album one by one, using backward and forward buttons to iterate the list of photos.
 * @author Gal
 */
public class Slideshow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7301458785850386489L;
	private JPanel contentPane;
	private ArrayList<Image> images;
	private int imageCounter = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Slideshow frame = new Slideshow(UserLogin.currentAlbum);
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
	public Slideshow(final String currentAlbum) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlSlideshowButtons = new JPanel();
		pnlSlideshowButtons.setPreferredSize(new Dimension(10, 50));
		panel.add(pnlSlideshowButtons, BorderLayout.SOUTH);
		
		images = new ArrayList<Image>();
		
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
		
		
		double w = images.get(imageCounter).getWidth(null);
		double h = images.get(imageCounter).getHeight(null);
		
		//double ratio = w /h;
		double h2 =  700 * (h / w);
		Image scaled = images.get(imageCounter).getScaledInstance(700, (int) h2, 0);
		ImageIcon i = new ImageIcon(scaled);
		//imageLabel = new JLabel(i);
		
		final JPanel pnlPhotos = new JPanel();
		panel.add(pnlPhotos, BorderLayout.CENTER);
		
		final JLabel lblImages = new JLabel("");
		pnlPhotos.add(lblImages);
		
		
		lblImages.setIcon(i);
		
		JButton btnBackward = new JButton("Backward");
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("back");
				imageCounter--;
				
				if (imageCounter < 0){
					imageCounter = images.size()-1;
				}
				
				double w = images.get(imageCounter).getWidth(null);
				double h = images.get(imageCounter).getHeight(null);
				
				//double ratio = w /h;
				double h2 =  700 * (h / w);
				
				ImageIcon i = new ImageIcon(images.get(imageCounter).getScaledInstance(700, (int) h2, 0));
				lblImages.setIcon(i);
				
				
			}
		});
		pnlSlideshowButtons.setLayout(new BorderLayout(0, 0));
		pnlSlideshowButtons.add(btnBackward, BorderLayout.WEST);
		
		JButton btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("forward");
				
				imageCounter++;
				
				if (imageCounter >= images.size()){
					imageCounter = 0;
				}
				
				double w = images.get(imageCounter).getWidth(null);
				double h = images.get(imageCounter).getHeight(null);
				
				//double ratio = w /h;
				double h2 =  700 * (h / w);
				
				ImageIcon i = new ImageIcon(images.get(imageCounter).getScaledInstance(700, (int) h2, 0));
				lblImages.setIcon(i);
				
			}
		});
		pnlSlideshowButtons.add(btnForward, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 20));
		panel_1.setMinimumSize(new Dimension(10, 20));
		pnlSlideshowButtons.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Return");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				// return to OpenAlbum
				OpenAlbum frame = new OpenAlbum(UserLogin.currentAlbum);
				frame.setVisible(true);
			}
		});
		btnNewButton.setPreferredSize(new Dimension(50, 29));
		panel_1.add(btnNewButton, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(150, 10));
		panel_1.add(panel_2, BorderLayout.EAST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setPreferredSize(new Dimension(150, 10));
		panel_1.add(panel_3, BorderLayout.WEST);
		
		
	}

}
