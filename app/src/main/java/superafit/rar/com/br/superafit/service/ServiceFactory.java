package superafit.rar.com.br.superafit.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ralmendro on 27/05/17.
 */

public class ServiceFactory {

    private static ServiceFactory instance;

    private Retrofit retrofit;

    private ServiceFactory() {

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

    public static ServiceFactory getInstance() {
        if(instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public LoginService getLoginService() {
        return this.retrofit.create(LoginService.class);
    }

    public UserService getUserService() {
        return this.retrofit.create(UserService.class);
    }

    public ScheduleService getScheduleService() {
        return this.retrofit.create(ScheduleService.class);
    }
}
