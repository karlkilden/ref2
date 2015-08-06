package com.kildeen.gv.poll;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.bval.constraints.NotEmpty;
import org.apache.solr.client.solrj.beans.Field;

import com.kildeen.gv.vote.Poll;

@XmlRootElement
public class PollDTO {

	public static final String ID_TYPE = "poll";

	public static final String NAME = "name";
	public static final String QUESTION = "question";
	public static final String EXPLAIN = "explain";
	public static final String LINK = "link";
	public static final String TAGS = "tags";
	public static final String META = "meta";
	public static final String ID = "id";

	private static final String DATABASE_ID = "databaseId";

	@Field(DATABASE_ID)
	private long databaseId;

	@Field(value = ID)
	private String id;

	@NotEmpty
	@NotNull
	@Field(value = NAME)
	private String name;
	@NotEmpty
	@NotNull
	@Field(value = QUESTION)
	private String question;
	@Field(value = EXPLAIN)
	private String explain;
	@Field(value = LINK)
	private String link;
	@Field(value = META)
	private boolean meta;
	@Field(value = TAGS)
	private List<Poll> tags;

	public static PollDTO get(Object poll) {
		return new PollDTO((Poll) poll);
	}

	public PollDTO() {

	}

	public PollDTO(Poll poll) {
		this.databaseId = poll.getId();
		this.id = ID_TYPE + String.valueOf(databaseId);
		this.question = poll.getQuestion();
		this.explain = poll.getExplain();
		this.name = poll.getName();
		this.link = poll.getLink();
		this.meta = poll.isMeta();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isMeta() {
		return meta;
	}

	public void setMeta(boolean meta) {
		this.meta = meta;
	}

	public List<Poll> getTags() {
		return tags;
	}

	public void setTags(List<Poll> tags) {
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(long databaseId) {
		this.databaseId = databaseId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(databaseId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PollDTO other = (PollDTO) obj;
		if (databaseId != other.databaseId)
			return false;
		return true;
	}

}
