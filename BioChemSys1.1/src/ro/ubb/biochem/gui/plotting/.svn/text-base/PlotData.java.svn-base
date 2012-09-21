package ro.ubb.biochem.gui.plotting;

import java.awt.Color;
import java.util.SortedSet;
import java.util.TreeSet;

public class PlotData {

	private SortedSet<PlotPoint> points;
	private PlotPoint minPoint, maxPoint;
	private Color color;
	private String name;

	public PlotData(double minX, double maxX, double minY, double maxY, Color color, String name) {
		this.minPoint = new PlotPoint(minX, minY);
		this.maxPoint = new PlotPoint(maxX, maxY);
		points = new TreeSet<PlotPoint>();
		this.color = color;
		this.name = name;
	}

	public void addPoint(double x, double y) throws PlotException {
		addPoint(new PlotPoint(x, y));
	}

	public void addPoint(PlotPoint plotPoint)  throws PlotException {
		if (plotPoint.x < minPoint.x || plotPoint.x > maxPoint.x
				|| plotPoint.y < minPoint.y || plotPoint.y > maxPoint.y) {
			throw new PlotException("Invalid plot point: (" + plotPoint.x
					+ ", " + plotPoint.y + ").");
		} else {
			if (!points.add(plotPoint)) {
				throw new PlotException("Invalid plot point. Duplicate x: "
						+ plotPoint.x);
			}
		}
	}

	public PlotPoint[] getPoints() {
		return points.toArray(new PlotPoint[0]);
	}

	public PlotPoint getMinPoint() {
		return minPoint;
	}

	public PlotPoint getMaxPoint() {
		return maxPoint;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}

}
