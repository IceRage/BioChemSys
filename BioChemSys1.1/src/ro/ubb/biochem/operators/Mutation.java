package ro.ubb.biochem.operators;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;

public interface Mutation {

	public Program mutate(Program program) throws InvalidProgramException;
	
}
