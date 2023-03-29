package com.twa.flights.api.catalog.controller.documentation;

import com.twa.flights.api.catalog.dto.ErrorDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.twa.flights.api.catalog.dto.BaseDTO;
import com.twa.flights.api.catalog.dto.CityDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "City", description = "Operations about the city")
@RequestMapping("/city")
public interface CityResource {

    @Operation(description = "Get entity by iso code", responses = {
            @ApiResponse(responseCode = "200", description = "The city information", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseDTO.class))),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") }, tags = {
                    "Catalog" }, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "code", description = "The code of the city (e.g. BUE, MIA, SCL, NYC, PAR, LON)", required = true, example = "BUE") })
    @GetMapping(value = "/{code}")
    ResponseEntity<CityDTO> get(@PathVariable String code);

    @Operation(description = "Modify entity by iso code", responses = {
            @ApiResponse(responseCode = "200", description = "The city information", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseDTO.class))),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") }, tags = {
                    "Catalog" }, parameters = {
                            @Parameter(in = ParameterIn.PATH, name = "code", description = "The code of the city (e.g. BUE, MIA, SCL, NYC, PAR, LON)", required = true, example = "BUE") })
    @PutMapping(value = "/{code}")
    ResponseEntity<CityDTO> update(@PathVariable String code, @RequestBody CityDTO city);

    @Operation(description = "Insert entity", responses = {
            @ApiResponse(responseCode = "201", description = "The city information", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") }, tags = { "Catalog" })
    @PostMapping
    ResponseEntity<CityDTO> insert(@RequestBody CityDTO city);

    @Operation(description = "Insert a list of entities", responses = {
            @ApiResponse(responseCode = "201", description = "The city information", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error") }, tags = { "Catalog" })
    @PostMapping(value = "/bulk")
    ResponseEntity<List<CityDTO>> insert(@RequestBody List<CityDTO> cities);

    @Operation(description = "Delete one city", responses = {
            @ApiResponse(responseCode = "200", description = "Delete one city", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDTO.class))) }, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "code", description = "Code of the city to delete", example = "BUE") })
    @DeleteMapping("/{code}")
    ResponseEntity<Void> delete(@PathVariable String code);

    @Operation(description = "Delete all the cities", responses = {
            @ApiResponse(responseCode = "200", description = "Delete all cities", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDTO.class))) })
    @DeleteMapping(value = "/bulk")
    ResponseEntity<Void> delete(@RequestBody List<String> codes);
}
