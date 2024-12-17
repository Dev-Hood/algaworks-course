package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exeption.EntidadeEmUsoExeption;
import com.algaworks.algafood.domain.exeption.EntidadeNaoEncontradaExeption;
import com.algaworks.algafood.domain.model.Cidade;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;


@Service
public class CidadeService {
    @Autowired
    private CidadeRepository repository;

    @Autowired
    private EstadoService estadoService;

    public List<Cidade> listar(){
        return repository.listar();
    }

    public Cidade buscar(Long id){
        return repository.buscar(id);
    }


    public Cidade salvar(Cidade cidade){
        Estado estado = estadoService.buscar(cidade.getEstado().getId());

        if(estado == null) {
            throw new EntidadeNaoEncontradaExeption(String.format("Estado com id %d não existe", cidade.getEstado().getId()));
        }

        return repository.salvar(cidade);
    }

    public Cidade atualizar(Cidade cidadeAtual, Cidade cidade){
        Long idEstado = cidade.getEstado().getId();
        Estado estado = estadoService.buscar(idEstado);

        if(estado == null){
            throw new EntidadeNaoEncontradaExeption(String.format("Estado com id %d não existe", idEstado));
        }

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");
        cidadeAtual.setEstado(estado);

        return repository.salvar(cidadeAtual);
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
