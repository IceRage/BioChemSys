package ro.ubb.biochem.operators;

import java.util.ArrayList;

public class TripleSurvivalSelection extends MixedSelection implements Selection {
	
	private static final Double ELITISM_PROPORTION = 0.05;
	private static final Double ROULETTE_PROPORTION = 0.50;
	private static final Double BT_PROPORTION = 0.45;
	
	public TripleSurvivalSelection() {
		super(new ArrayList<Selection>(), new ArrayList<Double>());
		this.addSelection(new ElitistSelection(), 1.0);
		this.addSelection(new RouletteSelection(), ROULETTE_PROPORTION / ELITISM_PROPORTION);
		this.addSelection(new BinaryTournamentSelection(), BT_PROPORTION / (ROULETTE_PROPORTION + ELITISM_PROPORTION));
	}
}
