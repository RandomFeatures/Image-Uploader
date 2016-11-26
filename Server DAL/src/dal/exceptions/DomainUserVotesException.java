package dal.exceptions;

public class DomainUserVotesException extends DaoException {

	private static final long serialVersionUID = 3724754294199563208L;

	public DomainUserVotesException(String message) {
		super(message);
	}

	public DomainUserVotesException(String message, Throwable cause) {
		super(message, cause);
	}
}
