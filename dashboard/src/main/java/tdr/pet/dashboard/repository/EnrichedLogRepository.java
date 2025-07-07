package tdr.pet.dashboard.repository;

import model.dto.EnrichedLogEventDto;
import model.entity.EnrichedLogEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface EnrichedLogRepository {
    Page<EnrichedLogEvent> searchByDto(EnrichedLogEventDto dto, Pageable pageable);

    Page<EnrichedLogEvent> findAll(Pageable pageable);

    Page<EnrichedLogEvent> searchByText(String searchText, Pageable pageable) throws IOException;

}