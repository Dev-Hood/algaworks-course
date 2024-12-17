package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Restaurante;

import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.RestauranteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService service;

    public RestauranteController(RestauranteRepository repository){
        this.restauranteRepository = repository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurante> listar(){
        return service.listar();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable("restauranteId") Long id){
        return restauranteRepository.buscar(id);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try{
            restaurante = service.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurante);
        }catch (EntidadeNaoEncontradaExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
       Restaurante restauranteAtual = service.buscar(restauranteId);

       if(restauranteAtual != null){
        try{
            restaurante = service.atualizar(restauranteAtual ,restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch(EntidadeNaoEncontradaExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
       }
       return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<?> deletar(@PathVariable Long restauranteId){
        try {
            service.excluir(restauranteId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntidadeEmUsoExeption e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
        Restaurante restauranteAtual = service.buscar(restauranteId);

        if(restauranteAtual == null){
            return ResponseEntity.notFound().build();
        }
        merge(campos, restauranteAtual);

        return atualizar(restauranteId, restauranteAtual);
    }


    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino){
        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            System.out.println(nomePropriedade + " = "+ valorPropriedade);
        });
    }
    

}
