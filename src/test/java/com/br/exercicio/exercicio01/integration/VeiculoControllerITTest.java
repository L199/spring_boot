package com.br.exercicio.exercicio01.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.br.exercicio.exercicio01.repository.VeiculoRepo;
import com.br.exercicio.exercicio01.util.GenerateVeiculo;
import com.br.exercicio.exercicio01.dto.VeiculoDTO;
import com.br.exercicio.exercicio01.exception.NotFoundException;
import com.br.exercicio.exercicio01.model.Veiculo;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.hamcrest.CoreMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// carrega o contexto do Spring para teste usando uma porta aleatória
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VeiculoControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VeiculoRepo veiculoRepo;

    @BeforeEach
    public void setup(){
        veiculoRepo.deleteAll();
    }

    @Test
    public void newVeiculo_returnVeiculoInserido_whenDadosVeiculoValido() throws Exception {
        Veiculo novoVeiculo = GenerateVeiculo.novoVeiculoToSave();

        ResultActions resposta = mockMvc.perform(post("/veiculo")
                .content(objectMapper.writeValueAsString(novoVeiculo))
                .contentType(MediaType.APPLICATION_JSON));

        resposta.andExpect(status().isCreated())
                .andExpect(jsonPath("$.placa", CoreMatchers.is(novoVeiculo.getPlaca())));
    }

    @Test
    public void getById_returnVeiculo_whenIdExist() throws Exception {
        Veiculo novoVeiculo = GenerateVeiculo.novoVeiculoToSave();

        Veiculo veiculoCriado = veiculoRepo.save(novoVeiculo);

        ResultActions resposta = mockMvc.perform(get("/veiculo/{id}", veiculoCriado.getId())
                .contentType(MediaType.APPLICATION_JSON));
        resposta.andExpect(status().isOk())
                .andExpect(jsonPath("$.placa", CoreMatchers.is(veiculoCriado.getPlaca())));

    }


    @Test
    public void getAll_returnListVeiculos_whenSuccess() throws Exception {
        // preparação
        List<Veiculo> lista = new ArrayList<>();
        lista.add(GenerateVeiculo.novoVeiculoToSave());
        lista.add(GenerateVeiculo.novoVeiculoToSave2());

        veiculoRepo.saveAll(lista);

        // ação
        ResultActions resposta = mockMvc.perform(get("/veiculo").contentType(MediaType.APPLICATION_JSON));
        // veriFicações
        resposta.andExpect(status().isOk()) // veriFica se o status é o OK (200)
                .andExpect(jsonPath("$.size()", CoreMatchers.is(lista.size())))
                .andExpect(jsonPath("$[0].placa", CoreMatchers.is(GenerateVeiculo.veiculoValido().getPlaca())));
    }

    



}
