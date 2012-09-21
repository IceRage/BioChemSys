/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
package ro.ubb.biochem.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import ro.ubb.biochem.algorithm.Algorithm;
import ro.ubb.biochem.algorithm.AlgorithmSettings;
import ro.ubb.biochem.algorithm.GPAlgorithm;
import ro.ubb.biochem.algorithm.UserStopCondition;
import ro.ubb.biochem.exceptions.InvalidInputException;
import ro.ubb.biochem.gui.plotting.ConcentrationsPlotWindow;
import ro.ubb.biochem.gui.plotting.FitnessPlotWindow;
import ro.ubb.biochem.operators.Crossover;
import ro.ubb.biochem.operators.Mutation;
import ro.ubb.biochem.operators.Selection;
import ro.ubb.biochem.utils.ClassesFinder;

public class GPUserFrame extends JFrame implements ActionListener, AlgorithmListener {

	private static final String FILEPATH_OUTPUT = "C:\\Users\\Ovi\\Documents\\Eclipse\\BioChemSys1.1\\files\\rkip.xml";
	private static final String FILEPATH_POSSIBLE_COMBINATIONS = "C:\\Users\\Ovi\\Documents\\Eclipse\\BioChemSys1.1\\files\\rkipRules.txt";
	private static final String FILEPATH_TARGET_BEHAVIOUR = "C:\\Users\\Ovi\\Documents\\Eclipse\\BioChemSys1.1\\files\\rkipTarget.txt";
	private static final String BASE_PACKAGE = "ro.ubb.biochem.operators";
	private static final long serialVersionUID = 1L;
	
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel6;
	private JLabel jLabel7;
	private JLabel jLabel8;
	private JLabel jLabel9;
	private JLabel jLabel10;
	private JPanel inputOutputPanel;
	private JButton browsePCFBtn;
	private JButton browseTBFBtn;
	private JTextField possibleCombinationsTxt;
	private JTextField targetBehaviorTxt;
	private JPanel gpPanel;
	private JButton stopBtn;
	private JButton startBtn;
	private JTextField iterationsTxt;
	private JRadioButton noIterationsRadio;
	private JRadioButton iterationsRadio;
	private ButtonGroup radioButtonsGroup;
	private JTextField populationSizeTxt;
	private JTextField mutationProbabilityTextField;
	private JLabel mutationProbabilityLabel;
	private JList<String> mutationOpSelector;
	private JScrollPane jScrollPaneMutation;
	private JComboBox<String> selectorOpForSurvivalSelector;
	private JComboBox<String> selectionOpCrossoverSelector;
	private JComboBox<String> crossoverOpSelector;

	private Integer currentGeneration;
	private ConcentrationsPlotWindow concentrationsPlotWindow;
	private JButton browseOutputBtn;
	private JTextField outputFileTxt;
	private JLabel jLabel11;
	private JButton resetButton;
	private JLabel generationLabel;

	private AlgorithmSettings algorithmSettings;
	private Algorithm algorithm;
	private FitnessPlotWindow fitnessPlotWindow;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GPUserFrame inst = new GPUserFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});

	}

	public GPUserFrame() {
		super("Biochemical Network Inference - GP-SA Approch");
		initGUI();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				UserStopCondition.stop();
				if (concentrationsPlotWindow != null) {
					concentrationsPlotWindow.stop();
					fitnessPlotWindow.dispose();
				}
			}
		});
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Biochemical Network Inference - GP-SA Approch");
				jLabel1.setBounds(259, 22, 330, 28);
				jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14));
			}
			{
				inputOutputPanel = new JPanel();
				getContentPane().add(inputOutputPanel);
				inputOutputPanel.setBounds(26, 67, 310, 444);
				inputOutputPanel.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
				inputOutputPanel.setLayout(null);
				{
					jLabel2 = new JLabel();
					inputOutputPanel.add(jLabel2);
					jLabel2.setText("Input/Output Stuff");
					jLabel2.setBounds(1, 1, 133, 19);
				}
				{
					jLabel4 = new JLabel();
					inputOutputPanel.add(jLabel4);
					jLabel4.setText("Target Behavior File:");
					jLabel4.setBounds(13, 32, 152, 16);
					jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12));
				}
				{
					targetBehaviorTxt = new JTextField();
					inputOutputPanel.add(targetBehaviorTxt);
					targetBehaviorTxt.setBounds(13, 54, 182, 22);
					targetBehaviorTxt.setText(FILEPATH_TARGET_BEHAVIOUR);
				}
				{
					jLabel5 = new JLabel();
					inputOutputPanel.add(jLabel5);
					jLabel5.setText("Possible Combinations File:");
					jLabel5.setBounds(13, 88, 182, 16);
					jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 12));
				}
				{
					possibleCombinationsTxt = new JTextField();
					inputOutputPanel.add(possibleCombinationsTxt);
					possibleCombinationsTxt.setBounds(13, 110, 182, 22);
					possibleCombinationsTxt.setText(FILEPATH_POSSIBLE_COMBINATIONS);
				}
				{
					browseTBFBtn = new JButton();
					inputOutputPanel.add(browseTBFBtn);
					browseTBFBtn.setText("Browse");
					browseTBFBtn.setBounds(217, 55, 81, 21);
					browseTBFBtn.addActionListener(this);
				}
				{
					browsePCFBtn = new JButton();
					inputOutputPanel.add(browsePCFBtn);
					inputOutputPanel.add(getJLabel11());
					inputOutputPanel.add(getOutputFile());
					inputOutputPanel.add(getBrowseOutputBtn());
					browsePCFBtn.setText("Browse");
					browsePCFBtn.setBounds(217, 110, 81, 22);
					browsePCFBtn.addActionListener(this);
				}
			}
			{
				gpPanel = new JPanel();
				getContentPane().add(gpPanel);
				gpPanel.setBounds(408, 67, 413, 444);
				gpPanel.setBorder(new LineBorder(new java.awt.Color(0, 0, 0), 1, false));
				gpPanel.setLayout(null);
				{
					jLabel3 = new JLabel();
					gpPanel.add(jLabel3);
					jLabel3.setText("GP Stuff");
					jLabel3.setBounds(1, 1, 66, 20);
				}
				{
					jLabel6 = new JLabel();
					gpPanel.add(jLabel6);
					jLabel6.setText("Population Size:");
					jLabel6.setBounds(13, 32, 104, 16);
					jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 12));
				}
				{
					populationSizeTxt = new JTextField();
					gpPanel.add(populationSizeTxt);
					gpPanel.add(getIterationsRadio());
					gpPanel.add(getNoIterationsRadio());
					radioButtonsGroup = new ButtonGroup();
					radioButtonsGroup.add(iterationsRadio);
					radioButtonsGroup.add(noIterationsRadio);
					gpPanel.add(getIterationsTxt());
					gpPanel.add(getStartBtn());
					gpPanel.add(getStopBtn());
					gpPanel.add(getJLabel7());
					gpPanel.add(getJLabel8());
					gpPanel.add(getJLabel9());
					gpPanel.add(getJLabel10());
					gpPanel.add(getCrossoverOpSelector());
					gpPanel.add(getSelectionOpCrossoverSelector());
					gpPanel.add(getSelectorOpForSurvivalSelector());
					gpPanel.add(getJScrollPaneMutation());
					gpPanel.add(getMutationProbabilityLabel());
					gpPanel.add(getMutationProbabilityTextField());
					gpPanel.add(getGenerationLabel());
					gpPanel.add(getResetButton());
					populationSizeTxt.setText("100");
					populationSizeTxt.setBounds(104, 26, 186, 22);
				}
			}
			pack();
			this.setSize(869, 559);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JRadioButton getIterationsRadio() {
		if (iterationsRadio == null) {
			iterationsRadio = new JRadioButton();
			iterationsRadio.setText("Run for max. iterations");
			iterationsRadio.setBounds(14, 68, 148, 20);
			iterationsRadio.setFont(new java.awt.Font("Segoe UI", 2, 12));
			iterationsRadio.addActionListener(this);
		}
		return iterationsRadio;
	}

	private JRadioButton getNoIterationsRadio() {
		if (noIterationsRadio == null) {
			noIterationsRadio = new JRadioButton();
			noIterationsRadio.setText("Use stopping criteria");
			noIterationsRadio.setBounds(263, 68, 138, 20);
			noIterationsRadio.setFont(new java.awt.Font("Segoe UI", 2, 12));
			noIterationsRadio.addActionListener(this);
			noIterationsRadio.setSelected(true);
		}
		return noIterationsRadio;
	}

	private JTextField getIterationsTxt() {
		if (iterationsTxt == null) {
			iterationsTxt = new JTextField();
			iterationsTxt.setText("500");
			iterationsTxt.setBounds(167, 66, 90, 22);
			iterationsTxt.setEditable(false);
			iterationsTxt.setEnabled(false);
		}
		return iterationsTxt;
	}

	private JButton getStartBtn() {
		if (startBtn == null) {
			startBtn = new JButton();
			startBtn.setText("Start");
			startBtn.setBounds(13, 410, 71, 22);
			startBtn.addActionListener(this);
		}
		return startBtn;
	}

	private JButton getStopBtn() {
		if (stopBtn == null) {
			stopBtn = new JButton();
			stopBtn.setText("Stop");
			stopBtn.setBounds(237, 410, 72, 22);
			stopBtn.addActionListener(this);
		}
		return stopBtn;
	}

	private JLabel getJLabel7() {
		if (jLabel7 == null) {
			jLabel7 = new JLabel();
			jLabel7.setText("Mutation Op:");
			jLabel7.setBounds(13, 198, 93, 16);
			jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 12));
		}
		return jLabel7;
	}

	private JLabel getJLabel8() {
		if (jLabel8 == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("Crossover Op:");
			jLabel8.setBounds(13, 103, 93, 16);
			jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 12));
		}
		return jLabel8;
	}

	private JLabel getJLabel9() {
		if (jLabel9 == null) {
			jLabel9 = new JLabel();
			jLabel9.setText("Selection for crossover:");
			jLabel9.setBounds(13, 136, 148, 16);
			jLabel9.setFont(new java.awt.Font("Segoe UI", 2, 12));
		}
		return jLabel9;
	}

	private JLabel getJLabel10() {
		if (jLabel10 == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("Selection for survival:");
			jLabel10.setBounds(14, 167, 138, 16);
			jLabel10.setFont(new java.awt.Font("Segoe UI", 2, 12));
		}
		return jLabel10;
	}

	private JComboBox<String> getCrossoverOpSelector() {
		if (crossoverOpSelector == null) {
			List<String> crossoverOp = ClassesFinder.getClassNamesImplementingInterface(Crossover.class,
					BASE_PACKAGE);
			DefaultComboBoxModel<String> crossoverOpSelectorModel = new DefaultComboBoxModel<String>(
					crossoverOp.toArray(new String[0]));
			crossoverOpSelector = new JComboBox<String>();
			crossoverOpSelector.setModel(crossoverOpSelectorModel);
			crossoverOpSelector.setBounds(104, 100, 291, 22);
		}
		return crossoverOpSelector;
	}

	private JComboBox<String> getSelectionOpCrossoverSelector() {
		if (selectionOpCrossoverSelector == null) {
			List<String> selectionOp = ClassesFinder.getClassNamesImplementingInterface(Selection.class,
					BASE_PACKAGE);
			DefaultComboBoxModel<String> selectionOpCrossoverSelectorModel = new DefaultComboBoxModel<String>(
					selectionOp.toArray(new String[0]));
			selectionOpCrossoverSelector = new JComboBox<String>();
			selectionOpCrossoverSelector.setModel(selectionOpCrossoverSelectorModel);
			selectionOpCrossoverSelector.setBounds(138, 130, 257, 22);
		}
		return selectionOpCrossoverSelector;
	}

	private JComboBox<String> getSelectorOpForSurvivalSelector() {
		if (selectorOpForSurvivalSelector == null) {
			List<String> selectionOp = ClassesFinder.getClassNamesImplementingInterface(Selection.class,
					BASE_PACKAGE);
			DefaultComboBoxModel<String> selectorOpForSurvivalSelectorModel = new DefaultComboBoxModel<String>(
					selectionOp.toArray(new String[0]));
			selectorOpForSurvivalSelector = new JComboBox<String>();
			selectorOpForSurvivalSelector.setModel(selectorOpForSurvivalSelectorModel);
			selectorOpForSurvivalSelector.setBounds(138, 164, 257, 22);
		}
		return selectorOpForSurvivalSelector;
	}

	private JList<String> getMutationOpSelector() {
		if (mutationOpSelector == null) {
			DefaultListModel<String> mutationOpSelectorModel = new DefaultListModel<String>();
			List<String> selectionOp = ClassesFinder.getClassNamesImplementingInterface(Mutation.class,
					BASE_PACKAGE);
			for (String selectionOperator : selectionOp) {
				mutationOpSelectorModel.addElement(selectionOperator);
			}
			mutationOpSelector = new JList<String>();
			mutationOpSelector.setModel(mutationOpSelectorModel);
			mutationOpSelector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			mutationOpSelector.setPreferredSize(new java.awt.Dimension(288, 104));
		}
		return mutationOpSelector;
	}

	private JScrollPane getJScrollPaneMutation() {
		if (jScrollPaneMutation == null) {
			jScrollPaneMutation = new JScrollPane();
			jScrollPaneMutation.setBounds(104, 198, 291, 108);
			jScrollPaneMutation.setViewportView(getMutationOpSelector());
		}
		return jScrollPaneMutation;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == browseTBFBtn) {
			chooseFile(1);
		}
		if (e.getSource() == browsePCFBtn) {
			chooseFile(2);
		}
		if(e.getSource() == browseOutputBtn){
			chooseFile(3);
		}
		if (e.getSource() == startBtn) {
			algorithmSettings = new AlgorithmSettings();

			String targetBehaviorFileName = targetBehaviorTxt.getText();
			if (targetBehaviorFileName == null || targetBehaviorFileName.equals("")) {
				showErrorMessage("You must provide a target behavior file.");
				return;
			}
			algorithmSettings.setTargetBehaviorFile(targetBehaviorFileName);

			String possibleCombinationsFileName = possibleCombinationsTxt.getText();
			if (possibleCombinationsFileName == null || possibleCombinationsFileName.equals("")) {
				showErrorMessage("You must provide a file with possible combinations.");
				return;
			}
			algorithmSettings.setPossibleCombinationsFile(possibleCombinationsFileName);
			
			algorithmSettings.setOutputFileName(outputFileTxt.getText());

			String populationSizeString = populationSizeTxt.getText();
			if (populationSizeString == null || populationSizeString.equals("")) {
				showErrorMessage("You must provide the population size.");
				return;
			}
			Integer populationSize = 0;
			try {
				populationSize = Integer.valueOf(populationSizeString);
			} catch (NumberFormatException nfe) {
				showErrorMessage("The population size must be an integer number.");
				return;
			}
			algorithmSettings.setPopulationSize(populationSize);

			Integer maxIterations = 0;
			if (iterationsRadio.isSelected()) {
				String iterationsString = iterationsTxt.getText();
				if (iterationsString == null || iterationsString.equals("")) {
					showErrorMessage("You must provide the number of iterations to run.");
					return;
				}
				try {
					maxIterations = Integer.valueOf(iterationsString);
				} catch (NumberFormatException nfe) {
					showErrorMessage("The number of iterations must be an integer number.");
					return;
				}
				
				algorithmSettings.setStoppingCriteria(false);
			} else {
				algorithmSettings.setStoppingCriteria(true);
			}
			
			algorithmSettings.setMaxInterations(maxIterations);

			List<String> selectedMutationOps = mutationOpSelector.getSelectedValuesList();
			if (selectedMutationOps.size() == 0) {
				showErrorMessage("You must select at least one mutation operator!");
				return;
			}
			algorithmSettings.setMutationOps(selectedMutationOps);

			String selectedCrossoverOp = (String) crossoverOpSelector.getSelectedItem();
			if (crossoverOpSelector.getSelectedIndex() < 0) {
				showErrorMessage("You must select a crossover operator!");
				return;
			}
			algorithmSettings.setCrossoverOp(selectedCrossoverOp);

			String selectedSelectionCrossoverOp = (String) selectionOpCrossoverSelector.getSelectedItem();
			if (selectionOpCrossoverSelector.getSelectedIndex() < 0) {
				showErrorMessage("You must select a selection operator for crossover!");
				return;
			}
			algorithmSettings.setSelectionCrossoverOp(selectedSelectionCrossoverOp);

			String selectedSelectionSurvivalOp = (String) selectorOpForSurvivalSelector.getSelectedItem();
			if (selectorOpForSurvivalSelector.getSelectedIndex() < 0) {
				showErrorMessage("You must select a selection operator for survival!");
				return;
			}
			algorithmSettings.setSelectionSurvivalOp(selectedSelectionSurvivalOp);

			try {
				Double mutationProbability = Double.parseDouble(mutationProbabilityTextField.getText());
				if (mutationProbability < 0 || mutationProbability > 1) {
					showErrorMessage("Invalid mutation probability: " + mutationProbabilityTextField.getText());
					return;
				} else {
					algorithmSettings.setMutationProbability(mutationProbability);
				}
			} catch (NumberFormatException ex) {
				showErrorMessage("Invalid mutation probability: " + mutationProbabilityTextField.getText());
				return;
			}

			try {
				algorithm = new GPAlgorithm(algorithmSettings);
			} catch (InvalidInputException exception) {
				showErrorMessage(exception.getMessage());
				return;
			}
			
			algorithm.addAlgorithmListener(this);
			UserStopCondition.start();
			new Thread(algorithm).start();
			currentGeneration = 0;
			generationLabel.setText("Generation: " + currentGeneration);
			initializePlotWindows();
		}
		if (e.getSource() == stopBtn) {
			UserStopCondition.stop();
		}
		if (e.getSource() == resetButton) {
			if (algorithm != null) {
				algorithm.reset();
			}
			currentGeneration = 0;
			generationLabel.setText("Generation: 0");
		}
		if (e.getSource() == iterationsRadio) {
			iterationsTxt.setEnabled(true);
			iterationsTxt.setEditable(true);
		}
		if (e.getSource() == noIterationsRadio) {
			iterationsTxt.setEnabled(false);
			iterationsTxt.setEditable(false);
		}

	}

	private void initializePlotWindows() {

		/*if (concentrationsPlotWindow != null) {
			concentrationsPlotWindow.dispose();
		}
		concentrationsPlotWindow = new ConcentrationsPlotWindow();
		concentrationsPlotWindow.setAlgorithm(algorithm);
		concentrationsPlotWindow.setTargetEvolution(InputReader.readTargetBehavior(algorithmSettings.getTargetBehaviorFile()));
		concentrationsPlotWindow.setSpecies(InputReader.readPossibleCombinations(algorithmSettings.getPossibleCombinationsFile())
				.getSpecies());
		System.out.println("window start");
		concentrationsPlotWindow.start(); */
		
		if (fitnessPlotWindow != null) {
			fitnessPlotWindow.dispose();
		}
		fitnessPlotWindow = new FitnessPlotWindow();
		fitnessPlotWindow.setAlgorithm(algorithm);
		algorithm.addAlgorithmListener(fitnessPlotWindow);
		fitnessPlotWindow.setVisible(true);
	}

	private void showErrorMessage(String text) {
		JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void chooseFile(final int whichFile) {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(this);
		File selectedFile = fileChooser.getSelectedFile();
		if (selectedFile != null) {
			if (whichFile == 1) {
				targetBehaviorTxt.setText(fileChooser.getSelectedFile().getAbsolutePath());
			} else if(whichFile == 2) {
				possibleCombinationsTxt.setText(fileChooser.getSelectedFile().getAbsolutePath());
			} else {
				outputFileTxt.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}

	private JLabel getMutationProbabilityLabel() {
		if (mutationProbabilityLabel == null) {
			mutationProbabilityLabel = new JLabel();
			mutationProbabilityLabel.setText("Mutation probability:");
			mutationProbabilityLabel.setBounds(13, 318, 125, 16);
			mutationProbabilityLabel.setFont(new java.awt.Font("Segoe UI", 2, 12));
		}
		return mutationProbabilityLabel;
	}

	private JTextField getMutationProbabilityTextField() {
		if (mutationProbabilityTextField == null) {
			mutationProbabilityTextField = new JTextField();
			mutationProbabilityTextField.setBounds(138, 315, 56, 23);
			mutationProbabilityTextField.setText("0.3");
		}
		return mutationProbabilityTextField;
	}

	private JLabel getGenerationLabel() {
		if (generationLabel == null) {
			generationLabel = new JLabel();
			generationLabel.setText("Generation: 0");
			generationLabel.setBounds(95, 412, 111, 16);
		}
		return generationLabel;
	}

	private JButton getResetButton() {
		if (resetButton == null) {
			resetButton = new JButton();
			resetButton.setText("Reset");
			resetButton.setBounds(320, 409, 75, 23);
			resetButton.addActionListener(this);
		}
		return resetButton;
	}

	@Override
	public void newGenerationCreatedNotification() {
		currentGeneration++;
		generationLabel.setText("Generation: " + currentGeneration);
	}
	
	private JLabel getJLabel11() {
		if(jLabel11 == null) {
			jLabel11 = new JLabel();
			jLabel11.setText("SBML Output File:");
			jLabel11.setBounds(13, 149, 182, 16);
			jLabel11.setFont(new java.awt.Font("Segoe UI",2,12));
		}
		return jLabel11;
	}
	
	private JTextField getOutputFile() {
		if(outputFileTxt == null) {
			outputFileTxt = new JTextField(FILEPATH_OUTPUT);
			outputFileTxt.setBounds(13, 172, 182, 22);
		}
		return outputFileTxt;
	}
	
	private JButton getBrowseOutputBtn() {
		if(browseOutputBtn == null) {
			browseOutputBtn = new JButton();
			browseOutputBtn.setText("Browse");
			browseOutputBtn.setBounds(217, 172, 81, 22);
			browseOutputBtn.addActionListener(this);
		}
		return browseOutputBtn;
	}
}
