package de.upb.mbse.taxcalculationsexample.rulestoreportstrafo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import de.upb.mbse.taxcalculationexample.reporting.GenerateReports;
import de.upb.mbse.taxcalculationsexample.rulestoreportstrafo.api.matches.CalculationResultsToReportingJobMatch;
import de.upb.mbse.taxcalculationsexample.rulestoreportstrafo.api.matches.DepotToReportMatch;
import de.upb.mbse.taxcalculationsexample.rulestoreportstrafo.api.rules.ClientGetsReportRule;
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
		api.depotToReport().forEachMatch(m -> {
			Optional<DepotToReportMatch> ocm = api.depotToReport().apply(m);
			ocm.ifPresent(cm -> {
				// Add recipients
				ClientGetsReportRule cr = api.clientGetsReport().bind(cm);
				cr.forEachMatch(rm -> cr.apply(rm));
			});
		});

		// Create events
		api.applicationToEvent().forEachMatch(am -> {
			api.applicationToEvent().apply(am)
					.ifPresent(mm -> mm.getEvent().setDescription(mm.getItem().eClass().getName() + ": "
							+ mm.getA().getAmount() + " Anteilen im Fond " + mm.getF().getName()));
		});

		Resource result = resourceSet.createResource(URI.createPlatformResourceURI(INSTANCES + "/result.xmi", true));

		job.ifPresent(j -> {
			GenerateReports.generate(j);
			result.getContents().add(j);
		});

		result.save(null);
	}
}
