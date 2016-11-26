package cal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Cutron implements IApp{

	private static List<WorkerBase> workerList = new ArrayList<WorkerBase>();
	private Properties propFile = new Properties();
	private static Logger logger = Logger.getLogger(Cutron.class);

	
	public void shutDown() {
		System.out.println("Shutting down threads");
		for (WorkerBase worker : workerList) {
			worker.requestStop();
		}
		workerList.clear();
	}
	
	
	public static void main(String[] args) {

		String fileName = "/calcutron.properties";

		if (args.length > 0)
			fileName = args[0];

		IApp app = new Cutron(fileName);
		ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
		Runtime.getRuntime().addShutdownHook(shutdownInterceptor);

	}

	public Cutron(String fileName) {
		try {
			propFile.load(Cutron.class.getResourceAsStream(fileName));
			
			// DOMConfigurator.configure("log4j.xml");
			//PropertyConfigurator.configure("log4j.properties");

			logger.info("Cutron Config: " + fileName);
			
			loadVotesWorker();
			loadViewWorker();
			loadSubmitWorker();
			loadCacheWorker();
			loadViewingWorker();
			loadDecayScoreWorker();
			loadPurchaseWorker();

		} catch (IOException e) {
			logger.error(e, e);
		}
	}

	private void loadDecayScoreWorker() {
		try {
			if (propFile.getProperty("decay", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for Score Decayp
				int threadInterval = Integer.parseInt(propFile.getProperty("decay.sleep", "1800000"));

				logger.info("Worker - Score Decay: Started | Interval: " + Integer.toString(threadInterval));
				// create the worker process
				DecayScoresWorker decaythread = new DecayScoresWorker(threadInterval);
				workerList.add(decaythread);
				// create the new thread
				(new Thread(decaythread)).start();
			}
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private void loadVotesWorker() {
		try {

			if (propFile.getProperty("vote", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for Votes
				String domainList = "";
				int threadInterval = Integer.parseInt(propFile.getProperty("vote.sleep", "60000"));
				for (int iloop = 0; iloop < 10; iloop++) {
					domainList = propFile.getProperty("vote." + Integer.toString(iloop) + ".domains", "");
					if (!domainList.isEmpty()) {// domains found for this thread
												// create the worker process
						logger.info("Worker - Votes: Started  | Interval: " + Integer.toString(threadInterval) + " | Monitoring Domains "
								+ domainList.toString());
						VotesWorker votethread = new VotesWorker(threadInterval).withParseDomains(domainList);
						workerList.add(votethread);
						// create the new thread
						(new Thread(votethread)).start();
					}
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}

	}

	private void loadViewWorker() {
		try {
			if (propFile.getProperty("view", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for Views

				String domainList = "";
				int threadInterval = Integer.parseInt(propFile.getProperty("view.sleep", "60000"));
				for (int iloop = 0; iloop < 10; iloop++) {
					domainList = propFile.getProperty("view." + Integer.toString(iloop) + ".domains", "");
					if (!domainList.isEmpty()) {// domains found for this thread
												// create the worker process
						logger.info("Worker - Views: Started  | Interval: " + Integer.toString(threadInterval) + " | Monitoring Domains "
								+ domainList.toString());
						ViewsWorker viewthread = new ViewsWorker(threadInterval).withParseDomains(domainList);
						workerList.add(viewthread);
						// create the new thread
						(new Thread(viewthread)).start();
					}
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private void loadSubmitWorker() {
		try {
			if (propFile.getProperty("submit", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for submit
				String domainList = "";
				int threadInterval = Integer.parseInt(propFile.getProperty("submit.sleep", "60000"));
				for (int iloop = 0; iloop < 10; iloop++) {
					domainList = propFile.getProperty("submit." + Integer.toString(iloop) + ".domains", "");
					if (!domainList.isEmpty()) {// domains found for this thread
												// create the worker process
						logger.info("Worker - Submit: Started  | Interval: " + Integer.toString(threadInterval) + " | Monitoring Domains "
								+ domainList.toString());
						SubmitWorker submitthread = new SubmitWorker(threadInterval).withParseDomains(domainList);
						workerList.add(submitthread);
						// create the new thread
						(new Thread(submitthread)).start();
					}
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private void loadCacheWorker() {
		try {
			if (propFile.getProperty("cache", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for cache
				int threadInterval = Integer.parseInt(propFile.getProperty("cache.sleep", "60000"));
				String server = propFile.getProperty("cache.server", "127.0.0.1:11211");

				logger.info("Worker - Cache: Started | Interval: " + Integer.toString(threadInterval));

				// create the worker process
				CacheWorker cachethread = new CacheWorker(threadInterval, server);
				workerList.add(cachethread);
				// create the new thread
				(new Thread(cachethread)).start();
			}
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private void loadViewingWorker() {

		try {
			if (propFile.getProperty("viewing", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for Viewing
				int threadInterval = Integer.parseInt(propFile.getProperty("viewing.sleep", "1800000"));
				int viewingAge = Integer.parseInt(propFile.getProperty("viewing.age", "24"));

				if (viewingAge > 0) {

					logger.info("Worker - Viewing: Started | Interval: " + Integer.toString(threadInterval));
					// create the worker process
					ViewingWorker viewingthread = new ViewingWorker(threadInterval, viewingAge);
					workerList.add(viewingthread);
					// create the new thread
					(new Thread(viewingthread)).start();
				}
			}
		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}

	}

	private void loadPurchaseWorker() {

		try {
			if (propFile.getProperty("purchase", "disabled").equalsIgnoreCase("enabled")) {
				// Load workers for purchases
				int threadInterval = Integer.parseInt(propFile.getProperty("purchase.sleep", "3600000"));

				logger.info("Worker - Vote Purchase: Started | Interval: " + Integer.toString(threadInterval));
				// create the worker process
				PurchaseWorker purchasethread = new PurchaseWorker(threadInterval);
				workerList.add(purchasethread);
				// create the new thread
				(new Thread(purchasethread)).start();
			}

		} catch (NumberFormatException e) {
			logger.error(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}

	}

}
