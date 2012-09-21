package ro.ubb.biochem.population;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import ro.ubb.biochem.program.elements.FitnessFunction;
import ro.ubb.biochem.program.elements.Program;

public interface Population {

	public Program getBestProgram();
	
	public List<Program> getAllPrograms();
	
	public int getSize();
	
	public void initialize(Integer size);
	
	public void addProgram(Program program);
	
	public void removeProgram(Program program);
	
	public void clear();

	public Program getRandomProgram();

	public Program getBetterProgram(Program program1, Program program2);
	
	public Program getBetterProgram(int index1, int index2);
	
	public Integer getBetterProgramIndex(int index1, int index2);
	
	public List<Program> getBestPrograms(Integer numberOfRequiredPrograms);
	
	public List<Double> getFitnesses();

	public Program getProgram(int index);
	
	public void addAllPrograms(Collection<Program> programs);
	
	public Population clone();

	public void removeAll(List<Program> temp);

	public boolean contains(Program program);

	public Double getBestFitness();

	public List<Integer> getBestProgramsIndices(Integer numberOfPrograms);

	public void removeAll(Set<Integer> temp);
	
	public Double getAverageFitness();
	
	public FitnessFunction getFitnessFunction();
}
