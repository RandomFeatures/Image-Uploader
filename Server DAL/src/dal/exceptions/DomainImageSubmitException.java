package dal.exceptions;

public class DomainImageSubmitException extends DaoException {

	private static final long serialVersionUID = -9217215870551529142L;
	
	public DomainImageSubmitException(String message) {
		super(message);
	}

	public DomainImageSubmitException(String message, Throwable cause) {
		super(message, cause);
	}

}
