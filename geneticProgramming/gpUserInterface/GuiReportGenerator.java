package gpUserInterface;

/*
 * GuiReportGenerator.java
 * By Yared Estifanos
 *
 */

import globals.GAUtility;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * class GuiReportGenerator defines an application program that gives a GUI based
 * interface for users to monitor mail log activities.
 *
 */
public class GuiReportGenerator extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GAController controller1;
	private JPanel gPanel1, gPanel2;
	private Graph graph1;
	private JTextArea outputArea;


	/**
	 * Constructs a GuiReportGenerator object
	 */
	public GuiReportGenerator()
	{
		super("Genetic Algorithm Monitor");
		Container c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.LIGHT_GRAY);
		
		outputArea = new JTextArea(10,60);
		(controller1 = new GAController(graph1, outputArea)).setBounds(15, 5, 210, 550);
		controller1.setGraph(graph1 = new Graph(new GraphData(GAUtility.targetFunctionData, null)));
		setSize(950, 700);
		controller1.setBorder(BorderFactory.createBevelBorder(0));
		c.add(controller1);
		setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			 public void windowClosing(WindowEvent e){
				 System.exit(0);
			 }
		 });
		(gPanel1 = new JPanel()).setBounds(250, 5, 680, 450);
		gPanel1.setBorder(BorderFactory.createBevelBorder(0));
		gPanel1.setBackground(Color.WHITE);
		
		JLabel labelHeader = new JLabel("Target Function");
		Font lblFont = new Font("Serif", Font.ITALIC, 16);
		labelHeader.setFont(lblFont);
		labelHeader.setBackground(Color.red);  
		labelHeader.setOpaque(true);
		gPanel1.add(labelHeader); 
		
		JLabel lblHeader = new JLabel("Function Obtained");
		lblFont = new Font("Serif", Font.TRUETYPE_FONT, 17);
		lblHeader.setFont(lblFont);
		lblHeader.setForeground(Color.BLACK);
		lblHeader.setBackground(Color.BLUE);  
		lblHeader.setOpaque(true);
		gPanel1.add(lblHeader); 
		
		
		c.add(gPanel1);
		(gPanel2 = new JPanel()).setBounds(250, 450, 680, 340);
		gPanel2.add(new JScrollPane(outputArea));
		gPanel2.setBorder(BorderFactory.createLoweredBevelBorder());

		
		c.add(gPanel2);
		gPanel1.setLayout(new FlowLayout());
		gPanel1.add(graph1);
		Runtime.getRuntime().gc();
	}

	/**
	 * method main is the driver method for this application
	 * @param args
	 */
	public static void main(String[] args)
	{
		(new GuiReportGenerator()).setVisible(true);
	}
}


