package info.happyretired.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivityItem implements Parcelable {
	
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
	private String contact;
	private String location;
	private String locationDesc;
	private String vacancy;
	private String category;
	private String categoryDesc;
	private String fee;
	private String address;
	private String url;
	private String viewCount;
	private String effectiveTo;
	private String contact_no;
	private String company_name;
	private String shareUrl;
	private String advertisementImgUrl;
	
	public ActivityItem(){
	
	}
	public ActivityItem(Parcel in){
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
		  contact= in.readString();
		  location= in.readString();
		  locationDesc= in.readString();
		  vacancy= in.readString();
		  category= in.readString();
		  categoryDesc= in.readString();
		  fee= in.readString();
		  address= in.readString();
		  url= in.readString();
		  viewCount= in.readString();
		  effectiveTo= in.readString();
		  contact_no = in.readString();
		  company_name= in.readString();
		  shareUrl= in.readString();
		  advertisementImgUrl = in.readString();
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
		dest.writeString(contact);
		dest.writeString(location);
		dest.writeString(locationDesc);
		dest.writeString(vacancy);
		dest.writeString(category);
		dest.writeString(categoryDesc);
		dest.writeString(fee);
		dest.writeString(address);
		dest.writeString(url);
		dest.writeString(viewCount);
		dest.writeString(effectiveTo);
		dest.writeString(contact_no);
		dest.writeString(company_name);
		dest.writeString(shareUrl);
		dest.writeString(advertisementImgUrl);
		
	}

	
	public String getAdvertisementImgUrl() {
		return advertisementImgUrl;
	}
	public void setAdvertisementImgUrl(String advertisementImgUrl) {
		this.advertisementImgUrl = advertisementImgUrl;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getContact_no() {
		return contact_no;
	}
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}
	public String getEffectiveTo() {
		return effectiveTo;
	}
	public void setEffectiveTo(String effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	public String getLocationDesc() {
		return locationDesc;
	}


	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}


	public String getCategoryDesc() {
		return categoryDesc;
	}


	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
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


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getFee() {
		return fee;
	}


	public void setFee(String fee) {
		this.fee = fee;
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
	
	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public ActivityItem createFromParcel(Parcel in) {
			return new ActivityItem(in);
		}
		public ActivityItem[] newArray(int size) {
			return new ActivityItem[size];
		}
	};
	
	 public void assignToItem(int i, JSONObject jsonObject) throws Exception{
		 try{
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
	    	this.setCategory(jsonObject.getString("category"));
	    	this.setCategoryDesc(jsonObject.getString("categoryDesc"));
	    	this.setFee(jsonObject.getString("fee"));
	    	this.setAddress(jsonObject.getString("address"));
	    	this.setUrl(jsonObject.getString("url"));
	    	this.setContact_no(jsonObject.getString("contact_no"));
	    	this.setCompany_name(jsonObject.getString("companyName"));
	    	this.setShareUrl(jsonObject.getString("shareUrl"));
	    	this.setAdvertisementImgUrl(jsonObject.getString("advertisementImgUrl"));
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	 }
}
