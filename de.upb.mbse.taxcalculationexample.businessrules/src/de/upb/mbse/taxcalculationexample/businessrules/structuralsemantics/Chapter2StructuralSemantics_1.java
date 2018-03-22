package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import businessrules.impl.BusinessrulesPackageImpl;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI;

public class Chapter2StructuralSemantics_1
		extends GTApp<DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI> {

	private DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI api;

	protected void registerUserMetamodels(ResourceSet rs) {
		BusinessrulesPackageImpl.init();
	}

	protected void loadModels(ResourceSet rs) {
		rs.getResource(URI.createPlatformResourceURI(//
				"de.upb.mbse.taxcalculationexample.businessrules/instances/kundeMitZweiUmsaetzen.xmi", true), true);
	}

	@Before
	public void setup() throws Exception {
		api = getAPI(DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI.class);
	}

	@Test
	public void patternMatching() {
		assertTrue("There shoud be two matches", api.kundeMitMindestensZweiUmsaetzen().findMatches().size() == 2);
	}

	@Test
	public void jedesModellMussEineBankHaben() {
		assertTrue("There must be at least one bank", api.jedesModellMussEineBankHaben().findAnyMatch().isPresent());
		api.jedesModellMussEineBankHaben().findAnyMatch().ifPresent(m -> EcoreUtil.delete(m.getBank()));
		assertFalse("There must be at least one bank", api.jedesModellMussEineBankHaben().findAnyMatch().isPresent());
	}
}
