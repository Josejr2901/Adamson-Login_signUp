
package javaux;

import javax.swing.*;
import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LoggedInPage {

    private JFrame frame;
    private JButton signoutButton, profileButton;
    private JLabel welcomeLabel, usernameLabel, imageLabel; // imageLabel

    private User user;
    // AES key for encryption and decryption (same as before)
    private static final String SECRET_KEY = "mysecretkey12345"; // 16-byte key (128 bits)
    
    // New constructor accepting gender
    public LoggedInPage(User user) {
        this.user = user;
        
        String username = user.getUsername();
        String userGender = user.getGender();
       
        frame = new JFrame();      
        frame.setTitle("Welcome to ADAMSON AI");  // Set window title
        ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\adamson-logo.png");
        frame.setIconImage(image.getImage()); // Set window icon 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
        frame.setResizable(false); // Disable window resizing
        frame.setSize(800, 425); // Set window size
        frame.getContentPane().setBackground(Color.decode("#222222")); // Set background color
        frame.setLocationRelativeTo(null); 
        frame.setLayout(null);
   
        // Display gender (or any other information dynamically)
        welcomeLabel = new JLabel("Welcome"); // Dynamically setting the gender
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font(null, Font.TYPE1_FONT, 30));
        welcomeLabel.setBounds(0, 15, 800, 40); //x, y, width, height
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Display the username
        usernameLabel  = new JLabel(username);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font(null, Font.TYPE1_FONT, 30));
        usernameLabel.setBounds(0, 65, 800, 40); //x, y, width, height
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        ImageIcon profileIcon1 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\editUserWhite.png");
        ImageIcon profileIcon2 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\EditUserGold.png");
        
        profileButton = new JButton();
        profileButton.setFocusable(false);
        profileButton.setContentAreaFilled(false); // Disable default background behavior
        profileButton.setOpaque(true);  // Make sure the background is opaque to see the color
        profileButton.setFocusPainted(false);
        profileButton.setBackground(Color.decode("#876F4D"));
        profileButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        profileButton.setIcon(profileIcon1);
        profileButton.setBounds(730, 20, 30, 30);
        profileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileButton.addActionListener((ActionEvent e) -> {
            frame.dispose();
            new ProfilePage(user); 
        });
               
        signoutButton = new JButton("Sign Out");
        signoutButton.setContentAreaFilled(false); // Disable default background behavior
        signoutButton.setOpaque(true);  // Make sure the background is opaque to see the color
        signoutButton.setFocusable(false);
        signoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signoutButton.setBackground(Color.decode("#876F4D"));
        signoutButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        signoutButton.setForeground(Color.WHITE);
        signoutButton.setBounds(250, 310, 300, 50); //x, y, width, height
        signoutButton.addActionListener((ActionEvent e) -> {
            System.out.println(userGender);
            frame.dispose();
            
            // Clear the session file upon logout
            try {
                Files.deleteIfExists(Paths.get("session.txt"));  // Delete the session file
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            
            new MainPage(new HashMap<>()); 
        });

        // Mouse listener code...
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                profileButton.setIcon(profileIcon2);
                source.setBackground(Color.WHITE);
                source.setForeground(Color.BLACK);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                profileButton.setIcon(profileIcon2);
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.decode("#8A6E4B"));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                profileButton.setIcon(profileIcon1);
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                profileButton.setIcon(profileIcon1);
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.decode("#514937"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                profileButton.setIcon(profileIcon1);
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
        };
        
        profileButton.addMouseListener(listener);
        signoutButton.addMouseListener(listener);

        // Resize image proportionally
        ImageIcon imageIcon2 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\Adamson1.jpg");  
        int originalWidth = 2865;  //original size of the image
        int originalHeight = 1381; //original size of the image
        
        // Desired width for the already set image
        int newWidth = 800;
        
        // Calculate the new height maintaining aspect ratio
        int newHeight = (int) (newWidth * ((double) originalHeight / originalWidth));
        
        // Resize image while maintaining the aspect ratio
        Image imageResized = imageIcon2.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(imageResized); // Create resized ImageIcon

        // Add image to JLabel
        imageLabel = new JLabel(imageIcon2);
        imageLabel.setBounds(0, 0, newWidth, newHeight); // Position image below the signoutButton
        
        // Add components to the frame
        frame.add(welcomeLabel);
        frame.add(usernameLabel);
        frame.add(profileButton);
        frame.add(signoutButton);      
        frame.add(imageLabel);  // Add image label to the frame
        frame.setVisible(true);
    }
}
