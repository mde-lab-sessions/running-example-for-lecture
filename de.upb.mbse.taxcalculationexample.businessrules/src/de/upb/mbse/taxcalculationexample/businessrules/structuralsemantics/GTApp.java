package de.upb.mbse.taxcalculationexample.businessrules.structuralsemantics;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.gt.api.GraphTransformationAPI;
import org.emoflon.ibex.gt.democles.runtime.DemoclesGTEngine;

/**
 * A helper class for creating a graph transformation API via a template method.
 * 
 * @author Anthony Anjorin
 *
 * @param <GTAPI>
 *            The concrete graph transformation API.
 */
public abstract class GTApp<GTAPI extends GraphTransformationAPI> {

	/**
	 * Implement this method to register all metamodels you require for loading your
	 * models. Make sure to remove all resources from your resource set after doing
	 * this. You should only either initialise generated packages, or fill the
	 * package registry of the resource set. If the resource set contains any
	 * resources, the pattern matcher will start searching for matches in all these
	 * resources.
	 * 
	 * @param rs
	 */
	abstract protected void registerUserMetamodels(ResourceSet rs);

	/**
	 * Implement this method to load all models as resources in the resource set.
	 * These models will be searched for matches.
	 * 
	 * @param rs
	 */
	abstract protected void loadModels(ResourceSet rs);

	/**
	 * A default strategy for creating and preparing the resource set. Override if
	 * you know what you're doing, or if you have to obtain an existing resource set
	 * from some other source.
	 * 
	 * @param engine
	 * @return
	 */
	protected ResourceSet createAndPrepareResourceSet(IContextPatternInterpreter engine) {
		String workspacePath = "../";
		ResourceSet rs = engine.createAndPrepareResourceSet(workspacePath);
		registerUserMetamodels(rs);
		return rs;
	}

	/**
	 * Standard workflow for Democles as pattern matcher. Override to use a
	 * different pattern matcher, or to implement a different workflow.
	 * 
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public GTAPI getAPI(Class<GTAPI> cls) throws Exception {
		IContextPatternInterpreter democlesEngine = new DemoclesGTEngine();
		ResourceSet rs = createAndPrepareResourceSet(democlesEngine);
		loadModels(rs);

		return cls.getConstructor(IContextPatternInterpreter.class, ResourceSet.class).newInstance(democlesEngine, rs);
	}
}
