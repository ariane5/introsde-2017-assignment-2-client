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
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="\"healthmeasurehistory\"")
@NamedQueries({
	@NamedQuery(name="HealthMeasureHistory.findByPersonAndType",
			query="SELECT h FROM HealthMeasureHistory h "
				+ "WHERE h.person = :person AND h.activity = :activity"),
	@NamedQuery(name="HealthMeasureHistory.findByPersonTypeAndId",
			query="SELECT h FROM HealthMeasureHistory h "
				+ "WHERE h.person = :person AND h.activity = :activity AND h.idMeasureHistory = :activity_id"),
	@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h")	
})
@XmlRootElement//(name="activity_type")
public class HealthMeasureHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"description\"")
	private String description;

	
        @Id
        @GeneratedValue(generator="sqlite_healthmeasurehistory")
	@TableGenerator(name="sqlite_healthmeasurehistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="healthmeasurehistory")
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
	
	public HealthMeasureHistory() {
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
	public static HealthMeasureHistory getHealthMeasureHistoryById(int id) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
		
	public static List<HealthMeasureHistory> getHistoryByPersonAndMeasureType(Person p,Activity md) {
	        EntityManager em = LifeCoachDao.instance.createEntityManager();
	       // MeasureDefinition  md=MeasureDefinition.getMeasureDefinitionByMN(measureType);
	        List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findByPersonAndType",
	        		HealthMeasureHistory.class)
	        	.setParameter("person", p)
	        	.setParameter("activity", md)
	        	.getResultList();

	        LifeCoachDao.instance.closeConnections(em);

	        return list;
        }
		
		
		/**
		 * get history given person, Measuredefinition and HealthMeasureHistory id 
		 * @param p
		 * @param md
		 * @param activity_id
		 * @return
		 */
	public static HealthMeasureHistory getHistoryByPersonAndTypeAndactivity_id(Person p,Activity md,int activity_id) {
	        EntityManager em = LifeCoachDao.instance.createEntityManager();
	       // MeasureDefinition  md=MeasureDefinition.getMeasureDefinitionByMN(measureType);
	        
			
	        HealthMeasureHistory sr = em.createNamedQuery("HealthMeasureHistory.findByPersonTypeAndId",
	        		HealthMeasureHistory.class)
	        	.setParameter("person", p)
	        	.setParameter("activity", md)
	        	.setParameter("activity_id",activity_id)
	        	.getSingleResult();

	         LifeCoachDao.instance.closeConnections(em);

	         return sr;
	    }
		
		
	public static HealthMeasureHistory saveHistoryByPersonAndTypeAndactivity_id(HealthMeasureHistory h,String  activity_type,int activity_id,int id) {
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
		 * get healthmeasurehistory database
		 * @return
		 */
	//@XmlElementWrapper(name = "activity_types")
	public static List<HealthMeasureHistory> getAll() {
		    EntityManager em = LifeCoachDao.instance.createEntityManager();
		    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
		    LifeCoachDao.instance.closeConnections(em);
		    return list;
		}
		/**
		 * save history given an healthmeasurehistory , a person id and a name of measure name
		 * @param h
		 * @param id
		 * @param mt
		 * @return
		 */
	public static HealthMeasureHistory	saveHistory(HealthMeasureHistory h, int id , String mt){
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
	public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p) {
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



		public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
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
		public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
		    p=em.merge(p);
		    em.remove(p);
		    tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
		}

}
