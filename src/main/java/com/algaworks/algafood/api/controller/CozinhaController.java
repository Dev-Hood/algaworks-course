package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;


@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
    
    private CozinhaRepository cozinhaRepository;

    public CozinhaController(CozinhaRepository repository){
        this.cozinhaRepository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar(){
        return cozinhaRepository.listar();
    }


    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id){
       
        
        var cozinha = cozinhaRepository.buscar(id);

        //return  ResponseEntity.status(HttpStatus.OK).body(cozinha);
        return ResponseEntity.ok(cozinha);

        //HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
        
        /*return ResponseEntity
            .status(HttpStatus.FOUND)
            .headers(headers).build();*/
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar( @RequestBody Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

        //cozinhaAtual.setNome(cozinha.getNome());

        if(cozinhaAtual != null){
            BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");

            cozinhaAtual = cozinhaRepository.salvar(cozinhaAtual);
    
            return ResponseEntity.ok(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();
       
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> deletar(@PathVariable Long cozinhaId){
        try {
            Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

            if(cozinha != null){
                cozinhaRepository.remover(cozinha);

                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        


    }

}
