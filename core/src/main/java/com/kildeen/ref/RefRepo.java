package com.kildeen.ref;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository

public interface RefRepo extends EntityRepository<RefEntity, Long> {

	RefEntity findOptionalByNameEqual(String name);

}
