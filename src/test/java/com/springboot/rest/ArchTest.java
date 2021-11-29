package com.springboot.rest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.ArchRule;

import io.jsonwebtoken.lang.Classes;

import org.junit.jupiter.api.Test;

@AnalyzeClasses(packages = "com.springboot.rest")
class ArchTest {
	
	

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.springboot.rest");

        noClasses()
            .that()
            .resideInAnyPackage("com.springboot.rest.service..")
            .or()
            .resideInAnyPackage("com.springboot.rest.adaptor..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.springboot.rest.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
