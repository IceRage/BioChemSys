package ro.ubb.biochem.optim;

import java.util.Collection;
import java.util.TreeMap;

import ro.ubb.biochem.program.elements.Program;

public class AnnealedProgramsGatherer {
	
	private TreeMap<Double, Program> annealedPrograms;
	
	public AnnealedProgramsGatherer(){
		annealedPrograms = new TreeMap<Double, Program>();
	}
	
	
	public synchronized void addProgram(Program program){
		annealedPrograms.put(program.getFitness(), program);
	}
	
	public Collection<Program> getAnnealed(){
		return annealedPrograms.values();
	}
	
	

}
