
package javaux;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SignUp {

    private JFrame frame;
    private JButton signUpButton, cancelButton;
    private JLabel signUpLabel, usernameLabel, emailLabel, passwordLabel, birthdayLabel, genderLabel, genderIcon, line1, confirmPasswordLabel, securityQuestionLabel,
                   usernameIcon, emailIcon, securityAnswerIcon;
    private JTextField usernameTxt, emailTxt, securityAnswerTxt;
    private JPasswordField signUpPasswordField, confirmPasswordField;
    private JCheckBox passwordVisibleCB1, passwordVisibleCB2;
    private ImageIcon visible, notVisible, icon;
    private JRadioButton maleButton, femaleButton;
    private JComboBox<Integer> yearDropdown, dayDropdown;
    private JComboBox<String> monthDropdown, securityQuestionDropdown;
    private MainPage mainPage; // Reference to MainPage

    // AES key for encryption and decryption (in real apps, don't hardcode keys)
    private static final String SECRET_KEY = "mysecretkey12345"; // 16-byte key (128 bits)
    
        public SignUp(MainPage mainPage) {
            this.mainPage = mainPage; // Initialize reference
            frame = new JFrame();
            frame.setTitle("Sign Up to ADAMSON AI");
            ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\TableTask\\src\\tabletask\\adamson-logo.png");
            visible = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\visible1.png"); 
            notVisible = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\notVisible1.png");
            frame.setIconImage(image.getImage());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(490, 550);
            frame.getContentPane().setBackground(Color.decode("#222222"));
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            signUpLabel = new JLabel("Sign Up Now");
            signUpLabel.setForeground(Color.WHITE);
            signUpLabel.setFont(new Font(null, Font.TYPE1_FONT, 20));
            signUpLabel.setBounds(50, 20, 130, 50);
            signUpLabel.setHorizontalAlignment(SwingConstants.LEFT);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            usernameLabel = new JLabel("Username");
            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setBounds(50, 100, 110, 20);

            usernameTxt = new JTextField();
            usernameTxt.setBounds(200, 100, 200, 20);
            usernameTxt.setToolTipText("Only use alphanumeric values and/or '_', no spaces allowed"); 
            usernameTxt.getDocument().addDocumentListener(new DocumentListener() { 
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateUsernameIconAndToolTip();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateUsernameIconAndToolTip();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateUsernameIconAndToolTip();
                }

                private void updateUsernameIconAndToolTip() {
                    String usernameIcn = usernameTxt.getText().trim();
                    String usernameStatus = getUsernameStatus();
        
                    usernameIcon.setToolTipText(usernameStatus);
                    
                    if (usernameIcn.isEmpty()) {
                        usernameIcon.setIcon(null);
                    } else if(isUsernameTaken(usernameIcn) || !usernameIcn.matches("[a-zA-z0-9_]+")) {
                        usernameIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IncorrectGold.png"));
                    } else if (!isUsernameTaken(usernameIcn) && usernameIcn.matches("[a-zA-z0-9_]+")) { 
                        usernameIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\CorrectGold.png"));
                    }
                } 
                
                private String getUsernameStatus() {
                    String usernameStatus = usernameTxt.getText().trim();

                    if (!usernameStatus.matches("[a-zA-Z0-9_]+")) {
                        return "Invalid username entered!";
                    }

                    if (isUsernameTaken(usernameStatus)) {
                        return "Username already in use!";
                    }

                    return null;
                }
            });
            
            usernameIcon = new JLabel(new ImageIcon());
            usernameIcon.setBounds(414, 100, 20, 20);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            emailLabel = new JLabel("Email");
            emailLabel.setForeground(Color.WHITE);
            emailLabel.setBounds(50, 140, 110, 20);

            emailTxt = new JTextField();
            emailTxt.setBounds(200, 140, 200, 20);
            emailTxt.getDocument().addDocumentListener(new DocumentListener() { 
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateEmailIconAndToolTip();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateEmailIconAndToolTip();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateEmailIconAndToolTip();
                }

                private void updateEmailIconAndToolTip() {
                    String emailIcn = emailTxt.getText().trim();
                    String emailStatus = getEmailStatus();
                    
                    emailIcon.setToolTipText(emailStatus);
                    
                    if (emailIcn.isEmpty()) {
                        emailIcon.setIcon(null);
                    } else if (isEmailTaken(emailIcn) || !emailIcn.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|ca|org|net|info)$")) {
                        emailIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IncorrectGold.png"));
                    } else if (!isEmailTaken(emailIcn) && emailIcn.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|ca|org|net|info)$")) {
                        emailIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\CorrectGold.png"));
                    }
                }
                
                private String getEmailStatus() {
                    String emailStatus = emailTxt.getText().trim();

                    if (!emailStatus.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|ca|org|net|info)$")) {
                        return "Invalid email entered!";
                    }

                    if (isEmailTaken(emailStatus)) {
                        return "Email already in use";
                    } 

                    return null;
                }                
            });
            
            emailIcon = new JLabel();
            emailIcon.setBounds(414, 140, 20, 20);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            passwordLabel = new JLabel("Password");
            passwordLabel.setForeground(Color.WHITE);
            passwordLabel.setBounds(50, 180, 110, 20);

            signUpPasswordField = new JPasswordField();
            signUpPasswordField.setToolTipText("8 to 16 characters long");
            signUpPasswordField.setBounds(200, 180, 200, 20);        
            
            passwordVisibleCB1 = new JCheckBox();
            passwordVisibleCB1.setBackground(Color.decode("#222222"));
            passwordVisibleCB1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            passwordVisibleCB1.setBounds(410, 180, 20, 20);
            passwordVisibleCB1.setIcon(notVisible);
            passwordVisibleCB1.setSelectedIcon(visible);
            passwordVisibleCB1.addActionListener(new PasswordVisible1());
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            confirmPasswordLabel = new JLabel("Confirm Password");
            confirmPasswordLabel.setForeground(Color.WHITE);
            confirmPasswordLabel.setBounds(50, 220, 110, 20);

            confirmPasswordField = new JPasswordField();
            confirmPasswordField.setToolTipText("8 to 16 characters long");
            confirmPasswordField.setBounds(200, 220, 200, 20);
            
            passwordVisibleCB2 = new JCheckBox();
            passwordVisibleCB2.setBackground(Color.decode("#222222"));
            passwordVisibleCB2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            passwordVisibleCB2.setBounds(410, 220, 20, 20);
            passwordVisibleCB2.setIcon(notVisible);
            passwordVisibleCB2.setSelectedIcon(visible);
            passwordVisibleCB2.addActionListener(new PasswordVisible2());
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                      
            securityQuestionLabel = new JLabel("Security Question");
            securityQuestionLabel.setForeground(Color.WHITE);
            securityQuestionLabel.setBounds(50, 260, 110, 20);

            String[] questions = {"Favourite food", "1st Pet's name", "Birth's City", "Highschool name", "Favourite color", "Your Nickname"};
            securityQuestionDropdown = new JComboBox(questions);
            securityQuestionDropdown.setBounds(200, 260, 110, 20);
            
            securityAnswerTxt = new JTextField();
            securityAnswerTxt.setBounds(314, 260, 86, 20);
            securityAnswerTxt.setToolTipText("Use alphanumeric values only"); 
            securityAnswerTxt.getDocument().addDocumentListener(new DocumentListener() { 
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateSecurityQuestionIconAndToolTip();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateSecurityQuestionIconAndToolTip();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateSecurityQuestionIconAndToolTip();
                }

                private void updateSecurityQuestionIconAndToolTip() {
                    String securityAnswerIcn = securityAnswerTxt.getText().trim();
                    
                    String securityAnswerStatus = getSecurityAnswerStatus();
                    
                    securityAnswerIcon.setToolTipText(securityAnswerStatus);
                    
                    if (securityAnswerIcn.isEmpty()) {
                        securityAnswerIcon.setIcon(null);
                    } else if (securityAnswerIcn.matches("[a-zA-Z0-9 ]+")) {
                        securityAnswerIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\CorrectGold.png"));
                    } else {
                        securityAnswerIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IncorrectGold.png"));
                    }
                }

                private String getSecurityAnswerStatus() {
                    String securityAnsStatus = securityAnswerTxt.getText().trim();
                    
                    if (!securityAnsStatus.matches("[a-zA-Z0-9 ]+")) {
                        return "Wrong format used!";
                    }
                    
                    return null;
                }
                
            });
            
            securityAnswerIcon = new JLabel();
            securityAnswerIcon.setBounds(414, 260, 20, 20);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
            birthdayLabel = new JLabel("Birthday");
            birthdayLabel.setForeground(Color.WHITE);
            birthdayLabel.setBounds(50, 300, 110, 20);

            /* Day Dropdown */  
            dayDropdown = new JComboBox<>();
            for (int day = 1; day <= 31; day++) {
                dayDropdown.addItem(day);
            }
            dayDropdown.setSelectedItem(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dayDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            dayDropdown.setBounds(200, 300, 50, 20);

            /* Month Dropdown */
            monthDropdown = new JComboBox<>();
            for (int month = 1; month <= 12; month++) {
                monthDropdown.addItem(getMonthName(month));
            }
            monthDropdown.setSelectedItem(getMonthName(Calendar.getInstance().get(Calendar.MONTH) + 1));
            monthDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            monthDropdown.setBounds(250, 300, 90, 20);

            /* Year Dropdown */
            yearDropdown = new JComboBox<>();
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int year = currentYear; year >= 1900; year--) { 
                yearDropdown.addItem(year);
            }
            yearDropdown.setSelectedItem(currentYear);
            yearDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            yearDropdown.setBounds(340, 300, 60, 20);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            genderLabel = new JLabel("Gender");
            genderLabel.setForeground(Color.WHITE);
            genderLabel.setBounds(50, 340, 110, 20);

            maleButton = new JRadioButton("Male");
            maleButton.setBackground(Color.decode("#222222"));
            maleButton.setForeground(Color.WHITE);
            maleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            maleButton.setBounds(200, 340, 80, 20);

            femaleButton = new JRadioButton("Female");
            femaleButton.setBackground(Color.decode("#222222"));
            femaleButton.setForeground(Color.WHITE);
            femaleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            femaleButton.setBounds(290, 340, 80, 20);

            ButtonGroup group = new ButtonGroup();
            group.add(maleButton);
            group.add(femaleButton);
          
            maleButton.addActionListener(new GenderIconAction());
            femaleButton.addActionListener(new GenderIconAction());       
            
            genderIcon = new JLabel();
            genderIcon.setBounds(385, 340, 20, 20);
            genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconGold16px.png"));
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////     
            line1 = new JLabel("-----------------------------------------------------------------------------------------------");
            line1.setForeground(Color.decode("#8A6E4B")); 
            line1.setBounds(50, 360, 380, 20); 
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            signUpButton = new JButton("Sign Up");
            signUpButton.setContentAreaFilled(false); // Disable default background behavior
            signUpButton.setOpaque(true);  // Make sure the background is opaque to see the color
            signUpButton.setFocusable(false);
            signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            signUpButton.setBackground(Color.decode("#876F4D"));
            signUpButton.setForeground(Color.WHITE);
            signUpButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            signUpButton.setBounds(50, 410, 380, 50);
            signUpButton.addActionListener(e -> saveUserData());
            //signUpButton.addActionListener(this);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            icon = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\iconX1.png");
            cancelButton = new JButton();
            cancelButton.setFocusable(false);
            cancelButton.setContentAreaFilled(false); // Disable default background behavior
            cancelButton.setOpaque(true);  // Make sure the background is opaque to see the color
            cancelButton.setFocusPainted(false);
            cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cancelButton.setBackground(Color.decode("#876F4D"));
            cancelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            cancelButton.setIcon(icon);
            cancelButton.setBounds(410, 30, 30, 30);
            cancelButton.addActionListener(e -> {
                frame.dispose();
                new MainPage(new HashMap<>());
            });
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
            usernameTxt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });

            emailTxt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            signUpPasswordField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            confirmPasswordField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            securityAnswerTxt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            dayDropdown.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            monthDropdown.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            yearDropdown.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            securityAnswerTxt.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            maleButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
                }
            });
            
            femaleButton.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        signUpButton.doClick();
                    }
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
        
        signUpButton.addMouseListener(listener);
        cancelButton.addMouseListener(listener);
        
            frame.add(signUpLabel);
            frame.add(usernameLabel);
            frame.add(usernameTxt);
            frame.add(usernameIcon);
            frame.add(emailLabel);
            frame.add(emailTxt);
            frame.add(emailIcon);
            frame.add(passwordLabel);
            frame.add(signUpPasswordField);
            frame.add(confirmPasswordLabel);
            frame.add(confirmPasswordField);
            frame.add(passwordVisibleCB1);
            frame.add(passwordVisibleCB2);
            frame.add(birthdayLabel);
            frame.add(dayDropdown);
            frame.add(monthDropdown);
            frame.add(yearDropdown);
            frame.add(genderLabel);
            frame.add(maleButton);
            frame.add(femaleButton); 
            frame.add(genderIcon);
            frame.add(securityQuestionLabel);
            frame.add(securityQuestionDropdown);
            frame.add(securityAnswerTxt);
            frame.add(securityAnswerIcon);
            frame.add(signUpButton);
            frame.add(cancelButton);
            frame.add(line1);
            frame.setVisible(true);
        }

    private class GenderIconAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == maleButton) {
                genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconMaleGold16px.png"));
            } else if (e.getSource() == femaleButton) {
                genderIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\IconFemaleGold16px.png"));
            }
        }
    }



    private void saveUserData() {
        String password1 = new String(signUpPasswordField.getPassword());
        String password2 = new String(confirmPasswordField.getPassword());

        // Check for empty fields or invalid input
        if (usernameTxt.getText().isEmpty() || emailTxt.getText().isEmpty() || signUpPasswordField.getPassword().length == 0 || 
            confirmPasswordField.getPassword().length == 0 || securityAnswerTxt.getText().isEmpty() || !maleButton.isSelected() && !femaleButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!usernameTxt.getText().trim().matches("[a-zA-Z0-9_]+")) {
            JOptionPane.showMessageDialog(null, "'Username' not valid [No spaces, and use only alphanumeric values and/or '_']", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!emailTxt.getText().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|ca|org|net|info)$")) {
            JOptionPane.showMessageDialog(null, "Please enter a valid 'Email'", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (password1.length() < 8 || password1.length() > 16) {
            JOptionPane.showMessageDialog(null, "Password must be between 8 and 16 characters long", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (!securityAnswerTxt.getText().trim().matches("[a-zA-Z0-9 ]+")) {
            JOptionPane.showMessageDialog(null, "'Answer' not valid [Use only alphanumeric values]", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

             String username = usernameTxt.getText().trim();
            // Check if the username already exists
            if (isUsernameTaken(username)) {
                JOptionPane.showMessageDialog(null, "This username is already taken. Please try another one.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop further processing if username exists
            }

           String email = emailTxt.getText().trim();
            // Check if the Email already exists
            if (isEmailTaken(email)) {
                JOptionPane.showMessageDialog(null, "This Email is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop further processing if username exists
            }

            String question = (String) securityQuestionDropdown.getSelectedItem();
            String answer = securityAnswerTxt.getText().trim();
            int day = (int) dayDropdown.getSelectedItem();
            String month = (String) monthDropdown.getSelectedItem();
            int year = (int) yearDropdown.getSelectedItem();
            String birthday = String.format("%d %s %d", day, month, year);
            String gender = maleButton.isSelected() ? "Male" : "Female";


            String encryptedUsername = encryptData(username); // Encrypt the username
            String encryptedEmail = encryptData(email); // Encrypt the email
            String encryptedPassword = encryptData(password1); // Encrypt the password
            String encryptedQuestion = encryptData(question); //Encrypt the security question
            String encryptedAnswer = encryptData(answer); //Encrypt the security question's answer
            String encryptedBirthday = encryptData(birthday); // Encrypt the birthday
            String encryptedGender = encryptData(gender); // Encrypt the gender

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data.txt", true))) {
                writer.write(encryptedUsername + "," + encryptedEmail + "," + encryptedPassword + "," + encryptedQuestion + "," + encryptedAnswer + "," + encryptedBirthday + "," +encryptedGender); // Write user data
                writer.newLine();
                JOptionPane.showMessageDialog(frame, "Sign up successful!");
                frame.dispose();
                mainPage.refreshLoginInfo(); // Refresh login info after signup
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            frame.dispose();
            new MainPage(new HashMap<>());
        }
    }

    private class PasswordVisible1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (passwordVisibleCB1.isSelected()) {
                signUpPasswordField.setEchoChar((char)0);
            } else {
                signUpPasswordField.setEchoChar('\u2022');
            }
        }
    }    

    private class PasswordVisible2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (passwordVisibleCB2.isSelected()) {  
                confirmPasswordField.setEchoChar((char)0);
            } else {
                confirmPasswordField.setEchoChar('\u2022');
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

    // AES Encryption and Decryption methods

    private String encryptData(String data) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes); // Convert to Base64 string for storage
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
            
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    //Method to decrypt the data Only used when I want to decrypt data                                                                                                      //                        
//    public void loadUserData() {                                                                                                                                            // 
//        try {                                                                                                                                                               //
//            // Simulating reading an encrypted string from a file (replace with actual file read)                                                                           //                                                                                               
//            String encryptedUsername = "vAlfFI8d++DvoG4InFhQ9A=="; // Example encrypted Username                                                                            //           
//            String encryptedEmail = "mWvCfnWEguRlwMWg0+GsteQpVnttnO9ZKwNe/b3s59k="; // Example encrypted Email                                                              //
//            String encryptedPassword = "KQVegCfm5EYPAHrTMQBtEQ=="; // Example encrypted Password                                                                            //  
//            String encryptedQuestion = "6OE3jmTGVZs8zzTJnM/GSQ=="; // Example encrypted Security Question                                                                   //  
//            String encryptedAnswer = "8cHphbaOXyDPnkfDu0R6ag==";   Example encrypted Security Question Answer                                                               //  
//            String encryptedBirthday = "Xr8BYV/kQvwC6VsGc1Vy+Y5nAHGB+IMk5A9PQkcNqTQ="; // Example encrypted Birthday                                                        //                
//            String encryptedGender = "CYax17Gj5hSFUZ5DPOY7Wg=="; // Example encrypted Gender                                                                                //                                                                                                                                 
//                                                                                                                                                                            // 
//            // Decrypt the data using the decryptData method                                                                                                                //
//            String decryptedUsername = decryptData(encryptedUsername);                                                                                                      //                           
//            String decryptedEmail = decryptData(encryptedEmail);                                                                                                            // 
//            String decryptedPassword = decryptData(encryptedPassword);                                                                                                      //  
//            String decryptedQuestion = decryptData(encryptedQuestion);                                                                                                      //
//            String decryptedAnswer = decryptData(encryptedAnswer);                                                                                                          //
//            String decryptedBirthday = decryptData(encryptedBirthday);                                                                                                      //       
//            String decryptedGender = decryptData(encryptedGender);                                                                                                          // 
//                                                                                                                                                                            //                
//                                                                                                                                                                            // 
//            // Use the decrypted data as needed                                                                                                                             //
//            System.out.println("Decrypted Username: " + decryptedUsername);                                                                                                 //
//            System.out.println("Decrypted Email: " + decryptedEmail);                                                                                                       //
//            System.out.println("Decrypted Password: " + decryptedPassword);                                                                                                 //
//            System.out.println("Decrypted Question: " + decryptedQuestion);                                                                                                 //
//            System.out.println("Decrypted Answer: " + decryptedAnswer);                                                                                                     //
//            System.out.println("Decrypted Birthday: " + decryptedBirthday);                                                                                                 //            
//            System.out.println("Decrypted Gender: " + decryptedGender);                                                                                                     //                 
//                                                                                                                                                                            //
//        } catch (Exception e) {                                                                                                                                             //
//            e.printStackTrace();                                                                                                                                            //
//        }                                                                                                                                                                   //
//    }                                                                                                                                                                       //                                                    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
