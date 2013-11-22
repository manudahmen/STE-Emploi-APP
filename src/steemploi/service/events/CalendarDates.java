// Not used
package steemploi.service.events;

import java.util.Calendar;

public class CalendarDates
{
	private int id;
	private boolean plage = false;
	private Calendar date1;
	private Calendar date2;
	private String name;
	private CalendarDates parent;
	private int parentId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public boolean isPlage() {
		return plage;
	}
	public void setPlage(boolean plage) {
		this.plage = plage;
	}
	public Calendar getDate1() {
		return date1;
	}
	public void setDate1(Calendar date1) {
		this.date1 = date1;
	}
	public Calendar getDate2() {
		return date2;
	}
	public void setDate2(Calendar date2) {
		this.date2 = date2;
	}
	
	
	public CalendarDates getParent() {
		return parent;
	}
	public void setParent(CalendarDates parent) {
		this.parent = parent;
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
		CalendarDates other = (CalendarDates) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}