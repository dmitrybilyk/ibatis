package rest.exception;

public class RecordInUse extends Exception {
	private static final long serialVersionUID = -4773170106238936526L;

	public RecordInUse() {
    super();
	}

	public RecordInUse(String message) {
		super(message);
	}

}
