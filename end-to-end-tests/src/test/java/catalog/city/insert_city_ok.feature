Feature: City - Insert -Success cases
  Background:
    * url AppUrl
    * def random_string =
    """
      function(s){
        var text = "";
        var pattern = "ABCDEFGHIJKLMNOPQRSYWXZ123456789";
        for (var i=0; i<s; i++)
          text += pattern.charAt(Math.floor(Math.random() * pattern.length()));
          return text;
      }
    """

    * def city_request_ok = read('./request/insert_city_request_ok.json')
    * city_request_ok.code = random_string(3)
    * def city_response_ok = read('./response/insert_city_response_ok.json')

  Scenario: Insert the information about one particular city
    Given path 'city'
    And request city_request_ok
    When method POST
    Then status 201
    And match response == city_response_ok