package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Before;
import org.junit.Test;

import businessrules.impl.BusinessrulesPackageImpl;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI;

public class Chapter2StructuralSemantics_2
		extends GTApp<DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI> {

	private DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI api;

	protected void registerUserMetamodels(ResourceSet rs) {
		BusinessrulesPackageImpl.init();
	}

	protected void loadModels(ResourceSet rs) {
		rs.getResource(URI.createPlatformResourceURI(//
				"de.upb.mbse.taxcalculationexample.businessrules/instances/zweiBankenMitKunden.xmi", true), true);
	}

	@Before
	public void setup() throws Exception {
		api = getAPI(DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI.class);
	}

	@Test
	public void keineZweiBankenMitKunden() {
		assertTrue("The constraint should be violated", api.nichtMehrAlsEineBankMitKunden().findAnyMatch().isPresent());
	}
}
