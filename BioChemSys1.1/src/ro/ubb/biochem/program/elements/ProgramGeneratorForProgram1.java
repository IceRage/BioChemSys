package ro.ubb.biochem.program.elements;

import java.util.List;
import java.util.Random;

import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;

public class ProgramGeneratorForProgram1 implements ProgramGenerator {

	private final static Double MAX_PROGRAM_DIMENSION_RATIO = 0.8;

	private RuleRepository ruleRepository;

	public ProgramGeneratorForProgram1(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public Program generate() {
		Program1 program = new Program1();
		Integer programSize = new Random().nextInt((int) Math.round(ruleRepository
				.getNumberOfRules() * MAX_PROGRAM_DIMENSION_RATIO)) + 1;
		programSize = 4;
		List<Rule> ruleList = ruleRepository.getRandomSetOfRules(programSize);
		for (Rule r : ruleList) {
			program.addReaction(new Reaction(r));
		}
		return program;
	}
}
