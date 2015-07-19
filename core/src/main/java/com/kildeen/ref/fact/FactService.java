package com.kildeen.ref.fact;

import javax.inject.Inject;

import com.kildeen.gv.Service;

@Service
public class FactService {

	@Inject
	private FactRepo repo;

	public Fact save(Fact fact) {
		return repo.save(fact);
	}
	
}
