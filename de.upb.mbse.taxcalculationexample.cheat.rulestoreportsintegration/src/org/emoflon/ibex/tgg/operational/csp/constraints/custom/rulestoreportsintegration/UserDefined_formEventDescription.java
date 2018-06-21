package org.emoflon.ibex.tgg.operational.csp.constraints.custom.rulestoreportsintegration;

import java.util.Optional;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class UserDefined_formEventDescription extends RuntimeTGGAttributeConstraint {

	private final String staticText = " Anteilen im Fond ";

	/**
	 * Constraint formEventDescription(v0, v1, v2, v3)
	 * 
	 * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
	 */
	@Override
	public void solve() {
		if (variables.size() != 4)
			throw new RuntimeException("The CSP -FORMEVENTDESCRIPTION- needs exactly 4 variables");

		RuntimeTGGAttributeConstraintVariable v0 = variables.get(0);
		RuntimeTGGAttributeConstraintVariable v1 = variables.get(1);
		RuntimeTGGAttributeConstraintVariable v2 = variables.get(2);
		RuntimeTGGAttributeConstraintVariable v3 = variables.get(3);
		String bindingStates = getBindingStates(v0, v1, v2, v3);

		switch (bindingStates) {
		case "BFFB":
			getSegments(v0, v3).ifPresent(segments -> {
				v1.bindToValue(segments[0]);
				v2.bindToValue(segments[1]);
				setSatisfied(true);
			});
			break;
		case "BFBF":
			v1.bindToValue(generateValue(v1.getType()));
			v3.bindToValue(formDescriptionString());
			setSatisfied(true);
			break;
		case "BFBB":
			getSegments(v0, v3).ifPresent(segments -> {
				v1.bindToValue(segments[0]);
				setSatisfied(v2.getValue().equals(segments[1]));
			});
			break;
		case "BBBF":
			v3.bindToValue(formDescriptionString());
			setSatisfied(true);
			break;
		case "BBBB":
			setSatisfied(v3.getValue().equals(formDescriptionString()));
			break;
		case "BFFF":
			v1.bindToValue(generateValue(v1.getType()));
			v2.bindToValue(generateValue(v2.getType()));
			v3.bindToValue(formDescriptionString());
			setSatisfied(true);
			break;
		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}

	private Optional<String[]> getSegments(RuntimeTGGAttributeConstraintVariable v0,
			RuntimeTGGAttributeConstraintVariable v3) {
		String description = v3.getValue().toString();
		String prefix = v0.getValue().toString();
		String[] segments = description.split(staticText);

		if (segments.length == 2 && segments[0].startsWith(prefix)) {
			segments[0] = segments[0].replace(prefix, "");
			return Optional.of(segments);
		}

		return Optional.empty();
	}

	private String formDescriptionString() {
		return "" + variables.get(0).getValue() + variables.get(1).getValue() + staticText
				+ variables.get(2).getValue();
	}
}
