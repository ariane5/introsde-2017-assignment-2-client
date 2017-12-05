package introsde.rest.ehealth.model;
import introsde.rest.ehealth.dao.*;
import introsde.rest.ehealth.model.*;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import java.util.Date;
import java.util.List;


@Entity
@Table(name="\"person\"")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement
public class Person implements Serializable {
  private static final long serialVersionUID = 1L;



        @Id
	@GeneratedValue(generator="sqlite_person")
	@TableGenerator(name="sqlite_person", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="Person")
	@Column(name="\"idPerson\"")
	private int idPerson;

	@Column(name="\"firstname\"")
	private String firstname;

	@Column(name="\"lastname\"")
	private String lastname;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="\"birthdate\"")
	private String birthdate;
	
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<ActivityPreference> activitypreferences;
        // mappedBy must be equal to the name of the attribute in ActivityPreference that maps this relation
	//@OneToMany(mappedBy="person" ),cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public int getIdPerson() {
		return idPerson;
	}


	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

        
        @XmlElementWrapper(name = "preferences")
	@XmlElement(name = "activity")
	public List<ActivityPreference> getActivitypreferences() {
		return activitypreferences;
	}


	public void setActivitypreferences(List<ActivityPreference> activitypreferences) {
		this.activitypreferences = activitypreferences;
	}


    
	public Person() {
	}
	public Person(String fname,String lname,String bdate) {
		this.birthdate=bdate;
		this.firstname=fname;
		this.lastname=lname;
	}
	
	// Database operations
		
	public static Person getPersonById(int personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	/**
	 * @return the whole list  in the DB
	 */
	public static List<Person> getAll() {

		System.out.println("--> Initializing Entity manager...");
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
	        List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
	        LifeCoachDao.instance.closeConnections(em);
	
	        return list;
	}
	
	/**
	 * save a person p
	 * @param p
	 * @return
	 */
	public static Person savePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	        return p;
	}
	
	/**
	 * update a person p
	 * @param p
	 * @return
	 */
	public static Person updatePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	        Person	pq=em.merge(p);
		tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	        return pq;
	}
	
	/**
	 * delete a person p
	 * @param p
	 */
	public static void removePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	        p=em.merge(p);
	        em.remove(p);
	        tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	}

}
