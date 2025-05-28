package com.web2.movelcontrol.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.Objects;

@Schema(name = "NotaFIscalResponseDTO", description = "Dados de saída de uma nota fiscal retornados pela API")
public class NotaFIscalResponseDTO {

    @Schema(description = "Codido da Nota Fiscal", example = "N°000.000.000")
    private String codigo;
    @Schema(description = "Data de Emissão da Nota Fiscal", example = "dd/MM/yyyy")
    private Date dataEmissao;
    @Schema(description = "ID do Pedido", example = "1")
    private Long idPedido;
    @Schema(description = "Valor da Nota Fiscal", example = "100.00")
    private Double valor;

    public NotaFIscalResponseDTO() {
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
        NotaFIscalResponseDTO that = (NotaFIscalResponseDTO) o;
        return Objects.equals(codigo, that.codigo) && Objects.equals(dataEmissao, that.dataEmissao) && Objects.equals(idPedido, that.idPedido) && Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, dataEmissao, idPedido, valor);
    }
}

