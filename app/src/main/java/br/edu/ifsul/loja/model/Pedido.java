package br.edu.ifsul.loja.model;
import java.util.List;

public class Pedido {
    private String key;
    private String formaDePagamento;
    private String estado;
    private Long dataCriacao;
    private Long dataModificacao;
    private Double totalPedido;
    private Boolean situacao;
    private List<ItemPedido> itens; //associação entre as classes Pedido-ItemPedido
    private Cliente cliente; //associação entre as classes Pedido-Cliente

    public Pedido(List<ItemPedido> itens){
        this.itens = itens;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Long dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(Long dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(Boolean situacao) {
        this.situacao = situacao;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "Pedido{" +
                "key=" + key +
                ", formaDePagamento='" + formaDePagamento + '\'' +
                ", estado='" + estado + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", dataModificacao=" + dataModificacao +
                ", totalPedido=" + totalPedido +
                ", situacao=" + situacao +
                ", itens=" + itens +
                ", cliente=" + cliente +
                '}';
    }
}
