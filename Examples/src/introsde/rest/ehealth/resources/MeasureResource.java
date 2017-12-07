package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.*;


import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless
@LocalBean//allows to get all the measure type present into the databaseS
//activity_types
@Path("/activity_types")
public class MeasureResource {
	
		@Context
		UriInfo uriInfo;
		@Context
		Request request;

		// THIS IS NOT WORKING
		@PersistenceUnit(unitName="assign21")
		EntityManager entityManager;
		
		// THIS IS NOT WORKING
	    @PersistenceContext(unitName = "assign21",type=PersistenceContextType.TRANSACTION)
	    private EntityManagerFactory entityManagerFactory;

		// Return the list of people to the user in the browser
		@GET
		@Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
		public List<Activity> getMeasureTypeBrowser() {
			System.out.println("Getting list of measurename...");
		    List<Activity> m1 = Activity.getAll();   
		    return m1;
		    
		}

}
