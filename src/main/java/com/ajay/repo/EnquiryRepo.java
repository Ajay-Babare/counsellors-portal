package com.ajay.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ajay.entities.Enquiry;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
	
	@Query(value="SELECT * from enquiries where counsellor_id=:counsellorId",nativeQuery=true)
	public List<Enquiry> getEnquiriesByCounsellorId(Integer counsellorId);
	
}
