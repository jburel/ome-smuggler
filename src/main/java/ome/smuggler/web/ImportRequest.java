package ome.smuggler.web;

/**
 * Web clients use instances of this class to request an import.
 * This is just a data transfer object whose sole purpose is to facilitate the
 * transfer of information from the client to the server.
 */
public class ImportRequest {
    
    /**
     * The email address to send an outcome notification to.
     * This should normally be the email address of the user who requested the
     * import so that on completion the system can send them an email indicating
     * success or failure to import the data.
     */
    public String experimenterEmail;
    
    /**
     * The URI of the file or directory containing the data to import.
     * This is a mandatory field and must be a "file:" URI pointing to the file 
     * on the machine where the data to import sits. 
     * If the host part is missing, the path is assumed to be that of a file on 
     * the same machine where this server runs. 
     * For now this is the only supported option, but, going forward, we will 
     * implement the means to resolve file locations across a network so that an 
     * import server may pull image data from multiple acquisition workstations.
     * @see https://en.wikipedia.org/wiki/File_URI_scheme
     */
    public String targetUri;
    
    /**
     * The hostname or IP address of the machine running the OMERO server in
     * which the data will be imported. This is a mandatory field.
     */
    public String omeroHost;
    
    /**
     * The port of the OMERO server. This is a mandatory field and must parse
     * to a non-negative integer.
     */
    public String omeroPort;
    
    /**
     * Mandatory field to identify the OMERO session to use for the import.
     * The client must acquire a session before submitting an import request as
     * this server will never accept user names and passwords to run an import.
     * The client can close the session as soon as this import request is 
     * submitted to this web server.
     */
    public String sessionKey;
    
    /**
     * Optional field for the image or plate name to use.
     */
    public String name;
    
    /**
     * Optional field for the image or plate description to use.
     */
    public String description;
    
    /**
     * If the image is to be imported into an existing OMERO dataset, then this
     * field specifies its ID; leave out otherwise. If this field is specified,
     * then it must parse to a non-negative long. Also, this field and the 
     * {@link #screenId} are mutually exclusive, it is only allowed to specify
     * one.
     * @see #screenId
     */
    public String datasetId;
    
    /**
     * If the image is to be imported into an existing OMERO screen, then this
     * field specifies its ID; leave out otherwise. If this field is specified,
     * then it must parse to a non-negative long. Also, this field and the 
     * {@link #datasetId} are mutually exclusive, it is only allowed to specify
     * one.
     * @see #datasetId
     */
    public String screenId;

    /**
     * Any optional image annotations to attach to the image being imported.
     * Each entry is an array-stored pair {@code [namespace, text]} with both
     * strings having length at least one. Any entry not conforming to this
     * format will cause a validation error.
     */
    public String[][] textAnnotations;
    
    /**
     * Any optional annotation ID's to attach to the image being imported.
     * If given, this array must contain a list of values that parse to a 
     * non-negative long in order to pass validation. 
     */
    public String[] annotationIds;
    
    public ImportRequest() { }

}
