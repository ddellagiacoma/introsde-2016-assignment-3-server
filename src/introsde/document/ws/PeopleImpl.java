package introsde.document.ws;

import introsde.document.model.*;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

//Service Implementation
@WebService(endpointInterface = "introsde.document.ws.People", serviceName = "PeopleService")
public class PeopleImpl implements People {

	@Override
	public List<Person> readPersonList() {
		try {
			List<Person> pl=Person.getAll();
			return pl;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Person readPerson(long id) {
		try {
			System.out.println("---> Reading Person by id = " + id);
			Person p = Person.getPersonById(id);
			if (p != null) {
				System.out.println("---> Found Person by id = " + id + " => " + p.getfirstname());
			} else {
				System.out.println("---> Didn't find any Person with  id = " + id);
			}
			return p;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Person updatePerson(Person person) {
		try {
			Person.updatePerson(person);
			return person;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Person createPerson(Person person) {
		try {
			Person.savePerson(person);
			return person;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int deletePerson(long id) {
		Person p = Person.getPersonById(id);
		if (p != null) {
			Person.removePerson(p);
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public List<HealthMeasureHistory> readPersonHistory(long id, String measureType) {
		try {
			Integer idMeasure = MeasureDefinition.getIdByName(measureType);
			List<HealthMeasureHistory> healthProfile = HealthMeasureHistory.getPeopleHistory(id, idMeasure);
			return healthProfile;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<MeasureDefinition> readMeasureTypes() {
		try {
			System.out.println("Getting list of measures...");
			List<MeasureDefinition> measures = MeasureDefinition.getAll();
			return measures;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public HealthMeasureHistory readPersonMeasure(long id, String measureType, long mid) {
		try {
			Integer idMeasure = MeasureDefinition.getIdByName(measureType);
			HealthMeasureHistory personMeasure = HealthMeasureHistory.getValueMeasureHistory(id, idMeasure, mid);
			return personMeasure;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public HealthProfile savePersonMeasure(long id, HealthProfile hp) {
		try {
			Integer idMeasure = hp.getMeasureDefinition().getIdMeasureDef();

			HealthProfile health = HealthProfile.getValueHealthProfile(id, idMeasure);

			Person p = Person.getPersonById(id);
			MeasureDefinition m = MeasureDefinition.getMeasureDefinitionById(idMeasure);
			String Oldvalue = health.getValue();
			HealthMeasureHistory history = new HealthMeasureHistory();
			history.setPerson(p);
			history.setMeasureDef(m);
			history.setTimestamp(new Date());
			history.setValue(Oldvalue);
			HealthMeasureHistory.saveHealthMeasureHistory(history);
			HealthProfile.removeLifeStatus(health);

			HealthProfile healthProfile = new HealthProfile();
			healthProfile.setValue(hp.getValue());
			healthProfile.setPerson(p);
			healthProfile.setMeasureDefinition(m);
			HealthProfile.saveLifeStatus(healthProfile);
			return healthProfile;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public HealthProfile updatePersonMeasure(long id, HealthProfile hp) {
		try {
			Integer idMeasure = hp.getMeasureDefinition().getIdMeasureDef();
			HealthProfile ls = HealthProfile.getValueHealthProfile(id, idMeasure);
			if (ls.getPerson().getIdPerson() == id) {
				Person p = Person.getPersonById(id);
				hp.setPerson(p);
				hp.setIdMeasure(ls.getIdMeasure());
				HealthProfile.updateLifeStatus(hp);
				return hp;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}