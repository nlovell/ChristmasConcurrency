package machine.data;

import java.util.Objects;

/**
 * The type Age range.
 */
public class AgeRange {
    /**
     * The Age min.
     */
    final int age_min;
    /**
     * The Age max.
     */
    final int age_max;

    /**
     * Instantiates a new Age range.
     *
     * @param age_min the age min
     * @param age_max the age max
     */
    public AgeRange(int age_min, int age_max) {
        this.age_min = age_min;
        this.age_max = age_max;
    }

    /**
     * Contains boolean.
     *
     * @param age the age
     * @return the boolean
     */
    public boolean contains(int age) {
        return age >= age_min && age <= age_max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgeRange)) return false;
        AgeRange ageRange = (AgeRange) o;
        return age_min == ageRange.age_min &&
                age_max == ageRange.age_max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age_min, age_max);
    }

    @Override
    public String toString() {
        return "AgeRange [" + age_min +
                "-" + age_max + "]";
    }
}
