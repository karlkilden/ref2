package com.kildeen.ref.fact;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository

public interface FactRepo extends EntityRepository<Fact, Long> {

}
