package com.system.crm.service.impl;

import com.system.crm.domain.entity.ResponsibleProfession;
import com.system.crm.exception.custom.CustomNotFoundException;
import com.system.crm.exception.custom.NotCorrectException;
import com.system.crm.exception.custom.SuccessResponse;
import com.system.crm.repository.ClientRepository;
import com.system.crm.repository.ResponsibleProfessionRepository;
import com.system.crm.service.ResponsibleProfessionService;
import com.system.crm.utils.ServiceUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResponsibleProfessionServiceImpl implements ResponsibleProfessionService {

    private ResponsibleProfessionRepository responsibleProfessionRepository;
    private ClientRepository clientRepository;

    public ResponsibleProfessionServiceImpl(ResponsibleProfessionRepository responsibleProfessionRepository,
                                            ClientRepository clientRepository) {
        this.responsibleProfessionRepository = responsibleProfessionRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void createProfession(Set<ResponsibleProfession> responsibleProfession) {

        Set<ResponsibleProfession> checkedProfessions = new HashSet<>();
        for (ResponsibleProfession profession: responsibleProfession){
            String newProfessionUpperCase = profession.getNameProfession().toUpperCase();
            Optional<ResponsibleProfession> professionFromDb = responsibleProfessionRepository.findByNameProfession(newProfessionUpperCase);
            if(!professionFromDb.isPresent()){
                profession.setNameProfession(newProfessionUpperCase);
                checkedProfessions.add(profession);
            }else {
                throw new NotCorrectException("Profession "+profession.getNameProfession()+" already exists");
            }
        }
        responsibleProfessionRepository.saveAll(checkedProfessions);
        throw new SuccessResponse("Profession created");
    }

    @Override
    public ResponsibleProfession updateProfession(ResponsibleProfession responsibleProfession) {
        Optional<ResponsibleProfession> foundResponsibleProfession = getProfessionById(String.valueOf(responsibleProfession.getId()));
        if(!foundResponsibleProfession.isPresent()) {
            throw new CustomNotFoundException("Profession not found");
        }
            foundResponsibleProfession.get().setNameProfession(responsibleProfession.getNameProfession());
            return responsibleProfessionRepository.save(foundResponsibleProfession.get());

    }

    @Override
    public Optional<ResponsibleProfession> getProfessionById(String id) {
        Optional<ResponsibleProfession> responsibleProfession;
        if(ServiceUtils.checkIsNumeric(id)) {
            responsibleProfession = responsibleProfessionRepository.findById(Long.parseLong(id));
            if(responsibleProfession.isPresent()){
                return responsibleProfession;
            }
        }
        throw new CustomNotFoundException("Profession not found by id: "+id);
    }

    @Override
    public List<ResponsibleProfession> getAllProfessions() {
        return responsibleProfessionRepository.findAll();
    }

    @Override
    public void deleteProfession(Long id) {
        int checkProfessionConnected = clientRepository.checkConnectedToProfession(id);
        if(checkProfessionConnected>0){
            throw new NotCorrectException("You cannot delete this profession it has connected to Client");
        }
        Optional<ResponsibleProfession> responsibleProfession = responsibleProfessionRepository.findById(id);
        if(responsibleProfession.isPresent()){
            responsibleProfessionRepository.deleteById(id);
            throw new SuccessResponse("Profession '"+responsibleProfession.get().getNameProfession()+"' has been deleted!");
        }else{
            throw new CustomNotFoundException("Profession not found by id: "+id);
        }
    }
}
