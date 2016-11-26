package cal;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkerBase {

	protected final List<String> m_Domains;
	protected final long m_Interval;
	protected volatile boolean stopRequested = false;

	
	public WorkerBase(long interval) {
		this.m_Domains = new ArrayList<String>();
		this.m_Interval = interval;
	}

	public void addDomain(String domain)
	{
		m_Domains.add(domain);
		
	}
	
	public void parseDomain(String domains)
	{
		String delims = "[,]";
		String[] dom = domains.split(delims);
		for (int i = 0; i < dom.length; i++)
			m_Domains.add(dom[i]);
		
	}
	
	public abstract WorkerBase withDomain(String domain);
	public abstract WorkerBase withParseDomains(String domains);
	
	public void requestStop() {
		  stopRequested = true;
	}

}
