package ro.ubb.biochem.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.persistance.util.IntegerWrapper;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;
import ro.ubb.biochem.reaction.components.RuleRepositoryImpl;
import ro.ubb.biochem.reaction.components.util.RuleFactory;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePhase;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

/**
 * Class designed for reading the input:
 * 	1. The target behaviour i.e. the concentrations of the given species at different points in time
 *  2. The templates i.e. the patterns for creating reactions (e.g. Mass-Action 1, Mass-Action 2 etc.)
 */
public class InputReader {

	private static final String TARGET_BEHAVIOUR_NUMBER_ERROR = "The numbers in the target behaviour input file are not specified correctly.";
	private static final String TARGET_BEHAVIOUR_INPUT_ERROR = "The target behaviour input file cannot be found or we cannot read from it.";
	private static final String TOKENS_NUMBER_ERROR = "The number of tokens in the line is invalid.";
	private static final String CONCENTRATION_SEPARATOR = "\\|";
	private static final String TARGET_BEHAVIOUR_INPUT_CLOSE = "The target behaviour input file could not be closed.";
	private static final String TEMPLATES_INPUT_ERROR = "The templates input file cannot be found or we cannot read from it.";
	private static final String TEMPLATES_CLOSE_ERROR = "The templates input file cannot be closed.";

	/**
	 * Read the concentrations of the species at different points in time
	 * 
	 * @param filePath The path of the input file
	 * @return The set of species and the concentrations of the species at different points in time
	 * @throws IOException Exception thrown if the file was not found or if reading from it was impossible
	 * @throws InvalidInputException The number of tokens in a line is invalid
	 * @throws NumberFormatException The concentration specification is invalid
	 */
	public static SpeciePoolEvolution readTargetBehavior(String filePath) throws InvalidInputException {
		BufferedReader reader = null;
		SpeciePoolEvolution speciePoolEvolution = new SpeciePoolEvolution();

		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));

			readConcentrationsAtSpecificTimePoints(filePath, speciePoolEvolution, reader);
		} catch (IOException ioException) {
			throw new InvalidInputException(TARGET_BEHAVIOUR_INPUT_ERROR);
		} catch (NumberFormatException numberFormatException) {
			throw new InvalidInputException(TARGET_BEHAVIOUR_NUMBER_ERROR);
		} catch (InvalidInputException invalidInputException) {
			throw invalidInputException;
		}
		finally {
			closeTargetBehaviourReader(reader);
		}
		
		return speciePoolEvolution;
	}

	/**
	 * Read the templates for creating reactions (i.e. rules) from the given file and create 
	 * all the possible reactions with the
	 * 
	 * @param filePath The path of the input file containing the templates
	 * @param speciePool The pool containing among others the set of species
	 * @return The repository containing all the rules generated for the given templates
	 * @throws InvalidInputException 
	 */
	public static RuleRepository readTemplatesAndCreateRules(String filePath, SpeciePoolEvolution speciePool) throws InvalidInputException {
		RuleRepository ruleRepository = new RuleRepositoryImpl();

		List<String> templates = readTemplatesFromFile(filePath);
		Set<Specie> species = speciePool.getSpecieSet();

		List<String> generatedRulesAsStrings = generateRulesAsStringsUsingTemplates(templates, species);
		List<Rule> generatedRules = convertStringsToRules(generatedRulesAsStrings);

		addRulesToRepository(generatedRules, ruleRepository);
		addGeneratedSpeciesToSpeciePool(ruleRepository.getSpecies(), speciePool);

		return ruleRepository;
	}

	/**
	 * Add the generated species to the existing specie pool
	 * 
	 * Prec: The initial set of species was already initialised
	 * 
	 * @param listOfAllSpecies The list of all species = initial species (i.e. for which the concentrations are known) +
	 * 													 species which were generated afterwards
	 * @param speciePool The already initialised instance of the SpeciePoolEvolution class
	 */
	private static void addGeneratedSpeciesToSpeciePool(List<Specie> listOfAllSpecies, SpeciePoolEvolution speciePool) {
		if (listOfAllSpecies != null) {
			Set<Specie> initialSpeciesSet = speciePool.getSpecieSet();
			
			if (initialSpeciesSet != null) {
				for (Specie specie : listOfAllSpecies) {
					if (!initialSpeciesSet.contains(specie)) {
						speciePool.addGeneratedSpecie(specie);
					}
				}
			}
		}
	}

	/**
	 * Add the rules from the list of generated rules to the rule repository
	 * 
	 * @param generatedRules The list of generated rules
	 * @param ruleRepository Repository in which all rules will be inserted
	 */
	private static void addRulesToRepository(List<Rule> generatedRules, RuleRepository ruleRepository) {
		for (Rule tmpRule : generatedRules) {
			ruleRepository.addRule(tmpRule);
		}
	}

	/**
	 * Convert the given strings to rules
	 * 
	 * @param generatedRulesAsStrings Rules represented as strings
	 * @return List of rules
	 */
	private static List<Rule> convertStringsToRules(List<String> generatedRulesAsStrings) {
		List<Rule> generatedRules = new ArrayList<Rule>();
		
		for (String tmpGeneratedRule : generatedRulesAsStrings) {
			Rule newRule = RuleFactory.getInstance().createRuleFromString(tmpGeneratedRule);
			
			generatedRules.add(newRule);
		}
		
		return generatedRules;
	}

	/**
	 * Generate all the possible rules using the given set of species and templates
	 * 
	 * Each variable within the template will be replaced with a specie from the set,
	 * such that in the end all the possible combinations have been generated
	 * 
	 * Therefore, the solution of the problem is to generate all permutations considering
	 * as variables the components of the templates (i.e. products, substrates, enzymes) 
	 * and concrete values of the variables the elements from the set of species
	 * 
	 * @param templates The templates used for generating all the possible rules
	 * @param species The set of species that can be used for generating all the rules
	 * @return The list of rules represented as strings
	 */
	private static List<String> generateRulesAsStringsUsingTemplates(List<String> templates, Set<Specie> species) {
		List<Specie> specieList = getListOfSpeciesFromSet(species);
		List<String> generatedRules = new ArrayList<String>();
		
		for (String tmpTemplate : templates) {
			List<String> variables = getVariablesFromTemplate(tmpTemplate);
			List<String[]> permutations = new ArrayList<String[]>();
			
			getAllPermutations(permutations, specieList, variables.size());
			
			generateRulesAsStringsFromPermutations(generatedRules, permutations, tmpTemplate, variables);
		}
		
		return generatedRules;
	}

	/**
	 * Generate the rules as strings corresponding to the given permutations
	 * 
	 * @param generatedRules The generated rules
	 * @param permutations The list of permutations
	 * @param tmpTemplate The template used for constructing the rules
	 * @param variables The list of variables in the template
	 */
	private static void generateRulesAsStringsFromPermutations(List<String> generatedRules, List<String[]> permutations,
			String tmpTemplate, List<String> variables) {
		int nrOfVariables = variables.size();
		
		for (String[] tmpPermutation : permutations) {
			String newRule = new String(tmpTemplate);
			
			for (int i=0; i<nrOfVariables; i++) {
				newRule = newRule.replaceAll(variables.get(i), tmpPermutation[i]);
			}
			
			generatedRules.add(newRule);
		}
	}

	/**
	 * Generate all the permutations considering the set of species as input
	 * Algorithm: Classic (Non-recursive) Backtracking
	 * 
	 * @param permutations The list of generated permutations
	 * @param specieList The list of input species
	 * @param nrOfVariables The number of elements in a permutation
	 */
	private static void getAllPermutations(List<String[]> permutations, List<Specie> specieList, int nrOfVariables) {
		Integer[] possible = new Integer[nrOfVariables];
		Integer nrOfSpecies = specieList.size();
		Integer index = 0;
		Integer currentElement = 0;
		
		possible[index] = initialize(index);
		
		while (index >= 0) {
			currentElement = possible[index];
			
			boolean foundNextElement = false;
			IntegerWrapper nextElement = new IntegerWrapper();
			
			while ((isNextElement(currentElement, nrOfSpecies, nextElement)) && (foundNextElement == false)) {
				currentElement = nextElement.getValue();
				
				if (isContinueCondition(possible, index, currentElement)) {
					foundNextElement = true;
				}
			}
			
			if (foundNextElement) {
				possible[index] = currentElement;
				
				if (isSolution(possible, index, nrOfVariables)) {
					String[] newPermutation = getPermutationFromArray(possible, specieList, nrOfVariables);
					
					permutations.add(newPermutation);
				} else {
					index++;
					
					possible[index] = initialize(index);
				}
			} else {
				index--;
			}
		}
		
	}

	/**
	 * Construct the permutation of species corresponding to the permutation of indexes
	 * from the array of species
	 * 
	 * @param possible The array of indexes which is a valid permutation
	 * @param specieList The list of species
	 * @param nrOfVariables The number of variables in a permutation
	 * @return The constructed permutation of species
	 */
	private static String[] getPermutationFromArray(Integer[] possible, List<Specie> specieList, int nrOfVariables) {
		String[] permutation = new String[nrOfVariables];
		
		for (int i=0; i<nrOfVariables; i++) {
			permutation[i] = specieList.get(possible[i]).toString();
		}
		
		return permutation;
	}

	/**
	 * Check if the current developing array is a permutation of the wanted length
	 * i.e. it is a solution
	 * 
	 * @param possible The current developing array
	 * @param index The index of the last element in the array
	 * @param nrOfVariables The number of required elements in a permutation
	 * @return True if the array is a solution and false otherwise
	 */
	private static boolean isSolution(Integer[] possible, int index,
			int nrOfVariables) {
		if (index == (nrOfVariables - 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if the permutation obeys all constraints and can be further constructed upon
	 * 
	 * @param possible The current developing permutation
	 * @param index The index of the element within the permutation
	 * @param currentElement The proposed value for the element whose index is given
	 * @return True if all constraints are satisfied and false otherwise
	 */
	private static boolean isContinueCondition(Integer[] possible, int index, int currentElement) {
		for (int i=0; i<index; i++) {
			if (possible[i] == currentElement) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Check if there exists a next element which can be chosen instead of the current
	 * element from the permutation 
	 * 
	 * @param currentElement The current value of the element in the permutation
	 * @param nrOfSpecies The total number of available species
	 * @param nextElement The next possible element if such an element exists
	 * @return True if we can choose another value for the current element and false otherwise
	 */
	private static boolean isNextElement(int currentElement, int nrOfSpecies, IntegerWrapper nextElement) {
		if (currentElement < (nrOfSpecies - 1)) {
			nextElement.setValue(currentElement + 1);
			
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Initialise the element with the given index in the current developing permutation
	 * 
	 * @param index The index of the element in the permutation
	 * @return Return the initialisation element with the given index in the array
	 */
	private static int initialize(int index) {
		return -1;
	}

	private static List<String> getVariablesFromTemplate(String template) {
		List<String> variablesFromTemplate = new ArrayList<String>();
		String[] variables = template.split("(\\+)|(\\|)|(->)|(,)");
		
		for (String tmpVariable : variables) {
			if (!variablesFromTemplate.contains(tmpVariable)) {
				variablesFromTemplate.add(tmpVariable);
			}
		}
		
		return variablesFromTemplate;
	}

	/**
	 * Read the templates from the input file and return them as a list 
	 * 
	 * The structure of the templates can be one of the following:
	 * 	1. A+B->A|B
	 * 	2. A+B->A,B
	 *  3. A|B->C|D
	 *  4. A|B->C,D
	 * 
	 * @param filePath The path of the input file containing the templates
	 * @return The list of templates represented as strings
	 * @throws InvalidInputException The input file cannot be opened, we cannot read from it or cannot close it
	 */
	private static List<String> readTemplatesFromFile(String filePath) throws InvalidInputException {
		BufferedReader reader = null;
		List<String> templates = new ArrayList<String>();

		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			
			readTemplatesFromFile(reader, filePath, templates);
		} catch (IOException ioException) {
			throw new InvalidInputException(TEMPLATES_INPUT_ERROR);
		} finally {
			closeTemplatesReader(reader);
		}

		return templates;
	}

	private static List<Specie> getListOfSpeciesFromSet(Set<Specie> species) {
		List<Specie> listOfSpecies = new ArrayList<Specie>();
		
		listOfSpecies.addAll(species);
		
		return listOfSpecies;
	}
	
	private static void readTemplatesFromFile(BufferedReader reader, String filePath, List<String> templates) throws IOException {
		String line = reader.readLine();

		while (line != null) {
			templates.add(line);

			line = reader.readLine();
		}
	}

	private static void closeTargetBehaviourReader(BufferedReader reader)
			throws InvalidInputException {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException ioException) {
				throw new InvalidInputException(TARGET_BEHAVIOUR_INPUT_CLOSE);
			}
		}
	}
	
	private static void closeTemplatesReader(BufferedReader reader)
			throws InvalidInputException {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException ioException) {
				throw new InvalidInputException(TEMPLATES_CLOSE_ERROR);
			}
		}
	}

	/**
	 * Read the concentrations of the species at specific points in time
	 * 
	 * @param filePath The path of the input file
	 * @param speciePoolEvolution The collection of all the pairs <time point, list of pairs [specie, concentration]>
	 * @param reader The Reader which will be used to read every line of the file
	 * @throws IOException Exception thrown if the file was not found or if reading from it was impossible
	 * @throws InvalidInputException The number of tokens in a line is invalid
	 * @throws NumberFormatException The concentration specification is invalid
	 */
	private static void readConcentrationsAtSpecificTimePoints(String filePath, SpeciePoolEvolution speciePoolEvolution, BufferedReader reader) throws IOException, NumberFormatException, InvalidInputException {
		List<Specie> speciesList = readSpeciesFromFile(reader);

		String line = reader.readLine();

		while (line != null) {
			String[] tokens = line.split(CONCENTRATION_SEPARATOR);

			Integer timeStep = Double.valueOf(tokens[0]).intValue();

			SpeciePool speciePool = readConcentrationsOfSpecies(tokens, speciesList);

			speciePoolEvolution.addPhase(timeStep, speciePool);

			line = reader.readLine();
		}
	}

	/**
	 * Read the list of species using the provided reader
	 *  
	 * @param reader BufferedReader which opened the input file containing the concentrations
	 *               of all the species at different points in time
	 * @return The list of species in case of success and an empty list otherwise
	 * @throws IOException Throws an exception in case it is impossible to read from the file
	 */
	private static List<Specie> readSpeciesFromFile(BufferedReader reader) throws IOException {
		List<Specie> speciesList = new ArrayList<Specie>();

		String line = reader.readLine();
		String[] species = line.split("\\]\\|\\[");

		/**
		 * The format of the first line of the input file is: Time|Specie1|Specie2|...|Specie9|...
		 * 
		 * Therefore, skip species[0] -- represents time which is not a specie
		 */
		for (int i=1; i<species.length; i++) {
			String specieName = species[i].replaceAll("[\\[\\]]", "");
			Specie newSpecie = new Specie(specieName);

			speciesList.add(newSpecie);
		}

		return speciesList;
	}

	/**
	 * Read the concentrations of the species at the given points in time
	 * 
	 * @param tokens The tokens which represent the point in time and the concentrations of the species
	 * @param speciesList The list of species read from the first line of the input file
	 * @return The list of <specie, concentration> pairs
	 * @throws NumberFormatException Throws an exception in case of wrong format specification for one of the concentrations
	 * @throws InvalidInputException The number of tokens in the current line is invalid
	 */
	private static SpeciePool readConcentrationsOfSpecies(String[] tokens, List<Specie> speciesList) throws NumberFormatException, InvalidInputException {
		if (tokens.length != (speciesList.size() + 1)) {
			throw new InvalidInputException(TOKENS_NUMBER_ERROR);
		}

		SpeciePool speciePool = new SpeciePhase();

		for (int i = 1; i < tokens.length; i++) {
			Specie currentSpecie = speciesList.get(i - 1);
			Double currentSpecieConcentration = Double.valueOf(tokens[i]) / 100;

			speciePool.addConcentration(currentSpecie, currentSpecieConcentration);
		}

		return speciePool;
	}

}
