package project.blacklist.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.blacklist.dto.SuspectRequest;

@Service
public interface SuspectService{
    void createSuspect(SuspectRequest suspectRequest) throws DataIntegrityViolationException;
}
