package BookBob.functions;

import BookBob.entity.Patient;

@FunctionalInterface
public interface FindPatientAttributeCheck {
    boolean test(Patient patient, String keyword);
}
