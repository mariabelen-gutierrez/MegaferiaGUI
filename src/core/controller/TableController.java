/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controller;

import core.controller.utils.Response;
import core.controller.utils.Status;
import core.model.Publisher;
import core.model.Stand;
import core.model.persona.Author;
import core.model.persona.Manager;
import core.model.persona.Narrator;
import core.model.persona.Person;
import core.model.persona.utils.FullName;
import core.model.storage.EditorialStorage;
import core.model.storage.PersonaStorage;
import core.model.storage.StandStorage;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class TableController {

    public static Response updateEditorialTable(DefaultTableModel model) {
        try {
            EditorialStorage editorialStorage = EditorialStorage.getInstance();
            ArrayList<Publisher> editoriales = editorialStorage.getEditoriales();

            if (editoriales == null || editoriales.isEmpty()) {
                return new Response("No editorials available to display", Status.NO_CONTENT, editoriales.clone());
            }

            editoriales.sort(Comparator.comparing(Publisher::getNit));
            model.setRowCount(0);
            for (Publisher editorial : editoriales) {
                Object[] rowData = {
                        editorial.getNit(),
                        editorial.getName(),
                        editorial.getAddress(),
                        FullName.unitVariables(editorial.getManager().getFirstname(),
                                editorial.getManager().getLastname()),
                        editorial.getStandQuantity()
                };
                model.addRow(rowData);
            }
            return new Response("Editorial table updated successfully", Status.OK, editoriales.clone());
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static Response updatePersonaTable(DefaultTableModel model) {
        try {
            PersonaStorage personaStorage = PersonaStorage.getInstance();
            ArrayList<Person> personas = personaStorage.getPersonas();
            if (personas == null || personas.isEmpty()) {
                return new Response("No personas available to display", Status.NO_CONTENT);
            }
            personas.sort(Comparator.comparing(Person::getId));
            model.setRowCount(0);
            for (Person persona : personas) {
                Object[] rowData = {
                        persona.getId(),
                        FullName.unitVariables(persona.getFirstname(), persona.getLastname()),
                        persona instanceof Manager ? "Gerente" : persona instanceof Author ? "Autor" : "Narrador",
                        persona instanceof Manager ? ((Manager) persona).getPublisher().getName() : "-",
                        persona instanceof Manager ? "0"
                                : persona instanceof Author ? ((Author) persona).getBookQuantity()
                                        : ((Narrator) persona).getBookQuantity()
                };
                model.addRow(rowData);
            }
            return new Response("Persona table updated successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updateStandTable(DefaultTableModel model) {
        try {
            StandStorage standStorage = StandStorage.getInstance();
            ArrayList<Stand> stands = standStorage.getStands();
            if (stands == null || stands.isEmpty()) {
                return new Response("No stands available to display", Status.NO_CONTENT);
            }
            model.setRowCount(0);
            
            for (Stand stand : stands) {
                String publishers = "";
                if(stand.getPublisherQuantity() > 0){
                    publishers += stand.getPublishers().get(0).getName();
                    for(int i = 1; i < stand.getPublisherQuantity(); i++){
                        publishers += ", " + stand.getPublishers().get(i).getName();
                    } 
                }
                Object[] rowData = {
                        stand.getId(),
                        stand.getPrice(),
                        stand.getPublisherQuantity() > 0 ? "Si" : "No",
                        publishers
                };
                model.addRow(rowData);
            }
            return new Response("Stand table updated successfully", Status.OK);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
