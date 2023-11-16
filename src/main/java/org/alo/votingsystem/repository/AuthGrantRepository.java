package org.alo.votingsystem.repository;

import org.alo.votingsystem.models.AuthGrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGrantRepository extends JpaRepository<AuthGrant, Long> {

    AuthGrant findByCode(String code);
}
