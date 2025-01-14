 
package javaux;
 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditProfilePage {

    private String usernameString;
    
    private JFrame frame;
    private JLabel titleLabel, currentInfoLabel, newInfoLabel, usernameLabel, currentUsernameLabel, emailLabel, currentEmailLabel, 
                   phoneNumberLabel, birthdayLabel, currentBirthdayLabel, birthdayDateLabel, genderLabel, currentGenderLabel, line1, genderIcon;
    private JRadioButton maleButton, femaleButton;
    private JTextField newUsernameTxt, newEmailTxt, phoneNumberTxt;
    private JButton deleteAccountButton, changePasswordButton, saveChangesButton, cancelButton;
    private JComboBox<Integer> yearDropdown, dayDropdown;
    private JComboBox<String> monthDropdown;
    
    private User user;
    
    // AES key for encryption and decryption (same as before)
    private static final String SECRET_KEY = "mysecretkey12345"; // 16-byte key (128 bits)
    
    public EditProfilePage(User user) {
        
        String username = user.getUsername();
        String userEmail = user.getEmail();
        String userBirthday = user.getBirthday();
        String userGender = user.getGender();
        
        // Parse the userBirthday string to a LocalDate object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        LocalDate birthdayDate = LocalDate.parse(userBirthday, formatter);

        // Extract the day, month, and year from the parsed LocalDate
        int day = birthdayDate.getDayOfMonth();
        int month = birthdayDate.getMonthValue();
        int year = birthdayDate.getYear();
        
        frame = new JFrame("Edit Profile");
        frame.setSize(540, 560); //Add 85 more of width
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#222222"));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\adamson-logo.png");
        frame.setIconImage(image.getImage());
              
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        titleLabel = new JLabel("Edit Profile Information");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(null, Font.TYPE1_FONT, 20));
        titleLabel.setBounds(50, 20, 250, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        currentInfoLabel = new JLabel("Current Information");
        currentInfoLabel.setForeground(Color.decode("#876F4D"));
        currentInfoLabel.setFont(new Font(null, Font.TYPE1_FONT, 15));
        currentInfoLabel.setBounds(50, 60, 182, 50);
        
        newInfoLabel = new JLabel("Enter New Information");
        newInfoLabel.setForeground(Color.decode("#876F4D")); 
        newInfoLabel.setFont(new Font(null, Font.TYPE1_FONT, 15));
        newInfoLabel.setBounds(265, 60, 210, 50);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 120, 62, 20);
        
        currentUsernameLabel = new JLabel(username);
        currentUsernameLabel.setForeground(Color.decode("#876F4D"));
        currentUsernameLabel.setBounds(120, 120, 140, 20); //x, y, width, height

        newUsernameTxt = new JTextField();
        newUsernameTxt.setToolTipText("Only use alphanumeric values and/or '_', no spaces allowed"); 
        newUsernameTxt.setBounds(265, 120, 210, 20);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 160, 110, 20);
        
        currentEmailLabel = new JLabel(userEmail);
        currentEmailLabel.setForeground(Color.decode("#876F4D"));
        currentEmailLabel.setBounds(120, 160, 140, 20); //x, y, width, height
        
        newEmailTxt = new JTextField();
        newEmailTxt.setBounds(265, 160, 210, 20);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        birthdayLabel = new JLabel("Birthday");
        birthdayLabel.setForeground(Color.WHITE);
        birthdayLabel.setBounds(50, 200, 110, 20);
        
        currentBirthdayLabel = new JLabel(userBirthday);
        currentBirthdayLabel.setForeground(Color.decode("#876F4D"));
        currentBirthdayLabel.setBounds(120, 200, 140, 20); //x, y, width, height

        /* Day Dropdown */  
        dayDropdown = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayDropdown.addItem(i);
        }
        dayDropdown.setSelectedItem(day);
        dayDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dayDropdown.setBounds(265, 200, 50, 20);

        /* Month Dropdown */
        monthDropdown = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthDropdown.addItem(getMonthName(i)); // You need a method like getMonthName for the month names
        }
        monthDropdown.setSelectedItem(getMonthName(month));
        monthDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        monthDropdown.setBounds(315, 200, 95, 20);

        /* Year Dropdown */
        yearDropdown = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            yearDropdown.addItem(i);
        }
        yearDropdown.setSelectedItem(year);
        yearDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        yearDropdown.setBounds(410, 200, 65, 20);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        genderLabel = new JLabel("Gender");
        genderLabel.setForeground(Color.WHITE);
        genderLabel.setBounds(50, 240, 110, 20);
        
        currentGenderLabel = new JLabel(userGender);
        currentGenderLabel.setForeground(Color.decode("#876F4D"));
        currentGenderLabel.setBounds(120, 240, 160, 20);  

        maleButton = new JRadioButton("Male");
        maleButton.setBackground(Color.decode("#222222"));
        maleButton.setForeground(Color.WHITE);
        maleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        maleButton.setBounds(265, 240, 80, 20);

        femaleButton = new JRadioButton("Female");
        femaleButton.setBackground(Color.decode("#222222"));
        femaleButton.setForeground(Color.WHITE);
        femaleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        femaleButton.setBounds(355, 240, 80, 20);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
                
        if("Male".equals(userGender)) {
            maleButton.setSelected(true);
        } else {
            femaleButton.setSelected(true);
        }
        
        genderIcon = new JLabel();
        genderIcon.setBounds(455, 240, 20, 20);
        genderIconMethod(userGender, genderIcon);
        //genderIcon.addActionListener(new GenderIconAction());
        maleButton.addActionListener(new GenderIconAction());
        femaleButton.addActionListener(new GenderIconAction());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////     
        newUsernameTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesButton.doClick();
                }
            }
         });
        
        newEmailTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesButton.doClick();
                }
            }
        });
        
        maleButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesButton.doClick();
                }
            }
        });
        
        femaleButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesButton.doClick();
                }
            }
        });
        
        yearDropdown.addKeyListener(new KeyAdapter() {
           @Override
           public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   saveChangesButton.doClick();
               }
           }
        });
        
        dayDropdown.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesButton.doClick();
                }
            }
        });
        monthDropdown.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    saveChangesButton.doClick();
                }
            }
        });
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
        changePasswordButton = new JButton("Change Password"); 
        changePasswordButton.setContentAreaFilled(false);
        changePasswordButton.setOpaque(true);
        changePasswordButton.setFocusable(false);
        changePasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        changePasswordButton.setBackground(Color.decode("#876F4D"));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); 
        changePasswordButton.setBounds(50, 290, 210, 30);
        changePasswordButton.addActionListener(e -> {
            new ResetPasswordFromProfilePage(user);
            frame.dispose();
        });       
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
        deleteAccountButton = new JButton("Delete Account"); 
        deleteAccountButton.setContentAreaFilled(false);
        deleteAccountButton.setOpaque(true);
        deleteAccountButton.setFocusable(false);
        deleteAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteAccountButton.setBackground(Color.decode("#876F4D"));
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); 
        deleteAccountButton.setBounds(265, 290, 210, 30);
        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentUsername = currentUsernameLabel.getText().trim();
                String currentEmail = currentEmailLabel.getText().trim();

                // Load user data
                HashMap<String, String> userData = loadUserData();

                // Check if the username and email match any entry in the database
                String encryptedCurrentEmail = encryptData(currentEmail);
                String encryptedCurrentUsername = encryptData(currentUsername);

                String key = encryptedCurrentEmail + ":" + encryptedCurrentUsername;

                if (userData.containsKey(key)) {
                    // If user found, ask for confirmation
                    int response = JOptionPane.showConfirmDialog(frame, 
                            "Are you sure you want to delete your account? This action cannot be undone.",
                            "Delete Account", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        // Proceed to delete the account
                        deleteUserData(encryptedCurrentEmail, encryptedCurrentUsername);

                        // Close the frame and redirect to the main page
                        frame.dispose();
                        new MainPage(userData); // Or another appropriate page
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "The information does not match any account in the database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
        line1 = new JLabel("----------------------------------------------------------------------------------------------------------");
        line1.setForeground(Color.decode("#8A6E4B"));
        line1.setBounds(50, 330, 425, 20);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////         
        saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setContentAreaFilled(false); // Disable default background behavior
        saveChangesButton.setOpaque(true);  // Make sure the background is opaque to see the color
        saveChangesButton.setFocusable(false);
        saveChangesButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveChangesButton.setBackground(Color.decode("#876F4D"));
        saveChangesButton.setForeground(Color.WHITE);
        saveChangesButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        saveChangesButton.setBounds(50, 370, 425, 50);
        saveChangesButton.addActionListener(new ResetInfoAction());
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                  
        cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false); // Disable default background behavior
        cancelButton.setOpaque(true);  // Make sure the background is opaque to see the color
        cancelButton.setFocusable(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBackground(Color.decode("#876F4D"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        cancelButton.setBounds(50, 430, 425, 50);
        cancelButton.addActionListener(e -> {
            String newUsername = newUsernameTxt.getText().trim();
            String newEmail = newEmailTxt.getText().trim();
            
            if(!newUsername.isEmpty() || !newEmail.isEmpty()) {
                int response = JOptionPane.showConfirmDialog(frame, 
                            "Are you sure you want exit without saving the changes?",
                            "Exit without saving", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        frame.dispose();
                        new ProfilePage(user); 
                    }
                } 
            
            if (newUsername.isEmpty() && newEmail.isEmpty()) {
                frame.dispose();
                new ProfilePage(user); 
            }
        });       
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
               
        MouseAdapter listener = new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                //System.out.println("You Clicked the mouse");
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
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.decode("#8A6E4B"));
                //signUpButton.setFont(new Font("Arial", Font.PLAIN, 20));
                //source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set border
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                //System.out.println("You Entered the mouse");
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.decode("#514937"));
                source.setForeground(Color.WHITE);
                //signUpButton.setFont(new Font("Arial", Font.PLAIN, 20));
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Set border
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
        };
        
        changePasswordButton.addMouseListener(listener);
        deleteAccountButton.addMouseListener(listener);
        saveChangesButton.addMouseListener(listener);
        cancelButton.addMouseListener(listener);
        
        frame.setVisible(true);
        frame.add(titleLabel);
        frame.add(currentInfoLabel);
        frame.add(newInfoLabel);
        frame.add(usernameLabel);
        frame.add(currentUsernameLabel);
        frame.add(newUsernameTxt);
        frame.add(emailLabel);
        frame.add(currentEmailLabel);
        frame.add(newEmailTxt);
        frame.add(birthdayLabel);
        frame.add(currentBirthdayLabel);
        frame.add(genderLabel);
        frame.add(currentGenderLabel);
        frame.add(maleButton);
        frame.add(femaleButton);
        frame.add(genderIcon);
        frame.add(line1);
        frame.add(yearDropdown);
        frame.add(dayDropdown);
        frame.add(monthDropdown);
        frame.add(deleteAccountButton);
        frame.add(changePasswordButton);
        frame.add(saveChangesButton);
        frame.add(cancelButton);
    }

    private void genderIconMethod(String userGender, JLabel genderIcon) {
        if (userGender.equals("Male")) {
            genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconMaleGold16px.png"));
        } else if (userGender.equals("Female")) {
            genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconFemaleGold16px.png"));
        }
    }
    
    
    private class GenderIconAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(maleButton.isSelected()) {
                genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconMaleGold16px.png"));
            } else if(femaleButton.isSelected()) {
                genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconFemaleGold16px.png"));
            }
        }
        
    }
    
    private void deleteUserData(String encryptedEmail, String encryptedUsername) {
        try {
            // Open the original file and the temporary file
            File file = new File("user_data.txt");
            File tempFile = new File("user_data_temp.txt");

            // Ensure that the temp file exists or create it
            if (!tempFile.exists()) {
                tempFile.createNewFile();
            }

            // Read from the original file and write to the temp file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean foundUser = false;

            // Read through each line in the original file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // Check if the line matches the user to be deleted
                    String storedEmail = parts[1].trim();
                    String storedUsername = parts[0].trim();

                    // Compare the encrypted email and username
                    if (storedEmail.equals(encryptedEmail) && storedUsername.equals(encryptedUsername)) {
                        foundUser = true;  // We found the user to delete
                        continue;  // Skip writing this line to the temp file
                    }
                }

                // Write the current line to the temp file if it doesn't match the user to delete
                writer.write(line);
                writer.newLine();
            }

            // Close the readers and writers
            reader.close();
            writer.close();

            // If we found and deleted the user, replace the original file
            if (foundUser) {
                // Delete the original file and rename the temp file
                if (file.delete()) {
                    boolean renamed = tempFile.renameTo(file);
                    if (!renamed) {
                        throw new IOException("Failed to rename temp file to original file");
                    }
                    JOptionPane.showMessageDialog(frame, "Your account has been successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    throw new IOException("Failed to delete original file");
                }
            } else {
                // If the user was not found, notify the user
                JOptionPane.showMessageDialog(frame, "No matching account found to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while deleting account data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    // Action listener for updating the username and email
    private class ResetInfoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentUsername = currentUsernameLabel.getText().trim();  // Get current username from label
            String currentEmail = currentEmailLabel.getText().trim();  // Get current email from label
            String newUsername = newUsernameTxt.getText().trim();  // New username from text field
            String newEmail = newEmailTxt.getText().trim();  // New email from text field
            int day = (int) dayDropdown.getSelectedItem();
            String month = (String) monthDropdown.getSelectedItem();
            int year = (int) yearDropdown.getSelectedItem();
            String newBirthday = String.format("%d %s %d", day, month, year);
            String newGender = maleButton.isSelected() ? "Male" : "Female";

            if (newUsername.isEmpty()) {
                newUsername = currentUsername;
            } 
            
            if (!newUsername.matches("[a-zA-Z0-9_]+")) {
                JOptionPane.showMessageDialog(null, "'Username' not valid [No Spaces, and use only alphanumeric valaues and/or '_'", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (newEmail.isEmpty()) {
                newEmail = currentEmail;
            }
            
            // Check if email format is valid
            if (!newEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|ca|org|net|info)$")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid 'Email'", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check if the username already exists
            if (!currentUsername.equals(newUsername)) {
                if (isUsernameTaken(newUsername)){
                    JOptionPane.showMessageDialog(null, "This username is already taken. Please try another one.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop further processing if username exists
                }
            }

            // Check if the email already exists
            if (!currentEmail.equals(newEmail) ) {
                if (isEmailTaken(newEmail)) {
                    JOptionPane.showMessageDialog(null, "This email is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop further processing if email exists
                }
            }

            // Check if username format is valid
            if (!newUsername.matches("[a-zA-Z0-9_]+")) {
                JOptionPane.showMessageDialog(null, "'Username' not valid [No spaces, and use only alphanumeric values and/or '_']", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Load existing users data
            HashMap<String, String> userData = loadUserData();
            String encryptedCurrentEmail = encryptData(currentEmail); // Encrypt current email
            String encryptedCurrentUsername = encryptData(currentUsername); // Encrypt current username

            // Combine current email and username to form the key
            String key = encryptedCurrentEmail + ":" + encryptedCurrentUsername;

            // Check if the combined key exists in the user data map
            if (userData.containsKey(key)) {
                // User found, update username and email
                String encryptedNewEmail = encryptData(newEmail);  // Encrypt new email
                String encryptedNewUsername = encryptData(newUsername);  // Encrypt new username
                String encryptedNewBirthday = encryptData(newBirthday);
                String encryptedNewGender = encryptData(newGender);
                
                // Save the updated username and email to the file
                saveUpdatedDataToFile(encryptedCurrentEmail, encryptedNewEmail, encryptedCurrentUsername, encryptedNewUsername, encryptedNewBirthday, encryptedNewGender);

                JOptionPane.showMessageDialog(frame, "Information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                new MainPage(userData);  // Refresh the main page
            } else {
                JOptionPane.showMessageDialog(frame, "Current username or email is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
 
        private boolean isUsernameTaken(String username) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    String decryptedUsername = decryptData(userData[0]); // Because the username is the first field
                    if (decryptedUsername.equalsIgnoreCase(username)) {
                        reader.close();
                        return true; // Username already exists
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false; // Username not found
        }
        
        private boolean isEmailTaken(String email) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");
                    String decryptedEmail = decryptData(userData[1]); // Because the username is the second field
                    if (decryptedEmail.equalsIgnoreCase(email)) {
                        reader.close();
                        return true; // Username already exists
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false; // Username not found
        }

    // Helper method to load user data from file
    private HashMap<String, String> loadUserData() {
        HashMap<String, String> userData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String encryptedUsername = parts[0];  // Encrypted username
                    String encryptedEmail = parts[1];  // Encrypted email
                    // Storing username and email combination as the key with the rest of the line as value
                    userData.put(encryptedEmail + ":" + encryptedUsername, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }
    
    // Save the updated username and email to the file
    private void saveUpdatedDataToFile(String encryptedCurrentEmail, String encryptedNewEmail, String encryptedCurrentUsername, 
                                       String encryptedNewUsername, String encryptedNewBirthday, String encryptedNewGender) {
        try {
            // Read the old user data, and update the username and email for the given email and username
            File file = new File("user_data.txt");
            File tempFile = new File("user_data_temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(encryptedCurrentUsername) && parts[1].equals(encryptedCurrentEmail)) {
                    // Replace the username and email with the new values
                    writer.write(encryptedNewUsername + "," + encryptedNewEmail + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + encryptedNewBirthday + "," + encryptedNewGender);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            reader.close();
            writer.close();

            // Replace the original file with the updated one
            if (file.delete()) {
                tempFile.renameTo(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    private String encryptData(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
     private String decryptData(String encryptedData) {
            try {
                SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
                byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
                return new String(decryptedBytes);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
     
    private static String getMonthName(int month) {
            switch (month) {
                case 1: return "January";
                case 2: return "February";
                case 3: return "March";
                case 4: return "April";
                case 5: return "May";
                case 6: return "June";
                case 7: return "July";
                case 8: return "August";
                case 9: return "September";
                case 10: return "October";
                case 11: return "November";
                case 12: return "December";
                default: return ""; // Should not reach here
            }
        }
    
}
