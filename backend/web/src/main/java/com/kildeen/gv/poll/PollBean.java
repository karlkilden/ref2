package com.kildeen.gv.poll;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.deltaspike.jsf.api.message.JsfMessage;

import com.kildeen.gv.Messages;
import com.kildeen.gv.vote.Poll;
import com.kildeen.ref.UserContext;

@Named
@ViewScoped
public class PollBean implements Serializable {

	@Inject
	private UserContext userContext;
	
	@Inject
	JsfMessage<Messages> msg;
	
	@Inject
	PollService pollService;

	private Poll poll;
	
	private Long id;
	
	public void save() {
		poll = pollService.save(poll);
		msg.addInfo().successfullySaved();
	}
	
	

}
