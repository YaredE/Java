package gpUserInterface;


import globals.GAUtility;
import gpDataAccessLayer.FileParser;
import gpRunningEngine.GARunningEngine;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 *<p>
 *class Graph controller gives is used to get information from users
 * and pass that information to Graph objects to generate graphs.
 * </p>
 */
public class GAController extends JPanel implements ActionListener,ItemListener
{

	/**
	 * <p>The serial version UID</p>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>The list of choices for running mode (Default vs. custom)</p>
	 */
	private Choice choice;
	
	/**
	 * <p>Represents buttons used in this GUI</p>
	 */
	private JButton clear, run, browse;
	
	/**
	 * <p>Represents the graph object that is 
	 * used for drawing the target and found functions</p>
	 */
	private Graph  graphArea;
	
	/**
	 * <p>Represents the output area for displaying 
	 * processing information</p>
	 */
	private JTextArea outputArea;
	
	/**
	 * <p>Represents the file chooser used by the browse
	 * button</p>
	 */
	private JFileChooser fileChooser;
	
	/**
	 * <p>Represents the different text fields used in the 
	 * application</p>
	 */
	private JTextField initialPopulation,fileName,
					   maximumGeneration,crossOverprobablity,
					   mutationProbability,depthIndividual,
					   maximumDepthIndividual,closenesstoFitnes;
	
	/**
	 * <p>Represents some control to enable and disable text fields based on
	 * some selection made (dafault vs run with custom values</p>
	 */
	private boolean enabled;
	
	/**
	 * <p>Represents the running engine for this application</p>
	 */
	private GARunningEngine mRunningEngine;
	
	/**
	 * <p>Progress bar</p>
	 */
	private JProgressBar progressBar;


	/**
	 * constructs a GraphController object
	 * @param _graphArea 
	 * @param _outputArea 
	 */
	public GAController(Graph _graphArea, JTextArea _outputArea)
	{
		this.setSize(350, 450);
		this.setGraphArea(_graphArea);
		this.setOutputArea(_outputArea);
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(20,2));
		JLabel title = new JLabel("Genetic Algorithm Main Menu");
		add(title,BorderLayout.CENTER);
		panel.add(title);
		
		choice = new Choice();
		choice.add("Select running mode");
		choice.add("Run with default values");
		choice.add("Run with custom (changed) values");
		choice.addItemListener(this);
		add(choice);		
		panel.add(choice);
		
		panel.add(new JLabel(" Initial population: "));
		initialPopulation = new JTextField();
		panel.add(initialPopulation);
		initialPopulation.setText(GAUtility.initialPopulationSize+"");
		initialPopulation.setEnabled(enabled);
		
		panel.add(new JLabel(" Maximum number of generations: "));
		maximumGeneration = new JTextField();
		panel.add(maximumGeneration);
		maximumGeneration.setText(GAUtility.maximumNumberofGenerations+"");
		maximumGeneration.setEnabled(enabled);
		
		panel.add(new JLabel(" Crossover Probability: "));
		crossOverprobablity = new JTextField(2);
		panel.add(crossOverprobablity);
		crossOverprobablity.setText(GAUtility.crossoverProbability+"");
		crossOverprobablity.setEnabled(enabled);
		
		panel.add(new JLabel(" Mutation probability: "));
		mutationProbability = new JTextField();
		panel.add(mutationProbability);
		mutationProbability.setText(GAUtility.mutationProbability+"");
		mutationProbability.setEnabled(enabled);
		
		panel.add(new JLabel(" Initial depth of Individual Tree: "));
		depthIndividual = new JTextField();
		panel.add(depthIndividual);
		depthIndividual.setText(GAUtility.initalDepthofIndividualTree+"");
		depthIndividual.setEnabled(enabled);
		
		
		panel.add(new JLabel(" Maximum depth of Individual Tree: "));
		maximumDepthIndividual= new JTextField();
		panel.add(maximumDepthIndividual);
		maximumDepthIndividual.setText(GAUtility.maximumAllowedDepthofIndividualTree+"");
		maximumDepthIndividual.setEnabled(enabled);
		
		panel.add(new JLabel( "Closeness to fitness:"));
		closenesstoFitnes= new JTextField();
		panel.add(closenesstoFitnes);
		closenesstoFitnes.setText(500D+"");
		closenesstoFitnes.setEnabled(enabled);
		
		panel.add(new JLabel(" Training data file path: "));
		fileName= new JTextField();
		panel.add(fileName);
		fileName.setText(GAUtility.trainingDataFileName);
		fileName.setEnabled(enabled);
	
		browse = new JButton(" Browse....");
		browse.setEnabled(enabled);
		
		
		fileChooser = new JFileChooser();
		browse.addActionListener(this);		    
		panel.add(browse);		    
		    
        progressBar = new JProgressBar(0, 100);
		panel.add(progressBar);
		progressBar.setEnabled(enabled);
		panel.setOpaque(true);
	
		add(panel,BorderLayout.NORTH);

		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		clear = new JButton ("Clear");
		clear.addActionListener(this);
		run = new JButton("Run");
		run.addActionListener(this);
		panel.add(clear);
		panel.add(run);
		add(panel,BorderLayout.SOUTH);
		setVisible(true);
	}
	
	/**
	 * @return the graphArea
	 */
	public Graph getGraphArea() {
		return graphArea;
	}

	/**
	 * @param graphArea the graphArea to set
	 */
	public void setGraphArea(Graph graphArea) {
		this.graphArea = graphArea;
	}

	/**
	 * @return the outputArea
	 */
	public JTextArea getOutputArea() {
		return outputArea;
	}

	/**
	 * @param outputArea the outputArea to set
	 */
	public void setOutputArea(JTextArea outputArea) {
		this.outputArea = outputArea;
	}

	/**
	 * handles events that the user inputs to generate graphs
	 * @param e 
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == browse)
		{
			int returnVal = 
				fileChooser.showOpenDialog(GAController.this);
			       if (JFileChooser.APPROVE_OPTION == returnVal) {
			        	String fileName = 
			        		fileChooser.getSelectedFile()!= null ?
			            fileChooser.getSelectedFile().getAbsolutePath(): null;
			            if (fileName != null)
			            {
				            this.fileName.setText(fileName);
				            if (GAUtility.ofileParser != null)
				            {
				            	GAUtility.ofileParser.setFileName(fileName);
				            }
				            else{
				            	GAUtility.ofileParser = new FileParser(fileName);
				            }
							GraphData graphData = 
								new GraphData(GAUtility.targetFunctionData,
										null);	
							this.graphArea.setGraphData(graphData);	 
							this.outputArea.setText("");
							this.outputArea.repaint();
							this.graphArea.repaint();
			            }
			        }else{
			        	fileChooser.cancelSelection();
			        }
		}
		if (e.getSource() == run)
		{
				
				int runningMode = choice.getSelectedIndex();		
				if (runningMode == 0)
				{
					JOptionPane.showMessageDialog(null, "Please select running mode");
					return;
				}else if (runningMode == 2){
					getUserInputs();
					if (GAUtility.initalDepthofIndividualTree == 
						GAUtility.maximumAllowedDepthofIndividualTree)
					{
						JOptionPane.showMessageDialog(null, "Maximum depth cannot be equal to initial depth of the individual ");
						return;
					}
					if (GAUtility.initalDepthofIndividualTree < 0)
					{
						JOptionPane.showMessageDialog(null, "Depth cannot be a negative number");
						return;
					}
				}
				this.progressBar.setIndeterminate(true);
				this.update(this.getGraphics());
				this.repaint();
				GraphData gd = 
					new GraphData(GAUtility.targetFunctionData,
							null);	
				try
				{
					mRunningEngine = 
					new GARunningEngine(GAUtility.initialPopulationSize,
							GAUtility.maximumNumberofGenerations,
							GAUtility.crossoverProbability,
							GAUtility.mutationProbability,
							GAUtility.initalDepthofIndividualTree,
							GAUtility.maximumAllowedDepthofIndividualTree,
							GAUtility.trainingDataFileName,
							GAUtility.fitnessLevelMeasure);
					mRunningEngine.setOutputArea(outputArea);
					mRunningEngine.runGA();
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, 
							ex.toString() + "\n" +" increase memory and try again.", 
							"GP Exceptions", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					gd = new GraphData(GAUtility.targetFunctionData,
							mRunningEngine.getClosestIndividualGenerated());
					this.graphArea.setGraphData(gd);
					this.graphArea.repaint();
					this.progressBar.setIndeterminate(false);
					this.repaint();
					this.update(this.getGraphics());
					Runtime.getRuntime().gc();
			   }
		}
		if (e.getSource() == clear) 
		{
			this.graphArea.setGraphData(null);
			outputArea.setText("");
			this.outputArea.repaint();
			this.graphArea.repaint();
			if (GAUtility.targetFunctionData == null)
			{
				resetToDefault();
			}else
			{
				GraphData gd = 
					new GraphData(GAUtility.targetFunctionData,
							null);				
				this.graphArea.setGraphData(gd);
				outputArea.setText("");
				this.outputArea.repaint();
				this.graphArea.repaint();
			}
		}
	}
	
	/**
	 * <p>Data is reset back to the default</p>
	 */
	private void resetToDefault() {
		File defaultFile = 
			new File(GAUtility.TRANING_DATA_FILENAME);
		if (defaultFile.exists())
		{
			GAUtility.ofileParser =
				new FileParser(GAUtility.TRANING_DATA_FILENAME);
			GraphData gd = 
				new GraphData(GAUtility.targetFunctionData,
						null);				
			this.graphArea.setGraphData(gd);
			outputArea.setText("");
			this.outputArea.repaint();
			this.graphArea.repaint();
		}else{
			JOptionPane.showMessageDialog(null, 
					"The default training data does not exist in the path. " + 
					"Please save the training file in the specified directory" + 
					" or run this application with custom values.");
			return;
		}
	}
	
	/**
	 * <p>Sets the current graph</p>
	 * 
	 * @param graph
	 */
	public void setGraph(Graph graph)
	{
		this.graphArea = graph;
	}


	/**
	 * <p>
	 * Handles the item state changed event of the choice selection
	 * </p>
	 * 
	 * @param e  the ItemEvent
	 */
	public void itemStateChanged(ItemEvent e) 
	{
	    if (e.getStateChange() == ItemEvent.SELECTED)
	    {
			int runningMode = choice.getSelectedIndex();
			if (runningMode == 2)
			{
				this.enabled = true;
				disableEnableFields();
			}else{
				this.enabled = false;
				disableEnableFields();
				resetToDefault();
			}
	    }
	}
	/**
	 * <p>
	 * Helper method to enable or disable fields
	 * </p>
	 *
	 */
	private void disableEnableFields() 
	{
		this.fileName.setEnabled(enabled);
		this.browse.setEnabled(enabled);
		this.closenesstoFitnes.setEnabled(enabled);
		this.maximumDepthIndividual.setEnabled(enabled);
		this.initialPopulation.setEnabled(enabled);
		this.mutationProbability.setEnabled(enabled);
		this.depthIndividual.setEnabled(enabled);
		this.crossOverprobablity.setEnabled(enabled);
		this.maximumGeneration.setEnabled(enabled);
	}
	
	/**
	 * <p>Changes the default values</p>
	 */
	private void getUserInputs()
	{
		GAUtility.initialPopulationSize = 
			  new Double(this.initialPopulation.getText()).intValue();
		GAUtility.initalDepthofIndividualTree = 
			  new Double(this.depthIndividual.getText()).intValue();
		GAUtility.crossoverProbability = 
			  new Double(this.crossOverprobablity.getText()).doubleValue();
		GAUtility.mutationProbability = 
			  new Double(this.mutationProbability.getText()).doubleValue();
		GAUtility.maximumAllowedDepthofIndividualTree = 
			  new Double(this.maximumDepthIndividual.getText()).intValue();
		GAUtility.maximumNumberofGenerations = 
			  new Double(this.maximumGeneration.getText()).intValue();
		double fitnessLevel =  
			new Double(this.closenesstoFitnes.getText()).doubleValue();
		
		if (fitnessLevel < 250)
		{
			JOptionPane.showMessageDialog(null, 
					"Fitness level measure cannot be lower than 250.00 due to performance reasons.");
			GAUtility.fitnessLevelMeasure = 250D;
		}else{
			GAUtility.fitnessLevelMeasure = fitnessLevel;
		}
		this.graphArea.repaint();
	}
}