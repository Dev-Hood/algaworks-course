package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository{

    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<Cozinha> listar(){
       TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);

       return query.getResultList();
    }

    @Override
    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return manager.merge(cozinha);
    }

    @Override
    public Cozinha buscar(Long id){
        return manager.find(Cozinha.class, id);
    }

    @Override
    @Transactional
    public void remover(Cozinha cozinha){
        cozinha = buscar(cozinha.getId());

        manager.remove(cozinha);
    }
    
}
