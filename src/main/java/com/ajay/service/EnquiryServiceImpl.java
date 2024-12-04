package com.ajay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ajay.dto.ViewEnquiriesFilteredRequest;
import com.ajay.entities.Counsellor;
import com.ajay.entities.Enquiry;
import com.ajay.repo.CounsellorRepo;
import com.ajay.repo.EnquiryRepo;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private EnquiryRepo enquiryRepo;
	
	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		
		if(counsellor == null)
			throw new Exception("No Counsellor Found");
		//associating counsellor to enquiry
		enq.setCounsellor(counsellor);
		
		Enquiry save = enquiryRepo.save(enq);
		
		if(save.getEnquiryId() != null)
			return true;
		
		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {
		return enquiryRepo.getEnquiriesByCounsellorId(counsellorId);
	}

	@Override
	public Enquiry getEnquiryById(Integer enqId) {
		return enquiryRepo.findById(enqId).orElse(null);
	}
	
	@Override
	public List<Enquiry> getEnquiriesWithFilter(ViewEnquiriesFilteredRequest filterReq, Integer counsellorId) {
		Enquiry enq = new Enquiry(); //entity
		
		if(StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}
		if(StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}
		if(StringUtils.isNotEmpty(filterReq.getEnquiryStatus())) {
			enq.setEnquiryStatus(filterReq.getEnquiryStatus());
		}
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		enq.setCounsellor(counsellor);
		
		Example<Enquiry> of = Example.of(enq);
		List<Enquiry> enqList = enquiryRepo.findAll(of);
		
		return enqList;
	}

}
