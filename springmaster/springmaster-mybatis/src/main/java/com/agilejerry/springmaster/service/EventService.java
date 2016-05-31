package com.agilejerry.springmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    @Autowired 
    EventAssistService eventAssistService;
    public EventService() {
        // TODO Auto-generated constructor stub
    }
    
    public int calc(int i){
        return eventAssistService.calcByAssist(i*2);
    }

}
