package cal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import dal.dao.DomainImagesDao;
import dal.dao.DomainVotePurchaseDao;
import dal.dto.DomainImageScores;
import dal.dto.DomainVotePurchase;
import dal.exceptions.DomainImagesException;
import dal.exceptions.DomainVotePurchaseException;

public class PurchaseWorker extends WorkerBase implements Runnable {

	private static Logger logger = Logger.getLogger(PurchaseWorker.class);
	private final DomainVotePurchaseDao m_dalVotePurchase;
	private final DomainImagesDao m_dalImages;

	
	public PurchaseWorker(long interval) {
		super(interval);
		m_dalVotePurchase = new DomainVotePurchaseDao();
		m_dalImages = new DomainImagesDao();
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
		boolean novotes = true;
		List<DomainVotePurchase> votesList = Collections.emptyList();
		List<DomainImageScores> imagesUpdateList = new ArrayList<DomainImageScores>();
		try {

			while (!stopRequested) {

				try {
					votesList = m_dalVotePurchase.findNew();
					
					for(DomainVotePurchase dto: votesList)
					{
						novotes = false;
						logger.info("Worker Purchase: processing purchase - User:" + dto.getUserID() + " Image: " + dto.getImageID());
						
						dto.setComplete(true);
						
						//find the image record to work with
						DomainImageScores dtoImages = m_dalImages.findScoreWhereIDEquals(dto.getImageID());
						if(dtoImages != null)
						{
							//update the new up votes total
							int rCount = 0;
							rCount = dtoImages.getUpVotes();
							dtoImages.setUpVotes(dto.getVotes() + rCount);
							
							//set score	
							dtoImages.setScore(ImageScore.calculateScore(dtoImages));
							
							//put in the update list
							imagesUpdateList.add(dtoImages);
						}
						
					}
					
					//update image votes
					try {
						m_dalImages.batchUpdateScores(imagesUpdateList);
						
						m_dalVotePurchase.batchUpdate(votesList);
					} catch (DomainImagesException e) {
						logger.error(e,e);
					}

				} catch (DomainVotePurchaseException e) {
					logger.error(e,e);
				} catch (DomainImagesException e) {
					logger.error(e,e);
				} finally {
					votesList.clear();
					imagesUpdateList.clear();
				}
				
				
				
				// no votes this time through so rest a bit
				if (novotes && !stopRequested)
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException iex) {
			logger.error(iex, iex);
		}
	}

}
