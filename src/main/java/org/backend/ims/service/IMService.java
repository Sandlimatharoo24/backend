package org.backend.ims.service;

import java.util.List;

import org.backend.ims.model.Incidents;

public interface IMService {

	Incidents findById(long id);

	// Incidents findByIncident(String incident);

	void saveIncident(Incidents incident);

	void updateIncident(Incidents incident);

	void deleteIncidentById(long id);

	List<Incidents> findAllIncidents();

	void deleteAllIncidents();

	// boolean isIncidentExist(Incidents incident);

}
