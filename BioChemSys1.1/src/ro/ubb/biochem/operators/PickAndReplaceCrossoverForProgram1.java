package ro.ubb.biochem.operators;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.ProgramImpl;
import ro.ubb.biochem.reaction.components.Reaction;

public class PickAndReplaceCrossoverForProgram1 implements Crossover {

	private static final Integer MAX_NUMBER_OF_TRIES = 15;

	@Override
	public Program generateOffsrping(Program firstParent, Program secondParent) throws InvalidProgramException {
		if (!(firstParent instanceof ProgramImpl) || !(secondParent instanceof ProgramImpl)) {
			throw new InvalidProgramException();
		}
		ProgramImpl offspring = ((ProgramImpl) firstParent).clone();
		ProgramImpl parent = ((ProgramImpl) secondParent);
		List<Reaction> offspringReactions = offspring.getReactions();
		List<Reaction> parentReactions = parent.getReactions();
		Random randomGenerator = new Random();
		Reaction selectedReaction = null;
		Reaction simmilarReaction = null;
		int attempts = 0;
		do {
			selectedReaction = offspringReactions.get(randomGenerator.nextInt(offspringReactions.size()));
			simmilarReaction = getSimmilarReaction(parentReactions, selectedReaction);
			attempts++;
		} while (simmilarReaction == null && attempts < MAX_NUMBER_OF_TRIES);
		if (simmilarReaction != null) {
			selectedReaction.changePattern(simmilarReaction.getPattern());
		}
		return offspring;
	}

	private Reaction getSimmilarReaction(List<Reaction> reactionList, Reaction selectedReaction) {
		Random randomGenerator = new Random();
		Reaction reaction = null;
		int i = 0;
		do {
			i++;
			reaction = reactionList.get(randomGenerator.nextInt(reactionList.size()));
		} while (!reaction.getPattern().isSimmilar(selectedReaction.getPattern()) && i < MAX_NUMBER_OF_TRIES);
		if (i < MAX_NUMBER_OF_TRIES) {
			return reaction;
		} else {
			for (Reaction r : reactionList) {
				if (r.getPattern().isSimmilar(selectedReaction.getPattern())) {
					return r;
				}
			}
			return null;
		}
	}

}
