package com.mapofzones.calculations.common.postgres.repository;

import com.mapofzones.calculations.common.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "ibaVolumeZoneRepository")
public interface ZoneRepository extends JpaRepository<Zone, String> {

    List<Zone> findAllByIsMainnetTrue();

}
