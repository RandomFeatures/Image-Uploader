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
import dal.dao.DomainViewsDao;
import dal.dto.DomainImageViews;
import dal.dto.DomainUsers;
import dal.dto.DomainViews;
import dal.exceptions.DomainImagesException;
import dal.exceptions.DomainUsersException;
import dal.exceptions.DomainViewsException;

public class ViewsWorker extends WorkerBase implements Runnable {

	
	private final DomainViewsDao m_dalViews;
	private final DomainUsersDao m_dalUsers;
	private final DomainImagesDao m_dalImages;
	private static Logger logger = Logger.getLogger(ViewsWorker.class);
	
	public ViewsWorker(long interval) {
		super(interval);
		
		m_dalViews = new DomainViewsDao();
		m_dalUsers = new DomainUsersDao();
		m_dalImages = new DomainImagesDao();
	}
	
	@Override
	public ViewsWorker withDomain(String domain) {
		this.addDomain(domain);
		return this;
	}
	
	@Override
	public ViewsWorker withParseDomains(String domains) {
		parseDomain(domains);
		return this;
	}



	public void run() {
		boolean novotes = true;
		List<DomainViews> viewsList = Collections.emptyList();
		HashMap<String, Integer> imageViews = new HashMap<String, Integer>();
		try {

			while (!stopRequested) {

				for (String domain : m_Domains) {
					// get all records from domain
					try {
						viewsList = m_dalViews.findAll(domain);

						// loop through all images
						for (DomainViews view : viewsList) {
							try {
								// found some stuff
								novotes = false;
								logger.info("Worker View: processing view - User:" + view.getUserID() + " Image: " + view.getImageID());
								
								try {
									// update this user as viewing this image
									DomainUsers dto = m_dalUsers.findWhereUserIDEquals(view.getUserID());

									dto.setCurrentImage(view.getImageID(), "");
									dto.setLastUpdated(new DateTime());

									m_dalUsers.update(dto);
								} catch (DomainUsersException e) {
									logger.error(e,e);
								}

								// place records in hashmap to weed out dupes
								// and count
								if (imageViews.containsKey(view.getImageID())) {
									int val = (imageViews.get(view.getImageID())) + 1;
									imageViews.put(view.getImageID(), val);
								} else
									imageViews.put(view.getImageID(), 1);
							} catch (Exception e) {
								logger.error(e,e);
							}

						}
						//go through all the images and update the db
						Iterator<Entry<String, Integer>> it = imageViews.entrySet().iterator();
						while (it.hasNext()) {
							Entry<String, Integer> pairs = it.next();
							String imgID = pairs.getKey();
							int count = pairs.getValue();
							it.remove(); // avoids a
											// ConcurrentModificationException

							try {
								// update image views
								DomainImageViews dtoImages = m_dalImages.findViewsWhereIDEquals(imgID);
								//make sure we found the image first
								if(dtoImages != null) 
								{
									dtoImages.setViews(dtoImages.getViews() + count);
									m_dalImages.update(dtoImages);
	
									// update user who owns image views
									DomainUsers dtoUsers = m_dalUsers.findWhereUserIDEquals(dtoImages.getUserID());
									dtoUsers.setViews(dtoImages.getViews());
									m_dalUsers.update(dtoUsers);
								}
							} catch (DomainImagesException e) {
								logger.error(e,e);
							} catch (DomainUsersException e) {
								logger.error(e,e);
							}
						}
						// clean out views tbl
						m_dalViews.deleteBatch(domain, viewsList);

					} catch (DomainViewsException e) {
						logger.error(e,e);
					} catch (Exception e) {
							logger.error(e,e);
					} finally {
						// clean up
						viewsList.clear();
						imageViews.clear();
					}

				}

				// no votes this time through so rest a bit
				if (novotes && !stopRequested)
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException iex) {
			logger.error(iex,iex);
		}
	}

}
