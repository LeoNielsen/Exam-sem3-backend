/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Tmp2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class Tmp2DTO {
    private long id;
    String dummy;

    public Tmp2DTO(Tmp2 tmp2) {
        if(tmp2.getId() != null)
            this.id = tmp2.getId();
        this.dummy = tmp2.getDummy();
    }

    public static List<Tmp2DTO> getDTOs(List<Tmp2> tmp2s){
        List<Tmp2DTO> tmp2DTOs = new ArrayList();
        tmp2s.forEach(tmp2->tmp2DTOs.add(new Tmp2DTO(tmp2)));
        return tmp2DTOs;
    }

    public long getId() {
        return id;
    }

    public String getDummy() {
        return dummy;
    }
}
