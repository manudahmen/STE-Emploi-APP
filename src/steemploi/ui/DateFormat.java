/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package steemploi.ui;

import java.util.Calendar;
import java.util.Locale;

/**
 * 
 * @author ubuntuuser
 */

public class DateFormat {

	String firstDay = "";
	String lastDay = "";
	String[] days;
	private int size;
	private long[] millisA;
	private int min = -1;
	private int max = -1;
	private String courant;
	private Calendar[] dateA;
	private Calendar next;
	private Calendar prev;
	private String[] dateYMD;

	private String dateFormat(Calendar calendar) {
		return calendar.getDisplayName(Calendar.DAY_OF_WEEK, 1, Locale
				.getDefault())
				+ " "
				+ calendar.get(Calendar.DAY_OF_MONTH)
				+ " "
				+ calendar.getDisplayName(Calendar.MONTH, 1, Locale
						.getDefault()) + " " + calendar.get(Calendar.YEAR);
	}

	public DateFormat(long millis, String etendue) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		etendue = "mois";

		if (etendue.equals("mois")) {
			courant = calendar.getDisplayName(Calendar.MONTH, 1, Locale
					.getDefault())
					+ " " + calendar.get(Calendar.YEAR);
		}

		if (etendue.equals("semaine")) {
			size = 7;
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			prev = Calendar.getInstance();
			prev.setTimeInMillis(calendar.getTimeInMillis());
			prev.add(Calendar.DATE, -7);
			next = Calendar.getInstance();
			next.setTimeInMillis(calendar.getTimeInMillis());
			next.add(Calendar.DATE, 7);

		} else if (etendue.equals("mois")) {
			prev = Calendar.getInstance();
			prev.setTimeInMillis(calendar.getTimeInMillis());
			prev.add(Calendar.MONTH, -1);
			next = Calendar.getInstance();
			next.setTimeInMillis(calendar.getTimeInMillis());
			next.add(Calendar.MONTH, 1);
			size = 42;
			calendar.set(calendar.get(Calendar.YEAR), calendar
					.get(Calendar.MONTH), 1);

			while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			}
			//calendar.add(Calendar.DAY_OF_YEAR, -1);
		} else if (etendue.equals("annee")) {
			size = 365;
			calendar.set(Calendar.DAY_OF_YEAR, 1);
		}

		firstDay = dateFormat(calendar);
		days = new String[size];
		millisA = new long[size];
		dateA = new Calendar[size];
		dateYMD = new String[size];
		int current = -1;

		for(int i = 0; i < size; i++) {
			days[i] = dateFormat(calendar);
			millisA[i] = calendar.getTimeInMillis();
			dateA[i] = Calendar.getInstance();
			dateA[i].setTimeInMillis(calendar.getTimeInMillis());
			dateYMD[i] = String.format("%tY-%tm-%td", calendar, calendar,
					calendar);

			if (etendue.equals("mois")
					&& current == 0
					&& dateA[i].get(Calendar.DAY_OF_MONTH) == dateA[i]
							.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				max = i;
				current++;
			}

			if (etendue.equals("mois") && current == -1
					&& dateA[i].get(Calendar.DAY_OF_MONTH) == 1) {
				min = i;
				current++;
			}

			calendar.add(Calendar.DAY_OF_YEAR, 1);
		}

		lastDay = days[size - 1];

		if (etendue.equals("semaine")) {
			courant = "Du " + days[0] + " au " + lastDay;
		}
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public String getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public long[] getMillisA() {
		return millisA;
	}

	public void setMillisA(long[] millisA) {
		this.millisA = millisA;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getCourant() {
		return courant;
	}

	public void setCourant(String courant) {
		this.courant = courant;
	}

	public Calendar[] getDateA() {
		return dateA;
	}

	public void setDateA(Calendar[] dateA) {
		this.dateA = dateA;
	}

	public Calendar getNext() {
		return next;
	}

	public void setNext(Calendar next) {
		this.next = next;
	}

	public Calendar getPrev() {
		return prev;
	}

	public void setPrev(Calendar prev) {
		this.prev = prev;
	}

	public String[] getDateYMD() {
		return dateYMD;
	}

	public void setDateYMD(String[] dateYMD) {
		this.dateYMD = dateYMD;
	}

}
