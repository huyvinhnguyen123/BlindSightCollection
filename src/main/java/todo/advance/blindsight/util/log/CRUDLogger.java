package todo.advance.blindsight.util.log;

public class CRUDLogger {
    // Request Logger
    public static final String REQUEST_CREATE(String value) {
        return "Request creating " + value + "...";
    }

    public static final String REQUEST_UPDATE(String value) {
        return "Request updating " + value + "...";
    }

    public static final String REQUEST_DELETE(String value) {
        return "Request deleting " + value + "...";
    }

    public static final String REQUEST_SELECT(String value) {
        return "Request selecting " + value + "...";
    }

    public static final String REQUEST_SEARCH(String value) {
        return "Request searching " + value + "...";
    }

    public static final String REQUEST_EXPORT_FILE_EXCEL(String value) {
        return "Request export " + value + " to excel file...";
    }

    public static final String REQUEST_AUTHENTICATE = "Request login authenticating...";
//=====================================================================================================================================
    // Auth Logger
    public static final String LOGIN_AUTHENTICATE_SUCCESS = "Login authenticate SUCCESS!";
    public static final String LOGIN_AUTHENTICATE_FAIL = "Login authenticate FAIL!";
    public static final String GENERATE_TOKEN_SUCCESS = "Generate token SUCCESS!";
    public static final String GENERATE_TOKEN_FAIL = "Generate token FAIL!";
//=====================================================================================================================================
    // CRUD Logger
    public static final String CREATE_SUCCESS(String value) {
        return "Create " + value + " SUCCESS!";
    }
    
    public static final String CREATE_FAIL(String value) {
        return "Create " + value + " FAIL!";
    }

    public static final String UPDATE_SUCCESS(String value) {
        return "Update " + value + " SUCCESS!";
    }

    public static final String UPDATE_FAIL(String value) {
        return "Update " + value + " FAIL!";
    }

    public static final String DELETE_SUCCESS(String value) {
        return "Delete " + value + " SUCCESS!";
    }

    public static final String DELETE_FAIL(String value) {
        return "Delete " + value + " FAIL!";
    }

    public static final String SELECT_ONE_SUCCESS(String value) {
        return "Select " + value + " SUCCESS!";
    }

    public static final String SELECT_ONE_FAIL(String value) {
        return "Select " + value + " FAIL!";
    }

    public static final String SELECT_ALL_SUCCESS(String value) {
        return "Select all " + value + " SUCCESS!";
    }

    public static final String SELECT_ALL_FAIL(String value) {
        return "Select all " + value + " FAIL!";
    }

    public static final String SEARCH_ONE_SUCCESS(String value) {
        return "Search " + value + " SUCCESS!";
    }

    public static final String SEARCH_ONE_FAIL(String value) {
        return "Search " + value + " FAIL!";
    }

    public static final String SEARCH_ALL_SUCCESS(String value) {
        return "Search all " + value + " SUCCESS!";
    }

    public static final String SEARCH_ALL_FAIL(String value) {
        return "Search all " + value + " FAIL!";
    }

    // Upload Logger 
    public static final String UPLOAD_SUCCESS(String value) {
        return "Upload " + value + " SUCCESS!";
    } 
    public static final String UPLOAD_FAIL(String value) {
        return "Upload " + value + " FAIL!";
    }

    // error Logger
    public static final String NOT_FOUND_ERR = "Error 404 NOT FOUND!";
    public static final String AUTHENTICATION_ERR = "Error 403 Authentication!";
}
