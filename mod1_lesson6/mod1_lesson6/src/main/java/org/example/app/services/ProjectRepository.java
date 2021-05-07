package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    void removeByValueFromKey(String keyToRemove, String valueToRemove);

    List<T> getByValueFromKey(String key, String value);
}
