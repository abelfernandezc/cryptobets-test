package fernandez.abel.cryptobets.model;

public enum RoundOfBetStatus {
    OPEN("open"),
    FINISHED("finished");

    private final String statusCode;

    RoundOfBetStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String get() {
        return this.statusCode;
    }
}
