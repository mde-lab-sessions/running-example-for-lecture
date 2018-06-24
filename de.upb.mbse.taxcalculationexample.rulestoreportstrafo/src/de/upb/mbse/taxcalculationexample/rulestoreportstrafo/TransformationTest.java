package de.upb.mbse.taxcalculationexample.rulestoreportstrafo;

import org.junit.Before;

import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.RulestoreportstrafoAPI;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.RulestoreportstrafoDemoclesApp;

abstract public class TransformationTest extends RulestoreportstrafoDemoclesApp {

	protected RulestoreportstrafoAPI api;
	protected static final String INSTANCES = "de.upb.mbse.taxcalculationexample.rulestoreportstrafo/instances";

	@Before
	public void setup() throws Exception {
		loadModels();
		api = initAPI();
	}
	
	abstract protected void loadModels();
}
