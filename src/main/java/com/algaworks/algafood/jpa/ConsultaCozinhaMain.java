package com.algaworks.algafood.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class ConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);

        CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);

        System.out.println("----------- buscando cozinhas");
        List<Cozinha> list = cozinhaRepository.listar();
        for (Cozinha cozinha : list) {
            System.out.println(cozinha.getNome());
        }

        Cozinha c1 = new Cozinha();
        c1.setNome("Brasileira");

        Cozinha c2 = new Cozinha();
        c2.setNome("Italiana");

        System.out.println("----------- salvando cozinhas");
        c2 = cozinhaRepository.salvar(c2);
        c1 = cozinhaRepository.salvar(c1);

        System.out.println("----------- buscando cozinha 1l");
        Cozinha cbuscada = cozinhaRepository.buscar(1L);

        System.out.println(cbuscada.getNome());
        System.out.println("----------- removendo cozinha 1l");
        cozinhaRepository.remover(cbuscada.getId());


        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        System.out.println("----------- buscando restaurante");
        List<Restaurante> listRe = restauranteRepository.listar();
        for (Restaurante restaurante : listRe) {
            System.out.println(restaurante.getNome()+"--"+restaurante.getTaxaFrete()+"--"+ restaurante.getCozinha().getNome());
        }

        Restaurante r1 = new Restaurante();
        r1.setNome("Restaurante BR");
        r1.setTaxaFrete(new BigDecimal(30));
        r1.setCozinha(c2);


        Restaurante r2 = new Restaurante();
        r2.setNome("Restaurante Italian0");
        r2.setTaxaFrete(new BigDecimal(60));
        r2.setCozinha(c2);

        System.out.println("----------- salvando Restaurantes");
        restauranteRepository.salvar(r2);
        restauranteRepository.salvar(r1);

        System.out.println("----------- buscando Restaurante 1l");
        Restaurante restB = restauranteRepository.buscar(1L);

        System.out.println(restB.getNome());
        System.out.println("----------- removendo Restaurante 1l");
       

    }
}
