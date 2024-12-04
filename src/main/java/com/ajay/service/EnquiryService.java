package com.ajay.service;

import java.util.List;

import com.ajay.dto.ViewEnquiriesFilteredRequest;
import com.ajay.entities.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public Enquiry getEnquiryById(Integer enqId);

	List<Enquiry> getEnquiriesWithFilter(ViewEnquiriesFilteredRequest filterReq, Integer counsellorId);
	
}
