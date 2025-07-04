package tdr.pet.dashboard.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.dto.EnrichedLogEventDto;
import model.exception.entity.EntityNotFoundException;
import model.mapper.EnrichedLogEventMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tdr.pet.dashboard.repository.EnrichedLogRepository;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class EnrichedLogServiceImpl implements EnrichedLogService {

    private final EnrichedLogRepository enrichedLogRepository;
    private final EnrichedLogEventMapper enrichedLogEventMapper;

    @Override
    @Cacheable(value = "logs", key = "T(java.util.Objects).hashCode(#dto) + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<EnrichedLogEventDto> searchByDto(EnrichedLogEventDto dto, Pageable pageable) {
        if (dto == null) {
            return enrichedLogRepository.findAll(pageable).map(enrichedLogEventMapper::toDto);
        }
        return enrichedLogRepository.searchByDto(dto, pageable).map(enrichedLogEventMapper::toDto);
    }

    @Override
    @Cacheable(value = "logs", key = "#searchText + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<EnrichedLogEventDto> searchByText(String searchText, Pageable pageable) {
        try {
            return enrichedLogRepository.searchByText(searchText, pageable).map(enrichedLogEventMapper::toDto);
        } catch (IOException e) {
            log.error("Error during enriched log text search", e);
            throw new EntityNotFoundException("Couldn't find enriched log");
        }
    }
}
