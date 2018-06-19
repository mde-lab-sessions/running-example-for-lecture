package de.upb.mbse.taxcalculationexample.rulestoreportstrafo;

import org.emoflon.ibex.gt.democles.runtime.DemoclesGTEngine;
import org.junit.Before;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.RulestoreportstrafoAPI;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.RulestoreportstrafoDemoclesApp;

abstract public class TransformationTest extends RulestoreportstrafoDemoclesApp {

	protected RulestoreportstrafoAPI api;
	protected static final String INSTANCES = "de.upb.mbse.taxcalculationexample.cheat.rulestoreportstrafo/instances";

	@Before
	public void setup() throws Exception {
		DemoclesGTEngine engine = new DemoclesGTEngine();
		resourceSet = engine.createAndPrepareResourceSet(workspacePath);
		loadModels();
		api = initAPI(engine);
	}
	
	abstract protected void loadModels();
}
