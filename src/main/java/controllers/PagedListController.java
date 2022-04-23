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

        final long totalCount = filter.map(abstractRepository::count)
                                      .orElseGet(abstractRepository::count);

        final var pageCount = ((totalCount - 1) / requestGetPagedList.getPageSize()) + 1;

        final var entities = filter.map(s -> abstractRepository.findAll(s, pageRequest))
                                                .orElseGet(() -> abstractRepository.findAll(pageRequest));

        final var responsesGetList = entities.stream()
                                                                .map(mapping)
                                                                .toList();

        return new ResponseGetPagedList<>(pageCount, responsesGetList, totalCount);
    }
}
