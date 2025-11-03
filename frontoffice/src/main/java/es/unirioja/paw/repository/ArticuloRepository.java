package es.unirioja.paw.repository;

import es.unirioja.paw.jpa.ArticuloEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticuloRepository extends JpaRepository<ArticuloEntity, String> {
    @Query("SELECT a FROM ArticuloEntity a WHERE (:fabricante = '-1' OR a.fabricante = :fabricante) AND (:tipo = '-1' OR a.tipo = :tipo)")
    Page<ArticuloEntity> findByFabricanteAndTipo(@Param("fabricante") String fabricante, @Param("tipo") String tipo, Pageable pageable);
}