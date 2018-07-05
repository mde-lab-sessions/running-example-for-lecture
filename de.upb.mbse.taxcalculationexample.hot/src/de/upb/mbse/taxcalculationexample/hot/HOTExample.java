package de.upb.mbse.taxcalculationexample.hot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.jupiter.api.Test;

import businessrules.BusinessrulesPackage;
import de.upb.mbse.taxcalculationexample.hot.api.HotAPI;
import de.upb.mbse.taxcalculationexample.hot.api.HotDemoclesApp;
import reporting.ReportingPackage;

public class HOTExample extends HotDemoclesApp {

	public long addMissingAssignments() throws IOException {
		registerMetaModels();

		// Load correspondence metamodel
		Resource corrMM = resourceSet.getResource(URI.createPlatformResourceURI(
				"de.upb.mbse.taxcalculationexample.cheat.rulestoreportsintegration/model/Rulestoreportsintegration.ecore",
				true), true);
		EPackage pack = (EPackage) corrMM.getContents().get(0);
		registerMetaModel(pack);
		resourceSet.getResources().clear();

		// Add all relevant models to the resourceSet
		Resource tgg = loadModel(URI.createFileURI("./instances/Rulestoreportsintegration.tgg.xmi"));

		getModel().getResources().add(BusinessrulesPackage.eINSTANCE.eResource());
		getModel().getResources().add(ReportingPackage.eINSTANCE.eResource());
		getModel().getResources().add(EcorePackage.eINSTANCE.eResource());

		// Initialise and apply rules
		HotAPI api = initAPI();

		long matches = api.addMissingAssignments().countMatches();

		while (api.addMissingAssignments().hasMatches())
			api.addMissingAssignments().apply();

		tgg.setURI(tgg.getURI().trimFileExtension().appendFileExtension("new.xmi"));
		tgg.save(null);

		return matches;
	}

	@Test
	public void doTest() throws IOException {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);

		HOTExample hot = new HOTExample();

		assertEquals(17, hot.addMissingAssignments());
	}
}
