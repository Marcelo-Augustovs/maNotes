package dev_marcelo.maNotes.dto;

import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.entity.Usuario;

import java.util.List;

public record FundosEDespesasDto(int mes, Usuario usuarioLogado, List<Despesa> despesaDoMes, List<Fundos> fundosDoMes) {

}
