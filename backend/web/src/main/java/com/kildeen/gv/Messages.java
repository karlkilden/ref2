package com.kildeen.gv;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageContextConfig;

@MessageBundle
@MessageContextConfig(messageSource = { "messages" })
public interface Messages {
	
	public String successfullySaved();

}
