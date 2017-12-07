package introsde.rest.ehealth.resources;


import java.io.IOException;
import java.util.ArrayList;
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
public class ValueOfTypeAndMid {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	//EntityManager entityManager;
	EntityManager entityManager = LifeCoachDao.instance.createEntityManager();
	int id;
	String activity_type ;
	int activity_id;
	public ValueOfTypeAndMid(UriInfo uriInfo, Request request,int id,String activity_type,int activity_id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.activity_type=activity_type;
		this.id = id;
		this.activity_id=activity_id;
	}

	
	public ValueOfTypeAndMid(UriInfo uriInfo, Request request,int id,EntityManager em,String activity_type,int activity_id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.entityManager = em;
		this.activity_type=activity_type;
		this.id = id;
		this.activity_id=activity_id;
	}
	
	
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML })
    public HealthMeasureHistory getHistoryByid() {

        Person person = Person.getPersonById(this.id);
       Activity activity = Activity.getActivityByActivtyType(this.activity_type);
        
        if (person == null||activity == null) {
        	System.out.println("[Error] Get: Person with id " + this.id + " not found");
           throw new RuntimeException("Get: Person with " + id + " and measure Name "+activity+"not found");
        }
        
        //System.out.println("Returning person with id :="+"    " + person.getIdPerson()+" "+ "and measuretype  := "+"  " + at.getName() +""+"with mid:="+" "+at.getIdActivity() );
        //return ActivityPreference .getActivityById_ActivityType_ActivityId(id, activity_type, activity_id);
        return HealthMeasureHistory.getHistoryByPersonAndTypeAndactivity_id(person, activity, this.activity_id);
    }
	
	
	
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.TEXT_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.TEXT_XML })
   public HealthMeasureHistory putActivity(HealthMeasureHistory hmh) {

		int n= HealthMeasureHistory.getAll().size();
		HealthMeasureHistory h = new HealthMeasureHistory();
	    Activity a =	Activity.getActivityByActivtyType(this.activity_type);
	    

	    Person p= Person.getPersonById(this.id);
		HealthMeasureHistory h1=HealthMeasureHistory.getHistoryByPersonAndTypeAndactivity_id(p, a, this.activity_id);
	   

		if (h1== null) {
	
		  System.out.println("can not find the history");
		  return null;
			
		} else {

		    h.setActivity(h1.getActivity());
		    h.setDescription(hmh.getDescription());
		    h.setIdMeasureHistory(h1.getIdMeasureHistory());
		    h.setPerson(h1.getPerson());
		    h.setPlace(hmh.getPlace());
		    h.setStartdate(hmh.getStartdate());
		                           
			h.setIdMeasureHistory(n+1);
		    HealthMeasureHistory pq =HealthMeasureHistory.updateHealthMeasureHistory(h);
			return pq;
		}
    }

	
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.TEXT_XML })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON,MediaType.TEXT_XML })
   public HealthMeasureHistory postActivity(HealthMeasureHistory h) {

		int n= HealthMeasureHistory.getAll().size();
		
	        h.setIdMeasureHistory(n+1);
	        HealthMeasureHistory hm = HealthMeasureHistory.saveHistoryByPersonAndTypeAndactivity_id(h, this.activity_type, this.activity_id, this.id);
	        
	        return hm;
    }
	
	
	
	}
	
    
	

	
