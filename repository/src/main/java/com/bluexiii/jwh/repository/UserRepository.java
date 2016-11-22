package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bluexiii on 16/8/25.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    public User findOneByUsername(String username);
    public User findOneByEmail(String email);
    public User findOneByPhone(String phone);
}
