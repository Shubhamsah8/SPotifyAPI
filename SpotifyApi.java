package Automation;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static io.restassured.RestAssured.given;

public class SpotifyApi {
    String token = "BQABek40uOs64RA68Um7gZA1aQ2jye_CMspoMUSsLVwIzAYC6_4QXzSzsM4_7n7dWLTb3YwsWi5rEwkiiy_DuY0Ti0Al95kYXCASJcN2KbT1ZC521QDArqR7OB6rltSfvSqmEHt-PZzpernj1Hp4fqMavgBJvbgFysvlJAywr4JFzCbEH0iP1NCJaeChPmYyBmWnLYezjHRbwUhBppwqb9_Hb-T9NI8PyCiOWmxk4ySaSGWFXvWb5NVlKkbJJv3mZHSOTgwf7ECQur9Vmg3IQQoVITWwmTXPt6Wy9DBwf9dQ8qWWSYw5mw37EfQXmH7ZgbM";

    @Test
    public void users() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);

    }

    @Test
    public void createplaylist() {
        Response res = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"name\": \"New Playlist\",\n" +
                        "    \"description\": \"New playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()

                .post("https://api.spotify.com/v1/users/31zg7mujxxejflwaxmquvj7bneea/playlists");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void AddItemtoplaylist()
    {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:6M6ZNRplLNspFEn6Ab3mOv\"\n" +
                        "    ],\n" +
                        "    \"position\": 0\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/playlists/2l25hm3ssih8ChOtFIyIGi/tracks");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void RemovePlaylistItems() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"tracks\": [\n" +
                        "        {\n" +
                        "            \"uri\": \"spotify:track:6M6ZNRplLNspFEn6Ab3mOv\"\n" +
                        "        }\n" +
                        "    ],\n" +
                        "    \"snapshot_id\": \"AAAABdUcmnL57BNhw9L2gX8Gcf3kv3BU\"\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/2l25hm3ssih8ChOtFIyIGi/tracks");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetCurrentUserPlaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);   //Spotify doesn't allowing this feature
    }

    @Test
    public void GetUserPlaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/users/31zg7mujxxejflwaxmquvj7bneea/playlists");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetFeaturedPlaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/featured-playlists");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetCategoryPlaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/browse/categories/dinner/playlists");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetPlaylistCoverImage() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3qbRpD6ZO2L5MauExVcg7J/images");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void AddCustomPlaylistImage() throws IOException {

        byte[] imageBytes = Files.readAllBytes(Paths.get("C:\\images.jpg"));;
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type","image/jpeg")
                .body(base64Image)
                .when()
                .put("https://api.spotify.com/v1/playlists/3qbRpD6ZO2L5MauExVcg7J/images");

        res.prettyPrint();
        res.then().assertThat().statusCode(202);   //Spotify doesn't allowing this feature
    }

    @Test
    public void GetUserProfile() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetUsertopItems() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/top/artists");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }


    @Test
    public void Putfollowplaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/37i9dQZF1DX0XUfTFmNBRM/followers");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void deleteUnfollowplaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("https://api.spotify.com/v1/playlists/37i9dQZF1DX0XUfTFmNBRM/followers");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetFollowedArtist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/following?type=artist");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void PutFollowArtistForUsers() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2CIMQHirSU0MQqyYHq0eOx\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/following?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx");

        res.prettyPrint();
        res.then().assertThat().statusCode(204);
    }

    @Test
    public void PutUnFollowArtistForUsers() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"2CIMQHirSU0MQqyYHq0eOx\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/me/following?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx");

        res.prettyPrint();
        res.then().assertThat().statusCode(204);
    }

    @Test
    public void GetCheckIfUserFollowArtistForUsers() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/me/following/contains?type=artist&ids=2CIMQHirSU0MQqyYHq0eOx");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

    @Test
    public void GetCheckIfUserFollowPlaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n/followers/contains?ids=jmperezperez");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }
    @Test
    public void UpdatePlaylist() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body("")
                .when()
                .get("https://api.spotify.com/v1/playlists/2l25hm3ssih8ChOtFIyIGi/tracks");

        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }

}
