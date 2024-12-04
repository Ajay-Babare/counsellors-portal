package com.ajay.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ajay.entities.Counsellor;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
	
	//select * from counsellor where email=:email
	 public Counsellor findByEmail(String email);
	
	//select * from counsellors where email=:email and password=:password
	public Counsellor findByEmailAndPassword(String email, String password);
}
