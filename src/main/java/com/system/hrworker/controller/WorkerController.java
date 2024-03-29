package com.system.hrworker.controller;

import com.system.hrworker.entities.Worker;
import com.system.hrworker.repositories.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    private static Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private Environment env; //informações do contexto da aplicação

    @Value("${test.config}") //mesma propriedade que esta no git
    private String testConfig;

    //testando config server
    @GetMapping("/config")
    public ResponseEntity<Void> config(){
        logger.info("PORT = " + testConfig);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Worker>> findAll(){
        List<Worker> workers = workerRepository.findAll();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Worker> findById(@PathVariable Long id){

        logger.info("PORT = " + env.getProperty("local.server.port")); //numero da porta que esta rodando

        //testando timeout ribbon
//        try {
//            Thread.sleep(3000L);
//        }catch (Exception e){
//            e.printStackTrace();
//        }


        Worker workers = workerRepository.findById(id).get();
        return ResponseEntity.ok(workers);
    }

}
