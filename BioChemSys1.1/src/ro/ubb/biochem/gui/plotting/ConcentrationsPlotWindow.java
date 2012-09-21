package ro.ubb.biochem.gui.plotting;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ro.ubb.biochem.algorithm.Algorithm;
import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.species.components.SpeciePoolEvolution;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ConcentrationsPlotWindow extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Integer REFRESH_INTERVAL = 1000;

	private JPanel headerPanel;
	private PlotView plotPanel;
	private JComboBox<Specie> specieComboBox;
	private DefaultComboBoxModel<Specie> specieComboBoxModel;
	private JLabel specieLabel;
	private Algorithm algorithm;
	private SpeciePoolEvolution targetEvolution;

	private boolean keepRefreshing;

	public ConcentrationsPlotWindow() {
		super("Concentration Evolution");
		initGUI();
	}

	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				headerPanel = new JPanel();
				getContentPane().add(headerPanel);
				headerPanel.setBounds(0, 5, 1079, 33);
				headerPanel.setLayout(null);
				specieLabel = new JLabel("Specie:");
				headerPanel.add(specieLabel);
				specieLabel.setBounds(12, 8, 72, 16);
				specieComboBox = new JComboBox<Specie>();
				specieComboBoxModel = new DefaultComboBoxModel<Specie>();
				specieComboBox.setModel(specieComboBoxModel);
				specieComboBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						refreshPlot();
					}
				});
				headerPanel.add(specieComboBox);
				specieComboBox.setBounds(96, 5, 232, 23);
			}
			{
				plotPanel = new PlotView();
				getContentPane().add(plotPanel);
				plotPanel.setBounds(0, 38, 1084, 624);
				plotPanel.setAxisNames("Time", "Concentration");
			}

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					stop();
				}
			});
			pack();
			this.setSize(1100, 700);
			this.setResizable(false);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	public void setSpecies(List<Specie> specieList) {
		specieComboBoxModel.removeAllElements();
		for (Specie specie : specieList) {
			specieComboBoxModel.addElement(specie);
		}
	}

	public void setTargetEvolution(SpeciePoolEvolution target) {
		this.targetEvolution = target;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	private void refreshPlot() {
		Program program = algorithm.getBestProgram();
		List<PlotPoint> targetPointList = new ArrayList<PlotPoint>();
		List<PlotPoint> actualPointList = new ArrayList<PlotPoint>();

		Specie selectedSpecie = (Specie) specieComboBox.getSelectedItem();

		Double x = targetEvolution.getTime(0).doubleValue();
		Double y = targetEvolution.getPhase(0).getSpecieConcentration(selectedSpecie) * 100;
		targetPointList.add(new PlotPoint(x, y));
		actualPointList.add(new PlotPoint(x, y));
		SpeciePool initialPool = targetEvolution.getPhase(0);

		Double minX = x;
		Double maxX = x;
		Double minTargetY = y;
		Double maxTargetY = y;
		Double minActualY = y;
		Double maxActualY = y;

		for (int i = 1; i < targetEvolution.getNumberOfPhases(); i++) {
			x = targetEvolution.getTime(i).doubleValue();
			if (minX > x) {
				minX = x;
			}
			if (maxX < x) {
				maxX = x;
			}
			y = targetEvolution.getPhase(i).getSpecieConcentration(selectedSpecie) * 100;
			if (minTargetY > y) {
				minTargetY = y;
			}
			if (maxTargetY < y) {
				maxTargetY = y;
			}
			targetPointList.add(new PlotPoint(x, y));

			try {
				initialPool = program.run(initialPool,
						targetEvolution.getTime(i) - targetEvolution.getTime(i - 1));
				y = initialPool.getSpecieConcentration(selectedSpecie) * 100;
				if (minActualY > y) {
					minActualY = y;
				}
				if (maxActualY < y) {
					maxActualY = y;
				}
			} catch (InvalidInputException e) {
				// should never happen
				throw new RuntimeException("Fatal error");
			}
			actualPointList.add(new PlotPoint(x, y));
		}
		PlotData targetPlotData = new PlotData(minX, maxX, minTargetY, maxTargetY, Color.RED, "Target");
		PlotData actualPlotData = new PlotData(minX, maxX, minActualY, maxActualY, Color.BLACK, "Actual");
		try {
			for (PlotPoint pp : targetPointList) {
				targetPlotData.addPoint(pp);
			}
			for (PlotPoint pp : actualPointList) {
				actualPlotData.addPoint(pp);
			}
		} catch (PlotException ex) {
			throw new RuntimeException("Fatal error");
		}

		List<PlotData> plotDataList = new ArrayList<PlotData>();
		plotDataList.add(targetPlotData);
		plotDataList.add(actualPlotData);
		plotPanel.setPlotData(plotDataList);
	}

	public void start() {
		keepRefreshing = true;
		new Refresher().start();
		this.setVisible(true);
	}

	public void stop() {
		keepRefreshing = false;
		this.setVisible(false);
	}

	private class Refresher extends Thread {

		public void run() {
			while (keepRefreshing) {
				refreshPlot();
				try {
					Thread.sleep(REFRESH_INTERVAL);
				} catch (InterruptedException e) {
					// do nothing
				}
			}
		}
	}
}
