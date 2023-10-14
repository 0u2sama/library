package com.hcl.library.repository;


import com.hcl.library.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token , Long> {

    Token findByUsername(String username);
    Token findByToken(String token);
}
