package cal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import dal.dao.DomainImageSubmitDao;
import dal.dto.DomainImageSubmit;
import dal.dto.DomainImageSubmit_Attr;
import dal.exceptions.DomainImageSubmitException;

public class SubmitWorker extends WorkerBase implements Runnable {
	
	private static Logger logger = Logger.getLogger(SubmitWorker.class);
	private final DomainImageSubmitDao m_dalSubmit;
	private final ProcessInstagram m_ProcessInstagram;
	
	public SubmitWorker(long interval) {
		super(interval);
		m_dalSubmit = new DomainImageSubmitDao();
		m_ProcessInstagram = new ProcessInstagram();
		  
	}

	@Override
	public SubmitWorker withDomain(String domain) {
		this.addDomain(domain);
		return this;
	}
	
	@Override
	public SubmitWorker withParseDomains(String domains) {
		parseDomain(domains);
		return this;
	}

	
	public void run() {
		boolean nosubmit = true;
		List<DomainImageSubmit> submitList = Collections.emptyList();
		List<DomainImageSubmit> deleteList = new ArrayList<DomainImageSubmit>();
		try {

			while (!stopRequested) {

				for (String domain : m_Domains) {

					try {
						//get all records from domain
						submitList = m_dalSubmit.findAll(domain);

						for(DomainImageSubmit dto: submitList)
						{
							
							
							logger.info("Processing Instagram - User:" + dto.getUserID() + " Image: " + dto.getSourceID());
							//found some stuff
							nosubmit = false;
							//process instagram images
							if(dto.getSource().equals(DomainImageSubmit_Attr.Instagram))
								if(m_ProcessInstagram.process(dto)) 
									//succeful so delete from the db
									deleteList.add(dto);
						}
						
						//delete all process records
						m_dalSubmit.deleteBatch(domain, deleteList);
						
					} catch (DomainImageSubmitException e) {
						logger.error(e,e);
					} catch (Exception e) {
						logger.error(e,e);
					} finally { //clean up
						submitList.clear();
						deleteList.clear();
					}
					
				}
				if(nosubmit && !stopRequested)
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException iex) {
			logger.error(iex,iex);
		}
	}

	
	
	
	

	
}
