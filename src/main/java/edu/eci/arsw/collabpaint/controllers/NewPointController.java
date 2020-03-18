package edu.eci.arsw.collabpaint.controllers;

import edu.eci.arsw.collabpaint.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class NewPointController {

    @Autowired
    SimpMessagingTemplate msgt;

    private ConcurrentHashMap<String, CopyOnWriteArrayList<Point>> puntos = new ConcurrentHashMap<>();

    @MessageMapping("/newpoint.{id}")
    public void handlePointEvent(Point point,@DestinationVariable String id) throws Exception {
        //System.out.println("Nuevo punto recibido en el servidor!:"+point);
        if(puntos.containsKey(id)){
            puntos.get(id).add(point);
        }else{
            CopyOnWriteArrayList nueva =new CopyOnWriteArrayList<Point>();
            nueva.add(point);
            puntos.put(id,nueva);
        }
        msgt.convertAndSend("/topic/newpoint."+id, point);
        if (puntos.get(id).size() >= 4){
            msgt.convertAndSend("/topic/newpolygon."+id, puntos.get(id));
        }
    }
}