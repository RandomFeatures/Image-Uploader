package dal.exceptions;

public class DaoException extends Exception {
	private static final long serialVersionUID = 2051029275758968341L;
	protected Throwable throwable;

	  public DaoException(String message)
	  {
	    super(message);
	  }

	  public DaoException(String message, Throwable throwable)
	  {
	    super(message);
	    this.throwable = throwable;
	  }

	  public Throwable getCause()
	  {
	    return this.throwable;
	  }
}
