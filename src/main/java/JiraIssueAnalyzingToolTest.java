
import org.apache.http.auth.AuthenticationException;

/**
 * Created by intern on 2016-07-19.
 */
public class JiraIssueAnalyzingToolTest {

    public static void main(String args[]) throws AuthenticationException {

        final String DO_FUTURETYPE_ISSUES_URL = "https://issue.daou.co.kr/rest/api/latest/search?jql=" +
                "project%20=%20AUTOTEST%20AND%20issuetype%20=%20%EC%8B%A0%EA%B7%9C%EA%B0%9C%EB%B0%9C%20AND%20%EC%A0%9C%ED%92%88%EA%B5%AC%EB%B6%84%20=%20%22DO%22%20ORDER%20BY%20priority%20DESC,%20updated%20DESC&maxResults=1000";
        final String TMSE_FUTURETYPE_ISSUES_URL = "https://issue.daou.co.kr/rest/api/latest/search?jql=" +
                "project%20=%20AUTOTEST%20AND%20issuetype%20=%20%EC%8B%A0%EA%B7%9C%EA%B0%9C%EB%B0%9C%20AND%20%EC%A0%9C%ED%92%88%EA%B5%AC%EB%B6%84%20=%20TMSe%20ORDER%20BY%20priority%20DESC,%20updated%20DESC&maxResults=1000";

        FindIssueQuantity findIssueQuantity = new FindIssueQuantity();
        FindIssueWorkingTime findIssueWorkingTime = new FindIssueWorkingTime();

        int doIssuesTotalQuantity =
                findIssueQuantity.findIssuesTotalQuantity(DO_FUTURETYPE_ISSUES_URL);

        int dototalworkingtime =
                findIssueWorkingTime.findTotalWorkingtime(DO_FUTURETYPE_ISSUES_URL);

        int doExistingTimespentIssuesQuantity =
                findIssueQuantity.findExistingTimespentissue(DO_FUTURETYPE_ISSUES_URL);

        int tmseIssuesTotalQuantity =
                findIssueQuantity.findIssuesTotalQuantity(TMSE_FUTURETYPE_ISSUES_URL);

        int tmsetotalworkingtime =
                findIssueWorkingTime.findTotalWorkingtime(TMSE_FUTURETYPE_ISSUES_URL);

        int tmseExistingTimespentIssuesQuantity =
                findIssueQuantity.findExistingTimespentissue(TMSE_FUTURETYPE_ISSUES_URL);

        System.out.println("DaouOffice 신규기능 타입의 Issue 총 갯수  : " + doIssuesTotalQuantity + "개");
        System.out.println("DaouOffice 신규기능 타입의 작업 시간 합계 : " + dototalworkingtime / 60 + "분");
        System.out.println("DaouOffice 신규기능 타입의 작업 시간 평균 : " + dototalworkingtime / (60 * doExistingTimespentIssuesQuantity) + "분\n");

        System.out.println("TMSe 신규기능 타입의 Issue 총 갯수  : " + tmseIssuesTotalQuantity + "개");
        System.out.println("TMSe 신규기능 타입의 작업 시간 합계 : " + tmsetotalworkingtime / 60 + "분");
        System.out.println("TMSe 신규기능 타입의 작업 시간 평균 : " + tmsetotalworkingtime / (60 * tmseExistingTimespentIssuesQuantity) + "분");
    }

}
