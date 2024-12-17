package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
    
    @Autowired
    private EstadoRepository repository;


    public List<Estado> listar(){
        return repository.listar();
    }

    public Estado buscar(Long id){
        return repository.buscar(id);
    }


    public Estado salvar(Estado estado){
        return repository.salvar(estado);
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
}
