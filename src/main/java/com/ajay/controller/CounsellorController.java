package com.ajay.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.ajay.dto.DashboardResponse;
import com.ajay.entities.Counsellor;
import com.ajay.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	@Autowired
	private CounsellorService counsellorService;
	
	@GetMapping("/register")
	public String registerPage(Model model){
		Counsellor cobj = new Counsellor();
		
		//sending data from counsellor to ui
		model.addAttribute("counsellor", cobj);
		
		//returning view name
		return "register";
	}
	
	@PostMapping("/register")
	public String handleRegister(Counsellor counsellor, Model model){
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		if(byEmail!=null) {
			//failure
			model.addAttribute("emsg", "Duplicate Email");
			return "register";
		}
		boolean isRegistered =counsellorService.register(counsellor);
		if(isRegistered) {
			//success
			model.addAttribute("smsg", "Registration Success...");
		}
		else {
			//failure
			model.addAttribute("emsg", "Registration Fail...");
		}	
		return "register";
	}
	
	@GetMapping("/")
	public String index(Model model){
		Counsellor cobj = new Counsellor();
		
		//sending data from counsellor to ui
		model.addAttribute("counsellor", cobj);
		
		//returning view name
		return "index";

	}
	
	@PostMapping("/login")
	public String login(Counsellor counsellor,HttpServletRequest req, Model model){
		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPassword());
		if(c==null) {
			model.addAttribute("emsg","Invalid Credentials");
			return "index";
		}
		else {
			//valid login, store counsellorId for future purpose
			HttpSession session = req.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());
			
//			DashboardResponse dbresobj = counsellorService.getDashboardInfo(c.getCounsellorId());
//			model.addAttribute("dashboardInfo",dbresobj);
			
			return "redirect:/dashboard";
		}

	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {
		
		//get existing session and invalidate it
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
		DashboardResponse dbresobj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo",dbresobj);
		//redirect to login page
		return "dashboard";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		
		//get existing session and invalidate it
		HttpSession session = req.getSession(false);
		session.invalidate();
		
		//redirect to login page
		return "redirect:/";
	}

}
