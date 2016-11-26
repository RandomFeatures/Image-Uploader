package cal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import dal.dao.DomainImagesDao;
import dal.dao.DomainUserVotesDao;
import dal.dao.DomainVotesDao;
import dal.dto.DomainImageScores;
import dal.dto.DomainUserVotes;
import dal.dto.DomainVotes;
import dal.dto.VoteType;
import dal.exceptions.DomainImagesException;
import dal.exceptions.DomainUserVotesException;
import dal.exceptions.DomainVotesException;


public class VotesWorker extends WorkerBase implements Runnable{

	private final DomainVotesDao m_dalVotes;
	private final DomainUserVotesDao m_dalUserVotes;
	private final DomainImagesDao m_dalImages;
	private static Logger logger = Logger.getLogger(VotesWorker.class);
	
	public VotesWorker(long interval) {
		super(interval);
		m_dalVotes = new DomainVotesDao();
		m_dalUserVotes = new DomainUserVotesDao();
		m_dalImages = new DomainImagesDao();
	}
	@Override
	public VotesWorker withDomain(String domain) {
		this.addDomain(domain);
		return this;
	}

	@Override
	public VotesWorker withParseDomains(String domains) {
		parseDomain(domains);
		return this;
	}
	
	public void run() {
		boolean novotes = true;
		
		List<DomainVotes> votesList = Collections.emptyList();
		List<Integer> dupeCheck = new ArrayList<Integer>();
		List<String> imageIDList = new ArrayList<String>();
		List<DomainImageScores> imagesUpdateList = new ArrayList<DomainImageScores>();
		HashMap<String, Integer> upVotes = new HashMap<String, Integer>();
		HashMap<String, Integer> downVotes = new HashMap<String, Integer>();
		try {
			
			while (!stopRequested) {

				for (String domain : m_Domains) {
					try {
						//get all records from domain
						votesList = m_dalVotes.findAll(domain);
						//loop through results
						for(DomainVotes vote: votesList)
						{
							try {
								//found some stuff
								novotes = false;
								logger.info("Worker Vote: processing vote - User:" + vote.getUserID() + " Image: " + vote.getImageID());
								//prevent users from voting twice
								int duphash = (vote.getUserID() +  vote.getImageID()).hashCode();
								if(dupeCheck.indexOf(duphash) == -1)
									dupeCheck.add(duphash);
								else continue;
								
								//look to see if user has voted on this image in the past
								try {
									//will prevent users from changing their vote
									if(m_dalUserVotes.checkPreviousVotes(vote.getUserID(), vote.getImageID()) > 0)
										continue;
								} catch (DomainUserVotesException e) {
									continue;
								}
								
								//Count up votes
								if(vote.getVoteType() == VoteType.UP)
								{
									//place records in hashmap to weed out dupes and count
									if(upVotes.containsKey(vote.getImageID()))
									{
										int val = (upVotes.get(vote.getImageID()))+1;
										upVotes.put(vote.getImageID(), val);
									}else
										upVotes.put(vote.getImageID(), 1);
								}
								//Count down votes
								if(vote.getVoteType() == VoteType.DOWN)
								{
									//place records in hashmap to weed out dupes and count
									if(downVotes.containsKey(vote.getImageID()))
									{
										int val = (downVotes.get(vote.getImageID()))+1;
										downVotes.put(vote.getImageID(), val);
									}else
										downVotes.put(vote.getImageID(), 1);
								}
								
								
								if(imageIDList.indexOf(vote.getImageID()) == -1)
									imageIDList.add(vote.getImageID());
								
								try {
									//build record of user votes
									DomainUserVotes dto = new DomainUserVotes.Builder()
																.withImageID(vote.getImageID())
																.withUserID(vote.getUserID())
																.withVoteType(vote.getVoteType())
																.build();
									
									m_dalUserVotes.insert(dto);
								} catch (DomainUserVotesException e) {
									logger.error(e,e);
								}
							} catch (Exception e) {
								
								logger.error(e,e);
							}
						}
						
						
						
						for(String imgID: imageIDList)
						{
							try {
								
								//find the image record to work with
								DomainImageScores dto = m_dalImages.findScoreWhereIDEquals(imgID);
								
								//update the new up votes total
								int lCount = 0;
								int rCount = 0;
								//get the new up votes
								if(upVotes.get(imgID) != null)
									lCount = upVotes.get(imgID);
								//get existing up votes
								rCount = dto.getUpVotes();
								dto.setUpVotes(lCount + rCount);
								
								//update the new down votes total
								lCount = 0;
								rCount = 0;
								//get the new down votes
								if(downVotes.get(imgID) != null)
									lCount = downVotes.get(imgID);
								//get existing down votes
								rCount = dto.getDownVotes();
								dto.setDownVotes(lCount + rCount);
								
								//set score	
								dto.setScore(ImageScore.calculateScore(dto));
								
								//put in the update list
								imagesUpdateList.add(dto);
								
							} catch (DomainImagesException e) {
								logger.error(e,e);
							}
							
						}
						
						//update image votes
						try {
							m_dalImages.batchUpdateScores(imagesUpdateList);
						} catch (DomainImagesException e) {
							logger.error(e,e);
						}
						
						//clean out vote tbl
						m_dalVotes.deleteBatch(domain, votesList);
						
						
					} catch (DomainVotesException e) {
						logger.error(e,e);
					} catch (Exception e) {
						logger.error(e,e);
					} finally {
						//clean up
						votesList.clear();
						dupeCheck.clear();
						downVotes.clear();
						upVotes.clear();
						imageIDList.clear();
						imagesUpdateList.clear();
					}
					
				}
				//no votes this time through so rest a bit
				if(novotes && !stopRequested)
					Thread.sleep(m_Interval);
			}
		} catch (InterruptedException iex) {
			logger.error(iex,iex);
		}

	}
	

	

}
