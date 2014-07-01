package info.happyretired.communicator;

import info.happyretired.model.BlogPostItem;

import java.util.ArrayList;

public interface FrontCommunicator {
	
	
	public void selectForum();
	public void selectBlog();
	public void selectActivity();
	public void selectVolunteer();
	public void selectJob();
	
	public void selectForum(int currentItem, ArrayList list);
	public void selectBlog(int currentItem, ArrayList list);
	public void selectActivity(int currentItem, ArrayList list);
	public void selectVolunteer(int currentItem, ArrayList list);
	public void selectJob(int currentItem, ArrayList list);

	
}
