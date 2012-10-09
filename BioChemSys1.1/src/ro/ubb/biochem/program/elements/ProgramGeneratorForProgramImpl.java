package ro.ubb.biochem.program.elements;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;

public class ProgramGeneratorForProgramImpl implements ProgramGenerator {

	private RuleRepository ruleRepository;

	public ProgramGeneratorForProgramImpl(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public Program generate() {
		ProgramImpl program = new ProgramImpl();

		Integer programSize = new Random().nextInt(ruleRepository.getNumberOfRules()) + 1;

		List<Rule> ruleList = ruleRepository.getRandomSetOfRules(programSize);
		
		for (Rule currentRule : ruleList) {
			program.addReaction(new Reaction(currentRule));
		}
		
		return program;
	}
	
}
