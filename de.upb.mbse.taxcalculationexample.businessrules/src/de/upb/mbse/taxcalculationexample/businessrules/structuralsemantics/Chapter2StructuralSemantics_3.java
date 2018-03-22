package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Before;
import org.junit.Test;

import businessrules.impl.BusinessrulesPackageImpl;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI;

public class Chapter2StructuralSemantics_3
		extends GTApp<DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI> {

	protected DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI api;

	protected void registerUserMetamodels(ResourceSet rs) {
		BusinessrulesPackageImpl.init();
	}

	protected void loadModels(ResourceSet rs) {
		rs.getResource(URI.createPlatformResourceURI(//
				"de.upb.mbse.taxcalculationexample.businessrules/instances/zweiBankenMitKundenAberEineBankIstKunde1.xmi", true), true);
	}

	@Before
	public void setup() throws Exception {
		api = getAPI(DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI.class);
	}

	@Test
	public void keineZweiBankenMitKunden() {
		api.nichtMehrAlsEineBankMitKunden().findAnyMatch().ifPresent(m -> {
			System.out.println(m.getB1());
			System.out.println(m.getB2());
			System.out.println(m.getK1());
			System.out.println(m.getK2());
		});
		
		//FIXME[Patrick]:  the match is non-injective as B2 and K2 are mapped to the same bank
		assertFalse("The constraint is not violated", api.nichtMehrAlsEineBankMitKunden().findAnyMatch().isPresent());
	}
	
	@Test
	public void keineZweiBankenMitKundenComplete() {
		assertTrue("The constraint is now violated", 
				api.nichtMehrAlsEineBankMitKunden().findAnyMatch().isPresent() ||
				api.nichtMehrAlsEineBankMitKundenEineBankIstKunde().findAnyMatch().isPresent());
	}
}
