package gpUserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JComponent;


public class Graph extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Point point = null;

	private Dimension preferredSize = new Dimension(650,400);

	private GraphData graphData;
	

	/**
	 * @return the graphData
	 */
	public GraphData getGraphData() {
		return graphData;
	}

	/**
	 * @param graphData the graphData to set
	 */
	public void setGraphData(GraphData graphData) {
		this.graphData = graphData;
	}

	public Graph(GraphData _graphData) 
	{
		setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.DARK_GRAY));
		setBackground(Color.WHITE);
		this.graphData = _graphData;
		setOpaque(true);
	}

	public Dimension getPreferredSize() 
	{
		return preferredSize;
	}

	protected void paintComponent(Graphics g) {

		
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		//Paint 20x20 grid.
		g.setColor(Color.ORANGE);
		drawGrid(g, 20);

		//If user has chosen a point, paint a small dot on top.
		if (point != null) {
			g.setColor(getForeground());
			g.fillRect(point.x - 3, point.y - 3, 7, 7);
		}
		if (graphData != null)
		{
			graphThisData(this.graphData.getTargetFunctionData(),Color.RED,g);	
			if (this.graphData.getBestFunctionObtained()!=null)
			{
				graphThisData(this.graphData.getBestFunctionObtained(),Color.blue,g);
				g.setColor(Color.BLACK);
				g.setFont(new Font("Serif", Font.HANGING_BASELINE, 16));
				g.drawString(this.graphData.getBestFitIndividual().parentheticalExpression(),120,55);
			}
		}
	}

	//Draws a 20x20 grid using the current color.
	private void drawGrid(Graphics g, int gridSpace) {
		Insets insets = getInsets();
		int firstX = insets.left;
		int firstY = insets.top;
		int lastX = getWidth() - insets.right;
		int lastY = getHeight() - insets.bottom;

		//Draw vertical lines.
		int x = firstX;
		while (x < lastX) {
			g.drawLine(x, firstY, x, lastY);
			x += gridSpace;
		}

		//Draw horizontal lines.
		int y = firstY;
		while (y < lastY) {
			g.drawLine(firstX, y, lastX, y);
			y += gridSpace;
		}
	}

	/**
	 * 
	 * @param data
	 * @param graphColor
	 * @param _grahics
	 */
	private void graphThisData(double[][] data, 
			Color graphColor,
			Graphics _grahics)
	{
		
		Insets insets = getInsets();
		int xCoord = 0 - getWidth()/10;
		int yCoord = 0 - getHeight()/5;
		int width = getWidth() - insets.right;
		int height = getHeight() - insets.bottom;
		
		if (data == null)
		{
			return;
		}
		int xInterval = width / data.length;
		int xVal = xCoord;
		int yVal = yCoord + height;
		double scale = (height/ max(data));
		_grahics.setColor(graphColor);		
		for(int i=0; i<data.length; i++){

			if (i >0)
			{
				_grahics.drawLine(xVal, yVal,  (xVal + xInterval),
						(int)(yCoord + height - data[i][1] * scale));
			}
			xVal += xInterval;
			yVal = (int)(yCoord + height - data[i][1] * scale);
		}
	}

	/**
	 * determines the maximum value in the array
	 */
	private double max(double data[][]){
		double max = 0;
		if (data == null)
			return max;
		for(int i=0; i<data.length; i++)
			if (max < data[i][1])
				max = data[i][1];
		return max;
	}
	/**
	 * updates the contents of the Graph using the data from its argument
	 * @param gd 
	 */
	public void update(GraphData gd)
	{
		graphData = gd;
		this.repaint();
	}
}