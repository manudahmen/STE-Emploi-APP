// Not used
package steemploi.service.events;

public class Attendee
{
	private int id;
	private AttendeeType type;
	/***
	* type==Etudiant  -> steemploi.service.Etudiant
	* type==Formateur -> steemploi.service.Formateur
	* TousLesEtudiants -> null
	* Formateurs -> null
	*/
	private Class<?> classofObject;
	private Object object;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AttendeeType getType() {
		return type;
	}
	public void setType(AttendeeType type) {
		this.type = type;
	}
	public Class<?> getClassofObject() {
		return classofObject;
	}
	public void setClassofObject(Class<?> classofObject) {
		this.classofObject = classofObject;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attendee other = (Attendee) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}