package de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class TestInit extends OperationalSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/gueltigeDepotsUndUmsaetze.xmi", true));
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/rules/regelmenge1.xmi", true));
	}

	@Test
	public void testInitPossible() throws IOException {
		// There should be exactly one match
		assertTrue(api.init().countMatches() == 1);
		
		// Apply init, should be successful
		assertTrue(api.init().apply().isPresent());
		
		// NAC should block second application
		assertFalse(api.init().apply().isPresent());
	}
}
