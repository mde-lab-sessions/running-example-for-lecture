package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertFalse;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Test;

public class Chapter2StructuralSemantics_4 extends Chapter2StructuralSemantics_3 {

	protected void loadModels(ResourceSet rs) {
		rs.getResource(URI.createPlatformResourceURI(//
				"de.upb.mbse.taxcalculationexample.businessrules/instances/zweiBankenMitKundenAberEineBankIstKunde2.xmi",
				true), true);
	}
	
	@Test
	@Override
	public void keineZweiBankenMitKundenComplete() {
		assertFalse("The constraint is not violated", 
				api.nichtMehrAlsEineBankMitKunden().findAnyMatch().isPresent() ||
				api.nichtMehrAlsEineBankMitKundenEineBankIstKunde().findAnyMatch().isPresent());
	}
}
