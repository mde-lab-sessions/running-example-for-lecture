package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertFalse;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class Chapter2StructuralSemantics_4 extends Chapter2StructuralSemantics_3 {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/zweiBankenMitKundenAberEineBankIstKunde2.xmi", true));
	}

	@Test
	@Override
	public void keineZweiBankenMitKundenComplete() {
		assertFalse("The constraint is not violated", api.nichtMehrAlsEineBankMitKunden().hasMatches()
				|| api.nichtMehrAlsEineBankMitKundenEineBankIstKunde().hasMatches());
	}
}
