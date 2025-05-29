package com.web2.movelcontrol.Model;

import jakarta.persistence.*;
import java.io.Serializable; // Boa prática para chaves compostas

// --- Entidade para a tabela associativa OrcamentoItem ---
// --- Esta entidade permitirá que um Orçamento tenha múltiplos Itens, com quantidades específicas ---

@Entity
@Table(name = "orcamento_item")
public class OrcamentoItem {

        //Chave Primária Composta
        @EmbeddedId
        private OrcamentoItemKey id;

       @ManyToOne (fetch = FetchType.LAZY)
        @MapsId("orcamentoId")
        @JoinColumn(name = "orcamento_id")
        private Orcamento orcamento;

       @ManyToOne(fetch = FetchType.LAZY)
        @MapsId("itemId")
        @JoinColumn(name = "item_id")
        private Item item;

       @Column(nullable = false)
        private Integer quantity;

        public OrcamentoItem() {
            this.id = new OrcamentoItemKey();
        }
        public OrcamentoItem(Orcamento orcamento, Item item, Integer quantity) {
                this.id = new OrcamentoItemKey(orcamento.getId(), item.getId());
                this.orcamento = orcamento;
                this.item = item;
                this.quantity = quantity;
        }

        public OrcamentoItemKey getId() {
                return id;
        }

        public void setId(OrcamentoItemKey id) {
                this.id = id;
        }

        public Orcamento getOrcamento() {
                return orcamento;
        }
        public void setOrcamento(Orcamento orcamento) {
                this.orcamento = orcamento;
        }

        public Item getItem() {
                return item;
        }

        public void setItem(Item item) {
                this.item = item;
        }

        public Integer getQuantity() {
                return quantity;
        }

        public void setQuantity(Integer quantity) {
                this.quantity = quantity;
        }

        //Método para calcular o subtotal deste item no orçamento
        public double getSubtotal() {
                if (item != null && item.getPrecoUnitario() != null && quantity != null) {
                        return item.getPrecoUnitario() * quantity;
                }
                return 0.0;
        }

        @Embeddable
        public static class OrcamentoItemKey implements Serializable {

                @Column(name = "orcamento_id")
                private Long orcamentoId;

               @Column(name ="item_id")
               private Long itemId;

                public OrcamentoItemKey() {
                }

                public OrcamentoItemKey(Long orcamentoId, Long itemId) {
                        this.orcamentoId = orcamentoId;
                        this.itemId = itemId;
                }

                public Long getItemId() {
                        return itemId;
                }

                public void setItemId(Long itemId) {
                        this.itemId = itemId;
                }

                public Long getOrcamentoId() {
                        return orcamentoId;
                }

                public void setOrcamentoId(Long orcamentoId) {
                        this.orcamentoId = orcamentoId;
                }

                @Override
                public int hashCode() {
                        final int prime = 31;
                        int result = 1;
                        result = prime * result + ((orcamentoId == null) ? 0 : orcamentoId.hashCode());
                        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
                        return result;
                }

                @Override
                public boolean equals(Object obj) {
                        if (this == obj)
                                return true;
                        if (obj == null)
                                return false;
                        if (getClass() != obj.getClass())
                                return false;
                        OrcamentoItemKey other = (OrcamentoItemKey) obj;
                        if (orcamentoId == null) {
                                if (other.orcamentoId != null)
                                        return false;
                        } else if (!orcamentoId.equals(other.orcamentoId))
                                return false;
                        if (itemId == null) {
                                if (other.itemId != null)
                                        return false;
                        } else if (!itemId.equals(other.itemId))
                                return false;
                        return true;
                }
        }
}