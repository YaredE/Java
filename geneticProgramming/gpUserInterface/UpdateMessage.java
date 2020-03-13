package gpUserInterface;

import javax.swing.*;

/**
 * <p>Updates the message on the GUI</p>
 * 
 * @author Yared K Estifanos
 *
 */
public class UpdateMessage extends Thread 
{

	/**
	 * <p>The text area to display message on</p>
	 */
   private JTextArea outputArea;
   
   /**
    * <p>The message to output</p>
    */
   private String messageToOutput;
  
   // initialize outputArea and message
   public UpdateMessage( JTextArea output, String message )
   {
      outputArea = output;
      messageToOutput = message;
	  new Thread(this).start();    
   }

   // method called to update outputArea
   public void run()
   {
	   	setMessage();
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setMessage();
   }
   public void setMessage()
   {
	   SwingUtilities.invokeLater(new Runnable(){
	   	public void run()
	   	{
	   	   outputArea.append( messageToOutput );
	   	 }
	   });		   
   }
} 

