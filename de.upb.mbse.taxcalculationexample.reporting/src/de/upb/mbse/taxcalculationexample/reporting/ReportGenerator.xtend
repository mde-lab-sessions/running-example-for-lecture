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

	def generateReports(){
		r.recipients.map[rec | generate(rec)]
	}

	private def generate(Recipient rec) {
		'''
			#«r.header»
			
			«handleSalutation(rec)»,
			
			«generateMainContent»
			
			«handleFarewell»
			
			---------------------------------------------------
			`«r.footer»`
		'''
	}

	private def handleSalutation(Recipient rec) {
		'''«IF rec.gender == GENDER.FEMALE»
		Sehr geehrte Frau«ELSEIF rec.gender == GENDER.MALE»
		Sehr geehrter Herr«ELSE»
		Guten Tag,«ENDIF»«handleTitle(rec)»«rec.name» «rec.familyName»'''
	}

	private def handleTitle(Recipient recipient) {
		''' «IF !recipient.title.empty»
		«recipient.title» «ENDIF»'''
	}

	private def generateMainContent() {
		'''
			«FOR event : r.events»
				- «event.description» am «handleDate(event.date)»
			«ENDFOR»
		'''
	}

	private def handleDate(Date date) {
		val ft = new SimpleDateFormat("d.MM.yyyy zzz");
		'''«ft.format(date)»'''
	}

	private def handleFarewell() {
		'''
			Mit freundlichen Grüßen
						
			«r.footer»
		'''
	}
}
