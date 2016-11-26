package cal;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import dal.dto.DomainImageScores;

public class ImageScore {
	
	private static Logger logger = Logger.getLogger(ImageScore.class);
	
	public static double calculateScore(DomainImageScores dto){
		double rtn = 0.0d;

		try {
			double votes = 0d;
			double hrs = 0d;
			double score = 0d;
			//Using scoring algorithm log(U-D)/T+2, where U=upvotes, D=downvotes, T=time in hours since submission. When U-D < 1, log(U-D) floors to 0.
			//get votes
			votes = (double)dto.getUpVotes() - (double)dto.getDownVotes();
			if(votes > 0)
			{//must have positive votes for this to work
				//get hours 
				hrs = new Interval(dto.getDate(), new DateTime()).toPeriod().toStandardHours().getHours() + 2d;
				//cal score
				score = (Math.log(votes)/hrs);
				if(score < 0.5d) score = 0.0d;
				//set score	
				rtn = score;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		return rtn;
	}
}
