package org.emoflon.ibex.tgg.run.rulestoreportsintegration;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.emoflon.ibex.tgg.runtime.engine.DemoclesTGGEngine;

import businessrules.Bank;
import businessrules.Depot;
import businessrules.Kunde;

public class SYNC_App extends SYNC {

	public SYNC_App() throws IOException {
		super(createIbexOptions());
		registerBlackInterpreter(new DemoclesTGGEngine());
	}

	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);

		SYNC_App sync = new SYNC_App();
		
		logger.info("Starting SYNC");
		long tic = System.currentTimeMillis();
		sync.forward();
		Bank b = (Bank) sync.s.getContents()
				.stream()
				.filter(root -> root instanceof Bank)
				.findAny()
				.get();
		Kunde marge = (Kunde) b.getKunden()
				.stream()
				.filter(client -> client.getFirstName().equals("Marge"))
				.findAny()
				.get();
		Depot d2 = (Depot) b.getAllDepots()
				.stream()
				.filter(d -> d.getNumber() == 2)
				.findAny()
				.get();
		marge.getDepots().add(d2);
		sync.forward();
		long toc = System.currentTimeMillis();
		logger.info("Completed SYNC in: " + (toc - tic) + " ms");
		
		sync.saveModels();
		sync.terminate();
	}
	
	
	@Override
	protected void registerUserMetamodels() throws IOException {
		_RegistrationHelper.registerMetamodels(rs, this);
			
		// Register correspondence metamodel last
		loadAndRegisterCorrMetamodel(options.projectPath() + "/model/" + options.projectName() + ".ecore");
	}
	
	private static IbexOptions createIbexOptions() {
		return _RegistrationHelper.createIbexOptions();
	}
}
