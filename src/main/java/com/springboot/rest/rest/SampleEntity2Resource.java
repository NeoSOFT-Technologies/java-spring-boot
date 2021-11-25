package com.springboot.rest.rest;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.rest.errors.BadRequestAlertException;
import com.springboot.rest.usecase.sampleentity2.CreateSampleEntity2;
import com.springboot.rest.usecase.sampleentity2.DeleteSampleEntity2;
import com.springboot.rest.usecase.sampleentity2.ReadSampleEntity2;
import com.springboot.rest.usecase.sampleentity2.UpdateSampleEntity2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link SampleEntity2}.
 */
// @Component
@RestController
@RequestMapping("/api")
public class SampleEntity2Resource {

    private final Logger log = LoggerFactory.getLogger(SampleEntity2Resource.class);

    private static final String ENTITY_NAME = "a";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    //private final SampleEntity2ServicePort sampleEntityServicePort;

    private final CreateSampleEntity2 createSampleEntity2;
    private final ReadSampleEntity2 readSampleEntity2;
    private final UpdateSampleEntity2 updateSampleEntity2;
    private final DeleteSampleEntity2 deleteSampleEntity2;
    

    public SampleEntity2Resource(CreateSampleEntity2 createSampleEntity2, ReadSampleEntity2 readSampleEntity2, UpdateSampleEntity2 updateSampleEntity2, DeleteSampleEntity2 deleteSampleEntity2) {
        //this.sampleEntityServicePort = sampleEntityServicePort;
        this.createSampleEntity2=createSampleEntity2;
        this.readSampleEntity2=readSampleEntity2;
        this.updateSampleEntity2=updateSampleEntity2;
        this.deleteSampleEntity2=deleteSampleEntity2;
    }
    
    /*
    // testing with mapper: initialize Mapper Class
	private final SampleEntity2Mapper sampleEntityMapper;

    public SampleEntity2Resource(SampleEntity2ServicePort sampleEntityServicePort, SampleEntity2Mapper sampleEntityMapper) {
        this.sampleEntityServicePort = sampleEntityServicePort;
        this.sampleEntityMapper = sampleEntityMapper;
    }
    */

    /**
     * {@code POST  /sample-entity} : Create a new a.
     *
     * @parama the a to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new a, or with status {@code 400 (Bad Request)} if the a has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sample-entity")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SampleEntity2DTO> createSampleEntity(@RequestBody SampleEntity2DTO sampleEntity2DTO) throws URISyntaxException {
        log.debug("REST request to save A : {}", sampleEntity2DTO);
        if (sampleEntity2DTO.getId() != null) {
            throw new BadRequestAlertException("A new a cannot already have an ID", ENTITY_NAME, "idexists");
        }

        SampleEntity2 sampleEntity2 = createSampleEntity2.save(sampleEntity2DTO);
        SampleEntity2DTO sampleEntityDTOResponse = new SampleEntity2DTO(sampleEntity2);

        
        return ResponseEntity
                .created(new URI("/api/sample-entity/" + sampleEntity2.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, sampleEntityDTOResponse.getId().toString()))
                .body(sampleEntityDTOResponse);

    }

    /**
     * {@code PUT  /sample-entity/:id} : Updates an existing a.
     *
     * @param id the id of the a to save.
     * @parama the a to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated a,
     * or with status {@code 400 (Bad Request)} if the a is not valid,
     * or with status {@code 500 (Internal Server Error)} if the a couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sample-entity/{id}")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SampleEntity2DTO> updateSampleEntity(@PathVariable(value = "id", required = false) final Long id, @RequestBody SampleEntity2DTO sampleEntity2DTO)
            throws URISyntaxException {
        log.debug("REST request to update A : {}, {}", id, sampleEntity2DTO);

        SampleEntity2 sampleEntity2 = updateSampleEntity2.update(id, sampleEntity2DTO);
        SampleEntity2DTO sampleEntityDTOResponse = new SampleEntity2DTO(sampleEntity2);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sampleEntityDTOResponse.getId().toString()))
                .body(sampleEntityDTOResponse);
    }

    /**
     * {@code PATCH  /sample-entity/:id} : Partial updates given fields of an existing a, field will ignore if it is null
     *
     * @parama the a to update.
     * @param id the id of the a to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated a,
     * or with status {@code 400 (Bad Request)} if the a is not valid,
     * or with status {@code 404 (Not Found)} if the a is not found,
     * or with status {@code 500 (Internal Server Error)} if the a couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sample-entity/{id}", consumes = "application/merge-patch+json")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SampleEntity2> partialUpdateSampleEntity(@PathVariable(value = "id", required = false) final Long id, @RequestBody SampleEntity2DTO sampleEntity2DTO)
            throws URISyntaxException {
        log.debug("REST request to partial update A partially : {}, {}", id, sampleEntity2DTO);

        Optional<SampleEntity2> result = updateSampleEntity2.patch(id, sampleEntity2DTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sampleEntity2DTO.getId().toString())
        );

    }

    /**
     * {@code GET  /sample-entity} : get all the aS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aS in body.
     */
    @GetMapping("/sample-entity")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public List<SampleEntity2DTO> getAllSampleEntity() {
        log.debug("REST request to get all SampleEntity2");

        List<SampleEntity2DTO> sampleEntityDTOS = readSampleEntity2.findAll()
                .stream()
                .map(a -> new SampleEntity2DTO(a))
                .collect(Collectors.toList());

        return sampleEntityDTOS;
    }

    /**
     * {@code GET  /sample-entity/:id} : get the "id" a.
     *
     * @param id the id of the a to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the a, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sample-entity/{id}")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public Optional<SampleEntity2> getA(@PathVariable Long id) {
        log.debug("REST request to get A : {}", id);

        Optional<SampleEntity2> a = readSampleEntity2.findById(id);

        return a;
    }

    /**
     * {@code DELETE  /sample-entity/:id} : delete the "id" a.
     *
     * @param id the id of the a to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sample-entity/{id}")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Boolean> deleteA(@PathVariable Long id) {
        log.debug("REST request to delete A : {}", id);
        deleteSampleEntity2.deleteById(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}