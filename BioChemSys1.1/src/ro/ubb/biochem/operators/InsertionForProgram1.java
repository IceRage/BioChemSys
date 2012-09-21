package ro.ubb.biochem.operators;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.Program1;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.RuleRepository;

public class InsertionForProgram1 implements Mutation {

	private RuleRepository ruleRepository;

	public RuleRepository getRuleRepository() {
		return ruleRepository;
	}

	public void setRuleRepository(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public Program mutate(Program program) throws InvalidProgramException {
		if (!(program instanceof Program1)) {
			throw new InvalidProgramException();
		}
		Random randomGenerator = new Random();
		Program1 program1 = (Program1) program.clone();
		List<Reaction> reactions = program1.getReactions();
		Double kineticRate = randomGenerator.nextDouble();
		Reaction newReaction = null;
		int attempts = 0;
		do {
			newReaction = new Reaction(ruleRepository.getRandomRule(),
					kineticRate);
			attempts++;
		} while (reactions.contains(newReaction) && attempts < ruleRepository.getNumberOfRules() * 2);
		if (!reactions.contains(newReaction)) {
			program1.addReaction(newReaction);
		}
		return program1;
	}
}
