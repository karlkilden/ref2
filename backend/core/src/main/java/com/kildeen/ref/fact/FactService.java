package com.kildeen.ref.fact;

import javax.inject.Inject;

import com.kildeen.gv.ee.Service;

@Service
public class FactService {

	@Inject
	private FactRepo repo;

	public Fact save(Fact fact) {
		return repo.save(fact);
	}
	
}
