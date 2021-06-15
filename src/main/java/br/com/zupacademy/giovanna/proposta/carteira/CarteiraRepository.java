package br.com.zupacademy.giovanna.proposta.carteira;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    Optional<Carteira> findByCartaoIdAndTipo(String cartaoId, TipoCarteira tipo);
}
