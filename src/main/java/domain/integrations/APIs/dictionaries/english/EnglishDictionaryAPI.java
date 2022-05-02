package domain.integrations.APIs.dictionaries.english;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface EnglishDictionaryAPI {
    @GET("api/v2/entries/en/{word}")
    Call<List<EnglishDictionaryWordResponse>> get(@Path("word") String word);
}
