package ro.ubb.biochem.operators;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.exceptions.InvalidProgramException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.program.elements.ProgramImpl;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;

public class SpecieReplaceMutationForProgram1 implements Mutation {

	private RuleRepository ruleRepository;

	public RuleRepository getRuleRepository() {
		return ruleRepository;
	}

	public void setRuleRepository(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public Program mutate(Program program) throws InvalidProgramException {
		if (!(program instanceof ProgramImpl)) {
			throw new InvalidProgramException();
		}
		Random randomGenerator = new Random();
		ProgramImpl program1 = (ProgramImpl) program.clone();
		List<Reaction> reactions = program1.getReactions();
		if (!reactions.isEmpty()) {
			Reaction selectedReaction = reactions.get(randomGenerator.nextInt(reactions.size()));
			program1.removeReaction(selectedReaction);
			Rule rule = selectedReaction.getPattern();
			selectedReaction.changePattern(ruleRepository.getSimmilarRule(rule));
			program1.addReaction(selectedReaction);
		} else {
			return program.clone();
		}
		return program1;
	}

}
