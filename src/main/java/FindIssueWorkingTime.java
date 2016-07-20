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
public class FindIssueWorkingTime {

    /**
     * jql필터 조건에 맞는 이슈의 작업시간 총합을 반환해준다.
     *
     * @param jiraurl
     * @return 필터 조건에 맞는 jira이슈들의 총 작업시간
     */
    public int findTotalWorkingtime(String jiraurl) throws AuthenticationException {

        int WORKINGTIME      = 0;
        int TOTAL_WORKINGTIME = 0;

        String auth = new String(Base64.encode("hoguma33:hoguma33"));

        Client client = Client.create();

        WebResource webResource = client.resource(jiraurl);

        ClientResponse response = webResource.header("Authorization", "Basic " + auth)
                .type("application/json").accept("application/json").get(ClientResponse.class);

        int statusCode = response.getStatus();

        if (statusCode == 401) {
            throw new AuthenticationException("Invalid Username or Password");
        }

        String jiraissuejsondata = response.getEntity(String.class);

        JSONObject jirajsonobj = new JSONObject(jiraissuejsondata);

        JSONArray projectArray = jirajsonobj.getJSONArray("issues");
        for (int i = 0; i < projectArray.length(); i++) {
            JSONObject doissue = projectArray.getJSONObject(i);
            if(!doissue.getJSONObject("fields").get("timespent").equals(null)) {
                WORKINGTIME = Integer.parseInt(doissue.getJSONObject("fields").get("timespent").toString());
                TOTAL_WORKINGTIME += WORKINGTIME;
            }
        }

        return TOTAL_WORKINGTIME;
    }
}
