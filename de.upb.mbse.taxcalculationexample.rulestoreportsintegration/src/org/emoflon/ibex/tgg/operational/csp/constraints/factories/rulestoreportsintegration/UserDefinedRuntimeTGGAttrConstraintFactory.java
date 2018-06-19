package org.emoflon.ibex.tgg.operational.csp.constraints.factories.rulestoreportsintegration;

import java.util.HashMap;
import java.util.HashSet;			

import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;			

import org.emoflon.ibex.tgg.operational.csp.constraints.custom.rulestoreportsintegration.UserDefined_eq_date;

public class UserDefinedRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {

	public UserDefinedRuntimeTGGAttrConstraintFactory() {
		super();
	}
	
	@Override
	protected void initialize() {
		creators = new HashMap<>();
		creators.put("eq_date", () -> new UserDefined_eq_date());

		constraints = new HashSet<String>();
		constraints.addAll(creators.keySet());
	}
}
