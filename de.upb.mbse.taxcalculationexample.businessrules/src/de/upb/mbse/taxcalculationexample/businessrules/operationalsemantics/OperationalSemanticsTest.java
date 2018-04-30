package de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics;

import org.emoflon.ibex.gt.democles.runtime.DemoclesGTEngine;
import org.junit.Before;

import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.OperationalsemanticsAPI;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.OperationalsemanticsApp;

abstract public class OperationalSemanticsTest extends OperationalsemanticsApp {

	protected OperationalsemanticsAPI api;
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
