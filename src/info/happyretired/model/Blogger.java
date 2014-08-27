package info.happyretired.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Blogger implements Parcelable {
	
	private String userid;
	private String user_name;
	private String blog_name;
	private String last_post_title;
	private String last_post_time;
	private String avatar_url;
	private String total_article;
	private String self_intro;
	private String update_remark;
	private String listType;
	private String category_name;
	private String view;
	private String coverUrl;
	private String refNo;
	private String content;
	private String shareUrl;
	private String advertisementImgUrl;
	
	public Blogger(){
	
	}
	public Blogger(Parcel in){
		readFromParcel(in);
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
	
		userid = in.readString();
		user_name = in.readString();
		blog_name = in.readString();
		last_post_title = in.readString();
		last_post_time = in.readString();
		avatar_url = in.readString();
		total_article = in.readString();
		self_intro = in.readString();
		update_remark = in.readString();
		listType = in.readString();
		category_name = in.readString();
		view = in.readString();
		coverUrl = in.readString();
		refNo = in.readString();
		content = in.readString();
		shareUrl= in.readString();
		advertisementImgUrl = in.readString();
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(userid);
		dest.writeString(user_name);
		dest.writeString(blog_name);
		dest.writeString(last_post_title);
		dest.writeString(last_post_time);
		dest.writeString(avatar_url);
		dest.writeString(total_article);
		dest.writeString(self_intro);
		dest.writeString(update_remark);
		dest.writeString(listType);
		dest.writeString(category_name);
		dest.writeString(view);
		dest.writeString(coverUrl);
		dest.writeString(refNo);
		dest.writeString(content);
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

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getBlog_name() {
		return blog_name;
	}
	public void setBlog_name(String blog_name) {
		this.blog_name = blog_name;
	}
	public String getLast_post_title() {
		return last_post_title;
	}
	public void setLast_post_title(String last_post_title) {
		this.last_post_title = last_post_title;
	}
	public String getLast_post_time() {
		return last_post_time;
	}
	public void setLast_post_time(String last_post_time) {
		this.last_post_time = last_post_time;
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}
	public String getTotal_article() {
		return total_article;
	}
	public void setTotal_article(String total_article) {
		this.total_article = total_article;
	}
	public String getSelf_intro() {
		return self_intro;
	}
	public void setSelf_intro(String self_intro) {
		this.self_intro = self_intro;
	}
	public String getUpdate_remark() {
		return update_remark;
	}
	public void setUpdate_remark(String update_remark) {
		this.update_remark = update_remark;
	}



	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public Blogger createFromParcel(Parcel in) {
			return new Blogger(in);
		}
		public Blogger[] newArray(int size) {
			return new Blogger[size];
		}
	};
	
	public void assignToItem(int i, JSONObject jsonObject) throws Exception{
    
    	try{
	    	this.setUserid(jsonObject.getString("userid"));
	    	this.setUser_name(jsonObject.getString("userName"));
	    	this.setLast_post_title(jsonObject.getString("title"));
	    	this.setCategory_name(jsonObject.getString("category_name"));
	    	this.setLast_post_time(jsonObject.getString("post_time"));
	    	this.setView(jsonObject.getString("hits"));
	    	this.setCoverUrl(jsonObject.getString("cover_url"));
	    	this.setRefNo(jsonObject.getString("refNo"));
	    	this.setListType("");
	    	this.setShareUrl(jsonObject.getString("shareUrl"));
	    	this.setAdvertisementImgUrl(jsonObject.getString("advertisementImgUrl"));
	    }
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
