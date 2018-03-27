package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class Chapter2StructuralSemantics_2 extends StructuralSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/zweiBankenMitKunden.xmi", true));
	}

	@Test
	public void keineZweiBankenMitKunden() {
		assertTrue("The constraint should be violated", api.nichtMehrAlsEineBankMitKunden().hasMatches());
	}
}
