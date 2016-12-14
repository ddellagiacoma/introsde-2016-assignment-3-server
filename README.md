# introsde-2016-assignment-3-server

This server is called by the client: **https://github.com/DavideLissoni/introsde-2016-assignment-3-client**

## IMPLEMENTATION
The server is divided in the following packages and classes:

* introsde.document.dao
  * LifeCoachDao.java
* introsde.document.endpoint
  * PeoplePublisher.java
* introsde.document.model
  * HealthMeasureHistory.java
  * HealthProfile.java
  * MeasureDefinition.java
  * Person.java
* introsde.document.ws
  * People.java
  * PeopleImpl.java
  
The model supported by the service includes:
```java
public class Person {
 int idPerson;
 String firstname;
 String lastname;
 Date birthdate
 List<HealthProfile> healthprofile;
}
public class HealthProfile {
 int idMeasure,
 int idMeasureDef;
 int idPerson;
 String value;
 Person person;
}
public class HealthMeasureHistory {
 int idMeasureHistory,
 int idPerson;
 int idMeasureDef
 String value;
 Date timestamp;
 MeasureDefinition measureDefinition
 Person person;
}
public class MeasureDefinition {
 int idMeasureDef,
 String measureName;
 String measureType;
}
```

Moreover, the server implements the following operations:
* **Method #1:** readPersonList() => List\<Person>
* **Method #2:** readPerson(Long personId) => Person
* **Method #3:** updatePerson(Person p) => Person
* **Method #4:** createPerson(Person p) => Person
* **Method #5:** deletePerson(Long personId) => 0 if succeed, otherwise -1
* **Method #6:** readPersonHistory(Long personId, String measureType) => List\<HealthMeasureHistory>
* **Method #7:** readMeasureTypes() => List\<MeasureDefinition>
* **Method #8:** readPersonMeasure(Long personId, String measureType, Long mid) => HealthMeasureHistory
* **Method #9:** savePersonMeasure(Long personId, HealthProfile hp) => HealthProfile
* **Method #10:** updatePersonMeasure(Long personId, HealthProfile hp) => HealthProfile

## DEPLOYMENT

The server was deployed on Heroku in the following way (inside the root folder of the project):

```sh
$ git init

$ git add .

$ git commit -m "first commit"

$ heroku create --stack cedar --buildpack https://github.com/DanieleDellagiacoma/heroku-buildpack-ant

$ git push heroku master
```
