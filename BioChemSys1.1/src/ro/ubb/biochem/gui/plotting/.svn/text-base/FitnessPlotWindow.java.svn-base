package ro.ubb.biochem.gui.plotting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.WindowConstants;

import ro.ubb.biochem.algorithm.Algorithm;
import ro.ubb.biochem.gui.AlgorithmListener;
import ro.ubb.biochem.program.elements.Program;

public class FitnessPlotWindow extends javax.swing.JFrame implements AlgorithmListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static Integer NUMBER_OF_TIME_STEPS = 100;

	private PlotView plotPanel;
	private Algorithm algorithm;
	private PlotData plotData;
	private LinkedList<Double> dataList;
	private Integer numberOfDataPieces;

	public FitnessPlotWindow() {
		super("Fitness evolution");
		initGUI();
		Integer minX = 0;
		Integer maxX = NUMBER_OF_TIME_STEPS;
		Double minY = 0.0;
		Double maxY = 0.0;
		numberOfDataPieces = 0;
		dataList = new LinkedList<Double>();
		changePlotData(minX, maxX, minY, maxY);
	}
	
	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	private void changePlotData(Integer minX, Integer maxX, Double minY, Double maxY) {
		minY = 0.0;
		plotData = new PlotData(minX, maxX, minY, maxY, Color.BLACK, "Fitness");
		List<PlotData> plotDataList = new ArrayList<PlotData>();
		plotDataList.add(plotData);
		Iterator<Double> it = dataList.iterator();
		for (int i = minX; i < numberOfDataPieces; i++) {
			try {
				plotData.addPoint(new PlotPoint(i, it.next()));
			}
			catch (PlotException ex) {
				//should never happen
				throw new RuntimeException("Fatal Error");
			}
		}
		plotPanel.setPlotData(plotDataList);
	}

	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				plotPanel = new PlotView();
				getContentPane().add(plotPanel);
				plotPanel.setBounds(0, 0, 1084, 624);
				plotPanel.setAxisNames("Generation", "Fitness");
			}

			pack();
			this.setSize(1100, 700);
			this.setResizable(false);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	@Override
	public void newGenerationCreatedNotification() {
		newGenerationCreatedNotification(algorithm.getPopulation().getBestFitness(), algorithm.getBestProgram());
	}
	
	public void newGenerationCreatedNotification(Double fitness, Program program){
		System.out.println("Best fitness: " + fitness);
		System.out.println("Best program: " + program);
		System.out.println(program.getReactionNo());
		if (numberOfDataPieces >= NUMBER_OF_TIME_STEPS) {
			dataList.remove();
		}
		numberOfDataPieces++;
		dataList.add(fitness);

		Integer minX = 0;
		Integer maxX = numberOfDataPieces;
		if (numberOfDataPieces < NUMBER_OF_TIME_STEPS) {
			maxX = NUMBER_OF_TIME_STEPS;
		}
		else {
			minX = numberOfDataPieces - NUMBER_OF_TIME_STEPS;
		}
		Double minY = Double.POSITIVE_INFINITY;
		Double maxY = Double.NEGATIVE_INFINITY;
		for (Double f : dataList) {
			if (f < minY) {
				minY = f;
			}
			if (f > maxY) {
				maxY = f;
			}
		}
		changePlotData(minX, maxX, minY, maxY);

	}

}
