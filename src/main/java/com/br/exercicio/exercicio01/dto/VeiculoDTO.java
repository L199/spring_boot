package com.br.exercicio.exercicio01.dto;

import com.br.exercicio.exercicio01.model.Veiculo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class VeiculoDTO {
    private String modelo;
    private String marca;
    private String placa;
    private  int anoFabricacao;


    public VeiculoDTO(Veiculo veiculo){

        this.placa = veiculo.getPlaca();
        this.marca =veiculo.getMarca();
        this.modelo=veiculo.getModelo();
        this.anoFabricacao=veiculo.getAnoFabricacao();

    }
}
