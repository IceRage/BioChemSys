package ro.ubb.biochem.persistance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.reaction.components.RuleRepository;
import ro.ubb.biochem.reaction.components.RuleRepositoryImpl;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePhase;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

public class InputReader {

	public static SpeciePoolEvolution readTargetBehavior(String fileName) {
		SpeciePoolEvolution speciesInput = new SpeciePoolEvolution();
		List<Specie> speciesList = new ArrayList<Specie>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
			String line = "";
			Integer counter = 0;
			while ((line = br.readLine()) != null) {
				if (counter == 0) {
					String[] species = line.split("\\]\\|\\[");
					for (int i = 1; i < species.length; i++) { // skip species[0] --time
						speciesList.add(new Specie(species[i].replaceAll("\\[", "").replaceAll("\\]", "")));
					}
				} else {
					String[] tokens = line.split("\\|");
					SpeciePool sp = new SpeciePhase();
					Integer timeStep = 0;
					for (int i = 0; i < tokens.length; i++) {
						if (i == 0) {
							try {
								timeStep = Double.valueOf(tokens[i]).intValue();
							} catch (NumberFormatException e) {
								;
							}
						} else {
							Double concentration = 0.0;
							try {
								concentration = Double.valueOf(tokens[i]) / 100;
							} catch (NumberFormatException e) {
								;
							}
							Specie currentSpecie = speciesList.get(i - 1);
							sp.addConcentration(currentSpecie, concentration);
						}
					}
					speciesInput.addPhase(timeStep, sp);
				}
				counter++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return speciesInput;
	}

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

}
