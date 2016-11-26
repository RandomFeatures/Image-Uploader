package dal.exceptions;

public class DomainImagesException extends DaoException {

	private static final long serialVersionUID = 899859308326255753L;
	public DomainImagesException(String message) {
		super(message);
	}

	public DomainImagesException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
