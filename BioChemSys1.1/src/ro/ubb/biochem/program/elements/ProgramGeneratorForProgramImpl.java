package ro.ubb.biochem.program.elements;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;

public class ProgramGeneratorForProgramImpl implements ProgramGenerator {

	private final static Double MAX_PROGRAM_DIMENSION_RATIO = 0.8;

	private RuleRepository ruleRepository;

	public ProgramGeneratorForProgramImpl(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public Program generate() {
		ProgramImpl program = new ProgramImpl();

		Integer programSize = new Random().nextInt((int) Math.round(ruleRepository
				.getNumberOfRules() * MAX_PROGRAM_DIMENSION_RATIO)) + 1;

		List<Rule> ruleList = ruleRepository.getRandomSetOfRules(programSize);
		
		for (Rule currentRule : ruleList) {
			program.addReaction(new Reaction(currentRule));
		}
		
		return program;
	}
	
}
