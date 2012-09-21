package ro.ubb.biochem.operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.program.elements.Program;

public class BinaryTournamentSelection implements Selection {

	@Override
	public List<Program> select(Population population, int numberOfPrograms) {
		List<Program> result = new ArrayList<Program>();
		for (int index : selectIndices(population, numberOfPrograms)) {
			result.add(population.getProgram(index));
		}
		return result;
	}

	@Override
	public Set<Integer> selectIndices(Population population, int numberOfPrograms) {
		if (numberOfPrograms > population.getSize()) {
			numberOfPrograms = population.getSize();
		}
		Set<Integer> selectedIndices = new HashSet<Integer>();
		Random randomGenerator = new Random();
		while (selectedIndices.size() < numberOfPrograms) {
			int index1 = randomGenerator.nextInt(population.getSize());
			int index2 = randomGenerator.nextInt(population.getSize());
			selectedIndices.add(population.getBetterProgramIndex(index1, index2));
		}
		return selectedIndices;
	}
}
