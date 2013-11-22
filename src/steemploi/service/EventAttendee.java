package steemploi.service;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.struts.action.ActionForm;

public class EventAttendee extends ActionForm implements Event {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int id;
		private Calendar date;
		private String name;
		private String description;
		private String title;
		private ArrayList<Attendee> attendees;
		private EventType type;
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Calendar getDate() {
			return date;
		}

		public void setDate(Calendar date) {
			this.date = date;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public ArrayList<Attendee> getAttendees() {
			return attendees;
		}

		public void setAttendees(ArrayList<Attendee> attendees) {
			this.attendees = attendees;
		}

		public EventAttendee() {
			// TODO Auto-generated constructor stub
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public EventType getType() {
			return type;
		}

		public void setType(EventType type) {
			this.type = type;
		}

	}
