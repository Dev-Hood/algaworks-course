package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;


@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
    
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CozinhaService service;

    public CozinhaController(CozinhaRepository repository){
        this.cozinhaRepository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cozinha> listar(){
        return service.listar();
    }


    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id){
       
        
        var cozinha = service.buscar(id);

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }

        //return  ResponseEntity.status(HttpStatus.OK).body(cozinha);
        return ResponseEntity.notFound().build();

        //HttpHeaders headers = new HttpHeaders();
        //headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
        
        /*return ResponseEntity
            .status(HttpStatus.FOUND)
            .headers(headers).build();*/
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar( @RequestBody Cozinha cozinha){
        return service.salvar(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

        //cozinhaAtual.setNome(cozinha.getNome());

        if(cozinhaAtual != null){
            BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");

            cozinhaAtual = service.salvar(cozinhaAtual);
    
            return ResponseEntity.ok(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();
       
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> deletar(@PathVariable Long cozinhaId){
        try {
            service.excluir(cozinhaId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaExeption e) {
            return ResponseEntity.badRequest().build();
        } catch (EntidadeEmUsoExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
