package tdr.pet.dashboard.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import model.dto.EnrichedLogEventDto;
import model.entity.EnrichedLogEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EnrichedLogRepositoryImpl implements EnrichedLogRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchClient elasticsearchClient;

    public EnrichedLogRepositoryImpl(ElasticsearchOperations elasticsearchOperations,
                                     ElasticsearchClient elasticsearchClient) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public Page<EnrichedLogEvent> searchByDto(EnrichedLogEventDto dto, Pageable pageable) {
        Criteria criteria = buildCriteriaFromDto(dto);
        CriteriaQuery query = new CriteriaQuery(criteria).setPageable(pageable);

        SearchHits<EnrichedLogEvent> hits = elasticsearchOperations.search(query, EnrichedLogEvent.class);

        List<EnrichedLogEvent> content = hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, hits.getTotalHits());
    }

    @Override
    public Page<EnrichedLogEvent> findAll(Pageable pageable) {
        CriteriaQuery query = new CriteriaQuery(new Criteria()).setPageable(pageable);

        SearchHits<EnrichedLogEvent> hits = elasticsearchOperations.search(query, EnrichedLogEvent.class);

        List<EnrichedLogEvent> content = hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, hits.getTotalHits());
    }

    private Criteria buildCriteriaFromDto(EnrichedLogEventDto dto) {
        Criteria criteria = new Criteria();

        if (dto.getId() != null && !dto.getId().isEmpty()) {
            criteria = criteria.and(Criteria.where("id").is(dto.getId()));
        }

        if (dto.getIp() != null && !dto.getIp().isEmpty()) {
            criteria = criteria.and(Criteria.where("ip").is(dto.getIp()));
        }

        if (dto.getMessage() != null && !dto.getMessage().isEmpty()) {
            criteria = criteria.and(Criteria.where("message").contains(dto.getMessage()));
        }

        if (dto.getRawUserAgent() != null && !dto.getRawUserAgent().isEmpty()) {
            criteria = criteria.and(Criteria.where("rawUserAgent").contains(dto.getRawUserAgent()));
        }

        if (dto.getUserId() != null && !dto.getUserId().isEmpty()) {
            criteria = criteria.and(Criteria.where("userId").is(dto.getUserId()));
        }

        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            criteria = criteria.and(Criteria.where("status").is(dto.getStatus()));
        }

        if (dto.getTimestamp() != null) {
            criteria = criteria.and(Criteria.where("timestamp").is(dto.getTimestamp()));
        }

        if (dto.getSeverity() != null && !dto.getSeverity().isEmpty()) {
            criteria = criteria.and(Criteria.where("severity").is(dto.getSeverity()));
        }

        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            criteria = criteria.and(Criteria.where("tags").in(dto.getTags()));
        }

        return criteria;
    }

    @Override
    public Page<EnrichedLogEvent> searchByText(String searchText, Pageable pageable) throws IOException {
        SearchRequest request = SearchRequest.of(s -> s
                .index("enriched_log_event")
                .query(q -> q
                        .multiMatch(m -> m
                                .fields("message")
                                .query(searchText)
                        )
                )
        );

        SearchResponse<EnrichedLogEvent> response = elasticsearchClient.search(request, EnrichedLogEvent.class);
        List<EnrichedLogEvent> content = response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, response.hits().total() != null ? response.hits().total().value() : 0);
    }
}