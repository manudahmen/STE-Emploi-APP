package steemploi.service.events;

import java.util.ArrayList;

public class EvenementAttendee
{
	private int id;
	private Attendee attendee;
	private AttendeeRole role;
	private AttendeeType type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Attendee getAttendee() {
		return attendee;
	}
	public void setAttendee(Attendee attendee) {
		this.attendee = attendee;
	}
	public AttendeeRole getRole() {
		return role;
	}
	public void setRole(AttendeeRole role) {
		this.role = role;
	}
	public AttendeeType getType() {
		return type;
	}
	public void setType(AttendeeType type) {
		this.type = type;
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
		EvenementAttendee other = (EvenementAttendee) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
