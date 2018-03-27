package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class Chapter2StructuralSemantics_5 extends StructuralSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/ungueltigeDepotsUndUmsaetze.xmi", true));
	}

	@Test
	public void ungueltigeDepotsUndUmsaetze() {
		assertFalse("Constraint is violated",
				api.premise().findMatches().stream().allMatch(m_p -> api.conclusion().bind(m_p).hasMatches()));
	}

	@Test
	public void ungueltigeDepotsUndUmsaetzeNonCommutative() {
		assertTrue("Constraint is (erroneously) not violated",
				api.premise().findMatches().stream().allMatch(m_p -> api.conclusion().hasMatches()));
	}
}
