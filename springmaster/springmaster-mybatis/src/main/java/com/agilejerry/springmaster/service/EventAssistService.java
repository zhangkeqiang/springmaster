package com.agilejerry.springmaster.service;

import org.springframework.stereotype.Service;


@Service
public class EventAssistService {

    public EventAssistService() {
        // TODO Auto-generated constructor stub
    }
    
    public int calcByAssist(int i){
        return i*12+i*i;
    }

}
