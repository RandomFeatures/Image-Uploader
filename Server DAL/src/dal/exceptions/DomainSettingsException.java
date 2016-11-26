package dal.exceptions;

public class DomainSettingsException extends DaoException {

	private static final long serialVersionUID = -8692328093218833541L;

	public DomainSettingsException(String message) {
		super(message);
	}

	public DomainSettingsException(String message, Throwable cause) {
		super(message, cause);
	}
}
