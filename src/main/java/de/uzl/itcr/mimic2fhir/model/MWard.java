/***********************************************************************
Copyright 2018 Stefanie Ververs, University of Lübeck

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
/***********************************************************************/
package de.uzl.itcr.mimic2fhir.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.ContactPoint;
import org.hl7.fhir.dstu3.model.Location;
import org.hl7.fhir.dstu3.model.Location.LocationPositionComponent;

import ca.uhn.fhir.model.primitive.IdDt;

/**
 * Represents one ward as location (from transfers)
 * @author Stefanie Ververs
 *
 */
public class MWard {
	private int wardId;
	private String careUnit;
	
	private Location fhirLocation;
	
	public String getCareUnit() {
		return careUnit;
	}
	public void setCareUnit(String careUnit) {
		if(careUnit == null) {
			this.careUnit = "NORMAL";
		}
		else {
			this.careUnit = careUnit;
		}
	}
	
	public int getWardId() {
		return wardId;
	}
	public void setWardId(int wardId) {
		this.wardId = wardId;
	}
	public String getWardName() {
		return "Ward " + wardId + " (" + careUnit + ")";
	}
	
	/**
	 * Create FHIR-"Location"
	 * @param locationInfo 
	 * @return 
	 */
	public void setFhirLocation(Map<String, String> locInfo) {
		Location loc = new Location();
		
		loc.setName(locInfo.get("Name"));
		
		LocationPositionComponent locPos = new LocationPositionComponent();
		locPos.setLatitude(Double.parseDouble(locInfo.get("Lat")));
		locPos.setLongitude(Double.parseDouble(locInfo.get("Lng")));
		loc.setPosition(locPos);
		
		Address locAddress = new Address();
		locAddress.setText(locInfo.get("Address"));
		locAddress.setCity(locInfo.get("City"));
		locAddress.setState(locInfo.get("State"));
		locAddress.setPostalCode(locInfo.get("Zip"));
		locAddress.setCountry(locInfo.get("Country"));
		loc.setAddress(locAddress);
		
//		List<ContactPoint> locTelecom = new ArrayList<ContactPoint>();
//		ContactPoint c = new ContactPoint();
//		loc.setTelecom(locTelecom);
		
		loc.addIdentifier().setSystem("http://www.imi-mimic.de/wards").setValue(locInfo.get("ID"));
		
		CodeableConcept cc = new CodeableConcept();
		switch(careUnit) {
			case "NORMAL":
			case "NWARD":	//Neonatal ward
				cc.addCoding().setCode("HU").setSystem("http://hl7.org/fhir/v3/RoleCode").setDisplay("Hospital unit");
				break;
			case "CCU": //Coronary care unit
				cc.addCoding().setCode("CCU").setSystem("http://hl7.org/fhir/v3/RoleCode").setDisplay("Coronary care unit");
				break;
			case "CSRU": 	//Cardiac surgery recovery unit
			case "MICU": 	//Medical intensive care unit
			case "SICU":	//Surgical intensive care unit
			case "TSICU":	//Trauma/surgical intensive care unit
				cc.addCoding().setCode("ICU").setSystem("http://hl7.org/fhir/v3/RoleCode").setDisplay("Intensive care unit");
				break;
			case "NICU": 	//Neonatal intensive care unit
				cc.addCoding().setCode("PEDNICU").setSystem("http://hl7.org/fhir/v3/RoleCode").setDisplay("Pediatric neonatal intensive care unit");
				break;

		}
		loc.setType(cc);
		
		loc.setId(IdDt.newRandomUuid());
		
		this.fhirLocation = loc;
	}

	
	public Location getFhirLocation() {
		return this.fhirLocation;
	}
}
