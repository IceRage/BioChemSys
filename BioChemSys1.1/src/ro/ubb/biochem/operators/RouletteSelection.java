package ro.ubb.biochem.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.program.elements.Program;

public class RouletteSelection implements Selection {

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
		List<Double> fitnessList = population.getFitnesses();
		List<Double> normalizedFitnessList = new ArrayList<Double>();
		Random randomGenerator = new Random();
		Double totalFitness = 0.0;
		for (Double d: fitnessList) {
			totalFitness += d;
		}
		for (Double d : fitnessList) {
			normalizedFitnessList.add(d / totalFitness);
		}
		Set<Integer> selectedIndices = new HashSet<Integer>();
		while (selectedIndices.size() < numberOfPrograms) {
			Double randomNumber = randomGenerator.nextDouble();
			int i = -1;
			while (randomNumber > 0) {
				i++;
				randomNumber -= normalizedFitnessList.get(i);
			}
			selectedIndices.add(i);
		}
		return selectedIndices;
	}
}
