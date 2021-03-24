package is.hi.hbv601g.kosmosinn_mobile.Controllers;

public class NetworkController {
    public void getAllBoards() {
        String url = "http://192.168.0.103:8080/api/boards";
    }
    public void getAllTopics() { String url = "http://192.168.0.103:8080/api/topics"; }
    public void getTopicsById(long id) { String url = "http://192.168.0.103:8080/api/topics/" + id; }
}
