package google;

public class NoResultException extends Exception {
	
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
	public NoResultException() {
	}

	// Constructor that accepts a message
	public NoResultException(String message) {
		super(message);
	}
}
