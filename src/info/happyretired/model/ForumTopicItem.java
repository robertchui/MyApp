package info.happyretired.model;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class ForumTopicItem implements Parcelable {
	
	private String thread;
	private String id;
	private String subject;
	private String category_name;
	private String posts;
	private String hits;
	private String first_post_guest_name;
	private String first_post_userid;
	private String last_post_guest_name;
	private String last_post_userid;
	private String last_post_time;
	private String first_avatar_url;
	private String last_avatar_url;
	private String message;
	
	private String listType;
	private String coverImgUrl;
	private String shareUrl;
	
	private String advertisementImgUrl;
	private String advertisementUrl;
	
	public ForumTopicItem(){
	
	}
	public ForumTopicItem(Parcel in){
		readFromParcel(in);
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
		thread = in.readString();
		id = in.readString();
		subject = in.readString();
		category_name = in.readString();
		posts = in.readString();
		hits = in.readString();
		first_post_guest_name = in.readString();
		first_post_userid = in.readString();
		last_post_guest_name = in.readString();
		last_post_userid = in.readString();
		last_post_time = in.readString();
		first_avatar_url = in.readString();
		last_avatar_url = in.readString();
		message= in.readString();
		listType= in.readString();
		coverImgUrl= in.readString();
		shareUrl= in.readString();
		advertisementImgUrl= in.readString();
		advertisementUrl= in.readString();
		}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(thread);
		dest.writeString(id);
		dest.writeString(subject);
		dest.writeString(category_name);
		dest.writeString(posts);
		dest.writeString(hits);
		dest.writeString(first_post_guest_name);
		dest.writeString(first_post_userid);
		dest.writeString(last_post_guest_name);
		dest.writeString(last_post_userid);
		dest.writeString(last_post_time);
		dest.writeString(first_avatar_url);
		dest.writeString(last_avatar_url);
		dest.writeString(message);
		dest.writeString(listType);
		dest.writeString(coverImgUrl);
		dest.writeString(shareUrl);
		dest.writeString(advertisementImgUrl);
		dest.writeString(advertisementUrl);
	}
	
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getAdvertisementImgUrl() {
		return advertisementImgUrl;
	}
	public void setAdvertisementImgUrl(String advertisementImgUrl) {
		this.advertisementImgUrl = advertisementImgUrl;
	}
	public String getAdvertisementUrl() {
		return advertisementUrl;
	}
	public void setAdvertisementUrl(String advertisementUrl) {
		this.advertisementUrl = advertisementUrl;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getCoverImgUrl() {
		return coverImgUrl;
	}
	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getPosts() {
		return posts;
	}
	public void setPosts(String posts) {
		this.posts = posts;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	public String getFirst_post_guest_name() {
		return first_post_guest_name;
	}
	public void setFirst_post_guest_name(String first_post_guest_name) {
		this.first_post_guest_name = first_post_guest_name;
	}
	public String getFirst_post_userid() {
		return first_post_userid;
	}
	public void setFirst_post_userid(String first_post_userid) {
		this.first_post_userid = first_post_userid;
	}
	public String getLast_post_guest_name() {
		return last_post_guest_name;
	}
	public void setLast_post_guest_name(String last_post_guest_name) {
		this.last_post_guest_name = last_post_guest_name;
	}
	public String getLast_post_userid() {
		return last_post_userid;
	}
	public void setLast_post_userid(String last_post_userid) {
		this.last_post_userid = last_post_userid;
	}
	public String getLast_post_time() {
		return last_post_time;
	}
	public void setLast_post_time(String last_post_time) {
		this.last_post_time = last_post_time;
	}
	public String getFirst_avatar_url() {
		return first_avatar_url;
	}
	public void setFirst_avatar_url(String first_avatar_url) {
		this.first_avatar_url = first_avatar_url;
	}
	public String getLast_avatar_url() {
		return last_avatar_url;
	}
	public void setLast_avatar_url(String last_avatar_url) {
		this.last_avatar_url = last_avatar_url;
	}

	


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}




	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public ForumTopicItem createFromParcel(Parcel in) {
			return new ForumTopicItem(in);
		}
		public ForumTopicItem[] newArray(int size) {
			return new ForumTopicItem[size];
		}
	};

	 
	 public void assignToItem(int i, JSONObject jsonObject) throws Exception{
	    	
		 try{
			this.setId(jsonObject.getString("thread"));
		 	this.setId(jsonObject.getString("id"));
		 	this.setSubject(jsonObject.getString("subject"));
		 	this.setFirst_post_guest_name(jsonObject.getString("first_post_guest_name"));
		 	this.setLast_post_guest_name(jsonObject.getString("last_post_guest_name"));
		 	this.setPosts(jsonObject.getString("posts"));
		 	this.setHits(jsonObject.getString("hits"));
		 	this.setLast_post_time(jsonObject.getString("last_post_time"));
		 	this.setCategory_name(jsonObject.getString("category_name"));
		 	this.setLast_avatar_url(jsonObject.getString("last_avatar_url"));
	    	this.setCoverImgUrl(jsonObject.getString("coverImgUrl"));
	    	this.setShareUrl(jsonObject.getString("shareUrl"));
	    	this.setAdvertisementImgUrl(jsonObject.getString("advertisementImageUrl"));
	    	this.setAdvertisementUrl(jsonObject.getString("advertisementUrl"));
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	 }
}
