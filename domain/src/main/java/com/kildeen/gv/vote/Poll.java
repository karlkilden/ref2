package com.kildeen.gv.vote;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.NotEmpty;

import com.kildeen.ref.BaseEntity;

@Entity
//@SequenceGenerator(name = BaseEntity.GVSEQ, sequenceName = "poll_seq")
public class Poll extends BaseEntity {

	@NotEmpty
	@NotNull
	private String question;
	private String explain;
	private String link;
	
	private boolean meta;

	private List<Poll> tags;
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getExplain() {
		return explain;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Poll> getTags() {
		return tags;
	}

	public void setTags(List<Poll> tags) {
		this.tags = tags;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public boolean isMeta() {
		return meta;
	}

	public void setMeta(boolean meta) {
		this.meta = meta;
	}


}
