package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import org.emoflon.ibex.gt.democles.runtime.DemoclesGTEngine;
import org.junit.Before;

import businessrules.impl.BusinessrulesPackageImpl;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.StructuralsemanticsAPI;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.StructuralsemanticsApp;

abstract public class StructuralSemanticsTest extends StructuralsemanticsApp {

	protected StructuralsemanticsAPI api;
	protected static final String INSTANCES = "de.upb.mbse.taxcalculationexample.businessrules/instances";
	
	@Before
	public void setup() throws Exception {
		BusinessrulesPackageImpl.init();
		setWorkspacePath("../");

		DemoclesGTEngine engine = new DemoclesGTEngine();
		resourceSet = engine.createAndPrepareResourceSet(workspacePath.get());
		
		loadModels();
		
		api = initAPI(engine);
	}

	abstract protected void loadModels();
}
