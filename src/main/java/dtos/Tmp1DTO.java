/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Tmp1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class Tmp1DTO {
    private long id;
    String dummy;

    public Tmp1DTO(Tmp1 tmp1) {
        if(tmp1.getId() != null)
            this.id = tmp1.getId();
        this.dummy = tmp1.getDummy();
    }

    public static List<Tmp1DTO> getDTOs(List<Tmp1> tmp1s){
        List<Tmp1DTO> tmp1DTOs = new ArrayList();
        tmp1s.forEach(tmp1->tmp1DTOs.add(new Tmp1DTO(tmp1)));
        return tmp1DTOs;
    }

    public long getId() {
        return id;
    }

    public String getDummy() {
        return dummy;
    }
}
