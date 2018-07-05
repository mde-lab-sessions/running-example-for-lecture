package de.upb.mbse.taxcalculationexample.hot;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;

import reporting.impl.ReportingPackageImpl;
import businessrules.impl.BusinessrulesPackageImpl;
import de.upb.mbse.taxcalculationexample.hot.api.HotAPI;
import de.upb.mbse.taxcalculationexample.hot.api.HotDemoclesApp;

public class HOTExample extends HotDemoclesApp {
	public static void main(String[] args) throws IOException {
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
		
		BusinessrulesPackageImpl.init();
		ReportingPackageImpl.init();
		
		HOTExample hot = new HOTExample();
		hot.loadModel(URI.createFileURI("./instances/Rulestoreportsintegration.tgg.xmi"));
		EcoreUtil.resolveAll(hot.resourceSet);
			
		EcoreUtil.UnresolvedProxyCrossReferencer.find(hot.resourceSet).forEach((eob, settings) -> {
			System.out.println("Problems resolving: " + eob);
		});
		
		HotAPI api = hot.initAPI();
		
		System.out.println("Matches: " + api.addMissingAssignments().countMatches());
		
		System.out.println("Matches: " + api.anyNode().countMatches());
		
		while(api.addMissingAssignments().hasMatches())
			api.addMissingAssignments().apply();
		
		hot.saveResourceSet();
	}
}
