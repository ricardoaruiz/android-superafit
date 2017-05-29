package superafit.rar.com.br.superafit.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ralmendro on 27/05/17.
 */

public class RetrofitFactory {

    private static RetrofitFactory instance;

    private Retrofit retrofit;

    private RetrofitFactory() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        this.retrofit = new Retrofit.Builder()
                .baseUrl(ServiceConstants.API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();

    }

    public static RetrofitFactory getInstance() {
        if(instance == null) {
            instance = new RetrofitFactory();
        }
        return instance;
    }

    public LoginService getLoginService() {
        return this.retrofit.create(LoginService.class);
    }
}
