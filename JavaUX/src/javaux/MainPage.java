
package javaux;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Map;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*

-Password Validation
*Strength indicator: Display password strength as the user types (e.g., weak, medium, strong).
*Requirements message: Show password complexity requirements (e.g., minimum length, special characters, etc.).

-Two-Factor Authentication (2FA): Enable 2FA for an added layer of security during login
*Ask for security answer before login in

-Login Attempts Limitation
*Rate-limiting: Limit the number of failed login attempts to prevent brute-force attacks. After a few failed attempts, temporarily lock the user out.

-Implement a "Forgot answer" option to recover it and change it
*/

public class MainPage extends Buttons {

    private JFrame frame;
    private JButton signUpButton, loginButton, forgotPasswordButton;
    private JLabel namePageLabel, usernameLabel, usernameIcon, passwordLabel, rememberMeLabel, dontHaveAccLabel, line2;
    private JTextField usernameTxt;
    private JPasswordField mainPasswordField;
    private JCheckBox checkBox, passwordVisibleCheckBox;
    private ImageIcon visible, notVisible, checkBoxIcon, checkedCbIcon;
    private int failedAttempts = 0; //To keep track of how many times the user has attempted to log in with an incorrect password.
    private long blockTime = 0; //Stores the time stamp of when the account was blocked after reaching the maximum number of failed attempts. used in conjuction with BLOCK_DURATION to determine if the user is currently locked out
    private final int MAX_FAILED_ATTEMPTS = 3; //A constant that defines the maximum number of allowed failed login attempts before the account is locked
    private final long BLOCK_DURATION = 60000; //Defining the lock duration in milliseconds *1 minute* [60 seconds * 1000 milliseconds = 1 minute]
    
    private User user;

    //private HashMap<String, String[]> userData; // Store username, password, email, question, answer, birthday, gender
    private Map<String, User> userData = new HashMap<>();
    private static final String SECRET_KEY = "mysecretkey12345"; // 16-byte key (128 bits)

    public MainPage(HashMap<String, String> loginInfoOriginal) {
        
        this.user = user;
        
        userData = new HashMap<>();
        loadUserData(); // Load user data on startup

    // Check if session exists and auto-login if valid
    User userFromSession = loadSessionData(); // Load the session as a User object
    if (userFromSession != null) {
        // If user is valid, proceed to the logged-in page
        new LoggedInPage(userFromSession); // Pass the User object
        frame.dispose(); // Close the login frame
        return; // Skip the login form
    }
    
    
         // Regular login UI setup
        frame = new JFrame();
        frame.setTitle("Login to ADAMSON AI");
        ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\adamson-logo.png");
        visible = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\visible1.png");
        notVisible = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\notVisible1.png");
        checkBoxIcon = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\checkBox.png");
        checkedCbIcon = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\checkedCheckBox.png");
        frame.setIconImage(image.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(440, 450);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#222222"));
        frame.setLocationRelativeTo(null);

        namePageLabel = new JLabel("ADAMSON AI");
        namePageLabel.setForeground(Color.WHITE);
        namePageLabel.setFont(new Font(null, Font.TYPE1_FONT, 30));
        namePageLabel.setBounds(0, 20, 400, 50);
        namePageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 100, 70, 20);

        usernameTxt = new JTextField();
        usernameTxt.setBounds(150, 100, 200, 20);
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
                
                if(usernameIcn.isEmpty()) {
                    usernameIcon.setIcon(null);
                } else if (usernameExists(usernameIcn)) {
                    usernameIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\CorrectGold.png"));
                } else if (!usernameExists(usernameIcn)) {
                    usernameIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\IncorrectGold.png"));
                }
            }

            private String getUsernameStatus() {
                String uNameStatus = usernameTxt.getText().trim();
                User user = userData.get(uNameStatus);
                
                if (user == null) {
                    return "Invalid email";
                }
                return null;
            }
            
        });
                
        usernameIcon = new JLabel();
        usernameIcon.setBounds(364, 100, 20, 20);

        passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 140, 70, 20);

        mainPasswordField = new JPasswordField();
        mainPasswordField.setBounds(150, 140, 200, 20);

        checkBox = new JCheckBox();
        checkBox.setBackground(Color.decode("#222222"));
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkBox.setBounds(45, 180, 20, 20);
        checkBox.setIcon(checkBoxIcon);
        checkBox.setSelectedIcon(checkedCbIcon); 

        rememberMeLabel = new JLabel("Remember me");
        rememberMeLabel.setForeground(Color.WHITE);
        rememberMeLabel.setBounds(70, 180, 100, 20);

        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setContentAreaFilled(false); // Disable default background behavior
        forgotPasswordButton.setOpaque(true);  // Make sure the background is opaque to see the color
        forgotPasswordButton.setFocusable(false);
        forgotPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.setBackground(Color.decode("#876F4D"));
        forgotPasswordButton.setForeground(Color.WHITE);
        forgotPasswordButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        forgotPasswordButton.setBounds(210, 180, 140, 20);
        forgotPasswordButton.addActionListener(e -> {
            new ForgotPasswordPage();
            frame.dispose();
        });
        
        passwordVisibleCheckBox = new JCheckBox();
        passwordVisibleCheckBox.setBackground(Color.decode("#222222"));
        passwordVisibleCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        passwordVisibleCheckBox.setBounds(360, 140, 20, 20);
        passwordVisibleCheckBox.setIcon(notVisible);
        passwordVisibleCheckBox.setSelectedIcon(visible);
        passwordVisibleCheckBox.addActionListener(new PasswordVisible());
                         
        usernameTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        mainPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
        
        checkBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
                      
        Buttons buttons = new Buttons();
        loginButton = new JButton("Log in");
        loginButton.setContentAreaFilled(false); // Disable default background behavior
        loginButton.setOpaque(true);  // Make sure the background is opaque to see the color
        buttons.setuploginButton(loginButton);
        loginButton.addActionListener(new LogInAction());

        line2 = new JLabel("----------------------------------------------------------------------------------");
        line2.setForeground(Color.decode("#8A6E4B"));
        line2.setBounds(50, 350, 330, 20);

        dontHaveAccLabel = new JLabel("Don't have an account?");
        dontHaveAccLabel.setForeground(Color.decode("#8A6E4B"));
        dontHaveAccLabel.setBounds(50, 370, 200, 20);

        signUpButton = new JButton("Sign Up");
        signUpButton.setContentAreaFilled(false); // Disable default background behavior
        signUpButton.setOpaque(true);  // Make sure the background is opaque to see the color
        signUpButton.setFocusable(false);
        signUpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpButton.setBackground(Color.decode("#876F4D"));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        signUpButton.setBounds(250, 370, 130, 20);
        signUpButton.addActionListener(e -> {
            new SignUp(this);
            //new SignUp(this).loadUserData(); //Used to decrypt data
            frame.dispose();
        });
        
        MouseAdapter listener = new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.decode("#8A6E4B"));
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
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.decode("#514937"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setBackground(Color.decode("#876F4D"));
                source.setForeground(Color.WHITE);
                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }
        };
        
        forgotPasswordButton.addMouseListener(listener);
        loginButton.addMouseListener(listener);
        signUpButton.addMouseListener(listener);
        
        frame.add(namePageLabel);
        frame.add(usernameLabel);
        frame.add(usernameTxt);
        frame.add(usernameIcon);
        frame.add(passwordLabel);
        frame.add(mainPasswordField);
        frame.add(checkBox);
        frame.add(passwordVisibleCheckBox);
        frame.add(rememberMeLabel);
        frame.add(forgotPasswordButton);
        frame.add(dontHaveAccLabel);
        frame.add(line2);
        frame.add(loginButton);
        frame.add(signUpButton);
        frame.setVisible(true);
    }
    
    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Ensure there are enough elements (username, password, email, question, answer, birthday, gender)
                    String encryptedUsername = parts[0];
                    String encryptedPassword = parts[2];
                    String encryptedEmail = parts[1];
                    String encryptedQuestion = parts[3];
                    String encryptedAnswer = parts[4];
                    String encryptedBirthday = parts[5];
                    String encryptedGender = parts[6];

                    // Decrypt all necessary data
                    String decryptedUsername = decryptData(encryptedUsername);
                    String decryptedPassword = decryptData(encryptedPassword);
                    String decryptedEmail = decryptData(encryptedEmail);
                    String decryptedQuestion = decryptData(encryptedQuestion);
                    String decryptedAnswer = decryptData(encryptedAnswer);
                    String decryptedBirthday = decryptData(encryptedBirthday);
                    String decryptedGender = decryptData(encryptedGender);

                    // Create a new User object and add it to the map
                    if (decryptedUsername != null && decryptedPassword != null) {
                        User user = new User(decryptedUsername, decryptedPassword, decryptedEmail, decryptedQuestion, decryptedAnswer, decryptedBirthday, decryptedGender);
                        userData.put(decryptedUsername, user); // Store User object in the map
                    }
                }
            }
        } catch (IOException e) {
            // Handle file reading error
            JOptionPane.showMessageDialog(frame, "Error loading user data: " + e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private class PasswordVisible implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (passwordVisibleCheckBox.isSelected()) {
                mainPasswordField.setEchoChar((char)0);
            } else {
                mainPasswordField.setEchoChar('\u2022');
            }
        }
    }
    
    private class LogInAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameTxt.getText().trim();
            String password = String.valueOf(mainPasswordField.getPassword());
            
            if (isBlocked()) {
                long timeleft = (blockTime + BLOCK_DURATION - System.currentTimeMillis()) / 1000;
                JOptionPane.showMessageDialog(frame, "Account is locked. Please try again in " + timeleft + " seconds");
                return;
            }
            
            //Check if username exists in the userData map
            User user = userData.get(username);
            
            if (user == null) { //Username does not exists
                JOptionPane.showMessageDialog(frame, "Invalid Username.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!user.getPassword().equals(password)) { //Username exists but password is incorrect
                JOptionPane.showMessageDialog(frame, "Invalid Password. Attempts left: " + (MAX_FAILED_ATTEMPTS - failedAttempts - 1));
                failedAttempts++; //Increments the failedAttempts value by 1
                if (failedAttempts >= MAX_FAILED_ATTEMPTS) { //Check if the failedAttempts has reached its maximum permited 
                    blockTime = System.currentTimeMillis(); //If failedAttempts do the following
                    JOptionPane.showMessageDialog(frame, "Too many failed attempts. Account is locked for 1 minute");
                }
            } else { //Login successful
                failedAttempts = 0; //sets finalAttempts back to 0
                if (checkBox.isSelected()) { //Checks if the checkBox is selected
                    //Encrypts and saves session data
                    String encryptedUsername = encryptData(username);
                    String encryptedPassword = encryptData(password);
                    
                    //Save encrypted data in session.txt
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("session.txt"))) {
                        writer.write(encryptedUsername + "\n" + encryptedPassword);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                //Proceed to LoggInPage and passes the User object
                new SecurityQuestionLogInPage(user);
                frame.dispose();
            }
        }
    }

    private boolean isBlocked() {
        return failedAttempts >= MAX_FAILED_ATTEMPTS && System.currentTimeMillis() < blockTime + BLOCK_DURATION;
    }
    
    private User loadSessionData() {
        try {
            // Check if the session file exists
            if (!Files.exists(Paths.get("session.txt"))) {
                return null; // If the file doesn't exist, there's no session
            }

            // If the file exists, read the session data (encrypted username)
            try (BufferedReader reader = new BufferedReader(new FileReader("session.txt"))) {
                String encryptedUsername = reader.readLine();
                if (encryptedUsername != null) {
                    // Decrypt the username before checking it
                    String username = decryptData(encryptedUsername);
                    if (username != null && userData.containsKey(username)) {
                        return userData.get(username); // Return the User object if valid
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // No session or invalid session
}

    // AES Encryption method (same as before)
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

    // AES Decryption method (same as before)
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
    
    private boolean usernameExists(String username) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String decryptedUsername = decryptData(userData[0]); // Because the username is the first field
                if (decryptedUsername.equals(username)) {
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
    
    public void refreshLoginInfo() {
        // Logic to refresh the login information (e.g., reloading user data)
        System.out.println("Login information refreshed!");
        loadUserData(); // Reload the user data to ensure it's up-to-date
    }
}

