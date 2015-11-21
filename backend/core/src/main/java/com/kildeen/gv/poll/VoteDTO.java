package com.kildeen.gv.poll;

import org.apache.solr.client.solrj.beans.Field;

import com.kildeen.gv.vote.Vote;

public class VoteDTO {

	public static final String POLL_ID = "pollId";
	public static final String USER_ID = "userId";
	public static final String ANSWER = "answer";
	public static final String ID = "id";
	public static final String ID_TYPE = "vote";
	public static final String DATABASE_ID = "databaseId";

	@Field(ID)
	private String id;

	@Field(DATABASE_ID)
	private long databaseId;

	@Field(POLL_ID)
	private long pollId;
	@Field(USER_ID)
	private long userId;
	@Field(ANSWER)
	private String answer;

	public VoteDTO(Vote vote) {
		this.id = ID_TYPE + vote.getId();
		this.databaseId = vote.getId();
		if (vote.getPoll() != null)
		this.pollId = vote.getPoll().getId();
		if (vote.getUser() != null)
		this.userId = vote.getUser().getId();
		if (vote.getAnswer() != null)
		this.answer = vote.getAnswer().toString();
	}

	public VoteDTO() {

	}

	public static VoteDTO get(Object vote) {
		return new VoteDTO((Vote) vote);
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

	public long getPollId() {
		return pollId;
	}

	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}