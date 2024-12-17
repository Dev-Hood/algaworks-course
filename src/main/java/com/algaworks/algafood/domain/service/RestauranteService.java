package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
    @Autowired
    private RestauranteRepository repository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

     public List<Restaurante> listar(){
        return repository.listar();
    }

    public Restaurante buscar(Long id){
        return repository.buscar(id);
    }


    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if(cozinha == null){
            throw new EntidadeNaoEncontradaExeption(String.format("Não existe cadastro de cozinha com cod %d", cozinhaId));
        }

        return repository.salvar(restaurante);
    }

    public void excluir(Long id){
        try {
            repository.remover(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoExeption("Erro: A entidade está em uso");
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaExeption("Erro: A entidade não foi encontrada");
        }
    }

    public Restaurante atualizar(Restaurante restauranteAtual, Restaurante restaurante) {
        Cozinha cozinha = cozinhaRepository.buscar(restaurante.getCozinha().getId());

        if(cozinha == null){
            throw new EntidadeNaoEncontradaExeption(String.format("Não foi possível encontrar cozinha de id %d", restaurante.getCozinha().getId()));
        }

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
            restauranteAtual.setCozinha(cozinha);
            restauranteAtual = repository.salvar(restauranteAtual);
        return restauranteAtual;
    }


    public void remover(Long id) {
       try{
        repository.remover(id);
       } catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaExeption("Erro: A entidade não foi encontrada");
       } catch (DataIntegrityViolationException e) {
        throw new EntidadeEmUsoExeption("Erro: A entidade está em uso");
       }
    }
}
