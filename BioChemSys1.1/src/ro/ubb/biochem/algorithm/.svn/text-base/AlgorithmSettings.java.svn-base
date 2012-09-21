package ro.ubb.biochem.algorithm;

import java.util.ArrayList;
import java.util.List;

import ro.ubb.biochem.operators.Crossover;
import ro.ubb.biochem.operators.Mutation;
import ro.ubb.biochem.operators.Selection;

@SuppressWarnings("rawtypes")
public class AlgorithmSettings {
	
	private Integer populationSize;
	
	private boolean isStoppingCriteria;
	
	private Integer maxInterations = 0;
	
	private String targetBehaviorFile;
	
	private String possibleCombinationsFile;
	
	private String outputFileName;
	
	private List<Mutation> mutationOps;
	
	private Crossover crossoverOp;
	
	private Selection selectionCrossoverOp;
	
	private Selection selectedSurvivalOp;
	
	private Double mutationProbability;

	public Integer getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
	}

	public boolean isStoppingCriteria() {
    	return isStoppingCriteria;
    }

	public void setStoppingCriteria(boolean isStoppingCriteria) {
    	this.isStoppingCriteria = isStoppingCriteria;
    }

	public Integer getMaxInterations() {
		return maxInterations;
	}

	public void setMaxInterations(Integer maxInterations) {
		this.maxInterations = maxInterations;
	}

	public String getTargetBehaviorFile() {
		return targetBehaviorFile;
	}

	public void setTargetBehaviorFile(String targetBehaviorFile) {
		this.targetBehaviorFile = targetBehaviorFile;
	}

	public String getPossibleCombinationsFile() {
		return possibleCombinationsFile;
	}

	public void setPossibleCombinationsFile(String possibleCombinationsFile) {
		this.possibleCombinationsFile = possibleCombinationsFile;
	}

	public List<Mutation> getMutationOps() {
		return mutationOps;
	}

	public Crossover getCrossoverOp() {
		return crossoverOp;
	}

	public Selection getSelectionCrossoverOp() {
		return selectionCrossoverOp;
	}

	public Selection getSelectionsSurvivalOp() {
		return selectedSurvivalOp;
	}

	
	public void setMutationOps(List<String> selectedMutationOps) {
		mutationOps = new ArrayList<Mutation>();
		for(String mutationOpName: selectedMutationOps){
			try{
				Class mutateClass = Class.forName(mutationOpName);
				mutationOps.add((Mutation)mutateClass.newInstance());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void setCrossoverOp(String selectedCrossoverOp) {
		try {
			Class crossoverClass = Class.forName(selectedCrossoverOp);
			crossoverOp = (Crossover) crossoverClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setSelectionCrossoverOp(String selectedSelectionCrossoverOp) {
		try {
			Class crossoverClass = Class.forName(selectedSelectionCrossoverOp);
			selectionCrossoverOp = (Selection) crossoverClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setSelectionSurvivalOp(String selectedSelectionSurvivalOp) {
		try {
			Class crossoverClass = Class.forName(selectedSelectionSurvivalOp);
			selectedSurvivalOp = (Selection) crossoverClass.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(Double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	
	public String getOutputFileName(){
		return outputFileName;
	}
	

}
