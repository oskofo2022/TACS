package domain.integrations.APIs.dictionaries.spanish;

import domain.integrations.APIs.dictionaries.spanish.entities.SpanishDictionaryWordResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpanishDictionaryAPI {
    @GET("/api/v2/entries/es/{word}?fields=definitions&strictMatch=false")
    Call<SpanishDictionaryWordResponse> get(@Path("word") String word);
}
