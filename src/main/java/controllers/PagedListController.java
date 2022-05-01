package controllers;

import domain.persistence.repositories.AbstractRepository;
import domain.requests.gets.lists.RequestGetPagedList;
import domain.responses.gets.lists.ResponseGetPagedList;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.function.Function;

public abstract class PagedListController {
    protected <TEntity, TResponseGetList> ResponseGetPagedList<TResponseGetList> list(AbstractRepository<TEntity> abstractRepository, RequestGetPagedList requestGetPagedList, Function<TEntity, TResponseGetList> mapping) {
        final Optional<Specification<TEntity>> filter = requestGetPagedList.getFilter()
                                                                           .map(RSQLJPASupport::toSpecification);
        final var pageRequest = requestGetPagedList.getPageRequest();

        final var pageEntities = filter.map(s -> abstractRepository.findAll(s, pageRequest))
                                                    .orElseGet(() -> abstractRepository.findAll(pageRequest));

        final var totalElements = pageEntities.getTotalElements();
        final var totalPages = pageEntities.getTotalPages();

        final var responsesGetList = pageEntities.stream()
                                                                    .map(mapping)
                                                                    .toList();

        return new ResponseGetPagedList<>(totalPages, responsesGetList, totalElements);
    }
}
