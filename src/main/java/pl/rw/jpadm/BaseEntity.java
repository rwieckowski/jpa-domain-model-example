package pl.rw.jpadm;

import java.util.Objects;

public abstract class BaseEntity<K,T extends BaseEntity<K, T>> {
    abstract K id();

    public boolean sameAs(T other) {
        return Objects.equals(id(), other.id());
    }
}
