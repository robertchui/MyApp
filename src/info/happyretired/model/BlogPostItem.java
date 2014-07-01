package info.happyretired.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BlogPostItem implements Parcelable {
	
	private String id;
	private String bloggerName;
	private String title;
	private String coverUrl;
	private String hits;
	private String category_name;
	private String content;
	private String refNo;
	private String post_time;
	private String listType;
	
	public BlogPostItem(){
	
	}
	public BlogPostItem(Parcel in){
		readFromParcel(in);
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
		id = in.readString();
		bloggerName = in.readString();
		title = in.readString();
		coverUrl = in.readString();
		hits = in.readString();
		category_name = in.readString();
		content = in.readString();
		refNo = in.readString();
		post_time = in.readString();
		listType = in.readString();
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(bloggerName);
		dest.writeString(title);
		dest.writeString(coverUrl);
		dest.writeString(hits);
		dest.writeString(category_name);
		dest.writeString(content);
		dest.writeString(refNo);
		dest.writeString(post_time);
		dest.writeString(listType);
	}

	
	public String getPost_time() {
		return post_time;
	}
	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBloggerName() {
		return bloggerName;
	}
	public void setBloggerName(String bloggerName) {
		this.bloggerName = bloggerName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
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
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}








	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public BlogPostItem createFromParcel(Parcel in) {
			return new BlogPostItem(in);
		}
		public BlogPostItem[] newArray(int size) {
			return new BlogPostItem[size];
		}
	};
}
