package com.ajay.service;

import com.ajay.dto.DashboardResponse;
import com.ajay.entities.Counsellor;

public interface CounsellorService {
	
	public Counsellor findByEmail(String email);
	
	public boolean register(Counsellor counsellor);
	
	public Counsellor login(String email, String pwd);
	
	public DashboardResponse getDashboardInfo(Integer counsellorId);
	
}
