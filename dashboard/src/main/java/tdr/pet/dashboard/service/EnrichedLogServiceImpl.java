package tdr.pet.dashboard.service;

import lombok.AllArgsConstructor;
import model.dto.EnrichedLogEventDto;
import model.mapper.EnrichedLogEventMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdr.pet.dashboard.repository.EnrichedLogRepository;

@Service
@AllArgsConstructor
public class EnrichedLogServiceImpl implements EnrichedLogService {

    private final EnrichedLogRepository enrichedLogRepository;
    private final EnrichedLogEventMapper enrichedLogEventMapper;

    @Override
    public Page<EnrichedLogEventDto> searchEnrichedLogEvents(Pageable pageable, EnrichedLogEventDto enrichedLogEventDto) {
        if (enrichedLogEventDto == null) {
            return enrichedLogRepository.findAll(pageable).map(enrichedLogEventMapper::toDto);
        }
        return enrichedLogRepository.searchSimilar(enrichedLogEventMapper.toEntity(enrichedLogEventDto),
                new String[]{}, pageable).map(enrichedLogEventMapper::toDto);
    }
}
