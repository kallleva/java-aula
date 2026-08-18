package aula.Dtos;

import aula.Models.Conta;

public interface ClienteDto {
    String getNome();
    String getEmail();
    String getCpf();
    Conta getConta();
}