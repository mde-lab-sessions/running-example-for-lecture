package de.upb.mbse.taxcalculationexample.reporting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import reporting.ReportingJob;
import reporting.impl.ReportingPackageImpl;

public class GenerateReports {
	public static void main(String[] args) throws IOException {
		ReportingPackageImpl.init();

		URI jobURI = URI.createFileURI("./instances/ReportingJob.xmi");
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap()//
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		Resource jobResource = rs.createResource(jobURI);
		jobResource.load(null);

		ReportingJob job = (ReportingJob) jobResource.getContents().get(0);

		File output = new File("./instances/reports" + LocalDateTime.now().toString().replace(":", "_"));
		output.mkdir();

		job.getReports().forEach(report -> {
			CharSequence content = new ReportGenerator(report).generate();
			try (PrintWriter out = new PrintWriter(output.getPath() + "/report" + job.getReports().indexOf(report) + ".md")) {
				out.println(content);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		
		System.out.println("Generated " + job.getReports().size() + " reports.");
	}
}
