package ro.ubb.biochem.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.program.elements.Program;

public abstract class MixedSelection implements Selection {
	
	private List<Selection> selections;
	private List<Double> proportions;

	public MixedSelection(List<Selection> selections, List<Double> proportions) {
		while (proportions.size() > selections.size()) {
			proportions.remove(proportions.size() - 1);
		}
		normalizeProportions(proportions);
		this.selections = selections;
		this.proportions = proportions;
	}
	
	public void addSelection(Selection selection, Double proportion) {
		selections.add(selection);
		proportions.add(proportion);
		normalizeProportions(proportions);
	}
	
	private void normalizeProportions(List<Double> proportions) {
		Double sum = 0.0;
		for (Double p : proportions) {
			sum += p;
		}
		List<Double> newProportions = new ArrayList<Double>();
		for (Double p : proportions) {
			newProportions.add(p / sum);
		}
		proportions.clear();
		proportions.addAll(newProportions);
		
	}

	@Override
	public List<Program> select(Population population, int numberOfPrograms) {
		
		List<Program> resultList = new ArrayList<Program>();
		for (int i : selectIndices(population, numberOfPrograms)) {
			resultList.add(population.getProgram(i));
		}
		return resultList;
	}
	
	@Override
	public Set<Integer> selectIndices(Population population, int numberOfPrograms) {
		if (numberOfPrograms > population.getSize()) {
			numberOfPrograms = population.getSize();
		}
		
		Population clonePop = population.clone();
		
		Set<Integer> selectedIndices = new HashSet<Integer>();
		
		for (int i = 0; i < selections.size(); i++) {
			Set<Integer> temp = selections.get(i).selectIndices(clonePop, (int) Math.round(numberOfPrograms * proportions.get(i)));
			selectedIndices.addAll(temp);
			clonePop.removeAll(temp);
		}
		
		return selectedIndices;
	}
}
