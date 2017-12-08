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



/**
 * The persistent class for the "Activity_Type" database table.
 * 
 */
@Entity
@Table(name="\"activity_type\"")
@NamedQueries({
	@NamedQuery(name="Activity_Type.findByPersonAndType",
			query="SELECT h FROM Activity_Type h "
				+ "WHERE h.person = :person AND h.activity = :activity"),
	@NamedQuery(name="Activity_Type.findByPersonTypeAndId",
			query="SELECT h FROM Activity_Type h "
				+ "WHERE h.person = :person AND h.activity = :activity AND h.idMeasureHistory = :activity_id"),
	@NamedQuery(name="Activity_Type.findAll", query="SELECT h FROM Activity_Type h")	
})
@XmlRootElement
public class Activity_Type implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"description\"")
	private String description;

	
        @Id
        @GeneratedValue(generator="sqlite_activity_type")
	@TableGenerator(name="sqlite_activity_type", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="activity_type")
	@Column(name="\"idMeasureHistory\"")
	private int idMeasureHistory;


	@Column(name="\"place\"")
	private String place;

	@Column(name="\"startdate\"")
	private String startdate;

	@ManyToOne
	@JoinColumn(name = "\"idActivity\"", referencedColumnName = "\"idActivity\"")
	private Activity activity;

	@ManyToOne
	@JoinColumn(name ="\"idPerson\"", referencedColumnName = "\"idPerson\"")
	private Person person;
	
	public Activity_Type() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	@XmlElement(name="activity_id")
	public int getIdMeasureHistory() {
		return this.idMeasureHistory;
	}

	public void setIdMeasureHistory(int idMeasureHistory) {
		this.idMeasureHistory = idMeasureHistory;
	}


	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getStartdate() {
		return this.startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	// database operations
	public static Activity_Type getActivity_TypeById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Activity_Type p = em.find(Activity_Type.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
		
	public static List<Activity_Type> getHistoryByPersonAndMeasureType(Person p,Activity md) {
	        EntityManager em = LifeCoachDao.instance.createEntityManager();
	       // MeasureDefinition  md=MeasureDefinition.getMeasureDefinitionByMN(measureType);
	        List<Activity_Type> list = em.createNamedQuery("Activity_Type.findByPersonAndType",
	        		Activity_Type.class)
	        	.setParameter("person", p)
	        	.setParameter("activity", md)
	        	.getResultList();

	        LifeCoachDao.instance.closeConnections(em);

	        return list;
        }
		
		
		/**
		 * get history given person, Measuredefinition and Activity_Type id 
		 * @param p
		 * @param md
		 * @param activity_id
		 * @return
		 */
	public static Activity_Type getHistoryByPersonAndTypeAndactivity_id(Person p,Activity md,int activity_id) {
	        EntityManager em = LifeCoachDao.instance.createEntityManager();
	       // MeasureDefinition  md=MeasureDefinition.getMeasureDefinitionByMN(measureType);
	        
			
	        Activity_Type sr = em.createNamedQuery("Activity_Type.findByPersonTypeAndId",
	        		Activity_Type.class)
	        	.setParameter("person", p)
	        	.setParameter("activity", md)
	        	.setParameter("activity_id",activity_id)
	        	.getSingleResult();

	         LifeCoachDao.instance.closeConnections(em);

	         return sr;
	    }
		
		
	public static Activity_Type saveHistoryByPersonAndTypeAndactivity_id(Activity_Type h,String  activity_type,int activity_id,int id) {
	          EntityManager em = LifeCoachDao.instance.createEntityManager();
	       // MeasureDefinition  md=MeasureDefinition.getMeasureDefinitionByMN(measureType);
	          Activity a = Activity.getActivityByActivtyType(activity_type);
			Person p= Person.getPersonById(id);
			h.setActivity(a);
			h.setPerson(p);
			h.setIdMeasureHistory(activity_id);
	          EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(h);
			tx.commit();

	          LifeCoachDao.instance.closeConnections(em);

	          return h;
		}
		
		
		
		/**
		 * get Activity_Type database
		 * @return
		 */
	//@XmlElementWrapper(name = "activity_types")
	public static List<Activity_Type> getAll() {
		    EntityManager em = LifeCoachDao.instance.createEntityManager();
		    List<Activity_Type> list = em.createNamedQuery("Activity_Type.findAll", Activity_Type.class).getResultList();
		    LifeCoachDao.instance.closeConnections(em);
		    return list;
		}
		/**
		 * save history given an Activity_Type , a person id and a name of measure name
		 * @param h
		 * @param id
		 * @param mt
		 * @return
		 */
	public static Activity_Type	saveHistory(Activity_Type h, int id , String mt){
		    h.setPerson(Person.getPersonById(id));
		   // h.setMeasureDefinition(MeasureDefinition.getMeasureDefinitionByMN(mt));
		    //h.setTimestamp();  // The created date is always the date the measure is being created on
		



	            h.setActivity(Activity.getActivityByActivtyType(mt));
		    EntityManager em = LifeCoachDao.instance.createEntityManager();
		    EntityTransaction tx = em.getTransaction();
		    tx.begin();
		    em.persist(h);
		    tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
		    return h;
			
		}
		
		/**
		 * save an history
		 * @param p
		 * @return
		 */
	public static Activity_Type saveActivity_Type(Activity_Type p) {
		     EntityManager em = LifeCoachDao.instance.createEntityManager();
		     EntityTransaction tx = em.getTransaction();
		     tx.begin();
		     em.persist(p);
		     tx.commit();
		     LifeCoachDao.instance.closeConnections(em);
		    return p;
		}
		
		/**
		 * update  history
		 * @param p
		 * @return
		 */



		public static Activity_Type updateActivity_Type(Activity_Type p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			p=em.merge(p);
			tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
		    return p;
		}
		
		/**
		 * delete history
		 * @param p
		 */
		public static void removeActivity_Type(Activity_Type p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
		    p=em.merge(p);
		    em.remove(p);
		    tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
		}

}