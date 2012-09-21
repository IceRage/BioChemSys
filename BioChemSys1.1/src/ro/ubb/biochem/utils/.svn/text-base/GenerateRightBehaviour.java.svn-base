package ro.ubb.biochem.utils;

import java.util.Arrays;

import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.program.elements.Program1;
import ro.ubb.biochem.reaction.components.Reaction;
import ro.ubb.biochem.reaction.components.Rule;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePhase;
import ro.ubb.biochem.species.components.SpeciePool;

public class GenerateRightBehaviour {

	static Specie e1 = new Specie("E1");
	static Specie a1 = new Specie("A1");
	static Specie a2 = new Specie("A2");
	static Specie b1 = new Specie("B1");
	static Specie b2 = new Specie("B2");
	static Specie e1a1 = new Specie("E1|A1");
	static Specie e1a2 = new Specie("E1|A2");

	static Specie v1 = new Specie("V1");
	static Specie v2 = new Specie("V2");
	static Specie v3 = new Specie("V3");
	static Specie v4 = new Specie("V4");

	static Specie Raf1Star = new Specie("Raf1Star");
	static Specie RKIP = new Specie("RKIP");
	static Specie Raf1Star_RKIP = new Specie("Raf1Star|RKIP");
	static Specie ERKPP = new Specie("ERKPP");
	static Specie Raf1Star_RKIP_ERKPP = new Specie("Raf1Star|RKIP|ERKPP");
	static Specie ERK = new Specie("ERK");
	static Specie RKIPP = new Specie("RKIPP");
	static Specie MEKPP = new Specie("MEKPP");
	static Specie MEKPP_ERK = new Specie("MEKPP|ERK");
	static Specie RP = new Specie("RP");
	static Specie RP_RKIPP = new Specie("RP|RKIPPP");

	/**
	 * @param args
	 * @throws InvalidInputException
	 */
	public static void main(String[] args) throws InvalidInputException {
		Program1 p = getJAKSTATGoodProgram();
		SpeciePool pool = getJAKSTATSpeciePool();
		for (int i = 1; i <= 16; i++) {
			System.out.println((i - 1) + "|" + pool);
			pool = p.run(pool, i);
		}
		
	}

	public static Program1 getRKIPGoodProgram() {
		Program1 program1 = new Program1();
		program1.setFitness(0.5331624292082779);
		program1.setMaxKineticRateStep(2.932393360645528);

		Rule k1 = new Rule(Arrays.asList(Raf1Star, RKIP), Arrays.asList(Raf1Star_RKIP));
		Reaction kk1 = new Reaction(k1, 0.53);
		program1.addReaction(kk1);

		Rule k2 = new Rule(Arrays.asList(Raf1Star_RKIP), Arrays.asList(Raf1Star, RKIP));
		Reaction kk2 = new Reaction(k2, 0.0072);
		program1.addReaction(kk2);

		Rule k3 = new Rule(Arrays.asList(ERKPP, Raf1Star_RKIP), Arrays.asList(Raf1Star_RKIP_ERKPP));
		Reaction kk3 = new Reaction(k3, 0.625);
		program1.addReaction(kk3);

		Rule k4 = new Rule(Arrays.asList(Raf1Star_RKIP_ERKPP), Arrays.asList(ERKPP, Raf1Star_RKIP));
		Reaction kk4 = new Reaction(k4, 0.00245);
		program1.addReaction(kk4);

		Rule k5 = new Rule(Arrays.asList(Raf1Star_RKIP_ERKPP), Arrays.asList(Raf1Star, RKIPP, ERK));
		Reaction kk5 = new Reaction(k5, 0.0315);
		program1.addReaction(kk5);

		Rule k6 = new Rule(Arrays.asList(MEKPP, ERK), Arrays.asList(MEKPP_ERK));
		Reaction kk6 = new Reaction(k6, 0.8);
		program1.addReaction(kk6);

		Rule k7 = new Rule(Arrays.asList(MEKPP_ERK), Arrays.asList(MEKPP, ERK));
		Reaction kk7 = new Reaction(k7, 0.0075);
		program1.addReaction(kk7);

		Rule k8 = new Rule(Arrays.asList(MEKPP_ERK), Arrays.asList(MEKPP, ERKPP));
		Reaction kk8 = new Reaction(k8, 0.071);
		program1.addReaction(kk8);

		Rule k9 = new Rule(Arrays.asList(RP, RKIPP), Arrays.asList(RP_RKIPP));
		Reaction kk9 = new Reaction(k9, 0.92);
		program1.addReaction(kk9);

		Rule k10 = new Rule(Arrays.asList(RP_RKIPP), Arrays.asList(RP, RKIPP));
		Reaction kk10 = new Reaction(k10, 0.00122);
		program1.addReaction(kk10);

		Rule k11 = new Rule(Arrays.asList(RP_RKIPP), Arrays.asList(RP, RKIP));
		Reaction kk11 = new Reaction(k11, 0.87);
		program1.addReaction(kk11);

		return program1;

	}

	public static SpeciePool getRKIPSpeciePool() {
		SpeciePool pool = new SpeciePhase();
		pool.addConcentration(Raf1Star, 2.5);
		pool.addConcentration(RKIP, 2.5);
		pool.addConcentration(Raf1Star_RKIP, 0.0);
		pool.addConcentration(ERKPP, 0.0);
		pool.addConcentration(Raf1Star_RKIP_ERKPP, 0.0);
		pool.addConcentration(ERK, 2.5);
		pool.addConcentration(RKIPP, 0.0);
		pool.addConcentration(MEKPP, 2.5);
		pool.addConcentration(MEKPP_ERK, 0.0);
		pool.addConcentration(RP, 3.0);
		pool.addConcentration(RP_RKIPP, 0.0);

		return pool;
	}

	public static SpeciePool getSpeciePool() {
		SpeciePool pool = new SpeciePhase();
		pool.addConcentration(a1, 0.5);
		pool.addConcentration(a2, 0.5);
		pool.addConcentration(e1, 3.0);
		pool.addConcentration(b1, 0.0);
		pool.addConcentration(b2, 0.0);
		pool.addConcentration(e1a1, 0.0);
		pool.addConcentration(e1a2, 0.0);

		return pool;
	}

	public static SpeciePool getJAKSTATSpeciePool() {
		SpeciePool pool = new SpeciePhase();
		pool.addConcentration(v1, 0.197667);
		pool.addConcentration(v2, 0.0);
		pool.addConcentration(v3, 0.0);
		pool.addConcentration(v4, 0.0);
		return pool;
	}

	public static Program1 getJAKSTATGoodProgram() {
		Program1 program1 = new Program1();
		program1.setFitness(0.5331624292082779);
		program1.setMaxKineticRateStep(2.932393360645528);

		Rule k1 = new Rule(Arrays.asList(v1), Arrays.asList(v2));
		Reaction kk1 = new Reaction(k1, 2.16252);
		program1.addReaction(kk1);

		Rule k2 = new Rule(Arrays.asList(v2), Arrays.asList(v3));
		Reaction kk2 = new Reaction(k2, 1.0);
		program1.addReaction(kk2);

		Rule k3 = new Rule(Arrays.asList(v3), Arrays.asList(v4));
		Reaction kk3 = new Reaction(k3, 1.818527);
		program1.addReaction(kk3);

		Rule k4 = new Rule(Arrays.asList(v4), Arrays.asList(v1));
		Reaction kk4 = new Reaction(k4, 0.295625);
		program1.addReaction(kk4);

		return program1;

	}

	public static Program1 getGoodProgram() {
		Program1 program1 = new Program1();
		program1.setFitness(0.5331624292082779);
		program1.setMaxKineticRateStep(2.932393360645528);

		Rule k1 = new Rule(Arrays.asList(a1, e1), Arrays.asList(e1a1));
		Reaction kk1 = new Reaction(k1, 0.03);
		program1.addReaction(kk1);

		Rule k3 = new Rule(Arrays.asList(a2, e1), Arrays.asList(e1a2));
		Reaction kk3 = new Reaction(k3, 0.007);
		program1.addReaction(kk3);

		Rule k4 = new Rule(Arrays.asList(e1a2), Arrays.asList(b2, e1));
		Reaction kk4 = new Reaction(k4, 0.072);
		program1.addReaction(kk4);

		Rule k5 = new Rule(Arrays.asList(e1a1), Arrays.asList(b1, e1));
		Reaction kk5 = new Reaction(k5, 0.0521);
		program1.addReaction(kk5);

		return program1;
	}
	
	public static void generateNets(){
		SBMLExporter.exportProgramToSMBL(getRKIPSpeciePool(), getRKIPGoodProgram(), "realRKIP.xml");
	}

}
