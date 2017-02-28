package com.softage.hrms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.el.ArrayELResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import org.json.simple.JSONArray;

import org.apache.axis.transport.mail.MailSender;
import org.hibernate.loader.custom.Return;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.tempuri.ISoftAgeEnterpriseProxy;
import org.tempuri.SoftAgeEnterpriseServiceLocator;
import org.w3c.dom.ls.LSInput;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.schemas._2003._10.Serialization.Arrays.ArrayOfKeyValueOfstringstringKeyValueOfstringstring;
import com.softage.hrms.dao.NoDuesDao;
import com.softage.hrms.model.MstQuestions;

import com.softage.hrms.model.MailSend;
import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;

import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.model.TblUploadedPath;

import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblExEmpCommunication;
import com.softage.hrms.model.TblExEmployeeQuery;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.model.TblNoDuesClearence;

import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.ApprovalService;
import com.softage.hrms.service.EmployeeDocumentService;
import com.softage.hrms.service.ExitInterviewService;
import com.softage.hrms.service.NoDuesService;
import com.softage.hrms.service.PageService;
import com.softage.hrms.service.QueryService;
import com.softage.hrms.service.ResignationService;
import com.softage.hrms.service.impl.MailServiceImpl;

import javassist.compiler.Parser;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ExitInterviewService exitinterviewservice;

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

	@Autowired
	private EmployeeDocumentService employeeDocumentService;

	@Autowired
	private QueryService queryService;
	

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Locale locale, Model model) {
		logger.info("Welcome to the home page");
		String first_Name = (String) request.getParameter("firstName");
		String employee_code = (String) request.getParameter("emp_code");
		int roleID = Integer.parseInt(request.getParameter("role_id"));
		System.out.println("role_id is : " + roleID + " first_name is : " + first_Name);
		int userID = Integer.parseInt(request.getParameter("user_id"));
		int spokeID = Integer.parseInt(request.getParameter("spoke_id"));
		int circleID = Integer.parseInt(request.getParameter("CircleID"));
		String officeCode=(String)request.getParameter("ReportingOfficeCode");
		HttpSession session = request.getSession();
		session.setAttribute("firstname", first_Name);
		session.setAttribute("employeecode", employee_code);
		session.setAttribute("roleid", roleID);
		session.setAttribute("userid", userID);
		session.setAttribute("spokeid", spokeID);
		session.setAttribute("circleid", circleID);
		session.setAttribute("officecode", officeCode);
		ISoftAgeEnterpriseProxy i = new ISoftAgeEnterpriseProxy();
		
		String [] keys={"empcode"};
		String [] values={"s42970"};
		String [] assetValues={"ss0073"};
		String[] officekeys = {"OFFICECODE"};
		String[] officevalues = {officeCode};
		String noduestring=null;
		try { 	
			String empInfo=i.enterPriseDataService("EVM","EmpInfo", keys,values);
			String assestInfo=i.enterPriseDataService("Asset", "ASSETINFO", keys, assetValues);
			noduestring=i.enterPriseDataService("EVM", "NODUESOWNERS", officekeys, officevalues);
			System.out.println("Asset information string" + assestInfo+"nodues : "+noduestring);
			JSONParser jsonParser =new JSONParser();
			JSONObject jsonObject  = (JSONObject)jsonParser.parse(empInfo);
			JSONObject assetJson=(JSONObject)jsonParser.parse(assestInfo);
			request.setAttribute("param1", i.getUserDetail("ss0077").getRoleId());
			System.out.println(i.getUserDetail("ss0077").getRoleId());
			model.addAttribute("emp", i.getUserDetail("ss0077").getFirstName());
		} catch (Exception e) {
			model.addAttribute("msg", "NULL Values");
		}

		return "home";
}

	@RequestMapping(value = "/getPages", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getTemplateLinks(HttpServletRequest request, HttpSession session) {
		JSONObject jsobj = new JSONObject();
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		int roleid = (Integer) session.getAttribute("roleid");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currDatetime = new Date();
		String current_date = df.format(currDatetime);
		System.out.println("The current datetime is : " + current_date);
		ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		// try {
		jsobj = pageService.getPagesLink(roleid);
		//jsobj=pageService.getPagesBasedOnRoleId(empcode,current_date,roleid); NEW METHOD TO BE MADE
		// } catch (RemoteException e) {
		// e.printStackTrace();
		// }
		return jsobj;
	}

	@RequestMapping(value = "/resignationIni", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationReason(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		JSONObject jsonReason = new JSONObject();
		JSONObject jsob = new JSONObject();
		JSONObject jsonRelDate = new JSONObject();
		ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		String[] keys={"empcode"};
		String[] values={empcode};
		try {
			String empInfo=emp_prxoy.enterPriseDataService("EVM","EmpInfo", keys,values);
			System.out.println(empInfo);
			JSONParser parser=new JSONParser();
			JSONObject serviceJson=(JSONObject)parser.parse(empInfo);
			//int notice_time=(Integer) serviceJson.get("NoticePeriod");ESF SERVICE TO BE USED
			
			int notice_time = 60;
			jsonReason = resignationService.resignationInitialization();
			
			jsonRelDate = resignationService.getReleivingDate(empcode, notice_time);
			jsob.put("ReasonJson", jsonReason);
			jsob.put("noticeTime", notice_time);
			jsob.put("reldate", jsonRelDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsob;
	}

	@RequestMapping(value = "/resignation", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationPage(HttpServletRequest request, HttpSession session) {
		// String employee_code="ss0077";
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		int circleid = (Integer) session.getAttribute("circleid");
		String office_code=(String)session.getAttribute("officecode");
		ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
		TblUserResignation resignation = new TblUserResignation();
		Date dateobj = new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println("date is : " + df.format(dateobj) + " office code is : "+office_code);
		JSONObject jsonObj = new JSONObject();
		//try {
		String re = request.getParameter("emp_reason");
		System.out.println(re);
		int reason_for_leaving = Integer.parseInt(request.getParameter("emp_reason"));
		MstReason reason_mast = resignationService.getReason(reason_for_leaving);
		MstResignationStatus status_mast = resignationService.getStatus(1);
		String remarks = request.getParameter("emp_comments");

		// emp.getUserDetail(empcode).getReportingManager(); GET RM USING ESF
		// SERVICE
		// String hr_empcode=emp.getUserDetail(empcode).getHrManager(); GET HR
		// USING ESF SERVICE
		String[] keys={"empcode"};
		String[] values={empcode};
		String empinfostring = null;
		String noduestring=null;
		JSONParser parser=new JSONParser();
		JSONObject empinfoJson = new JSONObject();
		JSONObject noduesJson=new JSONObject();
		String[] officekeys = {"OFFICECODE"};
		String[] officevalues = {office_code};
		String hr_empcode=null;
		try {
			empinfostring = emp.enterPriseDataService("EVM", "EmpInfo", keys, values);
			noduestring=emp.enterPriseDataService("EVM", "NODUESOWNERS", officekeys, officevalues);
			empinfoJson = (JSONObject)parser.parse(empinfostring);
			noduesJson=(JSONObject)parser.parse(noduestring);
			hr_empcode=(String)noduesJson.get("HrEmpCode");
			System.out.println(noduesJson.toString());
			System.out.println(empinfoJson);
			//noduesJson=(JSONObject)parser.parse("");
			//System.out.println(empinfoJson);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String rm_empcode=(String)empinfoJson.get("ManagerCode");
		//String rm_empcode = "ss0078";
		//String hr_empcode = "ss0073";
		int noticeperiod = 60; // Get Notice Period Using ESF Service
		String submit_date = df.format(dateobj);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateobj);
		cal.add(Calendar.DATE, noticeperiod);
		// String release_Datetime=String.valueOf(cal.getTime());
		Date release_Datetime = cal.getTime();
		// Date reldate =
		// Calendar.getInstance().setTimeInMillis(release_Datetime);
		String relDate = String.valueOf(df.format(release_Datetime));
		Date finalDate = new Date(relDate);
		resignation.setComments(remarks);
		resignation.setEmpCode(empcode);
		resignation.setRmEmpcode(rm_empcode);
		resignation.setHrEmpcode(hr_empcode);
		resignation.setMstReason(reason_mast);
		resignation.setReleivingDate(finalDate);
		resignation.setResignationDate(dateobj);
		resignation.setMstResignationStatus(status_mast);
		resignation.setCircleId(circleid);
		jsonObj = resignationService.submitResignationService(resignation);
		// emp.getUserDetail(emp_code).getRMEmail(); RM email Using ESF service
		// emp.getUserDetail(emp_code).getHREmail(); HR email Using ESF service
		// emp.getUserDetail(emp_code).getEmail(); EMployee Email
		// String manager_email=resignationService.getRmEmail(employee_code);
		String manager_email = "arpan.mathur@softageindia.com";// ESF Service
		String hr_email = "arpan.mathur@softageindia.com";
		String emp_email = "arpan.mathur@softageindia.com";
		// System.out.println(manager_email);
		String rm_message = "Request for resignatin has been raised by " + empcode + " for RM";
		String hr_message = "Request for resignatin has been raised by " + empcode + " for HR";
		String emp_message = "Request for resignation has been raised by you";
		if (jsonObj.get("result").equals("successful")) {
			try {
				mailService.sendEmail(manager_email, "evm@softageindia.com", "test", rm_message);
				mailService.sendEmail(hr_email, "evm@softageindia.com", "test", hr_message);
				mailService.sendEmail(emp_email, "evm@softageindia.com", "test", emp_message);
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Problem is sending email");
			}

		}
		
		
		return jsonObj;
	}

	@RequestMapping(value = "/approvalInitialization", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getApprovalRequests(HttpServletRequest request, HttpSession session) {
		// String empId="ss0087";
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		JSONObject jsonApproval = new JSONObject();
		jsonApproval = approvalservice.getApprovalRequestService(empcode);
		System.out.println("JSONVALUE" + jsonApproval);
		return jsonApproval;
	}

	@RequestMapping(value = "/getResignationAction", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationInitValues(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		int count = 1;
		List<JSONObject> quesList = new ArrayList<JSONObject>();
		session = request.getSession();
		int roleid = (Integer) session.getAttribute("roleid");
		List<MstQuestions> questions = approvalservice.getQuestionService(roleid);
		for (MstQuestions ques : questions) {
			JSONObject quesjson = new JSONObject();
			String quesText = (String) ques.getQuestionText();
			String questType = (String) ques.getQuestionType();
			int qid = (Integer) ques.getQid();
			quesjson.put("qText", quesText);
			quesjson.put("qType", questType);
			quesjson.put("sno", qid);
			quesjson.put("qCount", count);
			quesjson.put("qAns", "");
			count = count + 1;
			quesList.add(quesjson);
		}
		jsonObject.put("questions", quesList);
		// List<String> actionList =
		// approvalservice.getResignationActionService();
		// List noticeList = approvalservice.getResignationNoticeService();
		// jsonObject.put("actionList", actionList);
		// jsonObject.put("noticeList", noticeList);
		return jsonObject;
	}

	@RequestMapping(value = "/insertRmFeedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submitRmFeedback(@RequestParam(value = "answerList") String answerList,
			@RequestParam(value = "resignAction") String resignAction, @RequestParam("feedbackon") String feedbackon,
			HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		System.out.println("The data is : " + answerList + " res : " + resignAction + " feedbackon : " + feedbackon);
		int resignStatus = Integer.parseInt(resignAction);
		JSONObject statusJson = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			JSONObject answerJson = (JSONObject) parser.parse(answerList);
			List<JSONObject> listAnswers = (List<JSONObject>) answerJson.get("data");
			MstResignationStatus resignationStatus = new MstResignationStatus();
			resignationStatus = resignationService.getStatus(resignStatus);
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date rmapprovaldate = new Date();
			String rmappdate = df.format(rmapprovaldate);
			Date rm_approval_date = new Date(rmappdate);
			TblUserResignation resignedUser = resignationService.getResignationUserService(feedbackon, 1);
			resignedUser.setMstResignationStatus(resignationStatus);
			resignedUser.setRmApprovalDate(rm_approval_date);
			for (JSONObject answer : listAnswers) {
				Long serialid = (Long) answer.get("sno");
				int sid = serialid.intValue();
				String ans = (String) answer.get("qAns");
				String ques = (String) answer.get("qText");
				String feedbackby = "RM";
				TblFeedbacks feedback = new TblFeedbacks();
				feedback.setAnsText(ans);
				feedback.setFeedbackBy(feedbackby);
				feedback.setFeedbackFrom(empcode);
				feedback.setStageId(1);
				MstQuestions question = approvalservice.getRmFeedbackQuestionService(sid);
				feedback.setMstQuestions(question);
				feedback.setTblUserResignation(resignedUser);

				int status = approvalservice.saveRmFeedbackService(feedback);

				System.out.println("sno" + sid + " ans : " + ans + " ques" + ques);
			}
			approvalservice.updateResignationStatus(resignedUser);
			statusJson.put("status", "Success");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return statusJson;
	}

	@RequestMapping(value = "/hrapprovalInit", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getHrApprovalInit(HttpServletRequest request, HttpSession session) {

		session = request.getSession();
		String empCode = (String) session.getAttribute("employeecode");
		int circle_code = (Integer) session.getAttribute("circleid");

		JSONObject hrapprovaljson = resignationService.getHrApprovalInitService(empCode, 2, circle_code);
		/*
		 * for(TblUserResignation resignedUser : approvedResignationList){
		 * JSONObject acceptedResignation=new JSONObject(); String
		 * employee_code=resignedUser.getEmpCode(); ISoftAgeEnterpriseProxy
		 * emp=new ISoftAgeEnterpriseProxy(); try { String
		 * firstname=emp.getUserDetail(employee_code).getFirstName(); String
		 * lastname=emp.getUserDetail(employee_code).getLastName(); String
		 * name=firstname+" "+lastname; MstReason
		 * reason=resignedUser.getMstReason(); String
		 * reason_for_leaving=reason.getReason(); String
		 * remarks=resignedUser.getComments(); //int
		 * notice_period=emp.getUserDetail(employee_code).getNoticePeriod();
		 * FROM ESF Service int notice_period=60; Date
		 * resDate=resignedUser.getResignationDate(); String
		 * resignDate=df.format(resDate); String
		 * rmempcode=resignedUser.getRmEmpcode(); //String
		 * rm_email=emp.getUserDetail(rmempcode).getEmail(); FROM ESF SERVICE
		 * String rm_email="arpan.mathur@softageindia.com";
		 * acceptedResignation.put("sno", count);
		 * acceptedResignation.put("empname", name);
		 * acceptedResignation.put("empcode", employee_code);
		 * acceptedResignation.put("leaving_reason", reason_for_leaving);
		 * acceptedResignation.put("remarks", remarks);
		 * acceptedResignation.put("noticetime", notice_period);
		 * acceptedResignation.put("resignDate", resignDate);
		 * acceptedResignation.put("rm_empcode", rmempcode);
		 * acceptedResignation.put("rm_email", rm_email);
		 * acceptedResignedList.add(acceptedResignation); count=count+1; } catch
		 * (RemoteException e) { e.printStackTrace(); } }
		 * System.out.println(acceptedResignedList);
		 * hrapprovaljson.put("empinfo", acceptedResignedList);
		 * System.out.println(hrapprovaljson);
		 */
		return hrapprovaljson;
	}

	@RequestMapping(value = "/submitHrApproval", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject submitHrApproval(HttpServletRequest request, HttpSession session) {
		JSONObject jsob = new JSONObject();
		String lwdCommentStatus = "";
		session = request.getSession();
		String hrempcode = (String) session.getAttribute("employeecode");
		String empname = (String) request.getParameter("empname");
		String empcode = (String) request.getParameter("empCode");
		String lastworkingdate = (String) request.getParameter("hrlwd");
		System.out.println("lwd : " + lastworkingdate);
		String rmempcode = (String) request.getParameter("rmempcode");
		String hrcomments = (String) request.getParameter("hrcomments");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date hrapprovaldate = new Date();
		String hr_app_date = df.format(hrapprovaldate);
		Date hrappdate = new Date(hr_app_date);
		Date lwd = new Date(lastworkingdate);
		MstResignationStatus hrstatus = resignationService.getStatus(5); // Changed
		// to
		// 5
		// after
		// changing
		// status
		// table
		TblUserResignation resignation = resignationService.getResignationUserService(empcode, 2);
		MstQuestions hrcomment = approvalservice.getRmFeedbackQuestionService(9);
		TblFeedbacks hr_lwd_comment = new TblFeedbacks();
		resignation.setHrApprovalDate(hrappdate);
		resignation.setHrLwdDate(lwd);
		resignation.setMstResignationStatus(hrstatus);
		hr_lwd_comment.setAnsText(hrcomments);
		hr_lwd_comment.setFeedbackBy("HR");
		hr_lwd_comment.setFeedbackFrom(hrempcode);
		hr_lwd_comment.setMstQuestions(hrcomment);
		hr_lwd_comment.setStageId(2);
		hr_lwd_comment.setTblUserResignation(resignation);
		String lwdStatus = approvalservice.insertHrLwdService(resignation);
		/* We have to use ESF service to get emails */
		if (lwdStatus.equalsIgnoreCase("successful")) {
			lwdCommentStatus = approvalservice.insertHrLwdCommentService(hr_lwd_comment);
			String messageToEmp = "Hi your last working day has been set to - " + hrappdate;
			try {
				mailService.sendEmail("arpan.mathur@softageindia.com", "evm@softageindia.com",
						"Last Working Day Set By HR", messageToEmp);
				mailService.sendEmail("arpan.mathur@softageindia.com", "evm@softageindia.com",
						"Last Working Day Set By HR", messageToEmp);
				mailService.sendEmail("arpan.mathur@softageindia.com", "evm@softageindia.com",
						"Last Working Day Set By HR", messageToEmp);
			} catch (Exception e) {
				System.out.println("Exception in sending confirmation mails for the hr process");
			}
		} else {
			lwdCommentStatus = "Unable to update";
		}
		jsob.put("status", lwdCommentStatus);
		return jsob;
	}

	@RequestMapping(value = "/trackingStatusInit", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationStatus(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		JSONObject resigneduser = new JSONObject();
		String empcode = (String) session.getAttribute("employeecode");
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		TblUserResignation resignationModel = resignationService.getResignationUserService(empcode, 0);
		int resID = resignationModel.getResignationId();
		MstReason reasonBean = resignationModel.getMstReason();
		int reasonid = resignationModel.getResignationId();
		String reason = reasonBean.getReason();
		MstResignationStatus statusBean = resignationModel.getMstResignationStatus();
		int statusid = statusBean.getStatusId();
		String resignation_status = statusBean.getStatus();
		String res_empcode = resignationModel.getEmpCode();
		String res_remarks = resignationModel.getComments();
		Date res_date = resignationModel.getResignationDate();
		String res_date_string = (res_date != null ? df.format(res_date) : null);
		// String res_date_string=df.format(res_date);
		// System.out.println(res_date);
		Date relieving_date = resignationModel.getReleivingDate();
		String rel_date_string = (relieving_date != null ? df.format(relieving_date) : null);
		String res_rm_empcode = resignationModel.getRmEmpcode();
		String res_hr_empcode = resignationModel.getHrEmpcode();
		Date rm_approvaldate = resignationModel.getRmApprovalDate();
		String approval_date_String = (rm_approvaldate != null ? df.format(rm_approvaldate) : "Pending");
		Date hr_approvaldate = resignationModel.getHrApprovalDate();
		String approval_date_string = (hr_approvaldate != null ? df.format(hr_approvaldate) : "Pending");
		Date hr_lwd_date = resignationModel.getHrLwdDate();
		String lwd_date_string = (hr_lwd_date != null ? df.format(hr_lwd_date) : "Pending");
		resigneduser.put("resID", resID);
		resigneduser.put("resStatusId", statusid);
		resigneduser.put("resreason", reason);
		resigneduser.put("resStatus", resignation_status);
		resigneduser.put("resEmpcode", res_empcode);
		resigneduser.put("resRemarks", res_remarks);
		resigneduser.put("resDate", res_date_string);
		resigneduser.put("resRelievingDate", rel_date_string);
		resigneduser.put("resRmEmpcode", res_rm_empcode);
		resigneduser.put("resHrEmpcode", res_hr_empcode);
		resigneduser.put("resRmApprovalDate", approval_date_String);
		resigneduser.put("resHrApprovalDate", approval_date_string);
		resigneduser.put("resLwdDate", lwd_date_string);
		return resigneduser;
	}

	@RequestMapping(value="/exEmployeeLogin",method=RequestMethod.GET)
	public String getExEmpLogin(Model model){
		model.addAttribute("loginBean",new TblUserResignation());
		return "login";
	}
	
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	public String authenticate(@ModelAttribute("loginBean") TblUserResignation tbluserresignation,Model model,
			HttpServletRequest request){
		String emp_code=tbluserresignation.getExEmpUserid();
		TblUserResignation ex_emp=resignationService.getResignationUserService(emp_code, 13);
		if(ex_emp!=null){
			HttpSession session=request.getSession();
			session.setAttribute("resignID", ex_emp.getResignationId());
			session.setAttribute("employeecode", ex_emp.getEmpCode());
			session.setAttribute("roleid", 50);
			session.setAttribute("hrapprovaldate", ex_emp.getHrApprovalDate());
			session.setAttribute("hrempcode", ex_emp.getHrEmpcode());
			session.setAttribute("hrlwd", ex_emp.getHrLwdDate());
			session.setAttribute("resdate", ex_emp.getResignationDate());
			session.setAttribute("rmapprovaldate", ex_emp.getRmApprovalDate());
			session.setAttribute("rmempcode", ex_emp.getRmEmpcode());
			session.setAttribute("exexmpcode",ex_emp.getExEmpUserid());
			session.setAttribute("exempemail", ex_emp.getExEmpEmail());
			return "home";
			
		}else{
			model.addAttribute("msg", "Incorrect Username or Password");
			return "login";
		}
	}
	
	@RequestMapping(value = "/getNoDuesStatuses", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getNoDuesPendingStatuses(HttpServletRequest request, HttpSession session) {
		String resignID = (String) request.getParameter("resignationID");
		int resid = Integer.parseInt(resignID);
		JSONObject pendingNoDues = new JSONObject();
		pendingNoDues = noduesservice.getNoDuesPendingStatus(resid);
		return pendingNoDues;
	}

	@RequestMapping(value = "/getDocUploadedStatuses", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getDocumentUploadedPending(HttpServletRequest request, HttpSession session) {
		String resignID = (String) request.getParameter("resignationID");
		int resid = Integer.parseInt(resignID);
		JSONObject notUploadedDocuments = new JSONObject();
		notUploadedDocuments = employeeDocumentService.getNotUploadedDocumentsById(resid);
		return notUploadedDocuments;
	}

	@RequestMapping(value = "/getnoduesemplist", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getnoduesitinformation(HttpServletRequest request, HttpSession session,
			@RequestParam("status") int status) {
		int count = 1;
		session = request.getSession();
		int circleid = (Integer) session.getAttribute("circleid");
		ArrayList<JSONObject> listinformation = new ArrayList<JSONObject>();
		JSONObject jsonobject = new JSONObject();
		ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		List<String> listempcoderesign = noduesservice.listrmacceptedempcode(circleid, status);
		try {
			for (String code : listempcoderesign) {
				String[] key = { "empcode" };
				String[] value = { code };
				String empInfo = emp_prxoy.enterPriseDataService("EVM", "empInfo", key, value);
				JSONParser parser = new JSONParser();
				JSONObject servicejson = (JSONObject) parser.parse(empInfo);
				String employeename = (String) servicejson.get("EmployeeName");
				String designation = (String) servicejson.get("Designation");
				String spokename = (String) servicejson.get("SpokeName");
				String employeecode = (String) servicejson.get("EmployeeCode");
				long departmentid = (Long) servicejson.get("DepartmentID");

				JSONObject itjson = new JSONObject();
				itjson.put("sno", count);
				itjson.put("empcode", code);
				itjson.put("empname", employeename);
				itjson.put("department", departmentid);
				itjson.put("designation", designation);
				itjson.put("location", spokename);
				listinformation.add(itjson);
				count++;
			}
			jsonobject.put("emplist", listinformation);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonobject;

	}

	@RequestMapping(value = "/getemployeemodalinfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getemployeeinformation(HttpServletRequest request) {
		ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		String emp_code = request.getParameter("employee_code");
		JSONObject itassetsmodal = new JSONObject();
		String[] key = { "empcode" };
		String[] value = { emp_code };
		String empInfo = "";

		try {
			empInfo = emp_prxoy.enterPriseDataService("EVM", "empInfo", key, value);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		try {
			JSONParser parser = new JSONParser();
			JSONObject servicejson = (JSONObject) parser.parse(empInfo);
			String employeename = (String) servicejson.get("EmployeeName");
			String designation = (String) servicejson.get("Designation");
			String spokename = (String) servicejson.get("SpokeName");
			String employeecode = (String) servicejson.get("EmployeeCode");
			long departmentid = (Long) servicejson.get("DepartmentID");
			itassetsmodal.put("empcode", emp_code);
			itassetsmodal.put("empname", employeename);
			itassetsmodal.put("department", departmentid);
			itassetsmodal.put("designation", designation);
			itassetsmodal.put("location", spokename);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return itassetsmodal;
	}

	@RequestMapping(value = "/getitassets", method = RequestMethod.GET)
	@ResponseBody

	public JSONObject getjsondata(HttpServletRequest request) {

		String emp_code = request.getParameter("employee_code");

		JSONObject jsonObject = new JSONObject();

		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();

		List<String> listvalue = new ArrayList<String>();

		listvalue.add("laptop");
		listvalue.add("datacard");
		listvalue.add("pendrive");
		listvalue.add("other it assets");

		for (String str : listvalue) {
			JSONObject itassetsmodal = new JSONObject();
			itassetsmodal.put("name", str);
			arrlist.add(itassetsmodal);
		}
		jsonObject.put("itassets", arrlist);

		return jsonObject;
	}

	@RequestMapping(value = "/insertitassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertitassets(@RequestParam("emp_assets") String assetsissued,
			@RequestParam("comments") String comments, @RequestParam("emp_code") String empcode,
			HttpServletRequest request, HttpSession session) {
		session = request.getSession();

		String managerempcode = (String) session.getAttribute("employeecode");
		String itmanagername = "";
		int department = 0;
		String empinfo = "";
		String[] key = { "empcode" };
		String[] value = { empcode };
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		try {
			empinfo = empdetails.enterPriseDataService("EVM", "empinfo", key, value);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		JSONObject insertitasserts = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject serviceparser;
		try {
			serviceparser = (JSONObject) parser.parse(empinfo);
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();
			itmanagername = (String) serviceparser.get("EmployeeName");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date today = new Date();
		TblAssetsManagement itasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		String[] assetsvalue = assetsissued.split(",");
		for (String singleItem : assetsvalue) {
			itasset.setAssetsIssue(singleItem);
			itasset.setCreatedBy("System");
			itasset.setCreatedOn(today);
			itasset.setDepartmentId(4);
			itasset.setIssuedBy("pradeep attri");
			itasset.setIssuedOn(today);
			itasset.setItemStatus(2);
			itasset.setReceivedBy("pradeep attri");
			itasset.setReceivedOn(today);
			itasset.setTblUserResignation(resignedUser);
			insertitasserts = noduesservice.submitnoduesassets(itasset);
		}
		TblNoDuesClearence noduesclearence = new TblNoDuesClearence();
		noduesclearence.setComments(comments);
		noduesclearence.setDepartmentFinalStatus(2);
		noduesclearence.setDepartmentId(4);
		noduesclearence.setTbluserresignation(resignedUser);
		insertitasserts = noduesservice.submitNoduesclearence(noduesclearence);

		return insertitasserts;
	}

	@RequestMapping(value = "/getinframodalassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getinframodalassets(HttpServletRequest request) {
		JSONObject jsoninfraassets = new JSONObject();
		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();
		List<String> listvalue = new ArrayList<String>();
		listvalue.add("ICARD");
		listvalue.add("other infra assets");
		for (String str : listvalue) {
			JSONObject infraassetsmodal = new JSONObject();
			infraassetsmodal.put("name", str);
			arrlist.add(infraassetsmodal);
		}
		jsoninfraassets.put("infraassets", arrlist);
		return jsoninfraassets;
	}

	@RequestMapping(value = "/insertinfraassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertinfraassets(@RequestParam("emp_assets") String infraasserts,
			@RequestParam("comments") String comments, @RequestParam("emp_code") String empcode,
			HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String inframanagerempcode = (String) session.getAttribute("employeecode");
		int department = 0;
		String empinfo = "";
		String[] key = { "empcode" };
		String[] value = { empcode };
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		try {
			empinfo = empdetails.enterPriseDataService("EVM", "empinfo", key, value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONObject insertitasserts = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject serviceparser;
		try {
			serviceparser = (JSONObject) parser.parse(empinfo);
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();
			String inframanager = (String) serviceparser.get("EmployeeName");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject insertasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement infraasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		String[] asserts = infraasserts.split(",");
		for (String assetssplit : asserts) {
			infraasset.setAssetsIssue(assetssplit);
			infraasset.setCreatedBy("System");
			infraasset.setCreatedOn(today);
			infraasset.setDepartmentId(5);
			infraasset.setIssuedBy("delip jha");
			infraasset.setIssuedOn(today);
			infraasset.setItemStatus(2);
			infraasset.setReceivedBy("delip jha");
			infraasset.setReceivedOn(today);
			infraasset.setTblUserResignation(resignedUser);
			insertasserts = noduesservice.submitnoduesassets(infraasset);
		}
		TblNoDuesClearence nodues = new TblNoDuesClearence();
		nodues.setComments(comments);
		nodues.setDepartmentFinalStatus(2);
		nodues.setTbluserresignation(resignedUser);
		nodues.setDepartmentId(5);
		insertasserts = noduesservice.submitNoduesclearence(nodues);

		return insertasserts;
	}

	@RequestMapping(value = "/getNoDuesAssets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getassests(HttpServletRequest request) {
		String empcode = request.getParameter("employee_code");

		/*
		 * System.out.println("employee empcode "+empcode);
		 */

		int departmentid = 2;

		JSONObject accountassets = new JSONObject();
		List<JSONObject> value = noduesservice.listassetsdetails(departmentid);

		accountassets.put("list", value);

		return accountassets;
	}

	@RequestMapping(value = "/getUploadItems", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getUploadItems() {

		JSONArray list = new JSONArray();
		// item List depends on deptId 
		int deptId=2;

		List<MstUploadItem> itemList = employeeDocumentService.getUploadItems(2);

		for (MstUploadItem mstUploadItem : itemList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", mstUploadItem.getItem());
			jsonObject.put("itemId", mstUploadItem.getUploadId());

			list.add(jsonObject);

		}

		return list;
	}


	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject upload(MultipartHttpServletRequest request, HttpServletResponse response) {

		String fileLocation = "D:/CSVFile/";
		String empId = "ss0062";
		String result = null;

		try {

			HttpSession session = request.getSession();// added by arpan for change
			// in hr approval service
			JSONArray list = new JSONArray();
			int circle_code = (Integer) session.getAttribute("circleid");
			String uploadedBy = (String) session.getAttribute("employeecode");
			logger.info("Uploading file ");
			String itemId1 = request.getParameter("uploadId");
			String empCode = request.getParameter("empCode");
			String resignId = request.getParameter("resignId");
			int id= Integer.parseInt(resignId);
			int itemId = Integer.parseInt(itemId1);
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			byte[] bytes = mpf.getBytes();
			String filename = mpf.getOriginalFilename();
			File file = new File(fileLocation + filename);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();

			logger.info("Server File Location=" + file);
			String FilePath = empCode + "/" + filename;

			String filePath = uploadDocumentFTPClient(filename,empCode,bytes);
			MstUploadItem mstUploadItem = employeeDocumentService.entityById(itemId);


			TblUserResignation resignation = resignationService.getById(id);

			TblUploadedPath uploadPath = new TblUploadedPath();

			uploadPath.setUploadedBy(uploadedBy);
			uploadPath.setEmpCode(empCode);
			uploadPath.setPath(filePath);
			uploadPath.setUploadedOn(new Date());
			uploadPath.setTblUserResignation(resignation);
			uploadPath.setMstUploadItem(mstUploadItem);
			result= employeeDocumentService.save(uploadPath);

			if(itemId==3){
				MstResignationStatus resignationStatus=resignationService.getStatus(11);			
				resignation.setMstResignationStatus(resignationStatus);
				approvalservice.updateResignationStatus(resignation);
			}


			if(itemId==5){
				MstResignationStatus resignationStatus=resignationService.getStatus(13);		
				resignation.setMstResignationStatus(resignationStatus);
				approvalservice.updateResignationStatus(resignation);
			}

		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/getDownloadItem", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getDownloadItem(HttpServletRequest request, HttpServletResponse response) {

		String fileLocation = "D:/CSVFile/";
		String empcode = "ss0062";
		String result = null;

		String ftpHost = "122.15.90.140";
		String username = "administrator";
		String password = "softage@tchad";

		JSONArray uploadList = new JSONArray();

		try {

			List<TblUploadedPath> tblUploadedPaths = employeeDocumentService.getByEmpCode(empcode);
			int countItem = 0;
			for (TblUploadedPath tblUploadedPath : tblUploadedPaths) {
				countItem++;
				JSONObject jsonObject = new JSONObject();
				MstUploadItem uploadItem = tblUploadedPath.getMstUploadItem();
				String ItemName = uploadItem.getItem();
				int itemId = uploadItem.getUploadId();

				int resignId = 0;
				TblUserResignation resignation = tblUploadedPath.getTblUserResignation();
				if (resignation != null) {
					resignId = resignation.getResignationId();
				}
				jsonObject.put("count", countItem);
				jsonObject.put("itemName", ItemName);
				jsonObject.put("resignId", resignId);
				jsonObject.put("itemId", itemId);
				uploadList.add(jsonObject);

			}

		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}

		return uploadList;

	}
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) {

		String fileLocation = "D:/CSVFile/";
		String empcode = "ss0062";
		String result = null;

		String ftpHost = "122.15.90.140";
		String username = "administrator";
		String password = "softage@tchad";
		String path = null;
		OutputStream outStream = null;
		JSONArray uploadList = new JSONArray();

		try {
			HttpSession session = request.getSession();// added by arpan for change

			String downloadBy = (String) session.getAttribute("employeecode");

			String resignId1 = request.getParameter("resignId");
			String itemId1 = request.getParameter("itemId");
			int itemId = Integer.parseInt(itemId1);
			int resignId = Integer.parseInt(resignId1);

			TblUploadedPath tblUploadedPath = employeeDocumentService.getByResignId(resignId, itemId);
			if (tblUploadedPath != null) {
				path = tblUploadedPath.getPath();
			}
			String[] paths = path.split("/");
			String finalPath = "";
			String filename = paths[paths.length - 1];

			for (int i = 0; i < paths.length - 1; i++) {
				finalPath = finalPath + paths[i];
				finalPath = finalPath + "/";

			}

			System.out.println(finalPath);

			byte[] bytes = downloadDocumentFTPClient(finalPath, filename);

			tblUploadedPath.setDownloadBy(downloadBy);
			tblUploadedPath.setDownloadOn(new Date());
			String result1=employeeDocumentService.update(tblUploadedPath);

			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			// response.setContentLength((int) savePath.length());

			// forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", filename);
			response.setHeader(headerKey, headerValue);
			outStream = response.getOutputStream();
			outStream.write(bytes);



		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		} finally {
			try {
				outStream.close();
			} catch (Exception e) {

			}

		}

		return;
	}

	public static String uploadDocumentFTPClient(String file, String empId, byte[] bytes) {

		String ftpHost = "122.15.90.140";
		String username = "administrator";
		String password = "softage@tchad";
		FileOutputStream fos = null;
		String ftpPath = "";
		FTPClient ftpClient = new FTPClient();

		try {
			// InputStream inputStream=new FileInputStream("");

			ftpClient.connect(ftpHost);
			boolean login = ftpClient.login(username, password);
			if (login) {
				logger.info("Successfully Connected to FTP Server");
			} else {
				System.out.println("Unable to Connected to Server..... ");
			}

			// FTPFile[] files =ftpClient.listDirectories(ftpPath);
			if (!ftpClient.changeWorkingDirectory("HRMS")) {
				boolean result = ftpClient.makeDirectory("HRMS");
				ftpClient.changeWorkingDirectory("HRMS");
				System.out.println(result);

			}
			if (!ftpClient.changeWorkingDirectory("Documents")) {
				boolean result = ftpClient.makeDirectory("Documents");
				ftpClient.changeWorkingDirectory("Documents");
				System.out.println(result);

			}
			ftpPath = "HRMS/Documents";
			if (!ftpClient.changeWorkingDirectory(empId)) {
				boolean result = ftpClient.makeDirectory(empId);
				ftpClient.changeWorkingDirectory(empId);
				System.out.println(result);

			}
			ftpPath = ftpPath + "/" + empId + "/" + file;
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// ftpClient.changeWorkingDirectory("fptPath");

			System.out.println("Start uploading second file");
			OutputStream outputStream = ftpClient.storeFileStream(file);
			byte[] bytesIn = new byte[4096];
			int read = 0;

			outputStream.write(bytes);
			// inputStream.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				ftpClient.disconnect();

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}

		return ftpPath;

	}

	public static byte[] downloadDocumentFTPClient(String filePath, String filename) {

		String ftpHost = "122.15.90.140";
		String username = "administrator";
		String password = "softage@tchad";
		FileOutputStream fos = null;
		String ftpPath = "";
		FTPClient ftpClient = new FTPClient();
		byte[] bytes = null;
		InputStream inputStream = null;

		try {
			// InputStream inputStream=new FileInputStream("");

			ftpClient.connect(ftpHost);
			boolean login = ftpClient.login(username, password);
			if (login) {
				logger.info("Successfully Connected to FTP Server");
			} else {
				System.out.println("Unable to Connected to Server..... ");
			}

			// FTPFile[] files =ftpClient.listDirectories(ftpPath);

			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			ftpClient.changeWorkingDirectory(filePath);

			System.out.println("Start uploading second file");
			inputStream = ftpClient.retrieveFileStream(filename);
			int read = inputStream.read();
			bytes = IOUtils.toByteArray(inputStream);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				ftpClient.disconnect();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("", e);
			}
		}

		return bytes;

	}

	@RequestMapping(value = "/getEmpUploadList", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getEmpUploadList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();// added by arpan for change
		// in hr approval service
		JSONArray list = new JSONArray();
		int circle_code = (Integer) session.getAttribute("circleid");
		String empcode = (String) session.getAttribute("employeecode");
		int deptId =1 ;													
		int status = 0;													
	
		if(deptId==1){

			status = 9;
		}

		if(deptId==2){

			status = 11;
		}



		JSONObject resignations = null;

		try {
			resignations=resignationService.getResignationModelByCircleID(circle_code);
			//resignations = resignationService.getHrApprovalInitService(empcode, status, circle_code);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resignations;
	}
	@RequestMapping(value = "/insertaccountassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertaccountassets(@RequestParam("emp_assets") String assertsreceived,
			@RequestParam("accounts_comments") String comments, @RequestParam("emp_code") String empcode,
			HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String accountmanagerempcode = (String) session.getAttribute("employeecode");
		int department = 0;
		String empinfo = "";
		String[] key = { "empcode" };
		String[] value = { empcode };
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		try {
			empinfo = empdetails.enterPriseDataService("EVM", "empinfo", key, value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONObject insertitasserts = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject serviceparser;
		try {
			serviceparser = (JSONObject) parser.parse(empinfo);
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();
			String accountmanager = (String) serviceparser.get("EmployeeName");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JSONObject insertasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement accountasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		String[] asserts = assertsreceived.split(",");

		for (String assetssplit : asserts) {
			accountasset.setAssetsIssue(assetssplit);
			accountasset.setCreatedBy("system");
			accountasset.setCreatedOn(today);
			accountasset.setDepartmentId(1);
			accountasset.setIssuedBy("ck jha");
			accountasset.setIssuedOn(today);
			accountasset.setItemStatus(2);
			accountasset.setReceivedBy("ck jha");
			accountasset.setReceivedOn(today);
			accountasset.setTblUserResignation(resignedUser);
			insertasserts = noduesservice.submitnoduesassets(accountasset);
		}
		TblNoDuesClearence nodues = new TblNoDuesClearence();
		nodues.setComments(comments);
		nodues.setDepartmentFinalStatus(2);
		nodues.setTbluserresignation(resignedUser);
		nodues.setDepartmentId(1);
		insertasserts = noduesservice.submitNoduesclearence(nodues);
         return insertasserts;
	}

	@RequestMapping(value = "/rejectempassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject rejectaccountassets(HttpServletRequest request, HttpSession session,
			                              @RequestParam("received_assets") String receivedassets,
			                              @RequestParam("not_received") String notreceivedassets,  
			                              @RequestParam("comments") String comments,
			                              @RequestParam("emp_code") String empcode,
			                              @RequestParam("final_status") int status) {
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		String managercode="";
		session = request.getSession();
		// Department Manager Information
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String[] departmentmanagerkey={"empcode"};
		String[] departmentmanagervalue={departmentmanagerempcode};
		try {
			String departmentempInfo = empdetails.enterPriseDataService("EVM", "empInfo", departmentmanagerkey, departmentmanagervalue);
			JSONParser departmentmanagerparse=new JSONParser();
			try {
				JSONObject managerservicejson=(JSONObject) departmentmanagerparse.parse(departmentempInfo);
				String departmentmanagername=(String) managerservicejson.get("EmployeeName");
				String departmentmanageremail=(String) managerservicejson.get("CompanyEmail");
				String departmentmanagerdepartmentid=(String) managerservicejson.get("DepartmentID");
		} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		// Rejected Employee Information 
		String[] empkey = {"empcode"};
		String[] empvalue = {empcode};
		try {
			String empInfo = empdetails.enterPriseDataService("EVM", "empInfo", empkey, empvalue);
			JSONParser parser = new JSONParser();
			try {
				JSONObject servicejson = (JSONObject) parser.parse(empInfo);
				 managercode = (String) servicejson.get("ManagerCode");
				String empemail = (String) servicejson.get("CompanyEmail");
				String manageremail=(String) servicejson.get("ManagerEmail");
				long empdepartmentid = (Long) servicejson.get("DepartmentID");
				
				//information based on officecode
				String[] officekeys = {"OFFICECODE"};
				String[] officevalues = {"CORGUR001"};
				String NODUESOWNERS = empdetails.enterPriseDataService("EVM", "NODUESOWNERS", officekeys, officevalues);
				JSONParser parseemp = new JSONParser();
				JSONObject jsonparse = (JSONObject) parseemp.parse(NODUESOWNERS);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e1){
           e1.printStackTrace();
		}          /* String[] managerkey1 = {"empcode"};
					String[] managervalue1 = {"s00002"};
					String managerInfo ="";
					try {
						managerInfo = empdetails.enterPriseDataService("EVM", "empInfo", managerkey1, managervalue1);
					} catch (RemoteException e) {
					e.printStackTrace();
					}
					JSONParser managerparser=new JSONParser();
					JSONObject managerservicejson;
					try {
						managerservicejson = (JSONObject)managerparser.parse(managerInfo);
						String rmmanageremail = (String) managerservicejson.get("CompanyEmail");
						} catch (ParseException e) {
					e.printStackTrace();
					}*/
		Date today = new Date();
		JSONObject rejectjson = new JSONObject();
		TblAssetsManagement receiveditem = new TblAssetsManagement();
		String[] assertsreceived = receivedassets.split(",");
		String[] notreceived = notreceivedassets.split(",");
		for (String remaingassets : notreceived) {
			receiveditem.setAssetsIssue(remaingassets);
			receiveditem.setCreatedBy("system");
			receiveditem.setCreatedOn(today);
			receiveditem.setDepartmentId(4);
			receiveditem.setIssuedBy("pradeep attri");
			receiveditem.setIssuedOn(today);
			receiveditem.setItemStatus(1);
			receiveditem.setReceivedBy("");
			receiveditem.setReceivedOn(today);

			TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
			receiveditem.setTblUserResignation(resignedUser);
			rejectjson = noduesservice.submitnoduesassets(receiveditem);
		}

		if (receivedassets == null | receivedassets.length() == 0) {
			System.out.println("value not found...");
		} else {

			for (String assetsitem : assertsreceived) {
				receiveditem.setAssetsIssue(assetsitem);
				receiveditem.setCreatedBy("system");
				receiveditem.setCreatedOn(today);
				receiveditem.setDepartmentId(4);
				receiveditem.setIssuedBy("pradeep attri");
				receiveditem.setIssuedOn(today);
				receiveditem.setItemStatus(2);
				receiveditem.setReceivedOn(today);
				receiveditem.setReceivedBy("pradeep attri");
				TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
				receiveditem.setTblUserResignation(resignedUser);
				rejectjson = noduesservice.submitnoduesassets(receiveditem);
}
		}
		TblNoDuesClearence clearence = new TblNoDuesClearence();
		clearence.setComments(comments);
		clearence.setDepartmentFinalStatus(status);
		clearence.setDepartmentId(4);
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		clearence.setTbluserresignation(resignedUser);
		rejectjson = noduesservice.submitNoduesclearence(clearence);
		/*
		 * mailService.sendEmail(employeeemail, departmentmanageremail,
		 * "NO DUES CLEARENCES", comments);
		 */
		return rejectjson;
	}

	@RequestMapping(value = "/inserthrassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject inserthrassets(@RequestParam("emp_assets") String hrassets,
			@RequestParam("hr_comments") String comments, @RequestParam("emp_code") String empcode,
			HttpServletRequest request, HttpSession session) {

		session = request.getSession();
		String hrmanagerempcode = (String) session.getAttribute("employeecode");
		int department = 0;
		String empinfo = "";
		String[] key = { "empcode" };
		String[] value = { empcode };
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		try {
			empinfo = empdetails.enterPriseDataService("EVM", "empinfo", key, value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONObject insertitasserts = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject serviceparser;
		try {
			serviceparser = (JSONObject) parser.parse(empinfo);
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();
			String hrmanager = (String) serviceparser.get("EmployeeName");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
        JSONObject inserthrasserts = new JSONObject();
        Date today = new Date();
        TblAssetsManagement hrasset = new TblAssetsManagement();
		String[] asserts = hrassets.split(",");
		for (String assetssplit : asserts) {
			hrasset.setAssetsIssue(assetssplit);
			hrasset.setCreatedBy("system");
			hrasset.setCreatedOn(today);
			hrasset.setDepartmentId(2);
			hrasset.setIssuedBy("thomas verges");
			hrasset.setIssuedOn(today);
			hrasset.setItemStatus(2);
			hrasset.setReceivedBy("thomas verges");
			hrasset.setReceivedOn(today);
			hrasset.setTblUserResignation(resignedUser);
			inserthrasserts = noduesservice.submitnoduesassets(hrasset);
		}
		TblNoDuesClearence nodues = new TblNoDuesClearence();
		nodues.setDepartmentFinalStatus(2);
		nodues.setComments(comments);
		nodues.setTbluserresignation(resignedUser);
		nodues.setDepartmentId(2);
		inserthrasserts = noduesservice.submitNoduesclearence(nodues);
		MstResignationStatus status_mast = resignationService.getStatus(7);
		resignedUser.setMstResignationStatus(status_mast);
		approvalservice.updateResignationStatus(resignedUser);

		return inserthrasserts;
	}

	@RequestMapping(value = "/insertrmassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertrmassets(@RequestParam("emp_assets") String rmassets,
			@RequestParam("comments") String comments, @RequestParam("emp_code") String empcode,
			HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String rmmanagerempcode = (String) session.getAttribute("employeecode");
		int department = 0;
		String empinfo = "";
		String[] key = { "empcode" };
		String[] value = { empcode };
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		try {
			empinfo = empdetails.enterPriseDataService("EVM", "empinfo", key, value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		JSONObject insertitasserts = new JSONObject();
		JSONParser parser = new JSONParser();
		JSONObject serviceparser;
		try {
			serviceparser = (JSONObject) parser.parse(empinfo);
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();
			String rmmanager = (String) serviceparser.get("EmployeeName");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JSONObject insertrmasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement rmasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		String[] asserts = rmassets.split(",");
		for (String assetssplit : asserts) {
			rmasset.setAssetsIssue(assetssplit);
			rmasset.setCreatedBy("system");
			rmasset.setCreatedOn(today);
			rmasset.setDepartmentId(3);
			rmasset.setIssuedBy("sunil raizada");
			rmasset.setIssuedOn(today);
			rmasset.setItemStatus(2);
			rmasset.setReceivedBy("sunil raizada");
			rmasset.setReceivedOn(today);
			rmasset.setTblUserResignation(resignedUser);
			insertrmasserts = noduesservice.submitnoduesassets(rmasset);
		}
		TblNoDuesClearence nodues = new TblNoDuesClearence();
		nodues.setDepartmentFinalStatus(2);
		nodues.setComments(comments);
		nodues.setTbluserresignation(resignedUser);
		nodues.setDepartmentId(3);
		insertrmasserts = noduesservice.submitNoduesclearence(nodues);
		return insertrmasserts;
	}

	@RequestMapping(value = "/gethrfeedbackquestions", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject gethrfeedbackquestion() {
		int roleid = 8;
		int stageid = 5;
		JSONObject feedbackjson = new JSONObject();
		try {
			List<JSONObject> listhrquestion = exitinterviewservice.listHrQuestion(roleid, stageid);
			feedbackjson.put("hrquestionslist", listhrquestion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feedbackjson;

	}

	@RequestMapping(value = "/inserthrfeedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject inserthrfeedback(@RequestParam("hr_feedback") String feedback,
			@RequestParam("emp_code") String empcode, HttpSession session, HttpServletRequest request) {
		String empname = "";
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		session = request.getSession();
		String hrempcode = (String) session.getAttribute("employeecode");
		try {
			empname = empdetails.getUserDetail(hrempcode).getFirstName();

		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		JSONObject hranswers = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(feedback);
			List<JSONObject> listAnswers = (List<JSONObject>) json.get("data");
			TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 7);
			for (JSONObject hranswer : listAnswers) {
				Long serialid = (Long) hranswer.get("qid");
				int sid = serialid.intValue();
				String ans = (String) hranswer.get("value");
				TblFeedbacks feedbackhr = new TblFeedbacks();
				feedbackhr.setAnsText(ans);
				feedbackhr.setFeedbackBy(empname);
				feedbackhr.setFeedbackFrom(hrempcode);
				MstQuestions question = approvalservice.getRmFeedbackQuestionService(sid);
				feedbackhr.setMstQuestions(question);
				feedbackhr.setTblUserResignation(resignedUser);
				hranswers = exitinterviewservice.submithrfeedback(feedbackhr);
			}
			MstResignationStatus status_mast = resignationService.getStatus(9);
			resignedUser.setMstResignationStatus(status_mast);
			approvalservice.updateResignationStatus(resignedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return hranswers;

	}

	@RequestMapping(value = "/employeeQuery", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject employeeQuery(HttpServletRequest request, HttpSession session) {
		String empcode = "ss0062";
		String empName = "Rohit";
		String rmEmpCode = "ss0078";
		try {
			session = request.getSession();
			empcode = (String) session.getAttribute("employeecode");
			String deptId = request.getParameter("deptId");
			int departmentId = Integer.parseInt(deptId);
			// get Query Assigned Manager Employee Code
			TblUserResignation tblUserResignation = resignationService.getResignationUserService(empcode, 0);
			String assingToEmpcode = "ss0078";
			String queryText = request.getParameter("quertext");

			TblExEmployeeQuery employeeQuery = new TblExEmployeeQuery();

			employeeQuery.setCreatedBy(empName);
			employeeQuery.setCreatedOn(new Date());
			employeeQuery.setDepartmentId(departmentId);
			employeeQuery.setExEmpCode(empcode);
			employeeQuery.setTblUserResignation(tblUserResignation);
			employeeQuery.setQueryText(queryText);
			employeeQuery.setQueryAssigned(assingToEmpcode);

			int id = queryService.save(employeeQuery);

			if (id > 0) {
				TblExEmployeeQuery employeeQuery1 = queryService.getById(id);
				TblExEmpCommunication empCommunication = new TblExEmpCommunication();
				empCommunication.setTblExEmployeeQuery(employeeQuery1);
				empCommunication.setQueryFrom(empcode);
				empCommunication.setCreatedOn(new Date());
				empCommunication.setQueryText(queryText);

				String result1 = queryService.save(empCommunication);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/saveQueryManger", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray saveQueryManger(HttpServletRequest request, HttpSession session) {

		String empcode = "ss0062";
		String empName = "Rohit";
		String rmEmpCode = "";
		JSONArray array = new JSONArray();

		try {

			session = request.getSession();
			rmEmpCode = (String) session.getAttribute("employeecode");
			String queryId = request.getParameter("queryId");
			String replyText = request.getParameter("queryReply");

			int id = Integer.parseInt(queryId);

			TblExEmployeeQuery employeeQuery = queryService.getById(id);

			TblExEmpCommunication empCommunication = new TblExEmpCommunication();
			empCommunication.setTblExEmployeeQuery(employeeQuery);
			empCommunication.setQueryText(replyText);
			empCommunication.setCreatedOn(new Date());
			empCommunication.setQueryFrom(rmEmpCode);

			String result = queryService.save(empCommunication);

			System.out.println("Result " + result);

			// get Query Assigned Manager Employee Code
			// TblUserResignation tblUserResignation
			// =resignationService.getResignationUserService(empcode, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	@RequestMapping(value = "/getMessages", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getMessages(HttpServletRequest request, HttpSession session) {

		String empcode = "ss0062";
		String empName = "Rohit";
		String rmEmpCode = "";
		JSONArray array = new JSONArray();

		try {

			session = request.getSession();
			rmEmpCode = (String) session.getAttribute("employeecode");
			String queryId = request.getParameter("queryId");
			int id = Integer.parseInt(queryId);

			List<TblExEmpCommunication> tblExEmpCommunications = queryService.getByQueryId(id);
			int count = 0;

			for (TblExEmpCommunication tblExEmpCommunication : tblExEmpCommunications) {
				count++;
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("queryId", id);
				String queryText = tblExEmpCommunication.getQueryText();
				String queryFrom = tblExEmpCommunication.getQueryFrom();
				jsonObject.put("query", queryText);
				jsonObject.put("massageFrom", queryFrom);
				if (count % 2 == 0) {
					jsonObject.put("classType", "info");
				} else {
					jsonObject.put("classType", "success");
				}

				array.add(jsonObject);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	@RequestMapping(value = "/getDepartments", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getDepartment(HttpServletRequest request, HttpSession session) {
		JSONArray jsonArrey = new JSONArray();

		try {

			List<MstDepartment> listDepartment = queryService.getDepartmentList();

			for (MstDepartment mstDepartment : listDepartment) {
				JSONObject jsonObject = new JSONObject();
				int deptId = mstDepartment.getDepart_id();
				String deptName = mstDepartment.getDepartment_name();
				jsonObject.put("deptId", deptId);
				jsonObject.put("name", deptName);
				jsonArrey.add(jsonObject);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArrey;
	}

	@RequestMapping(value = "/getRMApprovalInitFromService", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getApprovalIntiFromService(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		JSONObject jsonApproval = new JSONObject();
		String emp_code = (String) session.getAttribute("employeecode");
		List<String> rm_list = resignationService.getAllResignedUserRMs();
		Set<String> to_show = new HashSet<String>();
		for (String rm : rm_list) {
			String[] keys={"empcode"};
			String[] values={emp_code};
			//String rm_rm1 = "ss0053";
			//String rm_rm2 = "ss0050";
			ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
			try {
				String empinfo=emp.enterPriseDataService("EVM", "EmpInfo", keys, values);
				JSONParser parser=new JSONParser();
				JSONObject serviceJson=(JSONObject)parser.parse(empinfo);
				String rm_rm1=(String)serviceJson.get("ManagerCode");
				String[] rmkey={"empcode"};
				String[] rmvalues={rm_rm1};
				String rm_empinfo=emp.enterPriseDataService("EVM", "EmpInfo", rmkey, rmvalues);
				JSONObject rm_rmJson=(JSONObject)parser.parse(rm_empinfo);
				String rm_rm2=(String)rm_rmJson.get("ManagerCode");
				if ((rm_rm1 == null || rm_rm1 == "") && emp_code.equals(rm)) {
					to_show.add(rm);
					// continue;
				} else if ((rm_rm1 == null || rm_rm1 == "") && !emp_code.equals(rm)) {
					continue;
				} else if (rm_rm2 == null || rm_rm2 == "") {
					if (emp_code.equals(rm)) {
						to_show.add(rm);
					} else if (emp_code.equals(rm_rm1)) {
						to_show.add(rm);
					} else {
						continue;
					}
				} else {

					if (emp_code.equals(rm)) {
						to_show.add(rm);
					} else if (emp_code.equals(rm_rm1)) {
						to_show.add(rm);
					} else if (emp_code.equals(rm_rm2)) {
						to_show.add(rm);
					} else {
						continue;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		jsonApproval = resignationService.getAllResignedUsers(to_show);
		return jsonApproval;

	}

	@RequestMapping(value = "/getHrApprovalFromService", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getHRApprovalInitFromService(HttpServletRequest request, HttpSession session) {
		JSONObject hrapprovaljson = new JSONObject();
		session = request.getSession();
		String emp_code = (String) session.getAttribute("employeecode");
		List<String> resignedUsersHrList = resignationService.getAllResignedUsersHrs();
		Set<String> hr_to_show = new HashSet<String>();
		for (String hr : resignedUsersHrList) {
			//String hr_hr1 = "ss0073";
			//String hr_hr2 = "ss0029";
			ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
			String[] key={"empcode"};
			String[] values={emp_code};
			try {
				String empinfo=emp.enterPriseDataService("EVM", "EmpInfo", key, values);
				JSONParser parser=new JSONParser();
				JSONObject serviceJson=(JSONObject)parser.parse(empinfo);
				String hr_hr1=(String)serviceJson.get("ManagerCode");
				String[] hr_key={"empcode"};
				String[] hr_values={hr_hr1};
				String hr_empinfo=emp.enterPriseDataService("EVM", "EmpInfo", hr_key, hr_values);
				JSONObject hr_json=(JSONObject)parser.parse(hr_empinfo);
				String hr_hr2=(String)hr_json.get("ManagerCode");
				if ((hr_hr1 == null || hr_hr1 == "") && emp_code.equals(hr)) {
					hr_to_show.add(hr);
				} else if ((hr_hr1 == null && hr_hr1 == "") && !emp_code.equals(hr)) {
					continue;
				} else if (hr_hr2 == null && hr_hr2 == "") {
					if (emp_code.equals(hr)) {
						hr_to_show.add(hr);
					} else if (emp_code.equals(hr_hr1)) {
						hr_to_show.add(hr);
					}
				} else {
					if (emp_code.equals(hr)) {
						hr_to_show.add(hr);
					} else if (emp_code.equals(hr_hr1)) {
						hr_to_show.add(hr);
					} else if (emp_code.equals(hr_hr2)) {
						hr_to_show.add(hr);
					} else {
						continue;
					}
				}
			}
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hrapprovaljson = resignationService.getAllResignedUsersHR(hr_to_show);
		return hrapprovaljson;
	}

	@RequestMapping(value = "/getEmpQueryList", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getEmpQueryList(HttpServletRequest request, HttpSession session) {
		JSONArray jsonArrey = new JSONArray();
		String empcode = "";
		try {
			session = request.getSession();
			empcode = (String) session.getAttribute("employeecode");
			List<TblExEmployeeQuery> listDepartment = queryService.getQueryList(empcode);
			int count = 0;

			for (TblExEmployeeQuery tblExEmployeeQuery : listDepartment) {
				String deptName = "";

				JSONObject jsonObject = new JSONObject();
				int queryId = tblExEmployeeQuery.getQueryId();
				int deptId = tblExEmployeeQuery.getDepartmentId();
				MstDepartment department = queryService.getDepartmentById(deptId);
				if (department != null) {
					deptName = department.getDepartment_name();
				}

				count++;
				jsonObject.put("id", count);
				jsonObject.put("department", deptName);
				jsonObject.put("queryId", tblExEmployeeQuery.getQueryId());
				jsonObject.put("queryText", tblExEmployeeQuery.getQueryText());
				jsonObject.put("date", tblExEmployeeQuery.getCreatedOn().toString());
				jsonArrey.add(jsonObject);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArrey;
	}

	@RequestMapping(value = "/getQueryList", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getQueryList(HttpServletRequest request, HttpSession session) {
		JSONArray jsonArrey = new JSONArray();
		String empcode = "";
		try {

			session = request.getSession();
			empcode = (String) session.getAttribute("employeecode");

			List<TblExEmployeeQuery> listDepartment = queryService.getQueryList(empcode);
			int count = 0;

			for (TblExEmployeeQuery tblExEmployeeQuery : listDepartment) {
				String deptName = "";

				JSONObject jsonObject = new JSONObject();
				int queryId = tblExEmployeeQuery.getQueryId();
				int deptId = tblExEmployeeQuery.getDepartmentId();
				MstDepartment department = queryService.getDepartmentById(deptId);
				if (department != null) {
					deptName = department.getDepartment_name();
				}

				count++;
				jsonObject.put("id", count);
				jsonObject.put("department", deptName);
				jsonObject.put("queryId", tblExEmployeeQuery.getQueryId());
				jsonObject.put("queryText", tblExEmployeeQuery.getQueryText());
				jsonObject.put("date", tblExEmployeeQuery.getCreatedOn().toString());
				jsonArrey.add(jsonObject);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArrey;
	}

	/*
	 * @RequestMapping(value = "/getQueryList", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public JSONArray getQueryList(HttpServletRequest
	 * request,HttpSession session) { JSONArray jsonArrey=new JSONArray();
	 * String empcode=""; try{
	 * 
	 * session=request.getSession(); empcode=(String)
	 * session.getAttribute("employeecode");
	 * 
	 * 
	 * List<TblExEmployeeQuery>
	 * listDepartment=queryService.getQueryList(empcode); int count=0;
	 * 
	 * for (TblExEmployeeQuery tblExEmployeeQuery : listDepartment) {
	 * 
	 * JSONObject jsonObject=new JSONObject(); int queryId=
	 * tblExEmployeeQuery.getQueryId(); count++; jsonObject.put("id", count);
	 * jsonObject.put("queryfrom",tblExEmployeeQuery.getExEmpCode());
	 * jsonObject.put("name", tblExEmployeeQuery.getCreatedBy());
	 * jsonObject.put("queryId",tblExEmployeeQuery.getQueryId());
	 * jsonObject.put("queryText",tblExEmployeeQuery.getQueryText());
	 * jsonObject.put("queryDate",tblExEmployeeQuery.getCreatedOn().toString());
	 * jsonArrey.add(jsonObject);
	 * 
	 * 
	 * } } catch (Exception e) { >>>>>>> 8d68558 commit for queryChat
	 * e.printStackTrace(); } return jsonArrey; }
	 */

	@RequestMapping(value = "/empfeedback", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getempfeedback(HttpSession session, HttpServletRequest request) {
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		int stageid = 3;
		String empinfo = "";
		String employeename = "";
		String designation = "";
		String spokename = "";
		int department = 0;
		String[] key = { "empcode" };
		String[] value = { empcode };
		try {
			empinfo = empdetails.enterPriseDataService("EVM", "empinfo", key, value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			JSONParser parser = new JSONParser();
			JSONObject serviceparser = (JSONObject) parser.parse(empinfo);
			employeename = (String) serviceparser.get("EmployeeName");
			designation = (String) serviceparser.get("Designation");
			spokename = (String) serviceparser.get("SpokeName");
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject empfeedback = new JSONObject();
		try {
			empfeedback.put("empcode", empcode);
			empfeedback.put("empname", employeename);
			empfeedback.put("department", department);
			empfeedback.put("designation", designation);
			empfeedback.put("location", spokename);
			List<JSONObject> listempquestion = exitinterviewservice.listempQuestion(stageid);
			empfeedback.put("empfeedbackquestion", listempquestion);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return empfeedback;
	}

	@RequestMapping(value = "/insertempfeedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertempfeedback(@RequestParam("emp_feedback") String feedback, HttpSession session,
			HttpServletRequest request) {
		String empname = "";
		int stageid = 3;
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		session = request.getSession();
		String userempcode = (String) session.getAttribute("employeecode");
		try {
			empname = empdetails.getUserDetail(userempcode).getFirstName();

		} catch (RemoteException e1) {

			e1.printStackTrace();
		}
		JSONObject empanswers = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(feedback);
			List<JSONObject> listAnswers = (List<JSONObject>) json.get("data");
			for (JSONObject hranswer : listAnswers) {
				Long serialid = (Long) hranswer.get("qid");
				int sid = serialid.intValue();
				String ans = (String) hranswer.get("value");
				TblFeedbacks feedbackemp = new TblFeedbacks();
				feedbackemp.setAnsText(ans);
				feedbackemp.setFeedbackBy(empname);
				feedbackemp.setFeedbackFrom(userempcode);
				feedbackemp.setStageId(stageid);
				TblUserResignation resignedUser = resignationService.getResignationUserService(userempcode, 7);
				MstQuestions question = approvalservice.getRmFeedbackQuestionService(sid);
				feedbackemp.setMstQuestions(question);
				feedbackemp.setTblUserResignation(resignedUser);
				empanswers = exitinterviewservice.submithrfeedback(feedbackemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return empanswers;
	}

	@RequestMapping(value = "/empratingfeedback", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getempratingfeedback(HttpSession session, HttpServletRequest request) {
		session = request.getSession();
		String userempcode = (String) session.getAttribute("employeecode");
		int roleid = (Integer) session.getAttribute("roleid");
		int stageid = 4;// stage of application employee feedback
		JSONObject empratingfeedback = new JSONObject();
		try {
			List<JSONObject> listempquestion = exitinterviewservice.listempQuestion(stageid);
			empratingfeedback.put("empratingfeedbackquestion", listempquestion);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return empratingfeedback;
	}

	@RequestMapping(value = "/insertempratingfeedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertempratingfeedback(@RequestParam("emprating_feedback") String feedback, HttpSession session,
			HttpServletRequest request) {
		String empname = "";
		int stageid = 4;
		ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();

		session = request.getSession();
		String userempcode = (String) session.getAttribute("employeecode");
		try {
			empname = empdetails.getUserDetail(userempcode).getFirstName();

		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		JSONObject empratinganswers = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(feedback);
			List<JSONObject> listAnswers = (List<JSONObject>) json.get("data");
			for (JSONObject hranswer : listAnswers) {
				Long serialid = (Long) hranswer.get("qid");
				int sid = serialid.intValue();
				String ans = (String) hranswer.get("value");
				String finalans = ans.substring(3);
				TblFeedbacks feedbackemprating = new TblFeedbacks();
				feedbackemprating.setAnsText(finalans);
				feedbackemprating.setFeedbackBy(empname);
				feedbackemprating.setStageId(stageid);
				feedbackemprating.setFeedbackFrom(userempcode);
				TblUserResignation resignedUser = resignationService.getResignationUserService(userempcode, 7);
				MstQuestions question = approvalservice.getRmFeedbackQuestionService(sid);
				feedbackemprating.setMstQuestions(question);
				feedbackemprating.setTblUserResignation(resignedUser);
				empratinganswers = exitinterviewservice.submithrfeedback(feedbackemprating);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return empratinganswers;

	}

	@RequestMapping(value = "/exemployee", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getexempregister(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		String userid = request.getParameter("emp_code");
		String email = request.getParameter("emp_email");
		String password = request.getParameter("emp_password");
		JSONObject exempregister = new JSONObject();
		try {
			TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 9);
			/* TblUserResignation register=new TblUserResignation(); */
			resignedUser.setExEmpEmail(email);
			resignedUser.setExEmpPassword(password);
			resignedUser.setExEmpUserid(userid);
			/* register.setComments(" ex employee id"); */
			approvalservice.updateResignationStatus(resignedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
return exempregister;
	}

	@RequestMapping(value = "/noduesstatus", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getothernodues(HttpServletRequest request) {
		JSONObject empstatus = new JSONObject();
		String empcode = request.getParameter("employeecode");
		TblUserResignation resignationbean = resignationService.getResignationUserService(empcode, 5);
		int resignationId = resignationbean.getResignationId();
		empstatus = noduesservice.getNoDuesPendingStatus(resignationId);
		return empstatus;
	}

	@RequestMapping(value = "/employeefeedbackstatus", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getemployeefeedbackstatus(HttpServletRequest request) {
		JSONObject employeefeedbackstatus = new JSONObject();
		String empcode = request.getParameter("employeecode");
		TblUserResignation resignationbean = resignationService.getResignationUserService(empcode, 7);
		int resignationId = resignationbean.getResignationId();
		employeefeedbackstatus = exitinterviewservice.listempfeedbackstatus(resignationId);

		return employeefeedbackstatus;
	}
}