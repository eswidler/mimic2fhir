package de.uzl.itcr.mimic2fhir;

import org.apache.log4j.BasicConfigurator;

import de.uzl.itcr.mimic2fhir.work.Config;

public class Main {
	public static void main( String[] args ) {
		//Add server and config data..
		Config configObj = new Config();
//		BasicConfigurator.configure();
		
		//Postgres
		configObj.setPassPostgres("postgres");
		configObj.setPortPostgres("5432");
		configObj.setUserPostgres("postgres");
		configObj.setPostgresServer("localhost");
		configObj.setDbnamePostgres("mimic");
		configObj.setSchemaPostgres("mimiciii");
		
		//Fhir
		configObj.setFhirServer("http://yourfhirserver.com/public/base/");
		configObj.setFhirxmlFilePath("/media/eric/602f6bd7-7d18-4182-82e4-3c9b0b6e2304/mimic2fhir_06052019");
		
		Mimic2Fhir app = new Mimic2Fhir();
		app.setConfig(configObj);
		app.setOutputMode(OutputMode.PRINT_JSON_FILE);
//		app.setPatientOffset(200);
//		app.setTopPatients(10);
		app.start();
	}
}
