package domain.integrations.APIs.dictionaries.english;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class EnglishDictionaryBean {

    @Bean
    public EnglishDictionaryAPI getEnglishDictionaryAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("https://api.dictionaryapi.dev")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        return retrofit.create(EnglishDictionaryAPI.class);
    }
}
