package org.backend.ims.controller;

import java.util.List;

import org.backend.ims.model.Incidents;
import org.backend.ims.service.IMServiceImpl;
import org.backend.ims.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class IMController {
	
	public static final Logger logger = LoggerFactory.getLogger(IMController.class);

	@Autowired
    IMServiceImpl imService; //Service which will do all data retrieval/manipulation work

	 // -------------------Retrieve All Incidents---------------------------------------------
	 
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/incident/", method = RequestMethod.GET)
    public ResponseEntity<List<Incidents>> listAllIncidents() {
        List<Incidents> inc = imService.findAllIncidents();
        if (inc.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Incidents>>(inc, HttpStatus.OK);
    }
    
    
    // -------------------Retrieve Single Incident------------------------------------------
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/incident/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching Incident with id {}", id);
        Incidents inc = imService.findById(id);
        if (inc == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Incidents>(inc, HttpStatus.OK);
    }

    // -------------------Create Incident -------------------------------------------
 
    @RequestMapping(value = "/incident/", method = RequestMethod.POST)
    public ResponseEntity<?> createIncident(@RequestBody Incidents incident, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Incident : {}", incident);
 
//        if (imService.isUserExist(user)) {
//            logger.error("Unable to create. A User with name {} already exist", user.getName());
//            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
//            user.getName() + " already exist."),HttpStatus.CONFLICT);
//        }
        imService.saveIncident(incident);;
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/incident/{id}").buildAndExpand(incident.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update Incident ------------------------------------------------
 
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/incident/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateIncident(@PathVariable("id") long id, @RequestBody Incidents incident) {
        logger.info("Updating User with id {}", id);
 
        Incidents currentincident = imService.findById(id);
 
        if (currentincident == null) {
            logger.error("Unable to update. Incident with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. incident with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
 
        currentincident.setType(incident.getType());
        currentincident.setComment(incident.getComment());
      
        imService.updateIncident(currentincident);
        return new ResponseEntity<Incidents>(currentincident, HttpStatus.OK);
    }
 
    // ------------------- Delete a Incident-----------------------------------------
 
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/incident/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteIncident(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Incident with id {}", id);
 
        Incidents incident = imService.findById(id);
        if (incident == null) {
            logger.error("Unable to delete. Incident with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. incident with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        imService.deleteIncidentById(id);
        return new ResponseEntity<Incidents>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Delete All Incidents-----------------------------
 
    @RequestMapping(value = "/incident/", method = RequestMethod.DELETE)
    public ResponseEntity<Incidents> deleteAllIncidents() {
        logger.info("Deleting All Incidents");
 
        imService.deleteAllIncidents();
        return new ResponseEntity<Incidents>(HttpStatus.NO_CONTENT);
    }
 
}
