package ro.ubb.biochem.operators;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.ProgramImpl;
import ro.ubb.biochem.reaction.components.Reaction;

public class CutAndSpliceCrossoverForProgram1 implements Crossover {

	@Override
	public Program generateOffsrping(Program firstParent, Program secondParent) throws InvalidProgramException {
		if (!(firstParent instanceof ProgramImpl) || !(secondParent instanceof ProgramImpl)) {
			throw new InvalidProgramException();
		}
		Set<Reaction> firstParentReactions = new HashSet<Reaction>();
		Set<Reaction> secondParentReactions = new HashSet<Reaction>();
		firstParentReactions.addAll(((ProgramImpl) firstParent).clone().getReactions());
		secondParentReactions.addAll(((ProgramImpl) secondParent).clone().getReactions());
		ProgramImpl offspring = new ProgramImpl();
		offspring.setMaxKineticRateStep((firstParent.getMaxKineticRateStep() + secondParent
				.getMaxKineticRateStep()) / 2);
		Random randomGenerator = new Random();

		for (Reaction r : firstParentReactions) {
			if (secondParentReactions.contains(r)) {
				Double otherKineticRate = 0.0;
				for (Reaction r2 : secondParentReactions) {
					if (r2.equals(r)) {
						otherKineticRate = r2.getKineticRate();
					}
				}
				Reaction copy = r.clone();
				copy.updateKineticRate((copy.getKineticRate() + otherKineticRate) / 2);
				offspring.addReaction(r.clone());
			} else {
				if (randomGenerator.nextDouble() >= 0.5) {
					offspring.addReaction(r.clone());
				}
			}
		}
		for (Reaction r : secondParentReactions) {
			if (!firstParentReactions.contains(r)) {
				if (randomGenerator.nextDouble() >= 0.5) {
					offspring.addReaction(r.clone());
				}
			}
		}
		return offspring;
	}

}
