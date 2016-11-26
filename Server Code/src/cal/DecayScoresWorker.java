package cal;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import dal.dao.DomainImagesDao;
import dal.dto.DomainImageScores;
import dal.exceptions.DomainImagesException;

public class DecayScoresWorker extends WorkerBase implements Runnable {

	private final DomainImagesDao m_dalImages;
	private static Logger logger = Logger.getLogger(DecayScoresWorker.class);
	
	public DecayScoresWorker(long interval) {
		super(interval);
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
		
		List<DomainImageScores> imagesList = Collections.emptyList();
		try {

			while (!stopRequested) {
				
				String oldest = Long.toString((new DateTime().minusHours(1)).getMillis());
				try {
					imagesList = m_dalImages.findDecayScore(oldest);
					
					for(DomainImageScores dto: imagesList)
					{
						logger.info("Worker Decay Score: processing purchase - Image: " + dto.getID());
						//set score	
						dto.setScore(ImageScore.calculateScore(dto));
					}
					
					m_dalImages.batchUpdateScores(imagesList);
					
				} catch (DomainImagesException e) {
					logger.error(e, e);
				} catch (Exception e) {
					logger.error(e,e);
				}finally {
					imagesList.clear();
				}
				
				if(!stopRequested)
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException iex) {
			logger.error(iex, iex);
		}
	}

}
