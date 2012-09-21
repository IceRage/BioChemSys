package ro.ubb.biochem.operators;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.Program1;
import ro.ubb.biochem.reaction.components.Reaction;

public class KineticRateAlterationForProgram1 implements Mutation {

	@Override
	public Program mutate(Program program) throws InvalidProgramException {
		if (!(program instanceof Program1)) {
			throw new InvalidProgramException();
		}
		//Double maxKineticRateStep = program.getMaxKineticRateStep();
		Random randomGenerator = new Random();
		Program1 program1 = (Program1) program.clone();
		List<Reaction> reactions = program1.getReactions();
		if (!reactions.isEmpty()) {
			Reaction selectedReaction = reactions.get(randomGenerator.nextInt(reactions.size()));
			program1.removeReaction(selectedReaction);
			int sign = 0;
			Double newKineticRate =randomGenerator.nextDouble() + sign;
			selectedReaction.updateKineticRate(Math.max(0.00000000000000001, newKineticRate));
			program1.addReaction(selectedReaction);
		}
		return program1;
	}
}
