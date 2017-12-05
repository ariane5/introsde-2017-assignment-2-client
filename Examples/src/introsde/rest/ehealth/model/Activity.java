package introsde.rest.ehealth.model;

//import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import introsde.rest.ehealth.dao.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@Entity
@Table(name="\"activity\"")
@NamedQueries({
	@NamedQuery(name="Activity.findByName",
			query="SELECT m FROM Activity m "
				+ "WHERE m.name = :name"),
	@NamedQuery(name="Activity.findAll", query="SELECT m FROM Activity m")	
})
@XmlRootElement(name="activity_type")
@XmlType(propOrder={"name"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Activity implements Serializable{
private static final long serialVersionUID = 1L;

        @XmlTransient
	@Id
	@GeneratedValue(generator="sqlite_activity")
	@TableGenerator(name="sqlite_activity", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="activity")
	@Column(name="\"idActivity\"")
	private int idActivity;

        public int getIdActivity() {
			return idActivity;
		}

		public void setIdActivity(int idActivity) {
			this.idActivity = idActivity;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	@XmlValue
	@Column(name="\"name\"")
	private String name;
        
    
		
		
		// database operations
		/**
		 * get Activity by Activity Type
		 * @param activity_type
		 * @return
		 */
		
	public static Activity getActivityByActivtyType(String name) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			//Activity a
			List<Activity> list= em.createNamedQuery("Activity.findByName",Activity.class).setParameter("name",name).getResultList();
			Activity a =list.get(0);
			//Activity p = em.find(Activity.class, activity_type);
			LifeCoachDao.instance.closeConnections(em);
			return a;
		}
		public static Activity getActivityById(int activityId) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			Activity p = em.find(Activity.class, activityId);
			LifeCoachDao.instance.closeConnections(em);
			return p;
		}
		
		/**
		 * get the whole Activity database
		 * @return
		 */
		public static List<Activity> getAll() {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
		        List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
		        LifeCoachDao.instance.closeConnections(em);
		        return list;
		}
		/**
		 * get Activity by name 
		 * @param measureType
		 * @return Activity
		 */
		/*public static Activity getActivityByMN(String measureType) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
		    Activity m = em.createNamedQuery("Activity.findByMesureName", Activity.class)
		    		.setParameter("measureType",measureType).getSingleResult();
		    LifeCoachDao.instance.closeConnections(em);
		    return m;
		}*/
		
		/**
		 * save new Activity
		 * @param p
		 * @return
		 */
		public static Activity saveActivity(Activity p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(p);
			tx.commit();
		        LifeCoachDao.instance.closeConnections(em);
		        return p;
		}
		
		/**
		 * update Activity
		 * @param p
		 * @return
		 */
		public static Activity updateActivity(Activity p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			p=em.merge(p);
			tx.commit();
		        LifeCoachDao.instance.closeConnections(em);
		        return p;
		}
		
		/**
		 * delete Activity
		 * @param p
		 */
		public static void removeActivity(Activity p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
		    p=em.merge(p);
		    em.remove(p);
		    tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
		}
}
