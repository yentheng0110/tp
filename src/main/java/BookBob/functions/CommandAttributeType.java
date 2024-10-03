package BookBob.functions;

public enum CommandAttributeType {
    NAME("NAME"),
    NRIC("NRIC"),
    DATE_OF_BIRTH("DATE_OF_BIRTH"),
    PHONE_NUMBER("PHONE_NUMBER"),
    HOME_ADDRESS("HOME_ADDRESS"),
    DIAGNOSIS("DIAGNOSIS"),
    MEDICATION("MEDICATION");

    private final String label;

    CommandAttributeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
