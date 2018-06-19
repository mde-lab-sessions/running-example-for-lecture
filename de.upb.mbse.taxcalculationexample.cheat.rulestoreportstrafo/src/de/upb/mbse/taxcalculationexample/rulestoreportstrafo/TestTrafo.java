package de.upb.mbse.taxcalculationexample.rulestoreportstrafo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import businessrules.Depot;
import de.upb.mbse.taxcalculationexample.reporting.GenerateReports;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.matches.ApplicationToEventMatch;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.matches.CalculationResultsToReportingJobMatch;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.matches.DepotToReportMatch;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.rules.ApplicationToEventRule;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.rules.ClientGetsReportRule;
import reporting.Report;
import reporting.ReportingJob;

public class TestTrafo extends TransformationTest {

	@Override
	protected void loadModels() {
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/regelmenge2.xmi", true));
		loadModel(URI.createPlatformResourceURI(INSTANCES + "/kundeMitZweiUmsaetzen.xmi", true));
	}

	@Test
	public void testTrafo() throws IOException {
		// Create the job
		assertTrue(api.calculationResultsToReportingJob().isApplicable());
		Optional<CalculationResultsToReportingJobMatch> match = api.calculationResultsToReportingJob().apply();
		Optional<ReportingJob> job = match.map(m -> m.getJob());
		assertTrue(job.isPresent());

		// Create recipients
		api.clientToRecipient().forEachMatch(m -> api.clientToRecipient().apply(m));

		// Create reports
		Hashtable<Depot, Report> depotToReport = new Hashtable<>();
		api.depotToReport().forEachMatch(m -> {
			Optional<DepotToReportMatch> cm = api.depotToReport().apply(m);
			cm.ifPresent(mm -> depotToReport.put(mm.getDepot(), mm.getRep()));
		});

		// Add recipients
		depotToReport.forEach((depot, report) -> {
			ClientGetsReportRule r = api.clientGetsReport();
			r.bindDepot(depot).bindReport(report).forEachMatch(mmm -> r.apply(mmm));
		});

		// Create events
		depotToReport.forEach((depot, report) -> {
			ApplicationToEventRule r = api.applicationToEvent();
			r.bindDepot(depot).bindReport(report).forEachMatch(m -> {
				Optional<ApplicationToEventMatch> cm = api.applicationToEvent().apply(m);
				
				// Determine description of event
				cm.ifPresent(mm -> mm.getEvent().setDescription(mm.getItem().eClass().getName() + ": "
						+ mm.getA().getAmount() + " Anteilen im Fond " + mm.getF().getName()));
			});
		});

		Resource result = resourceSet.createResource(URI.createPlatformResourceURI(INSTANCES + "/result.xmi", true));

		job.ifPresent(j -> {
			GenerateReports.generate(j);
			result.getContents().add(j);
		});

		result.save(null);
	}
}
