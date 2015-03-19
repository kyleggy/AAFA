package com.hp.fedex.day.aafa.qc.readFromQC;

    /**
 * Created by chengya on 2015/3/18.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Constants;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Entity;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.EntityMarshallingUtils;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.RestConnector;
import com.hp.fedex.day.aafa.qc.readFromQC.infrastructure.Entity.Fields.Field;
/**
 * This example shows how to read collections and entities as XML or into objects.
 */
public class ReadExample {
    public static void main(String[] args) throws Exception {

        RestConnector con =
                RestConnector.getInstance().init(
                        new HashMap<String, String>(),
                        Constants.HOST,
                        Constants.PORT,
                        Constants.DOMAIN,
                        Constants.PROJECT);


        AuthenticateLoginLogoutExample login = new AuthenticateLoginLogoutExample();
        login.login(Constants.USERNAME, Constants.PASSWORD);
        String requirementsUrl = con.buildEntityCollectionUrl("defects");
        //Read a simple resource. This example is not an entity.
        String resourceWeWantToRead = con.buildUrl("qcbin/rest/server");
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Accept", "application/xml");
        String responseStr = con.httpGet(resourceWeWantToRead, null, requestHeaders).toString();
        System.out.println("server properties from rest: " + responseStr.trim());
        System.out.println("cookies after first service (creates an implicit session on the server, delivered using the cookie QCSession):"
                + con.getCookieString());

        CreateDeleteExample writeExample = new CreateDeleteExample();
        String newCreatedResourceUrl = writeExample.createEntity(requirementsUrl, writeExample.getEntityToPostXml());
        //Query a collection of entities:
        StringBuilder b = new StringBuilder();
        //The query: "where field name starts with r"
        b.append("?query={id[*6198]}");
        //The fields to display: id, name
        b.append("&fields=id,name");
        //The sort order: descending by ID (highest ID first)
        b.append("&order-by={id[DESC]}");
        //Display 10 results
        b.append("&page-size=10");
        //Counting from the 1st result, inclusive
        b.append("&start-index=1");
        String listFromCollectionAsXml =
                con.httpGet(requirementsUrl, b.toString(), requestHeaders).toString();
        System.out.println("response for list requirements: " + listFromCollectionAsXml);
        //Read the entity we generated in the above step. Perform a get operation on its URL.
        String postedEntityReturnedXml =
                con.httpGet(newCreatedResourceUrl, null, requestHeaders).toString();
        System.out.println("response for retrieving entity: " + postedEntityReturnedXml.trim());
        //xml -> class instance
        Entity entity = EntityMarshallingUtils.marshal(Entity.class, postedEntityReturnedXml);
        //Now show that you can do something with that object
        List<Field> fields = entity.getFields().getField();
        System.out.print("listing fields from marshalled object: ");
        for (Field field : fields) {
            System.out.print(field.getName() + "=" + field.getValue() + ", ");
        }
        System.out.println("");
        //cleanup
        writeExample.deleteEntity(newCreatedResourceUrl).toString().trim();
        login.logout();
    }
}