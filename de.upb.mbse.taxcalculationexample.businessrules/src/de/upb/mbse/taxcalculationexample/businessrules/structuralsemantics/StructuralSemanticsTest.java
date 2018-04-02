package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import org.emoflon.ibex.gt.democles.runtime.DemoclesGTEngine;
import org.junit.Before;

import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.StructuralsemanticsAPI;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.StructuralsemanticsApp;

abstract public class StructuralSemanticsTest extends StructuralsemanticsApp {

	protected StructuralsemanticsAPI api;
	protected static final String INSTANCES = "de.upb.mbse.taxcalculationexample.businessrules/instances";

	@Before
	public void setup() throws Exception {
		DemoclesGTEngine engine = new DemoclesGTEngine();
		resourceSet = engine.createAndPrepareResourceSet(workspacePath);

		loadModels();
		api = initAPI(engine);
	}

	abstract protected void loadModels();
}
