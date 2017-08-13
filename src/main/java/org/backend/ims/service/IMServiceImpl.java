package org.backend.ims.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.backend.ims.model.Incidents;
import org.springframework.stereotype.Service;

@Service("incidentService")
public class IMServiceImpl implements IMService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Incidents> incidents;

	static {
		incidents = populateDummyUsers();
	}

	@Override
	public Incidents findById(long id) {
		for (Incidents inc : incidents) {
			if (inc.getId() == id) {
				return inc;
			}
		}
		return null;
	}

	// @Override
	// public Incidents findByIncident(String incident) {
	// for(Incidents inc : incidents){
	// if(inc.getName().equalsIgnoreCase(incident)){
	// return inc;
	// }
	// }
	// return null;
	// }

	@Override
	public void saveIncident(Incidents incident) {
		incident.setId(counter.incrementAndGet());
		incidents.add(incident);

	}

	@Override
	public void updateIncident(Incidents incident) {
		int index = incidents.indexOf(incident);
		incidents.set(index, incident);

	}

	@Override
	public void deleteIncidentById(long id) {

		for (Iterator<Incidents> iterator = incidents.iterator(); iterator.hasNext();) {
			Incidents inc = iterator.next();
			if (inc.getId() == id) {
				iterator.remove();
			}
		}

	}

	@Override
	public List<Incidents> findAllIncidents() {
		// TODO Auto-generated method stub
		return incidents;
	}

	@Override
	public void deleteAllIncidents() {
		incidents.clear();

	}

	// @Override
	// public boolean isIncidentExist(Incidents incident) {
	// return findByName(incident.getName())!=null;
	// }

	private static List<Incidents> populateDummyUsers() {
		List<Incidents> incident = new ArrayList<Incidents>();
		incident.add(new Incidents(counter.incrementAndGet(), "Add on", "This is a first incident"));
		incident.add(new Incidents(counter.incrementAndGet(), "Activation", "This is a second incident"));
		incident.add(new Incidents(counter.incrementAndGet(), "Barred", "This is a third incident"));
		return incident;
	}

}
