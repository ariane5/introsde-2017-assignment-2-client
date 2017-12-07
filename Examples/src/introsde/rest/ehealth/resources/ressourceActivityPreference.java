
package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Activity;
import introsde.rest.ehealth.model.ActivityPreference;
import introsde.rest.ehealth.model.Person;

import java.io.IOException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
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
@Path("/activitypreference")
public class ressourceActivityPreference {
	@GET
	@Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
	public List<ActivityPreference> getPersonsBrowser() {
		System.out.println("Getting list of people...");
	    List<ActivityPreference> people = ActivityPreference.getAll();
	    System.out.println("I'VE GOT THE LIST OF PEOPLE.");
		return people;
	}

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
			//@Path("{id,idt}")
		public ActivityPreference CreateActivity(ActivityPreference a,@PathParam("idt") int idt,@PathParam("id") int id) throws IOException {
				ActivityPreference ac = new ActivityPreference();		
				List<ActivityPreference> at = ActivityPreference.getAll();

			if(at==null){
				ac.setActivity(Activity.getActivityById(1));
				ac.setDescription("running to roncafort");
				ac.setIdActivityPreference(1);
				ac.setName("sport");
				ac.setPerson(Person.getPersonById(1));
				ac.setPlace("roncafort");
				ac.setStartdate("12-12-2017");
				count=1;
				return ActivityPreference.saveActivityPreference(ac);
			}else{
				Person p= Person.getPersonById(id);
				Activity activity = Activity.getActivityById(idt);
				List<ActivityPreference> acT = ActivityPreference.getAll();
				int count = acT.size();
				ac.setName(a.getName());
				ac.setDescription(a.getDescription());
				ac.setPerson(p);
				ac.setPlace(a.getPlace());
				ac.setStartdate(a.getStartdate());
				ac.setActivity(activity);
		        ac.setIdActivityPreference(count +1);
		        
		    	 
		        return ActivityPreference.saveActivityPreference(ac);
		}
}
}
