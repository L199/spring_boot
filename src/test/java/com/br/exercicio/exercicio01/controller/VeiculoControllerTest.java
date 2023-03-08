package com.br.exercicio.exercicio01.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThatCode;
import org.mockito.ArgumentMatchers;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.*;
import org.hamcrest.CoreMatchers;
import com.br.exercicio.exercicio01.dto.VeiculoDTO;
import com.br.exercicio.exercicio01.exception.NotFoundException;
import com.br.exercicio.exercicio01.model.Veiculo;
import com.br.exercicio.exercicio01.service.VeiculoService;
import com.br.exercicio.exercicio01.util.GenerateVeiculo;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(VeiculoController.class)
public class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Json para String

    @MockBean
    private VeiculoService service;

    @Test
    public void getAll_returnListVeiculos_whenSuccess() throws Exception {
        List<VeiculoDTO> lista = new ArrayList<>();
        lista.add((new VeiculoDTO(GenerateVeiculo.veiculoValido())));
        lista.add((new VeiculoDTO(GenerateVeiculo.veiculoValidoId2())));

        BDDMockito.when(service.getAll()).thenReturn(lista);

        ResultActions resposta = mockMvc.perform(get("/veiculo").contentType(MediaType.APPLICATION_JSON));

        resposta.andExpect(status().isOk()) // veriFica se o status é o OK (200)
                .andExpect(jsonPath("$.size()", CoreMatchers.is(lista.size())))
                .andExpect(jsonPath("$[0].placa", CoreMatchers.is(GenerateVeiculo.veiculoValido().getPlaca())));
    }

    @Test
    public void getById_returnVeiculo_whenIdNotExist() throws Exception {
        final long ID_NOT_EXIST =0;
        Veiculo veiculo = GenerateVeiculo.veiculoValido();

        BDDMockito.given(service.getById(anyLong()))
        .willThrow(new NotFoundException("Veiculo não encontrado"));

        // ação
       // assertThatCode( ()-> {
        ResultActions resposta = mockMvc.perform(get("/veiculo/{id}", veiculo.getId())
                .contentType(MediaType.APPLICATION_JSON));
       // }).hasCause(new NotFoundException("Veiculo não encontrado"));
        //resposta.andExpect(status().isOk())
                //.andExpect(jsonPath("$.placa", CoreMatchers.is(veiculo.getPlaca())));
        resposta.andExpect(status().isNotFound());
    }

    @Test
    public void newVeiculo_returnVeiculoInserido_whenDadosValidos() throws Exception {
        Veiculo novoVeiculo = GenerateVeiculo.novoVeiculoToSave();
        Veiculo veiculoValido = GenerateVeiculo.veiculoValido();

        BDDMockito.when(service.newVeiculo(ArgumentMatchers.any(Veiculo.class)))
                    .thenReturn(veiculoValido);

        ResultActions resposta = mockMvc.perform(post("/veiculo")
                    .content(objectMapper.writeValueAsString(novoVeiculo))
                    .contentType(MediaType.APPLICATION_JSON));

        resposta.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.placa", CoreMatchers.is(novoVeiculo.getPlaca())));
    }
    
}
