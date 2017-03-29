package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Doctor;

import com.mycompany.myapp.domain.Doctor;
import com.mycompany.myapp.repository.DoctorRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.mycompany.myapp.service.dto.ChartData;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * REST controller for managing Doctor.
 */
@RestController
@RequestMapping("/api")
public class DoctorResource {

    private final Logger log = LoggerFactory.getLogger(DoctorResource.class);

    private static final String ENTITY_NAME = "doctor";

    private final DoctorRepository doctorRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public DoctorResource(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * POST  /doctors : Create a new doctor.
     *
     * @param doctor the doctor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doctor, or with status 400 (Bad Request) if the doctor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doctors")
    @Timed
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) throws URISyntaxException {
        log.debug("REST request to save Doctor : {}", doctor);
        if (doctor.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new doctor cannot already have an ID")).body(null);
        }
        Doctor result = doctorRepository.save(doctor);
        return ResponseEntity.created(new URI("/api/doctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doctors : Updates an existing doctor.
     *
     * @param doctor the doctor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doctor,
     * or with status 400 (Bad Request) if the doctor is not valid,
     * or with status 500 (Internal Server Error) if the doctor couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doctors")
    @Timed
    public ResponseEntity<Doctor> updateDoctor(@RequestBody Doctor doctor) throws URISyntaxException {
        log.debug("REST request to update Doctor : {}", doctor);
        if (doctor.getId() == null) {
            return createDoctor(doctor);
        }
        Doctor result = doctorRepository.save(doctor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doctor.getId().toString()))
            .body(result);
    }


    @GetMapping("/doctors/all-doctor.pdf")
    public void printAllUsers(HttpServletResponse resp) throws ServletException,
        IOException, URISyntaxException, DRException {
        final List<Doctor> page = doctorRepository.findAll();
     //   Doctor doc = doctorRepository.findOne();

        JRBeanCollectionDataSource dataSource = new
            JRBeanCollectionDataSource(page);
        resp.setContentType("application/pdf");
        OutputStream out = resp.getOutputStream();
        StyleBuilder boldStyle = DynamicReports.stl.style().bold();
        StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)

            .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle =
            DynamicReports.stl.style(boldCenteredStyle)

                .setBorder(DynamicReports.stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
        DynamicReports.report()
            .setColumnTitleStyle(columnTitleStyle)
            .highlightDetailEvenRows()
            .columns(
                Columns.column("Nom", "nometprenom", DataTypes.stringType()),
                Columns.column("Spécialité", "specialite", DataTypes.stringType()),
                Columns.column("Service Medical", "servicemedi", DataTypes.stringType()),
                Columns.column("Login", "login",
                    DataTypes.stringType()))
            .title(
                Components.text("Liste des Medecins")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER))
            .pageFooter(Components.pageXofY())
            .setDataSource(dataSource)
            .toPdf(out);
    }


    /**
     * GET  /doctors : get all the doctors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doctors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/doctors")
    @Timed
    public ResponseEntity<List<Doctor>> getAllDoctors(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Doctors");
        Page<Doctor> page = doctorRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doctors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /doctors/:id : get the "id" doctor.
     *
     * @param id the id of the doctor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doctor, or with status 404 (Not Found)
     */
    @GetMapping("/doctors/{id}")
    @Timed
    public ResponseEntity<Doctor> getDoctor(@PathVariable String id) {
        log.debug("REST request to get Doctor : {}", id);
        Doctor doctor = doctorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doctor));
    }

    /**
     * DELETE  /doctors/:id : delete the "id" doctor.
     *
     * @param id the id of the doctor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doctors/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoctor(@PathVariable String id) {
        log.debug("REST request to delete Doctor : {}", id);
        doctorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    @Secured({AuthoritiesConstants.ADMIN,})
    @GetMapping("/doctors/{id}/doctor.pdf")
    public void printOne(@PathVariable String id ,HttpServletResponse resp) throws ServletException, IOException , URISyntaxException, DRException {
        final Doctor medecin = doctorRepository.findOne(id);

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(medecin));
        printMedecin(resp, dataSource);
    }





    private void printMedecin(HttpServletResponse resp, JRBeanCollectionDataSource dataSource)
        throws IOException, DRException {
        resp.setContentType("application/pdf");
        OutputStream out = resp.getOutputStream();
        StyleBuilder boldStyle         = DynamicReports.stl.style().bold();
        StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
            .setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle)
            .setBorder(DynamicReports.stl.pen1Point())
            .setBackgroundColor(Color.LIGHT_GRAY);
        DynamicReports.report()
            .setColumnTitleStyle(columnTitleStyle)
            .highlightDetailEvenRows()
            .columns(
               Columns.column("Nom", "nometprenom", DataTypes.stringType()),
               Columns.column("Spécialité", "specialite", DataTypes.stringType()),
              Columns.column("Service Medical", "servicemedi", DataTypes.stringType()),
             //  Columns.column("Photo", "photo", DataTypes.byteType())
             Columns.column("Login", "login",DataTypes.stringType()))
            .title(
                Components.text("Liste des Medecins")
                    .setHorizontalAlignment(HorizontalAlignment.CENTER))
            .pageFooter(Components.pageXofY())
            .setDataSource(dataSource)
            .toPdf(out);
        out.close();
    }
    @GetMapping("/doctors/chartdata")
    public List<ChartData> getMedecinChartData(){
        Aggregation agg = newAggregation(
            match(Criteria.where("_id").ne(null)),
            group("specialite").count().as("value"),
            project("value").and("title").previousOperation()
        );

        AggregationResults<ChartData> groupResults
            = mongoTemplate.aggregate(agg, Doctor.class, ChartData.class);
        return groupResults.getMappedResults();


    }
}
