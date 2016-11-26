package dal.exceptions;

public class DomainVotePurchaseException extends DaoException {

	private static final long serialVersionUID = 3845984368671671367L;
	
	public DomainVotePurchaseException(String message) {
		super(message);
	}

	public DomainVotePurchaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
