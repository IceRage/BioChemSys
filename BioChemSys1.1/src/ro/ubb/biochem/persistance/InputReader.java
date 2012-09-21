package ro.ubb.biochem.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;
import ro.ubb.biochem.reaction.components.RuleRepositoryImpl;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePhase;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

/**
 * Class designed for reading the input:
 * 	1. The target behaviour i.e. the concentrations of the given species at different points in time
 *  2. The possible combinations i.e. the initial reactions from which other reactions will be derived
 */
public class InputReader {

	private static final String TOKENS_NUMBER_ERROR = "The number of tokens in the line is invalid.";
	private static final String CONCENTRATION_SEPARATOR = "\\|";

	/**
	 * Read the concentrations of the species at different points in time
	 * 
	 * @param filePath The path of the input file
	 * @return The set of species and the concentrations of the species at different points in time
	 * @throws IOException Exception thrown if the file was not found or if reading from it was impossible
	 * @throws InvalidInputException The number of tokens in a line is invalid
	 * @throws NumberFormatException The concentration specification is invalid
	 */
	public static SpeciePoolEvolution readTargetBehavior(String filePath) throws IOException, NumberFormatException, InvalidInputException {
		BufferedReader reader = null;

		try {
			SpeciePoolEvolution speciePoolEvolution = new SpeciePoolEvolution();
			reader = new BufferedReader(new FileReader(new File(filePath)));
			
			readConcentrationsAtSpecificTimePoints(filePath, speciePoolEvolution, reader);
			
			return speciePoolEvolution;
		} catch (Exception exception) {
			throw exception;
		} finally {
			reader.close();
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static RuleRepository readPossibleCombinations(String fileName) {
		RuleRepositoryImpl rules = new RuleRepositoryImpl();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] hs = line.split("\\-\\>");
				String[] lhs = hs[0].split("\\+");
				String[] rhs = hs[1].split(",");
				List<Specie> lhsList = new ArrayList<Specie>();
				for (String l : lhs) {
					lhsList.add(new Specie(l));
				}
				List<Specie> rhsList = new ArrayList<Specie>();
				for (String r : rhs) {
					rhsList.add(new Specie(r));
				}
				rules.addRule(new Rule(lhsList, rhsList));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		rules.printRules();

		return rules;
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
