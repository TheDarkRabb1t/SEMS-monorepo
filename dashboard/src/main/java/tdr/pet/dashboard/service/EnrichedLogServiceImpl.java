package tdr.pet.dashboard.service;

import lombok.AllArgsConstructor;
import model.dto.EnrichedLogEventDto;
import model.mapper.EnrichedLogEventMapper;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "logs", key = "T(java.util.Objects).hash(#dto) + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<EnrichedLogEventDto> searchEnrichedLogEvents(Pageable pageable, EnrichedLogEventDto dto) {
        if (dto == null) {
            return enrichedLogRepository.findAll(pageable).map(enrichedLogEventMapper::toDto);
        }
        return enrichedLogRepository.searchByDto(dto, pageable).map(enrichedLogEventMapper::toDto);
    }
}
