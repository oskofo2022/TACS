package domain.responses.gets.lists;

import java.util.List;

public record ResponseGetListUserInscription(ResponseGetListTournament tournament, List<ResponseGetListUserInscriptionGame> games) { }
