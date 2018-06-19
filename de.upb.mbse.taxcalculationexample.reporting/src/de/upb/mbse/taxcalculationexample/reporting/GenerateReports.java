package de.upb.mbse.taxcalculationexample.reporting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

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
		generate(job);
		
		System.out.println("Completed report generation.");
	}

	public static void generate(ReportingJob job) {
		File output = new File("./instances/reports" + LocalDateTime.now().toString().replace(":", "_"));
		output.mkdir();

		job.getReports().forEach(report -> {
			List<CharSequence> contents = new ReportGenerator(report).generateReports();
			for (int i = 0; i < contents.size(); i++) {
				try (PrintWriter out = new PrintWriter(output.getPath() + "/report" + job.getReports().indexOf(report) + "_" + i + ".md")) {
					out.println(contents.get(i));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
