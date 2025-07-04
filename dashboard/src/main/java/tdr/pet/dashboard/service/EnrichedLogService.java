package tdr.pet.dashboard.service;

import model.dto.EnrichedLogEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnrichedLogService {
    Page<EnrichedLogEventDto> searchEnrichedLogEvents(Pageable pageable, EnrichedLogEventDto enrichedLogEventDto);
}
