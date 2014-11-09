package info.happyretired.communicator;

import info.happyretired.model.BlogPostItem;

import java.util.ArrayList;

public interface ProfileCommunicator {
	public void goToPage(int page);
	
	public void respond(int currentItem, ArrayList list);
}
