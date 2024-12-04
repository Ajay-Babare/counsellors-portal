package com.ajay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ajay.dto.ViewEnquiriesFilteredRequest;
import com.ajay.entities.Enquiry;
import com.ajay.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	EnquiryService enquiryService;
	
	@GetMapping("/enquiry")
	public String enquiryPage(Model model) {
		Enquiry enquiry = new Enquiry();
		model.addAttribute("enquiry", enquiry);		
		return "enquiry";
	}
	
	@GetMapping("/editEnq")	
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model){
//		HttpSession session = req.getSession(false);
//		session.getAttribute("counsellorId");
		Enquiry enquiry = enquiryService.getEnquiryById(enqId);
		model.addAttribute("enquiry", enquiry);	
		return 	"enquiry";
	}
	@PostMapping("/editEnq")	
	public String updateEnquiry(@RequestParam("enqId") Integer enqId, HttpServletRequest req, Model model) throws Exception{
		HttpSession session = req.getSession(false);
		Integer cId = (Integer)session.getAttribute("counsellorId");
		Enquiry enquiry = enquiryService.getEnquiryById(enqId);
		enquiryService.addEnquiry(enquiry, cId);
		model.addAttribute("enquiry", enquiry);	
		return 	"enquiry";
	}
	
	@PostMapping("/addEnq")
	public String handleEnquiry(Enquiry enquiry, HttpServletRequest req, Model model) throws Exception {
		
		// get existing session obj
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
		boolean isSaved = enquiryService.addEnquiry(enquiry, counsellorId);
		if(isSaved) {
			model.addAttribute("smsg", "Enquiry Added");
		}
		else {
			model.addAttribute("emsg", "Failed to Add Enquiry");			
		}
		
//		enquiry = new Enquiry();
//		model.addAttribute("enquiry", enquiry);
		
			return "enquiry";
	}
	
	@GetMapping("/enquiries")	
	public String getAllEnquiries(HttpServletRequest req, Model model){
		ViewEnquiriesFilteredRequest filter = new ViewEnquiriesFilteredRequest();
		HttpSession session = req.getSession(false);
		Integer cId =(Integer) session.getAttribute("counsellorId");
		List<Enquiry> allEnquiries = enquiryService.getAllEnquiries(cId);
		model.addAttribute("enquiries", allEnquiries);
		model.addAttribute("filterReq", filter);
		return 	"view-enquiries";
	}
	
	@PostMapping("/enquiries")	
	public String filteredEnquiries(ViewEnquiriesFilteredRequest filterEnqs, HttpServletRequest req, Model model){
		HttpSession session = req.getSession(false);
		Integer cId =(Integer) session.getAttribute("counsellorId");
		List<Enquiry> allEnquiries = enquiryService.getEnquiriesWithFilter(filterEnqs, cId);
		model.addAttribute("filterReq", filterEnqs);
		model.addAttribute("enquiries", allEnquiries);
		return 	"view-enquiries";
	}
	

}
