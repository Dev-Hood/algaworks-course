package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    private EntityManager manager;
    
    
    @Override
    public List<Estado> listar() {
        TypedQuery<Estado> query = manager.createQuery("from Estado", Estado.class);

       return query.getResultList();
    }

    @Override
    public Estado buscar(Long id) {  
       return manager.find(Estado.class, id);
    }

    @Override
    public Estado salvar(Estado estado) {
      return manager.merge(estado);
    }

    @Override
    public void remover(Estado estado) {
      var estadoBuscado =  buscar(estado.getId());
      manager.remove(estadoBuscado);
    }
    
}
