package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

public class Chapter2StructuralSemantics_1 extends StructuralSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/kundeMitZweiUmsaetzen.xmi", true));
	}

	@Test
	public void patternMatching() {
		assertTrue("There shoud be two matches", api.kundeMitMindestensZweiUmsaetzen().countMatches() == 2);
	}

	@Test
	public void jedesModellMussEineBankHaben() {
		assertTrue("There must be at least one bank", api.jedesModellMussEineBankHaben().findAnyMatch().isPresent());
		api.jedesModellMussEineBankHaben().findAnyMatch().ifPresent(m -> EcoreUtil.delete(m.getBank()));
		assertFalse("There must be at least one bank", api.jedesModellMussEineBankHaben().findAnyMatch().isPresent());
	}
}
