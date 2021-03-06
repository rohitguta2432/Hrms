package com.softage.hrms.controller;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblExEmpCommunication;
import com.softage.hrms.model.TblExEmployeeQuery;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.model.TblNoDuesClearence;
import com.softage.hrms.model.TblResetPassword;
import com.softage.hrms.model.TblUploadedPath;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.sconnect.service.SconnectUtil;
import com.softage.hrms.service.ApprovalService;
import com.softage.hrms.service.EmployeeDocumentService;
import com.softage.hrms.service.ExEmployeeService;
import com.softage.hrms.service.ExitInterviewService;
import com.softage.hrms.service.NoDuesService;
import com.softage.hrms.service.PageService;
import com.softage.hrms.service.QueryService;
import com.softage.hrms.service.ResignationService;
import com.softage.hrms.service.RestServiceCall;
import com.softage.hrms.service.SearchService;
import com.softage.hrms.service.impl.MailServiceImpl;

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
	@Autowired
	private ExEmployeeService exemployeeservice;
	@Autowired
	private SearchService searchService;

	@Autowired
	private RestServiceCall restService;

	SconnectUtil sconnct = new SconnectUtil();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model, HttpSession session) {
		logger.info("Welcome to the home page");
		String first_Name = (String) request.getParameter("firstName");
		String employee_code = (String) request.getParameter("emp_code");
		int roleID = Integer.parseInt(request.getParameter("role_id"));
		int userID = Integer.parseInt(request.getParameter("user_id"));
		String officeCode = (String) request.getParameter("ReportingOfficeCode");
		session = request.getSession();
		session.setAttribute("firstname", first_Name);
		session.setAttribute("employeecode", employee_code);
		session.setAttribute("roleid", roleID);
		session.setAttribute("userid", userID);
		session.setAttribute("officecode", officeCode);
		String noduestring = null;
		String sconnectServiceServer = restService.getServiceDetails();

		try {

			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", employee_code);
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObj = new JSONObject(jsonObject);
			JSONObject jsonEvm = (JSONObject) jsonObj.get("GetEmployeeInfoResult");
			boolean IsManager = (Boolean) jsonEvm.get("Is_Manager");

			// String assestInfo = i.enterPriseDataService("Asset", "ASSETINFO",
			// keys, assetValues);

			// noduestring = i.enterPriseDataService("EVM", "NODUESOWNERS",
			// officekeys, officevalues);
			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			noduestring = getInputOfNoDues.replace("OFFICEID", officeCode);
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			JSONObject noduesJson = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
			// System.out.println("Asset information string" + assestInfo +
			// "nodues : " + noduestring);
			/* System.out.println(jsonObject + "keys " + empInfo); */
			// JSONObject assetJson = (JSONObject) jsonParser.parse(assestInfo);
			// request.setAttribute("param1",
			// i.getUserDetail("ss0077").getRoleId());
			/* System.out.println(i.getUserDetail("ss0077").getRoleId()); */
			// model.addAttribute("emp",
			// i.getUserDetail("ss0077").getFirstName());
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>> Exception in default method" + e.getMessage());
			model.addAttribute("msg", "NULL Values");
		}
		return "home";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPages", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getTemplateLinks(HttpServletRequest request, HttpSession session) {
		JSONObject jsobj = new JSONObject();
		session = request.getSession();
		try {
			String lastLoginDate = (String) session.getAttribute("lastLoginDate");
			String empcode = (String) session.getAttribute("employeecode");
			int roleid = (Integer) session.getAttribute("roleid");
			String userRoleId = Integer.toString(roleid).trim();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currDatetime = new Date();
			String current_date = df.format(currDatetime);
			/*
			 * System.out.println("The current datetime is : " + current_date);
			 */
			// jsobj = pageService.getPagesLink(roleid);NEW METHOD TO BE
			// MADE,made below this
			jsobj = pageService.getPagesBasedOnRoleId(empcode, current_date, roleid);
			jsobj.put("lastLogin", lastLoginDate);
			jsobj.put("roleId", userRoleId);
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>> Exception in getPages in controller" + e.getMessage());
		}
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
		// ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		String[] keys = { "empcode" };
		String[] values = { empcode };
		try {
			// String empInfo = emp_prxoy.enterPriseDataService("EVM",
			// "EmpInfo", keys, values);
			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", empcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObj = new JSONObject(jsonObject);
			JSONObject serviceJson = (JSONObject) jsonObj.get("GetEmployeeInfoResult");
			/* System.out.println(empInfo); */
			int notice_time = ((Long) serviceJson.get("NoticePeriod")).intValue();
			// SERVICE TO BE USED ESF
			if (notice_time == 0) {
				notice_time = 60;
			}
			// int notice_time = 60;
			jsonReason = resignationService.resignationInitialization();

			jsonRelDate = resignationService.getReleivingDate(empcode, notice_time);
			jsob.put("ReasonJson", jsonReason);
			jsob.put("noticeTime", notice_time);
			jsob.put("reldate", jsonRelDate);
		} catch (Exception e) {
			logger.error(
					">>>>>>>>>>>>>>> Exception in initialization of resignation page in controller" + e.getMessage());
			e.printStackTrace();
		}
		return jsob;
	}

	@RequestMapping(value = "/resignation", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResignationPage(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		String office_code = (String) session.getAttribute("officecode");
		TblUserResignation resignation = new TblUserResignation();
		Date dateobj = new Date();
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		JSONObject jsonObj = new JSONObject();
		try {
			// String resignReason = request.getParameter("emp_reason");
			int reason_for_leaving = Integer.parseInt(request.getParameter("emp_reason"));
			MstReason reason_mast = resignationService.getReason(reason_for_leaving);
			String leave_Reason=reason_mast.getReason();
			MstResignationStatus status_mast = resignationService.getStatus(1);
			String remarks = request.getParameter("emp_comments");
			String noduestring = null;
			String hr_empcode = null;
			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", empcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			JSONObject empinfoJson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			noduestring = getInputOfNoDues.replace("OFFICEID", office_code);
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			JSONObject noduesJson = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
			hr_empcode = (String) noduesJson.get("HrEmpCode");
			String rm_empcode = (String) empinfoJson.get("ManagerCode");
			String empName = (String) empinfoJson.get("EmployeeName");
			int noticeperiod = ((Long) empinfoJson.get("NoticePeriod")).intValue();
			if (noticeperiod == 0) {
				noticeperiod = 60;
			}
			String submit_date = df.format(dateobj);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateobj);
			cal.add(Calendar.DATE, noticeperiod);
			Date release_Datetime = cal.getTime();
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
			// resignation.setCircleId(circleid);
			resignation.setOfficeId(office_code);
			jsonObj = resignationService.submitResignationService(resignation);
			String managerMail = (String) empinfoJson.get("ManagerEmail");
			String hrMail = (String) noduesJson.get("HrEmpEmail");
			String empMail = (String) empinfoJson.get("CompanyEmail");
			String newline = System.getProperty("line.separator");
			/*String resignMsg = "Resignation request for employee" + empcode+ "has been processed hr "
					+ "and last working day has been set to "+ finalDate +" sent by employee	";	*/
			String resignMsg ="Dear Sir,"+ newline 
					+ newline
					+"Due to " +leave_Reason+ " I will not be able  to continue in your esteemed organization and please consider"+ "("+finalDate+")"+" as my last working in the Company."+  newline
					+ newline
					+"I will return all the item of the Company and get the �No Dues� form filled/signed "
					+"from all concerned departments and submit to HR department."
					+"I will also complete the �Exit Interview� process with the HR person before "
					+"leaving the Company." + newline
					+ newline
					+"Thanks & Regards,"+ newline
					+empcode +newline
					+empName;
			String subject = "Resignation Request";
			String from = empMail;

			if (jsonObj.get("result").equals("successful")) {
				if ((org.apache.commons.lang3.StringUtils.isNotBlank(hrMail))
						&& (org.apache.commons.lang3.StringUtils.isNotBlank(empMail))
						&& (org.apache.commons.lang3.StringUtils.isNotBlank(empMail))) {
					mailService.sendEmail(managerMail, from, subject, resignMsg);
					mailService.sendEmail(hrMail, from, subject, resignMsg);
					//mailService.sendEmail(empMail, from, subject, resignMsg);
				} else {
					System.out.println("Resigned employee mail not available");
				}

			}
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>>> Exception in submitting resignation" + e.getMessage());
		}
		return jsonObj;
	}

	@RequestMapping(value = "/approvalInitialization", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getApprovalRequests(HttpServletRequest request, HttpSession session) {
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
		// ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
		JSONObject mailJson = new JSONObject();
		String empinfo = null;
		String resOfficeCode = null;
		String noduestring = null;
		JSONObject noduesJson = new JSONObject();
		String resEmpcode = null;
		String action = null;
		String rmEmpinfoString = null;
		JSONObject rmEMpinfoJson = new JSONObject();
		try {
			JSONObject answerJson = (JSONObject) parser.parse(answerList);
			List<JSONObject> listAnswers = (List<JSONObject>) answerJson.get("data");
			MstResignationStatus resignationStatus = new MstResignationStatus();
			resignationStatus = resignationService.getStatus(resignStatus);
			action = resignationStatus.getStatus();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date rmapprovaldate = new Date();
			String rmappdate = df.format(rmapprovaldate);
			Date rm_approval_date = new Date(rmappdate);
			TblUserResignation resignedUser = resignationService.getResignationUserService(feedbackon, 1);
			resignedUser.setMstResignationStatus(resignationStatus);
			resignedUser.setRmApprovalDate(rm_approval_date);
			resignedUser.setApprovedBy(empcode);
			resOfficeCode = (String) resignedUser.getOfficeId();
			resEmpcode = (String) resignedUser.getEmpCode();
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
			statusJson.put("status", "Feedback successfully submitted");
			String[] keys = { "empcode" };
			String[] values = { resEmpcode };
			String[] rmValues = { empcode };
			String[] officekeys = { "OFFICECODE" };
			String[] officevalues = { resOfficeCode };
			// empinfo = emp.enterPriseDataService("EVM", "EmpInfo", keys,
			// values);
			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", resEmpcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			mailJson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			String empName = (String) mailJson.get("EmployeeName");
			String designation = (String) mailJson.get("Designation");
			String role = (String) mailJson.get("Role");
			
			String getInputRm = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfoRm = getInputRm.replace("employeeCode", empcode);
			
			String evmDataRm = sconnct.getPostServiceData(evmServiceUrl, empInfoRm).toString();
			JSONParser jsonParserRm = new JSONParser();
			JSONObject jsonObjectRm = (JSONObject) jsonParserRm.parse(evmDataRm);
			JSONObject jsonObjEvmRm = new JSONObject(jsonObjectRm);
			JSONObject	mailJsonRm = (JSONObject) jsonObjEvmRm.get("GetEmployeeInfoResult");
		
			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			noduestring = getInputOfNoDues.replace("OFFICEID", resOfficeCode);
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			noduesJson = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
			TblUserResignation resignation = resignationService.getResignationUserService(resEmpcode, resignStatus);
			Date resignationDate=resignation.getResignationDate();
			Date relDate=resignation.getReleivingDate();
			
			// noduesJson = (JSONObject) parser.parse(noduestring);
			String manager_email = (String) mailJsonRm.get("CompanyEmail");
			String rmName = (String) mailJsonRm.get("EmployeeName");
			String hr_email = (String) noduesJson.get("HrEmpEmail");
			String emp_email = (String) mailJson.get("CompanyEmail");
			String departmentName = (String) mailJson.get("Department");
			String newline = System.getProperty("line.separator");
			String rm_message = newline+ "Name: "+empName+newline
					+"Employee Code: "+resEmpcode +newline
					+"Department: "+departmentName+newline
					+"Designation: "+designation+newline
					+"Role: "+role+newline+newline
			        +" Mr. / Ms. "+empName+","+newline
					+newline
			        +"Your resignation dated " + resignationDate + " is hereby accepted. You will be relived from "
					+ "the service of the Company from the close of working hours of ("+relDate+"). "
                    +"Before leaving you must return, all Company items with you and go �No Dues� filled "
                    + "& signed from all concerned departments. You will fill the �Exit Interview� and "
                    + "submit to concern HR also before leaving the Company."+newline
                    +newline
                    +"Thanks & Regards,"+newline
                    +empcode+newline
                    +rmName;
			String from = manager_email;
            String Subject="Manager Feedback";

			if ((org.apache.commons.lang3.StringUtils.isNotBlank(hr_email))
					&& (org.apache.commons.lang3.StringUtils.isNotBlank(emp_email))
					&& (org.apache.commons.lang3.StringUtils.isNotBlank(emp_email))) {
				//mailService.sendEmail(manager_email, from, Subject, rm_message);
				mailService.sendEmail(hr_email, from, Subject, rm_message);
				mailService.sendEmail(emp_email, from, Subject, rm_message);
			} else {
				System.out.println("rmInsert Mail not Available");
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		String officeCode = null;
		Date hrapprovaldate = new Date();
		String hr_app_date = df.format(hrapprovaldate);
		Date hrappdate = new Date(hr_app_date);
		Date lwd = new Date(lastworkingdate);
		MstResignationStatus hrstatus = resignationService.getStatus(5);
		TblUserResignation resignation = resignationService.getResignationUserService(empcode, 2);
		officeCode = (String) resignation.getOfficeId();
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
				// ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
				String[] keys = { "empcode" };
				String[] hrvalues = { hrempcode };
				String[] empvalues = { empcode };
				String[] officekeys = { "OFFICECODE" };
				String[] officevalues = { officeCode };
				// String noduestring = emp.enterPriseDataService("EVM",
				// "NODUESOWNERS", officekeys, officevalues);
				String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
				String noduestring = getInputOfNoDues.replace("OFFICEID", officeCode);
				String sconnectServiceServer = restService.getServiceDetails();
				String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
				String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
				JSONParser noDuesParser = new JSONParser();
				JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
				JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
				JSONObject noduesJson = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
				// String empinfoString = emp.enterPriseDataService("EVM",
				// "EmpInfo", keys, empvalues);
				String getInputHr = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfoHr = getInputHr.replace("employeeCode", hrempcode);
				String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
				String evmDataHr = sconnct.getPostServiceData(evmServiceUrl, empInfoHr).toString();
				JSONParser jsonParserHr = new JSONParser();
				JSONObject jsonObjectHr = (JSONObject) jsonParserHr.parse(evmDataHr);
				JSONObject jsonObjEvmHr = new JSONObject(jsonObjectHr);
				JSONObject empJsonHr = (JSONObject) jsonObjEvmHr.get("GetEmployeeInfoResult");
				String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfo = getInput.replace("employeeCode", empcode);
				//String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
				String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
				JSONObject jsonObjEvm = new JSONObject(jsonObject);
				JSONObject empduesJson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
				String empName = (String) empduesJson.get("EmployeeName");
				String designation = (String) empduesJson.get("Designation");
				String role = (String) empduesJson.get("Role");
				String empNameHr = (String) empJsonHr.get("EmployeeName");
				String departmentName = (String) empduesJson.get("Department");
				// JSONObject empduesJson = new JSONObject();
				// JSONParser parser = new JSONParser();
				// noduesjson = (JSONObject) parser.parse(noduestring);
				// empduesJson = (JSONObject) parser.parse(empinfoString);
				String hr_email = (String) noduesJson.get("HrEmpEmail");
				String emp_email = (String) empduesJson.get("CompanyEmail");
				String rm_email = (String) empduesJson.get("ManagerEmail");
				String newline = System.getProperty("line.separator");
				String rm_message = newline+ "Name: "+empName+newline
						+"Employee Code: "+empcode +newline
						+"Department: "+departmentName+newline
						+"Designation: "+designation+newline
						+"Role: "+role+newline+newline
						+ "Mr / Ms."+empName+","
                      +"You are hereby informed that your last working day with Company will be from" +newline +"the close of the working hours of ("+lastworkingdate+"). "+newline
                      +newline
                      +"Please complete your �No Dues� and �Exit Interview� before leaving the Company."+newline
                      +newline
                      +"Thanks & Regards,"+newline
                      +hrempcode
                      +empNameHr;
				String from = hr_email;
	            String Subject="Last Working Day Confirmation";
				if ((org.apache.commons.lang3.StringUtils.isNotBlank(hr_email))
						&& (org.apache.commons.lang3.StringUtils.isNotBlank(emp_email))
						&& (org.apache.commons.lang3.StringUtils.isNotBlank(rm_email))) {
					//mailService.sendEmail(hr_email, from, Subject, rm_message);
					mailService.sendEmail(emp_email, from, Subject, rm_message);
					mailService.sendEmail(rm_email, from, Subject, rm_message);
				} else {
					System.out.println("Submit hr approval mail not Availble");
				}

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

	@RequestMapping(value = "/exEmployeeLogin", method = RequestMethod.GET)
	public String getExEmpLogin(Model model) {
		model.addAttribute("loginBean", new TblUserResignation());
		return "login";
	}

	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	public String authenticate(@ModelAttribute("loginBean") TblUserResignation tbluserresignation, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String emp_code = tbluserresignation.getExEmpUserid();
		String password = tbluserresignation.getExEmpPassword();
		// TblUserResignation ex_emp =
		// resignationService.getResignationUserService(emp_code, 13);
		TblUserResignation ex_emp = resignationService.getExEmpResignationUserService(emp_code, password, 9);
		if (ex_emp == null) {
			redirectAttributes.addFlashAttribute("msg", "Incorrect username or password");
			return "redirect:exEmployeeLogin";
		} else if (ex_emp.getRemainingLoginDays() > 180) {
			redirectAttributes.addFlashAttribute("msg", "Your Login username and  password are Expired");
			return "redirect:exEmployeeLogin";
		} else if (ex_emp != null) {
			HttpSession session = request.getSession();
			session.setAttribute("resignID", ex_emp.getResignationId());
			session.setAttribute("employeecode", ex_emp.getEmpCode());
			session.setAttribute("roleid", 50);
			session.setAttribute("hrapprovaldate", ex_emp.getHrApprovalDate());
			session.setAttribute("hrempcode", ex_emp.getHrEmpcode());
			session.setAttribute("hrlwd", ex_emp.getHrLwdDate());
			session.setAttribute("resdate", ex_emp.getResignationDate());
			session.setAttribute("rmapprovaldate", ex_emp.getRmApprovalDate());
			session.setAttribute("rmempcode", ex_emp.getRmEmpcode());
			session.setAttribute("exexmpcode", ex_emp.getExEmpUserid());
			session.setAttribute("exempemail", ex_emp.getExEmpEmail());
			session.setAttribute("firstname", ex_emp.getExEmpUserid());
			session.setAttribute("lastLoginDate", ex_emp.getLastLogin());
			session.setAttribute("officeCode", ex_emp.getOfficeId());
			session.setAttribute("remainingLoginDays", ex_emp.getRemainingLoginDays());

			return "home";
		} else {
			// model.addAttribute("msg", "Incorrect Username or Password");
			redirectAttributes.addFlashAttribute("msg", "Incorrect username or password");
			return "redirect:exEmployeeLogin";
		}

	}

	@RequestMapping(value = "/getNoDuesStatuses", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getNoDuesPendingStatuses(HttpServletRequest request, HttpSession session) {
		JSONObject pendingNoDues = new JSONObject();
		try {
			String resignID = (String) request.getParameter("resignationID");
			int resid = Integer.parseInt(resignID);
			pendingNoDues = noduesservice.getNoDuesPendingStatus(resid);
			logger.info("No Dues Status   " + pendingNoDues);
		} catch (Exception e) {
			logger.error("  ", e);
		}

		return pendingNoDues;
	}

	@RequestMapping(value = "/getDocUploadedStatuses", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getDocumentUploadedPending(HttpServletRequest request, HttpSession session) {
		JSONObject notUploadedDocuments = new JSONObject();
		try {
			String resignID = (String) request.getParameter("resignationID");
			int resid = Integer.parseInt(resignID);
			notUploadedDocuments = employeeDocumentService.getNotUploadedDocumentsById(resid);
		} catch (Exception e) {
			logger.error(" ", e);
		}
		return notUploadedDocuments;
	}

	/*
	 * @RequestMapping(value = "/getnoduesemplist", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public JSONObject getnoduesitinformation(HttpServletRequest
	 * request, HttpSession session) { String stageid =
	 * request.getParameter("stageid"); String status1 =
	 * request.getParameter("status"); int status = Integer.valueOf(status1);
	 * String office_code = null; int count = 1; int departmentid = 0;
	 * JSONParser parseemp = null; String DepartmentManagerRm = null; String
	 * NODUESOWNERS = null; JSONObject jsonparse = null; String DepartmentManger
	 * = null; // session = request.getSession(); DepartmentManger = (String)
	 * session.getAttribute("employeecode");
	 * 
	 * //role id from scannect users int role_id = (Integer)
	 * session.getAttribute("roleid"); if (role_id == 15) { departmentid = 4; }
	 * else if (role_id == 16) { departmentid = 5; } else if (role_id == 17) {
	 * departmentid = 6; } else if (role_id == 18) { departmentid = 3; } else if
	 * (role_id == 19) { departmentid = 1; } office_code = (String)
	 * session.getAttribute("officecode"); ArrayList<JSONObject> listinformation
	 * = new ArrayList<JSONObject>(); JSONObject jsonobject = new JSONObject();
	 * ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
	 * 
	 * // information based on officecode String[] officekeys = { "OFFICECODE"
	 * }; String[] officevalues = { office_code }; try { NODUESOWNERS =
	 * emp_prxoy.enterPriseDataService("EVM", "NODUESOWNERS", officekeys,
	 * officevalues); parseemp = new JSONParser(); jsonparse = (JSONObject)
	 * parseemp.parse(NODUESOWNERS); } catch (RemoteException e1) {
	 * e1.printStackTrace(); } catch (ParseException e) { e.printStackTrace(); }
	 * String itManager = ((String) jsonparse.get("ItEmpCode")).trim(); String
	 * accountManager = ((String) jsonparse.get("AccountEmpCode")).trim();
	 * String hrManager = ((String) jsonparse.get("HrEmpCode")).trim(); String
	 * infraManager = ((String) jsonparse.get("InfraEmpCode")).trim(); String[]
	 * key = { "empcode" }; String[] value = { DepartmentManger }; String
	 * empInfo; try { empInfo = emp_prxoy.enterPriseDataService("EVM",
	 * "empInfo", key, value);
	 * 
	 * JSONParser parser = new JSONParser(); JSONObject servicejson =
	 * (JSONObject) parser.parse(empInfo); DepartmentManagerRm = (String)
	 * servicejson.get("ManagerCode");
	 * 
	 * } catch (RemoteException e1) { logger.error("departmentMangetRm" +
	 * e1.getMessage()); e1.printStackTrace(); } catch (Exception e) {
	 * logger.error("departmentMangetRm" + e.getMessage()); e.printStackTrace();
	 * } if ((DepartmentManger.equals(itManager)) ||
	 * (DepartmentManger.equals(accountManager)) ||
	 * (DepartmentManger.equals(hrManager)) ||
	 * (DepartmentManger.equals(infraManager))) { List<String> listempcoderesign
	 * = noduesservice.listrmacceptedempcode(stageid, departmentid, office_code,
	 * status); try { for (String code : listempcoderesign) { String[] empKey =
	 * { "empcode" }; String[] empValue = { code }; String employeeInfo =
	 * emp_prxoy.enterPriseDataService("EVM", "empInfo", empKey, empValue);
	 * JSONParser parserEmp = new JSONParser(); JSONObject servicejsonemp =
	 * (JSONObject) parserEmp.parse(employeeInfo); String rmMangerCode =
	 * (String) servicejsonemp.get("ManagerCode"); String employeename =
	 * (String) servicejsonemp.get("EmployeeName"); String designation =
	 * (String) servicejsonemp.get("Designation"); String spokename = (String)
	 * servicejsonemp.get("SpokeName"); String employeecode = (String)
	 * servicejsonemp.get("EmployeeCode"); String spokeCode = (String)
	 * servicejsonemp.get("SpokeCode");
	 * 
	 * JSONObject itjson = new JSONObject(); itjson.put("sno", count);
	 * itjson.put("empcode", code); itjson.put("empname", employeename);
	 * itjson.put("department", departmentid); itjson.put("designation",
	 * designation); itjson.put("spokecode", spokeCode); itjson.put("location",
	 * spokename); listinformation.add(itjson); count++; }
	 * 
	 * jsonobject.put("emplist", listinformation);
	 * 
	 * } catch (Exception e) { logger.error("", e); } } else {
	 * System.out.println("Employee Not Exit In OfficeId"); }
	 * 
	 * return jsonobject;
	 * 
	 * }
	 * 
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getnoduesemplist", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getnoduesitinformation(HttpServletRequest request, HttpSession session) {
		String stageid = request.getParameter("stageid");
		String status1 = request.getParameter("status");
		int status = Integer.valueOf(status1);
		String office_code = null;
		int count = 1;
		int departmentid = 0;
		// String DepartmentManagerRm = null;
		JSONObject jsonparse = null;
		String DepartmentManger = null;
		DepartmentManger = (String) session.getAttribute("employeecode");
		String sconnectServiceServer = restService.getServiceDetails();

		// role id from sconnect users
		int role_id = (Integer) session.getAttribute("roleid");
		if (role_id == 15) {
			departmentid = 4;
		} else if (role_id == 16) {
			departmentid = 5;
		} else if (role_id == 17) {
			departmentid = 6;
		} else if (role_id == 18) {
			departmentid = 3;
		} else if (role_id == 19) {
			departmentid = 1;
		}
		office_code = (String) session.getAttribute("officecode");
		ArrayList<JSONObject> listinformation = new ArrayList<JSONObject>();
		JSONObject jsonobject = new JSONObject();
		try {
			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			String noduestring = getInputOfNoDues.replace("OFFICEID", office_code);
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			jsonparse = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String itManager = ((String) jsonparse.get("ItEmpCode")).trim();
		String accountManager = ((String) jsonparse.get("AccountEmpCode")).trim();
		String hrManager = ((String) jsonparse.get("HrEmpCode")).trim();
		String infraManager = ((String) jsonparse.get("InfraEmpCode")).trim();

		if ((DepartmentManger.equals(itManager)) || (DepartmentManger.equals(accountManager))
				|| (DepartmentManger.equals(hrManager)) || (DepartmentManger.equals(infraManager))) {
			List<String> listempcoderesign = noduesservice.listrmacceptedempcode(stageid, departmentid, office_code,
					status);
			try {
				for (String code : listempcoderesign) {
					String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
					String empInfo = getInput.replace("employeeCode", code);
					String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
					String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
					JSONObject jsonObjEvm = new JSONObject(jsonObject);
					JSONObject servicejsonemp = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
					// String rmMangerCode = (String)
					// servicejsonemp.get("ManagerCode");
					String employeename = (String) servicejsonemp.get("EmployeeName");
					String designation = (String) servicejsonemp.get("Designation");
					String spokename = (String) servicejsonemp.get("SpokeName");
					// String employeecode = (String)
					// servicejsonemp.get("EmployeeCode");
					String spokeCode = (String) servicejsonemp.get("SpokeCode");

					JSONObject itjson = new JSONObject();
					itjson.put("sno", count);
					itjson.put("empcode", code);
					itjson.put("empname", employeename);
					itjson.put("department", departmentid);
					itjson.put("designation", designation);
					itjson.put("spokecode", spokeCode);
					itjson.put("location", spokename);
					listinformation.add(itjson);
					count++;
				}

				jsonobject.put("emplist", listinformation);

			} catch (Exception e) {
				logger.error("", e);
			}
		} else {

			String itManagerinfo = ((String) jsonparse.get("ItEmpCode")).trim();
			String accountManagerinfo = ((String) jsonparse.get("AccountEmpCode")).trim();
			String hrManagerinfo = ((String) jsonparse.get("HrEmpCode")).trim();
			String infraManagerinfo = ((String) jsonparse.get("InfraEmpCode")).trim();

			/*
			 * String itDepartmentManagerRm = null; String
			 * accountDepartmentManagerRm = null; String hrDepartmentManagerRm =
			 * null; String infraDepartmentManagerRm = null; try {
			 * 
			 * String getInputOfNoDues =
			 * " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}"
			 * ; String noduestring=getInputOfNoDues.replace("OFFICEID",
			 * office_code); String noduesServiceUrl = sconnectServiceServer
			 * +"GetNoDuesOwners"; String noDuesData =
			 * sconnct.getPostServiceData(noduesServiceUrl,
			 * noduestring).toString(); JSONParser noDuesParser = new
			 * JSONParser(); JSONObject noDuesJsonObject = (JSONObject)
			 * noDuesParser.parse(noDuesData); JSONObject jsonObjNoDues = new
			 * JSONObject(noDuesJsonObject); jsonparse = (JSONObject)
			 * jsonObjNoDues.get("GetNoDuesOwnersResult"); } catch
			 * (ParseException e) { e.printStackTrace(); }
			 * 
			 * try { String getInput =
			 * " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}"
			 * ; String empInfo=getInput.replace("employeeCode", itManagerinfo);
			 * String evmServiceUrl = sconnectServiceServer +"GetEmployeeInfo";
			 * String evmData = sconnct.getPostServiceData(evmServiceUrl,
			 * empInfo).toString(); JSONParser jsonParser = new JSONParser();
			 * JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			 * JSONObject jsonObjEvm = new JSONObject(jsonObject); JSONObject
			 * servicejsonit = (JSONObject)
			 * jsonObjEvm.get("GetEmployeeInfoResult"); itDepartmentManagerRm =
			 * (String) servicejsonit.get("ManagerCode");
			 * 
			 * } catch (ParseException e) { e.printStackTrace(); }
			 * 
			 * try { String getInput =
			 * " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}"
			 * ; String empInfo=getInput.replace("employeeCode",
			 * accountManagerinfo); String evmServiceUrl = sconnectServiceServer
			 * +"GetEmployeeInfo"; String evmData =
			 * sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			 * JSONParser jsonParser = new JSONParser(); JSONObject jsonObject =
			 * (JSONObject) jsonParser.parse(evmData); JSONObject jsonObjEvm =
			 * new JSONObject(jsonObject); JSONObject servicejsonaccount =
			 * (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			 * accountDepartmentManagerRm = (String)
			 * servicejsonaccount.get("ManagerCode");
			 * 
			 * } catch (ParseException e) { e.printStackTrace(); }
			 * 
			 * try { String getInput =
			 * " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}"
			 * ; String empInfo=getInput.replace("employeeCode", hrManagerinfo);
			 * String evmServiceUrl = sconnectServiceServer +"GetEmployeeInfo";
			 * String evmData = sconnct.getPostServiceData(evmServiceUrl,
			 * empInfo).toString(); JSONParser jsonParser = new JSONParser();
			 * JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			 * JSONObject jsonObjEvm = new JSONObject(jsonObject); JSONObject
			 * servicejsonhr = (JSONObject)
			 * jsonObjEvm.get("GetEmployeeInfoResult"); hrDepartmentManagerRm =
			 * (String) servicejsonhr.get("ManagerCode"); } catch
			 * (ParseException e) { e.printStackTrace(); }
			 * 
			 * try { String getInput =
			 * " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}"
			 * ; String empInfo=getInput.replace("employeeCode",
			 * infraManagerinfo); String evmServiceUrl = sconnectServiceServer
			 * +"GetEmployeeInfo"; String evmData =
			 * sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			 * JSONParser jsonParser = new JSONParser(); JSONObject jsonObject =
			 * (JSONObject) jsonParser.parse(evmData); JSONObject jsonObjEvm =
			 * new JSONObject(jsonObject); JSONObject servicejsoninfra =
			 * (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			 * infraDepartmentManagerRm = (String)
			 * servicejsoninfra.get("ManagerCode"); } catch (ParseException e) {
			 * e.printStackTrace(); }
			 */

			if ((DepartmentManger.equalsIgnoreCase(itManagerinfo))
					|| (DepartmentManger.equalsIgnoreCase(accountManagerinfo))
					|| (DepartmentManger.equalsIgnoreCase(hrManagerinfo))
					|| (DepartmentManger.equalsIgnoreCase(infraManagerinfo))) {
				List<String> listempcoderesign = noduesservice.listrmacceptedempcode(stageid, departmentid, office_code,
						status);
				try {
					for (String code : listempcoderesign) {
						String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
						String empInfo = getInput.replace("employeeCode", code);
						String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
						String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
						JSONParser jsonParser = new JSONParser();
						JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
						JSONObject jsonObjEvm = new JSONObject(jsonObject);
						JSONObject servicejsonemp = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
						// String rmMangerCode = (String)
						// servicejsonemp.get("ManagerCode");
						String employeename = (String) servicejsonemp.get("EmployeeName");
						String designation = (String) servicejsonemp.get("Designation");
						String spokename = (String) servicejsonemp.get("SpokeName");
						// String employeecode = (String)
						// servicejsonemp.get("EmployeeCode");
						String spokeCode = (String) servicejsonemp.get("SpokeCode");

						JSONObject itjson = new JSONObject();
						itjson.put("sno", count);
						itjson.put("empcode", code);
						itjson.put("empname", employeename);
						itjson.put("department", departmentid);
						itjson.put("designation", designation);
						itjson.put("spokecode", spokeCode);
						itjson.put("location", spokename);
						listinformation.add(itjson);
						count++;
					}

					jsonobject.put("emplist", listinformation);

				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}
		return jsonobject;

	}

	// @RequestMapping(value = "/getsearch", method = RequestMethod.GET)
	// @ResponseBody
	@RequestMapping(value = "/getrmnoduesemplist", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getnoduesrminformation(HttpServletRequest request, HttpSession session) {
		// String stageid = request.getParameter("stageid");
		String status1 = request.getParameter("status");
		int status = Integer.parseInt(status1);
		int count = 1;
		String office_code = null;
		JSONObject jsonobject = null;
		String rmEmpCode = null;
		int departmentid = 0;
		// session = request.getSession();
		int role_id = (Integer) session.getAttribute("roleid");
		if (role_id == 19) {
			rmEmpCode = (String) session.getAttribute("employeecode");
			departmentid = 1;
			office_code = (String) session.getAttribute("officecode");
			ArrayList<JSONObject> listinformation = new ArrayList<JSONObject>();
			jsonobject = new JSONObject();
			// ISoftAgeEnterpriseProxy emp_prxoy = new
			// ISoftAgeEnterpriseProxy();
			List<String> listempcoderesign = noduesservice.listemprmaccepted(departmentid, status);
			try {
				for (String code : listempcoderesign) {
					String[] key = { "empcode" };
					String[] value = { code };
					// String empInfo = emp_prxoy.enterPriseDataService("EVM",
					// "empInfo", key, value);
					String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
					String empInfo = getInput.replace("employeeCode", code);
					String sconnectServiceServer = restService.getServiceDetails();
					String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
					String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
					JSONParser jsonParser = new JSONParser();
					JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
					JSONObject jsonObjEvm = new JSONObject(jsonObject);
					JSONObject servicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
					// JSONParser parser = new JSONParser();
					// JSONObject servicejson = (JSONObject)
					// parser.parse(empInfo);
					String rmMangerCode = (String) servicejson.get("ManagerCode");
					if (rmMangerCode.equalsIgnoreCase(rmEmpCode)) {

						// information based on officecode
						String[] officekeys = { "OFFICECODE" };
						String[] officevalues = { office_code };
						// String NODUESOWNERS =
						// emp_prxoy.enterPriseDataService("EVM",
						// "NODUESOWNERS", officekeys,
						// officevalues);

						// JSONParser parseemp = new JSONParser();
						// JSONObject jsonparse = (JSONObject)
						// parseemp.parse(NODUESOWNERS);
						String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
						String noduestring = getInputOfNoDues.replace("OFFICEID", office_code);
						String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
						String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
						JSONParser noDuesParser = new JSONParser();
						JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
						JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
						JSONObject servicejsonNodues = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
						String employeename = (String) servicejson.get("EmployeeName");
						String designation = (String) servicejson.get("Designation");
						String spokename = (String) servicejson.get("SpokeName");
						String employeecode = (String) servicejson.get("EmployeeCode");
						long Departmentid = (Long) servicejson.get("DepartmentID");
						String spokeCode = (String) servicejson.get("SpokeCode");

						JSONObject itjson = new JSONObject();
						itjson.put("sno", count);
						itjson.put("empcode", code);
						itjson.put("empname", employeename);
						itjson.put("department", departmentid);
						itjson.put("designation", designation);
						itjson.put("spokecode", spokeCode);
						itjson.put("location", spokename);
						listinformation.add(itjson);
						count++;
					}

					jsonobject.put("emplist", listinformation);
				}
			} catch (Exception e) {
				logger.error("Rm noduesEmployeeList", e);
			}
		}

		return jsonobject;

	}

	@RequestMapping(value = "/getemployeemodalinfo", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getemployeeinformation(HttpServletRequest request) {
		// ISoftAgeEnterpriseProxy emp_prxoy = new ISoftAgeEnterpriseProxy();
		String emp_code = request.getParameter("employee_code");
		JSONObject itassetsmodal = new JSONObject();
		String[] key = { "empcode" };
		String[] value = { emp_code };

		try {
			// empInfo = emp_prxoy.enterPriseDataService("EVM", "empInfo", key,
			// value);
			// JSONParser parser = new JSONParser();
			// JSONObject servicejson = (JSONObject) parser.parse(empInfo);
			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", emp_code);
			String sconnectServiceServer = restService.getServiceDetails();
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			JSONObject servicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			String employeename = (String) servicejson.get("EmployeeName");
			String designation = (String) servicejson.get("Designation");
			String spokename = (String) servicejson.get("SpokeName");
			String spokecode = (String) servicejson.get("SpokeCode");
			String employeecode = (String) servicejson.get("EmployeeCode");
			long departmentid = (Long) servicejson.get("DepartmentID");
			itassetsmodal.put("empcode", emp_code);
			itassetsmodal.put("empname", employeename);
			itassetsmodal.put("department", departmentid);
			itassetsmodal.put("spokecode", spokecode);
			itassetsmodal.put("designation", designation);
			itassetsmodal.put("location", spokename);

		} catch (ParseException e1) {
			logger.error("ParseException   ", e1);
		}

		return itassetsmodal;
	}

	@RequestMapping(value = "/getitassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getjsondata(HttpServletRequest request) {
		JSONObject itassetsmodal = null;
		String empcode = request.getParameter("employee_code");
		String empassets = null;
		String barcodeno = null;
		JSONObject jsonItassets = new JSONObject();
		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();
		List<String> listvalue = new ArrayList<String>();
		String[] keys = { "empcode" };
		String[] value = { empcode };
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		/*
		 * try { empassets = empdetails.enterPriseDataService("Asset",
		 * "ASSETINFO", keys, value); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */
		try {

			String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
			String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(assetsData);
			JSONObject hsonService = new JSONObject(jsonObject);
			JSONArray jsonArray = (JSONArray) hsonService.get("GetAssetDetailsResult");
			// JSONArray jsonArray = (JSONArray) jsonParser.parse(assetsData);
			// JSONArray serviceparser = null;
			// JSONParser parser = new JSONParser();
			// serviceparser = (JSONArray) parser.parse(empassets);
			if (!jsonArray.isEmpty()) {
				for (Object str : jsonArray) {
					JSONObject jsondata = (JSONObject) str;

					Long departmentId = (Long) jsondata.get("Department_Id");
					if (departmentId == 4) {
						String assetname = (String) jsondata.get("Asset_Name");
						barcodeno = (String) jsondata.get("Barcode_No");
						itassetsmodal = new JSONObject();
						itassetsmodal.put("name", assetname);
						itassetsmodal.put("DepartmentId", departmentId);
						itassetsmodal.put("barcodeno", barcodeno);
						arrlist.add(itassetsmodal);
					}
				}
				jsonItassets.put("itassets", arrlist);
			} else {
				itassetsmodal.put("name", "No Assets Allocated");
				arrlist.add(itassetsmodal);
			}
			jsonItassets.put("itassets", arrlist);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonItassets;
	}

	@RequestMapping(value = "/getacccountassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getjsondataaccount(HttpServletRequest request) {
		JSONObject accountassetsmodal = null;
		String empcode = request.getParameter("employee_code");
		String empassets = null;
		String barcodeno = null;
		JSONObject jsonAccountassets = new JSONObject();
		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();
		List<String> listvalue = new ArrayList<String>();
		String[] keys = { "empcode" };
		String[] value = { empcode };
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		/*
		 * try { empassets = empdetails.enterPriseDataService("Asset",
		 * "ASSETINFO", keys, value); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */
		try {
			String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
			String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(assetsData);
			JSONObject hsonService = new JSONObject(jsonObject);
			JSONArray jsonArray = (JSONArray) hsonService.get("GetAssetDetailsResult");
			// JSONArray jsonArray = (JSONArray) jsonParser.parse(assetsData);

			if (!jsonArray.isEmpty()) {
				for (Object str : jsonArray) {
					JSONObject jsondata = (JSONObject) str;
					Long departmentId = (Long) jsondata.get("Department_Id");
					if (departmentId == 6) {
						barcodeno = (String) jsondata.get("Barcode_No");
						String assetname = (String) jsondata.get("Asset_Name");
						accountassetsmodal = new JSONObject();
						accountassetsmodal.put("name", assetname);
						accountassetsmodal.put("DepartmentId", departmentId);
						accountassetsmodal.put("barcodeno", barcodeno);
						arrlist.add(accountassetsmodal);
					}
				}
				jsonAccountassets.put("accountassets", arrlist);
			} else {
				accountassetsmodal.put("name", "No Assets Allocated");
				arrlist.add(accountassetsmodal);
			}
			jsonAccountassets.put("itassets", arrlist);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return jsonAccountassets;
	}

	@RequestMapping(value = "/gethrassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getjsondatahr(HttpServletRequest request) {
		JSONObject hrassetsmodal = null;
		String empcode = request.getParameter("employee_code");
		String empassets = null;
		JSONObject jsonhrassets = new JSONObject();
		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();
		List<String> listvalue = new ArrayList<String>();
		String[] keys = { "empcode" };
		String[] value = { empcode };
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		/*
		 * try { empassets = empdetails.enterPriseDataService("Asset",
		 * "ASSETINFO", keys, value); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */
		try {
			/*
			 * JSONArray serviceparser = null; JSONParser parser = new
			 * JSONParser(); serviceparser = (JSONArray)
			 * parser.parse(empassets);
			 */
			String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
			String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(assetsData);
			JSONObject hsonService = new JSONObject(jsonObject);
			JSONArray jsonArray = (JSONArray) hsonService.get("GetAssetDetailsResult");
			// JSONArray jsonArray = (JSONArray) jsonParser.parse(assetsData);
			if (!jsonArray.isEmpty()) {
				for (Object str : jsonArray) {
					JSONObject jsondata = (JSONObject) str;

					Long departmentId = (Long) jsondata.get("Department_Id");
					if (departmentId == 5) {
						String assetname = (String) jsondata.get("Asset_Name");
						String barcodeno = (String) jsondata.get("Barcode_No");
						hrassetsmodal = new JSONObject();
						hrassetsmodal.put("name", assetname);
						hrassetsmodal.put("DepartmentId", departmentId);
						hrassetsmodal.put("barcodeno", barcodeno);
						arrlist.add(hrassetsmodal);
					}
				}
				jsonhrassets.put("accountassets", arrlist);
			} else {
				hrassetsmodal.put("name", "No Assets Allocated");
				arrlist.add(hrassetsmodal);
			}
			jsonhrassets.put("hrassets", arrlist);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsonhrassets;
	}

	@RequestMapping(value = "/insertitassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertitassets(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		JSONArray serviceparser = null;
		String assetname = null;
		String itmanagerempcode = (String) session.getAttribute("employeecode");
		JSONObject insertitasserts = new JSONObject();
		String assetsissued = request.getParameter("emp_assets");
		String comments = request.getParameter("comments");
		String empcode = request.getParameter("emp_code");
		String DepartmentId = request.getParameter("departmentId");

		int assetDepartment = Integer.parseInt(DepartmentId);
		String itmanagername = null;
		int department = 0;
		int resignationId = 0;
		Date date = null;
		String empassets = null;
		String barcodeno = null;
		String assetsallocatedby = null;
		String assetsallocateddate = null;
		String departmentmanageremail=null;
		String	empemail=null;
		String	manageremail =null;
		JSONObject managerservicejson = null;
		JSONObject servicejson = null;
		String[] key = { "empcode" };
		String[] value = { empcode };
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", departmentmanagerempcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
		    managerservicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

		    String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfoemp = getInputemp.replace("employeeCode", empcode);
			String evmDataemp = sconnct.getPostServiceData(evmServiceUrl, empInfoemp).toString();
		
				JSONParser jsonParseremp = new JSONParser();
				JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(evmDataemp);
				JSONObject jsonObjEvmemp = new JSONObject(jsonObjectemp);
				servicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		departmentmanageremail = (String) managerservicejson.get("CompanyEmail");
		String departmentmanagername = (String) managerservicejson.get("EmployeeName");
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");
		String	managercode = (String) servicejson.get("ManagerCode");
		empemail = (String) servicejson.get("CompanyEmail");
	    manageremail = (String) servicejson.get("ManagerEmail");
		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		//String sconnectServiceServer = restService.getServiceDetails();
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();
		/*
		 * JSONParser jsonParser = new JSONParser(); JSONArray jsonArray =
		 * (JSONArray) jsonParser.parse(assetsData);
		 */
		Date today = new Date();
		TblAssetsManagement itasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		resignationId = resignedUser.getResignationId();
		String[] assetsvalue = assetsissued.split(",");
		Set<String> assetsset = new HashSet<String>(Arrays.asList(assetsvalue));
		for (String assetssplit : assetsset) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(assetsData);
				JSONObject hsonService = new JSONObject(jsonObject);
				serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
				// serviceparser = (JSONArray) parser.parse(assetsData);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Object str : serviceparser) {
				JSONObject jsondata = (JSONObject) str;
				Long departmentId = (Long) jsondata.get("Department_Id");
				if (departmentId == 4) {
					assetname = (String) jsondata.get("Asset_Name");
					if (assetssplit.equals(assetname)) {
						barcodeno = (String) jsondata.get("Barcode_No");
						assetsallocatedby = (String) jsondata.get("Allocated_By");
						assetsallocateddate = (String) jsondata.get("AllocatedDate");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							date = formatter.parse(assetsallocateddate);
							formatter.format(date);
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}

						/*
						 * try { empdetails.assetDeallocation(empcode,
						 * barcodeno, itmanagerempcode); } catch
						 * (RemoteException e) { e.printStackTrace(); }
						 */

						String getInputAssetsDeallocate = " {\"emp\": \"employeeCode\",\"barcode\": \"BARCODEID\",  \"deallocatedBy\": [\"DEALLOCATEDUSER\"]}";
						String AssetsInfoDeallocate = getInputAssetsDeallocate.replace("employeeCode", empcode)
								.replace("BARCODEID", barcodeno).replace("DEALLOCATEDUSER", itmanagerempcode);
						sconnct.getPostServiceData(assetsServiceUrl, AssetsInfoDeallocate).toString();

						itasset.setAssetsIssue(assetssplit);
						itasset.setCreatedBy("System");
						itasset.setCreatedOn(today);
						itasset.setDepartmentId(assetDepartment);
						itasset.setIssuedBy(assetsallocatedby);
						itasset.setIssuedOn(date);
						itasset.setItemStatus(2);
						itasset.setReceivedBy(itmanagerempcode);
						itasset.setReceivedOn(today);
						itasset.setTblUserResignation(resignedUser);
						itasset.setAssetsbarcodeno(barcodeno);
						insertitasserts = noduesservice.submitnoduesassets(itasset);
					}
				}
			}
		}

		TblNoDuesClearence noduesclearence = noduesservice.getByResignationId(resignationId, assetDepartment);
		if (noduesclearence == null) {
			noduesclearence = new TblNoDuesClearence();
		}
		noduesclearence.setComments(comments);
		noduesclearence.setDepartmentFinalStatus(2);
		noduesclearence.setDepartmentId(assetDepartment);
		noduesclearence.setTbluserresignation(resignedUser);

		noduesservice.updateNoduesClearence(noduesclearence);
		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+""+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES SUBMITTED FOR IT", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES SUBMITTED FOR IT", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return insertitasserts;
	}

	@RequestMapping(value = "/getinframodalassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getinframodalassets(HttpServletRequest request) {
		JSONObject infraassetsmodal = null;
		String empcode = request.getParameter("employee_code");
		String empassets = null;
		String barcodeno = null;
		JSONObject jsoninfraassets = new JSONObject();
		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();
		List<String> listvalue = new ArrayList<String>();
		String[] keys = { "empcode" };
		String[] value = { empcode };
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		/*
		 * try { empassets = empdetails.enterPriseDataService("Asset",
		 * "ASSETINFO", keys, value); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */

		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();
		try {
			JSONArray serviceparser = null;
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(assetsData);
			JSONObject hsonService = new JSONObject(jsonObject);
			serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
			// serviceparser = (JSONArray) parser.parse(assetsData);
			if (!serviceparser.isEmpty()) {
				for (Object str : serviceparser) {
					JSONObject jsondata = (JSONObject) str;
					Long departmentId = (Long) jsondata.get("Department_Id");
					if (departmentId == 3) {
						infraassetsmodal = new JSONObject();
						String assetname = (String) jsondata.get("Asset_Name");
						barcodeno = (String) jsondata.get("Barcode_No");
						infraassetsmodal.put("name", assetname);
						infraassetsmodal.put("departmentId", departmentId);
						infraassetsmodal.put("barcodeno", barcodeno);
						arrlist.add(infraassetsmodal);
					}
				}
				jsoninfraassets.put("infraassets", arrlist);
			} else {
				infraassetsmodal.put("name", "No Assets Allocated");
				arrlist.add(infraassetsmodal);
				jsoninfraassets.put("infraassets", arrlist);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return jsoninfraassets;
	}

	@RequestMapping(value = "/insertinfraassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertinfraassets(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String inframanagerempcode = (String) session.getAttribute("employeecode");
		String infraasserts = request.getParameter("emp_assets");
		String empcode = request.getParameter("emp_code");
		String comments = request.getParameter("comments");
		String departmentid = request.getParameter("departmentId");
		String assetbarcodeno = request.getParameter("emp_barcode");
		long Department = Long.parseLong(departmentid);
		int assetDepartment = (int) Department;
		int department = 0;
		JSONArray serviceparser = null;
		String assetname = null;
		int resignationId = 0;
		String barcodeno = null;
		String empinfo = null;
		String empassets = null;
		String assetsallocatedby = null;
		String assetsallocateddate = null;
		String departmentmanageremail=null;
		String	empemail=null;
		String	manageremail =null;
		JSONObject managerservicejson = null;
		JSONObject servicejson = null;
		Date date = null;
		String[] key = { "empcode" };
		String[] value = { empcode };
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", departmentmanagerempcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
		    managerservicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

		    String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfoemp = getInputemp.replace("employeeCode", empcode);
			String evmDataemp = sconnct.getPostServiceData(evmServiceUrl, empInfoemp).toString();
		
				JSONParser jsonParseremp = new JSONParser();
				JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(evmDataemp);
				JSONObject jsonObjEvmemp = new JSONObject(jsonObjectemp);
				servicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");
		String departmentmanagername = (String) managerservicejson.get("EmployeeName");
	    departmentmanageremail = (String) managerservicejson.get("CompanyEmail");
	    String	managercode = (String) servicejson.get("ManagerCode");
		empemail = (String) servicejson.get("CompanyEmail");
	    manageremail = (String) servicejson.get("ManagerEmail");
		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		//String sconnectServiceServer = restService.getServiceDetails();
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();
		JSONObject insertasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement infraasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		resignationId = resignedUser.getResignationId();
		String[] asserts = infraasserts.split(",");
		Set<String> assetsset = new HashSet<String>(Arrays.asList(asserts));
		for (String assetssplit : assetsset) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(assetsData);
				JSONObject hsonService = new JSONObject(jsonObject);
				serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
				// serviceparser = (JSONArray) parser.parse(assetsData);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Object str : serviceparser) {
				JSONObject jsondata = (JSONObject) str;
				Long departmentId = (Long) jsondata.get("Department_Id");
				if (departmentId == 3) {
					assetname = (String) jsondata.get("Asset_Name");
					if (assetssplit.equals(assetname)) {
						barcodeno = (String) jsondata.get("Barcode_No");
						assetsallocatedby = (String) jsondata.get("Allocated_By");
						assetsallocateddate = (String) jsondata.get("AllocatedDate");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							date = formatter.parse(assetsallocateddate);
							formatter.format(date);
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}

						/*
						 * try { empdetails.assetDeallocation(empcode,
						 * barcodeno, inframanagerempcode); } catch
						 * (RemoteException e) {
						 * 
						 * e.printStackTrace(); }
						 */
						String getInputAssetsDeallocate = " {\"emp\": \"employeeCode\",\"barcode\": \"BARCODEID\",  \"deallocatedBy\": [\"DEALLOCATEDUSER\"]}";
						String AssetsInfoDeallocate = getInputAssetsDeallocate.replace("employeeCode", empcode)
								.replace("BARCODEID", barcodeno).replace("DEALLOCATEDUSER", inframanagerempcode);
						sconnct.getPostServiceData(assetsServiceUrl, AssetsInfoDeallocate).toString();

						infraasset.setAssetsIssue(assetssplit);
						infraasset.setCreatedBy("System");
						infraasset.setCreatedOn(today);
						infraasset.setDepartmentId(assetDepartment);
						infraasset.setIssuedBy(assetsallocatedby);
						infraasset.setIssuedOn(date);
						infraasset.setItemStatus(2);
						infraasset.setReceivedBy(inframanagerempcode);
						infraasset.setReceivedOn(today);
						infraasset.setAssetsbarcodeno(barcodeno);
						infraasset.setTblUserResignation(resignedUser);
						insertasserts = noduesservice.submitnoduesassets(infraasset);
					}
				}
			}
		}
		TblNoDuesClearence noduesclearence = noduesservice.getByResignationId(resignationId, assetDepartment);
		if (noduesclearence == null) {
			noduesclearence = new TblNoDuesClearence();
		}
		noduesclearence.setComments(comments);
		noduesclearence.setDepartmentFinalStatus(2);
		noduesclearence.setTbluserresignation(resignedUser);
		noduesclearence.setDepartmentId(assetDepartment);
		// insertasserts = noduesservice.submitNoduesclearence(nodues);
		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+"You are required to get the �No Dues� form filled & signed from all concerned "+newline
				+"departments on your last date of working and submit to the concerned HR "+newline
				+"person before leaving the Company."+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES SUBMITTED FOR INFRA", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES SUBMITTED FOR INFRA", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return insertasserts;
	}

	@RequestMapping(value = "/getNoDuesAssets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getassests(HttpServletRequest request) {
		String empcode = request.getParameter("employee_code");

		int departmentid = 1;
		JSONObject accountassets = new JSONObject();
		List<JSONObject> value = noduesservice.listassetsdetails(departmentid);
		accountassets.put("list", value);
		return accountassets;
	}

	@RequestMapping(value = "/getUploadItems", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getUploadItems(HttpServletRequest request, HttpSession session) {
		JSONArray list = new JSONArray();
		session = request.getSession();
		int roleid = (Integer) session.getAttribute("roleid");
		// item List depends on deptId
		int deptId = 0;// will change as roleid changes
		if (roleid == 16) {
			deptId = 5;
		} else if (roleid == 17) {
			deptId = 6;
		}

		List<MstUploadItem> itemList = employeeDocumentService.getUploadItems(deptId);

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

		JSONObject ftpdetails = employeeDocumentService.getFtpDetails();

		HttpSession session = request.getSession();
		String fileLocation = "D:/CSVFile/";
		// String empId = "ss0062";
		JSONObject msgJSON = new JSONObject();
		String result = null;
		String filePath = "";
		try {
			JSONArray list = new JSONArray();
			// int circle_code = (Integer) session.getAttribute("circleid");
			String uploadedBy = (String) session.getAttribute("employeecode");
			logger.info("Uploading file ");
			String itemId1 = request.getParameter("uploadId");
			String empCode = request.getParameter("empCode");
			String resignId = request.getParameter("resignId");
			int id = Integer.parseInt(resignId);
			int itemId = Integer.parseInt(itemId1);
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			byte[] bytes = mpf.getBytes();
			String filename = mpf.getOriginalFilename();
			/*
			 * File file = new File(fileLocation + filename);
			 * BufferedOutputStream stream = new BufferedOutputStream(new
			 * FileOutputStream(file)); stream.write(bytes); stream.close();
			 * 
			 * logger.info("Server File Location=" + file); String FilePath =
			 * empCode + "/" + filename;
			 */

			filePath = uploadDocumentFTPClient(filename, empCode, bytes, ftpdetails);
			MstUploadItem mstUploadItem = employeeDocumentService.entityById(itemId);
			TblUserResignation resignation = resignationService.getById(id);

			TblUploadedPath uploadPath = employeeDocumentService.findByEmpCodeAndItemId(empCode, itemId);
			if (uploadPath == null) {
				TblUploadedPath newUploadPath = new TblUploadedPath();
				newUploadPath.setUploadedBy(uploadedBy);
				newUploadPath.setEmpCode(empCode);
				newUploadPath.setPath(filePath);
				newUploadPath.setUploadedOn(new Date());
				newUploadPath.setTblUserResignation(resignation);
				newUploadPath.setMstUploadItem(mstUploadItem);
				result = employeeDocumentService.save(newUploadPath);
			} else {

				uploadPath.setUploadedBy(uploadedBy);
				uploadPath.setPath(filePath);
				uploadPath.setUploadedOn(new Date());
				result = employeeDocumentService.save(uploadPath);
			}

			if (itemId == 3) {
				MstResignationStatus resignationStatus = resignationService.getStatus(11);
				resignation.setMstResignationStatus(resignationStatus);
				approvalservice.updateResignationStatus(resignation);
			}

			if (itemId == 5) {
				MstResignationStatus resignationStatus = resignationService.getStatus(13);
				resignation.setMstResignationStatus(resignationStatus);
				approvalservice.updateResignationStatus(resignation);
			}

		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(filePath)) {
			msgJSON.put("msg", "Successfully uploaded");
		} else {
			msgJSON.put("msg", "Unable to upload the file");
		}
		return msgJSON;

	}

	@RequestMapping(value = "/getDownloadItem", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getDownloadItem(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String fileLocation = "D:/CSVFile/";
		// String empcode = "ss0062";
		String empcode = (String) session.getAttribute("employeecode");
		String result = null;

		JSONObject ftpdetails = employeeDocumentService.getFtpDetails();
		String ftpHost = (String) ftpdetails.get("host");
		String username = (String) ftpdetails.get("username");
		String password = (String) ftpdetails.get("password");
		// String ftpHost = "172.25.37.14";
		// String username = "hrms";
		// String password = "hrms@123@15";

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
		// String empcode = "ss0062";
		String empcode = null;
		String result = null;

		JSONObject ftpdetails = employeeDocumentService.getFtpDetails();
		String ftpHost = (String) ftpdetails.get("host");
		String username = (String) ftpdetails.get("username");
		String password = (String) ftpdetails.get("password");

		// String ftpHost = "172.25.37.14";
		// String username = "hrms";
		// String password = "hrms@123@15";
		String path = null;
		OutputStream outStream = null;
		JSONArray uploadList = new JSONArray();

		try {

			HttpSession session = request.getSession();// added by arpan for
														// change
			empcode = (String) session.getAttribute("employeecode");
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

			byte[] bytes = downloadDocumentFTPClient(finalPath, filename, ftpdetails);

			tblUploadedPath.setDownloadBy(downloadBy);
			tblUploadedPath.setDownloadOn(new Date());
			String result1 = employeeDocumentService.update(tblUploadedPath);

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

	public static String uploadDocumentFTPClient(String file, String empId, byte[] bytes, JSONObject ftpdetails) {

		String ftpHost = (String) ftpdetails.get("host");
		String username = (String) ftpdetails.get("username");
		String password = (String) ftpdetails.get("password");

		// String ftpHost = "172.25.37.14";
		// String username = "hrms";
		// String password = "hrms@123@15";

		FileOutputStream fos = null;
		String ftpPath = "";
		FTPSClient ftpClient = new FTPSClient(false);

		try {
			// InputStream inputStream=new FileInputStream("");
			ftpClient.connect(ftpHost, 21);

			String[] replies = ftpClient.getReplyStrings();
			if (replies != null && replies.length > 0) {
				for (String aReply : replies) {
					System.out.println("SERVER: " + aReply);
				}
			}

			boolean login = true;

			login = ftpClient.login(username, password);
			showServerReply(ftpClient);
			ftpClient.execPROT("P");
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
			showServerReply(ftpClient);
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

	private static void showServerReply(FTPClient ftpClient) {
		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String aReply : replies) {
				System.out.println("SERVER: " + aReply);
			}
		}
	}

	public static byte[] downloadDocumentFTPClient(String filePath, String filename, JSONObject ftpdetails) {

		String ftpHost = (String) ftpdetails.get("host");
		String username = (String) ftpdetails.get("username");
		String password = (String) ftpdetails.get("password");

		// String ftpHost = "172.25.37.14";
		// String username = "hrms";
		// String password = "hrms@123@15";
		FileOutputStream fos = null;
		String ftpPath = "";
		// FTPClient ftpClient = new FTPClient();
		FTPSClient ftpClient = new FTPSClient();
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
			ftpClient.execPROT("P");
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

		String officecode = (String) session.getAttribute("officecode");

		// int circle_code = (Integer) session.getAttribute("circleid");
		String empcode = (String) session.getAttribute("employeecode");
		int deptId = 1;
		int status = 0;
		if (deptId == 1) {
			status = 9;
		}

		if (deptId == 2) {

			status = 11;
		}

		JSONObject resignations = null;

		try {

			// resignations =
			// resignationService.getResignationModelByCircleID(circle_code);

			resignations = resignationService.getResignationModelByCircleID(officecode);

			// resignations =
			// resignationService.getHrApprovalInitService(empcode, status,
			// circle_code);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resignations;
	}

	@RequestMapping(value = "/insertaccountassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertaccountassets(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String accountmanagerempcode = (String) session.getAttribute("employeecode");
		String assertsreceived = (String) request.getParameter("emp_assets");
		String comments = (String) request.getParameter("accounts_comments");
		String empcode = (String) request.getParameter("emp_code");
		String DepartmentId = (String) request.getParameter("departmentId");
		/* long Department = Long.parseLong(DepartmentId); */
		int assetDepartment = Integer.parseInt(DepartmentId);
		int department = 0;
		int resignationId = 0;
		String empassets = null;
		String barcodeno = null;
		String assetsallocatedby = null;
		String assetsallocateddate = null;
		JSONArray serviceparser = null;
		String departmentmanageremail=null;
		String	empemail=null;
		String	manageremail =null;
		JSONObject managerservicejson =null;
		JSONObject servicejson = null;
		Date date = null;
		String assetname = null;
		String[] key = { "empcode" };
		String[] value = { empcode };
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", departmentmanagerempcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			managerservicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

			String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfoemp = getInputemp.replace("employeeCode", empcode);
			String evmDataemp = sconnct.getPostServiceData(evmServiceUrl, empInfoemp).toString();
			
				JSONParser jsonParseremp = new JSONParser();
				JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(evmDataemp);
				JSONObject jsonObjEvmemp = new JSONObject(jsonObjectemp);
			    servicejson = (JSONObject) jsonObjEvmemp.get("GetEmployeeInfoResult");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");
		String	managercode = (String) servicejson.get("ManagerCode");
		empemail = (String) servicejson.get("CompanyEmail");
	    manageremail = (String) servicejson.get("ManagerEmail");
	    String departmentmanagername = (String) managerservicejson.get("EmployeeName");
        departmentmanageremail = (String) managerservicejson.get("CompanyEmail");
		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		//String sconnectServiceServer = restService.getServiceDetails();
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();

		JSONObject insertasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement accountasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		resignationId = resignedUser.getResignationId();
		String[] asserts = assertsreceived.split(",");
		Set<String> assetsset = new HashSet<String>(Arrays.asList(asserts));
		for (String assetssplit : assetsset) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(assetsData);
				JSONObject hsonService = new JSONObject(jsonObject);
				serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
				// serviceparser = (JSONArray) parser.parse(assetsData);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Object str : serviceparser) {
				JSONObject jsondata = (JSONObject) str;
				Long departmentId = (Long) jsondata.get("Department_Id");
				int department1 = (int) (long) departmentId;
				if (department1 == assetDepartment) {
					assetname = (String) jsondata.get("Asset_Name");
					if (assetssplit.equals(assetname)) {
						barcodeno = (String) jsondata.get("Barcode_No");
						assetsallocatedby = (String) jsondata.get("Allocated_By");
						assetsallocateddate = (String) jsondata.get("AllocatedDate");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							date = formatter.parse(assetsallocateddate);
							formatter.format(date);
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
						/*
						 * try { empdetails.assetDeallocation(empcode,
						 * barcodeno, accountmanagerempcode); } catch
						 * (RemoteException e) { // TODO Auto-generated catch
						 * block e.printStackTrace(); }
						 */

						String getInputAssetsDeallocate = " {\"emp\": \"employeeCode\",\"barcode\": \"BARCODEID\",  \"deallocatedBy\": [\"DEALLOCATEDUSER\"]}";
						String AssetsInfoDeallocate = getInputAssetsDeallocate.replace("employeeCode", empcode)
								.replace("BARCODEID", barcodeno).replace("DEALLOCATEDUSER", accountmanagerempcode);
						sconnct.getPostServiceData(assetsServiceUrl, AssetsInfoDeallocate).toString();

						accountasset.setAssetsIssue(assetssplit);
						accountasset.setCreatedBy("system");
						accountasset.setCreatedOn(today);
						accountasset.setDepartmentId(assetDepartment);
						accountasset.setIssuedBy(assetsallocatedby);
						accountasset.setIssuedOn(date);
						accountasset.setItemStatus(2);
						accountasset.setReceivedBy(accountmanagerempcode);
						accountasset.setReceivedOn(today);
						accountasset.setTblUserResignation(resignedUser);
						accountasset.setAssetsbarcodeno(barcodeno);
						insertasserts = noduesservice.submitnoduesassets(accountasset);
					}
				}
			}
		}
		TblNoDuesClearence noduesclearence = noduesservice.getByResignationId(resignationId, assetDepartment);
		if (noduesclearence == null) {
			noduesclearence = new TblNoDuesClearence();
		}
		noduesclearence.setComments(comments);
		noduesclearence.setDepartmentFinalStatus(2);
		noduesclearence.setTbluserresignation(resignedUser);
		noduesclearence.setDepartmentId(assetDepartment);
		// insertasserts = noduesservice.submitNoduesclearence(nodues);
		noduesservice.updateNoduesClearence(noduesclearence);
		
		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+"You are required to get the �No Dues� form filled & signed from all concerned "+newline
				+"departments on your last date of working and submit to the concerned HR "+newline
				+"person before leaving the Company."+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES SUBMITTED FOR ACCOUNT", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES SUBMITTED FOR ACCOUNT", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return insertasserts;
	}

	@RequestMapping(value = "/rejectempassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject rejectaccountassets(HttpServletRequest request, HttpSession session) {
		String receivedassets = request.getParameter("received_assets");
		String notreceivedassets = request.getParameter("not_received");
		String comments = request.getParameter("comments");
		String empcode = request.getParameter("emp_code");
		String status = request.getParameter("final_status");
		int finalstatus = Integer.parseInt(status);
		String departmentId = request.getParameter("departmentId");

		int assetDepartment = Integer.parseInt(departmentId);

		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		String managercode = null;
		String sendToemail = null;
		String barcodeno = null;
		String assetsallocateddate = null;
		String assetsallocatedby = null;
		String assetname = null;
		JSONArray serviceparser = null;
		JSONObject servicejson =null;
		Date date = null;
		Date date1 = null;
		String empassets = null;
		String empemail = null;
		String manageremail = null;
		String departmentmanageremail = null;
		String departmentmanagername = null;
		session = request.getSession();
		// Department Manager Information
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String[] departmentmanagerkey = { "empcode" };
		String[] departmentmanagervalue = { departmentmanagerempcode };
		/*
		 * String departmentempInfo = empdetails.enterPriseDataService("EVM",
		 * "empInfo", departmentmanagerkey, departmentmanagervalue); JSONParser
		 * departmentmanagerparse = new JSONParser();
		 */
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", departmentmanagerempcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			JSONObject managerservicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

			 departmentmanagername = (String) managerservicejson.get("EmployeeName");
			departmentmanageremail = (String) managerservicejson.get("CompanyEmail");

		} catch (ParseException e) {

			e.printStackTrace();
		}
		// Rejected Employee Information
		String[] empkey = { "empcode" };
		String[] empvalue = { empcode };
		

		String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfoemp = getInputemp.replace("employeeCode", empcode);
		
		String evmDataemp = sconnct.getPostServiceData(evmServiceUrl, empInfoemp).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmDataemp);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			 servicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

			// JSONObject servicejson = (JSONObject) parser.parse(empInfo);
			managercode = (String) servicejson.get("ManagerCode");
			empemail = (String) servicejson.get("CompanyEmail");
			manageremail = (String) servicejson.get("ManagerEmail");
			/*
			 * long empdepartmentid = (Long) servicejson.get("DepartmentID");
			 */
			sendToemail = managercode + empemail;
			// information based on officecode
			String[] officekeys = { "OFFICECODE" };
			String[] officevalues = { "CORGUR001" };
			// String NODUESOWNERS = empdetails.enterPriseDataService("EVM",
			// "NODUESOWNERS", officekeys, officevalues);
			// JSONParser parseemp = new JSONParser();
			// JSONObject jsonparse = (JSONObject) parseemp.parse(NODUESOWNERS);
			

			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			String noduestring = getInputOfNoDues.replace("OFFICEID", "CORGUR001");
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			servicejson = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] key = { "empcode" };
		String[] value = { empcode };
		// empassets = empdetails.enterPriseDataService("Asset", "ASSETINFO",
		// key, value);
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");
		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();

		Date today = new Date();
		JSONObject rejectjson = new JSONObject();
		TblAssetsManagement receiveditem = new TblAssetsManagement();
		String[] notreceived = notreceivedassets.split(",");
		Set<String> notreceivedassetsset = new HashSet<String>(Arrays.asList(notreceived));
		for (String remaingassets : notreceivedassetsset) {
			JSONParser parser = new JSONParser();
			try {

				JSONObject jsonObject = (JSONObject) parser.parse(assetsData);
				JSONObject hsonService = new JSONObject(jsonObject);
				serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
				// serviceparser = (JSONArray) parser.parse(assetsData);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Object str : serviceparser) {
				JSONObject jsondata = (JSONObject) str;
				Long departmentId1 = (Long) jsondata.get("Department_Id");
				/* int DepartmentId=Integer.parseInt(departmentId1); */
				int DepartmentId = (int) (long) departmentId1;
				if (DepartmentId == assetDepartment) {
					assetname = (String) jsondata.get("Asset_Name");
					if (remaingassets.equals(assetname)) {
						barcodeno = (String) jsondata.get("Barcode_No");
						assetsallocatedby = (String) jsondata.get("Allocated_By");
						assetsallocateddate = (String) jsondata.get("AllocatedDate");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							date = formatter.parse(assetsallocateddate);
							formatter.format(date);
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}
						receiveditem.setAssetsIssue(remaingassets);
						receiveditem.setCreatedBy("system");
						receiveditem.setCreatedOn(today);
						receiveditem.setDepartmentId(assetDepartment);
						receiveditem.setIssuedBy(assetsallocatedby);
						receiveditem.setIssuedOn(date);
						receiveditem.setItemStatus(finalstatus);
						receiveditem.setReceivedBy("");
						receiveditem.setReceivedOn(date1);
						receiveditem.setAssetsbarcodeno(barcodeno);
						TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
						receiveditem.setTblUserResignation(resignedUser);
						rejectjson = noduesservice.submitnoduesassets(receiveditem);
					}
				}
			}
		}

		if (receivedassets == null | receivedassets.length() == 0) {
			System.out.println("value not found...");
		} else {
			String[] assertsreceived = receivedassets.split(",");
			Set<String> receivedassetsset = new HashSet<String>(Arrays.asList(assertsreceived));
			for (String assetsitem : receivedassetsset) {
				JSONParser parser1 = new JSONParser();
				try {
					serviceparser = (JSONArray) parser1.parse(empassets);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				for (Object str1 : serviceparser) {
					JSONObject jsondata1 = (JSONObject) str1;
					Long departmentId2 = (Long) jsondata1.get("Department_Id");
					/* int DepartmentId=Integer.parseInt(departmentId1); */
					int departmnet2 = (int) (long) departmentId2;
					if (departmnet2 == assetDepartment) {
						assetname = (String) jsondata1.get("Asset_Name");
						if (assetsitem.equals(assetname)) {
							barcodeno = (String) jsondata1.get("Barcode_No");
							assetsallocatedby = (String) jsondata1.get("Allocated_By");
							assetsallocateddate = (String) jsondata1.get("AllocatedDate");
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							try {
								date = formatter.parse(assetsallocateddate);
								formatter.format(date);
							} catch (java.text.ParseException e) {
								e.printStackTrace();
							}
							receiveditem.setAssetsIssue(assetsitem);
							receiveditem.setCreatedBy("system");
							receiveditem.setCreatedOn(today);
							receiveditem.setDepartmentId(assetDepartment);
							receiveditem.setIssuedBy(assetsallocatedby);
							receiveditem.setIssuedOn(date);
							receiveditem.setItemStatus(2);
							receiveditem.setReceivedOn(today);
							receiveditem.setReceivedBy(departmentmanagerempcode);
							receiveditem.setAssetsbarcodeno(barcodeno);
							TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
							receiveditem.setTblUserResignation(resignedUser);
							rejectjson = noduesservice.submitnoduesassets(receiveditem);
						}
					}
				}
			}
		}
		TblNoDuesClearence clearence = new TblNoDuesClearence();
		clearence.setComments(comments);
		clearence.setDepartmentFinalStatus(finalstatus);
		clearence.setDepartmentId(assetDepartment);
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		clearence.setTbluserresignation(resignedUser);
		rejectjson = noduesservice.submitNoduesclearence(clearence);

		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+"You are required to get the �No Dues� form filled & signed from all concerned "+newline
				+"departments on your last date of working and submit to the concerned HR "+newline
				+"person before leaving the Company."+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES CLEARANCE ACCOUNT", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES CLEARANCE ACCOUNT", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return rejectjson;
	}

	@RequestMapping(value = "/rejectrmpassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject rejectrmassets(HttpServletRequest request, HttpSession session) {
		String receivedassets = request.getParameter("received_assets");
		String notreceivedassets = request.getParameter("not_received");
		String comments = request.getParameter("comments");
		String empcode = request.getParameter("emp_code");
		String status = request.getParameter("final_status");
		int finalstatus = Integer.parseInt(status);
		String departmentId = request.getParameter("departmentId");

		int assetDepartment = Integer.parseInt(departmentId);
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		String managercode = null;
		String barcodeno = null;
		String assetsallocateddate = null;
		String assetsallocatedby = null;
		String assetname = null;
		JSONArray serviceparser = null;
		Date date = null;
		Date date1 = null;
		String empemail = null;
		String empassets = null;
		String manageremail = null;
		String sendTo = null;
		String departmentmanageremail = null;
		JSONObject servicejson = null;
		String departmentmanagername = null;
		session = request.getSession();
		// Department Manager Information
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String[] departmentmanagerkey = { "empcode" };
		String[] departmentmanagervalue = { departmentmanagerempcode };
		// String departmentempInfo = empdetails.enterPriseDataService("EVM",
		// "empInfo", departmentmanagerkey,
		// departmentmanagervalue);
		// JSONParser departmentmanagerparse = new JSONParser();
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", departmentmanagerempcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			JSONObject managerservicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

			// JSONObject managerservicejson = (JSONObject)
			// departmentmanagerparse.parse(departmentempInfo);
		    departmentmanagername = (String) managerservicejson.get("EmployeeName");
			departmentmanageremail = (String) managerservicejson.get("CompanyEmail");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		// Rejected Employee Information
		String[] empkey = { "empcode" };
		String[] empvalue = { empcode };

		String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfoemp = getInputemp.replace("employeeCode", empcode);
		String evmDataemp = sconnct.getPostServiceData(evmServiceUrl, empInfoemp).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmDataemp);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			 servicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

			managercode = (String) servicejson.get("ManagerCode");
			empemail = (String) servicejson.get("CompanyEmail");
			manageremail = (String) servicejson.get("ManagerEmail");

			sendTo = manageremail + empemail;

			// information based on officecode
			String[] officekeys = { "OFFICECODE" };
			String[] officevalues = { "CORGUR001" };
			// String NODUESOWNERS = empdetails.enterPriseDataService("EVM",
			// "NODUESOWNERS", officekeys, officevalues);
			// JSONParser parseemp = new JSONParser();
			// JSONObject jsonparse = (JSONObject) parseemp.parse(NODUESOWNERS);
			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			String noduestring = getInputOfNoDues.replace("OFFICEID", "CORGUR001");
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			servicejson = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String[] key = { "empcode" };
		String[] value = { empcode };

		/*
		 * try { empassets = empdetails.enterPriseDataService("Asset",
		 * "ASSETINFO", key, value); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");

		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();

		Date today = new Date();
		JSONObject rejectjson = new JSONObject();
		TblAssetsManagement receiveditem = new TblAssetsManagement();
		String[] notreceived = notreceivedassets.split(",");
		Set<String> notreceivedassetsset = new HashSet<String>(Arrays.asList(notreceived));
		for (String remaingassets : notreceivedassetsset) {

			receiveditem.setAssetsIssue(remaingassets);
			receiveditem.setCreatedBy("system");
			receiveditem.setCreatedOn(today);
			receiveditem.setDepartmentId(assetDepartment);
			receiveditem.setIssuedBy(assetsallocatedby);
			receiveditem.setIssuedOn(date);
			receiveditem.setItemStatus(finalstatus);
			receiveditem.setReceivedBy("");
			receiveditem.setReceivedOn(date1);
			receiveditem.setAssetsbarcodeno(barcodeno);
			TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
			receiveditem.setTblUserResignation(resignedUser);
			rejectjson = noduesservice.submitnoduesassets(receiveditem);
		}
		if (receivedassets == null | receivedassets.length() == 0) {
			System.out.println("value not found...");
		} else {
			String[] assertsreceived = receivedassets.split(",");
			Set<String> receivedassetsset = new HashSet<String>(Arrays.asList(assertsreceived));
			for (String assetsitem : receivedassetsset) {
				receiveditem.setAssetsIssue(assetsitem);
				receiveditem.setCreatedBy("system");
				receiveditem.setCreatedOn(today);
				receiveditem.setDepartmentId(assetDepartment);
				receiveditem.setIssuedBy(assetsallocatedby);
				receiveditem.setIssuedOn(date);
				receiveditem.setItemStatus(2);
				receiveditem.setReceivedOn(today);
				receiveditem.setReceivedBy(departmentmanagerempcode);
				receiveditem.setAssetsbarcodeno(barcodeno);
				TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
				receiveditem.setTblUserResignation(resignedUser);
				rejectjson = noduesservice.submitnoduesassets(receiveditem);
			}

		}
		TblNoDuesClearence clearence = new TblNoDuesClearence();
		clearence.setComments(comments);
		clearence.setDepartmentFinalStatus(finalstatus);
		clearence.setDepartmentId(assetDepartment);
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		clearence.setTbluserresignation(resignedUser);
		rejectjson = noduesservice.submitNoduesclearence(clearence);
		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+"You are required to get the �No Dues� form filled & signed from all concerned "+newline
				+"departments on your last date of working and submit to the concerned HR "+newline
				+"person before leaving the Company."+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES CLEARANCE RM", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES CLEARANCE RM", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return rejectjson;
	}

	@RequestMapping(value = "/inserthrassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject inserthrassets(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String hrmanagerempcode = (String) session.getAttribute("employeecode");
		String hrassets = (String) request.getParameter("emp_assets");
		String comments = (String) request.getParameter("hr_comments");
		String empcode = (String) request.getParameter("emp_code");
		String departmntId = (String) request.getParameter("departmentId");
		/* long Department = Long.parseLong(departmntId); */
		int assetDepartment = Integer.parseInt(departmntId);
		int department = 0;
		String assetname = null;
		JSONArray serviceparser = null;
		int resignationId = 0;
		String empassets = null;
		String barcodeno = null;
		String assetsallocatedby = null;
		String assetsallocateddate = null;
		String departmentmanageremail=null;
		String	empemail=null;
		String	manageremail =null;
		JSONObject managerservicejson=null;
		JSONObject servicejson =null;
		Date date = null;
		String[] key = { "empcode" };
		String[] value = { empcode };
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", departmentmanagerempcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try{
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObjectdept = (JSONObject) jsonParser.parse(evmData);
		JSONObject jsonObjEvm = new JSONObject(jsonObjectdept);
		 managerservicejson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
	    
	    String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfoemp = getInputemp.replace("employeeCode", empcode);
		String evmDataemp = sconnct.getPostServiceData(evmServiceUrl, empInfoemp).toString();
		//try {
			JSONParser jsonParseremp = new JSONParser();
			JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(evmDataemp);
			JSONObject jsonObjEvmemp = new JSONObject(jsonObjectemp);
			 servicejson = (JSONObject) jsonObjEvmemp.get("GetEmployeeInfoResult");
			}catch(ParseException e){
			e.printStackTrace();
		}
		String departmentmanagername = (String) managerservicejson.get("EmployeeName");
	    departmentmanageremail = (String) managerservicejson.get("CompanyEmail");
		String	managercode = (String) servicejson.get("ManagerCode");
		empemail = (String) servicejson.get("CompanyEmail");
		manageremail = (String) servicejson.get("ManagerEmail");
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");

		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
	
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		String assetsData = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();

		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		resignationId = resignedUser.getResignationId();
		JSONObject inserthrasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement hrasset = new TblAssetsManagement();
		String[] asserts = hrassets.split(",");
		Set<String> assetsset = new HashSet<String>(Arrays.asList(asserts));
		for (String assetssplit : assetsset) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject jsonObject = (JSONObject) parser.parse(assetsData);
				JSONObject hsonService = new JSONObject(jsonObject);
				serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
				
				// serviceparser = (JSONArray) parser.parse(assetsData);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Object str : serviceparser) {
				JSONObject jsondata = (JSONObject) str;
				Long departmentId = (Long) jsondata.get("Department_Id");
				int departmentId1 = (int) (long) departmentId;
				if (departmentId1 == assetDepartment) {
					assetname = (String) jsondata.get("Asset_Name");
					if (assetssplit.equals(assetname)) {
						barcodeno = (String) jsondata.get("Barcode_No");
						assetsallocatedby = (String) jsondata.get("Allocated_By");
						assetsallocateddate = (String) jsondata.get("AllocatedDate");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						try {
							date = formatter.parse(assetsallocateddate);
							formatter.format(date);
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}

						

						String getInputAssetsDeallocate = " {\"emp\": \"employeeCode\",\"barcode\": \"BARCODEID\",  \"deallocatedBy\": [\"DEALLOCATEDUSER\"]}";
						String AssetsInfoDeallocate = getInputAssetsDeallocate.replace("employeeCode", empcode)
								.replace("BARCODEID", barcodeno).replace("DEALLOCATEDUSER", hrmanagerempcode);
						sconnct.getPostServiceData(assetsServiceUrl, AssetsInfoDeallocate).toString();

						hrasset.setAssetsIssue(assetssplit);
						hrasset.setCreatedBy("system");
						hrasset.setCreatedOn(today);
						hrasset.setDepartmentId(assetDepartment);
						hrasset.setIssuedBy(assetsallocatedby);
						hrasset.setIssuedOn(date);
						hrasset.setItemStatus(2);
						hrasset.setReceivedBy(hrmanagerempcode);
						hrasset.setReceivedOn(today);
						hrasset.setAssetsbarcodeno(barcodeno);
						hrasset.setTblUserResignation(resignedUser);
						inserthrasserts = noduesservice.submitnoduesassets(hrasset);
					}
				}
			}
		}
		TblNoDuesClearence noduesclearence = noduesservice.getByResignationId(resignationId, assetDepartment);
		if (noduesclearence == null) {
			noduesclearence = new TblNoDuesClearence();
		}
		noduesclearence.setDepartmentFinalStatus(2);
		noduesclearence.setComments(comments);
		noduesclearence.setTbluserresignation(resignedUser);
		noduesclearence.setDepartmentId(assetDepartment);
		noduesservice.updateNoduesClearence(noduesclearence);
		// inserthrasserts = noduesservice.submitNoduesclearence(nodues);
		MstResignationStatus status_mast = resignationService.getStatus(7);
		resignedUser.setMstResignationStatus(status_mast);
		approvalservice.updateResignationStatus(resignedUser);
		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+"You are required to get the �No Dues� form filled & signed from all concerned "+newline
				+"departments on your last date of working and submit to the concerned HR "+newline
				+"person before leaving the Company."+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES SUBMITTED FOR HR", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES SUBMITTED FOR HR", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return inserthrasserts;
	}

	@RequestMapping(value = "/insertrmassets", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertrmassets(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String rmmanagerempcode = (String) session.getAttribute("employeecode");
		String rmassets = request.getParameter("emp_assets");
		String comments = request.getParameter("comments");
		String empcode = request.getParameter("emp_code");
		int department = 0;
		String empinfo = null;
		int resignationId = 0;
		String departmentmanageremail=null;
		String	empemail=null;
		String	manageremail =null;
		JSONObject deptmanagerservicejson=null;
		JSONObject servicejson =null;
		Date datenull = null;
		String[] key = { "empcode" };
		String[] value = { empcode };
		String departmentmanagerempcode = (String) session.getAttribute("employeecode");
		String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String empInfo = getInput.replace("employeeCode", empcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
		String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		try {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
			JSONObject jsonObjEvm = new JSONObject(jsonObject);
			JSONObject serviceparser = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			
			Long departmentid = (Long) serviceparser.get("DepartmentID");
			department = departmentid.intValue();
			String rmmanager = (String) serviceparser.get("EmployeeName");
			String	managercode = (String) servicejson.get("ManagerCode");
			empemail = (String) servicejson.get("CompanyEmail");
			manageremail = (String) servicejson.get("ManagerEmail");
			String getInputdept = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfodept = getInput.replace("employeeCode", departmentmanagerempcode);
			String evmDatadept = sconnct.getPostServiceData(evmServiceUrl, empInfodept).toString();
			JSONParser jsonParserdept = new JSONParser();
			JSONObject jsonObjectdept = (JSONObject) jsonParserdept.parse(evmDatadept);
			JSONObject jsonObjEvmdept = new JSONObject(jsonObjectdept);
		    deptmanagerservicejson = (JSONObject) jsonObjEvmdept.get("GetEmployeeInfoResult");
		    
		    departmentmanageremail = (String) deptmanagerservicejson.get("CompanyEmail");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String departmentmanagername = (String) deptmanagerservicejson.get("EmployeeName");
		String empName = (String) servicejson.get("EmployeeName");
		String designation = (String) servicejson.get("Designation");
		String role = (String) servicejson.get("Role");
		String departmentName = (String) servicejson.get("Department");
		JSONObject insertrmasserts = new JSONObject();
		Date today = new Date();
		TblAssetsManagement rmasset = new TblAssetsManagement();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 5);
		resignationId = resignedUser.getResignationId();
		String[] asserts = rmassets.split(",");
		for (String assetssplit : asserts) {
			rmasset.setAssetsIssue(assetssplit);
			rmasset.setCreatedBy("system");
			rmasset.setCreatedOn(today);
			rmasset.setDepartmentId(1);
			rmasset.setIssuedBy("");
			rmasset.setIssuedOn(datenull);
			rmasset.setItemStatus(2);
			rmasset.setReceivedBy(rmmanagerempcode);
			rmasset.setReceivedOn(today);
			rmasset.setTblUserResignation(resignedUser);
			insertrmasserts = noduesservice.submitnoduesassets(rmasset);
		}
		TblNoDuesClearence noduesclearence = noduesservice.getByResignationId(resignationId, 1);
		if (noduesclearence == null) {
			noduesclearence = new TblNoDuesClearence();
		}
		noduesclearence.setDepartmentFinalStatus(2);
		noduesclearence.setComments(comments);
		noduesclearence.setTbluserresignation(resignedUser);
		noduesclearence.setDepartmentId(1);
		// insertrmasserts = noduesservice.submitNoduesclearence(nodues);
		noduesservice.updateNoduesClearence(noduesclearence);
		String newline = System.getProperty("line.separator");
		String rm_message = newline+ "Name: "+empName+newline
				+"Employee Code: "+empcode +newline
				+"Department: "+departmentName+newline
				+"Designation: "+designation+newline
				+"Role: "+role+newline+newline
				+"Remarks: "+comments+newline+newline
		        +" Mr. / Ms. "+empName+","+newline
				+newline
				+"You are required to get the �No Dues� form filled & signed from all concerned "+newline
				+"departments on your last date of working and submit to the concerned HR "+newline
				+"person before leaving the Company."+newline
				+newline
				+"Thanks & Regards, "+newline
				+departmentmanagerempcode+newline
				+departmentmanagername;
		if ((org.apache.commons.lang3.StringUtils.isNotBlank(manageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(departmentmanageremail))
				&& (org.apache.commons.lang3.StringUtils.isNotBlank(empemail))) {
			mailService.sendEmail(manageremail, departmentmanageremail, "NO DUES SUBMITTED FOR RM", rm_message);
			mailService.sendEmail(empemail, departmentmanageremail, "NO DUES SUBMITTED FOR RM", rm_message);
		} else {
			System.out.println("Rejected Employee mail not Availble");
		}
		return insertrmasserts;
	}

	@RequestMapping(value = "/gethrfeedbackquestions", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject gethrfeedbackquestion() {
		int roleid = 16;
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
			@RequestParam("emp_code") String empcode, HttpSession session, HttpServletRequest request)
			throws ParseException {
		String empname = "";
		int stageId = 5;
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		session = request.getSession();
		String hrempcode = (String) session.getAttribute("employeecode");

		// empname = empdetails.getUserDetail(hrempcode).getFirstName();
		String sconnectServiceServer = restService.getServiceDetails();
		String getUserDetailServiceUrl = sconnectServiceServer + "GetUserDetail/" + hrempcode;
		empname = sconnct.getServiceData(getUserDetailServiceUrl).toString();
		JSONParser jsonParseremp = new JSONParser();
		JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(empname);
		JSONObject jsonObjEmp = new JSONObject(jsonObjectemp);
		String firstname = (String) jsonObjEmp.get("FirstName");
		String lastname = (String) jsonObjEmp.get("LastName");

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
				feedbackhr.setFeedbackBy(firstname);
				feedbackhr.setFeedbackFrom(hrempcode);
				MstQuestions question = approvalservice.getRmFeedbackQuestionService(sid);
				feedbackhr.setMstQuestions(question);
				feedbackhr.setTblUserResignation(resignedUser);
				feedbackhr.setStageId(stageId);
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
		String empcode = null;
		String empName = null;
		String assingToEmpcode = null;
		String officecode = null;
		// JSONParser parser = new JSONParser();
		JSONObject jsonObject = new JSONObject();
		// String rmEmpCode = "ss0078";officeCode

		try {
			session = request.getSession();
			empcode = (String) session.getAttribute("employeecode");
			empName = (String) session.getAttribute("firstname");
			String deptId = request.getParameter("deptId");
			int departmentId = Integer.parseInt(deptId);
			// get Query Assigned Manager Employee Code
			TblUserResignation tblUserResignation = resignationService.getResignationUserService(empcode, 0);
			// String assingToEmpcode = "ss0078";
			officecode = (String) session.getAttribute("officeCode");
			// ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
			String[] officekeys = { "OFFICECODE" };
			String[] officevalues = { officecode };
			// String noduesString = emp.enterPriseDataService("EVM",
			// "NODUESOWNERS", officekeys, officevalues);

			// jsonObject = (JSONObject) parser.parse(noduesString);
			String getInputOfNoDues = " {\"Service\": \"EVM\",\"Operation\": \"NODUESOWNERS\",  \"Keys\": [\"OFFICECODE\"],\"Values\":[\"OFFICEID\"]}";
			String noduestring = getInputOfNoDues.replace("OFFICEID", officecode);
			String sconnectServiceServer = restService.getServiceDetails();
			String noduesServiceUrl = sconnectServiceServer + "GetNoDuesOwners";
			String noDuesData = sconnct.getPostServiceData(noduesServiceUrl, noduestring).toString();
			JSONParser noDuesParser = new JSONParser();
			JSONObject noDuesJsonObject = (JSONObject) noDuesParser.parse(noDuesData);
			JSONObject jsonObjNoDues = new JSONObject(noDuesJsonObject);
			jsonObject = (JSONObject) jsonObjNoDues.get("GetNoDuesOwnersResult");

			if (departmentId == 3) {
				assingToEmpcode = (String) jsonObject.get("InfraEmpCode");
			}
			if (departmentId == 4) {
				assingToEmpcode = (String) jsonObject.get("ItEmpCode");
			}
			if (departmentId == 5) {
				assingToEmpcode = (String) jsonObject.get("HrEmpCode");
			}
			if (departmentId == 6) {
				assingToEmpcode = (String) jsonObject.get("AccountEmpCode");
			}
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
			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", assingToEmpcode);
			//String sconnectServiceServer = restService.getServiceDetails();
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
		
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObjectAssignedEmp = (JSONObject) jsonParser.parse(evmData);
				JSONObject jsonObjEvm = new JSONObject(jsonObjectAssignedEmp);
				JSONObject serviceparser = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");
			    String	assingToEmpmail = (String) serviceparser.get("CompanyEmail");
			    
				String getInputemp = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfoemp = getInput.replace("employeeCode", empcode);
				//String sconnectServiceServer = restService.getServiceDetails();
				String evmServiceUrlemp = sconnectServiceServer + "GetEmployeeInfo";
				String evmDataemp = sconnct.getPostServiceData(evmServiceUrlemp, empInfo).toString();
			
					JSONParser jsonParseremp = new JSONParser();
					JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(evmData);
					JSONObject jsonObjEvmemp = new JSONObject(jsonObjectemp);
					JSONObject serviceparseremp = (JSONObject) jsonObjEvmemp.get("GetEmployeeInfoResult");
				    String	empmail = (String) serviceparseremp.get("CompanyEmail");
				    String	empname = (String) serviceparseremp.get("EmployeeName");
				    String newline = System.getProperty("line.separator");
               String msg="Dear Sir, " +newline
            		   +newline
            		   +queryText+newline
            		   +newline
            		   +"Thanks & Regards, "+newline
            		   +empcode+newline
            		   +empname;
			if (id > 0) {
				TblExEmployeeQuery employeeQuery1 = queryService.getById(id);
				TblExEmpCommunication empCommunication = new TblExEmpCommunication();
				empCommunication.setTblExEmployeeQuery(employeeQuery1);
				empCommunication.setQueryFrom(empcode);
				empCommunication.setCreatedOn(new Date());
				empCommunication.setQueryText(queryText);

				String result1 = queryService.save(empCommunication);
				if ((org.apache.commons.lang3.StringUtils.isNotBlank(assingToEmpmail))
						&& (org.apache.commons.lang3.StringUtils.isNotBlank(empmail))) {
					mailService.sendEmail(assingToEmpmail, empmail, "Ex-Employee Query", msg);
					//mailService.sendEmail(empemail, departmentmanageremail, "NO DUES Submitted For RM", queryText);
				} else {
					System.out.println("Rejected Employee mail not Availble");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/saveQueryManger", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray saveQueryManger(HttpServletRequest request, HttpSession session) {

		// String empcode = "ss0062";
		// String empName = "Rohit";
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

		// String empcode = "ss0062";
		// String empName = "Rohit";
		// String rmEmpCode = "";
		JSONArray array = new JSONArray();

		try {

			session = request.getSession();
			// rmEmpCode = (String) session.getAttribute("employeecode");
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

	@RequestMapping(value = "/rmapproval", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject rmApproval(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String employeecode = (String) session.getAttribute("employeecode");
		JSONObject jsonApproval = approvalservice.getResignedUsersForRm(employeecode, 1);
		return jsonApproval;
	}

	@RequestMapping(value = "/getRMApprovalInitFromService", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getApprovalIntiFromService(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		JSONObject jsonApproval = new JSONObject();
		String emp_code = (String) session.getAttribute("employeecode");
		List<String> rm_list = resignationService.getAllResignedUserRMs();
		System.out.println(rm_list);
		Set<String> to_show = new HashSet<String>();
		for (String rm : rm_list) {
			String[] keys = { "empcode" };
			String[] values = { rm };
			// String rm_rm1 = "ss0053";
			// String rm_rm2 = "ss0050";
			// ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
			try {
				// String empinfo = emp.enterPriseDataService("EVM", "EmpInfo",
				// keys, values);
				String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfo = getInput.replace("employeeCode", emp_code);
				String sconnectServiceServer = restService.getServiceDetails();
				String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
				String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
				JSONObject jsonObjEvm = new JSONObject(jsonObject);
				JSONObject serviceJson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

				// JSONParser parser = new JSONParser();
				// JSONObject serviceJson = (JSONObject) parser.parse(empinfo);
				String rm_rm1 = (String) serviceJson.get("ManagerCode");
				String[] rmkey = { "empcode" };
				String[] rmvalues = { rm_rm1 };
				// String rm_empinfo = emp.enterPriseDataService("EVM",
				// "EmpInfo", rmkey, rmvalues);

				String getInputRm = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfoRm = getInput.replace("employeeCode", emp_code);
				String evmDataRm = sconnct.getPostServiceData(evmServiceUrl, empInfoRm).toString();
				JSONParser jsonParserRm = new JSONParser();
				JSONObject jsonObjectRm = (JSONObject) jsonParser.parse(evmData);
				JSONObject jsonObjEvmRm = new JSONObject(jsonObject);
				JSONObject rm_rmJson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

				// JSONObject rm_rmJson = (JSONObject) parser.parse(rm_empinfo);
				String rm_rm2 = (String) rm_rmJson.get("ManagerCode");
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

	@RequestMapping(value = "/hrapproval", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getHrApprovalOnOfficeCode(HttpServletRequest request, HttpSession session) {
		session = request.getSession();
		String officeCode = (String) session.getAttribute("officecode");
		JSONObject resignedUsers = resignationService.getUsersForHrApproval(officeCode, 2);
		return resignedUsers;
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
			// String hr_hr1 = "ss0073";
			// String hr_hr2 = "ss0029";
			// ISoftAgeEnterpriseProxy emp = new ISoftAgeEnterpriseProxy();
			String[] key = { "empcode" };
			String[] values = { hr };
			try {
				/*
				 * String empinfo = emp.enterPriseDataService("EVM", "EmpInfo",
				 * key, values); JSONParser parser = new JSONParser();
				 * JSONObject serviceJson = (JSONObject) parser.parse(empinfo);
				 */
				String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfo = getInput.replace("employeeCode", emp_code);
				String sconnectServiceServer = restService.getServiceDetails();
				String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
				String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
				JSONObject jsonObjEvm = new JSONObject(jsonObject);
				JSONObject serviceJson = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

				String hr_hr1 = (String) serviceJson.get("ManagerCode");
				String[] hr_key = { "empcode" };
				String[] hr_values = { hr_hr1 };
				/*
				 * String hr_empinfo = emp.enterPriseDataService("EVM",
				 * "EmpInfo", hr_key, hr_values); JSONObject hr_json =
				 * (JSONObject) parser.parse(hr_empinfo); String hr_hr2 =
				 * (String) hr_json.get("ManagerCode");
				 */

				String getInputHr = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
				String empInfoHr = getInput.replace("employeeCode", emp_code);
				String evmDataHr = sconnct.getPostServiceData(evmServiceUrl, empInfoHr).toString();
				JSONParser jsonParserHr = new JSONParser();
				JSONObject jsonObjectHr = (JSONObject) jsonParserHr.parse(evmData);
				JSONObject jsonObjEvmHr = new JSONObject(jsonObjectHr);
				JSONObject hr_json = (JSONObject) jsonObjEvmHr.get("GetEmployeeInfoResult");
				String hr_hr2 = (String) hr_json.get("ManagerCode");
				if ((hr_hr1 == null || hr_hr1 == "") && emp_code.equals(hr)) {
					hr_to_show.add(hr);
				} else if ((hr_hr1 == null && hr_hr1 == "") && !emp_code.equals(hr)) {
					continue;
				} else if (hr_hr2 == null && hr_hr2 == "") {
					if (emp_code.equals(hr)) {
						hr_to_show.add(hr);
					} else if (emp_code.equals(hr_hr1)) {
						hr_to_show.add(hr);
					} else {
						continue;
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
			} catch (Exception e) {
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
				jsonObject.put("queryfrom", tblExEmployeeQuery.getCreatedBy());
				jsonObject.put("queryText", tblExEmployeeQuery.getQueryText());
				jsonObject.put("queryDate", tblExEmployeeQuery.getCreatedOn().toString());
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
		session = request.getSession();
		String empcode = (String) session.getAttribute("employeecode");
		int stageid = 3;
		JSONObject empfeedback = new JSONObject();
		int resignationId = 0;
		String empinfo = null;
		String employeename = null;
		String designation = null;
		String spokename = null;
		String spokecode = null;
		int department = 0;
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 7);
		resignationId = resignedUser.getResignationId();
		List<TblFeedbacks> feedbackanswer = exitinterviewservice.getEmpFeedback(resignationId, stageid);
		if (!feedbackanswer.isEmpty()) {
			empfeedback.put("feedbackdetails", null);
		} else {

			String getInput = " {\"Service\": \"EVM\",\"Operation\": \"EMPINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
			String empInfo = getInput.replace("employeeCode", empcode);
			String sconnectServiceServer = restService.getServiceDetails();
			String evmServiceUrl = sconnectServiceServer + "GetEmployeeInfo";
			String evmData = sconnct.getPostServiceData(evmServiceUrl, empInfo).toString();
			try {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(evmData);
				JSONObject jsonObjEvm = new JSONObject(jsonObject);
				JSONObject serviceparser = (JSONObject) jsonObjEvm.get("GetEmployeeInfoResult");

				// JSONParser parser = new JSONParser();
				// JSONObject serviceparser = (JSONObject)
				// parser.parse(empinfo);
				employeename = (String) serviceparser.get("EmployeeName");
				designation = (String) serviceparser.get("Designation");
				spokecode = (String) serviceparser.get("SpokeCode");
				spokename = (String) serviceparser.get("SpokeName");
				Long departmentid = (Long) serviceparser.get("DepartmentID");
				department = departmentid.intValue();

			} catch (ParseException e) {
				e.printStackTrace();
			}

			try {
				empfeedback.put("empcode", empcode);
				empfeedback.put("empname", employeename);
				empfeedback.put("department", department);
				empfeedback.put("spokecode", spokecode);
				empfeedback.put("designation", designation);
				empfeedback.put("location", spokename);
				List<JSONObject> listempquestion = exitinterviewservice.listempQuestion(stageid);
				empfeedback.put("empfeedbackquestion", listempquestion);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return empfeedback;
	}

	@RequestMapping(value = "/insertempfeedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertempfeedback(@RequestParam("emp_feedback") String feedback, HttpSession session,
			HttpServletRequest request) throws ParseException {
		String empname = null;
		int stageid = 3;
		int resignationId = 0;
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		session = request.getSession();
		String userempcode = (String) session.getAttribute("employeecode");
		// empname = empdetails.getUserDetail(userempcode).getFirstName();
		String sconnectServiceServer = restService.getServiceDetails();
		String getUserDetailServiceUrl = sconnectServiceServer + "GetUserDetail/" + userempcode;
		empname = sconnct.getServiceData(getUserDetailServiceUrl).toString();
		JSONParser jsonParseremp = new JSONParser();
		JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(empname);
		JSONObject jsonObjEmp = new JSONObject(jsonObjectemp);
		String firstname = (String) jsonObjEmp.get("FirstName");
		String lastname = (String) jsonObjEmp.get("LastName");
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
				feedbackemp.setFeedbackBy(firstname);
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
		String empcode = (String) session.getAttribute("employeecode");
		int roleid = (Integer) session.getAttribute("roleid");
		int resignationId = 0;
		int stageid = 4;// stage of application employee feedback
		JSONObject empratingfeedback = new JSONObject();
		TblUserResignation resignedUser = resignationService.getResignationUserService(empcode, 7);
		resignationId = resignedUser.getResignationId();
		List<TblFeedbacks> feedbackanswer = exitinterviewservice.getEmpFeedback(resignationId, stageid);
		if (!feedbackanswer.isEmpty()) {
			empratingfeedback.put("feedbackdetails", null);
		} else {
			try {
				List<JSONObject> listempquestion = exitinterviewservice.listempQuestion(stageid);
				empratingfeedback.put("empratingfeedbackquestion", listempquestion);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return empratingfeedback;
	}

	@RequestMapping(value = "/insertempratingfeedback", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject insertempratingfeedback(@RequestParam("emprating_feedback") String feedback, HttpSession session,
			HttpServletRequest request) throws ParseException {
		String empname = "";
		int stageid = 4;
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();

		session = request.getSession();
		String userempcode = (String) session.getAttribute("employeecode");
		/*
		 * try { empname = empdetails.getUserDetail(userempcode).getFirstName();
		 * 
		 * } catch (RemoteException e1) { e1.printStackTrace(); }
		 */
		String sconnectServiceServer = restService.getServiceDetails();
		String getUserDetailServiceUrl = sconnectServiceServer + "GetUserDetail/" + userempcode;
		empname = sconnct.getServiceData(getUserDetailServiceUrl).toString();
		JSONParser jsonParseremp = new JSONParser();
		JSONObject jsonObjectemp = (JSONObject) jsonParseremp.parse(empname);
		JSONObject jsonObjEmp = new JSONObject(jsonObjectemp);
		String firstname = (String) jsonObjEmp.get("FirstName");
		String lastname = (String) jsonObjEmp.get("LastName");
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
				feedbackemprating.setFeedbackBy(firstname);
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
		try {
			String empcode = request.getParameter("employeecode");
			TblUserResignation resignationbean = resignationService.getResignationUserService(empcode, 5);
			int resignationId = resignationbean.getResignationId();
			empstatus = noduesservice.getNoDuesPendingStatus(resignationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empstatus;
	}

	@RequestMapping(value = "/getassets", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getnoduesassets(HttpServletRequest request) {
		JSONObject assetsmodal = null;
		String empcode = request.getParameter("employee_code");
		String departmentid = request.getParameter("department");
		int assetdepartment = Integer.parseInt(departmentid);
		JSONObject noFoundAssets = new JSONObject();
		String empassets = null;
		String barcodeno = null;
		String assetname = null;
		Integer department = 0;
		Long departmentId = null;
		String msg = "No Assets Allocated";
		List list = new ArrayList();
		list.add(msg);
		// assetsmodal.put("name", "No Assets Allocated");
		JSONObject jsonassets = new JSONObject();
		ArrayList<JSONObject> arrlist = new ArrayList<JSONObject>();
		List<String> listvalue = new ArrayList<String>();
		String[] keys = { "empcode" };
		String[] value = { empcode };
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		/*
		 * try { empassets = empdetails.enterPriseDataService("Asset",
		 * "ASSETINFO", keys, value); } catch (RemoteException e) {
		 * e.printStackTrace(); }
		 */

		String getInputAssets = " {\"Service\": \"Asset\",\"Operation\": \"ASSETINFO\",  \"Keys\": [\"EMPCODE\"],\"Values\":[\"employeeCode\"]}";
		String AssetsInfo = getInputAssets.replace("employeeCode", empcode);
		String sconnectServiceServer = restService.getServiceDetails();
		String assetsServiceUrl = sconnectServiceServer + "GetAssetDetails";
		empassets = sconnct.getPostServiceData(assetsServiceUrl, AssetsInfo).toString();

		// if()
		try {
			JSONArray serviceparser = null;
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(empassets);
			JSONObject hsonService = new JSONObject(jsonObject);
			serviceparser = (JSONArray) hsonService.get("GetAssetDetailsResult");
			boolean assetPresent = false;
			if (!serviceparser.isEmpty()) {
				for (Object str : serviceparser) {
					JSONObject jsondata = (JSONObject) str;
					departmentId = (Long) jsondata.get("Department_Id");
					department = (int) (long) departmentId;/* assetdepartment */
					if (department == assetdepartment) {
						assetname = (String) jsondata.get("Asset_Name");
						barcodeno = (String) jsondata.get("Barcode_No");
						// Set<String> assetbarcode = new
						// HashSet<String>(Arrays.asList(barcodeno));
						assetsmodal = new JSONObject();
						assetsmodal.put("name", assetname);
						assetsmodal.put("DepartmentId", departmentId);
						assetsmodal.put("barcodeno", barcodeno);
						arrlist.add(assetsmodal);
						assetPresent = true;
					}
				}
			}

			if (!assetPresent) {
				JSONObject errorObj = new JSONObject();
				jsonassets.put("error", msg);
			}

			jsonassets.put("assets", arrlist);

		} catch (ParseException e) {
			logger.error("Nodues Assets Details" + e.getMessage());
			e.printStackTrace();
		}
		return jsonassets;
	}

	@RequestMapping(value = "/empfeedbackquestion", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getempfeedbackq(HttpSession session, HttpServletRequest request) {
		// ISoftAgeEnterpriseProxy empdetails = new ISoftAgeEnterpriseProxy();
		int resignationId = 0;
		String msg = "Employee has not given feedback";
		JSONObject empfeedback = new JSONObject();
		List<JSONObject> listarray = new ArrayList<JSONObject>();
		String empcode = (String) request.getParameter("employeecode");
		TblUserResignation resignationbean = resignationService.getResignationUserService(empcode, 7);
		resignationId = resignationbean.getResignationId();
		int stageid1 = 3;
		int stageid2 = 4;
		try {
			List<TblFeedbacks> employeefeedback = exitinterviewservice.listempfeedback(resignationId, stageid1,
					stageid2);
			boolean questionPresent = false;
			if (!employeefeedback.isEmpty()) {
				for (TblFeedbacks answers : employeefeedback) {
					JSONObject anslist = new JSONObject();
					anslist.put("empfeedbackans", answers.getAnsText());
					anslist.put("empfeedbackqes", answers.getMstQuestions().getQuestionText());
					listarray.add(anslist);
					questionPresent = true;
				}
			}
			if (!questionPresent) {
				JSONObject errorObj = new JSONObject();
				empfeedback.put("error", msg);

			}
			empfeedback.put("empfeedbackanslist", listarray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empfeedback;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject sendResetPassword(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String email = (String) request.getParameter("email");
		String returnedMessage = null;
		boolean mailExists = exemployeeservice.emailExists(email, 8);
		TblResetPassword uuid_entry = null;
		if (mailExists) {
			try {
				UUID uuid = UUID.randomUUID();
				String randomUUIDString = uuid.toString();
				String applicationURL = exemployeeservice.getAppUrlLink();
				// String
				// url="http://localhost:8080/hrms/pwdReset?id="+randomUUIDString;
				applicationURL = applicationURL + "/" + randomUUIDString;
				uuid_entry = exemployeeservice.resetPasswordModel(email);
				if (uuid_entry == null) {
					uuid_entry = new TblResetPassword();
					uuid_entry.setUser_email(email);
					uuid_entry.setUserkey(randomUUIDString);
					uuid_entry.setKey_status(1);
				} else {
					uuid_entry.setUserkey(randomUUIDString);
				}
				String saveMessgae = exemployeeservice.saveResetPwdModel(uuid_entry);
				if (saveMessgae.equals("success")) {
					String message = "Hi, As per your request the link for resetting the password is :"
							+ applicationURL;
					mailService.sendEmail(email, "evm@softageindia.com", "RESET USER PASSWORD", message);
					returnedMessage = "Password reset link sent to the specified email";
				} else {
					returnedMessage = "Unable to generate the email token";
				}
			} catch (Exception e) {
				returnedMessage = "Error in sending the password link to the specified email";

				System.out.println(e.getMessage());
			}
		} else {
			returnedMessage = "Specified email does not exist in our records";
		}
		jsonObject.put("messageValue", returnedMessage);
		return jsonObject;
	}

	@RequestMapping(value = "/pwdReset/{uid}", method = RequestMethod.GET)
	public String getResetPasswordPage(@PathVariable("uid") String uid, Model model) {
		model.addAttribute("uniqueID", uid);
		return "resetPassword";
	}

	@RequestMapping(value = "/passwordChange", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject passwordChange(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String msg = "Password Updated";
		String uuid = (String) request.getParameter("uuid");
		String password = (String) request.getParameter("changedPassword");
		boolean isUUIDExists = exemployeeservice.checkID(uuid);
		if (isUUIDExists) {
			String updatePassword = exemployeeservice.saveUpdatedPassword(password, uuid);
			if (updatePassword.equals("success")) {
				jsonObject.put("msg", msg);
			}
		}
		return jsonObject;
	}

	@RequestMapping(value = "/empSearchInfo", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject empSearchInfo(HttpServletRequest request) {
		JSONObject getdata = new JSONObject();
		String empCode = request.getParameter("empCode");
		getdata = searchService.getDetails(empCode);
		return getdata;
	}
}
