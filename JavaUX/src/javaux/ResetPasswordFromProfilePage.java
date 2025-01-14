 
package javaux;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ResetPasswordFromProfilePage {

    private JFrame frame;
    private JLabel usernameLabel, emailLabel, resetPasswordLabel, newPasswordLabel, reEnterPasswordLabel, securityQuestionLabel, questionLabel, answerLabel, emailIcon, answerIcon;
    private JTextField emailTxt, securityAnswerTxt;
    private JPasswordField newPasswordField, reEnterPasswordField;
    private JButton resetPasswordButton, cancelButton;
    private JCheckBox passwordVisibleCB1, passwordVisibleCB2;
    private ImageIcon image, visible, notVisible;
    
    private User user;
     
    private static final String SECRET_KEY = "mysecretkey12345"; // 16-byte key (128 bits)
    
    public ResetPasswordFromProfilePage(User user) {
        
        String username = user.getUsername();
        String email = user.getEmail();
        String securityQuestion = user.getQuestion();
        String securityQuestionAnswer = user.getAnswer();
        
        frame = new JFrame("ResetPassword - ADAMSON AI");
        frame.setSize(420, 440);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#222222"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

        image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\adamson-logo.png");
        frame.setIconImage(image.getImage());
        
        visible = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\visible1.png");
        notVisible = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\notVisible1.png");
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        resetPasswordLabel = new JLabel("Reset Password of " + username);
        resetPasswordLabel.setForeground(Color.WHITE);
        resetPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resetPasswordLabel.setFont(new Font(null, Font.BOLD, 18));
        resetPasswordLabel.setBounds(0, 20, 400, 50);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        securityQuestionLabel = new JLabel("Security Question");
        securityQuestionLabel.setForeground(Color.WHITE);
        securityQuestionLabel.setBounds(50, 90, 120, 20);

        questionLabel = new JLabel(securityQuestion);
        questionLabel.setForeground(Color.decode("#876F4D"));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setBounds(180, 90, 150, 20);

        answerLabel = new JLabel("Security Answer");
        answerLabel.setForeground(Color.WHITE);
        answerLabel.setBounds(50, 130, 120, 20);

        securityAnswerTxt = new JTextField();
        securityAnswerTxt.setBounds(180, 130, 150, 20);
        securityAnswerTxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                UpdateAnswerIconAndToolTip();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                UpdateAnswerIconAndToolTip();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                UpdateAnswerIconAndToolTip();
            }

            private void UpdateAnswerIconAndToolTip() {
                String answerIcn = securityAnswerTxt.getText().trim();
                
                String answerStatus = getAnswerStatus();
                answerIcon.setToolTipText(answerStatus);
                
                if (answerIcn.isEmpty()) {
                    answerIcon.setIcon(null);
                } else if (answerIcn.equals(securityQuestionAnswer)) {
                    answerIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\CorrectGold.png"));
                } else if (!answerIcn.equals(securityQuestionAnswer)) {
                    answerIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\IncorrectGold.png"));
                }
            }

            private String getAnswerStatus() {
                String ansStatus = securityAnswerTxt.getText().trim();
                
                if (!ansStatus.equals(securityQuestionAnswer)) {
                    return "Incorrect answer!";
                }
                return null;
            }
            
        });
         
        answerIcon = new JLabel();
        answerIcon.setBounds(344, 130, 20, 20); 
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        emailLabel = new JLabel("Re-Enter your Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(50, 170, 120, 20);

        emailTxt = new JTextField();
        emailTxt.setBounds(180, 170, 150, 20);
        emailTxt.getDocument().addDocumentListener(new DocumentListener() { 
            @Override
            public void insertUpdate(DocumentEvent e) {
                UpdateEmailIconAndToolTip();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                UpdateEmailIconAndToolTip();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                UpdateEmailIconAndToolTip();
            }

            private void UpdateEmailIconAndToolTip() {
                String emailIcn = emailTxt.getText().trim();
                
                String emailStatus = getEmailStatus();
                emailIcon.setToolTipText(emailStatus);
                
                if (emailIcn.isEmpty()) {
                    emailIcon.setIcon(null);
                } else if (emailIcn.equals(email)) {
                    emailIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\CorrectGold.png"));
                } else if (!emailIcn.equals(email)) {
                    emailIcon.setIcon(new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\IncorrectGold.png"));
                }
            }

            private String getEmailStatus() {
                String emailSts = emailTxt.getText().trim();
                
                if (!emailSts.equals(email)) {
                    return "Wrong email!";
                }
                return null;
            }
        
        });
        
        emailIcon = new JLabel(); 
        emailIcon.setBounds(344, 170, 20, 20);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setForeground(Color.WHITE);
        newPasswordLabel.setBounds(50, 210, 120, 20);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(180, 210, 150, 20);
                
        passwordVisibleCB1 = new JCheckBox();
        passwordVisibleCB1.setBackground(Color.decode("#222222"));
        passwordVisibleCB1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        passwordVisibleCB1.setBounds(340, 210, 20, 20);
        passwordVisibleCB1.setIcon(notVisible);
        passwordVisibleCB1.setSelectedIcon(visible);
        passwordVisibleCB1.addActionListener(new PasswordVisible1());
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        reEnterPasswordLabel = new JLabel("Re-Enter Password");
        reEnterPasswordLabel.setForeground(Color.WHITE);
        reEnterPasswordLabel.setBounds(50, 250, 120, 20);

        reEnterPasswordField = new JPasswordField();
        reEnterPasswordField.setBounds(180, 250, 150, 20);
                
        passwordVisibleCB2 = new JCheckBox();
        passwordVisibleCB2.setBackground(Color.decode("#222222"));
        passwordVisibleCB2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        passwordVisibleCB2.setBounds(340, 250, 20, 20);
        passwordVisibleCB2.setIcon(notVisible);
        passwordVisibleCB2.setSelectedIcon(visible);
        passwordVisibleCB2.addActionListener(new PasswordVisible2());
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setContentAreaFilled(false);
        resetPasswordButton.setOpaque(true);
        resetPasswordButton.setFocusable(false);
        resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        resetPasswordButton.setBounds(50, 300, 310, 30);
        resetPasswordButton.setBackground(Color.decode("#876F4D"));
        resetPasswordButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.addActionListener(new ResetPasswordAction());
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false);
        cancelButton.setOpaque(true);
        cancelButton.setFocusable(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBounds(50, 340, 310, 30);
        cancelButton.setBackground(Color.decode("#876F4D"));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new EditProfilePage(user); 
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        securityAnswerTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    resetPasswordButton.doClick();
            }
        });
                
        emailTxt.addKeyListener(new KeyAdapter() { 
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    resetPasswordButton.doClick();
                }
            }
        });

        newPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    resetPasswordButton.doClick();
                }
            }
        });

        reEnterPasswordField.addKeyListener(new KeyAdapter() { 
            @Override 
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    resetPasswordButton.doClick();
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        MouseAdapter listener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setBackground(Color.WHITE);
                source.setForeground(Color.BLACK);
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
               source.setForeground(Color.WHITE);
               source.setBackground(Color.decode("#876F4D"));
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

        resetPasswordButton.addMouseListener(listener);
        cancelButton.addMouseListener(listener);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        frame.add(resetPasswordLabel);
        frame.add(securityQuestionLabel);
        frame.add(questionLabel);
        frame.add(answerLabel);
        frame.add(securityAnswerTxt);
        frame.add(answerIcon);
        frame.add(emailLabel);
        frame.add(emailTxt);
        frame.add(emailIcon);
        frame.add(newPasswordLabel);
        frame.add(newPasswordField);
        frame.add(passwordVisibleCB1);
        frame.add(reEnterPasswordLabel);
        frame.add(reEnterPasswordField);
        frame.add(passwordVisibleCB2);
        frame.add(resetPasswordButton);
        frame.add(cancelButton);   
        frame.setVisible(true);       
    }
    
    public class PasswordVisible1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (passwordVisibleCB1.isSelected()) {
                newPasswordField.setEchoChar((char)0);
            } else {
                newPasswordField.setEchoChar('\u2022');
            }
        }
        
    }
    
    public class PasswordVisible2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
             if (passwordVisibleCB2.isSelected()) {
                 reEnterPasswordField.setEchoChar((char)0);
             } else {
                 reEnterPasswordField.setEchoChar('\u2022');
             }
        }
        
    }
    
    private class ResetPasswordAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailTxt.getText().trim();
            String answer = securityAnswerTxt.getText().trim();
            String newPassword = new String(newPasswordField.getPassword());
            String reEnterPassword = new String(reEnterPasswordField.getPassword());
            
            if (email.isEmpty() || answer.isEmpty() || newPassword.isEmpty() || reEnterPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!newPassword.equals(reEnterPassword)) {
                JOptionPane.showMessageDialog(frame, "Password do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (newPassword.length() < 8 || newPassword.length() > 16) {
                JOptionPane.showMessageDialog(frame, "Password must be between 8 and 16 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            HashMap<String, String> userData = loadUserData();
            String encryptedEmail = encryptData(email);
            String encryptedAnswer = encryptData(answer);
            
            String key = encryptedEmail + ":" + encryptedAnswer;
            
            if (userData.containsKey(key)) {
                String encryptedNewPassword = encryptData(newPassword);
                
                saveNewPasswordToFile(encryptedEmail, encryptedNewPassword);
                
                JOptionPane.showMessageDialog(frame, "Password reset successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                frame.dispose();
                new MainPage(userData); 
            } else {
                JOptionPane.showMessageDialog(frame, "Email or security answer is incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private HashMap<String, String> loadUserData() {
        HashMap<String, String> userData = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String encryptedEmail = parts[1];
                    String encryptedAnswer = parts[4];
                    String encryptedPassword = parts[2];
                    
                    userData.put(encryptedEmail + ":" + encryptedAnswer, encryptedPassword);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }
    
    private void saveNewPasswordToFile(String encryptedEmail, String encryptedNewPassword) {
        try {
            File file = new File("user_data.txt");
            File tempFile = new File("user_data_temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(encryptedEmail)) {
                    writer.write(parts[0] + "," + parts[1] + "," + encryptedNewPassword + "," + parts[3] + "," + parts[4] + "," + parts[5] + "," + parts[6]);
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
            
            reader.close();
            writer.close();
            
            if (file.delete()) {
                tempFile.renameTo(file);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
}
