package ro.ubb.biochem.population;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ro.ubb.biochem.program.elements.FitnessFunction;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.ProgramGenerator;

public class PopulationImpl implements Population {

	private List<Program> programs;
	private List<Double> fitness;
	private FitnessFunction fitnessFunction;
	private ProgramGenerator programGenerator;

	public PopulationImpl(FitnessFunction fitnessFunction, ProgramGenerator programGenerator) {
		this.programs = new ArrayList<Program>();
		this.fitness = new ArrayList<Double>();
		this.fitnessFunction = fitnessFunction;
		this.programGenerator = programGenerator;
	}

	public PopulationImpl(Integer size, FitnessFunction fitnessFunction, ProgramGenerator programGenerator) {
		this.programs = new ArrayList<Program>();
		this.fitness = new ArrayList<Double>();
		this.fitnessFunction = fitnessFunction;
		this.programGenerator = programGenerator;
		initialize(size);
	}

	public PopulationImpl(List<Program> programs, FitnessFunction fitnessFunction, ProgramGenerator programGenerator) {
		this.programs = new ArrayList<Program>();
		this.fitness = new ArrayList<Double>();
		for (Program p : programs) {
			addProgram(p);
		}
		this.fitnessFunction = fitnessFunction;
		this.programGenerator = programGenerator;
	}

	@Override
	public Program getBestProgram() {
		System.out.println(fitness.get(0));
		if (!programs.isEmpty()) {
			return programs.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Program> getAllPrograms() {
		List<Program> result = new ArrayList<Program>();
		for (Program p : programs) {
			result.add(p);
		}
		return result;
	}

	@Override
	public int getSize() {
		return programs.size();
	}

	@Override
	public void initialize(Integer size) {
		programs = new ArrayList<Program>();
		for (int i = 0; i < size; i++) {
			addProgram(programGenerator.generate());
		}
	}

	@Override
	public void addProgram(Program program) {
		Double currentFitness = 0.0;
		if(program.getFitness() == null)  fitnessFunction.computeFitness(program);
		currentFitness = program.getFitness();
		int index = 0;
		while (index < programs.size() && currentFitness > fitness.get(index)) {
			index++;
		}
		this.programs.add(index, program);
		this.fitness.add(index, currentFitness);
	}

	@Override
	public void removeProgram(Program program) {
		Double programFitness = 0.0;
		if(program.getFitness() == null)  fitnessFunction.computeFitness(program);
		programFitness = program.getFitness();
		int index = 0;
		while (index < this.programs.size() && programFitness > this.fitness.get(index)) {
			index++;
		}
		if (index < this.programs.size() && this.fitness.get(index).equals(programFitness)
				&& this.programs.get(index).equals(program)) {
			this.programs.remove(index);
			this.fitness.remove(index);
		}
	}

	@Override
	public void clear() {
		this.programs.clear();
		this.fitness.clear();
	}

	@Override
	public Program getRandomProgram() {
		return programs.get((new Random()).nextInt(programs.size()));
	}

	@Override
	public Program getBetterProgram(Program program1, Program program2) {
		Double programFitness1 = 0.0;
		if(program1.getFitness() == null)  fitnessFunction.computeFitness(program1);
		programFitness1 = program1.getFitness();
		Double programFitness2 = 0.0;
		if(program2.getFitness() == null) fitnessFunction.computeFitness(program2);
		programFitness2 = program2.getFitness();
		
		if (programFitness1 > programFitness2) {
			return program2;
		} else {
			return program1;
		}
	}

	public Program getBetterProgram(int index1, int index2) {
		if (fitness.get(index1) > fitness.get(index2)) {
			return programs.get(index2);
		} else {
			return programs.get(index1);
		}
	}

	@Override
	public Integer getBetterProgramIndex(int index1, int index2) {
		if (fitness.get(index1) > fitness.get(index2)) {
			return index2;
		} else {
			return index2;
		}
	}

	@Override
	public List<Program> getBestPrograms(Integer numberOfRequiredPrograms) {

		List<Program> resultList = new ArrayList<Program>();
		resultList.addAll(programs.subList(0, numberOfRequiredPrograms));
		return resultList;
	}

	@Override
	public List<Double> getFitnesses() {
		List<Double> fitnesses = new ArrayList<Double>();
		fitnesses.addAll(fitness);
		return fitnesses;
	}

	@Override
	public Program getProgram(int index) {
		return programs.get(index);
	}

	@Override
	public void addAllPrograms(Collection<Program> programs) {
		for (Program program : programs)
			addProgram(program);
	}

	public Population clone() {
		PopulationImpl newPop = new PopulationImpl(fitnessFunction, programGenerator);
		newPop.fitness = new ArrayList<Double>();
		newPop.programs = new ArrayList<Program>();
		newPop.fitness.addAll(fitness);
		for (Program p : programs) {
			newPop.programs.add(p.clone());
		}
		return newPop;
	}

	@Override
	public void removeAll(List<Program> programList) {
		for (Program p : programList) {
			removeProgram(p);
		}
	}

	@Override
	public boolean contains(Program program) {
		Double programFitness = 0.0;
		if(program.getFitness() == null) fitnessFunction.computeFitness(program);
		programFitness = program.getFitness();
		int index = 0;
		while (index < this.programs.size() && programFitness > this.fitness.get(index)) {
			index++;
		}
		if (index < this.programs.size() && this.fitness.get(index).equals(programFitness)
				&& this.programs.get(index).equals(program)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Double getBestFitness() {
		return fitness.get(0);
	}

	@Override
	public List<Integer> getBestProgramsIndices(Integer numberOfPrograms) {
		List<Integer> resultList = new ArrayList<Integer>();
		for (int i = 0; i < numberOfPrograms; i++) {
			resultList.add(i);
		}
		return resultList;
	}

	@Override
	public void removeAll(Set<Integer> indicesSet) {
		for (int i = programs.size() - 1; i >= 0; i--) {
			if (indicesSet.contains(i)) {
				programs.remove(i);
				fitness.remove(i);
			}
		}
		
	}
	
	public Double getAverageFitness(){
		Double average = 0.0;
		for(Double fitnesses : fitness){
			average += fitnesses;
		}
		return average/fitness.size();
	}

	@Override
	public FitnessFunction getFitnessFunction() {
		return fitnessFunction;
	}

}
