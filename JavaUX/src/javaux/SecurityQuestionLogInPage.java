
package javaux;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityQuestionLogInPage {

    private JFrame frame;
    private JLabel titleLabel, securityQuestionLabel;
    private JTextField securityAnswerTxt;
    private JButton continueButton, cancelButton;
    private int failedAttempts = 0; //To keep track of how many times the user has attempted to log in with an incorrect password.
    private long blockTime = 0; //Stores the time stamp of when the account was blocked after reaching the maximum number of failed attempts. used in conjuction with BLOCK_DURATION to determine if the user is currently locked out
    private final int MAX_FAILED_ATTEMPTS = 3; //A constant that defines the maximum number of allowed failed login attempts before the account is locked
    private final long BLOCK_DURATION = 60000; //Defining the lock duration in milliseconds *1 minute* [60 seconds * 1000 milliseconds = 1 minute]
    
    private static final String SECRET_KEY = "mysecretkey12345";
        
    public SecurityQuestionLogInPage(User user) {
        
        String securityQuestion = user.getQuestion();
        String answer = user.getAnswer();
        
        frame = new JFrame("LogIn Menu ADAMSON-AI");
        //frame.setTitle("LogIn Menu ADAMSON-AI");
        frame.setSize(400, 360);
        frame.getContentPane().setBackground(Color.decode("#222222"));
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); 
        
        ImageIcon image = new ImageIcon("C:\\Users\\Jose.m\\Documents\\NetBeansProjects\\JavaUX\\src\\adamson-logo.png");
        frame.setIconImage(image.getImage());
        
        titleLabel = new JLabel("Please answer the security question");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font(null, Font.BOLD, 18)); 
        titleLabel.setBounds(0, 20, 380, 50);
        
        securityQuestionLabel = new JLabel(securityQuestion);
        securityQuestionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        securityQuestionLabel.setForeground(Color.decode("#876F4D"));
        securityQuestionLabel.setFont(new Font(null, Font.BOLD, 16));
        securityQuestionLabel.setBounds(90, 90, 200, 20);
        
        securityAnswerTxt = new JTextField();
        securityAnswerTxt.setHorizontalAlignment(JTextField.CENTER);
        securityAnswerTxt.setBounds(90, 130, 200, 20);
        
        continueButton = new JButton("Continue");
        continueButton.setContentAreaFilled(false);
        continueButton.setOpaque(true);
        continueButton.setFocusable(false);
        continueButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        continueButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        continueButton.setBackground(Color.decode("#876F4D"));
        continueButton.setForeground(Color.WHITE); 
        continueButton.setBounds(50, 200, 280, 30); 
        //continueButton.addActionListener(new NextAction());
        continueButton.addActionListener(e -> {
//            String checkAnswer = securityAnswerTxt.getText().trim();
//            
//            if (checkAnswer.equals(answer)) {
//                frame.dispose();
//                new LoggedInPage(user);
//            } else {
//                JOptionPane.showMessageDialog(frame, "Wrong answer, please try again!", "Error", JOptionPane.ERROR_MESSAGE);
//            }
            
            String securityAnswer = securityAnswerTxt.getText().trim();
            
            if (isBlocked()) {
                long timeleft = (blockTime + BLOCK_DURATION - System.currentTimeMillis()) / 1000;
                JOptionPane.showMessageDialog(frame, "Account is locked. Please try again in " + timeleft + " seconds");
                return;
            }
            
            if (!securityAnswer.equals(answer)) {
                JOptionPane.showMessageDialog(frame, "Invalid Password. Attempts left: " + (MAX_FAILED_ATTEMPTS - failedAttempts - 1));
                failedAttempts++;
                if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                    blockTime = System.currentTimeMillis();
                    JOptionPane.showMessageDialog(frame, "Too many failed attempts. Account is locked for 1 minute");
                } 
            } else {
                failedAttempts = 0;
                
                frame.dispose();
                new LoggedInPage(user);
            }
            
        });
        
        cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false);
        cancelButton.setOpaque(true);
        cancelButton.setFocusable(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        cancelButton.setBackground(Color.decode("#876F4D"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(50, 240, 280, 30);
        cancelButton.addActionListener( e -> {
            frame.dispose();
            new MainPage(new HashMap<>());
        });
        
        securityAnswerTxt.addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    continueButton.doClick();
                }
            }
        });
        
        MouseAdapter listener = new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                source.setBackground(Color.WHITE);
                source.setForeground(Color.decode("#8A6E4B"));
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
        
        continueButton.addMouseListener(listener);
        cancelButton.addMouseListener(listener); 
        
        frame.add(titleLabel);
        frame.add(securityQuestionLabel);
        frame.add(securityAnswerTxt);
        frame.add(continueButton);
        frame.add(cancelButton);
        frame.setVisible(true);
        
    }
    
//    private class NextAction implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String securityAnswer = securityAnswerTxt.getText().trim();
//            
//            if (isBlocked()) {
//                long timeleft = (blockTime + BLOCK_DURATION - System.currentTimeMillis()) / 1000;
//                JOptionPane.showConfirmDialog(frame, "Account is locked. Please try again in " + timeleft + " seconds");
//                return;
//            }
//            
//            if (!securityAnswer.equals(answer)) {
//                JOptionPane.showMessageDialog(frame, "Invalid Password. Attempts left: " + (MAX_FAILED_ATTEMPTS - failedAttempts));
//                failedAttempts++;
//                if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
//                    blockTime = System.currentTimeMillis();
//                    JOptionPane.showMessageDialog(frame, "Too many failed attempts. Account is locked for 1 minute");
//                } 
//            } else {
//                failedAttempts = 0;
//                
//                frame.dispose();
//                new LoggedInPage(user);
//            }
//        }
//        
//    }
    
    private boolean isBlocked() {
        return failedAttempts >= MAX_FAILED_ATTEMPTS && System.currentTimeMillis() < blockTime + BLOCK_DURATION;
    }
    
}
