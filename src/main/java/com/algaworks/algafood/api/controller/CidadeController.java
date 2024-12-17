package com.algaworks.algafood.api.controller;

import java.util.List;


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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController {
     @Autowired
    private CidadeService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cidade> listar(){
        return service.listar();
    }


    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long id){
       
        
        var cidade = service.buscar(id);

        if (cidade != null) {
            return ResponseEntity.ok(cidade);
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
    public ResponseEntity<?> adicionar( @RequestBody Cidade cidade){

        try {
            Cidade cidadeSalva = service.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeSalva);
        } catch (EntidadeNaoEncontradaExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade){
        Cidade cidadeAtual = service.buscar(cidadeId);

        if(cidadeAtual != null){
            try {
                cidadeAtual = service.atualizar(cidadeAtual, cidade);
                return ResponseEntity.ok(cidadeAtual);
            } catch (EntidadeNaoEncontradaExeption e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> deletar(@PathVariable Long cidadeId){
        try {
            service.excluir(cidadeId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaExeption e) {
            return ResponseEntity.badRequest().build();
        } catch (EntidadeEmUsoExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
