package com.example.restaurant;

import com.example.restaurant.model.entity.Address;
import com.example.restaurant.model.entity.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestaurantApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "management.server.port=0", "management.context-path=/admin"})
public class RestaurantControllerIntegrationTests extends
        AbstractRestaurantControllerTests {

    //Required to Generate JSON content from Java objects
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private final TestRestTemplate restTemplate = new TestRestTemplate();
    @LocalServerPort // shorthand for @Value("${local.server.port}")
    private int port;

    /**
     * Test the GET /v1/restaurants/{id} API
     */
    @Test
    public void testGetById() {
        //API call
        Map<String, Object> response
                = restTemplate.getForObject("http://localhost:" + port + "/v1/restaurants/1", Map.class);

        Assert.assertNotNull(response);

        //Asserting API Response
        String id = response.get("id").toString();
        Assert.assertNotNull(id);
        Assert.assertEquals("1", id);
        String name = response.get("name").toString();
        Assert.assertNotNull(name);
        Assert.assertEquals("Le Meurice", name);
        boolean isModified = (boolean) response.get("isModified");
        Assert.assertEquals(false, isModified);
        List<Table> tableList = (List<Table>) response.get("tables");
        Assert.assertNull(tableList);
    }

    /**
     * Test the GET /v1/restaurants/{id} API for no content
     */
    @Test
    public void testGetById_NoContent() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseE = restTemplate
                .exchange("http://localhost:" + port + "/v1/restaurants/99", HttpMethod.GET, entity,
                        Map.class);

        Assert.assertNotNull(responseE);

        // Should return no content as there is no restaurant with id 99
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseE.getStatusCode());
    }

    /**
     * Test the GET /v1/restaurants API
     */
    @Test
    public void testGetByName() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("name", "Meurice");
        ResponseEntity<Map[]> responseE = restTemplate
                .exchange("http://localhost:" + port + "/v1/restaurants?name={name}", HttpMethod.GET,
                        entity, Map[].class, uriVariables);

        Assert.assertNotNull(responseE);

        // Should return no content as there is no restaurant with id 99
        Assert.assertEquals(HttpStatus.OK, responseE.getStatusCode());
        Map<String, Object>[] responses = responseE.getBody();
        Assert.assertNotNull(responses);

        // Assumed only single instance exist for restaurant name contains word "Meurice"
        Assert.assertEquals(1, responses.length);

        Map<String, Object> response = responses[0];
        String id = response.get("id").toString();
        Assert.assertNotNull(id);
        Assert.assertEquals("1", id);
        String name = response.get("name").toString();
        Assert.assertNotNull(name);
        Assert.assertEquals("Le Meurice", name);
        boolean isModified = (boolean) response.get("isModified");
        Assert.assertFalse(isModified);
        List<Table> tableList = (List<Table>) response.get("tables");
        Assert.assertNull(tableList);
    }

    /**
     * Test the POST /v1/restaurants API
     */
    @Test
    public void testAdd() throws JsonProcessingException {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "La Plaza Restaurant");
        requestBody.put("id", "11");
        Address addressTest = new Address("1", "la plaza", 3, "rue des cocqulicot",
                75001,"France");
        requestBody.put("address", addressTest);
        Map<String, Object> table1 = new HashMap<>();
        table1.put("name", "Table 1");
        table1.put("id", 1);
        table1.put("capacity", 6);
        Map<String, Object> table2 = new HashMap<>();
        table2.put("name", "Table 2");
        table2.put("id", 2);
        table2.put("capacity", 4);
        Map<String, Object> table3 = new HashMap<>();
        table3.put("name", "Table 3");
        table3.put("id", 3);
        table3.put("capacity", 2);
        List<Map<String, Object>> tableList = new ArrayList();
        tableList.add(table1);
        tableList.add(table2);
        tableList.add(table3);
        requestBody.put("tables", tableList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(requestBody),
                headers);

        ResponseEntity<Map> responseE = restTemplate
                .exchange("http://localhost:" + port + "/v1/restaurants", HttpMethod.POST, entity,
                        Map.class, Collections.EMPTY_MAP);

        Assert.assertNotNull(responseE);

        // Should return created (status code 201)
        Assert.assertEquals(HttpStatus.CREATED, responseE.getStatusCode());

        //validating the newly created restaurant using API call
        Map<String, Object> response
                = restTemplate.getForObject("http://localhost:" + port + "/v1/restaurants/11", Map.class);

        Assert.assertNotNull(response);

        //Asserting API Response
        String id = response.get("id").toString();
        Assert.assertNotNull(id);
        Assert.assertEquals("11", id);
        String name = response.get("name").toString();
        Assert.assertNotNull(name);
        Assert.assertEquals("La Plaza Restaurant", name);
        boolean isModified = (boolean) response.get("isModified");
        String address = response.get("address").toString();
        Assert.assertEquals("{id=1, name=la plaza, isModified=false, number=3, " +
                "street=rue des cocqulicot, zipcode=75001, country=France}", address);


        Assert.assertEquals(false, isModified);
        List<Map<String, Object>> tableList2 = (List<Map<String, Object>>) response.get("tables");
        Assert.assertNotNull(tableList2);
        Assert.assertEquals(tableList2.size(), 3);
        tableList2.stream().forEach((table) -> {
            Assert.assertNotNull(table);
            Assert.assertNotNull(table.get("name"));
            Assert.assertNotNull(table.get("id"));
            Assert.assertTrue((Integer) table.get("capacity") > 0);
        });
    }

}
