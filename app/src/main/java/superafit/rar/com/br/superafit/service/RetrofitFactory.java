package superafit.rar.com.br.superafit.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ralmendro on 27/05/17.
 */

public class RetrofitFactory {

    private Retrofit retrofit;

    public RetrofitFactory() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://private-60fce-superafit.apiary-mock.com/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();

    }

    public LoginService getLoginService() {
        return this.retrofit.create(LoginService.class);
    }
}
