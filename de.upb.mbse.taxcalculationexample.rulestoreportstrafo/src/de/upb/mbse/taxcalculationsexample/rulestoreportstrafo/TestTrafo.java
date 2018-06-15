package de.upb.mbse.taxcalculationsexample.rulestoreportstrafo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import de.upb.mbse.taxcalculationexample.reporting.GenerateReports;
import de.upb.mbse.taxcalculationsexample.rulestoreportstrafo.api.matches.ApplicationToEventMatch;
import de.upb.mbse.taxcalculationsexample.rulestoreportstrafo.api.matches.CalculationResultsToReportingJobMatch;
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
		api.createReport().forEachMatch(m -> api.createReport().apply(m));
		
		// Create events
		api.applicationToEvent().forEachMatch(m -> {
			Optional<ApplicationToEventMatch> o = api.applicationToEvent().apply(m);
			o.ifPresent(cm -> cm.getEvent().setDescription(cm.getItem().eClass().getName() + ": " + cm.getA().getAmount() + " Anteilen im Fond " + cm.getF().getName()));
		});

		Resource result = resourceSet.createResource(URI.createPlatformResourceURI(INSTANCES + "/result.xmi", true));

		job.ifPresent(j -> {
			GenerateReports.generate(j);			
			result.getContents().add(j);	
		});
		
		result.save(null);
	}
}
