package superafit.rar.com.br.superafit.converter;

import java.util.List;

/**
 * Created by ralmendro on 06/06/17.
 */

public interface Converter<M, S> {

    M toModel(S object);

    List<M> toModel(List<S> source);

    S fromModel(M object);

    List<S> fromModel(List<M> source);

}
