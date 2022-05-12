package domain.integrations.APIs.dictionaries.english;


import constants.ApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class EnglishDictionaryBean {

    @Value(ApplicationProperties.Wordle.Integrations.Dictionaries.English.Arguments.URL)
    private String url;

    @Bean
    public EnglishDictionaryAPI getEnglishDictionaryAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(this.url)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

        return retrofit.create(EnglishDictionaryAPI.class);
    }
}
