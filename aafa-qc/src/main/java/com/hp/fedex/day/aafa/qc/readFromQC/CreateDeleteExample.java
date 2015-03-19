package com.hp.fedex.day.aafa.qc.readFromQC;

/**
 * Created by chengya on 2015/3/18.
 */
import java.util.HashMap;
import java.util.Map;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Constants;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Entity;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.EntityMarshallingUtils;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Response;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.RestConnector;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Entity.Fields.Field;
/**
 *
 * This example shows several ways to create an entity; and shows how to remove entities.
 *
 */
public class CreateDeleteExample {
    public static void main(String[] args) throws Exception {
        RestConnector con =
                RestConnector.getInstance().init(
                        new HashMap<String, String>(),
                        Constants.HOST,
                        Constants.PORT,
                        Constants.DOMAIN,
                        Constants.PROJECT);


        AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();
        CreateDeleteExample example = new CreateDeleteExample();
        login.login(Constants.USERNAME, Constants.PASSWORD);
        //Build the location of the requirements collection, and build the XML for an entity of type requirement.
        String requirementsUrl = con.buildEntityCollectionUrl("requirement");
        String entityToPostXml =
                ("<Entity Type=\"requirement\"><Fields><Field Name=\"name\"><Value>req"
                        + Double.toHexString(Math.random()) + "</Value></Field><Field Name=\"type-id\"><Value>1</Value></Field></Fields></Entity>");
        //Create the entity on the server-side, keep its URL
        String newlyCreatedEntityUrl = example.createEntity(requirementsUrl, entityToPostXml);
        System.out.println("response for created entity: " + newlyCreatedEntityUrl);
        String deletedEntityResponse = example.deleteEntity(newlyCreatedEntityUrl);
        System.out.println("deleting previously created entity: " + deletedEntityResponse.trim());
        /* now do the same only this time using an object, and not string xml.
        (though we do build the object from the xml, we could have instantiated it differently, theoretically) */
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        Response postedEntityResponse =
                con.httpPost(requirementsUrl, entityToPostXml.getBytes(), requestHeaders);
        String postedEntityResponseXml = postedEntityResponse.toString();
        Entity e = EntityMarshallingUtils.marshal(Entity.class, postedEntityResponseXml);
        System.out.println("posted marshalled entity: "
                + EntityMarshallingUtils.unmarshal(Entity.class, e).trim());
        // The marshalled object is an object in every way
        // Iterating some fields:
        System.out.print("displaying fields from marshalled entity:");
        for (Field f : e.getFields().getField()) {
            System.out.print(f.getName() + "=" + f.getValue() + "; ");
        }
        System.out.println();
        //Getting the location of the new entity from the post response.
        newlyCreatedEntityUrl =
                postedEntityResponse.getResponseHeaders().get("Location").iterator().next();
        deletedEntityResponse = example.deleteEntity(newlyCreatedEntityUrl).toString();
        //So that we can delete it
        System.out.println("deleting previously created entity (marshal/unmarshal): "
                + deletedEntityResponse.trim());
        login.logout();
    }
    /**
     * Use this string to create new "requirement" type entities.
     */
    private final String entityToPostXml =
            ("<Entity Type=\"requirement\"><Fields><Field Name=\"name\"><Value>req"
                    + Double.toHexString(Math.random()) + "</Value></Field><Field Name=\"type-id\"><Value>1</Value></Field></Fields></Entity>");
    private RestConnector con;
    public CreateDeleteExample() {
        con = RestConnector.getInstance();
    }
    /**
     * @param collectionUrl
     *            the url of the collection of the relevant entity type.
     * @param postedEntityXml
     *            the xml describing an instance of said entity type.
     * @return the url of the newly created entity.
     */
    public String createEntity(String collectionUrl, String postedEntityXml) throws Exception {
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        //As can be seen in the implementation below, creating an entity is simply posting its XML into the correct collection.
        Response response = con.httpPost(collectionUrl, postedEntityXml.getBytes(), requestHeaders);
        Exception failure = response.getFailure();
        if (failure != null) {
            throw failure;
        }
        /*
         Note that we also get the XML of the newly created entity.
         At the same time, we get the URL where it was created in a location response header.
        */
        String entityUrl = response.getResponseHeaders().get("Location").iterator().next();
        return entityUrl;
    }
    /**
     * @param entityUrl
     *            the url of the entity that we wish to remove
     * @return xml of deleted entity
     */
    public String deleteEntity(String entityUrl) throws Exception {
        //As we can see from the implementation below, to delete an entity is simply to use HTTP delete on its URL.
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Accept", "application/xml");
        return con.httpDelete(entityUrl, requestHeaders).toString();
    }
    /**
     * @return the entityToPostXml
     */
    public String getEntityToPostXml() {
        return entityToPostXml;
    }
}
