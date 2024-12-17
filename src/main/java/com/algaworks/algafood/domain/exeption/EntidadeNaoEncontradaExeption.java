package com.algaworks.algafood.domain.exeption;

public class EntidadeNaoEncontradaExeption extends RuntimeException{
    public EntidadeNaoEncontradaExeption(String msg){
        super(msg);
    }
}
