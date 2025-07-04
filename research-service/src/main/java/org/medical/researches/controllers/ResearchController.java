package org.medical.researches.controllers;

import org.medical.researches.model.Research;
import org.medical.researches.repositories.ResearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/research")
public class ResearchController {

    @Autowired
    private ResearchRepository researchRepository;

    @GetMapping("/list")
    public List<Research> findAll(@RequestParam(value = "patientId", required = false)String patientId){
        if (patientId == null || patientId.isBlank()){
            return researchRepository.findAll();
        } else {
            return researchRepository.findByPatientId(Integer.parseInt(patientId));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> addResearch(@RequestBody Research research){
        if (research == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка добавления");
        } else {
            researchRepository.save(research);
            return  ResponseEntity.status(HttpStatus.CREATED).body("Исследование добавлено");
        }
    }
}
