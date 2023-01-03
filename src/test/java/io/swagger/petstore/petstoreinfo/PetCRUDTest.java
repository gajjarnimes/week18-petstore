package io.swagger.petstore.petstoreinfo;

import cucumber.api.java.hu.Ha;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.swagger.petstore.constants.EndPoints;
import io.swagger.petstore.model.PetPojo;
import io.swagger.petstore.testbase.TestBase;
import io.swagger.petstore.utils.TestUtils;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasValue;

public class PetCRUDTest  extends TestBase {

    static int id = Integer.parseInt(99 + TestUtils.getRandomValue());

    static HashMap<String,Object> category;

    static String name = "Doggie" + TestUtils.getRandomValue();

    static List<String> photoUrls;

    static List<HashMap<String,Object>> tags;

    static String status = TestUtils.getRandomValue()+"available";

    static int petId;

    @Title("This Will create new Pet")
    @Test
    public void test01() {

        HashMap<String, Object> pet = new HashMap<>();
        pet.put("id", "9223372036854309000");
        pet.put("name", "abc");

        List<String> name = new ArrayList<>();
        name.add("String");

        List<HashMap<String, Object>> petList = new ArrayList<>();
        petList.add(pet);

        PetPojo petPojo = new PetPojo();
        petPojo.setId(id);
        petPojo.setCategory(pet);
        petPojo.setName("doggie");
        petPojo.setPhotoUrls(name);
        petPojo.setTags(petList);
        petPojo.setStatus("available");
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(petPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);
    }
    @Title("Verify it the Pet was added successfully")
    @Test
    public void test02(){
        String p1 = "findAll{it.name =='";
        String p2 = "'}.get(0)";

        HashMap<String,Object>petMap =SerenityRest.given().log().all()
                .when()
                .get(EndPoints.Get_all_Pet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(petMap,hasValue(name));
        petId = (int)petMap.get("id");
    }
    @Title("Update the pet details and verify the updated details")
    @Test
    public void test03(){
        name = name + "_updated";
        List<String>name = new ArrayList<>();
        name.add("Puppy");
        name.add("Maxi");

        HashMap<String, Object> pet = new HashMap<>();
        pet.put("id", "9223372036854309000");
        pet.put("name", "abc");

        List<HashMap<String, Object>> petList = new ArrayList<>();
        petList.add(pet);

        PetPojo petPojo = new PetPojo();
        petPojo.setId(id);
        petPojo.setCategory(pet);
        petPojo.setName("doggie");
        petPojo.setPhotoUrls(name);
        petPojo.setTags(petList);
        petPojo.setStatus("available");
        SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .pathParam("petId",petId)
                .body(petPojo)
                .when()
                .put(EndPoints.Update_Pet_By_ID)
                .then().log().all().statusCode(200);
        String p1 = "findAll{it.name =='";
        String p2 = "'}.get(0)";

        HashMap<String,Object>petMap =SerenityRest.given().log().all()
                .when()
                .get(EndPoints.Get_all_Pet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(petMap,hasValue(name));

    }
    @Title("Delete the pet and verify if the pet is deleted")
    @Test
    public void test04(){
        SerenityRest.given().log().all()
                .pathParam("petId",petId)
                .when()
                .delete(EndPoints.Delete_Pet_Bu_ID)
                .then().statusCode(204);
        SerenityRest.given().log().all()
                .pathParam("petId",petId)
                .when()
                .get(EndPoints.Get_Single_Pet_By_ID)
                .then().statusCode(404);
        }

    }




