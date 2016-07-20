import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by intern on 2016-07-20.
 */
public class FindIssueQuantity {


    /**
     * jql필터 조건에 맞는 이슈의 개수를 반환해준다.
     *
     * @param jiraurl
     * @return 필터 조건에 맞는 jira이슈들의 총 갯수
     */

    public int findIssuesTotalQuantity(String jiraurl) throws AuthenticationException {

        String auth = new String(Base64.encode("hoguma33:hoguma33"));

        Client client = Client.create();

        WebResource jirawebResource = client.resource(jiraurl);

        ClientResponse response = jirawebResource.header("Authorization", "Basic " + auth)
                .type("application/json").accept("application/json").get(ClientResponse.class);

        int statusCode = response.getStatus();

        if (statusCode == 401) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        String jiraissuesjsondata = response.getEntity(String.class);

        JSONObject jiraissuesjsonobj = new JSONObject(jiraissuesjsondata);

        return Integer.parseInt(jiraissuesjsonobj.get("total").toString());
    }


    /**
     * jql필터 조건에 맞는 이슈중 작업시간이 있는 이슈들의 갯수 반환
     *
     * @param jiraurl
     * @return 필터 조건에 맞는 작업시간이 있는 이슈들의 갯수
     */
    public int findExistingTimespentissue(String jiraurl) throws AuthenticationException {

        int EXISTING_TIMESPENT_ISSUEQUANTITY = 0;

        String auth = new String(Base64.encode("hoguma33:hoguma33"));

        Client client = Client.create();

        WebResource jirawebResource = client.resource(jiraurl);

        ClientResponse response = jirawebResource.header("Authorization", "Basic " + auth)
                .type("application/json").accept("application/json").get(ClientResponse.class);

        int statusCode = response.getStatus();

        if (statusCode == 401) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        String jiraissuesjsondata = response.getEntity(String.class);

        JSONObject jiraissuesjsonobj = new JSONObject(jiraissuesjsondata);

        JSONArray projectArray = jiraissuesjsonobj.getJSONArray("issues");
        for (int i = 0; i < projectArray.length(); i++) {
            JSONObject doissue = projectArray.getJSONObject(i);
            if (!doissue.getJSONObject("fields").get("timespent").equals(null)) {
                EXISTING_TIMESPENT_ISSUEQUANTITY += 1;
            }

        }

        return EXISTING_TIMESPENT_ISSUEQUANTITY;
    }


}
