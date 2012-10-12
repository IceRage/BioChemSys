package ro.ubb.biochem.operators;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.ProgramImpl;
import ro.ubb.biochem.reaction.components.Reaction;

public class DeletionForProgramImpl implements Mutation {

	@Override
	public Program mutate(Program program) throws InvalidProgramException {
		if (!(program instanceof ProgramImpl)) {
			throw new InvalidProgramException();
		}
		Random randomGenerator = new Random();
		ProgramImpl program1 = (ProgramImpl) program.clone();
		List<Reaction> reactions = program1.getReactions();
		
		int nrOfReactionsToRemove = (reactions.size() / 100) + 1;		
		
		for (int i=0; i<nrOfReactionsToRemove; i++) {
			if (!reactions.isEmpty()) {
				program1.removeReaction(reactions.get(randomGenerator.nextInt(reactions.size())));
			}
		}
		
		return program1;
	}

}
