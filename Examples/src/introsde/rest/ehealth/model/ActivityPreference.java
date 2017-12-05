package introsde.rest.ehealth.model;
import introsde.rest.ehealth.dao.*;
//import introsde.rest.ehealth.dao.LifeCoachDao;
import introsde.rest.ehealth.model.*;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.XmlAccessType;


@Entity
@Table(name = "\"activitypreference\"")
//@NamedQuery(name = "ActivityPreference.findAll", query = "SELECT l FROM ActivityPreference l")
@NamedQueries({
	@NamedQuery(name = "ActivityPreference.findAll", query = "SELECT l FROM ActivityPreference l"),
	@NamedQuery(name="ActivityPreference.findByPersonIdAndActivity",
			query="SELECT p FROM ActivityPreference p "
				+ "WHERE p.person = :pe AND p.activity= :at"),
	@NamedQuery(name="ActivityPreference.findByPersonActivityAndActivityId",
			query="SELECT a FROM ActivityPreference a "
				+ "WHERE a.person.idPerson = :id AND a.idActivityPreference= :activity_id AND a.activity.name = :activity_type")
				//@NamedQuery(name="ActivityPreference.findAll", query="SELECT h FROM ActivityPreference h")
	})
@XmlRootElement(name="activitypreference")
public class ActivityPreference implements Serializable {
       private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(generator="sqlite_activitypreference")
	@TableGenerator(name="sqlite_activitypreference", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="activitypreference")
	@Column(name = "\"idActivityPreference\"")
	private int idActivityPreference;

        
    
        @Column(name="\"name\"")
	private String name;
   

       @Column(name = "\"description\"")
	private String description;


        public int getIdActivityPreference() {
		return idActivityPreference;
	}

	public void setIdActivityPreference(int idActivityPreference) {
		this.idActivityPreference = idActivityPreference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
         @XmlTransient
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
        @XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

		@Column(name = "\"place\"")
	private String place;

        @Column(name = "\"startdate\"")
	private String startdate;
	
	//@OneToMany(mappedBy="activitypreference",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "\"idActivity\"", referencedColumnName = "\"idActivity\"",insertable = true, updatable = true)
	private Activity activity;
	
	/*@ManyToOne
	@JoinColumns({
		})
	private Person person;*/
	

	@ManyToOne
	@JoinColumn(name="\"idPerson\"",referencedColumnName="\"idPerson\"")
	private Person person;
	
	/*@ManyToOne
	@JoinColumn(name="\"idPerson\"",referencedColumnName="\"idPerson\"")
	private Person person;*/

	public ActivityPreference() {
	}
	
	public static List<ActivityPreference> getActivityByIdAndActivityType(int id,String activity_type) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		//ActivityPreference p = em.find("ActivityPreference.findByPersonIdAndActivity", id)
		Person pe =Person.getPersonById(id);
		Activity at =Activity.getActivityByActivtyType(activity_type);
		List<ActivityPreference> sr = em.createNamedQuery("ActivityPreference.findByPersonIdAndActivity",
	        		ActivityPreference.class)
	        	.setParameter("person", pe)
	        	.setParameter("activity", at).getResultList();
	        	
		LifeCoachDao.instance.closeConnections(em);
		return sr;
	}
	

	public static ActivityPreference getActivityById_ActivityType_ActivityId(int id,String activity_type,int activity_id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		//ActivityPreference p = em.find("ActivityPreference.findByPersonIdAndActivity", id)
		Person p =Person.getPersonById(id);
		Activity a =Activity.getActivityByActivtyType(activity_type);
		ActivityPreference sr = em.createNamedQuery("ActivityPreference.findByPersonIdAndActivity",
	        		ActivityPreference.class)
	        	.setParameter("person", p)
	        	.setParameter("activity", a)
	        	.setParameter("idActivityPreference",activity_id).getSingleResult();
		LifeCoachDao.instance.closeConnections(em);
		return sr;
	}
	
	/**
	 * get the the whole ActivityPreference database
	 * @return
	 */
	public static List<ActivityPreference> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	        List<ActivityPreference> list = em.createNamedQuery("ActivityPreference.findAll", ActivityPreference.class).getResultList();
	        LifeCoachDao.instance.closeConnections(em);
	        return list;
	}
	
	/**
	 * save ActivityPreference
	 * @param p
	 * @return
	 */
	public static ActivityPreference saveActivityPreference(ActivityPreference p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	        return p;
	}
	
        public static ActivityPreference saveActivity(ActivityPreference ac,int id ,String activity_type) {
		Activity a = Activity.getActivityByActivtyType(activity_type);
		Person p= Person.getPersonById(id);
		ac.setActivity(a);
		ac.setPerson(p);
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(ac);
		tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	        return ac;
	}
	/**
	 * update ActivityPreference
	 * @param p
	 * @return
	 */
	public static ActivityPreference updateActivityPreference(ActivityPreference p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	        return p;
	}
	
	/**
	 * delete activitypreference
	 * @param p
	 */
	public static void removeActivityPreference(ActivityPreference p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	        p=em.merge(p);
	        em.remove(p);
	        tx.commit();
	        LifeCoachDao.instance.closeConnections(em);
	}
}
