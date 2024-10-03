package BookBob.functions;

import BookBob.entity.Patient;

@FunctionalInterface
public interface FindAttributeCheck {
    boolean test(Patient patient, String keyword);
}
