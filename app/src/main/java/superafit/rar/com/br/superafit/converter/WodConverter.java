package superafit.rar.com.br.superafit.converter;

import java.util.ArrayList;
import java.util.List;

import superafit.rar.com.br.superafit.model.Wod;
import superafit.rar.com.br.superafit.model.WodItem;
import superafit.rar.com.br.superafit.service.model.response.GetWodDataResponse;
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
        List<WodItem> wodItemList = new ArrayList<>();

        for(GetWodDataResponse item : source.getData()) {
            WodItem wodItem = new WodItem();
            wodItem.setType(item.getType());
            wodItem.setDate(item.getDate());
            wodItem.setRound(item.getRound());
            wodItem.setDescription(item.getDescription());
            wodItem.setMovements(MovementConverter.getInstance().toModel(item.getMovements()));
            wodItemList.add(wodItem);
        }

        toReturn.setData(wodItemList);
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
        List<GetWodDataResponse> wodDataResponseList = new ArrayList<>();

        for(WodItem wodItem : source.getData()) {
            GetWodDataResponse item = new GetWodDataResponse();
            item.setType(wodItem.getType());
            item.setRound(wodItem.getRound());
            item.setDate(wodItem.getDate());
            item.setDescription(wodItem.getDescription());
            item.setMovements(MovementConverter.getInstance().fromModel(wodItem.getMovements()));
            wodDataResponseList.add(item);
        }

        toReturn.setData(wodDataResponseList);
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
