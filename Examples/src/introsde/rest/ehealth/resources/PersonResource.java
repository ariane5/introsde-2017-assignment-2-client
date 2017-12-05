package introsde.rest.ehealth.resources;

import java.util.ArrayList;
import java.util.List;

import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.*;

import javax.persistence.*;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
public class PersonResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	EntityManager entityManager;
	
	int id;
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private EntityTransaction tx;
	public PersonResource(UriInfo uriInfo, Request request,int id, EntityManager em) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.entityManager = em;
	}

	@Path("{activity_type}")
	public ValueOfType getPerson(@PathParam("activity_type") String activity_type) {
		
		return new ValueOfType(uriInfo, request, id,activity_type);
	}
	public PersonResource(UriInfo uriInfo, Request request,int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	
	
	// Application integration
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON ,MediaType.TEXT_XML})
	public Person getPerson() {
		Person person = this.getPersonById(id);
		if (person == null)
			throw new RuntimeException("Get: Person with " + id + " not found");
		return person;
	}

	// for the browser
	/*@GET
	@Produces(MediaType.TEXT_XML)
	public Person getPersonHTML() {
		Person person = this.getPersonById(id);
		if (person == null)
			throw new RuntimeException("Get: Person with " + id + " not found");
		System.out.println("Returning person... " + person.getIdPerson());
		return person;
	}*/

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
   public Person putPerson(Person person1) {

		
		Person p = new Person();
		
		Person person = Person.getPersonById(this.id);
		
		if (person == null) {
	
		  System.out.println("can not find the person");
		  return null;
			
		} else {
		String b=	person.getBirthdate();
		String l= person.getLastname();
		int i= person.getIdPerson();
			/*person.getLastname();
			person.setFirstname("Jean De Dieu");*/
		
			p.setIdPerson(i);
			p.setFirstname(person1.getFirstname());
		    p.setBirthdate(b);
		    p.setLastname(l);
		    Person pq =Person.updatePerson(p);
			return pq;
		}
    }

		
	

	@DELETE
	public void deletePerson() {
		Person c = getPersonById(id);
		if (c == null)
			throw new RuntimeException("Delete: Person with " + id
					+ " not found");

		Person.removePerson(c);
	}

	
	public Person getPersonById(int personId) {
		System.out.println("Reading person from DB with id: "+personId);
		//Person person = entityManager.find(Person.class, personId);
		
		Person person = Person.getPersonById(personId);
		System.out.println("Person: "+person.toString());
		return person;
	}
}
