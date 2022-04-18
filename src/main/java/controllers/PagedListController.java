package controllers;

import domain.persistence.repositories.AbstractRepository;
import domain.requests.gets.lists.RequestGetPagedList;
import domain.responses.gets.lists.ResponseGetPagedList;
import org.springframework.data.domain.Page;

import java.util.function.Function;

import static io.github.perplexhub.rsql.RSQLJPASupport.toSpecification;

public abstract class PagedListController {
    protected <TEntity, TResponseGetList> ResponseGetPagedList<TResponseGetList> list(AbstractRepository<TEntity> abstractRepository, RequestGetPagedList requestGetPagedList, Function<TEntity, TResponseGetList> mapping) {
        var filter = requestGetPagedList.getFilter();
        var pageRequest = requestGetPagedList.getPageRequest();
        long totalCount = filter.map(s -> abstractRepository.count(toSpecification(s)))
                                .orElseGet(abstractRepository::count);

        var pageCount = ((totalCount - 1) / requestGetPagedList.getPageSize()) + 1;

        var entities = filter.map(s -> abstractRepository.findAll(toSpecification(s), pageRequest))
                                         .orElseGet(() -> abstractRepository.findAll(pageRequest));

        var responsesGetList = entities.stream()
                                                          .map(mapping)
                                                          .toList();

        return new ResponseGetPagedList<>(pageCount, responsesGetList, totalCount);
    }
}
