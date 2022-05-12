package domain.integrations.APIs.dictionaries.spanish;

import constants.ApplicationProperties;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class SpanishDictionaryBean {

    @Value(ApplicationProperties.Wordle.Integrations.Dictionaries.Spanish.Arguments.URL)
    private String url;

    @Value(ApplicationProperties.Wordle.Integrations.Dictionaries.Spanish.App.Arguments.ID)
    private String appId;

    @Value(ApplicationProperties.Wordle.Integrations.Dictionaries.Spanish.App.Arguments.KEY)
    private String appKey;

    @Bean
    public SpanishDictionaryAPI getSpanishDictionaryAPI() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                                                  .addInterceptor(
                                                      chain -> {
                                                        final var request = chain.request()
                                                                                         .newBuilder()
                                                                                         .addHeader(SpanishDictionaryAPIConstants.Headers.APP_ID, this.appId)
                                                                                         .addHeader(SpanishDictionaryAPIConstants.Headers.APP_KEY, this.appKey)
                                                                                         .build();
                                                        return chain.proceed(request);
                                                      }).build();

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(this.url)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(httpClient)
                                        .build();

        return retrofit.create(SpanishDictionaryAPI.class);
    }
}
