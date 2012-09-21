package ro.ubb.biochem.operators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro.ubb.biochem.population.Population;
import ro.ubb.biochem.program.elements.Program;

public class ElitistSelection implements Selection {

	@Override
	public List<Program> select(Population population, int numberOfPrograms) {
		if (numberOfPrograms > population.getSize()) {
			numberOfPrograms = population.getSize();
		}
		return population.getBestPrograms(numberOfPrograms);
	}

	@Override
	public Set<Integer> selectIndices(Population population, int numberOfPrograms) {
		if (numberOfPrograms > population.getSize()) {
			numberOfPrograms = population.getSize();
		}
		Set<Integer> resultSet = new HashSet<Integer>();
		resultSet.addAll(population.getBestProgramsIndices(numberOfPrograms));
		return resultSet;
	}
}
