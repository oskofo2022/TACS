package domain.integrations.APIs.dictionaries.spanish;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
public class SpanishDictionaryBean {

    @Bean
    public SpanishDictionaryAPI getSpanishDictionaryAPI() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                                                  .addInterceptor(
                                                      chain -> {
                                                        final var request = chain.request()
                                                                                         .newBuilder()
                                                                                         .addHeader(SpanishDictionaryAPIConstants.Headers.APP_ID, "dc7eb62a")
                                                                                         .addHeader(SpanishDictionaryAPIConstants.Headers.APP_KEY, "25ed7f9b3166f1107dcd64a2dd06d2ee")
                                                                                         .build();
                                                        return chain.proceed(request);
                                                      }).build();

        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("https://od-api.oxforddictionaries.com")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .client(httpClient)
                                        .build();

        return retrofit.create(SpanishDictionaryAPI.class);
    }
}
