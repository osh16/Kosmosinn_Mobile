package is.hi.hbv601g.kosmosinn_mobile.Controllers;

import org.json.JSONException;

public interface NetworkCallback<T> {

    void onSuccess(T result) throws JSONException;

    void onFailure(String errorString);
}
