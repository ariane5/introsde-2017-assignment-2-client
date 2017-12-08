package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.Activity;
import introsde.rest.ehealth.model.ActivityPreference;
import introsde.rest.ehealth.model.Activity_Type;
import introsde.rest.ehealth.model.Person;

import java.io.IOException;
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
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
@Stateless
@LocalBean//allows to get all the measure type present into the databaseS
//activity_types
@Path("/DataBase_init")
public class DataBaseInit {
	public DataBaseInit(){};
	

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
	@Produces({MediaType.TEXT_XML,MediaType.APPLICATION_XML })
	public static List<Person>  fillDataBase() throws IOException{
		DataBaseInit	db = new DataBaseInit();
		
		   db.CreateActivity("sport");
		   db.CreateActivity("social");
		   db.CreateActivity("school");
		   db.CreatePerson("mario", "marco", "17-02-1978");
		   db.CreatePerson("john", "john", "11-12-1988");
		   db.CreatePerson("marc","marc", "18-09-1958");
		   db.CreatePerson("paul", "paul", "19-07-1968");
		   db.CreatePerson("esther", "esther", "20-10-1990");
		   db.CreateActivityPreference("sport", "swimming", "Trento city", "2017-09-13T12:00:00.0", 1);
		   db.CreateActivityPreference("sport", "football", "sanbartolameo", "2017-09-13T19:00:00.0", 1);
		   db.CreateActivityPreference("sport", "swimming", "Trento city", "2017-09-13T12:00:00.0", 2);
		   db.CreateActivityPreference("sport", "football", "sanbartolameo", "2017-09-13T19:00:00.0", 3);
		   db.CreateActivityPreference("social", "discussion", "sanbartolameo", "2017-09-13T20:00:00.0", 4);
		   db.CreateActivityPreference("school", "economics", "povo", "2017-09-13T9:00:00.0", 5);
		   db.CreateHistory("swimming", "Trento city", "2017-09-13T12:00:00.0", "sport", 1);
		   db.CreateHistory("history", "Trento povo", "2017-09-17T09:00:00.0", "school", 2);
		   db.CreateHistory("football", "sanbartolameo", "2017-09-13T19:00:00.0","sport", 3);
		   db.CreateHistory("discussion", "sanbartolameo", "2017-09-13T20:00:00.0","social", 4);
		   db.CreateHistory("economics", "povo", "2017-09-13T9:00:00.0","school", 5);
		   List<Person> people = Person.getAll();
		    return people;
	}
	
	public void CreatePerson(String fname,String lname,String bdate) throws IOException {
		Person p = new Person();
		List<Person> people = Person.getAll();
		int count = people.size();
        p.setFirstname(fname);
		p.setLastname(lname);
		p.setBirthdate(bdate);
    	p.setIdPerson(count +1);
    	System.out.println(count +1);
		 Person.savePerson(p);
	}
	
	public void CreateActivityPreference( String name,String description,String place,String startdate,int idP) throws IOException {
		ActivityPreference ac = new ActivityPreference();
		Person p= Person.getPersonById(idP);
		Activity a = Activity.getActivityByActivtyType(name);
		List<ActivityPreference> acT = ActivityPreference.getAll();
		int count = acT.size();
		ac.setName(name);
		ac.setDescription(description);
		ac.setPerson(p);
		ac.setPlace(place);
		ac.setStartdate(startdate);
		ac.setActivity(a);
        ac.setIdActivityPreference(count +1);
        
    	 
       ActivityPreference.saveActivityPreference(ac);
	}
	
	public void CreateActivity(String name) throws IOException {
		Activity a= new Activity();
		List<Activity> at = Activity.getAll();
		int count = at.size();
		a.setName(name);
        a.setIdActivity(count +1);
         Activity.saveActivity(a);
	}
	
	public void CreateHistory(String d,String p,String s,String name,int idP) throws IOException {
		Activity_Type h= new Activity_Type();
		Person person= Person.getPersonById(idP);
		Activity a = Activity.getActivityByActivtyType(name);
		List<Activity_Type> ht = Activity_Type.getAll();
		int count = ht.size();
		h.setActivity(a);
		h.setDescription(d);
		h.setPlace(p);
		h.setPerson(person);
		h.setStartdate(s);
		
        h.setIdMeasureHistory(count +1);
        Activity_Type.saveActivity_Type(h);
		
	}

}
