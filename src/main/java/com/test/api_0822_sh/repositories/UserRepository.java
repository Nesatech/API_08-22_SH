package com.test.api_0822_sh.repositories;

import com.test.api_0822_sh.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
