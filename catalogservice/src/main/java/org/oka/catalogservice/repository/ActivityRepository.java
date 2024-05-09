package org.oka.catalogservice.repository;

import org.oka.catalogservice.domain.Activity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends ListCrudRepository<Activity, Integer> {

}
