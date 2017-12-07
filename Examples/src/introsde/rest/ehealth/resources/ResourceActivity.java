package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Activity;

import java.io.IOException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Stateless
@LocalBean//Will map the resource to the URL /ehealth/v2
@Path("/activity")
public class ResourceActivity {
	// Allows to insert contextual objects into the class,
		// e.g. ServletContext, Request, Response, UriInfo
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
	    private static int count;
		// THIS IS NOT WORKING
		@PersistenceUnit(unitName="assign21")
		EntityManager entityManager;
		
		@POST
		//@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON ,Me})
		//@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
		@Path("{name}")
	public Activity CreateActivity(@PathParam("name") String name) throws IOException {
		Activity a= new Activity();
		List<Activity> at = Activity.getAll();

		if(at==null){
			a.setName(name);
			a.setIdActivity(1);
			count=1;
			return Activity.saveActivity(a);
		}else{
		count = at.size();
		a.setName(name);
        a.setIdActivity(count +1);
        return Activity.saveActivity(a);
		}
	}
	
}
