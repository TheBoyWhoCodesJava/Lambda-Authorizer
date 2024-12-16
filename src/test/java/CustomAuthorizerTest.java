
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.nt.CustomAuthorizer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Map;
import java.util.List;

public class CustomAuthorizerTest {

    @Test
    public void testAllowRequestWithJayHeader() {

        Context context = mock(Context.class);
        APIGatewayProxyRequestEvent input = new APIGatewayProxyRequestEvent();
        input.setHeaders(Map.of("name", "jay"));
        CustomAuthorizer authorizer = new CustomAuthorizer();
        Map<String, Object> response = authorizer.handleRequest(input, context);
        assertEquals("jay", response.get("principalId"));
        List<Map<String, Object>> statementList = (List<Map<String, Object>>) ((Map<String, Object>) response.get("policyDocument")).get("Statement");
        Map<String, Object> statement = statementList.get(0); // Accessing first element of list
        assertEquals("Allow", statement.get("Effect"));
    }

    @Test
    public void testDenyRequestWithOtherHeader() {

        Context context = mock(Context.class);
        APIGatewayProxyRequestEvent input = new APIGatewayProxyRequestEvent();
        input.setHeaders(Map.of("name", "unknown"));
        CustomAuthorizer authorizer = new CustomAuthorizer();
        Map<String, Object> response = authorizer.handleRequest(input, context);
        assertEquals("unknown", response.get("principalId"));
        List<Map<String, Object>> statementList = (List<Map<String, Object>>) ((Map<String, Object>) response.get("policyDocument")).get("Statement");
        Map<String, Object> statement = statementList.get(0); //Accessing first element of list
        assertEquals("Deny", statement.get("Effect"));
    }
}
