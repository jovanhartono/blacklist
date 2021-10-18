package project.blacklist.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.blacklist.dto.SuspectRequest;
import project.blacklist.model.Suspect;
import project.blacklist.repository.SuspectRepository;
import project.blacklist.service.SuspectService;

@Service
public class SuspectServiceImplementation implements SuspectService {

    private final SuspectRepository suspectRepository;

    @Autowired
    public SuspectServiceImplementation(SuspectRepository suspectRepository) {
        this.suspectRepository = suspectRepository;
    }

    @Override
    public void createSuspect(SuspectRequest suspectRequest) throws DataIntegrityViolationException {
        Suspect suspect = Suspect.builder().name(suspectRequest.getName())
                .phoneNumber(suspectRequest.getPhoneNumber()).build();
        this.suspectRepository.save(suspect);
    }
}
