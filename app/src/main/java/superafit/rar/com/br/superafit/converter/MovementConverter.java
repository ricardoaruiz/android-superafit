package superafit.rar.com.br.superafit.converter;

import java.util.ArrayList;
import java.util.List;

import superafit.rar.com.br.superafit.model.WodMovement;
import superafit.rar.com.br.superafit.service.model.response.MovementResponse;

/**
 * Created by ralmendro on 06/06/17.
 */

public class MovementConverter implements Converter<WodMovement, MovementResponse> {

    private static MovementConverter instance;

    private MovementConverter() {
    }

    public static MovementConverter getInstance() {
        if(instance == null) {
            instance = new MovementConverter();
        }
        return instance;
    }

    @Override
    public WodMovement toModel(MovementResponse source) {
        WodMovement toReturn = new WodMovement();
        toReturn.setName(source.getName());
        toReturn.setTranslate(source.getTranslate());
        toReturn.setDescription(source.getDescription());
        toReturn.setQtRep(source.getQtRep());
        return toReturn;
    }

    @Override
    public List<WodMovement> toModel(List<MovementResponse> source) {
        List<WodMovement> toReturn = null;
        if(source != null) {
            toReturn = new ArrayList<WodMovement>();
            for (MovementResponse movement : source) {
                WodMovement mov = new WodMovement();
                mov.setName(movement.getName());
                mov.setDescription(movement.getDescription());
                mov.setTranslate(movement.getDescription());
                mov.setQtRep(movement.getQtRep());
                toReturn.add(mov);
            }
        }
        return toReturn;
    }

    @Override
    public MovementResponse fromModel(WodMovement source) {
        MovementResponse toReturn = new MovementResponse();
        toReturn.setName(source.getName());
        toReturn.setDescription(source.getDescription());
        toReturn.setTranslate(source.getTranslate());
        toReturn.setQtRep(source.getQtRep());
        return toReturn;
    }

    @Override
    public List<MovementResponse> fromModel(List<WodMovement> source) {
        List<MovementResponse> toReturn = new ArrayList<MovementResponse>();
        for (WodMovement movement : source) {
            toReturn.add(fromModel(movement));
        }
        return toReturn;
    }
}
