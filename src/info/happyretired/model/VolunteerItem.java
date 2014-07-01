package info.happyretired.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class VolunteerItem implements Parcelable {
	
	private String refNo;
	private String title;
	private int icon;
	private String count = "0";
	private String dateFrom  = "";
	private String dateTo = "";
	private String timeFrom= "";
	private String timeTo= "";
	private String imageURL= "";
	private String content= "";
	private String content2= "";
	private String contact;
	private String location;
	private String locationDesc;
	private String vacancy;
	private String targetgroup;
	private String targetgroupDesc;
	private String timeofservice;
	private String timeofserviceDesc;
	private String address;
	private String url;
	private String viewCount;
	private String companyName;
	private String effectiveTo;
	private String shareUrl;
	
	public VolunteerItem(){
	
	}
	public VolunteerItem(Parcel in){
		readFromParcel(in);
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
		refNo = in.readString();
		 title= in.readString();
		icon= in.readInt();
		count = in.readString();
		dateFrom  = in.readString();
		  dateTo= in.readString();
		  timeFrom= in.readString();
		  timeTo= in.readString();
		  imageURL= in.readString();
		  content= in.readString();
		  content2= in.readString();
		  contact= in.readString();
		  location= in.readString();
		  locationDesc= in.readString();
		  vacancy= in.readString();
		  targetgroup= in.readString();
		  targetgroupDesc= in.readString();
		  timeofservice= in.readString();
		  timeofserviceDesc= in.readString();
		  address= in.readString();
		  url= in.readString();
		  viewCount= in.readString();
		  companyName = in.readString();
		  effectiveTo = in.readString();
		  shareUrl= in.readString();
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(refNo);
		dest.writeString(title);
		dest.writeInt(icon);
		dest.writeString(count);
		dest.writeString(dateFrom);
		dest.writeString(dateTo);
		dest.writeString(timeFrom);
		dest.writeString(timeTo);
		dest.writeString(imageURL);
		dest.writeString(content);
		dest.writeString(content2);
		dest.writeString(contact);
		dest.writeString(location);
		dest.writeString(locationDesc);
		dest.writeString(vacancy);
		dest.writeString(targetgroup);
		dest.writeString(targetgroupDesc);
		dest.writeString(timeofservice);
		dest.writeString(timeofserviceDesc);
		dest.writeString(address);
		dest.writeString(url);
		dest.writeString(viewCount);
		dest.writeString(companyName);
		dest.writeString(effectiveTo);
		dest.writeString(shareUrl);
	}

	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(String effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLocationDesc() {
		return locationDesc;
	}


	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}




	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	
	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getVacancy() {
		return vacancy;
	}


	public void setVacancy(String vacancy) {
		this.vacancy = vacancy;
	}




	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getViewCount() {
		return viewCount;
	}


	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}


	public String getTitle(){
		return this.title;
	}
	
	public int getIcon(){
		return this.icon;
	}
	
	public String getCount(){
		return this.count;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
	public void setCount(String count){
		this.count = count;
	}
	
	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getTimeFrom() {
		if(timeFrom!=null && timeFrom.length()==4)
			return (timeFrom.substring(0,2)+":"+timeFrom.substring(2,4));
		else
			return "";
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		if(timeTo!=null && timeTo.length()==4)
			return (timeTo.substring(0,2)+":"+timeTo.substring(2,4));
		else
			return "";
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}


	public String getImageURL() {
		return imageURL;
	}


	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}


	public String getRefNo() {
		return refNo;
	}


	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public String getTargetgroup() {
		return targetgroup;
	}
	public void setTargetgroup(String targetgroup) {
		this.targetgroup = targetgroup;
	}
	public String getTargetgroupDesc() {
		return targetgroupDesc;
	}
	public void setTargetgroupDesc(String targetgroupDesc) {
		this.targetgroupDesc = targetgroupDesc;
	}
	public String getTimeofservice() {
		return timeofservice;
	}
	public void setTimeofservice(String timeofservice) {
		this.timeofservice = timeofservice;
	}
	public String getTimeofserviceDesc() {
		return timeofserviceDesc;
	}
	public void setTimeofserviceDesc(String timeofserviceDesc) {
		this.timeofserviceDesc = timeofserviceDesc;
	}

	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public VolunteerItem createFromParcel(Parcel in) {
			return new VolunteerItem(in);
		}
		public VolunteerItem[] newArray(int size) {
			return new VolunteerItem[size];
		}
	};
	
	public void assignToItem( int i, JSONObject jsonObject) throws Exception{
    	this.setIcon(i+1);
    	this.setRefNo(jsonObject.getString("refNo"));
    	this.setTitle(jsonObject.getString("title"));
    	this.setDateFrom(jsonObject.getString("dateFrom"));
    	this.setDateTo(jsonObject.getString("dateTo"));
    	this.setTimeFrom(jsonObject.getString("timeFrom"));
    	this.setTimeTo(jsonObject.getString("timeTo"));
    	this.setImageURL(jsonObject.getString("imageURL"));
    	this.setLocation(jsonObject.getString("location"));
    	this.setLocationDesc(jsonObject.getString("locationDesc"));
    	this.setTargetgroup(jsonObject.getString("targetgroup"));
    	this.setTargetgroupDesc(jsonObject.getString("targetgroupDesc"));
    	this.setTimeofservice(jsonObject.getString("timeofservice"));
    	this.setTimeofserviceDesc(jsonObject.getString("timeofserviceDesc"));
    	this.setAddress(jsonObject.getString("address"));
    	this.setUrl(jsonObject.getString("url"));
    	this.setCompanyName(jsonObject.getString("companyName"));
    	this.setEffectiveTo(jsonObject.getString("effectiveTo"));
    	this.setVacancy(jsonObject.getString("vacancy"));
    	this.setShareUrl(jsonObject.getString("shareUrl"));
    }
    
}
