package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class Chapter2StructuralSemantics_3 extends StructuralSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/zweiBankenMitKundenAberEineBankIstKunde1.xmi", true));
	}

	@Test
	public void keineZweiBankenMitKunden() {
		api.nichtMehrAlsEineBankMitKunden().findAnyMatch().ifPresent(m -> {
			System.out.println(m.getB1());
			System.out.println(m.getB2());
			System.out.println(m.getK1());
			System.out.println(m.getK2());
		});

		assertFalse("The constraint is not violated", api.nichtMehrAlsEineBankMitKunden().hasMatches());
	}

	@Test
	public void keineZweiBankenMitKundenComplete() {
		assertTrue("The constraint is now violated", api.nichtMehrAlsEineBankMitKunden().hasMatches()
				|| api.nichtMehrAlsEineBankMitKundenEineBankIstKunde().hasMatches());
	}
}
