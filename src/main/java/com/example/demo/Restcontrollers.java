package com.example.demo;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import lombok.Getter;
import lombok.Setter;
import java.util.*;;


@Getter
@Setter
class NasaApodResponse {
    private Map<String, Object> resource; // Assuming a generic structure for flexibility
    private Boolean concept_tags;
    private String title;
    private String date;
    private String url;
    private String hdurl; // May be omitted if not available
    private String media_type;
    private String explanation;
    private String[] concepts; // Assuming an array of strings for the concepts
    private String thumbnail_url;
    private String copyright;
    private String service_version;}

@RestController
@RequestMapping("/apod")
public class Restcontrollers {

    @GetMapping("/hello")
    public String Hello(){
        return "hello";
    }

    @GetMapping("/call")
	public ResponseEntity<?> getterMethod(@RequestParam(value = "date", required = false) String date,
                            @RequestParam(value = "start_date", required = false) String start_date,
                            @RequestParam(value = "end_date",required = false) String end_date,
                            @RequestParam(value = "count",required = false) String count,
                            @RequestParam(value = "thumbs",required = false) String thumbs) {
		String apiKey = "IabhjfXdft4AXDugcZhBfr8fMRb1mfnk4RfYyNz0"; // Ideally store this securely
        StringBuilder urlBuilder = new StringBuilder("https://api.nasa.gov/planetary/apod?api_key=" + apiKey);

        // Append query parameters if present
        if (date != null) {
            urlBuilder.append("&date=").append(date);
        }
        if (start_date != null) {
            urlBuilder.append("&start_date=").append(start_date);
        }
        if (end_date != null) {
            urlBuilder.append("&end_date=").append(end_date);
        }
        if (count != null) {
            urlBuilder.append("&count=").append(count);
        }
        if (thumbs != null) {
            urlBuilder.append("&thumbs=").append(thumbs);
        }

        String url = urlBuilder.toString();
        System.out.println(url);

        /* RestTemplate restTemplate = new RestTemplate();
        NasaApodResponse result = restTemplate.getForObject(url, NasaApodResponse.class);
        return result; */

        RestTemplate restTemplate = new RestTemplate();
        
        if (start_date != null || end_date != null || count != null) {
            NasaApodResponse[] responsesArray = restTemplate.getForObject(url, NasaApodResponse[].class);
            List<NasaApodResponse> responsesList = Arrays.asList(responsesArray);
            return ResponseEntity.ok(responsesList);
        } else {
            NasaApodResponse response = restTemplate.getForObject(url, NasaApodResponse.class);
            return ResponseEntity.ok(response);
        }
	}
}