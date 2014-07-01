package info.happyretired.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class JobItem implements Parcelable {
	
	private String postdate;
	private String refNo;
    private String title;
    private String companyName;
    private String companyImgUrl;
    private String companyUrl;
    private String companyDesc;
    private String jobDesc1;
    private String jobDesc2;
    private String jobDesc3;
    private String jobDesc4;
    private String jobDesc5;
    
    private String jobReq1;
    private String jobReq2;
    private String jobReq3;
    private String jobReq4;
    private String jobReq5;
    
    private String applyDesc;
    private String jobcategory_name;
    private String jobnature_name;
    private String locationDesc;
    private String eduDesc;
    private String working_exp;
    private String working_period;
    private String hour_per_day;
    private String day_per_week;
    private String salary;
    private String salary_type;
    private String benefit;
    private String employment_nature;
    private String employment_type;
    private String vacancy;
    private String effectiveTo;
    private String displayLang;
    private String viewCount;
    
    private String shareUrl;
	
	public JobItem(){
	
	}
	public JobItem(Parcel in){
		readFromParcel(in);
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
		postdate = in.readString();
		refNo = in.readString();
		title = in.readString();
		companyName = in.readString();
		companyImgUrl = in.readString();
		companyUrl = in.readString();
		companyDesc = in.readString();
		jobDesc1 = in.readString();
		jobDesc2 = in.readString();
		jobDesc3 = in.readString();
		jobDesc4 = in.readString();
		jobDesc5 = in.readString();
		
		jobReq1 = in.readString();
		jobReq2 = in.readString();
		jobReq3 = in.readString();
		jobReq4 = in.readString();
		jobReq5 = in.readString();
		
		applyDesc = in.readString();
		jobcategory_name = in.readString();
		jobnature_name = in.readString();
		locationDesc = in.readString();
		eduDesc = in.readString();
		working_exp = in.readString();
		working_period = in.readString();
		hour_per_day = in.readString();
		day_per_week = in.readString();
		salary = in.readString();
		salary_type = in.readString();
		benefit = in.readString();
		employment_nature = in.readString();
		employment_type = in.readString();
		vacancy = in.readString();
		effectiveTo = in.readString();
		displayLang = in.readString();
		viewCount = in.readString();
		shareUrl= in.readString();
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(postdate);    
		dest.writeString(refNo);    
	    dest.writeString(title);           
	    dest.writeString(companyName);
	    dest.writeString(companyImgUrl);    
	    dest.writeString(companyUrl);       
	    dest.writeString(companyDesc);      
	    dest.writeString(jobDesc1);         
	    dest.writeString(jobDesc2);        
	    dest.writeString(jobDesc3);        
	    dest.writeString(jobDesc4);        
	    dest.writeString(jobDesc5);        
	                  
	    dest.writeString(jobReq1);          
	    dest.writeString(jobReq2);          
	    dest.writeString(jobReq3);          
	    dest.writeString(jobReq4);          
	    dest.writeString(jobReq5);          
	              
	    dest.writeString(applyDesc);        
	    dest.writeString(jobcategory_name); 
	    dest.writeString(jobnature_name);   
	    dest.writeString(locationDesc);     
	    dest.writeString(eduDesc);          
	    dest.writeString(working_exp);      
	    dest.writeString(working_period);   
	    dest.writeString(hour_per_day);     
	    dest.writeString(day_per_week);     
	    dest.writeString(salary);           
	    dest.writeString(salary_type);      
	    dest.writeString(benefit);          
	    dest.writeString(employment_nature);
	    dest.writeString(employment_type);  
	    dest.writeString(vacancy);          
	    dest.writeString(effectiveTo);      
	    dest.writeString(displayLang);      
	    dest.writeString(viewCount);
	    dest.writeString(shareUrl);
	}



	
	
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyImgUrl() {
		return companyImgUrl;
	}
	public void setCompanyImgUrl(String companyImgUrl) {
		this.companyImgUrl = companyImgUrl;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public String getCompanyDesc() {
		return companyDesc;
	}
	public void setCompanyDesc(String companyDesc) {
		this.companyDesc = companyDesc;
	}
	public String getJobDesc1() {
		return jobDesc1;
	}
	public void setJobDesc1(String jobDesc1) {
		this.jobDesc1 = jobDesc1;
	}
	public String getJobDesc2() {
		return jobDesc2;
	}
	public void setJobDesc2(String jobDesc2) {
		this.jobDesc2 = jobDesc2;
	}
	public String getJobDesc3() {
		return jobDesc3;
	}
	public void setJobDesc3(String jobDesc3) {
		this.jobDesc3 = jobDesc3;
	}
	public String getJobDesc4() {
		return jobDesc4;
	}
	public void setJobDesc4(String jobDesc4) {
		this.jobDesc4 = jobDesc4;
	}
	public String getJobDesc5() {
		return jobDesc5;
	}
	public void setJobDesc5(String jobDesc5) {
		this.jobDesc5 = jobDesc5;
	}
	public String getJobReq1() {
		return jobReq1;
	}
	public void setJobReq1(String jobReq1) {
		this.jobReq1 = jobReq1;
	}
	public String getJobReq2() {
		return jobReq2;
	}
	public void setJobReq2(String jobReq2) {
		this.jobReq2 = jobReq2;
	}
	public String getJobReq3() {
		return jobReq3;
	}
	public void setJobReq3(String jobReq3) {
		this.jobReq3 = jobReq3;
	}
	public String getJobReq4() {
		return jobReq4;
	}
	public void setJobReq4(String jobReq4) {
		this.jobReq4 = jobReq4;
	}
	public String getJobReq5() {
		return jobReq5;
	}
	public void setJobReq5(String jobReq5) {
		this.jobReq5 = jobReq5;
	}
	public String getApplyDesc() {
		return applyDesc;
	}
	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}
	public String getJobcategory_name() {
		return jobcategory_name;
	}
	public void setJobcategory_name(String jobcategory_name) {
		this.jobcategory_name = jobcategory_name;
	}
	public String getJobnature_name() {
		return jobnature_name;
	}
	public void setJobnature_name(String jobnature_name) {
		this.jobnature_name = jobnature_name;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public String getEduDesc() {
		return eduDesc;
	}
	public void setEduDesc(String eduDesc) {
		this.eduDesc = eduDesc;
	}
	public String getWorking_exp() {
		return working_exp;
	}
	public void setWorking_exp(String working_exp) {
		this.working_exp = working_exp;
	}
	public String getWorking_period() {
		return working_period;
	}
	public void setWorking_period(String working_period) {
		this.working_period = working_period;
	}
	public String getHour_per_day() {
		return hour_per_day;
	}
	public void setHour_per_day(String hour_per_day) {
		this.hour_per_day = hour_per_day;
	}
	public String getDay_per_week() {
		return day_per_week;
	}
	public void setDay_per_week(String day_per_week) {
		this.day_per_week = day_per_week;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getSalary_type() {
		return salary_type;
	}
	public void setSalary_type(String salary_type) {
		this.salary_type = salary_type;
	}
	public String getBenefit() {
		return benefit;
	}
	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}
	public String getEmployment_nature() {
		return employment_nature;
	}
	public void setEmployment_nature(String employment_nature) {
		this.employment_nature = employment_nature;
	}
	public String getEmployment_type() {
		return employment_type;
	}
	public void setEmployment_type(String employment_type) {
		this.employment_type = employment_type;
	}
	public String getVacancy() {
		return vacancy;
	}
	public void setVacancy(String vacancy) {
		this.vacancy = vacancy;
	}
	public String getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(String effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	public String getDisplayLang() {
		return displayLang;
	}
	public void setDisplayLang(String displayLang) {
		this.displayLang = displayLang;
	}
	public String getViewCount() {
		return viewCount;
	}
	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}





	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public JobItem createFromParcel(Parcel in) {
			return new JobItem(in);
		}
		public JobItem[] newArray(int size) {
			return new JobItem[size];
		}
	};
	
	 public void assignToItem(JSONObject jsonObject) throws Exception{
		 	this.setPostdate(jsonObject.getString("postdate"));
	    	this.setRefNo(jsonObject.getString("refNo"));
	    	this.setTitle(jsonObject.getString("title"));
	    	this.setCompanyName(jsonObject.getString("companyName"));
	    	this.setCompanyImgUrl(jsonObject.getString("companyImgUrl"));
	    	this.setCompanyUrl(jsonObject.getString("companyUrl"));
	    	this.setCompanyDesc(jsonObject.getString("companyDesc"));
	    	this.setJobDesc1(jsonObject.getString("jobDesc1"));
	    	this.setJobDesc2(jsonObject.getString("jobDesc2"));
	    	this.setJobDesc3(jsonObject.getString("jobDesc3"));
	    	this.setJobDesc4(jsonObject.getString("jobDesc4"));
	    	this.setJobDesc5(jsonObject.getString("jobDesc5"));
	    	
	    	this.setJobReq1(jsonObject.getString("jobReq1"));
	    	this.setJobReq2(jsonObject.getString("jobReq2"));
	    	this.setJobReq3(jsonObject.getString("jobReq3"));
	    	this.setJobReq4(jsonObject.getString("jobReq4"));
	    	this.setJobReq5(jsonObject.getString("jobReq5"));
	    	
	    	this.setApplyDesc(jsonObject.getString("applyDesc"));
	    	this.setJobcategory_name(jsonObject.getString("jobcategory_name"));
	    	this.setJobnature_name(jsonObject.getString("jobnature_name"));
	    	this.setLocationDesc(jsonObject.getString("locationDesc"));
	    	this.setEduDesc(jsonObject.getString("eduDesc"));
	    	this.setWorking_exp(jsonObject.getString("working_exp"));
	    	this.setWorking_period(jsonObject.getString("working_period"));
	    	this.setHour_per_day(jsonObject.getString("hour_per_day"));
	    	this.setDay_per_week(jsonObject.getString("day_per_week"));
	    	this.setSalary(jsonObject.getString("salary"));
	    	this.setSalary_type(jsonObject.getString("salary_type"));
	    	this.setBenefit(jsonObject.getString("benefit"));
	    	this.setEmployment_nature(jsonObject.getString("employment_nature"));
	    	this.setEmployment_type(jsonObject.getString("employment_type"));
	    	this.setVacancy(jsonObject.getString("vacancy"));
	    	this.setEffectiveTo(jsonObject.getString("effectiveTo"));
	    	this.setDisplayLang(jsonObject.getString("displayLang"));
	    	this.setViewCount(jsonObject.getString("viewCount"));
	    	this.setShareUrl(jsonObject.getString("shareUrl"));
	    }
}
