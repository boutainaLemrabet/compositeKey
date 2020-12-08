package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.WithIdStringDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WithIdStringDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WithIdStringDetailsRepository
    extends JpaRepository<WithIdStringDetails, String>, JpaSpecificationExecutor<WithIdStringDetails> {}
