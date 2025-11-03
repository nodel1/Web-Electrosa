package es.unirioja.paw.repository;

import es.unirioja.paw.jpa.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, String> {

    @Query("select SUM(cantidad) from StockEntity s where s.codigoArticulo = ?#{[0]}")
    Integer findStockByArticulo(@Param("codigoArticulo") String codigoArticulo);

    List<StockEntity> findByCodigoArticulo(String codigoArticulo);
}
