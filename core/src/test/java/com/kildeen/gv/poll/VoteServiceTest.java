package com.kildeen.gv.poll;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.kildeen.gv.vote.AnswerType;
import com.kildeen.gv.vote.Vote;
@RunWith(CdiTestRunner.class)
public class VoteServiceTest {

	 @Inject
	 private VoteService voteService;
	
	 @Rule
	 public TestHelp<Vote> testhelp = TestHelp.getInstance(Vote.class);
	
	@Test
	public void save() {
		testhelp.assertCountIncreaseWith(0);

		voteService.save(new Vote(AnswerType.YES));
		testhelp.assertCountIncreaseWith(1);
	}
	
}
