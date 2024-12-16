package com.nt;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import java.util.Map;
import java.util.Collections;

public class CustomAuthorizer implements RequestHandler<APIGatewayProxyRequestEvent, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        Map<String, String> headers = input.getHeaders();
        String nameHeader = headers != null ? headers.get("name") : null;
        if ("jay".equalsIgnoreCase(nameHeader)) {

            return generateAllowResponse("jay");
        } else {

            return generateDenyResponse("unknown");
        }
    }
    // Allow Policy
    private Map<String, Object> generateAllowResponse(String principalId) {
        return Map.of(
                "principalId", principalId,
                "policyDocument", Map.of(
                        "Version", "2012-10-17",
                        "Statement", Collections.singletonList(
                                Map.of(
                                        "Effect", "Allow",
                                        "Action", "*",
                                        "Resource", "*"
                                )
                        )
                )
        );
    }
    // Deny Policy
    private Map<String, Object> generateDenyResponse(String principalId) {
        return Map.of(
                "principalId", principalId,
                "policyDocument", Map.of(
                        "Version", "2012-10-17",
                        "Statement", Collections.singletonList(
                                Map.of(
                                        "Effect", "Deny",
                                        "Action", "*",
                                        "Resource", "*"
                                )
                        )
                )
        );
    }
}
