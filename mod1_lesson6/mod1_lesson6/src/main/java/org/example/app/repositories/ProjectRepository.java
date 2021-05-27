package org.example.app.repositories;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    void remove(Object o);

    List<T> getByValueFromKey(Object o);
}
