package com.ajay.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ajay.dto.DashboardResponse;
import com.ajay.entities.Counsellor;
import com.ajay.entities.Enquiry;
import com.ajay.repo.CounsellorRepo;
import com.ajay.repo.EnquiryRepo;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;
	
	@Autowired
	private EnquiryRepo enquiryRepo;

	@Override
	public Counsellor findByEmail(String email) {

		return counsellorRepo.findByEmail(email);
	}
	
	@Override
	public boolean register(Counsellor counsellor) {
		
		Counsellor savedCounsellor = counsellorRepo.save(counsellor);
		
		if(null != savedCounsellor.getCounsellorId())
			return true;
		
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		
		return counsellorRepo.findByEmailAndPassword(email,pwd);
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {
		DashboardResponse response = new DashboardResponse();
		
		List<Enquiry> listEnqs= enquiryRepo.getEnquiriesByCounsellorId(counsellorId);
		Integer totalEnqs = listEnqs.size();

		Integer openEnqs = listEnqs.stream()
									.filter(e -> e.getEnquiryStatus().equals("opened"))
									.collect(Collectors.toList())
									.size();
		Integer enrolledEnqs = listEnqs.stream()
				.filter(e -> e.getEnquiryStatus().equals("enrolled"))
				.collect(Collectors.toList())
				.size();
					
		Integer lostEnqs = listEnqs.stream()
				.filter(e -> e.getEnquiryStatus().equals("lost"))
				.collect(Collectors.toList())
				.size();
		
		response.setTotalEnqs(totalEnqs);
		response.setOpenEnqs(openEnqs);
		response.setEnrolledEnqs(enrolledEnqs);
		response.setLostEnqs(lostEnqs);
		
		return response;
	}


	
	

}
