import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO{

    private final String endPoint;

    public ApiPropertyAssessmentDAO(){
        this("https://data.edmonton.ca/resource/q7d6-ambg.csv");
    }

    public ApiPropertyAssessmentDAO(String endPoint){
        this.endPoint = endPoint;
    }

    String getData(String queryAddition) {
        String url = endPoint + queryAddition;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e){
            return "";
        }
    }

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        String response = getData("?account_number=" + accountNumber);

        String[] propertyAssessmentStringArray = response.replaceAll("\"", "").split("\n");

        return new PropertyAssessment(propertyAssessmentStringArray[1].split(","));
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        List<PropertyAssessment> neighbourhoodPropertyAssessments = new ArrayList<>();

        String response = getData("?neighbourhood=" + neighbourhood.toUpperCase());

        String[] propertyAssessments = response.replaceAll("\"", "").split("\n");

        for (int i = 1; i < propertyAssessments.length; i++) {
            neighbourhoodPropertyAssessments.add(new PropertyAssessment(propertyAssessments[i].split(",")));
        }

        return neighbourhoodPropertyAssessments;
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        return null;
    }
}
