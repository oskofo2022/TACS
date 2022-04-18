package domain.responses.gets.lists;

import java.util.List;

public record ResponseGetPagedList<TResponseGetList>(long pageCount,
                                                     List<TResponseGetList> pageItems,
                                                     long totalCount) { }
