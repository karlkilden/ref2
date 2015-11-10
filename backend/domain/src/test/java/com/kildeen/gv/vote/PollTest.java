package com.kildeen.gv.vote;

import org.junit.ClassRule;
import org.junit.Test;

import com.kildeen.gv.vote.EntityRule;
import com.kildeen.gv.vote.Poll;


public class PollTest  {
	
    @ClassRule
    public static EntityRule propertyTester = new EntityRule();
    
	private Poll poll = new Poll();

    
    @Test
	public void testName() throws Exception {
		System.out.println("");
	}
}
