package de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics;

import org.junit.Before;

import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.OperationalsemanticsAPI;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.OperationalsemanticsDemoclesApp;

abstract public class OperationalSemanticsTest extends OperationalsemanticsDemoclesApp {

	protected OperationalsemanticsAPI api;
	protected static final String INSTANCES = "de.upb.mbse.taxcalculationexample.businessrules/instances";

	@Before
	public void setup() throws Exception {
		loadModels();
		api = initAPI();
	}

	abstract protected void loadModels();
}
