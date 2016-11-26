package dal.exceptions;

public class DomainUsersException extends DaoException {

	
	private static final long serialVersionUID = 1859203033397570856L;

	public DomainUsersException(String message) {
		super(message);
	}
	
	public DomainUsersException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
