package introsde.document.model;

import introsde.document.dao.LifeCoachDao;
import introsde.document.model.MeasureDefinition;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.persistence.Query;

@Entity
@Table(name = "HealthProfile")
@NamedQuery(name = "HealthProfile.findAll", query = "SELECT h FROM HealthProfile h")
@XmlRootElement(name="Measure")
public class HealthProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_healthprofile")
	@TableGenerator(name="sqlite_healthprofile", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthProfile")
	@Column(name = "idMeasure")
	private int idMeasure;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "idMeasureDef", insertable = false, updatable = false)
	private int idMeasureDef;
	
	@Column(name = "idPerson", insertable = false, updatable = false)
	private int idPerson;
	
	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public HealthProfile() {
	}

	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	public static HealthProfile getLifeStatusById(long healthprofileId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		HealthProfile p = em.find(HealthProfile.class, (int)healthprofileId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<HealthProfile> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<HealthProfile> list = em.createNamedQuery("HealthProfile.findAll", HealthProfile.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static HealthProfile saveLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static HealthProfile updateLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeLifeStatus(HealthProfile p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
		
	public static HealthProfile getValueHealthProfile(long idPerson, Integer idMeasureDef){
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		
		Query query=em.createQuery("SELECT h FROM HealthProfile h WHERE h.idMeasureDef=:idMeasureDef AND h.idPerson=:idPerson", HealthProfile.class);
		query.setParameter("idMeasureDef", idMeasureDef);
		query.setParameter("idPerson", (int) idPerson);

		HealthProfile result=(HealthProfile) query.getSingleResult();
		System.out.println("QUERY RESULT"+result.getIdMeasure());
		LifeCoachDao.instance.closeConnections(em);

		return result;
	}
}
