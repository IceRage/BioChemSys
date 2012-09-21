package ro.ubb.biochem.gui.plotting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class PlotView extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	private static final int X_MARGIN = 70;
	private static final int Y_MARGIN = 50;
	private static final int TICK_SIZE = 10;
	private static final Font FONT = new Font("Arial", Font.PLAIN, 12);
	private static final int LABEL_WIDTH = 30;
	private static final int LEGEND_WIDTH = 100;
	private static final int LEGEND_Y_MARGIN = 10;
	private static final int LEGEND_LINE_LENGTH = 50;

	private List<PlotData> plotDataList;
	private double xUnit, yUnit;
	private int numberOfXTicks, numberOfYTicks;
	private String xAxisName, yAxisName;
	private Double minX, minY, maxX, maxY;

	/*
	 * public static void main(String args[]) { JFrame frame = new JFrame();
	 * JPanel panel = new PlotView(); panel.setPreferredSize(new Dimension(400,
	 * 300)); frame.getContentPane().add(panel);
	 * frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); frame.pack();
	 * frame.setVisible(true); }
	 */

	public PlotView() {
		super();
		this.plotDataList = new ArrayList<PlotData>();
		plotDataList.add(new PlotData(0, 0, 0, 0, Color.BLACK, "default"));
		this.minX = 0.0;
		this.minY = 0.0;
		this.maxX = 0.0;
		this.maxY = 0.0;
		xUnit = 0;
		yUnit = 0;
		numberOfXTicks = 10;
		numberOfYTicks = 10;
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		this.xAxisName = "X Axis";
		this.yAxisName = "Y Axis";

		/*
		 * this.plotData = new PlotData(0, 100, 0, 100); for (int i = 0; i <
		 * 100; i++) { try { plotData.addPoint(i, 25 + new
		 * Random().nextInt(50)); } catch (PlotException e) {
		 * e.printStackTrace(); } }
		 */
	}

	public void setAxisNames(String xAxisName, String yAxisName) {
		this.xAxisName = xAxisName;
		this.yAxisName = yAxisName;
	}

	public void setPlotData(List<PlotData> plotDataList) {
		this.plotDataList = plotDataList;
		minX = Double.POSITIVE_INFINITY;
		minY = Double.POSITIVE_INFINITY;
		maxX = Double.NEGATIVE_INFINITY;
		maxY = Double.NEGATIVE_INFINITY;
		for (PlotData pd : plotDataList) {
			if (pd.getMinPoint().x < minX) {
				minX = pd.getMinPoint().x;
			}
			if (pd.getMinPoint().y < minY) {
				minY = pd.getMinPoint().y;
			}
			if (pd.getMaxPoint().x > maxX) {
				maxX = pd.getMaxPoint().x;
			}
			if (pd.getMaxPoint().y > maxY) {
				maxY = pd.getMaxPoint().y;
			}
		}
		repaint();
	}

	public void setNumberOfTicks(int xNumber, int yNumber) {
		numberOfXTicks = xNumber;
		numberOfYTicks = yNumber;
	}

	@Override
	public void paint(Graphics g) {
		xUnit = (this.getWidth() - X_MARGIN) / (maxX - minX);
		yUnit = (this.getHeight() - Y_MARGIN) / (maxY - minY);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setBackground(BACKGROUND_COLOR);
		g2d.clearRect(0, 0, getWidth(), getHeight());
		drawAxes(g2d);
		drawGraph(g2d);
		drawLegend(g2d);
	}

	private void drawLegend(Graphics2D g2d) {

		int xPosition = getWidth() - LEGEND_WIDTH;
		int yPosition = LEGEND_Y_MARGIN + g2d.getFont().getSize() / 2 + 1;

		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
		for (PlotData pd : plotDataList) {
			g2d.drawString(pd.getName(), xPosition, yPosition);
			Color currentColor = g2d.getColor();
			g2d.setColor(pd.getColor());
			int xPos = xPosition + pd.getName().length() * 6 + 10;
			int yPos = yPosition - g2d.getFont().getSize() / 2;
			g2d.fill(new Rectangle(xPos, yPos, LEGEND_LINE_LENGTH, 2));
			g2d.setColor(currentColor);
			yPosition += g2d.getFont().getSize() * 2;
		}
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN));
	}

	private void drawGraph(Graphics2D g2d) {
		Color currentColor = g2d.getColor();
		for (PlotData pd : plotDataList) {
			g2d.setColor(pd.getColor());
			PlotPoint[] points = pd.getPoints();
			int numberOfPoints = points.length;
			int[] x = new int[numberOfPoints];
			int[] y = new int[numberOfPoints];

			for (int i = 0; i < numberOfPoints; i++) {
				x[i] = X_MARGIN + (int) (xUnit * (points[i].x - minX));
				y[i] = this.getHeight() - Y_MARGIN - (int) (yUnit * (points[i].y - minY));
			}
			g2d.drawPolyline(x, y, numberOfPoints);
		}
		g2d.setColor(currentColor);
	}

	private void drawAxes(Graphics2D g2d) {
		NumberFormat numberFormat = new DecimalFormat("0.000");
		g2d.setFont(FONT);

		// x axis
		int y = getHeight() - Y_MARGIN;
		g2d.drawLine(X_MARGIN, y, getWidth(), y);
		int tickSpace = (getWidth() - X_MARGIN) / numberOfXTicks;
		double tickValue = (maxX - minX) / numberOfXTicks;
		for (int i = 0; i < numberOfXTicks; i++) {
			int xPosition = X_MARGIN + i * tickSpace;
			g2d.drawLine(xPosition, y - TICK_SIZE / 2, xPosition, y + TICK_SIZE / 2);
			g2d.drawString(numberFormat.format(minX + tickValue * i), xPosition - LABEL_WIDTH / 2, y + TICK_SIZE / 2
					+ g2d.getFont().getSize() + 1);
		}
		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 15));
		g2d.drawString(xAxisName, getWidth() / 2 - xAxisName.length() * 5, y + TICK_SIZE / 2
				+ g2d.getFont().getSize() * 2 + 10);
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));

		// y axis
		g2d.drawLine(X_MARGIN, 0, X_MARGIN, getHeight() - Y_MARGIN);
		tickSpace = (getHeight() - Y_MARGIN) / numberOfYTicks;
		tickValue = (maxY - minY) / numberOfYTicks;
		for (int i = 0; i < numberOfYTicks; i++) {
			int yPosition = getHeight() - Y_MARGIN - i * tickSpace;
			g2d.drawLine(X_MARGIN - TICK_SIZE / 2, yPosition, X_MARGIN + TICK_SIZE / 2, yPosition);
			g2d.drawString(numberFormat.format(minY + tickValue * i), X_MARGIN - LABEL_WIDTH - TICK_SIZE / 2 - 1,
					yPosition + g2d.getFont().getSize() / 2);
		}
		g2d.rotate(-Math.PI / 2);
		g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 15));
		g2d.drawString(yAxisName, -getHeight() / 2 - yAxisName.length() * 5, 20);
		g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 12));
		g2d.rotate(Math.PI / 2);
	}
}
