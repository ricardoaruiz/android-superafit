package superafit.rar.com.br.superafit.converter;

import java.util.ArrayList;
import java.util.List;

import superafit.rar.com.br.superafit.model.Wod;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;

/**
 * Created by ralmendro on 06/06/17.
 */

public class WodConverter implements Converter<Wod, GetWodResponse> {

    private static WodConverter instance;

    private WodConverter() {
    }

    public static WodConverter getInstance() {
        if(instance == null) {
            instance = new WodConverter();
        }
        return instance;
    }

    @Override
    public Wod toModel(GetWodResponse source) {
        Wod toReturn = new Wod();
        toReturn.setType(source.getType());
        toReturn.setDate(source.getDate());
        toReturn.setRound(source.getRound());
        toReturn.setMovements(MovementConverter.getInstance().toModel(source.getMovements()));
        return toReturn;
    }

    @Override
    public List<Wod> toModel(List<GetWodResponse> source) {
        List<Wod> toReturn = new ArrayList<Wod>();
        for(GetWodResponse resp : source) {
            toReturn.add(toModel(resp));
        }
        return toReturn;
    }

    @Override
    public GetWodResponse fromModel(Wod source) {
        GetWodResponse toReturn = new GetWodResponse();
        toReturn.setType(source.getType());
        toReturn.setRound(source.getRound());
        toReturn.setDate(source.getDate());
        toReturn.setMovements(MovementConverter.getInstance().fromModel(source.getMovements()));
        return toReturn;
    }

    @Override
    public List<GetWodResponse> fromModel(List<Wod> source) {
        List<GetWodResponse> toReturn = new ArrayList<GetWodResponse>();
        for(Wod wod : source) {
            toReturn.add(fromModel(wod));
        }
        return toReturn;
    }
}
