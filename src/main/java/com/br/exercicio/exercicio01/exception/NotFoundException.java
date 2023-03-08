package com.br.exercicio.exercicio01.exception;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String msg){
        super(msg);
    }
}
