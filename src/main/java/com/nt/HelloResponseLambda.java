package com.nt;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class HelloResponseLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        String nameHeader = input.getHeaders().get("name");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        // Checking name header value and access request to jay
        if ("jay".equalsIgnoreCase(nameHeader)) {
            response.setStatusCode(200);
            response.setBody("Hello Jay Welcome, Access Granted");
            System.out.println("Access Granted To Jay");
        } else {

            response.setStatusCode(403);
            response.setBody("Unauthorized User Access Denied");
            System.out.println("Unauthorized User Access Denied");
        }
        return response;
    }
}
