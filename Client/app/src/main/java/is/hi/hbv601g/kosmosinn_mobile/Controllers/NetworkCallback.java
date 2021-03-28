package is.hi.hbv601g.kosmosinn_mobile.Controllers;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}
