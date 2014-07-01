package info.happyretired.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ForumCategoryItem implements Parcelable {
	
	private String id;
	private String name;
	
	public ForumCategoryItem(){
	
	}
	public ForumCategoryItem(Parcel in){
		readFromParcel(in);
	}

	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void readFromParcel(Parcel in) {
		id = in.readString();
		 name= in.readString();
	}
	

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



	public static final Parcelable.Creator CREATOR = 
			new Parcelable.Creator() { 
		public ForumCategoryItem createFromParcel(Parcel in) {
			return new ForumCategoryItem(in);
		}
		public ForumCategoryItem[] newArray(int size) {
			return new ForumCategoryItem[size];
		}
	};
}
