 
package javaux;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class ProfilePage {

    private JFrame frame;
    private JLabel usernameLabel, emailLabel, genderLabel;
    private JButton editProfileButton, cancelButton, logoutButton, profilePictureButton;
    
    //private User user;

    public ProfilePage(User user) {
        
        String username = user.getUsername();
        String userEmail = user.getEmail();
        String userGender = user.getGender();
                      
        frame = new JFrame("Profile Page");
        frame.setSize(410, 500);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#222222"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        
        ImageIcon icon = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\adamson-logo.png");
        frame.setIconImage(icon.getImage());
        
//        ImageIcon profilePictureMale1 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureMaleWhite.png");
//        ImageIcon profilePictureMale2 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureMaleGold.png");
//        
//        ImageIcon profilePictureFemale1 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureFemaleWhite.png");
//        ImageIcon profilePictureFemale2 = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureFemaleGold.png");
        
        profilePictureButton = new JButton();
        profilePictureButton.setContentAreaFilled(false);
        profilePictureButton.setFocusable(false);
        profilePictureButton.setOpaque(true);  // Make sure the background is opaque to see the color
        profilePictureButton.setFocusPainted(false);
        profilePictureButton.setBackground(Color.decode("#876F4D"));
        profilePictureButton.setBounds(130, 40, 140, 160);
        //profilePictureButton.setIcon(profilePictureIcon1);
        profilePictureButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        WhiteProfileIcon(userGender, profilePictureButton);
        
        usernameLabel = new JLabel(username);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font(null, Font.TYPE1_FONT, 18));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setBounds(0, 205, 400, 30);
        
        emailLabel = new JLabel(userEmail);
        emailLabel.setForeground(Color.decode("#876F4D"));
        emailLabel.setFont(new Font(null, Font.TYPE1_FONT, 14));
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emailLabel.setBounds(0, 230, 400, 20);
        
        editProfileButton = new JButton("Edit Profile");
        editProfileButton.setContentAreaFilled(false); // Disable default background behavior
        editProfileButton.setOpaque(true);  // Make sure the background is opaque to see the color
        editProfileButton.setFocusable(false);
        editProfileButton.setFocusPainted(false);
        editProfileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editProfileButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        editProfileButton.setForeground(Color.WHITE);
        editProfileButton.setBounds(100, 280, 200, 30);
        editProfileButton.setBackground(Color.decode("#876F4D"));
        editProfileButton.addActionListener((ActionEvent e) -> {
            new EditProfilePage(user);  
            frame.dispose();
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false); // Disable default background behavior
        cancelButton.setOpaque(true);  // Make sure the background is opaque to see the color
        cancelButton.setFocusable(false);
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(100, 350, 200, 30);
        cancelButton.setBackground(Color.decode("#876F4D"));
        cancelButton.addActionListener((ActionEvent e) -> {
            new LoggedInPage(user);
            frame.dispose();            
        });
        
        MouseAdapter listener = new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                //System.out.println("You Clicked the mouse");
                //profilePictureButton.setIcon(profilePictureIcon2);
                GoldProfileIcon(userGender, profilePictureButton);
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.BLACK);
                //signUpButton.setFont(new Font("Arial", Font.PLAIN, 20));
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set border
            }

            @Override
            public void mousePressed(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                //System.out.println("You Pressed the mouse");
                //profilePictureButton.setIcon(profilePictureIcon2);
                GoldProfileIcon(userGender, profilePictureButton);
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.decode("#8A6E4B"));
                //signUpButton.setFont(new Font("Arial", Font.PLAIN, 20));
                //source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set border
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                WhiteProfileIcon(userGender, profilePictureButton);
                //profilePictureButton.setIcon(profilePictureIcon1);
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                //System.out.println("You Entered the mouse");
                //profilePictureButton.setIcon(profilePictureIcon1);
                WhiteProfileIcon(userGender, profilePictureButton);
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.decode("#514937"));
                source.setForeground(Color.WHITE);
                //signUpButton.setFont(new Font("Arial", Font.PLAIN, 20));
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set border
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                //profilePictureButton.setIcon(profilePictureIcon1);
                WhiteProfileIcon(userGender, profilePictureButton);
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            private void GoldProfileIcon(String userGender, JButton profilePictureButton) {
                if (userGender.equals("Male")) {
                    profilePictureButton.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureMaleGold.png"));
                } else if (userGender.equals("Female")) {
                    profilePictureButton.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureFemaleGold.png"));
                } else {
                    profilePictureButton.setIcon(null);
                }
            }
        };
        
        profilePictureButton.addMouseListener(listener);
        editProfileButton.addMouseListener(listener);
        cancelButton.addMouseListener(listener);
                
        frame.add(usernameLabel);
        frame.add(emailLabel);
        frame.add(profilePictureButton);
        frame.add(cancelButton);
        frame.add(editProfileButton);
        frame.setVisible(true);
      }

    private void WhiteProfileIcon(String userGender, JButton profilePictureButton) {
        if (userGender.equals("Male")) {
            profilePictureButton.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureMaleWhite.png"));
        } else if (userGender.equals("Female")) {
            profilePictureButton.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\profilePictureFemaleWhite.png"));
        } else {
            profilePictureButton.setIcon(null);
        }
    }

    
    
}
