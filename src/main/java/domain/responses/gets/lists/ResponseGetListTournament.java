package domain.responses.gets.lists;

import domain.repositories.entities.Language;
import domain.repositories.entities.Visibility;

import java.util.Date;

public record ResponseGetListTournament(long id,
                                        String Name,
                                        Language language,
                                        Visibility visibility,
                                        Date startDate,
                                        Date endDate) { }
