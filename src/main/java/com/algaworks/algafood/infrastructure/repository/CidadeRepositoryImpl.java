package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Component
public class CidadeRepositoryImpl implements CidadeRepository{
    
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> listar() {
        TypedQuery<Cidade> query = manager.createQuery("from Cidade", Cidade.class);

        return query.getResultList();
    }

    @Override
    public Cidade buscar(Long id) {
       var cidade = manager.find(Cidade.class, id);
       return cidade;
    }

    @Override
    @Transactional
    public Cidade salvar(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Override
    @Transactional
    public void remover(Long id) {
        var cidade =  buscar(id);
        if(cidade == null){
              throw new EmptyResultDataAccessException(1);
          }
        manager.remove(cidade);
    }

    

  
}
