package com.algaworks.algafood.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

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


@RestController
@RequestMapping("/estados")
public class EstadoController {
    
    @Autowired
    private EstadoService service;

   @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Estado> listar(){
        return service.listar();
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable("estadoId") Long id){
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Estado estado) {
        try{
            estado = service.salvar(estado);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(estado);
        }catch (EntidadeNaoEncontradaExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
        Estado estadoAtual = service.buscar(estadoId);

       if(estadoAtual != null){
            BeanUtils.copyProperties(estado, estadoAtual,"id");

            estadoAtual = service.salvar(estadoAtual);
    
            return ResponseEntity.ok(estadoAtual);
        }

        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> deletar(@PathVariable Long estadoId){
        try {
            service.excluir(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntidadeEmUsoExeption e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }
    
    
}
