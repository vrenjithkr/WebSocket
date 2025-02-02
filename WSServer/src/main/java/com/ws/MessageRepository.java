package com.ws;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MessageRepository extends JpaRepository<UserMessages, Long> {
	List<UserMessages> findByStatus(String status);

}