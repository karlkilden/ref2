package com.kildeen.gv.poll;

import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.kildeen.gv.TheKnowledge;
import com.kildeen.gv.vote.Poll;

@RunWith(CdiTestRunner.class)
public class PollServiceIntegrationTest {
	@Rule
	public TestHelp<Poll> testhelp = TestHelp.getInstance(Poll.class);

	private TheKnowledge tk = testhelp.getKnowledge();

	@Inject
	private PollService pollService;

	@Test
	public void persist() throws Exception {
		pollService.save(tk.beer);
	}
	
	
	@Test
	public void critiera() throws Exception {
		List<Poll> res = pollService.fetchAll();
	}
	
	@Test
	public void create() throws Exception {
		pollService.create("test", "test2", true);
	}
	
	@Test
	
	public void fetch() throws Exception {
		Assert.assertNotNull(pollService.fetch(tk.beer.getId()));
	}
}
