package core.controller;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Stand;
import core.model.storage.EditorialStorage;
import core.model.storage.StandStorage;
import core.model.Publisher;
import java.util.ArrayList;

public class StandController {

    public static Response addStand(String idtxt, String pricetxt) {
        StandStorage standRegister = StandStorage.getInstance();
        ArrayList<Stand> stands= standRegister.getStands();
        try {
            long id;
            double price;
            try {
                id = Long.parseLong(idtxt);
                if (id < 0) {
                    return new Response("ID must be greater than or equal to 0", Status.BAD_REQUEST);
                }
                if (String.valueOf(id).length() > 15) {
                    return new Response("ID must have a maximum of 15 characters.", Status.BAD_REQUEST);
                }
                if (stands != null) {
                    for (Stand s: stands) {
                        if(s.getId()== id){
                            return new Response("Id in use. Enter another id", Status.BAD_REQUEST);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                return new Response("ID must be a number", Status.BAD_REQUEST);
            }

            try {
                price = Double.parseDouble(pricetxt);
                if (price <= 0) {
                    return new Response("Price must be greater than to 0", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Price must be a number", Status.BAD_REQUEST);
            }
            
            Stand standTemp = new Stand(id, price);
            stands.add(standTemp);
            
            return new Response("Stand created successfully", Status.CREATED);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static Response addStandForCompra(String standData, String standsSelectedData ){
        if (standsSelectedData.contains(standData)) {
            return new Response("Stand is in the list", Status.BAD_REQUEST);
        }else{
            return new Response("Stand added to book successfully", Status.OK);
        }
    }

    public static Response comprarStand(String standsData, String editorialesData){
        StandStorage standStorage = StandStorage.getInstance();
        EditorialStorage editorialStorage = EditorialStorage.getInstance();
        ArrayList<Stand> stands = standStorage.getStands();
        ArrayList<Publisher> editoriales = editorialStorage.getEditoriales();
        ArrayList<Stand> standsComprados = new ArrayList<>();
        ArrayList<Publisher> editorialesCompradas = new ArrayList<>();
        if (standsData.isBlank() || standsData.isEmpty()) {
            return new Response("Select a Stand", Status.BAD_REQUEST);
        }
        if (editorialesData.isBlank() || editorialesData.isEmpty()) {
            return new Response("Select a Publisher", Status.BAD_REQUEST);
        }
        String[] standInfo = standsData.split("\n");
    
        for(String standId: standInfo){
            for(Stand stand: stands){
                if(stand.getId() == Long.parseLong(standId)){
                    standsComprados.add(stand);
                }
            }
        }

        String[] editorialInfo = editorialesData.split("\n");
        
        for(String editoriall: editorialInfo){
            String nitEditorial = editoriall.split(" ")[1].replace("(", "").replace(")", "");
            for(Publisher editorial: editoriales){
                if(editorial.getNit().equals(nitEditorial)){
                    editorialesCompradas.add(editorial);
                }
            }
        }

        for(Stand stand: standsComprados){
            for(Publisher editorial: editorialesCompradas){
                stand.addPublisher(editorial);
                editorial.addStand(stand);
            }
        }
        return new Response("Stand(s) purchased successfully", Status.OK);

    }


}
