package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Stateless
@LocalBean
public class ValueOfType {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	//EntityManager entityManager;
	EntityManager entityManager = LifeCoachDao.instance.createEntityManager();
	int id;
	String activity_type ;
	public ValueOfType(UriInfo uriInfo, Request request,int id,String activity_type) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.activity_type=activity_type;
		this.id = id;
	}

	public ValueOfType(UriInfo uriInfo, Request request,int id, EntityManager em,String activity_type) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.activity_type=activity_type;
		this.entityManager = em;
	}
	
	// allows to get the history of a personId and a given measure TypeS
	
	@GET
	@Produces({ MediaType.APPLICATION_XML,MediaType.TEXT_XML })
	public List<Activity_Type> getHistory() {

	    Person person = Person.getPersonById(this.id);
	    System.out.println("success 3");
	    Activity  md = Activity.getActivityByActivtyType(this.activity_type) ;
	    System.out.println("success 1");
	    if (person == null||md == null) {
	        	System.out.println("[Error] Get: Person with id " + this.id + " not found");
	            throw new RuntimeException("Get: Person with " + id + " and measure Name "+activity_type+"not found");
	        }
	    System.out.println("success 2");
	    System.out.println("Returning person with id :="+"" + person.getIdPerson()+""+ "and measuretype  := "+"" + md.getName() +""+""+md.getIdActivity() );
	    return Activity_Type.getHistoryByPersonAndMeasureType(person, md);
	    
	 }
	
	
	// allows to insert a new history
	 
	@POST
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	public ActivityPreference saveHistory(ActivityPreference h ) {
		List<ActivityPreference> ac = ActivityPreference.getAll();
		int count = ac.size();
	     h.setIdActivityPreference(count+1);
		ActivityPreference hm = ActivityPreference.saveActivity(h, this.id , this.activity_type);
	        
	     return hm;
	     }
	
	
	
	// Defines that the next path parameter after the base url is
	// treated as a parameter and passed to the PersonResources
	// Allows to type http://localhost:599/base_url/1
	// 1 will be treaded as parameter todo and passed to ValueOfTypeAndMid
	
    @Path("{activity_id}")
	public ValueOfTypeAndMid getPerson(@PathParam("activity_id") int activity_id) {
			
		return new ValueOfTypeAndMid(uriInfo, request, id,activity_type,activity_id);
	}
	
		
			
}
