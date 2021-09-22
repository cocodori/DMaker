package com.fastcampus.progmming.dmaker.repository;

import com.fastcampus.progmming.dmaker.entity.Developer;
import com.fastcampus.progmming.dmaker.type.StatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long > {
    Optional<Developer> findByMemberId(String memberId);

    List<Developer> findDevelopersByStatusCodeEquals(StatusCode statusCode);
}
