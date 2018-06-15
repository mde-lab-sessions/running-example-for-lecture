package de.upb.mbse.taxcalculationexample.reporting

import java.text.SimpleDateFormat
import java.util.Date
import reporting.GENDER
import reporting.Recipient
import reporting.Report

class ReportGenerator {
	private val Report r

	new(Report r) {
		this.r = r
	}

	def generate() {
		'''
			#«r.header»
			
			«handleSalutation»,
			
			«generateMainContent»
			
			«handleFarewell»
			
			---------------------------------------------------
			`«r.footer»`
		'''
	}

	def handleSalutation() {
		'''«IF r.recipient.gender == GENDER.FEMALE»
		Sehr geehrte Frau«ELSEIF r.recipient.gender == GENDER.MALE»
		Sehr geehrter Herr«ELSE»
		Guten Tag,«ENDIF»«handleTitle(r.recipient)»«r.recipient.name» «r.recipient.familyName»'''
	}

	def handleTitle(Recipient recipient) {
		''' «IF !recipient.title.empty»
		«recipient.title» «ENDIF»'''
	}

	def generateMainContent() {
		'''
			«FOR event : r.events»
				- «event.description» am «handleDate(event.date)»
			«ENDFOR»
		'''
	}

	def handleDate(Date date) {
		val ft = new SimpleDateFormat("d.MM.yyyy zzz");
		'''«ft.format(date)»'''
	}

	def handleFarewell() {
		'''
			Mit freundlichen Grüßen
						
			«r.footer»
		'''
	}
}
