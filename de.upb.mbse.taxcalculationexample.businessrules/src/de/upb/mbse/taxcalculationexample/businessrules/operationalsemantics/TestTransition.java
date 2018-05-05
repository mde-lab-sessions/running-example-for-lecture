package de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.emoflon.ibex.common.operational.IMatch;
import org.junit.Test;

import businessrules.Aus;
import businessrules.Berechnungslauf;
import businessrules.Fehler;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.matches.CleanUpMatch;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.matches.InitMatch;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.rules.AusfuehrenRule;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.rules.GewinnSteuerinlaenderRule;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.rules.InitRule;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.rules.TerminateErrorRule;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.rules.TerminateSuccessRule;
import de.upb.mbse.taxcalculationexample.businessrules.operationalsemantics.api.rules.VerlustSteuerinlaenderRule;

public class TestTransition extends OperationalSemanticsTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/opsemantics/kundeMitZweiUmsaetzen.xmi", true));
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/opsemantics/regelmenge2.xmi", true));
	}

	@Test
	public void testSubscribeRuleApp1() {
		Collection<IMatch> matches = new ArrayList<>();
		Collection<IMatch> ruleApplications = new ArrayList<>();

		InitRule init = api.init();

		init.subscribeRuleApplications(m -> ruleApplications.add(m.toIMatch()));
		init.subscribeAppearing(m -> matches.add(m.toIMatch()));

		assertTrue(matches.isEmpty());
		assertTrue(ruleApplications.isEmpty());

		api.updateMatches();

		assertEquals(1, matches.size());
		assertEquals(0, ruleApplications.size());

		Optional<InitMatch> m = init.apply();

		assertTrue(m.isPresent());
		assertEquals(1, matches.size());
		assertEquals(1, ruleApplications.size());
	}

	@Test
	public void testSubscribeRuleApp2() {
		Collection<IMatch> matches = new ArrayList<>();
		Collection<IMatch> ruleApplications = new ArrayList<>();

		InitRule init = api.init();
		init.subscribeRuleApplications(m -> ruleApplications.add(m.toIMatch()));
		init.subscribeAppearing(m -> matches.add(m.toIMatch()));

		init.enableAutoApply();

		assertTrue(matches.isEmpty());
		assertTrue(ruleApplications.isEmpty());

		api.updateMatches();

		assertEquals(1, matches.size());
		assertEquals(1, ruleApplications.size());
	}

	@Test
	public void testOperationalSemantics1() {
		Collection<IMatch> matches = new ArrayList<>();
		Collection<IMatch> ruleApplications = new ArrayList<>();

		InitRule init = api.init();
		init.subscribeRuleApplications(m -> ruleApplications.add(m.toIMatch()));
		init.subscribeAppearing(m -> matches.add(m.toIMatch()));

		GewinnSteuerinlaenderRule gs = api.gewinnSteuerinlaender();
		gs.subscribeRuleApplications(m -> ruleApplications.add(m.toIMatch()));
		gs.subscribeAppearing(m -> matches.add(m.toIMatch()));

		init.enableAutoApply();
		gs.enableAutoApply();

		assertTrue(matches.isEmpty());
		assertTrue(ruleApplications.isEmpty());

		api.updateMatches();

		assertEquals(2, matches.size());
		assertEquals(2, ruleApplications.size());
	}

	@Test
	public void testOperationalSemantics2() {
		// Rules
		InitRule init = api.init();
		GewinnSteuerinlaenderRule gsi = api.gewinnSteuerinlaender();
		TerminateErrorRule error = api.terminateError();
		TerminateSuccessRule terminate = api.terminateSuccess();
		AusfuehrenRule trans = api.ausfuehren();

		// Derivation
		while(init.isApplicable()) init.apply();
		while(gsi.isApplicable()) gsi.apply();
		while(trans.isApplicable()) trans.apply();
		while(terminate.isApplicable()) terminate.apply();
		while(error.isApplicable()) error.apply();
		
		// Assert result
		Optional<Berechnungslauf> result = api.berechnungsLauf().findAnyMatch().map(l -> l.getLl());
		assertTrue(result.isPresent() && result.get().getZustand() instanceof Fehler);
	}
	
	@Test
	public void testOperationalSemantics3() {
		// Rules
		InitRule init = api.init();
		GewinnSteuerinlaenderRule gsi = api.gewinnSteuerinlaender();
		VerlustSteuerinlaenderRule vsi = api.verlustSteuerinlaender();
		TerminateErrorRule terminateError = api.terminateError();
		TerminateSuccessRule terminateSuccess = api.terminateSuccess();
		AusfuehrenRule trans = api.ausfuehren();

		// Init
		while(init.isApplicable()) init.apply();
		
		// Mark all applications
		while(gsi.isApplicable()) gsi.apply();
		while(vsi.isApplicable()) vsi.apply();
		
		// Apply
		while(trans.isApplicable()) trans.apply();
		
		// Terminate
		while(terminateSuccess.isApplicable()) terminateSuccess.apply();
		while(terminateError.isApplicable()) terminateError.apply();
		
		// Assert result
		Optional<Berechnungslauf> result = api.berechnungsLauf().findAnyMatch().map(l -> l.getLl());
		assertTrue(result.isPresent() && result.get().getZustand() instanceof Aus);
	}
	
	@Test
	public void testCleanUpSPO_DPO(){
		api.init().apply();

		api.setDPO();
		Optional<CleanUpMatch> result = api.cleanUp().apply();
		assertFalse(result.isPresent());
		
		api.setSPO();
		result = api.cleanUp().apply();
		assertTrue(result.isPresent());
	}
}
