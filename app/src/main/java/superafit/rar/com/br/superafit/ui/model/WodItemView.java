package superafit.rar.com.br.superafit.ui.model;

import java.util.ArrayList;
import java.util.List;

import superafit.rar.com.br.superafit.model.*;

/**
 * Created by ralmendro on 19/08/17.
 */

public class WodItemView {

    private WodItem header;

    private List<WodItem> movements;

    public WodItem getHeader() {
        return header;
    }

    public void setHeader(WodItem header) {
        this.header = header;
    }

    public List<WodItem> getMovements() {
        if(movements == null) {
            movements = new ArrayList<WodItem>();
        }
        return movements;
    }

    public void setMovements(List<WodItem> movements) {
        this.movements = movements;
    }
}
