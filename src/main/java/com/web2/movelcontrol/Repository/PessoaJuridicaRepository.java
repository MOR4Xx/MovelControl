package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    List<PessoaJuridica> findByNome(String nome);
    List<PessoaJuridica> findByCnpj(String cnpj);
    List<PessoaJuridica> findByEmail(String email);
    //List<PessoaJuridica> findByTipo(String tipo);
    
    /*Após as alterações que fizemos, a entidade PessoaFisica não possui mais um campo (propriedade) chamado tipo.
 Nós removemos o private String tipo = "FISICA"; porque a informação do tipo agora é
 gerenciada pela coluna discriminadora (@DiscriminatorColumn(name = "tipo")) definida na superclasse
 Pessoa e pelo @DiscriminatorValue("FISICA") na classe PessoaFisica.
 */
}
