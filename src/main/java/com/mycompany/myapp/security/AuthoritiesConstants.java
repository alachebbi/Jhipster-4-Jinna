package com.mycompany.myapp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String MANAGER = "ROLE_MANAGER";

    public static final String MEDECIN = "ROLE_MEDECIN";

    public static final String CHEF_SERVICE = "ROLE_CHEF_SERVICE";

    public static final String SERVICE_FINANCIER = "ROLE_SERVICE_FINANCIER";

    public static final String INFIRMIER = "ROLE_INFIRMIER";

    public static final String GESTIONNAIREDEMATERIEL = "ROLE_GESTIONNAIREDEMATERIEL";

    public static final String PHARMACIEN = "ROLE_PHARMACIEN";



    private AuthoritiesConstants() {
    }
}
