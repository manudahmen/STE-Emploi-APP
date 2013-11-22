package steemploi.service.events;

import java.util.ArrayList;

public class Evenement
{
	private int id;
	private ArrayList<EvenementAttendee> personnes;
	private String name;
	private String description;
	private ArrayList<CalendarDates> dates;
	private Object sub; ///-> db: table evt_subtype_
	private String tableName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<EvenementAttendee> getPersonnes() {
		return personnes;
	}
	public void setPersonnes(ArrayList<EvenementAttendee> personnes) {
		this.personnes = personnes;
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
	public ArrayList<CalendarDates> getDates() {
		return dates;
	}
	public void setDates(ArrayList<CalendarDates> dates) {
		this.dates = dates;
	}
	public Object getSub() {
		return sub;
	}
	public void setSub(Object subclass) {
		this.sub = subclass;
	}

	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Evenement other = (Evenement) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}