package cal;

import org.apache.log4j.Logger;


public class CacheWorker extends WorkerBase implements Runnable {

	private final ImageCache m_ImageCache;
	private static Logger logger = Logger.getLogger(CacheWorker.class);
	
	public CacheWorker(long interval, String servers) {
		super(interval);
		m_ImageCache = new ImageCache(servers);
		
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
		try {

			while (!stopRequested) {
				
				m_ImageCache.newest();
				m_ImageCache.topRanked();
				m_ImageCache.topTags();
				m_ImageCache.newestByTopTag();
				m_ImageCache.topRankedByTopTag();
				
				if(!stopRequested)				
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException e) {
			logger.error(e, e);
		}

	}

}
