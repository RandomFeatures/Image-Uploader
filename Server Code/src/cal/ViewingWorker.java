package cal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import dal.dao.DomainImagesDao;
import dal.dao.DomainUsersDao;
import dal.dto.DomainImageViews;
import dal.dto.DomainUsers;
import dal.exceptions.DomainImagesException;
import dal.exceptions.DomainUsersException;

public class ViewingWorker extends WorkerBase implements Runnable {

	private final DomainUsersDao m_dalUsers;
	private final DomainImagesDao m_dalImages;
	private final int m_AgeLookup;
	private static Logger logger = Logger.getLogger(ViewingWorker.class);
	
	public ViewingWorker(long interval, int age) {
		super(interval);
		m_dalUsers = new DomainUsersDao();
		m_dalImages = new DomainImagesDao();
		m_AgeLookup = age;
	}

	@Override
	public WorkerBase withDomain(String domain) {
		return this;
	}

	@Override
	public WorkerBase withParseDomains(String domains) {
		return this;
	}
	public void run() {
		List<DomainUsers> viewsList = Collections.emptyList();
		HashMap<String, Integer> imageViews = new HashMap<String, Integer>();
		try {

			while (!stopRequested) {
				
				
				try {
					//select users view in last (24) hours
					viewsList = m_dalUsers.findCurrentViewing(Long.toString(new DateTime().minusHours(m_AgeLookup).getMillis()));
					
					//loop through each and count imges viewed
					for(DomainUsers user: viewsList)
					{
						try {
							// place records in hashmap to weed out dupes
							// and count
							if (imageViews.containsKey(user.getCurrentImage())) {
								int val = (imageViews.get(user.getCurrentImage())) + 1;
								imageViews.put(user.getCurrentImage(), val);
							} else
								imageViews.put(user.getCurrentImage(), 1);
						} catch (Exception e) {
							logger.error(e,e);
						}
					}
					
					//lookup each image
					//go through all the images and update the db
					Iterator<Entry<String, Integer>> it = imageViews.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, Integer> pairs = it.next();
						String imgID = pairs.getKey();
						int count = pairs.getValue();
						it.remove(); // avoids a
										// ConcurrentModificationException

						try {
							// update image viewing
							DomainImageViews dtoImages = m_dalImages.findViewsWhereIDEquals(imgID);
							if(dtoImages != null) {
								dtoImages.setViewing(count);
								m_dalImages.update(dtoImages);
	
								// update user who owns image viewing
								DomainUsers dtoUsers = m_dalUsers.findWhereUserIDEquals(dtoImages.getUserID());
								dtoUsers.setViewing(count);
								m_dalUsers.update(dtoUsers);
							}
						} catch (DomainImagesException e) {
							logger.error(e,e);
						} catch (DomainUsersException e) {
							logger.error(e,e);
						}
					}
				} catch (Exception e) {
					logger.error(e,e);
				} finally {
					imageViews.clear();
					viewsList.clear();
				}
				
				if(!stopRequested)
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException iex) {
			logger.error(iex,iex);
		}

	}



}
