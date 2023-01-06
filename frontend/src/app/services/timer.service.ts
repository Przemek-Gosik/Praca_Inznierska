import { Injectable, OnInit } from "@angular/core";

@Injectable({
    providedIn: 'root'
  })
export class TimerService {
interval: number = 0
hundredsOfSecond : number = 0
seconds : number= 0 
minutes : number = 0 

startTimer(){
    this.interval = window.setInterval(()=> {
        this.hundredsOfSecond+=1
        if(this.hundredsOfSecond == 100){
          this.seconds +=1
          this.hundredsOfSecond = 0
        }
        if(this.seconds == 60){
          this.minutes +=1
          this.seconds = 0
        }
      },10)
}

stopTimer(){
    clearInterval(this.interval)
}

clearTimer(){
  this.stopTimer()
  this.minutes = 0
  this.seconds = 0
  this.hundredsOfSecond = 0
}

addUnits(unit: number):string{
    var time :string = ""
    if(unit < 10){
      time += "0"
    }
    time += unit.toString()
    return time
  }

  calculateTime(){
    var timeElapsed = this.minutes *60
    timeElapsed += this.seconds
    timeElapsed += this.hundredsOfSecond/100
    return timeElapsed
  }

  displayTime():string{
    var timeDisplay = ""
    timeDisplay += this.addUnits(this.minutes)
    timeDisplay +=":"
    timeDisplay += this.addUnits(this.seconds)
    timeDisplay +=":"
    timeDisplay +=this.addUnits(this.hundredsOfSecond)
    return timeDisplay
  }

  getCurrentDate():string{
    return new Date().toISOString()
  }

}