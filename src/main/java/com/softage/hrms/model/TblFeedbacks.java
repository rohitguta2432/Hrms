package com.softage.hrms.model;
// Generated Dec 6, 2016 10:45:57 AM by Hibernate Tools 5.2.0.Beta1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TblFeedbacks generated by hbm2java
 */
@Entity
@Table(name = "tbl_feedbacks")
public class TblFeedbacks implements java.io.Serializable {

	private Integer feedbackId;
	private MstAnswers mstAnswers;
	private MstQuestions mstQuestions;
	private TblUserResignation tblUserResignation;
	private String ansText;
	private String feedbackBy;
	private String feedbackFrom;

	public TblFeedbacks() {
	}

	public TblFeedbacks(MstAnswers mstAnswers, MstQuestions mstQuestions, TblUserResignation tblUserResignation,
			String ansText, String feedbackBy, String feedbackFrom) {
		this.mstAnswers = mstAnswers;
		this.mstQuestions = mstQuestions;
		this.tblUserResignation = tblUserResignation;
		this.ansText = ansText;
		this.feedbackBy = feedbackBy;
		this.feedbackFrom = feedbackFrom;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "feedback_id", unique = true, nullable = false)
	public Integer getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(Integer feedbackId) {
		this.feedbackId = feedbackId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ans_id")
	public MstAnswers getMstAnswers() {
		return this.mstAnswers;
	}

	public void setMstAnswers(MstAnswers mstAnswers) {
		this.mstAnswers = mstAnswers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "q_id")
	public MstQuestions getMstQuestions() {
		return this.mstQuestions;
	}

	public void setMstQuestions(MstQuestions mstQuestions) {
		this.mstQuestions = mstQuestions;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feedback_on")
	public TblUserResignation getTblUserResignation() {
		return this.tblUserResignation;
	}

	public void setTblUserResignation(TblUserResignation tblUserResignation) {
		this.tblUserResignation = tblUserResignation;
	}

	@Column(name = "ans_text", length = 500)
	public String getAnsText() {
		return this.ansText;
	}

	public void setAnsText(String ansText) {
		this.ansText = ansText;
	}

	@Column(name = "feedback_by", length = 15)
	public String getFeedbackBy() {
		return this.feedbackBy;
	}

	public void setFeedbackBy(String feedbackBy) {
		this.feedbackBy = feedbackBy;
	}

	@Column(name = "feedback_from", length = 15)
	public String getFeedbackFrom() {
		return this.feedbackFrom;
	}

	public void setFeedbackFrom(String feedbackFrom) {
		this.feedbackFrom = feedbackFrom;
	}

}
