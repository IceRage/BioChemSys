package ro.ubb.biochem.operators;

import java.util.ArrayList;

public class DoubleSelection extends MixedSelection implements Selection {
	
	private static final Double ELITISM_PROPORTION = 0.15;
	private static final Double BT_PROPORTION = 0.85;
	
	public DoubleSelection() {
		super(new ArrayList<Selection>(), new ArrayList<Double>());
		this.addSelection(new ElitistSelection(), 1.0);
		this.addSelection(new BinaryTournamentSelection(), BT_PROPORTION / ELITISM_PROPORTION);
	}
}
