package gpDataAccessLayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import globals.GAUtility;

/**
 * <p>Represents the data access mechanism this will read the given file and return the contents to the caller.</p>
 * <p>Used for accessing training data in this genetic programming environment.</p>
 * 
 * @author Yared Kassa Estifanos
 *
 */
public class FileParser
{
	
	/**
	 * <p>The current file name</p>
	 */
	private String fileName;
	
	
	/**
	 * <p>The current training data</p>
	 */	
	private String [][] trainingData;
	
	
	/**
	 * <p>The target function data used for graph</p>
	 */
	private double targetFunctionData [][];
	
	
	/**
	 * <p>
	 * used for parsing the file contents
	 * </p>
	 */
	private Vector<String> itemsInFile;


	/**
	 * @param fileName
	 */
	public FileParser(String fileName) 
	{
		this.fileName = fileName;		
		this.checkAndUpdateData(fileName);
	}

	/**
	 * <p>Checks and updates the current training data</p>
	 * 
	 * @param fileName
	 */
	private void checkAndUpdateData(String fileName) {
		if (!GAUtility.trainingDataFileName.equalsIgnoreCase(fileName)
			|| GAUtility.targetFunctionData == null)
		{
			itemsInFile = new Vector<String>();
			GAUtility.trainingDataFileName = fileName;
			this.parseTrainingData();
			this.populateTargetFunctionData();
			GAUtility.targetFunctionData = 
				this.targetFunctionData;
			GAUtility.trainingData = 
				this.getTrainingData();
		}
	}
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName)
	{
		this.fileName = fileName;
		this.checkAndUpdateData(fileName);
	}

	/**
	 * @return the trainingData
	 */
	public String[][] getTrainingData() {
		return trainingData;
	}

	/**
	 * @param trainingData the trainingData to set
	 */
	public void setTrainingData(String[][] trainingData) {
		this.trainingData = trainingData;
	}


	/**
	 * <p>
	 * Gets the training data contents
	 * </p>
	 */
	private void parseTrainingData()
	{
		String sLine;

		// Open the file for reading
		try {
			BufferedReader br = 
				new BufferedReader(new FileReader(this.getFileName()));
			while ((sLine = br.readLine()) != null) 
			{ // while loop begins here
				itemsInFile.add(sLine);
			} // end while
			br.close();
			itemsInFile.trimToSize();
			populateDataMatrix();
		} // end try
		catch (IOException e)
		{
			System.err.println("Error: " + e);
		}
	}
	
	/**
	 * <p>
	 * this will populate the training data matrix that is used for the
	 * evaluation process
	 * </p>
	 */
	private void populateDataMatrix()
	{
		
		Vector data = itemsInFile;

		String rowItem = data.elementAt(0).toString();
		StringTokenizer tokenizer = new StringTokenizer(rowItem);
		int iColCount = tokenizer.countTokens();

		String[][] tableData = new String[data.size()][iColCount];

		for (int row = 0; row < data.size(); row++) {
			rowItem = data.elementAt(row).toString();
			tokenizer = new StringTokenizer(rowItem);
			int column = 0;
			while (tokenizer.hasMoreTokens()) {
				String currentToken = tokenizer.nextToken();
				String fdata = (currentToken.trim());
				tableData[row][column] = fdata;
				column++;
			}
		}
		trainingData = tableData;
	}
	
	/**
	 * <p>puts the string data for in double array</p>
	 * <p>for eliminating boxing and unboxing operations in
	 * expression evaluation and fitness measure</p>
	 *
	 */
	private void populateTargetFunctionData()
	{
		int rows = 
			trainingData.length;
		int column = 
			trainingData[0].length;
		 targetFunctionData  = new double [rows][column];
		
		for (int row=0; row < rows; row++)
		{
			double rowData [] =
				new double[trainingData[row].length];
			for(int s=0; s< trainingData[row].length; s++)
			{
				rowData[s] = 
					(new Double(trainingData[row][s])).doubleValue();
			}
			targetFunctionData[row] = rowData;
		}
	}
}
