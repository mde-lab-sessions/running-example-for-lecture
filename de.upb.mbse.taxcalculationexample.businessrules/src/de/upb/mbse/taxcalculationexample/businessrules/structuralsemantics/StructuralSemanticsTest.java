package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import org.junit.Before;

import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.StructuralsemanticsAPI;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.StructuralsemanticsDemoclesApp;

abstract public class StructuralSemanticsTest extends StructuralsemanticsDemoclesApp {

	protected StructuralsemanticsAPI api;
	protected static final String INSTANCES = "de.upb.mbse.taxcalculationexample.businessrules/instances";

	@Before
	public void setup() throws Exception {
		loadModels();
		api = initAPI();
	}

	abstract protected void loadModels();
}
