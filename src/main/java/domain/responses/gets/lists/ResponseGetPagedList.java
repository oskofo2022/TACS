package domain.responses.gets.lists;

import java.util.List;

public record ResponseGetPagedList<TResponseGetList>(int pageCount,
                                                     List<TResponseGetList> pageItems,
                                                     int totalCount) { }
