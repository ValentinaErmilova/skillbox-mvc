package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.app.repositories.ProjectRepository;
import org.example.web.dto.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final ProjectRepository<LoginForm> loginRepo;

    @Autowired
    public LoginService(ProjectRepository<LoginForm> loginRepo) {
        this.loginRepo = loginRepo;
    }

    private Logger logger = Logger.getLogger(LoginService.class);

    public boolean authenticate(LoginForm loginFrom) {
        logger.info("try auth with user-form: " + loginFrom);
        if (loginFrom.getUsername().equals("root") && loginFrom.getPassword().equals("123")) {
            return true;
        }
        for (LoginForm login : loginRepo.retreiveAll()) {
            if (login.getUsername().equals(loginFrom.getUsername())
                    && login.getPassword().equals(loginFrom.getPassword())){
                return true;
            }
        }
        return false;
    }

    public void join(LoginForm loginFrom) {
        loginRepo.store(loginFrom);
    }
}

