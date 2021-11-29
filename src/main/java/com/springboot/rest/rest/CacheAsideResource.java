package com.springboot.rest.rest;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.rest.errors.BadRequestAlertException;
import com.springboot.rest.usecase.CacheAside.CreateCacheAside;
import com.springboot.rest.usecase.CacheAside.DeleteCacheAside;
import com.springboot.rest.usecase.CacheAside.ReadCacheAside;
import com.springboot.rest.usecase.CacheAside.UpdateCacheAside;

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
 * REST controller for managing {@link CacheAsideEntity}.
 */
// @Component
@RestController
@RequestMapping("/api")
public class CacheAsideResource {

    private final Logger log = LoggerFactory.getLogger(CacheAsideResource.class);

    private static final String ENTITY_NAME = "cacheAside";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    //private final SampleEntityServicePort sampleEntityServicePort;

    private final CreateCacheAside createSampleEntity;
    private final ReadCacheAside readSampleEntity;
    private final UpdateCacheAside updateSampleEntity;
    private final DeleteCacheAside deleteSampleEntity;
    

    public CacheAsideResource(CreateCacheAside createSampleEntity, ReadCacheAside readSampleEntity, UpdateCacheAside updateSampleEntity, DeleteCacheAside deleteSampleEntity) {
        //this.sampleEntityServicePort = sampleEntityServicePort;
        this.createSampleEntity=createSampleEntity;
        this.readSampleEntity=readSampleEntity;
        this.updateSampleEntity=updateSampleEntity;
        this.deleteSampleEntity=deleteSampleEntity;
    }
    
    /*
    // testing with mapper: initialize Mapper Class
	private final SampleEntityMapper sampleEntityMapper;

    public SampleEntityResource(SampleEntityServicePort sampleEntityServicePort, SampleEntityMapper sampleEntityMapper) {
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
    @PostMapping("/cacheAside-entity")
    @Operation(summary = "/cacheAside-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CacheAsideDTO> createSampleEntity(@RequestBody CacheAsideDTO sampleEntityDTO) throws URISyntaxException {
        log.debug("REST request to save cacheAside : {}", sampleEntityDTO);
        if (sampleEntityDTO.getId() != null) {
            throw new BadRequestAlertException("cacheAside new a cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CacheAsideEntity sampleEntity = createSampleEntity.save(sampleEntityDTO);
        CacheAsideDTO sampleEntityDTOResponse = new CacheAsideDTO(sampleEntity);
        
        return ResponseEntity
                .created(new URI("/api/cacheAside-entity/" + sampleEntity.getId()))
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
    @PutMapping("/cacheAside-entity/{id}")
    @Operation(summary = "/cacheAside-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CacheAsideDTO> updateSampleEntity(@PathVariable(value = "id", required = false) final Long id, @RequestBody CacheAsideDTO sampleEntityDTO)
            throws URISyntaxException {
        log.debug("REST request to update cacheAside : {}, {}", id, sampleEntityDTO);

        CacheAsideEntity sampleEntity = updateSampleEntity.update(id, sampleEntityDTO);
        CacheAsideDTO sampleEntityDTOResponse = new CacheAsideDTO(sampleEntity);
        
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
    @PatchMapping(value = "/cacheAside-entity/{id}", consumes = "application/merge-patch+json")
    @Operation(summary = "/cacheAside-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CacheAsideEntity> partialUpdateSampleEntity(@PathVariable(value = "id", required = false) final Long id, @RequestBody CacheAsideDTO sampleEntityDTO)
            throws URISyntaxException {
        log.debug("REST request to partial update cacheAside partially : {}, {}", id, sampleEntityDTO);

        Optional<CacheAsideEntity> result = updateSampleEntity.patch(id, sampleEntityDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sampleEntityDTO.getId().toString())
        );

    }

    /**
     * {@code GET  /sample-entity} : get all the aS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aS in body.
     */
    @GetMapping("/cacheAside-entity")
    @Operation(summary = "/cacheAside-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public List<CacheAsideDTO> getAllSampleEntity() {
        log.debug("REST request to get all cacheAside");

        List<CacheAsideDTO> sampleEntityDTOS = readSampleEntity.findAll()
                .stream()
                .map(cacheAside -> new CacheAsideDTO(cacheAside))
                .collect(Collectors.toList());

        return sampleEntityDTOS;
    }

    /**
     * {@code GET  /sample-entity/:id} : get the "id" a.
     *
     * @param id the id of the a to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the a, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cacheAside-entity/{id}")
    @Operation(summary = "/cacheAside-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity getcacheAside(@PathVariable Long id) {
        log.debug("REST request to get A : {}", id);

        Optional<CacheAsideEntity> a = readSampleEntity.findById(id);

        return ResponseUtil.wrapOrNotFound(a);
    }

    /**
     * {@code DELETE  /sample-entity/:id} : delete the "id" a.
     *
     * @param id the id of the a to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cacheAside-entity/{id}")
    @Operation(summary = "/cacheAside-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Boolean> deleteA(@PathVariable Long id) {
        log.debug("REST request to delete cacheAside : {}", id);
        deleteSampleEntity.deleteById(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}