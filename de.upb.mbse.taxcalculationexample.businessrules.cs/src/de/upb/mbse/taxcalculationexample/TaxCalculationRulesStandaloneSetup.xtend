/*
 * generated by Xtext 2.12.0
 */
package de.upb.mbse.taxcalculationexample


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class TaxCalculationRulesStandaloneSetup extends TaxCalculationRulesStandaloneSetupGenerated {

	def static void doSetup() {
		new TaxCalculationRulesStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
