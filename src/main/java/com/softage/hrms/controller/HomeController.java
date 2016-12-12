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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
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
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.ApprovalService;
import com.softage.hrms.service.NoDuesService;
import com.softage.hrms.service.PageService;
import com.softage.hrms.service.ResignationService;
import com.softage.hrms.service.impl.MailServiceImpl;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@Autowired
	private PageService pageService;
	
	@Autowired
	private ResignationService resignationService;
	
	@Autowired
	private MailServiceImpl mailService;
	
	@Autowired
	private ApprovalService approvalservice;
	
	@Autowired
	private NoDuesService noduesservice;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Locale locale, Model model) {
		logger.info("Welcome to the home page");
		String first_Name=(String)request.getParameter("firstName");
		String employee_code=(String)request.getParameter("emp_code");
		int roleID=Integer.parseInt(request.getParameter("role_id"));
		System.out.println("role_id is : "+roleID+" first_name is : "+first_Name);
		int userID=Integer.parseInt(request.getParameter("user_id"));
		int spokeID=Integer.parseInt(request.getParameter("spoke_id"));
		int circleID=Integer.parseInt(request.getParameter("CircleID"));
		HttpSession session = request.getSession();
		session.setAttribute("firstname", first_Name);
		session.setAttribute("employeecode", employee_code);
		session.setAttribute("roleid", roleID);
		session.setAttribute("userid", userID);
		session.setAttribute("spokeid", spokeID);
		session.setAttribute("circleid", circleID);
		ISoftAgeEnterpriseProxy i = new ISoftAgeEnterpriseProxy();
		try {
			request.setAttribute("param1", i.getUserDetail("ss0077").getRoleId());
			model.addAttribute("emp", i.getUserDetail("ss0077").getFirstName());
		} catch (Exception e) {
			model.addAttribute("msg", "NULL Values");
		}
		return "home";

	}
	
	
	@RequestMapping(value="/getPages",method= RequestMethod.GET)
	@ResponseBody
	public JSONObject getTemplateLinks(HttpServletRequest request,HttpSession session){
		JSONObject jsobj=new JSONObject();
		session=request.getSession();
		String empcode=(String)session.getAttribute("employeecode");
		int roleid=(Integer)session.getAttribute("roleid");
		ISoftAgeEnterpriseProxy emp_prxoy=new ISoftAgeEnterpriseProxy();
		//try {
			//int id=emp_prxoy.getUserDetail("ss0077").getRoleId(); standalones id
			//int id=1;
			jsobj=pageService.getPagesLink(roleid);
		//} catch (RemoteException e) {
			//e.printStackTrace();
		//}
		return jsobj;
	}
	
	@RequestMapping(value = "/resignationIni", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationReason(HttpServletRequest request,HttpSession session) {
		session=request.getSession();
		String empcode=(String)request.getAttribute("employeecode");
		JSONObject jsonReason = new JSONObject();
		JSONObject jsob=new JSONObject();
		JSONObject jsonRelDate=new JSONObject();
		ISoftAgeEnterpriseProxy emp_prxoy=new ISoftAgeEnterpriseProxy();
		//int notice_time=emp_prxoy.getUserDetail("").getNoticePeriod(); We have to use ESF Service
		int notice_time=60;
		jsonReason = resignationService.resignationInitialization();
		jsonRelDate=resignationService.getReleivingDate(empcode, notice_time);
		jsob.put("ReasonJson", jsonReason);
		jsob.put("noticeTime", notice_time);
		jsob.put("reldate", jsonRelDate);
		return jsob;
	}
	
	@RequestMapping(value = "/resignation", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationPage(HttpServletRequest request,HttpSession session) {
		//String employee_code="ss0077";
		session=request.getSession();
		String empcode=(String)session.getAttribute("employeecode");
		ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
		TblUserResignation resignation=new TblUserResignation();
		Date dateobj=new Date();
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("date is : "+df.format(dateobj));
		JSONObject jsonObj = new JSONObject();
		//try {
			String re = request.getParameter("emp_reason");
			System.out.println(re);
			int reason_for_leaving=Integer.parseInt(request.getParameter("emp_reason"));
			MstReason reason_mast=resignationService.getReason(reason_for_leaving);
			MstResignationStatus status_mast=resignationService.getStatus(1);
			String remarks = request.getParameter("emp_comments");
			//emp.getUserDetail(empcode).getReportingManager(); GET RM USING ESF SERVICE
			//emp.getUserDetail(empcode).getHrManager(); GET HR USING ESF SERVICE
			String rm_empcode="ss0078";
			String hr_empcode="ss0053";
			int noticeperiod=60; // Get Notice Period Using ESF Service
			String submit_date = df.format(dateobj);
			Calendar cal=Calendar.getInstance();
			cal.setTime(dateobj);
			cal.add(Calendar.DATE,noticeperiod);
			//String release_Datetime=String.valueOf(cal.getTime());
			Date release_Datetime=cal.getTime();
			//Date reldate = Calendar.getInstance().setTimeInMillis(release_Datetime);
			String relDate=String.valueOf(df.format(release_Datetime));
			Date finalDate=new Date(relDate);
			resignation.setComments(remarks);
			resignation.setEmpCode(empcode);
			resignation.setRmEmpcode(rm_empcode);
			resignation.setHrEmpcode(hr_empcode);
			resignation.setMstReason(reason_mast);
			resignation.setReleivingDate(finalDate);
			resignation.setResignationDate(dateobj);
			resignation.setMstResignationStatus(status_mast);
			jsonObj = resignationService.submitResignationService(resignation);
			//emp.getUserDetail(emp_code).getRMEmail(); RM email Using ESF service 
			//emp.getUserDetail(emp_code).getHREmail(); HR email Using ESF service
			//String manager_email=resignationService.getRmEmail(employee_code);
			//System.out.println(manager_email);
			//if (jsonObj.get("reason").equals("successful")) { have to apply some logic here
			//	mailService.sendEmail(manager_email, "evm@softageindia.com", "test",
			//			"exit module test");
			
		//} catch (RemoteException e) {
			//e.printStackTrace();
		//}
		return jsonObj;
	}
	
	@RequestMapping(value="/approvalInitialization",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getApprovalRequests(HttpServletRequest request,HttpSession session){
		//String empId="ss0087";
		session=request.getSession();
		String empcode=(String)session.getAttribute("employeecode");
		JSONObject jsonApproval=new JSONObject();
		jsonApproval=approvalservice.getApprovalRequestService(empcode);
		System.out.println("JSONVALUE"+jsonApproval);
		return jsonApproval;
	}
	
	@RequestMapping(value = "/getResignationAction", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationInitValues(HttpServletRequest request,HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		int count=1;
		List<JSONObject> quesList=new ArrayList<JSONObject>();
		session=request.getSession();
		int roleid=(Integer)session.getAttribute("roleid");
		List<MstQuestions> questions=approvalservice.getQuestionService(roleid);
		for(MstQuestions ques:questions){
			JSONObject quesjson=new JSONObject();
			String quesText=(String)ques.getQuestionText();
			String questType=(String)ques.getQuestionType();
			quesjson.put("qText", quesText);
			quesjson.put("qType", questType);
			quesjson.put("sno", count);
			quesjson.put("qAns", "");
			count=count+1;
			quesList.add(quesjson);
		}
		jsonObject.put("questions", quesList);
		//List<String> actionList = approvalservice.getResignationActionService();
		//List noticeList = approvalservice.getResignationNoticeService();
		//jsonObject.put("actionList", actionList);
		//jsonObject.put("noticeList", noticeList);
		return jsonObject;
	}
	
	
	
	@RequestMapping(value="/insertRmFeedback",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject submitRmFeedback(@RequestParam(value="answerList") String answerList,@RequestParam(value="resignAction") String resignAction){
		System.out.println("The data is : "+answerList + " res"+ resignAction);
		JSONParser parser=new JSONParser();
		try {
			JSONObject answerJson=(JSONObject)parser.parse(answerList);
			List<JSONObject> listAnswers=(List<JSONObject>) answerJson.get("data");
			for(JSONObject answer: listAnswers){
				String sid=String.valueOf(answer.get("sno"));
				String ans=(String)answer.get("qAns");
				String ques=(String)answer.get("qText");
				System.out.println("sno"+sid+" ans : "+ans+" ques"+ ques);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
/*	@RequestMapping(value= "/hrapprovalInit",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getHrApprovalInit(HttpServletRequest request,HttpSession session){
		System.out.println("HR Initialisation");
		session=request.getSession();
		String empCode=(String)session.getAttribute("empCode");
		return approvalservice.getHrApprovalInit(empCode);
		
	}*/
	
	
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
	 /*itassetsmodal= noduesservice.listassetsdetails();*/
	 
	 System.out.println(itassetsmodal);
		return itassetsmodal;
	}
	@RequestMapping(value = "/getnoduesaccounts", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getnoduesaccountinformation() {
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
	@RequestMapping(value = "/getaccountmodalassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getaccountsmodalassets(HttpServletRequest request) {
		
		JSONObject accountassetsmodal = new JSONObject();
	
	 
	 String emp_code=request.getParameter("employee_code");
	 accountassetsmodal.put("empcode","ss0097");
	 accountassetsmodal.put("firstname", "rohit");
	 accountassetsmodal.put("lastname", "raj");
	 accountassetsmodal.put("department", "it-software");
	 accountassetsmodal.put("designation", "java developer");
	 accountassetsmodal.put("location", "circle");
	 
	 
	 
	
	
	 
	
		return accountassetsmodal;
	}
	@RequestMapping(value = "/getAssets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getassests() {
		
		JSONObject accountassets = new JSONObject();
	
	 List<String> value = noduesservice.listassetsdetails();
	/*Iterator<String> i1=value.iterator();
	 while(i1.hasNext())
	 {
		 System.out.println("controller "+i1.next());
		 
	 }*/
	 
	 
	 accountassets.put("list", value);
	 
	 System.out.println("controller "+value);
	
		return accountassets;
	}
}

