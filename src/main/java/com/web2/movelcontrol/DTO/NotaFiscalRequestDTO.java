/*
 * Autor: Jorge Afonso
 * Responsavel: Jorge Afonso
 */

package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Objects;

@Schema(name = "NotaFIscalRequestDTO", description = "Dados de entrada para criação de uma nota fiscal")
public class NotaFiscalRequestDTO {

    @Schema(description = "Codido da Nota Fiscal", example = "N°000.000.000")
    @NotBlank(message = "O código não pode ser vazio")
    private String codigo;

    @Schema(description = "Data de Emissão da Nota Fiscal", example = "dd/MM/yyyy")
    @NotBlank(message = "A data de emissão não pode ser vazia")
    private Date dataEmissao;

    @Schema(description = "ID do Pedido", example = "1")
    @NotBlank(message = "O ID do pedido não pode ser vazio")
    private Long idPedido;

    @Schema(description = "Valor da Nota Fiscal", example = "100.00")
    @NotBlank(message = "O valor da nota fiscal não pode ser vazio")
    private Double valor;

    public NotaFiscalRequestDTO() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NotaFiscalRequestDTO that = (NotaFiscalRequestDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(dataEmissao, that.dataEmissao) && Objects.equals(idPedido, that.idPedido) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, dataEmissao, idPedido, valor);
    }
}
