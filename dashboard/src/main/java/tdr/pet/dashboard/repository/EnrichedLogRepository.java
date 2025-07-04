package tdr.pet.dashboard.repository;

import model.entity.EnrichedLogEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrichedLogRepository extends ElasticsearchRepository<EnrichedLogEvent, UUID> {
}
