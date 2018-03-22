package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import static org.junit.Assert.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Before;
import org.junit.Test;

import businessrules.impl.BusinessrulesPackageImpl;
import de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics.api.DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI;

public class Chapter2StructuralSemantics_5
		extends GTApp<DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI> {

	protected DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI api;

	protected void registerUserMetamodels(ResourceSet rs) {
		BusinessrulesPackageImpl.init();
	}

	protected void loadModels(ResourceSet rs) {
		rs.getResource(URI.createPlatformResourceURI(//
				"de.upb.mbse.taxcalculationexample.businessrules/instances/ungueltigeDepotsUndUmsaetze.xmi", true), true);
	}

	@Before
	public void setup() throws Exception {
		api = getAPI(DeupbmbsetaxcalculationexamplebusinessrulesstructuralsemanticsAPI.class);
	}
	
	@Test
	public void ungueltigeDepotsUndUmsaetze() {
		assertFalse("Constraint is violated",
				api.premise()
				   .findMatches()
				   .stream()
				   .allMatch(m_p ->api.conclusion()
						   			  .bind(m_p)
						   			  .findAnyMatch()
						   			  .isPresent()));
	}
	
	@Test
	public void ungueltigeDepotsUndUmsaetzeNonCommutative() {
		assertTrue("Constraint is (erroneously) not violated",
				api.premise()
				   .findMatches()
				   .stream()
				   .allMatch(m_p ->api.conclusion()
						   			  .findAnyMatch()
						   			  .isPresent()));
	}
}
