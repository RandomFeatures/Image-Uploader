package dal.exceptions;

public class DomainFollowersException extends DaoException {

	private static final long serialVersionUID = -1002056144225008403L;

	
	public DomainFollowersException(String message) {
		super(message);
	}

	public DomainFollowersException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
