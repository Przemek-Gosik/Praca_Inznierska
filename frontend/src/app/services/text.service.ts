import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })

export class TextService{
  getLevelName(level:string):string{
    switch(level){
      case "EASY":{
        return "Łatwy"
      }
      case "MEDIUM":{
        return "Średni"
      }
      default:{
        return "Trudny"
      }
    }
  }

  showDate(date:string):string{
    const dateTime = new Date(date);
    return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`
  }
    
}