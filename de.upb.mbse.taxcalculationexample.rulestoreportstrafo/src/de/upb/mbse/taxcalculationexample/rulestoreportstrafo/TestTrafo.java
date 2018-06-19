package de.upb.mbse.taxcalculationexample.rulestoreportstrafo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import de.upb.mbse.taxcalculationexample.reporting.GenerateReports;
import de.upb.mbse.taxcalculationexample.rulestoreportstrafo.api.matches.CalculationResultsToReportingJobMatch;
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

		// TODO:  Add remaining transformation

		Resource result = resourceSet.createResource(URI.createPlatformResourceURI(INSTANCES + "/result.xmi", true));

		job.ifPresent(j -> {
			GenerateReports.generate(j);
			result.getContents().add(j);
		});

		result.save(null);
	}
}
