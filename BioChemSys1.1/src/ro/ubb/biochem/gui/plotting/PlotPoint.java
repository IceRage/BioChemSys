package ro.ubb.biochem.gui.plotting;

public class PlotPoint implements Comparable<PlotPoint> {

	public final double x;
	public final double y;

	public PlotPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlotPoint) {
			return ((PlotPoint) obj).x == x;
		}
		else {
			return false;
		}
	}

	@Override
	public int compareTo(PlotPoint otherPoint) {
		if (x - otherPoint.x < 0) {
			return -1;
		}
		else if (x - otherPoint.x > 0) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
