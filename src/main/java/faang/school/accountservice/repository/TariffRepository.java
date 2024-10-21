package faang.school.accountservice.repository;

import faang.school.accountservice.model.Tariff;
import faang.school.accountservice.model.enumeration.TariffType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {
    Optional<Tariff> findByTariffType(TariffType tariffType);
}
