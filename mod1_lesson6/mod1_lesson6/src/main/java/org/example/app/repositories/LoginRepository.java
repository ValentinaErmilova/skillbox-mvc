package org.example.app.repositories;

import org.apache.log4j.Logger;
import org.example.web.dto.forms.LoginForm;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LoginRepository implements ProjectRepository<LoginForm> {
    private final Logger logger = Logger.getLogger(LoginRepository.class);
    private final List<LoginForm> repo = new ArrayList<>();

    @Override
    public List<LoginForm> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(LoginForm loginForm) {
        loginForm.setId(loginForm.hashCode());
        logger.info("store new loginForm: " + loginForm);
        repo.add(loginForm);
    }

    @Override
    public void remove(Object o) {}

    @Override
    public List<LoginForm> getByValueFromKey(Object o) {
        return null;
    }
}
