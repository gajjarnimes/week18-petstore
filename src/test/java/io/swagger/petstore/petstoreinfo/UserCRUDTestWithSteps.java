package io.swagger.petstore.petstoreinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.model.UserPojo;
import io.swagger.petstore.testbase.TestBase;
import io.swagger.petstore.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class UserCRUDTestWithSteps extends TestBase {

    static String userName = "PrimeStudent" + TestUtils.getRandomValue();

    static String firstName = "Prem10" + TestUtils.getRandomValue();

    static String lastName ="Pujara" + TestUtils.getRandomValue();

    static String email= "xyz@yahoo.com"+TestUtils.getRandomValue();

    static String password = "Prem@999"+TestUtils.getRandomValue();

    static String phone = "0793456789" + TestUtils.getRandomValue();

    static int userStatus;

    static int userId;

    @Steps
    UserSteps userSteps;

    @Title("It will Create New User")

    @Test
    public void test01(){
        ValidatableResponse response = userSteps.createUser(userName, firstName,lastName,email,password,phone, userStatus);
        response.log().all().statusCode(201);

        UserPojo userPojo = new UserPojo();
        userPojo.setUserName(userName);
        userPojo.setFirstName(firstName);
        userPojo.setLastName(lastName);
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        userPojo.setPhone(phone);
        userPojo.setUserStatus(userStatus);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);

    }
    @Title("Verify it the User was added successfully")
    @Test
    public void test02(){
        HashMap<String,Object>userMap = userSteps.getUserdetailsByUserName(userName);
        Assert.assertThat(userMap,hasValue(userName));
        userId =(int)userMap.get("id");
        System.out.println("User Id is :"+userId);

    }

    @Title("Update the user details and verify the updated information")
    @Test
    public void test03(){
        firstName = firstName +"_updated";
        lastName = lastName +"_updated";
        List<String> firstNameList = new ArrayList<>();
        List<String> lastNameList = new ArrayList<>();
        firstNameList.add("Mohit");
        lastNameList.add("Kansara");

        userSteps.updateUser(userId,userName,firstName,lastName,email,password,phone,userStatus)
                .log().all().statusCode(200);
        HashMap<String,Object>userMap = userSteps.getUserdetailsByUserName(userName);
        Assert.assertThat(userMap,hasValue(userName));
    }
    @Title("Delete the User and verify the user is deleted")
    @Test
    public void test04(){
        userSteps.getUserByUserName(userName).statusCode(204);
        userSteps.deleteByUserName(userName).statusCode(404);
    }


}
