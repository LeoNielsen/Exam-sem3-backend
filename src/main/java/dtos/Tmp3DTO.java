/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Tmp3;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class Tmp3DTO {
    private long id;
    String dummy;

    public Tmp3DTO(Tmp3 tmp3) {
        if(tmp3.getId() != null)
            this.id = tmp3.getId();
        this.dummy = tmp3.getDummy();
    }

    public static List<Tmp3DTO> getDTOs(List<Tmp3> tmp3s){
        List<Tmp3DTO> tmp3DTOs = new ArrayList();
        tmp3s.forEach(tmp3->tmp3DTOs.add(new Tmp3DTO(tmp3)));
        return tmp3DTOs;
    }

    public long getId() {
        return id;
    }

    public String getDummy() {
        return dummy;
    }
}
