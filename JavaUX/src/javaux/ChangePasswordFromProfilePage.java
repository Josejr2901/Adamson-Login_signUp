// 
//package javaux;
// 
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.*;
//import java.util.Base64;
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//
//public class ChangePasswordFromProfilePage {
//    
//    private JFrame frame;
//    private JLabel changePasswordLabel;
//    private JTextField emailTxt;
//    private JButton nextButton, cancelButton;
//    
//    private static final String SECRET_KEY = "mysecretkey12345";
//    
//    public ChangePasswordFromProfilePage(User user) {
//        
//        String userEmail = user.getEmail();
//
//        frame = new JFrame("Change Your Password - ADAMSON AI");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 300);
//        frame.setResizable(false);
//        frame.getContentPane().setBackground(Color.decode("#222222"));
//        frame.setLayout(null);
//        frame.setLocationRelativeTo(null);
//        
//        ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\javaux\\adamson-logo.png");
//        frame.setIconImage(image.getImage());
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        changePasswordLabel = new JLabel("Please confirm your Email");
//        changePasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        changePasswordLabel.setForeground(Color.WHITE);
//        changePasswordLabel.setFont(new Font(null, Font.BOLD, 18));
//        changePasswordLabel.setBounds(80, 20, 230, 50);
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        emailTxt = new JTextField();
//        emailTxt.setBounds(90, 90, 200, 20);
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        nextButton = new JButton("Confirm");
//        nextButton.setContentAreaFilled(false);
//        nextButton.setOpaque(true);
//        nextButton.setFocusable(false);
//        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        nextButton.setBounds(50, 150, 280, 30);
//        nextButton.setBackground(Color.decode("#876F4D"));
//        nextButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//        nextButton.setForeground(Color.WHITE);
//        nextButton.addActionListener(e -> {
//            String email = emailTxt.getText().trim();
//            String[] userData = emailExists(email);
//            
//            if (email.equals(userEmail)) {
//                    openEmailFrame(user);
//            } else {
//                JOptionPane.showMessageDialog(frame, "Wrong email entered!", "Error", JOptionPane.ERROR_MESSAGE);
//            }            
//        });
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        cancelButton = new JButton("Cancel");
//        cancelButton.setContentAreaFilled(false);
//        cancelButton.setOpaque(true);
//        cancelButton.setFocusable(false);
//        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        cancelButton.setBounds(50, 190, 280, 30);
//        cancelButton.setBackground(Color.decode("#876F4D"));
//        cancelButton.setForeground(Color.WHITE);
//        cancelButton.addActionListener(e -> {
//            frame.dispose();
//            new EditProfilePage(user);
//        });
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        emailTxt.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    nextButton.doClick();
//                }
//            }
//        });
//        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        MouseAdapter listener = new MouseAdapter() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JButton source = (JButton) e.getSource();
//                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//                source.setBackground(Color.WHITE);
//                source.setForeground(Color.BLACK);
//                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                JButton source = (JButton) e.getSource();
//                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//                source.setBackground(Color.WHITE);
//                source.setForeground(Color.decode("#8A6E4B"));
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                JButton source = (JButton) e.getSource();
//                source.setBackground(Color.decode("#876F4D"));
//                source.setForeground(Color.WHITE);
//                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                JButton source = (JButton) e.getSource();
//                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//                source.setBackground(Color.decode("#514937"));
//                source.setForeground(Color.WHITE);
//                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                JButton source = (JButton) e.getSource();
//                source.setBackground(Color.decode("#876F4D"));
//                source.setForeground(Color.WHITE);
//                source.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
//            }
//        };
//
//        nextButton.addMouseListener(listener);
//        cancelButton.addMouseListener(listener);
//
//        frame.setVisible(true);
//        frame.add(changePasswordLabel);
//        frame.add(emailTxt);
//        frame.add(nextButton);
//        frame.add(cancelButton);        
//    }
//
//    private String[] emailExists(String email) {
//        try (BufferedReader br = new BufferedReader(new FileReader("user_data.txt"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                if(parts.length > 4) {
//                    String encryptedEmail = parts[1].trim();
//                    String encryptedUsername = parts[0].trim();
//                    String encryptedSecurityQuestion = parts[3].trim();
//                    
//                    String decryptedEmail = decryptData(encryptedEmail);
//                    String decryptedUsername = decryptData(encryptedUsername);
//                    String decryptedSecurityQuestion = decryptData(encryptedSecurityQuestion);
//                    
//                    if (decryptedEmail.equalsIgnoreCase(email)) {
//                        return new String[] {decryptedUsername, decryptedSecurityQuestion};
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void openEmailFrame(User user) {
//        new ResetPasswordFromProfilePage(user); 
//        frame.dispose();
//    }
//
////    // AES Encryption method (same as before)
////    private String encryptData(String data) {
////        try {
////            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
////            Cipher cipher = Cipher.getInstance("AES");
////            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
////            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
////            return Base64.getEncoder().encodeToString(encryptedBytes); // Convert to Base64 string for storage
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
//
//    // AES Decryption method
//    private String decryptData(String encryptedData) {
//        try {
//            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, keySpec);
//            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData); // Decode the Base64 string
//            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
//            return new String(decryptedBytes); // Return decrypted string
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
