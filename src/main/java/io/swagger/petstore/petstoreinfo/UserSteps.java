package io.swagger.petstore.petstoreinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.constants.EndPoints;
import io.swagger.petstore.model.UserPojo;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class UserSteps {
    @Step("Creating User with userName:{0},firstName:{1},lastName:{2},email:{3},password:{4},phone:{5},userStatus:{6}")

    public ValidatableResponse createUser(String userName,String firstName,String lastName,String email,String password,String phone,int userStatus){

        UserPojo userPojo = new UserPojo();
        userPojo.setUserName(userName);
        userPojo.setFirstName(firstName);
        userPojo.setLastName(lastName);
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        userPojo.setPhone(phone);
        userPojo.setUserStatus(userStatus);
        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(userPojo)
                .when()
                .post()
                .then();
    }@Step("Getting User details By username:{0}")

    public HashMap<String,Object> getUserdetailsByUserName(String userName){
        String p1 = "findAll{it.username == '";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.Get_all_user)
                .then()
                .statusCode(200)
                .extract()
                .path(p1+userName+p2);
    }
    @Step("Updating User detail with ,userid:{0},userName:{1},firstName:{2},lastName:{3},email:{4},password:{5},phone:{6},userStatus:{7}")

    public ValidatableResponse updateUser(int userId,String userName,String firstName,String lastName,String email,String password,String phone,int userStatus){


        UserPojo userPojo = new UserPojo();
        userPojo.setUserName(userName);
        userPojo.setFirstName(firstName);
        userPojo.setLastName(lastName);
        userPojo.setEmail(email);
        userPojo.setPassword(password);
        userPojo.setPhone(phone);
        userPojo.setUserStatus(userStatus);
        return SerenityRest.given().log().all()
                .header("Content-Type","application/json")
                .pathParam("userName",userName)
                .body(userPojo)
                .when()
                .put(EndPoints.Update_Single_User_By_Name)
                .then();
    }
    @Step("Deleting user details with username:{0}")

    public ValidatableResponse deleteByUserName(String userName){
        return SerenityRest.given().log().all()
                .pathParam("userName",userName)
                .when()
                .get(EndPoints.Delete_User_By_Name)
                .then();

    }
    @Step("Getting user details with username:{0}")

    public ValidatableResponse getUserByUserName(String userName){
        return SerenityRest.given().log().all()
                .pathParam("userName",userName)
                .when()
                .get(EndPoints.Get_Single_User_By_Name)
                .then();
    }
}
