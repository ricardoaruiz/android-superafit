package superafit.rar.com.br.superafit.model;

import java.io.Serializable;

/**
 * Created by ralmendro on 07/06/17.
 */

public class Device implements Serializable {

    private static final long serialVersionUID = 5440862437282527615L;

    private boolean syncronized;

    private String token;

    public boolean isSyncronized() {
        return syncronized;
    }

    public void setSyncronized(boolean syncronized) {
        this.syncronized = syncronized;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void syncronized() {
        this.syncronized = true;
    }
}
