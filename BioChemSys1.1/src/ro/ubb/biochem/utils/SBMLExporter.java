package ro.ubb.biochem.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import ro.ubb.biochem.program.elements.Program;
import ro.ubb.biochem.species.components.Specie;
import ro.ubb.biochem.species.components.SpeciePool;
import ro.ubb.biochem.temp.OutputWriter;
import ro.ubb.biochem.utils.export.Apply;
import ro.ubb.biochem.utils.export.KineticLaw;
import ro.ubb.biochem.utils.export.Math;
import ro.ubb.biochem.utils.export.Model;
import ro.ubb.biochem.utils.export.Parameter;
import ro.ubb.biochem.utils.export.Reaction;
import ro.ubb.biochem.utils.export.Species;
import ro.ubb.biochem.utils.export.SpeciesReference;
import ro.ubb.biochem.utils.export.SysBioMarkLang;


public class SBMLExporter {
	
	public static File exportProgramToSMBL(SpeciePool speciePool, Program program, String exportFileName){
		 File exportFile = null;
		
		try {
			final SysBioMarkLang smbl = new SysBioMarkLang();
			populateSMBL(smbl, speciePool, program);
			final JAXBContext jaxbContext = JAXBContext.newInstance(Apply.class,
					KineticLaw.class, Math.class, Model.class, Parameter.class, Reaction.class, 
					Species.class, SpeciesReference.class, SysBioMarkLang.class);
			StringWriter writer = new StringWriter();
	        jaxbContext.createMarshaller().marshal(smbl, writer);
	        OutputWriter.println(writer.toString());
	        
	        try{
		        exportFile = new File(exportFileName);
		        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile));
		        bw.write(writer.toString());
		        bw.flush();
		        bw.close();
		        
	        }catch (Exception e) {
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return exportFile;
		
	}

	private static void populateSMBL(SysBioMarkLang smbl, SpeciePool species, Program program) {
		smbl.setModel(new Model());
		smbl.getModel().setId("GPGeneratedModel");
			
		Species[] listOfSpecies = new Species[species.getSpecies().size()];
		smbl.getModel().setListOfSpecies(listOfSpecies);
		for(Specie s: species.getSpecies()){
			int index = species.getSpecies().indexOf(s);
			Species newSpecies = new Species();
			smbl.getModel().getListOfSpecies()[index] = newSpecies;
			smbl.getModel().getListOfSpecies()[index].setId(s.toString());
			smbl.getModel().getListOfSpecies()[index].setInitialAmount(species.getSpecieConcentration(s).toString());
		}

		Parameter[] listOfParameters = new Parameter[program.getReactionNo()];
		smbl.getModel().setListOfParameters(listOfParameters);
		
		Reaction[] listofReactions = new Reaction[program.getReactionNo()];
		smbl.getModel().setListOfReactions(listofReactions);
		
		for(ro.ubb.biochem.reaction.components.Reaction r: program.getReactions()){
			int index = program.getReactions().indexOf(r);
			
			Parameter newParameter = new Parameter();
			smbl.getModel().getListOfParameters()[index] = newParameter;
			smbl.getModel().getListOfParameters()[index].setId("k"+index);
			smbl.getModel().getListOfParameters()[index].setValue(r.getKineticRate().toString());
			
			Reaction newReaction = new Reaction();
			smbl.getModel().getListOfReactions()[index] = newReaction;
			smbl.getModel().getListOfReactions()[index].setId("r"+index);
			
			SpeciesReference[] listOfReactants = new SpeciesReference[r.getPattern().getLhs().size()];
			smbl.getModel().getListOfReactions()[index].setListOfReactants(listOfReactants);
			
			for(Specie reactant: r.getPattern().getLhs()){
				int rIndex = r.getPattern().getLhs().indexOf(reactant);
				smbl.getModel().getListOfReactions()[index].getListOfReactants()[rIndex] = new SpeciesReference();
				smbl.getModel().getListOfReactions()[index].getListOfReactants()[rIndex].setSpecies(reactant.toString());
			}
			
			SpeciesReference[] listOfProducts = new SpeciesReference[r.getPattern().getRhs().size()];
			smbl.getModel().getListOfReactions()[index].setListOfProducts(listOfProducts);
			for(Specie product: r.getPattern().getRhs()){
				int pIndex = r.getPattern().getRhs().indexOf(product);
				smbl.getModel().getListOfReactions()[index].getListOfProducts()[pIndex] = new SpeciesReference();
				smbl.getModel().getListOfReactions()[index].getListOfProducts()[pIndex].setSpecies(product.toString());
			}

			smbl.getModel().getListOfReactions()[index].setKineticLaw(new KineticLaw());
			smbl.getModel().getListOfReactions()[index].getKineticLaw().setMath(new Math());
			smbl.getModel().getListOfReactions()[index].getKineticLaw().getMath().setApply(new Apply());
			
			String[] ci = new String[1 + r.getPattern().getLhs().size()];
			smbl.getModel().getListOfReactions()[index].getKineticLaw().getMath().getApply().setCi(ci);
			smbl.getModel().getListOfReactions()[index].getKineticLaw().getMath().getApply().getCi()[0] = "k"+index;
			for(Specie reactant: r.getPattern().getLhs()){
				int rIndex = r.getPattern().getLhs().indexOf(reactant) + 1;
				smbl.getModel().getListOfReactions()[index].getKineticLaw().getMath().getApply().
							getCi()[rIndex] = reactant.toString();
			}
			
		}
		
		
		
		
	}

	
	
}
