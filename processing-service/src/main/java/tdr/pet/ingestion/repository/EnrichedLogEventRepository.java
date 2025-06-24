package tdr.pet.ingestion.repository;

import model.EnrichedLogEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EnrichedLogEventRepository extends ElasticsearchRepository<EnrichedLogEvent, Long> {
}
