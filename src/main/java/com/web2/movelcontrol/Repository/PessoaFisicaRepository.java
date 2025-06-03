package com.web2.movelcontrol.Repository;

import com.web2.movelcontrol.Model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
	List<PessoaFisica> findByNome(String nome);
	
	List<PessoaFisica> findByCpf(String cpf);
	
	List<PessoaFisica> findByEmail(String email);
//    List<PessoaFisica> findByTipo(String tipo);
/*Após as alterações que fizemos, a entidade PessoaFisica não possui mais um campo (propriedade) chamado tipo.
 Nós removemos o private String tipo = "FISICA"; porque a informação do tipo agora é
 gerenciada pela coluna discriminadora (@DiscriminatorColumn(name = "tipo")) definida na superclasse
 Pessoa e pelo @DiscriminatorValue("FISICA") na classe PessoaFisica.
 */
}
