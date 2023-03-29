Feature: City - Get -Cases of exceptions
  Background:
    * url AppUrl
    * def city_response_error = read('./response/get_city_response_error.json')

  Scenario: Obtain an exception to one particular code
    Given path 'city/BID'
    When method GET
    Then status 404
    And match response == city_response_error