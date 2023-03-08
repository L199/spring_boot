package com.br.exercicio.exercicio01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.br.exercicio.exercicio01.model.Veiculo;

public interface VeiculoRepo extends CrudRepository<Veiculo, Long>{
    //Usando Query Methods
    Veiculo findByPlaca(String placa);
    List<Veiculo> findByAnoFabricacaoGreaterThanEqual(int anoFabricacao);

    //Usando NamedQueries
    @Query("select v from Veiculo v order by v.marca")
    List<Veiculo> getAllOrderMarca();

}
