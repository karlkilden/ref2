package com.kildeen.gv.vote;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import com.kildeen.gv.auth.User;
import com.kildeen.ref.BaseEntity;

@Entity
// @SequenceGenerator(name = BaseEntity.GVSEQ, sequenceName = "vote_seq")
public class Vote extends BaseEntity {

	private static final int VOTE_NO_POINTS = -1;
	private static final int _VOTE_YES_POINTS = 1;
	private Poll poll;
	@ManyToOne
	private User user;

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		
		this.poll = poll;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AnswerType getAnswer() {
		return answer;
	}

	public void setAnswer(AnswerType answer) {
		this.answer = answer;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private AnswerType answer;

	private long points;

	@PrePersist
	@PreUpdate
	private void addPoints() {
		switch (answer) {
		case YES:
			points = _VOTE_YES_POINTS;
			break;
		case NO:
			points = VOTE_NO_POINTS;
			break;
		default:
			break;
		}
	}


}