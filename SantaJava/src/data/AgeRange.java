package data;

import java.util.Objects;

public class AgeRange {
    final int age_min;
    final int age_max;

    public AgeRange(int age_min, int age_max) {
        this.age_min = age_min;
        this.age_max = age_max;
    }

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
}
