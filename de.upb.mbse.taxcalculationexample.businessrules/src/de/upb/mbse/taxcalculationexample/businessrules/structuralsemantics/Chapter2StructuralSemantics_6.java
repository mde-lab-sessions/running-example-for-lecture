package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

public class Chapter2StructuralSemantics_6 extends StructuralSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/gueltigeDepotsUndUmsaetze.xmi", true));
	}

	@Test
	public void gueltigeDepotsUndUmsaetze() {
		assertTrue("Constraint is not violated", //
				api.premise()//
						.findMatches()//
						.stream()//
						.allMatch(m_p -> api.conclusion()//
								.bind(m_p)//
								.hasMatches()));
	}
}
