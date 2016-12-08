package com.softage.hrms.controller;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.ISoftAgeEnterpriseProxy;
import org.tempuri.SoftAgeEnterpriseServiceLocator;

import com.softage.hrms.dao.NoDuesDao;
import com.softage.hrms.service.NoDuesService;
import com.softage.hrms.service.PageService;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private NoDuesService noduesservice;
	
	@Autowired
	private PageService pageservice;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Locale locale, Model model) {
		logger.info("Welcome to the home page");

		HttpSession session = request.getSession();
		session.getAttribute("");
		ISoftAgeEnterpriseProxy i = new ISoftAgeEnterpriseProxy();
		try {
			request.setAttribute("param1", i.getUserDetail("ss0077").getRoleId());
			model.addAttribute("emp", i.getUserDetail("ss0077").getFirstName());
		} catch (Exception e) {
			model.addAttribute("msg", "NULL Values");
		}
		return "home";

	}
	@RequestMapping(value = "/getPages", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getTemplateLinks(HttpServletRequest request,HttpSession session) {
		JSONObject jsobj = new JSONObject();
		session=request.getSession();
		/*int role_id=(Integer)session.getAttribute("roleid");*/
		/*int role_id=(Integer)session.getAttribute("ss0097");*/
		//ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		//try {
			//int id = emp_prxoy.getUserDetail("ss0077").getRoleId();
		/*role_id		*/
			jsobj = pageservice.getPagesLink();
		//} catch (RemoteException e) {
		//	e.printStackTrace();
		//}
		return jsobj;
	}
	@RequestMapping(value = "/getnoduesit", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getnoduesitinformation() {
		ArrayList<JSONObject> listinformation=new ArrayList<JSONObject>();
		JSONObject jsonobject=new JSONObject();
		
		
		try{
			JSONObject itjson = new JSONObject();
			itjson.put("sno", 1);
			itjson.put("empcode","ss0097");
			itjson.put("firstname", "rohit");
			itjson.put("lastname", "raj");
			itjson.put("department", "it-software");
			itjson.put("designation", "java developer");
			itjson.put("location", "circle");
			List<String> listempcoderesign=noduesservice.listrmacceptedempcode();
			
			listinformation.add(itjson);
		
		jsonobject.put("emplist", listinformation);
		
		}
		catch (Exception e) {
			e.printStackTrace();
}
		
		return jsonobject;

}
	
	@RequestMapping(value = "/getitmodalassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getitmodalassets(HttpServletRequest request) {
		
		JSONObject itassetsmodal = new JSONObject();
	
	 
	 String emp_code=request.getParameter("employee_code");
	 itassetsmodal.put("empcode","ss0097");
	 itassetsmodal.put("firstname", "rohit");
	 itassetsmodal.put("lastname", "raj");
	 itassetsmodal.put("department", "it-software");
	 itassetsmodal.put("designation", "java developer");
	 itassetsmodal.put("location", "circle");
	 
	 
	 /*itassetsmodal.put("assets", assets);*/
	 itassetsmodal= noduesservice.listassetsdetails();
	 
	 System.out.println(itassetsmodal);
		return itassetsmodal;
	}
}

