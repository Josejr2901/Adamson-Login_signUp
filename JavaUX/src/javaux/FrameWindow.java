//
//package javaux;
//
//
//import java.awt.Button;
//import java.awt.Frame;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//
//public class FrameWindow extends Frame 
//	implements WindowListener
//{
//	public FrameWindow(String FrameTitle) {
//		//Display the Frame Window
//	    super(FrameTitle);
//	    setSize(460, 200);
//	    setLocation(100,100); 
//	    addWindowListener(this);
//	    
//	    //Sample 01: Create 9 Buttons and Set Grid bag Layout
//	    Button btn1 = new Button("Btn 1");
//	    Button btn2 = new Button("Btn 2");
//	    Button btn3 = new Button("Btn 3");
//	    Button btn4 = new Button("Btn 4");
//	    Button btn5 = new Button("Btn 5");
//	    Button btn6 = new Button("Btn 6");
//	    Button btn7 = new Button("Btn 7");
//	    Button btn8 = new Button("Btn 8");
//	    Button btn9 = new Button("Btn 9");
//	    GridBagLayout gbl = new GridBagLayout();
//	    setLayout(gbl);		
//	    
//	    //Sample 02: Get Gridbag Layout
//
//	    GridBagConstraints gcon = new GridBagConstraints();
//	    gcon.weightx = 1;
//	    gcon.weighty = 1;
//	    gcon.fill = GridBagConstraints.BOTH;
//	    
//	    //Sample 03: Add Btn1 & Btn2
//	    //3.1 Prepare Contraints for Btn 1
//	    gcon.gridy = 0;
//	    gcon.gridx = 0;
//	    gcon.gridwidth = 2;
//	    gcon.gridheight=1;
//	    //3.2 Add Control
//	    gbl.setConstraints(btn1, gcon);
//	    add(btn1);
//	    
//	    //3.3 Prepare Contraints for Btn 2
//	    gcon.gridx = 2;
//
//	    //3.4 Add Control
//	    gbl.setConstraints(btn2, gcon);
//	    add(btn2);	  
//	    
//	    //Sample 04: Add Btn3, Btn4, Btn5, Btn6
//	    gcon.gridy = 1;
//	    gcon.gridwidth = 1;
//	    
//	    gcon.gridx = 0;
//	    gbl.setConstraints(btn3, gcon);
//	    add(btn3);
//	    
//	    gcon.gridx = 1;
//	    gbl.setConstraints(btn4, gcon);
//	    add(btn4);
//	    
//	    gcon.gridx = 2;
//	    gbl.setConstraints(btn5, gcon);
//	    add(btn5);
//	    
//	    gcon.gridx = 3;
//	    gbl.setConstraints(btn6, gcon);
//	    add(btn6);
//	    
//	    //Sample 05: Add Btn 7
//	    gcon.gridx = 0;
//	    gcon.gridy = 2;
//	    gcon.gridwidth =3;
//	    gbl.setConstraints(btn7, gcon);
//	    add(btn7);
//	    
//	    //Sample 06: Add Btn 8
//	    gcon.gridx = 0;
//	    gcon.gridy = 3;
//	    gcon.gridwidth = 3;
//	    gbl.setConstraints(btn8, gcon);
//	    add(btn8);	    
//	    
//	    //Sample 07: Add Btn 9
//	    gcon.gridx = 3;
//	    gcon.gridy = 2;
//	    gcon.gridwidth = 1;
//	    gcon.gridheight = 2;
//	    gbl.setConstraints(btn9, gcon);
//	    add(btn9);
//	    
//	}
//
//	public void windowOpened(WindowEvent e) {}
//	public void windowClosed(WindowEvent e) {}
//	public void windowIconified(WindowEvent e) {}
//	public void windowDeiconified(WindowEvent e) {}
//	public void windowActivated(WindowEvent e) {}
//	public void windowDeactivated(WindowEvent e) {}
//	public void windowClosing(WindowEvent e) {
//		this.dispose();
//	}
//}