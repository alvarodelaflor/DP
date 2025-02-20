
package domain;

import org.hibernate.validator.constraints.URL;
import org.joda.time.LocalDate;

public class EducationalRecord extends DomainEntity {

	private String	diploma, awardedBy, link, comments;
	private LocalDate	start, end;


	public String getDiploma() {
		return this.diploma;
	}

	public void setDiploma(final String diploma) {
		this.diploma = diploma;
	}

	public String getAwardedBy() {
		return this.awardedBy;
	}

	public void setAwardedBy(final String awardedBy) {
		this.awardedBy = awardedBy;
	}

	@URL
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public LocalDate getStart() {
		return this.start;
	}

	public void setStart(final LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(final LocalDate end) {
		this.end = end;
	}
}
