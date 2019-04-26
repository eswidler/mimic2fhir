package de.uzl.itcr.mimic2fhir;

import de.uzl.itcr.mimic2fhir.work.Config;

public class Main {
	public static void main( String[] args ) {
		//Add server and config data..
		Config configObj = new Config();
		
		//Postgres
		configObj.setPassPostgres("postgres");
		configObj.setPortPostgres("5432");
		configObj.setUserPostgres("postgres");
		configObj.setPostgresServer("localhost");
		configObj.setDbnamePostgres("mimic");
		configObj.setSchemaPostgres("mimiciii");
		
		//Fhir
		configObj.setFhirServer("http://yourfhirserver.com/public/base/");
		configObj.setFhirxmlFilePath("/home/eric/Immunity/fhirbasejson");
		
		Mimic2Fhir app = new Mimic2Fhir();
		app.setConfig(configObj);
		app.setOutputMode(OutputMode.PRINT_JSON_FILE);
		app.setPatientOffset(200);
		app.setTopPatients(10);
		app.start();
	}
}
