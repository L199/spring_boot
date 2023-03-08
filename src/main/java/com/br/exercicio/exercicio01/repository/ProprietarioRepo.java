package com.br.exercicio.exercicio01.repository;

import org.springframework.data.repository.CrudRepository;

import com.br.exercicio.exercicio01.model.Proprietario;

public interface ProprietarioRepo extends CrudRepository<Proprietario , Long> {
    
}
