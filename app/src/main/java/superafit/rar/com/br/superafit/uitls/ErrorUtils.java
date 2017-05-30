package superafit.rar.com.br.superafit.uitls;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.response.ErrorsResponse;

/**
 * Created by ralmendro on 30/05/17.
 */

public class ErrorUtils {

    public static ErrorsResponse parseError(ResponseBody response) {

        Converter<ResponseBody, ErrorsResponse> converter = ServiceFactory.getInstance().getRetrofit()
                .responseBodyConverter(ErrorsResponse.class, new Annotation[0]);

        ErrorsResponse error;

        try {
            error = converter.convert(response);
        } catch (IOException e) {
            return new ErrorsResponse();
        }

        return error;
    }

}
