package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.WithIdString;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WithIdString entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WithIdStringRepository extends JpaRepository<WithIdString, String>, JpaSpecificationExecutor<WithIdString> {}
