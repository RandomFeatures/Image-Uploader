package dal.exceptions;

public class DomainVotesException extends DaoException {

	private static final long serialVersionUID = 8543240807656929485L;

	public DomainVotesException(String message) {
		super(message);
	}

	public DomainVotesException(String message, Throwable cause) {
		super(message, cause);
	}
}
