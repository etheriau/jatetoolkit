package uk.ac.shef.dcs.oak.jate;

/**
 */

public class JATEException extends Exception {
	private static final long serialVersionUID = -4511571544868986419L;

	public JATEException(String exception){
		super(exception);
	}

	public JATEException(Exception e){
		super(e);
	}

	public JATEException(String exception, Throwable cause){
		super(exception, cause);
	}
}
