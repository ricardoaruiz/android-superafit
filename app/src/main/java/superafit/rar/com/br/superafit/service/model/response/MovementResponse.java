package superafit.rar.com.br.superafit.service.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by ralmendro on 03/06/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementResponse implements Serializable {

    private static final long serialVersionUID = 552832530182059345L;

    private String name;

    private String translate;

    private String description;

    private String qtRep;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQtRep() {
        return qtRep;
    }

    public void setQtRep(String qtRep) {
        this.qtRep = qtRep;
    }

}
