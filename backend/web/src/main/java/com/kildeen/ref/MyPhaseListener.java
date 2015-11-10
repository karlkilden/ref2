package com.kildeen.ref;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class MyPhaseListener implements PhaseListener {
	
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
	}

	@Override
	public void afterPhase(PhaseEvent event) {

	}



}