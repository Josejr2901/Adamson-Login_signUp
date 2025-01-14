package javaux;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ForgotPasswordPage {
    
    private JFrame frame;
    private JLabel forgotPasswordLabel;
    private JTextField emailTxt;
    private JButton nextButton, cancelButton;
    
    private static final String SECRET_KEY = "mysecretkey12345";
    
    public ForgotPasswordPage() {
        frame = new JFrame("Forgot Password - ASAMSON AI");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.decode("#222222"));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        
        ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\src\\JavaUX\\adamson-logo.png");
        frame.setIconImage(image.getImage());
        
        forgotPasswordLabel = new JLabel("Enter Your Email");
        forgotPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        forgotPasswordLabel.setForeground(Color.WHITE);
        forgotPasswordLabel.setFont(new Font(null, Font.BOLD, 18));
        forgotPasswordLabel.setBounds(100, 20, 180, 50);
        
        emailTxt = new JTextField();
        emailTxt.setHorizontalAlignment(JTextField.CENTER); 
        emailTxt.setBounds(90, 90, 200, 20);
        
        nextButton = new JButton("Next");
        nextButton.setContentAreaFilled(false);
        nextButton.setOpaque(true);
        nextButton.setFocusable(false);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.setBounds(50, 150, 280, 30);
        nextButton.setBackground(Color.decode("#876F4D"));
        nextButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            String email = emailTxt.getText().trim();
            String[] userData = emailExists(email);
            
            if (userData != null) {
                openEmailFrame(userData[0], userData[1], userData[2], userData[3]);
            } else {
                JOptionPane.showMessageDialog(frame, "Email not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false);
        cancelButton.setOpaque(true);
        cancelButton.setFocusable(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBounds(50, 190, 280, 30);
        cancelButton.setBackground(Color.decode("#876F4D"));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> {
            frame.dispose();
            new MainPage(new HashMap<>());
        });
        
        emailTxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nextButton.doClick();
                }
            }
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
        
        nextButton.addMouseListener(listener);
        cancelButton.addMouseListener(listener);
        
        frame.add(forgotPasswordLabel);
        frame.add(emailTxt);
        frame.add(nextButton);
        frame.add(cancelButton);
        frame.setVisible(true);
    }
   
    private String[] emailExists(String email) {
        try (BufferedReader br = new BufferedReader(new FileReader("user_data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4) {
                    String encryptedUsername = parts[0].trim();
                    String encryptedEmail = parts[1].trim();
                    String encryptedSecurityQuestion = parts[3].trim();
                    String encryptedAnswer = parts[4].trim();
                                        
                    String decryptedUsername = decryptData(encryptedUsername);
                    String decryptedEmail = decryptData(encryptedEmail);
                    String decryptedSecurityQuestion = decryptData(encryptedSecurityQuestion);
                    String decryptedAnswer = decryptData(encryptedAnswer);
                    
                    if (decryptedEmail.equalsIgnoreCase(email)) {
                        return new String[] {decryptedUsername, decryptedEmail, decryptedSecurityQuestion, decryptedAnswer};
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void openEmailFrame(String username, String email, String securityQuestion, String answer) {
        new ChangePasswordPage(username, email, securityQuestion, answer);
        frame.dispose();
    }
    
//    private String encryptData(String data) {
//        try {
//            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
//            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
//            return Base64.getEncoder().encodeToString(encryptedBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    
    // AES Decryption method
    private String decryptData(String encryptedData) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData); // Decode the Base64 string
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes); // Return decrypted string
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
