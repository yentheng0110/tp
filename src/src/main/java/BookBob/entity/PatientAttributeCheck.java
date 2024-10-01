package BookBob.entity;

@FunctionalInterface
public interface PatientAttributeCheck {
    boolean test(Patient patient, String keyword);
}
