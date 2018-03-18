package ws.client;


import org.codehaus.jackson.annotate.JsonProperty;
public class StateObject {
    @JsonProperty("RestResponse")
    RestResponse restResponse;
    public RestResponse getRestResponse() {
        return restResponse;
    }
    public void setRestResponse(RestResponse restResponse) {
        this.restResponse = restResponse;
    }
}