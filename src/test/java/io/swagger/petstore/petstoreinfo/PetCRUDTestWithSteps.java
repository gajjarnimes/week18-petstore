package io.swagger.petstore.petstoreinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.model.PetPojo;
import io.swagger.petstore.testbase.TestBase;
import io.swagger.petstore.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)

public class PetCRUDTestWithSteps extends TestBase {
    static int id = Integer.parseInt(99 + TestUtils.getRandomValue());

    static HashMap<String,Object> category;

    static String name = "Doggie" + TestUtils.getRandomValue();

    static List<String> photoUrls;

    static List<HashMap<String,Object>> tags;

    static String status = TestUtils.getRandomValue()+"available";

    static int petId;
    @Steps
    PetSteps petSteps;

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
       ValidatableResponse response = petSteps.getPetById(id).statusCode(200);
      String name = response.extract().path("name");
      Assert.assertTrue(name.matches(name));
    }
    @Title("Update the pet details and verify the updated details")
    @Test
    public void test03() {
        name = name + "_updated";
        List<String> name1 = new ArrayList<>();
        name1.add("Puppy");
        name1.add("Maxi");

       ValidatableResponse response =  petSteps.updatePet(petId,category, name1.toString(),photoUrls,tags,status);
       HashMap<String,?>update = response.extract().path("");
        Assert.assertThat(update,hasValue(name));


        }
    @Title("Delete the pet and verify if the pet is deleted")
    @Test
    public void test04(){
        petSteps.getPetById(petId).statusCode(204);
        petSteps.deletePet(petId).statusCode(404);
    }


    }
