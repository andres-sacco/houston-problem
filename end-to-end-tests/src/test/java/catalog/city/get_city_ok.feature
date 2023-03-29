Feature: City - Get -Success cases
  Background:
    * url AppUrl
    * def city_response_ok = read('./response/get_city_response_ok.json')

  Scenario: Obtain the information about one particular city
    Given path 'city/BUE'
    When method GET
    Then status 200
    And match response == city_response_ok