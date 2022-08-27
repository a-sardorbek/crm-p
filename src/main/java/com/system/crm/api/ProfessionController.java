package com.system.crm.api;

import com.system.crm.domain.entity.ResponsibleProfession;
import com.system.crm.service.ResponsibleProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/profession")
public class ProfessionController {

    private ResponsibleProfessionService responsibleProfessionService;

    @Autowired
    public ProfessionController(ResponsibleProfessionService responsibleProfessionService) {
        this.responsibleProfessionService = responsibleProfessionService;
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addProfession(@Valid @RequestBody Set<ResponsibleProfession> responsibleProfession){
        responsibleProfessionService.createProfession(responsibleProfession);
      return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @PutMapping(value = "/update",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateProfession(@Valid @RequestBody ResponsibleProfession responsibleProfession){
        ResponsibleProfession updatedResponsibleProfession = responsibleProfessionService.updateProfession(responsibleProfession);
        return new ResponseEntity<>(updatedResponsibleProfession, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @DeleteMapping(value = "/delete", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteProfessionUsingId(@RequestParam("professionId") Long professionId){
       responsibleProfessionService.deleteProfession(professionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping(value = "all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<ResponsibleProfession>> allProfessions(){
        List<ResponsibleProfession> responsibleProfessionList = responsibleProfessionService.getAllProfessions();
        return new ResponseEntity<>(responsibleProfessionList,HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponsibleProfession> professionById(@RequestParam("professionId") String professionId){
        ResponsibleProfession responsibleProfession = responsibleProfessionService.getProfessionById(professionId).get();
        return new ResponseEntity<>(responsibleProfession,HttpStatus.OK);
    }
}
